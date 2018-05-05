//js交互逻辑
var seckill = {

    //封装秒杀相关ajaxURL地址
    url:{
        now:function () {
            return '/seckill/time/now';
        },
        exposer:function (secKillId) {
            return '/seckill/'+secKillId+'/exposer';
        },
        killURL:function (secKillId,md5) {
            return '/seckill/'+secKillId+'/'+md5+'/execute';
        }

    },

    validatePhone:function (phone) {
        var r=/^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}))$/;
        if(phone && phone.length ==11 && r.test(phone)){
            return true;
        }else {
            return false;
        }
    },
    handlerKill:function (secKillId,node) {
        //获取秒杀地址，控制显示逻辑，执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg " id="killBtn">开始秒杀</button>');

        //发送Post请求，获取秒杀接口，这样才能拿到 秒杀商品地址中的MD5
        $.post(seckill.url.exposer(secKillId),{},function (result) {
            //获取秒杀地址，控制显示逻辑，执行秒杀
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    //已经开启秒杀
                    var md5 = exposer['md5'];
                    var killURL = seckill.url.killURL(secKillId,md5);
                    console.log("killURL:"+killURL);

                    //只接收一次点击事件，防止秒杀时用户多次反复点击
                    $('#killBtn').one('click',function () {
                        //点击完按键之后执行
                        // 禁用按钮
                        $(this).addClass('disabled');

                        //发送秒杀请求
                        $.post(killURL,{},function (result) {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //显示秒杀结果
                                console.log(result);
                                node.html('<span class="label label-success">'+stateInfo+'</span>')
                            }else {
                                console.log(result);
                            }
                        })
                    })

                }else {
                    //未开始秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(secKillId,start,end,now);
                }
            }else {
                console.log(result);
            }

        });

        node.show();

    },
    countDown:function(secKillId,startTime,endTime,nowTime){

            var seckillBox = $('#seckill-box');

            if(nowTime>endTime){
            //秒杀结束
                seckillBox.html('秒杀结束');
            }else if(nowTime < startTime){
                //秒杀未开始,计时

                var killTime = new Date(startTime + 1000);

                //jQuerycountdown计时插件使用
                seckillBox.countdown(killTime,function (event) {
                    var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                    seckillBox.html(format);
                }).on('finish.countdown',function () { //时间跑完之后 触发的事件
                    //获取秒杀地址，控制显示逻辑，执行秒杀
                    seckill.handlerKill(secKillId,seckillBox);
                });
            }else {
                //秒杀开始
                seckill.handlerKill(secKillId,seckillBox);
            }
    },
    //详情页秒杀逻辑
    detail:{
        init:function(params){

        //    验证手机:在cookie中查找手机号
            var killPhone = $.cookie('killPhone');

            //验证不通过
            if(!seckill.validatePhone(killPhone)){
                var killPhoneModal = $('#killPhoneModal');

                //显示弹出层
                killPhoneModal.modal({
                        show:true,//显示弹出层
                        backdrop:'static', //禁止位置关闭
                        keyboard:false//关闭键盘事件
                    }
                );

                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        //验证通过
                        //电话写入cookie,有效期7天，只在/seckill模块下有效
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        console.log(killPhone);
                        // 刷新页面
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300)
                    }
                })

            }

            //已经登录

            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var secKillId = params['secKillId'];

            //向后端发送get请求，获取当前系统时间
            $.get(seckill.url.now(),{},function (result){

                if(result && result['success']){
                    // console.log(result);
                    var nowTime = result['data']; //拿到的是毫秒
                    seckill.countDown(secKillId,startTime,endTime,nowTime);
                }else {
                    console.log("获取当前系统时间失败:"+result);
                }
            });



        }
    }
}