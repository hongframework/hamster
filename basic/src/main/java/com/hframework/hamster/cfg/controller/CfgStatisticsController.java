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
import com.hframework.hamster.cfg.domain.model.CfgStatistics;
import com.hframework.hamster.cfg.domain.model.CfgStatistics_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsSV;

@Controller
@RequestMapping(value = "/cfg/cfgStatistics")
public class CfgStatisticsController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgStatisticsController.class);

	@Resource
	private ICfgStatisticsSV iCfgStatisticsSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示流量统计列表
     * @param cfgStatistics
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgStatistics") CfgStatistics cfgStatistics,
                                      @ModelAttribute("example") CfgStatistics_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgStatistics, example, pagination);
        try{
            ExampleUtils.parseExample(cfgStatistics, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgStatistics> list = iCfgStatisticsSV.getCfgStatisticsListByExample(example);
            pagination.setTotalCount(iCfgStatisticsSV.getCfgStatisticsCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示流量统计明细
     * @param cfgStatistics
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgStatistics") CfgStatistics cfgStatistics){
        logger.debug("request : {},{}", cfgStatistics.getCfgStatisticsId(), cfgStatistics);
        try{
            CfgStatistics result = null;
            if(cfgStatistics.getCfgStatisticsId() != null) {
                result = iCfgStatisticsSV.getCfgStatisticsByPK(cfgStatistics.getCfgStatisticsId());
            }else {
                CfgStatistics_Example example = ExampleUtils.parseExample(cfgStatistics, CfgStatistics_Example.class);
                List<CfgStatistics> list = iCfgStatisticsSV.getCfgStatisticsListByExample(example);
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
    * 搜索一个流量统计
    * @param  cfgStatistics
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgStatistics") CfgStatistics  cfgStatistics){
        logger.debug("request : {}",  cfgStatistics);
        try{
            CfgStatistics result = null;
            if(cfgStatistics.getCfgStatisticsId() != null) {
                result =  iCfgStatisticsSV.getCfgStatisticsByPK(cfgStatistics.getCfgStatisticsId());
            }else {
                CfgStatistics_Example example = ExampleUtils.parseExample( cfgStatistics, CfgStatistics_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgStatistics> list =  iCfgStatisticsSV.getCfgStatisticsListByExample(example);
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
    * 创建流量统计
    * @param cfgStatistics
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgStatistics") CfgStatistics cfgStatistics) {
        logger.debug("request : {}", cfgStatistics);
        try {
            ControllerHelper.setDefaultValue(cfgStatistics, "cfgStatisticsId");
            int result = iCfgStatisticsSV.create(cfgStatistics);
            if(result > 0) {
                return ResultData.success(cfgStatistics);
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
    * 批量维护流量统计
    * @param cfgStatisticss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgStatistics[] cfgStatisticss) {
        logger.debug("request : {}", cfgStatisticss);

        try {
            ControllerHelper.setDefaultValue(cfgStatisticss, "cfgStatisticsId");
            ControllerHelper.reorderProperty(cfgStatisticss);

            int result = iCfgStatisticsSV.batchOperate(cfgStatisticss);
            if(result > 0) {
                return ResultData.success(cfgStatisticss);
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
    * 更新流量统计
    * @param cfgStatistics
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgStatistics") CfgStatistics cfgStatistics) {
        logger.debug("request : {}", cfgStatistics);
        try {
            ControllerHelper.setDefaultValue(cfgStatistics, "cfgStatisticsId");
            int result = iCfgStatisticsSV.update(cfgStatistics);
            if(result > 0) {
                return ResultData.success(cfgStatistics);
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
    * 创建或更新流量统计
    * @param cfgStatistics
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgStatistics") CfgStatistics cfgStatistics) {
        logger.debug("request : {}", cfgStatistics);
        try {
            ControllerHelper.setDefaultValue(cfgStatistics, "cfgStatisticsId");
            int result = iCfgStatisticsSV.batchOperate(new CfgStatistics[]{cfgStatistics});
            if(result > 0) {
                return ResultData.success(cfgStatistics);
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
    * 删除流量统计
    * @param cfgStatistics
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgStatistics") CfgStatistics cfgStatistics) {
        logger.debug("request : {}", cfgStatistics);

        try {
            ControllerHelper.setDefaultValue(cfgStatistics, "cfgStatisticsId");
            int result = iCfgStatisticsSV.delete(cfgStatistics);
            if(result > 0) {
                return ResultData.success(cfgStatistics);
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
 	
	public ICfgStatisticsSV getICfgStatisticsSV(){
		return iCfgStatisticsSV;
	}
	//setter
	public void setICfgStatisticsSV(ICfgStatisticsSV iCfgStatisticsSV){
    	this.iCfgStatisticsSV = iCfgStatisticsSV;
    }
}
