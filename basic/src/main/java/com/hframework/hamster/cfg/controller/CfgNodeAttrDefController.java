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
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeAttrDefSV;

@Controller
@RequestMapping(value = "/cfg/cfgNodeAttrDef")
public class CfgNodeAttrDefController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgNodeAttrDefController.class);

	@Resource
	private ICfgNodeAttrDefSV iCfgNodeAttrDefSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示动态节点属性定义列表
     * @param cfgNodeAttrDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgNodeAttrDef") CfgNodeAttrDef cfgNodeAttrDef,
                                      @ModelAttribute("example") CfgNodeAttrDef_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgNodeAttrDef, example, pagination);
        try{
            ExampleUtils.parseExample(cfgNodeAttrDef, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgNodeAttrDef> list = iCfgNodeAttrDefSV.getCfgNodeAttrDefListByExample(example);
            pagination.setTotalCount(iCfgNodeAttrDefSV.getCfgNodeAttrDefCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示动态节点属性定义明细
     * @param cfgNodeAttrDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgNodeAttrDef") CfgNodeAttrDef cfgNodeAttrDef){
        logger.debug("request : {},{}", cfgNodeAttrDef.getId(), cfgNodeAttrDef);
        try{
            CfgNodeAttrDef result = null;
            if(cfgNodeAttrDef.getId() != null) {
                result = iCfgNodeAttrDefSV.getCfgNodeAttrDefByPK(cfgNodeAttrDef.getId());
            }else {
                CfgNodeAttrDef_Example example = ExampleUtils.parseExample(cfgNodeAttrDef, CfgNodeAttrDef_Example.class);
                List<CfgNodeAttrDef> list = iCfgNodeAttrDefSV.getCfgNodeAttrDefListByExample(example);
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
    * 搜索一个动态节点属性定义
    * @param  cfgNodeAttrDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgNodeAttrDef") CfgNodeAttrDef  cfgNodeAttrDef){
        logger.debug("request : {}",  cfgNodeAttrDef);
        try{
            CfgNodeAttrDef result = null;
            if(cfgNodeAttrDef.getId() != null) {
                result =  iCfgNodeAttrDefSV.getCfgNodeAttrDefByPK(cfgNodeAttrDef.getId());
            }else {
                CfgNodeAttrDef_Example example = ExampleUtils.parseExample( cfgNodeAttrDef, CfgNodeAttrDef_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgNodeAttrDef> list =  iCfgNodeAttrDefSV.getCfgNodeAttrDefListByExample(example);
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
    * 创建动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgNodeAttrDef") CfgNodeAttrDef cfgNodeAttrDef) {
        logger.debug("request : {}", cfgNodeAttrDef);
        try {
            ControllerHelper.setDefaultValue(cfgNodeAttrDef, "id");
            int result = iCfgNodeAttrDefSV.create(cfgNodeAttrDef);
            if(result > 0) {
                return ResultData.success(cfgNodeAttrDef);
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
    * 批量维护动态节点属性定义
    * @param cfgNodeAttrDefs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgNodeAttrDef[] cfgNodeAttrDefs) {
        logger.debug("request : {}", cfgNodeAttrDefs);

        try {
            ControllerHelper.setDefaultValue(cfgNodeAttrDefs, "id");
            ControllerHelper.reorderProperty(cfgNodeAttrDefs);

            int result = iCfgNodeAttrDefSV.batchOperate(cfgNodeAttrDefs);
            if(result > 0) {
                return ResultData.success(cfgNodeAttrDefs);
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
    * 更新动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgNodeAttrDef") CfgNodeAttrDef cfgNodeAttrDef) {
        logger.debug("request : {}", cfgNodeAttrDef);
        try {
            ControllerHelper.setDefaultValue(cfgNodeAttrDef, "id");
            int result = iCfgNodeAttrDefSV.update(cfgNodeAttrDef);
            if(result > 0) {
                return ResultData.success(cfgNodeAttrDef);
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
    * 创建或更新动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgNodeAttrDef") CfgNodeAttrDef cfgNodeAttrDef) {
        logger.debug("request : {}", cfgNodeAttrDef);
        try {
            ControllerHelper.setDefaultValue(cfgNodeAttrDef, "id");
            int result = iCfgNodeAttrDefSV.batchOperate(new CfgNodeAttrDef[]{cfgNodeAttrDef});
            if(result > 0) {
                return ResultData.success(cfgNodeAttrDef);
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
    * 删除动态节点属性定义
    * @param cfgNodeAttrDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgNodeAttrDef") CfgNodeAttrDef cfgNodeAttrDef) {
        logger.debug("request : {}", cfgNodeAttrDef);

        try {
            ControllerHelper.setDefaultValue(cfgNodeAttrDef, "id");
            int result = iCfgNodeAttrDefSV.delete(cfgNodeAttrDef);
            if(result > 0) {
                return ResultData.success(cfgNodeAttrDef);
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
 	
	public ICfgNodeAttrDefSV getICfgNodeAttrDefSV(){
		return iCfgNodeAttrDefSV;
	}
	//setter
	public void setICfgNodeAttrDefSV(ICfgNodeAttrDefSV iCfgNodeAttrDefSV){
    	this.iCfgNodeAttrDefSV = iCfgNodeAttrDefSV;
    }
}
