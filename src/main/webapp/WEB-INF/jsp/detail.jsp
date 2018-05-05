<%@ page contentType="text/html;charset=utf-8" language="java" %>

<%--引入JSTL标签库--%>
<%@include file="common/tag.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<%--页面显示部分--%>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${secKill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <%--显示time图标--%>
                <span class="glyphicon-time "></span>
                <%--展示倒计时--%>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>

</div>

<%--登录弹出层 输入电话--%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="请填手机号" class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"></span>

                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>

</body>



<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<%--jquery-cookie插件--%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>

<%--jquery-countdown计时插件--%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>



<%--编写交互逻辑--%>

<%--引入js脚本--%>
<script src="/resource/script/seckill.js" type="text/javascript"></script>


<%--调用引入的js方法--%>
<script type="text/javascript">
    $(function () {
        seckill.detail.init({
            secKillId:${secKill.secKillId},
            startTime:${secKill.startTime.time}, //毫秒
            endTime:${secKill.endTime.time}
        })
    });

</script>


</html>