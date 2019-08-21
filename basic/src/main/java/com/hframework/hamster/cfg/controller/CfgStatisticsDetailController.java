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
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgStatisticsDetailSV;

@Controller
@RequestMapping(value = "/cfg/cfgStatisticsDetail")
public class CfgStatisticsDetailController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgStatisticsDetailController.class);

	@Resource
	private ICfgStatisticsDetailSV iCfgStatisticsDetailSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示流量统计明细列表
     * @param cfgStatisticsDetail
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgStatisticsDetail") CfgStatisticsDetail cfgStatisticsDetail,
                                      @ModelAttribute("example") CfgStatisticsDetail_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgStatisticsDetail, example, pagination);
        try{
            ExampleUtils.parseExample(cfgStatisticsDetail, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgStatisticsDetail> list = iCfgStatisticsDetailSV.getCfgStatisticsDetailListByExample(example);
            pagination.setTotalCount(iCfgStatisticsDetailSV.getCfgStatisticsDetailCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示流量统计明细明细
     * @param cfgStatisticsDetail
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgStatisticsDetail") CfgStatisticsDetail cfgStatisticsDetail){
        logger.debug("request : {},{}", cfgStatisticsDetail.getCfgStatisticsDetailId(), cfgStatisticsDetail);
        try{
            CfgStatisticsDetail result = null;
            if(cfgStatisticsDetail.getCfgStatisticsDetailId() != null) {
                result = iCfgStatisticsDetailSV.getCfgStatisticsDetailByPK(cfgStatisticsDetail.getCfgStatisticsDetailId());
            }else {
                CfgStatisticsDetail_Example example = ExampleUtils.parseExample(cfgStatisticsDetail, CfgStatisticsDetail_Example.class);
                List<CfgStatisticsDetail> list = iCfgStatisticsDetailSV.getCfgStatisticsDetailListByExample(example);
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
    * 搜索一个流量统计明细
    * @param  cfgStatisticsDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgStatisticsDetail") CfgStatisticsDetail  cfgStatisticsDetail){
        logger.debug("request : {}",  cfgStatisticsDetail);
        try{
            CfgStatisticsDetail result = null;
            if(cfgStatisticsDetail.getCfgStatisticsDetailId() != null) {
                result =  iCfgStatisticsDetailSV.getCfgStatisticsDetailByPK(cfgStatisticsDetail.getCfgStatisticsDetailId());
            }else {
                CfgStatisticsDetail_Example example = ExampleUtils.parseExample( cfgStatisticsDetail, CfgStatisticsDetail_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgStatisticsDetail> list =  iCfgStatisticsDetailSV.getCfgStatisticsDetailListByExample(example);
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
    * 创建流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgStatisticsDetail") CfgStatisticsDetail cfgStatisticsDetail) {
        logger.debug("request : {}", cfgStatisticsDetail);
        try {
            ControllerHelper.setDefaultValue(cfgStatisticsDetail, "cfgStatisticsDetailId");
            int result = iCfgStatisticsDetailSV.create(cfgStatisticsDetail);
            if(result > 0) {
                return ResultData.success(cfgStatisticsDetail);
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
    * 批量维护流量统计明细
    * @param cfgStatisticsDetails
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgStatisticsDetail[] cfgStatisticsDetails) {
        logger.debug("request : {}", cfgStatisticsDetails);

        try {
            ControllerHelper.setDefaultValue(cfgStatisticsDetails, "cfgStatisticsDetailId");
            ControllerHelper.reorderProperty(cfgStatisticsDetails);

            int result = iCfgStatisticsDetailSV.batchOperate(cfgStatisticsDetails);
            if(result > 0) {
                return ResultData.success(cfgStatisticsDetails);
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
    * 更新流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgStatisticsDetail") CfgStatisticsDetail cfgStatisticsDetail) {
        logger.debug("request : {}", cfgStatisticsDetail);
        try {
            ControllerHelper.setDefaultValue(cfgStatisticsDetail, "cfgStatisticsDetailId");
            int result = iCfgStatisticsDetailSV.update(cfgStatisticsDetail);
            if(result > 0) {
                return ResultData.success(cfgStatisticsDetail);
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
    * 创建或更新流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgStatisticsDetail") CfgStatisticsDetail cfgStatisticsDetail) {
        logger.debug("request : {}", cfgStatisticsDetail);
        try {
            ControllerHelper.setDefaultValue(cfgStatisticsDetail, "cfgStatisticsDetailId");
            int result = iCfgStatisticsDetailSV.batchOperate(new CfgStatisticsDetail[]{cfgStatisticsDetail});
            if(result > 0) {
                return ResultData.success(cfgStatisticsDetail);
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
    * 删除流量统计明细
    * @param cfgStatisticsDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgStatisticsDetail") CfgStatisticsDetail cfgStatisticsDetail) {
        logger.debug("request : {}", cfgStatisticsDetail);

        try {
            ControllerHelper.setDefaultValue(cfgStatisticsDetail, "cfgStatisticsDetailId");
            int result = iCfgStatisticsDetailSV.delete(cfgStatisticsDetail);
            if(result > 0) {
                return ResultData.success(cfgStatisticsDetail);
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
 	
	public ICfgStatisticsDetailSV getICfgStatisticsDetailSV(){
		return iCfgStatisticsDetailSV;
	}
	//setter
	public void setICfgStatisticsDetailSV(ICfgStatisticsDetailSV iCfgStatisticsDetailSV){
    	this.iCfgStatisticsDetailSV = iCfgStatisticsDetailSV;
    }
}
