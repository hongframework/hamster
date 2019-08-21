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
import com.hframework.hamster.cfg.domain.model.CfgNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeDefSV;

@Controller
@RequestMapping(value = "/cfg/cfgNodeDef")
public class CfgNodeDefController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgNodeDefController.class);

	@Resource
	private ICfgNodeDefSV iCfgNodeDefSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示动态节点定义列表
     * @param cfgNodeDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgNodeDef") CfgNodeDef cfgNodeDef,
                                      @ModelAttribute("example") CfgNodeDef_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgNodeDef, example, pagination);
        try{
            ExampleUtils.parseExample(cfgNodeDef, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgNodeDef> list = iCfgNodeDefSV.getCfgNodeDefListByExample(example);
            pagination.setTotalCount(iCfgNodeDefSV.getCfgNodeDefCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示动态节点定义明细
     * @param cfgNodeDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgNodeDef") CfgNodeDef cfgNodeDef){
        logger.debug("request : {},{}", cfgNodeDef.getId(), cfgNodeDef);
        try{
            CfgNodeDef result = null;
            if(cfgNodeDef.getId() != null) {
                result = iCfgNodeDefSV.getCfgNodeDefByPK(cfgNodeDef.getId());
            }else {
                CfgNodeDef_Example example = ExampleUtils.parseExample(cfgNodeDef, CfgNodeDef_Example.class);
                List<CfgNodeDef> list = iCfgNodeDefSV.getCfgNodeDefListByExample(example);
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
    * 搜索一个动态节点定义
    * @param  cfgNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgNodeDef") CfgNodeDef  cfgNodeDef){
        logger.debug("request : {}",  cfgNodeDef);
        try{
            CfgNodeDef result = null;
            if(cfgNodeDef.getId() != null) {
                result =  iCfgNodeDefSV.getCfgNodeDefByPK(cfgNodeDef.getId());
            }else {
                CfgNodeDef_Example example = ExampleUtils.parseExample( cfgNodeDef, CfgNodeDef_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgNodeDef> list =  iCfgNodeDefSV.getCfgNodeDefListByExample(example);
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
    * 创建动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgNodeDef") CfgNodeDef cfgNodeDef) {
        logger.debug("request : {}", cfgNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgNodeDef, "id");
            int result = iCfgNodeDefSV.create(cfgNodeDef);
            if(result > 0) {
                return ResultData.success(cfgNodeDef);
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
    * 批量维护动态节点定义
    * @param cfgNodeDefs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgNodeDef[] cfgNodeDefs) {
        logger.debug("request : {}", cfgNodeDefs);

        try {
            ControllerHelper.setDefaultValue(cfgNodeDefs, "id");
            ControllerHelper.reorderProperty(cfgNodeDefs);

            int result = iCfgNodeDefSV.batchOperate(cfgNodeDefs);
            if(result > 0) {
                return ResultData.success(cfgNodeDefs);
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
    * 更新动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgNodeDef") CfgNodeDef cfgNodeDef) {
        logger.debug("request : {}", cfgNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgNodeDef, "id");
            int result = iCfgNodeDefSV.update(cfgNodeDef);
            if(result > 0) {
                return ResultData.success(cfgNodeDef);
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
    * 创建或更新动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgNodeDef") CfgNodeDef cfgNodeDef) {
        logger.debug("request : {}", cfgNodeDef);
        try {
            ControllerHelper.setDefaultValue(cfgNodeDef, "id");
            int result = iCfgNodeDefSV.batchOperate(new CfgNodeDef[]{cfgNodeDef});
            if(result > 0) {
                return ResultData.success(cfgNodeDef);
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
    * 删除动态节点定义
    * @param cfgNodeDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgNodeDef") CfgNodeDef cfgNodeDef) {
        logger.debug("request : {}", cfgNodeDef);

        try {
            ControllerHelper.setDefaultValue(cfgNodeDef, "id");
            int result = iCfgNodeDefSV.delete(cfgNodeDef);
            if(result > 0) {
                return ResultData.success(cfgNodeDef);
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
 	
	public ICfgNodeDefSV getICfgNodeDefSV(){
		return iCfgNodeDefSV;
	}
	//setter
	public void setICfgNodeDefSV(ICfgNodeDefSV iCfgNodeDefSV){
    	this.iCfgNodeDefSV = iCfgNodeDefSV;
    }
}
