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
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeDetailSV;

@Controller
@RequestMapping(value = "/cfg/cfgSubscribeDetail")
public class CfgSubscribeDetailController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgSubscribeDetailController.class);

	@Resource
	private ICfgSubscribeDetailSV iCfgSubscribeDetailSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示数据订阅明细列表
     * @param cfgSubscribeDetail
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgSubscribeDetail") CfgSubscribeDetail cfgSubscribeDetail,
                                      @ModelAttribute("example") CfgSubscribeDetail_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgSubscribeDetail, example, pagination);
        try{
            ExampleUtils.parseExample(cfgSubscribeDetail, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgSubscribeDetail> list = iCfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(example);
            pagination.setTotalCount(iCfgSubscribeDetailSV.getCfgSubscribeDetailCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示数据订阅明细明细
     * @param cfgSubscribeDetail
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgSubscribeDetail") CfgSubscribeDetail cfgSubscribeDetail){
        logger.debug("request : {},{}", cfgSubscribeDetail.getCfgSubscribeDetailId(), cfgSubscribeDetail);
        try{
            CfgSubscribeDetail result = null;
            if(cfgSubscribeDetail.getCfgSubscribeDetailId() != null) {
                result = iCfgSubscribeDetailSV.getCfgSubscribeDetailByPK(cfgSubscribeDetail.getCfgSubscribeDetailId());
            }else {
                CfgSubscribeDetail_Example example = ExampleUtils.parseExample(cfgSubscribeDetail, CfgSubscribeDetail_Example.class);
                List<CfgSubscribeDetail> list = iCfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(example);
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
    * 搜索一个数据订阅明细
    * @param  cfgSubscribeDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgSubscribeDetail") CfgSubscribeDetail  cfgSubscribeDetail){
        logger.debug("request : {}",  cfgSubscribeDetail);
        try{
            CfgSubscribeDetail result = null;
            if(cfgSubscribeDetail.getCfgSubscribeDetailId() != null) {
                result =  iCfgSubscribeDetailSV.getCfgSubscribeDetailByPK(cfgSubscribeDetail.getCfgSubscribeDetailId());
            }else {
                CfgSubscribeDetail_Example example = ExampleUtils.parseExample( cfgSubscribeDetail, CfgSubscribeDetail_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgSubscribeDetail> list =  iCfgSubscribeDetailSV.getCfgSubscribeDetailListByExample(example);
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
    * 创建数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgSubscribeDetail") CfgSubscribeDetail cfgSubscribeDetail) {
        logger.debug("request : {}", cfgSubscribeDetail);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribeDetail, "cfgSubscribeDetailId");
            int result = iCfgSubscribeDetailSV.create(cfgSubscribeDetail);
            if(result > 0) {
                return ResultData.success(cfgSubscribeDetail);
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
    * 批量维护数据订阅明细
    * @param cfgSubscribeDetails
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgSubscribeDetail[] cfgSubscribeDetails) {
        logger.debug("request : {}", cfgSubscribeDetails);

        try {
            ControllerHelper.setDefaultValue(cfgSubscribeDetails, "cfgSubscribeDetailId");
            ControllerHelper.reorderProperty(cfgSubscribeDetails);

            int result = iCfgSubscribeDetailSV.batchOperate(cfgSubscribeDetails);
            if(result > 0) {
                return ResultData.success(cfgSubscribeDetails);
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
    * 更新数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgSubscribeDetail") CfgSubscribeDetail cfgSubscribeDetail) {
        logger.debug("request : {}", cfgSubscribeDetail);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribeDetail, "cfgSubscribeDetailId");
            int result = iCfgSubscribeDetailSV.update(cfgSubscribeDetail);
            if(result > 0) {
                return ResultData.success(cfgSubscribeDetail);
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
    * 创建或更新数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgSubscribeDetail") CfgSubscribeDetail cfgSubscribeDetail) {
        logger.debug("request : {}", cfgSubscribeDetail);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribeDetail, "cfgSubscribeDetailId");
            int result = iCfgSubscribeDetailSV.batchOperate(new CfgSubscribeDetail[]{cfgSubscribeDetail});
            if(result > 0) {
                return ResultData.success(cfgSubscribeDetail);
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
    * 删除数据订阅明细
    * @param cfgSubscribeDetail
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgSubscribeDetail") CfgSubscribeDetail cfgSubscribeDetail) {
        logger.debug("request : {}", cfgSubscribeDetail);

        try {
            ControllerHelper.setDefaultValue(cfgSubscribeDetail, "cfgSubscribeDetailId");
            int result = iCfgSubscribeDetailSV.delete(cfgSubscribeDetail);
            if(result > 0) {
                return ResultData.success(cfgSubscribeDetail);
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
 	
	public ICfgSubscribeDetailSV getICfgSubscribeDetailSV(){
		return iCfgSubscribeDetailSV;
	}
	//setter
	public void setICfgSubscribeDetailSV(ICfgSubscribeDetailSV iCfgSubscribeDetailSV){
    	this.iCfgSubscribeDetailSV = iCfgSubscribeDetailSV;
    }
}
