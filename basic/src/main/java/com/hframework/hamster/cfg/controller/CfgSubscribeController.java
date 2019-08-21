package com.hframework.hamster.cfg.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.ExampleUtils;
import com.hframework.beans.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import com.hframework.web.ControllerHelper;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeSV;

@Controller
@RequestMapping(value = "/cfg/cfgSubscribe")
public class CfgSubscribeController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgSubscribeController.class);

	@Resource
	private ICfgSubscribeSV iCfgSubscribeSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示订阅列表
     * @param cfgSubscribe
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgSubscribe") CfgSubscribe cfgSubscribe,
                                      @ModelAttribute("example") CfgSubscribe_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgSubscribe, example, pagination);
        try{
            ExampleUtils.parseExample(cfgSubscribe, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgSubscribe> list = iCfgSubscribeSV.getCfgSubscribeListByExample(example);
            pagination.setTotalCount(iCfgSubscribeSV.getCfgSubscribeCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示订阅明细
     * @param cfgSubscribe
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgSubscribe") CfgSubscribe cfgSubscribe){
        logger.debug("request : {},{}", cfgSubscribe.getCfgSubscribeId(), cfgSubscribe);
        try{
            CfgSubscribe result = null;
            if(cfgSubscribe.getCfgSubscribeId() != null) {
                result = iCfgSubscribeSV.getCfgSubscribeByPK(cfgSubscribe.getCfgSubscribeId());
            }else {
                CfgSubscribe_Example example = ExampleUtils.parseExample(cfgSubscribe, CfgSubscribe_Example.class);
                List<CfgSubscribe> list = iCfgSubscribeSV.getCfgSubscribeListByExample(example);
                if(list != null && list.size() == 1) {
                    result = list.get(0);
                }
            }

            if(result != null) {
                return ResultData.success(result);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
    * 搜索一个订阅
    * @param  cfgSubscribe
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgSubscribe") CfgSubscribe  cfgSubscribe){
        logger.debug("request : {}",  cfgSubscribe);
        try{
            CfgSubscribe result = null;
            if(cfgSubscribe.getCfgSubscribeId() != null) {
                result =  iCfgSubscribeSV.getCfgSubscribeByPK(cfgSubscribe.getCfgSubscribeId());
            }else {
                CfgSubscribe_Example example = ExampleUtils.parseExample( cfgSubscribe, CfgSubscribe_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgSubscribe> list =  iCfgSubscribeSV.getCfgSubscribeListByExample(example);
                if(list != null && list.size() > 0) {
                    result = list.get(0);
                }
            }

            if(result != null) {
                return ResultData.success(result);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
    * 创建订阅
    * @param cfgSubscribe
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgSubscribe") CfgSubscribe cfgSubscribe) {
        logger.debug("request : {}", cfgSubscribe);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribe, "cfgSubscribeId");
            int result = iCfgSubscribeSV.create(cfgSubscribe);
            if(result > 0) {
                return ResultData.success(cfgSubscribe);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 批量维护订阅
    * @param cfgSubscribes
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgSubscribe[] cfgSubscribes) {
        logger.debug("request : {}", cfgSubscribes);

        try {
            ControllerHelper.setDefaultValue(cfgSubscribes, "cfgSubscribeId");
            ControllerHelper.reorderProperty(cfgSubscribes);

            int result = iCfgSubscribeSV.batchOperate(cfgSubscribes);
            if(result > 0) {
                return ResultData.success(cfgSubscribes);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新订阅
    * @param cfgSubscribe
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgSubscribe") CfgSubscribe cfgSubscribe) {
        logger.debug("request : {}", cfgSubscribe);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribe, "cfgSubscribeId");
            int result = iCfgSubscribeSV.update(cfgSubscribe);
            if(result > 0) {
                return ResultData.success(cfgSubscribe);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 创建或更新订阅
    * @param cfgSubscribe
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgSubscribe") CfgSubscribe cfgSubscribe) {
        logger.debug("request : {}", cfgSubscribe);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribe, "cfgSubscribeId");
            int result = iCfgSubscribeSV.batchOperate(new CfgSubscribe[]{cfgSubscribe});
            if(result > 0) {
                return ResultData.success(cfgSubscribe);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除订阅
    * @param cfgSubscribe
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgSubscribe") CfgSubscribe cfgSubscribe) {
        logger.debug("request : {}", cfgSubscribe);

        try {
            ControllerHelper.setDefaultValue(cfgSubscribe, "cfgSubscribeId");
            int result = iCfgSubscribeSV.delete(cfgSubscribe);
            if(result > 0) {
                return ResultData.success(cfgSubscribe);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public ICfgSubscribeSV getICfgSubscribeSV(){
		return iCfgSubscribeSV;
	}
	//setter
	public void setICfgSubscribeSV(ICfgSubscribeSV iCfgSubscribeSV){
    	this.iCfgSubscribeSV = iCfgSubscribeSV;
    }
}
