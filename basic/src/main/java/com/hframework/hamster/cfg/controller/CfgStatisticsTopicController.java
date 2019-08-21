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
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsTopicSV;

@Controller
@RequestMapping(value = "/cfg/cfgStatisticsTopic")
public class CfgStatisticsTopicController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgStatisticsTopicController.class);

	@Resource
	private ICfgStatisticsTopicSV iCfgStatisticsTopicSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示统计主题列表
     * @param cfgStatisticsTopic
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgStatisticsTopic") CfgStatisticsTopic cfgStatisticsTopic,
                                      @ModelAttribute("example") CfgStatisticsTopic_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgStatisticsTopic, example, pagination);
        try{
            ExampleUtils.parseExample(cfgStatisticsTopic, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgStatisticsTopic> list = iCfgStatisticsTopicSV.getCfgStatisticsTopicListByExample(example);
            pagination.setTotalCount(iCfgStatisticsTopicSV.getCfgStatisticsTopicCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示统计主题明细
     * @param cfgStatisticsTopic
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgStatisticsTopic") CfgStatisticsTopic cfgStatisticsTopic){
        logger.debug("request : {},{}", cfgStatisticsTopic.getCfgStatisticsTopicId(), cfgStatisticsTopic);
        try{
            CfgStatisticsTopic result = null;
            if(cfgStatisticsTopic.getCfgStatisticsTopicId() != null) {
                result = iCfgStatisticsTopicSV.getCfgStatisticsTopicByPK(cfgStatisticsTopic.getCfgStatisticsTopicId());
            }else {
                CfgStatisticsTopic_Example example = ExampleUtils.parseExample(cfgStatisticsTopic, CfgStatisticsTopic_Example.class);
                List<CfgStatisticsTopic> list = iCfgStatisticsTopicSV.getCfgStatisticsTopicListByExample(example);
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
    * 搜索一个统计主题
    * @param  cfgStatisticsTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgStatisticsTopic") CfgStatisticsTopic  cfgStatisticsTopic){
        logger.debug("request : {}",  cfgStatisticsTopic);
        try{
            CfgStatisticsTopic result = null;
            if(cfgStatisticsTopic.getCfgStatisticsTopicId() != null) {
                result =  iCfgStatisticsTopicSV.getCfgStatisticsTopicByPK(cfgStatisticsTopic.getCfgStatisticsTopicId());
            }else {
                CfgStatisticsTopic_Example example = ExampleUtils.parseExample( cfgStatisticsTopic, CfgStatisticsTopic_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgStatisticsTopic> list =  iCfgStatisticsTopicSV.getCfgStatisticsTopicListByExample(example);
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
    * 创建统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgStatisticsTopic") CfgStatisticsTopic cfgStatisticsTopic) {
        logger.debug("request : {}", cfgStatisticsTopic);
        try {
            ControllerHelper.setDefaultValue(cfgStatisticsTopic, "cfgStatisticsTopicId");
            int result = iCfgStatisticsTopicSV.create(cfgStatisticsTopic);
            if(result > 0) {
                return ResultData.success(cfgStatisticsTopic);
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
    * 批量维护统计主题
    * @param cfgStatisticsTopics
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgStatisticsTopic[] cfgStatisticsTopics) {
        logger.debug("request : {}", cfgStatisticsTopics);

        try {
            ControllerHelper.setDefaultValue(cfgStatisticsTopics, "cfgStatisticsTopicId");
            ControllerHelper.reorderProperty(cfgStatisticsTopics);

            int result = iCfgStatisticsTopicSV.batchOperate(cfgStatisticsTopics);
            if(result > 0) {
                return ResultData.success(cfgStatisticsTopics);
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
    * 更新统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgStatisticsTopic") CfgStatisticsTopic cfgStatisticsTopic) {
        logger.debug("request : {}", cfgStatisticsTopic);
        try {
            ControllerHelper.setDefaultValue(cfgStatisticsTopic, "cfgStatisticsTopicId");
            int result = iCfgStatisticsTopicSV.update(cfgStatisticsTopic);
            if(result > 0) {
                return ResultData.success(cfgStatisticsTopic);
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
    * 创建或更新统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgStatisticsTopic") CfgStatisticsTopic cfgStatisticsTopic) {
        logger.debug("request : {}", cfgStatisticsTopic);
        try {
            ControllerHelper.setDefaultValue(cfgStatisticsTopic, "cfgStatisticsTopicId");
            int result = iCfgStatisticsTopicSV.batchOperate(new CfgStatisticsTopic[]{cfgStatisticsTopic});
            if(result > 0) {
                return ResultData.success(cfgStatisticsTopic);
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
    * 删除统计主题
    * @param cfgStatisticsTopic
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgStatisticsTopic") CfgStatisticsTopic cfgStatisticsTopic) {
        logger.debug("request : {}", cfgStatisticsTopic);

        try {
            ControllerHelper.setDefaultValue(cfgStatisticsTopic, "cfgStatisticsTopicId");
            int result = iCfgStatisticsTopicSV.delete(cfgStatisticsTopic);
            if(result > 0) {
                return ResultData.success(cfgStatisticsTopic);
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
 	
	public ICfgStatisticsTopicSV getICfgStatisticsTopicSV(){
		return iCfgStatisticsTopicSV;
	}
	//setter
	public void setICfgStatisticsTopicSV(ICfgStatisticsTopicSV iCfgStatisticsTopicSV){
    	this.iCfgStatisticsTopicSV = iCfgStatisticsTopicSV;
    }
}
