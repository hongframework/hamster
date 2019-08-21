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
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import com.hframework.web.ControllerHelper;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDeploymentDetailSV;

@Controller
@RequestMapping(value = "/cfg/cfgDeploymentDetail")
public class CfgDeploymentDetailController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgDeploymentDetailController.class);

	@Resource
	private ICfgDeploymentDetailSV iCfgDeploymentDetailSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示发布明细列表
     * @param cfgDeploymentDetail
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgDeploymentDetail") CfgDeploymentDetail cfgDeploymentDetail,
                                      @ModelAttribute("example") CfgDeploymentDetail_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgDeploymentDetail, example, pagination);
        try{
            ExampleUtils.parseExample(cfgDeploymentDetail, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgDeploymentDetail> list = iCfgDeploymentDetailSV.getCfgDeploymentDetailListByExample(example);
            pagination.setTotalCount(iCfgDeploymentDetailSV.getCfgDeploymentDetailCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示发布明细明细
     * @param cfgDeploymentDetail
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgDeploymentDetail") CfgDeploymentDetail cfgDeploymentDetail){
        logger.debug("request : {},{}", cfgDeploymentDetail.getId(), cfgDeploymentDetail);
        try{
            CfgDeploymentDetail result = null;
            if(cfgDeploymentDetail.getId() != null) {
                result = iCfgDeploymentDetailSV.getCfgDeploymentDetailByPK(cfgDeploymentDetail.getId());
            }else {
                CfgDeploymentDetail_Example example = ExampleUtils.parseExample(cfgDeploymentDetail, CfgDeploymentDetail_Example.class);
                List<CfgDeploymentDetail> list = iCfgDeploymentDetailSV.getCfgDeploymentDetailListByExample(example);
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
    * 搜索一个发布明细
    * @param  cfgDeploymentDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgDeploymentDetail") CfgDeploymentDetail  cfgDeploymentDetail){
        logger.debug("request : {}",  cfgDeploymentDetail);
        try{
            CfgDeploymentDetail result = null;
            if(cfgDeploymentDetail.getId() != null) {
                result =  iCfgDeploymentDetailSV.getCfgDeploymentDetailByPK(cfgDeploymentDetail.getId());
            }else {
                CfgDeploymentDetail_Example example = ExampleUtils.parseExample( cfgDeploymentDetail, CfgDeploymentDetail_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgDeploymentDetail> list =  iCfgDeploymentDetailSV.getCfgDeploymentDetailListByExample(example);
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
    * 创建发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgDeploymentDetail") CfgDeploymentDetail cfgDeploymentDetail) {
        logger.debug("request : {}", cfgDeploymentDetail);
        try {
            ControllerHelper.setDefaultValue(cfgDeploymentDetail, "id");
            int result = iCfgDeploymentDetailSV.create(cfgDeploymentDetail);
            if(result > 0) {
                return ResultData.success(cfgDeploymentDetail);
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
    * 批量维护发布明细
    * @param cfgDeploymentDetails
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgDeploymentDetail[] cfgDeploymentDetails) {
        logger.debug("request : {}", cfgDeploymentDetails);

        try {
            ControllerHelper.setDefaultValue(cfgDeploymentDetails, "id");
            ControllerHelper.reorderProperty(cfgDeploymentDetails);

            int result = iCfgDeploymentDetailSV.batchOperate(cfgDeploymentDetails);
            if(result > 0) {
                return ResultData.success(cfgDeploymentDetails);
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
    * 更新发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgDeploymentDetail") CfgDeploymentDetail cfgDeploymentDetail) {
        logger.debug("request : {}", cfgDeploymentDetail);
        try {
            ControllerHelper.setDefaultValue(cfgDeploymentDetail, "id");
            int result = iCfgDeploymentDetailSV.update(cfgDeploymentDetail);
            if(result > 0) {
                return ResultData.success(cfgDeploymentDetail);
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
    * 创建或更新发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgDeploymentDetail") CfgDeploymentDetail cfgDeploymentDetail) {
        logger.debug("request : {}", cfgDeploymentDetail);
        try {
            ControllerHelper.setDefaultValue(cfgDeploymentDetail, "id");
            int result = iCfgDeploymentDetailSV.batchOperate(new CfgDeploymentDetail[]{cfgDeploymentDetail});
            if(result > 0) {
                return ResultData.success(cfgDeploymentDetail);
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
    * 删除发布明细
    * @param cfgDeploymentDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgDeploymentDetail") CfgDeploymentDetail cfgDeploymentDetail) {
        logger.debug("request : {}", cfgDeploymentDetail);

        try {
            ControllerHelper.setDefaultValue(cfgDeploymentDetail, "id");
            int result = iCfgDeploymentDetailSV.delete(cfgDeploymentDetail);
            if(result > 0) {
                return ResultData.success(cfgDeploymentDetail);
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
 	
	public ICfgDeploymentDetailSV getICfgDeploymentDetailSV(){
		return iCfgDeploymentDetailSV;
	}
	//setter
	public void setICfgDeploymentDetailSV(ICfgDeploymentDetailSV iCfgDeploymentDetailSV){
    	this.iCfgDeploymentDetailSV = iCfgDeploymentDetailSV;
    }
}
