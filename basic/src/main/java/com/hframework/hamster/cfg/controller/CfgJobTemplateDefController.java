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
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateDefSV;

@Controller
@RequestMapping(value = "/cfg/cfgJobTemplateDef")
public class CfgJobTemplateDefController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgJobTemplateDefController.class);

	@Resource
	private ICfgJobTemplateDefSV iCfgJobTemplateDefSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务模板定义列表
     * @param cfgJobTemplateDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgJobTemplateDef") CfgJobTemplateDef cfgJobTemplateDef,
                                      @ModelAttribute("example") CfgJobTemplateDef_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgJobTemplateDef, example, pagination);
        try{
            ExampleUtils.parseExample(cfgJobTemplateDef, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgJobTemplateDef> list = iCfgJobTemplateDefSV.getCfgJobTemplateDefListByExample(example);
            pagination.setTotalCount(iCfgJobTemplateDefSV.getCfgJobTemplateDefCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务模板定义明细
     * @param cfgJobTemplateDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgJobTemplateDef") CfgJobTemplateDef cfgJobTemplateDef){
        logger.debug("request : {},{}", cfgJobTemplateDef.getId(), cfgJobTemplateDef);
        try{
            CfgJobTemplateDef result = null;
            if(cfgJobTemplateDef.getId() != null) {
                result = iCfgJobTemplateDefSV.getCfgJobTemplateDefByPK(cfgJobTemplateDef.getId());
            }else {
                CfgJobTemplateDef_Example example = ExampleUtils.parseExample(cfgJobTemplateDef, CfgJobTemplateDef_Example.class);
                List<CfgJobTemplateDef> list = iCfgJobTemplateDefSV.getCfgJobTemplateDefListByExample(example);
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
    * 搜索一个任务模板定义
    * @param  cfgJobTemplateDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgJobTemplateDef") CfgJobTemplateDef  cfgJobTemplateDef){
        logger.debug("request : {}",  cfgJobTemplateDef);
        try{
            CfgJobTemplateDef result = null;
            if(cfgJobTemplateDef.getId() != null) {
                result =  iCfgJobTemplateDefSV.getCfgJobTemplateDefByPK(cfgJobTemplateDef.getId());
            }else {
                CfgJobTemplateDef_Example example = ExampleUtils.parseExample( cfgJobTemplateDef, CfgJobTemplateDef_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgJobTemplateDef> list =  iCfgJobTemplateDefSV.getCfgJobTemplateDefListByExample(example);
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
    * 创建任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgJobTemplateDef") CfgJobTemplateDef cfgJobTemplateDef) {
        logger.debug("request : {}", cfgJobTemplateDef);
        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateDef, "id");
            int result = iCfgJobTemplateDefSV.create(cfgJobTemplateDef);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateDef);
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
    * 批量维护任务模板定义
    * @param cfgJobTemplateDefs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgJobTemplateDef[] cfgJobTemplateDefs) {
        logger.debug("request : {}", cfgJobTemplateDefs);

        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateDefs, "id");
            ControllerHelper.reorderProperty(cfgJobTemplateDefs);

            int result = iCfgJobTemplateDefSV.batchOperate(cfgJobTemplateDefs);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateDefs);
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
    * 更新任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgJobTemplateDef") CfgJobTemplateDef cfgJobTemplateDef) {
        logger.debug("request : {}", cfgJobTemplateDef);
        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateDef, "id");
            int result = iCfgJobTemplateDefSV.update(cfgJobTemplateDef);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateDef);
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
    * 创建或更新任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgJobTemplateDef") CfgJobTemplateDef cfgJobTemplateDef) {
        logger.debug("request : {}", cfgJobTemplateDef);
        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateDef, "id");
            int result = iCfgJobTemplateDefSV.batchOperate(new CfgJobTemplateDef[]{cfgJobTemplateDef});
            if(result > 0) {
                return ResultData.success(cfgJobTemplateDef);
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
    * 删除任务模板定义
    * @param cfgJobTemplateDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgJobTemplateDef") CfgJobTemplateDef cfgJobTemplateDef) {
        logger.debug("request : {}", cfgJobTemplateDef);

        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateDef, "id");
            int result = iCfgJobTemplateDefSV.delete(cfgJobTemplateDef);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateDef);
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
 	
	public ICfgJobTemplateDefSV getICfgJobTemplateDefSV(){
		return iCfgJobTemplateDefSV;
	}
	//setter
	public void setICfgJobTemplateDefSV(ICfgJobTemplateDefSV iCfgJobTemplateDefSV){
    	this.iCfgJobTemplateDefSV = iCfgJobTemplateDefSV;
    }
}
