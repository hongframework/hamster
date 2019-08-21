package com.hframework.hamster.cfg.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.ExampleUtils;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobAttrSV;
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
import com.hframework.hamster.cfg.domain.model.CfgJobAttr;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobAttrSV;

@Controller
@RequestMapping(value = "/cfg/cfgJobAttr")
public class CfgJobAttrController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgJobAttrController.class);

	@Resource
	private ICfgJobAttrSV iCfgJobAttrSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务属性定义列表
     * @param cfgJobAttr
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgJobAttr") CfgJobAttr cfgJobAttr,
                           @ModelAttribute("example") CfgJobAttr_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgJobAttr, example, pagination);
        try{
            ExampleUtils.parseExample(cfgJobAttr, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgJobAttr> list = iCfgJobAttrSV.getCfgJobAttrListByExample(example);
            pagination.setTotalCount(iCfgJobAttrSV.getCfgJobAttrCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务属性定义明细
     * @param cfgJobAttr
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgJobAttr") CfgJobAttr cfgJobAttr){
        logger.debug("request : {},{}", cfgJobAttr.getId(), cfgJobAttr);
        try{
            CfgJobAttr result = null;
            if(cfgJobAttr.getId() != null) {
                result = iCfgJobAttrSV.getCfgJobAttrByPK(cfgJobAttr.getId());
            }else {
                CfgJobAttr_Example example = ExampleUtils.parseExample(cfgJobAttr, CfgJobAttr_Example.class);
                List<CfgJobAttr> list = iCfgJobAttrSV.getCfgJobAttrListByExample(example);
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
    * 搜索一个任务属性定义
    * @param  cfgJobAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgJobAttr") CfgJobAttr  cfgJobAttr){
        logger.debug("request : {}",  cfgJobAttr);
        try{
            CfgJobAttr result = null;
            if(cfgJobAttr.getId() != null) {
                result =  iCfgJobAttrSV.getCfgJobAttrByPK(cfgJobAttr.getId());
            }else {
                CfgJobAttr_Example example = ExampleUtils.parseExample( cfgJobAttr, CfgJobAttr_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgJobAttr> list =  iCfgJobAttrSV.getCfgJobAttrListByExample(example);
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
    * 创建任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgJobAttr") CfgJobAttr cfgJobAttr) {
        logger.debug("request : {}", cfgJobAttr);
        try {
            ControllerHelper.setDefaultValue(cfgJobAttr, "id");
            int result = iCfgJobAttrSV.create(cfgJobAttr);
            if(result > 0) {
                return ResultData.success(cfgJobAttr);
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
    * 批量维护任务属性定义
    * @param cfgJobAttrs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgJobAttr[] cfgJobAttrs) {
        logger.debug("request : {}", cfgJobAttrs);

        try {
            ControllerHelper.setDefaultValue(cfgJobAttrs, "id");
            ControllerHelper.reorderProperty(cfgJobAttrs);

            int result = iCfgJobAttrSV.batchOperate(cfgJobAttrs);
            if(result > 0) {
                return ResultData.success(cfgJobAttrs);
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
    * 更新任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgJobAttr") CfgJobAttr cfgJobAttr) {
        logger.debug("request : {}", cfgJobAttr);
        try {
            ControllerHelper.setDefaultValue(cfgJobAttr, "id");
            int result = iCfgJobAttrSV.update(cfgJobAttr);
            if(result > 0) {
                return ResultData.success(cfgJobAttr);
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
    * 创建或更新任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgJobAttr") CfgJobAttr cfgJobAttr) {
        logger.debug("request : {}", cfgJobAttr);
        try {
            ControllerHelper.setDefaultValue(cfgJobAttr, "id");
            int result = iCfgJobAttrSV.batchOperate(new CfgJobAttr[]{cfgJobAttr});
            if(result > 0) {
                return ResultData.success(cfgJobAttr);
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
    * 删除任务属性定义
    * @param cfgJobAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgJobAttr") CfgJobAttr cfgJobAttr) {
        logger.debug("request : {}", cfgJobAttr);

        try {
            ControllerHelper.setDefaultValue(cfgJobAttr, "id");
            int result = iCfgJobAttrSV.delete(cfgJobAttr);
            if(result > 0) {
                return ResultData.success(cfgJobAttr);
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
 	
	public ICfgJobAttrSV getICfgJobAttrSV(){
		return iCfgJobAttrSV;
	}
	//setter
	public void setICfgJobAttrSV(ICfgJobAttrSV iCfgJobAttrSV){
    	this.iCfgJobAttrSV = iCfgJobAttrSV;
    }
}
