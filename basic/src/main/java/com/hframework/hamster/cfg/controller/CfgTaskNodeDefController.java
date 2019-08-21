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
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskNodeDefSV;

@Controller
@RequestMapping(value = "/cfg/cfgTaskNodeDef")
public class CfgTaskNodeDefController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgTaskNodeDefController.class);

	@Resource
	private ICfgTaskNodeDefSV iCfgTaskNodeDefSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务子节点定义列表
     * @param cfgTaskNodeDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgTaskNodeDef") CfgTaskNodeDef cfgTaskNodeDef,
                                      @ModelAttribute("example") CfgTaskNodeDef_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgTaskNodeDef, example, pagination);
        try{
            ExampleUtils.parseExample(cfgTaskNodeDef, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgTaskNodeDef> list = iCfgTaskNodeDefSV.getCfgTaskNodeDefListByExample(example);
            pagination.setTotalCount(iCfgTaskNodeDefSV.getCfgTaskNodeDefCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务子节点定义明细
     * @param cfgTaskNodeDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgTaskNodeDef") CfgTaskNodeDef cfgTaskNodeDef){
        logger.debug("request : {},{}", cfgTaskNodeDef.getCfgTaskNodeDefId(), cfgTaskNodeDef);
        try{
            CfgTaskNodeDef result = null;
            if(cfgTaskNodeDef.getCfgTaskNodeDefId() != null) {
                result = iCfgTaskNodeDefSV.getCfgTaskNodeDefByPK(cfgTaskNodeDef.getCfgTaskNodeDefId());
            }else {
                CfgTaskNodeDef_Example example = ExampleUtils.parseExample(cfgTaskNodeDef, CfgTaskNodeDef_Example.class);
                List<CfgTaskNodeDef> list = iCfgTaskNodeDefSV.getCfgTaskNodeDefListByExample(example);
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
    * 搜索一个任务子节点定义
    * @param  cfgTaskNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgTaskNodeDef") CfgTaskNodeDef  cfgTaskNodeDef){
        logger.debug("request : {}",  cfgTaskNodeDef);
        try{
            CfgTaskNodeDef result = null;
            if(cfgTaskNodeDef.getCfgTaskNodeDefId() != null) {
                result =  iCfgTaskNodeDefSV.getCfgTaskNodeDefByPK(cfgTaskNodeDef.getCfgTaskNodeDefId());
            }else {
                CfgTaskNodeDef_Example example = ExampleUtils.parseExample( cfgTaskNodeDef, CfgTaskNodeDef_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgTaskNodeDef> list =  iCfgTaskNodeDefSV.getCfgTaskNodeDefListByExample(example);
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
    * 创建任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgTaskNodeDef") CfgTaskNodeDef cfgTaskNodeDef) {
        logger.debug("request : {}", cfgTaskNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgTaskNodeDef, "cfgTaskNodeDefId");
            int result = iCfgTaskNodeDefSV.create(cfgTaskNodeDef);
            if(result > 0) {
                return ResultData.success(cfgTaskNodeDef);
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
    * 批量维护任务子节点定义
    * @param cfgTaskNodeDefs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgTaskNodeDef[] cfgTaskNodeDefs) {
        logger.debug("request : {}", cfgTaskNodeDefs);

        try {
            ControllerHelper.setDefaultValue(cfgTaskNodeDefs, "cfgTaskNodeDefId");
            ControllerHelper.reorderProperty(cfgTaskNodeDefs);

            int result = iCfgTaskNodeDefSV.batchOperate(cfgTaskNodeDefs);
            if(result > 0) {
                return ResultData.success(cfgTaskNodeDefs);
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
    * 更新任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgTaskNodeDef") CfgTaskNodeDef cfgTaskNodeDef) {
        logger.debug("request : {}", cfgTaskNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgTaskNodeDef, "cfgTaskNodeDefId");
            int result = iCfgTaskNodeDefSV.update(cfgTaskNodeDef);
            if(result > 0) {
                return ResultData.success(cfgTaskNodeDef);
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
    * 创建或更新任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgTaskNodeDef") CfgTaskNodeDef cfgTaskNodeDef) {
        logger.debug("request : {}", cfgTaskNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgTaskNodeDef, "cfgTaskNodeDefId");
            int result = iCfgTaskNodeDefSV.batchOperate(new CfgTaskNodeDef[]{cfgTaskNodeDef});
            if(result > 0) {
                return ResultData.success(cfgTaskNodeDef);
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
    * 删除任务子节点定义
    * @param cfgTaskNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgTaskNodeDef") CfgTaskNodeDef cfgTaskNodeDef) {
        logger.debug("request : {}", cfgTaskNodeDef);

        try {
            ControllerHelper.setDefaultValue(cfgTaskNodeDef, "cfgTaskNodeDefId");
            int result = iCfgTaskNodeDefSV.delete(cfgTaskNodeDef);
            if(result > 0) {
                return ResultData.success(cfgTaskNodeDef);
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
 	
	public ICfgTaskNodeDefSV getICfgTaskNodeDefSV(){
		return iCfgTaskNodeDefSV;
	}
	//setter
	public void setICfgTaskNodeDefSV(ICfgTaskNodeDefSV iCfgTaskNodeDefSV){
    	this.iCfgTaskNodeDefSV = iCfgTaskNodeDefSV;
    }
}
