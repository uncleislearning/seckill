package com.xiao.seckill.web;

import com.xiao.seckill.common.SecKillState;
import com.xiao.seckill.dto.Exposer;
import com.xiao.seckill.dto.SecKillExection;
import com.xiao.seckill.dto.SecKillResult;
import com.xiao.seckill.entity.SecKill;
import com.xiao.seckill.exceptions.RepeatKillException;
import com.xiao.seckill.exceptions.SecKillClosedException;
import com.xiao.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by unclexiao on 31/03/2018.
 */
@Controller
@RequestMapping(value = "/seckill") //模块
public class SecKillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        List<SecKill> secKills = secKillService.getSecKillList();

        model.addAttribute("list", secKills);
        return "list";
    }


    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }

        SecKill secKill = secKillService.getSecKillById(seckillId);

        if (secKill == null) {
            return "forward:/seckill/list";
        }


        model.addAttribute("secKill", secKill);
        return "detail";
    }


    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SecKillResult<Exposer> result;

        try {
            Exposer exposer = secKillService.exportSecKillUrl(seckillId);
            result = new SecKillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result = new SecKillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<SecKillExection> execute(@PathVariable("seckillId") Long seckillId
            , @PathVariable("md5") String md5
            , @CookieValue(value = "killPhone", required = false) Long phone) {

        if(phone == null){
            return new SecKillResult<SecKillExection>(false,"未注册");
        }

        try {
//            SecKillExection exection = secKillService.executeSecKill(seckillId, phone, md5);

            SecKillExection exection = secKillService.executeSecKillByProcedure(seckillId,phone,md5);
            return new SecKillResult<SecKillExection>(true, exection);
        }catch (RepeatKillException e){
            SecKillExection secKillExection = new SecKillExection(seckillId, SecKillState.REPEAT_KILL);
            return new SecKillResult<SecKillExection>(true,secKillExection);
        }catch (SecKillClosedException e){
            SecKillExection secKillExection = new SecKillExection(seckillId,SecKillState.END);
            return new SecKillResult<SecKillExection>(true,secKillExection);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            SecKillExection secKillExection = new SecKillExection(seckillId,SecKillState.INNER_ERROR);
            return new SecKillResult<SecKillExection>(true,secKillExection);
        }


    }


    @RequestMapping(value = "/time/now",method = RequestMethod.GET
            ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public SecKillResult<Long> now(){
        Date now = new Date();
        //返回的是毫秒
        return new SecKillResult<Long>(true,now.getTime());
    }


}
