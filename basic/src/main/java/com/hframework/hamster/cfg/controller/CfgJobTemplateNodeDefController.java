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
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateNodeDefSV;

@Controller
@RequestMapping(value = "/cfg/cfgJobTemplateNodeDef")
public class CfgJobTemplateNodeDefController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgJobTemplateNodeDefController.class);

	@Resource
	private ICfgJobTemplateNodeDefSV iCfgJobTemplateNodeDefSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务模板节点定义列表
     * @param cfgJobTemplateNodeDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgJobTemplateNodeDef") CfgJobTemplateNodeDef cfgJobTemplateNodeDef,
                                      @ModelAttribute("example") CfgJobTemplateNodeDef_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgJobTemplateNodeDef, example, pagination);
        try{
            ExampleUtils.parseExample(cfgJobTemplateNodeDef, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgJobTemplateNodeDef> list = iCfgJobTemplateNodeDefSV.getCfgJobTemplateNodeDefListByExample(example);
            pagination.setTotalCount(iCfgJobTemplateNodeDefSV.getCfgJobTemplateNodeDefCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务模板节点定义明细
     * @param cfgJobTemplateNodeDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgJobTemplateNodeDef") CfgJobTemplateNodeDef cfgJobTemplateNodeDef){
        logger.debug("request : {},{}", cfgJobTemplateNodeDef.getId(), cfgJobTemplateNodeDef);
        try{
            CfgJobTemplateNodeDef result = null;
            if(cfgJobTemplateNodeDef.getId() != null) {
                result = iCfgJobTemplateNodeDefSV.getCfgJobTemplateNodeDefByPK(cfgJobTemplateNodeDef.getId());
            }else {
                CfgJobTemplateNodeDef_Example example = ExampleUtils.parseExample(cfgJobTemplateNodeDef, CfgJobTemplateNodeDef_Example.class);
                List<CfgJobTemplateNodeDef> list = iCfgJobTemplateNodeDefSV.getCfgJobTemplateNodeDefListByExample(example);
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
    * 搜索一个任务模板节点定义
    * @param  cfgJobTemplateNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgJobTemplateNodeDef") CfgJobTemplateNodeDef  cfgJobTemplateNodeDef){
        logger.debug("request : {}",  cfgJobTemplateNodeDef);
        try{
            CfgJobTemplateNodeDef result = null;
            if(cfgJobTemplateNodeDef.getId() != null) {
                result =  iCfgJobTemplateNodeDefSV.getCfgJobTemplateNodeDefByPK(cfgJobTemplateNodeDef.getId());
            }else {
                CfgJobTemplateNodeDef_Example example = ExampleUtils.parseExample( cfgJobTemplateNodeDef, CfgJobTemplateNodeDef_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgJobTemplateNodeDef> list =  iCfgJobTemplateNodeDefSV.getCfgJobTemplateNodeDefListByExample(example);
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
    * 创建任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgJobTemplateNodeDef") CfgJobTemplateNodeDef cfgJobTemplateNodeDef) {
        logger.debug("request : {}", cfgJobTemplateNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateNodeDef, "id");
            int result = iCfgJobTemplateNodeDefSV.create(cfgJobTemplateNodeDef);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateNodeDef);
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
    * 批量维护任务模板节点定义
    * @param cfgJobTemplateNodeDefs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgJobTemplateNodeDef[] cfgJobTemplateNodeDefs) {
        logger.debug("request : {}", cfgJobTemplateNodeDefs);

        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateNodeDefs, "id");
            ControllerHelper.reorderProperty(cfgJobTemplateNodeDefs);

            int result = iCfgJobTemplateNodeDefSV.batchOperate(cfgJobTemplateNodeDefs);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateNodeDefs);
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
    * 更新任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgJobTemplateNodeDef") CfgJobTemplateNodeDef cfgJobTemplateNodeDef) {
        logger.debug("request : {}", cfgJobTemplateNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateNodeDef, "id");
            int result = iCfgJobTemplateNodeDefSV.update(cfgJobTemplateNodeDef);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateNodeDef);
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
    * 创建或更新任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgJobTemplateNodeDef") CfgJobTemplateNodeDef cfgJobTemplateNodeDef) {
        logger.debug("request : {}", cfgJobTemplateNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateNodeDef, "id");
            int result = iCfgJobTemplateNodeDefSV.batchOperate(new CfgJobTemplateNodeDef[]{cfgJobTemplateNodeDef});
            if(result > 0) {
                return ResultData.success(cfgJobTemplateNodeDef);
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
    * 删除任务模板节点定义
    * @param cfgJobTemplateNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgJobTemplateNodeDef") CfgJobTemplateNodeDef cfgJobTemplateNodeDef) {
        logger.debug("request : {}", cfgJobTemplateNodeDef);

        try {
            ControllerHelper.setDefaultValue(cfgJobTemplateNodeDef, "id");
            int result = iCfgJobTemplateNodeDefSV.delete(cfgJobTemplateNodeDef);
            if(result > 0) {
                return ResultData.success(cfgJobTemplateNodeDef);
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
 	
	public ICfgJobTemplateNodeDefSV getICfgJobTemplateNodeDefSV(){
		return iCfgJobTemplateNodeDefSV;
	}
	//setter
	public void setICfgJobTemplateNodeDefSV(ICfgJobTemplateNodeDefSV iCfgJobTemplateNodeDefSV){
    	this.iCfgJobTemplateNodeDefSV = iCfgJobTemplateNodeDefSV;
    }
}
