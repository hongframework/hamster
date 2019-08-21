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
import com.hframework.hamster.cfg.domain.model.CfgBroker;
import com.hframework.hamster.cfg.domain.model.CfgBroker_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgBrokerSV;

@Controller
@RequestMapping(value = "/cfg/cfgBroker")
public class CfgBrokerController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgBrokerController.class);

	@Resource
	private ICfgBrokerSV iCfgBrokerSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示消息队列列表
     * @param cfgBroker
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgBroker") CfgBroker cfgBroker,
                                      @ModelAttribute("example") CfgBroker_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgBroker, example, pagination);
        try{
            ExampleUtils.parseExample(cfgBroker, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgBroker> list = iCfgBrokerSV.getCfgBrokerListByExample(example);
            pagination.setTotalCount(iCfgBrokerSV.getCfgBrokerCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示消息队列明细
     * @param cfgBroker
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgBroker") CfgBroker cfgBroker){
        logger.debug("request : {},{}", cfgBroker.getCfgBrokerId(), cfgBroker);
        try{
            CfgBroker result = null;
            if(cfgBroker.getCfgBrokerId() != null) {
                result = iCfgBrokerSV.getCfgBrokerByPK(cfgBroker.getCfgBrokerId());
            }else {
                CfgBroker_Example example = ExampleUtils.parseExample(cfgBroker, CfgBroker_Example.class);
                List<CfgBroker> list = iCfgBrokerSV.getCfgBrokerListByExample(example);
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
    * 搜索一个消息队列
    * @param  cfgBroker
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgBroker") CfgBroker  cfgBroker){
        logger.debug("request : {}",  cfgBroker);
        try{
            CfgBroker result = null;
            if(cfgBroker.getCfgBrokerId() != null) {
                result =  iCfgBrokerSV.getCfgBrokerByPK(cfgBroker.getCfgBrokerId());
            }else {
                CfgBroker_Example example = ExampleUtils.parseExample( cfgBroker, CfgBroker_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgBroker> list =  iCfgBrokerSV.getCfgBrokerListByExample(example);
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
    * 创建消息队列
    * @param cfgBroker
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgBroker") CfgBroker cfgBroker) {
        logger.debug("request : {}", cfgBroker);
        try {
            ControllerHelper.setDefaultValue(cfgBroker, "cfgBrokerId");
            int result = iCfgBrokerSV.create(cfgBroker);
            if(result > 0) {
                return ResultData.success(cfgBroker);
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
    * 批量维护消息队列
    * @param cfgBrokers
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgBroker[] cfgBrokers) {
        logger.debug("request : {}", cfgBrokers);

        try {
            ControllerHelper.setDefaultValue(cfgBrokers, "cfgBrokerId");
            ControllerHelper.reorderProperty(cfgBrokers);

            int result = iCfgBrokerSV.batchOperate(cfgBrokers);
            if(result > 0) {
                return ResultData.success(cfgBrokers);
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
    * 更新消息队列
    * @param cfgBroker
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgBroker") CfgBroker cfgBroker) {
        logger.debug("request : {}", cfgBroker);
        try {
            ControllerHelper.setDefaultValue(cfgBroker, "cfgBrokerId");
            int result = iCfgBrokerSV.update(cfgBroker);
            if(result > 0) {
                return ResultData.success(cfgBroker);
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
    * 创建或更新消息队列
    * @param cfgBroker
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgBroker") CfgBroker cfgBroker) {
        logger.debug("request : {}", cfgBroker);
        try {
            ControllerHelper.setDefaultValue(cfgBroker, "cfgBrokerId");
            int result = iCfgBrokerSV.batchOperate(new CfgBroker[]{cfgBroker});
            if(result > 0) {
                return ResultData.success(cfgBroker);
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
    * 删除消息队列
    * @param cfgBroker
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgBroker") CfgBroker cfgBroker) {
        logger.debug("request : {}", cfgBroker);

        try {
            ControllerHelper.setDefaultValue(cfgBroker, "cfgBrokerId");
            int result = iCfgBrokerSV.delete(cfgBroker);
            if(result > 0) {
                return ResultData.success(cfgBroker);
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
 	
	public ICfgBrokerSV getICfgBrokerSV(){
		return iCfgBrokerSV;
	}
	//setter
	public void setICfgBrokerSV(ICfgBrokerSV iCfgBrokerSV){
    	this.iCfgBrokerSV = iCfgBrokerSV;
    }
}
