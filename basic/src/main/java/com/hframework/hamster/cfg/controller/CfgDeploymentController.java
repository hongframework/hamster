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
import com.hframework.hamster.cfg.domain.model.CfgDeployment;
import com.hframework.hamster.cfg.domain.model.CfgDeployment_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDeploymentSV;

@Controller
@RequestMapping(value = "/cfg/cfgDeployment")
public class CfgDeploymentController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgDeploymentController.class);

	@Resource
	private ICfgDeploymentSV iCfgDeploymentSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示发布列表
     * @param cfgDeployment
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgDeployment") CfgDeployment cfgDeployment,
                                      @ModelAttribute("example") CfgDeployment_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgDeployment, example, pagination);
        try{
            ExampleUtils.parseExample(cfgDeployment, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgDeployment> list = iCfgDeploymentSV.getCfgDeploymentListByExample(example);
            pagination.setTotalCount(iCfgDeploymentSV.getCfgDeploymentCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示发布明细
     * @param cfgDeployment
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgDeployment") CfgDeployment cfgDeployment){
        logger.debug("request : {},{}", cfgDeployment.getId(), cfgDeployment);
        try{
            CfgDeployment result = null;
            if(cfgDeployment.getId() != null) {
                result = iCfgDeploymentSV.getCfgDeploymentByPK(cfgDeployment.getId());
            }else {
                CfgDeployment_Example example = ExampleUtils.parseExample(cfgDeployment, CfgDeployment_Example.class);
                List<CfgDeployment> list = iCfgDeploymentSV.getCfgDeploymentListByExample(example);
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
    * 搜索一个发布
    * @param  cfgDeployment
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgDeployment") CfgDeployment  cfgDeployment){
        logger.debug("request : {}",  cfgDeployment);
        try{
            CfgDeployment result = null;
            if(cfgDeployment.getId() != null) {
                result =  iCfgDeploymentSV.getCfgDeploymentByPK(cfgDeployment.getId());
            }else {
                CfgDeployment_Example example = ExampleUtils.parseExample( cfgDeployment, CfgDeployment_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgDeployment> list =  iCfgDeploymentSV.getCfgDeploymentListByExample(example);
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
    * 创建发布
    * @param cfgDeployment
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgDeployment") CfgDeployment cfgDeployment) {
        logger.debug("request : {}", cfgDeployment);
        try {
            ControllerHelper.setDefaultValue(cfgDeployment, "id");
            int result = iCfgDeploymentSV.create(cfgDeployment);
            if(result > 0) {
                return ResultData.success(cfgDeployment);
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
    * 批量维护发布
    * @param cfgDeployments
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgDeployment[] cfgDeployments) {
        logger.debug("request : {}", cfgDeployments);

        try {
            ControllerHelper.setDefaultValue(cfgDeployments, "id");
            ControllerHelper.reorderProperty(cfgDeployments);

            int result = iCfgDeploymentSV.batchOperate(cfgDeployments);
            if(result > 0) {
                return ResultData.success(cfgDeployments);
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
    * 更新发布
    * @param cfgDeployment
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgDeployment") CfgDeployment cfgDeployment) {
        logger.debug("request : {}", cfgDeployment);
        try {
            ControllerHelper.setDefaultValue(cfgDeployment, "id");
            int result = iCfgDeploymentSV.update(cfgDeployment);
            if(result > 0) {
                return ResultData.success(cfgDeployment);
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
    * 创建或更新发布
    * @param cfgDeployment
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgDeployment") CfgDeployment cfgDeployment) {
        logger.debug("request : {}", cfgDeployment);
        try {
            ControllerHelper.setDefaultValue(cfgDeployment, "id");
            int result = iCfgDeploymentSV.batchOperate(new CfgDeployment[]{cfgDeployment});
            if(result > 0) {
                return ResultData.success(cfgDeployment);
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
    * 删除发布
    * @param cfgDeployment
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgDeployment") CfgDeployment cfgDeployment) {
        logger.debug("request : {}", cfgDeployment);

        try {
            ControllerHelper.setDefaultValue(cfgDeployment, "id");
            int result = iCfgDeploymentSV.delete(cfgDeployment);
            if(result > 0) {
                return ResultData.success(cfgDeployment);
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
 	
	public ICfgDeploymentSV getICfgDeploymentSV(){
		return iCfgDeploymentSV;
	}
	//setter
	public void setICfgDeploymentSV(ICfgDeploymentSV iCfgDeploymentSV){
    	this.iCfgDeploymentSV = iCfgDeploymentSV;
    }
}
