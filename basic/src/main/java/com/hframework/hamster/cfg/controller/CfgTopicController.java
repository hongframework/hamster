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
import com.hframework.hamster.cfg.domain.model.CfgTopic;
import com.hframework.hamster.cfg.domain.model.CfgTopic_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgTopicSV;

@Controller
@RequestMapping(value = "/cfg/cfgTopic")
public class CfgTopicController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgTopicController.class);

	@Resource
	private ICfgTopicSV iCfgTopicSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示主题列表
     * @param cfgTopic
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgTopic") CfgTopic cfgTopic,
                                      @ModelAttribute("example") CfgTopic_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgTopic, example, pagination);
        try{
            ExampleUtils.parseExample(cfgTopic, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgTopic> list = iCfgTopicSV.getCfgTopicListByExample(example);
            pagination.setTotalCount(iCfgTopicSV.getCfgTopicCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示主题明细
     * @param cfgTopic
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgTopic") CfgTopic cfgTopic){
        logger.debug("request : {},{}", cfgTopic.getCfgTopicId(), cfgTopic);
        try{
            CfgTopic result = null;
            if(cfgTopic.getCfgTopicId() != null) {
                result = iCfgTopicSV.getCfgTopicByPK(cfgTopic.getCfgTopicId());
            }else {
                CfgTopic_Example example = ExampleUtils.parseExample(cfgTopic, CfgTopic_Example.class);
                List<CfgTopic> list = iCfgTopicSV.getCfgTopicListByExample(example);
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
    * 搜索一个主题
    * @param  cfgTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgTopic") CfgTopic  cfgTopic){
        logger.debug("request : {}",  cfgTopic);
        try{
            CfgTopic result = null;
            if(cfgTopic.getCfgTopicId() != null) {
                result =  iCfgTopicSV.getCfgTopicByPK(cfgTopic.getCfgTopicId());
            }else {
                CfgTopic_Example example = ExampleUtils.parseExample( cfgTopic, CfgTopic_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgTopic> list =  iCfgTopicSV.getCfgTopicListByExample(example);
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
    * 创建主题
    * @param cfgTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgTopic") CfgTopic cfgTopic) {
        logger.debug("request : {}", cfgTopic);
        try {
            ControllerHelper.setDefaultValue(cfgTopic, "cfgTopicId");
            int result = iCfgTopicSV.create(cfgTopic);
            if(result > 0) {
                return ResultData.success(cfgTopic);
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
    * 批量维护主题
    * @param cfgTopics
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgTopic[] cfgTopics) {
        logger.debug("request : {}", cfgTopics);

        try {
            ControllerHelper.setDefaultValue(cfgTopics, "cfgTopicId");
            ControllerHelper.reorderProperty(cfgTopics);

            int result = iCfgTopicSV.batchOperate(cfgTopics);
            if(result > 0) {
                return ResultData.success(cfgTopics);
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
    * 更新主题
    * @param cfgTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgTopic") CfgTopic cfgTopic) {
        logger.debug("request : {}", cfgTopic);
        try {
            ControllerHelper.setDefaultValue(cfgTopic, "cfgTopicId");
            int result = iCfgTopicSV.update(cfgTopic);
            if(result > 0) {
                return ResultData.success(cfgTopic);
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
    * 创建或更新主题
    * @param cfgTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgTopic") CfgTopic cfgTopic) {
        logger.debug("request : {}", cfgTopic);
        try {
            ControllerHelper.setDefaultValue(cfgTopic, "cfgTopicId");
            int result = iCfgTopicSV.batchOperate(new CfgTopic[]{cfgTopic});
            if(result > 0) {
                return ResultData.success(cfgTopic);
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
    * 删除主题
    * @param cfgTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgTopic") CfgTopic cfgTopic) {
        logger.debug("request : {}", cfgTopic);

        try {
            ControllerHelper.setDefaultValue(cfgTopic, "cfgTopicId");
            int result = iCfgTopicSV.delete(cfgTopic);
            if(result > 0) {
                return ResultData.success(cfgTopic);
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
 	
	public ICfgTopicSV getICfgTopicSV(){
		return iCfgTopicSV;
	}
	//setter
	public void setICfgTopicSV(ICfgTopicSV iCfgTopicSV){
    	this.iCfgTopicSV = iCfgTopicSV;
    }
}
