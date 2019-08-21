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
import com.hframework.hamster.cfg.domain.model.CfgTaskDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskDef_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskDefSV;

@Controller
@RequestMapping(value = "/cfg/cfgTaskDef")
public class CfgTaskDefController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgTaskDefController.class);

	@Resource
	private ICfgTaskDefSV iCfgTaskDefSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务定义列表
     * @param cfgTaskDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgTaskDef") CfgTaskDef cfgTaskDef,
                                      @ModelAttribute("example") CfgTaskDef_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgTaskDef, example, pagination);
        try{
            ExampleUtils.parseExample(cfgTaskDef, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgTaskDef> list = iCfgTaskDefSV.getCfgTaskDefListByExample(example);
            pagination.setTotalCount(iCfgTaskDefSV.getCfgTaskDefCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务定义明细
     * @param cfgTaskDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgTaskDef") CfgTaskDef cfgTaskDef){
        logger.debug("request : {},{}", cfgTaskDef.getCfgTaskDefId(), cfgTaskDef);
        try{
            CfgTaskDef result = null;
            if(cfgTaskDef.getCfgTaskDefId() != null) {
                result = iCfgTaskDefSV.getCfgTaskDefByPK(cfgTaskDef.getCfgTaskDefId());
            }else {
                CfgTaskDef_Example example = ExampleUtils.parseExample(cfgTaskDef, CfgTaskDef_Example.class);
                List<CfgTaskDef> list = iCfgTaskDefSV.getCfgTaskDefListByExample(example);
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
    * 搜索一个任务定义
    * @param  cfgTaskDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgTaskDef") CfgTaskDef  cfgTaskDef){
        logger.debug("request : {}",  cfgTaskDef);
        try{
            CfgTaskDef result = null;
            if(cfgTaskDef.getCfgTaskDefId() != null) {
                result =  iCfgTaskDefSV.getCfgTaskDefByPK(cfgTaskDef.getCfgTaskDefId());
            }else {
                CfgTaskDef_Example example = ExampleUtils.parseExample( cfgTaskDef, CfgTaskDef_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgTaskDef> list =  iCfgTaskDefSV.getCfgTaskDefListByExample(example);
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
    * 创建任务定义
    * @param cfgTaskDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgTaskDef") CfgTaskDef cfgTaskDef) {
        logger.debug("request : {}", cfgTaskDef);
        try {
            ControllerHelper.setDefaultValue(cfgTaskDef, "cfgTaskDefId");
            int result = iCfgTaskDefSV.create(cfgTaskDef);
            if(result > 0) {
                return ResultData.success(cfgTaskDef);
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
    * 批量维护任务定义
    * @param cfgTaskDefs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgTaskDef[] cfgTaskDefs) {
        logger.debug("request : {}", cfgTaskDefs);

        try {
            ControllerHelper.setDefaultValue(cfgTaskDefs, "cfgTaskDefId");
            ControllerHelper.reorderProperty(cfgTaskDefs);

            int result = iCfgTaskDefSV.batchOperate(cfgTaskDefs);
            if(result > 0) {
                return ResultData.success(cfgTaskDefs);
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
    * 更新任务定义
    * @param cfgTaskDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgTaskDef") CfgTaskDef cfgTaskDef) {
        logger.debug("request : {}", cfgTaskDef);
        try {
            ControllerHelper.setDefaultValue(cfgTaskDef, "cfgTaskDefId");
            int result = iCfgTaskDefSV.update(cfgTaskDef);
            if(result > 0) {
                return ResultData.success(cfgTaskDef);
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
    * 创建或更新任务定义
    * @param cfgTaskDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgTaskDef") CfgTaskDef cfgTaskDef) {
        logger.debug("request : {}", cfgTaskDef);
        try {
            ControllerHelper.setDefaultValue(cfgTaskDef, "cfgTaskDefId");
            int result = iCfgTaskDefSV.batchOperate(new CfgTaskDef[]{cfgTaskDef});
            if(result > 0) {
                return ResultData.success(cfgTaskDef);
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
    * 删除任务定义
    * @param cfgTaskDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgTaskDef") CfgTaskDef cfgTaskDef) {
        logger.debug("request : {}", cfgTaskDef);

        try {
            ControllerHelper.setDefaultValue(cfgTaskDef, "cfgTaskDefId");
            int result = iCfgTaskDefSV.delete(cfgTaskDef);
            if(result > 0) {
                return ResultData.success(cfgTaskDef);
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
 	
	public ICfgTaskDefSV getICfgTaskDefSV(){
		return iCfgTaskDefSV;
	}
	//setter
	public void setICfgTaskDefSV(ICfgTaskDefSV iCfgTaskDefSV){
    	this.iCfgTaskDefSV = iCfgTaskDefSV;
    }
}
