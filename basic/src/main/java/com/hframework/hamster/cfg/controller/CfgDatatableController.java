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
import com.hframework.hamster.cfg.domain.model.CfgDatatable;
import com.hframework.hamster.cfg.domain.model.CfgDatatable_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatatableSV;

@Controller
@RequestMapping(value = "/cfg/cfgDatatable")
public class CfgDatatableController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgDatatableController.class);

	@Resource
	private ICfgDatatableSV iCfgDatatableSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示数据表列表
     * @param cfgDatatable
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgDatatable") CfgDatatable cfgDatatable,
                                      @ModelAttribute("example") CfgDatatable_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgDatatable, example, pagination);
        try{
            ExampleUtils.parseExample(cfgDatatable, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgDatatable> list = iCfgDatatableSV.getCfgDatatableListByExample(example);
            pagination.setTotalCount(iCfgDatatableSV.getCfgDatatableCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示数据表明细
     * @param cfgDatatable
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgDatatable") CfgDatatable cfgDatatable){
        logger.debug("request : {},{}", cfgDatatable.getId(), cfgDatatable);
        try{
            CfgDatatable result = null;
            if(cfgDatatable.getId() != null) {
                result = iCfgDatatableSV.getCfgDatatableByPK(cfgDatatable.getId());
            }else {
                CfgDatatable_Example example = ExampleUtils.parseExample(cfgDatatable, CfgDatatable_Example.class);
                List<CfgDatatable> list = iCfgDatatableSV.getCfgDatatableListByExample(example);
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
    * 搜索一个数据表
    * @param  cfgDatatable
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgDatatable") CfgDatatable  cfgDatatable){
        logger.debug("request : {}",  cfgDatatable);
        try{
            CfgDatatable result = null;
            if(cfgDatatable.getId() != null) {
                result =  iCfgDatatableSV.getCfgDatatableByPK(cfgDatatable.getId());
            }else {
                CfgDatatable_Example example = ExampleUtils.parseExample( cfgDatatable, CfgDatatable_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgDatatable> list =  iCfgDatatableSV.getCfgDatatableListByExample(example);
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
    * 创建数据表
    * @param cfgDatatable
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgDatatable") CfgDatatable cfgDatatable) {
        logger.debug("request : {}", cfgDatatable);
        try {
            ControllerHelper.setDefaultValue(cfgDatatable, "id");
            int result = iCfgDatatableSV.create(cfgDatatable);
            if(result > 0) {
                return ResultData.success(cfgDatatable);
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
    * 批量维护数据表
    * @param cfgDatatables
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgDatatable[] cfgDatatables) {
        logger.debug("request : {}", cfgDatatables);

        try {
            ControllerHelper.setDefaultValue(cfgDatatables, "id");
            ControllerHelper.reorderProperty(cfgDatatables);

            int result = iCfgDatatableSV.batchOperate(cfgDatatables);
            if(result > 0) {
                return ResultData.success(cfgDatatables);
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
    * 更新数据表
    * @param cfgDatatable
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgDatatable") CfgDatatable cfgDatatable) {
        logger.debug("request : {}", cfgDatatable);
        try {
            ControllerHelper.setDefaultValue(cfgDatatable, "id");
            int result = iCfgDatatableSV.update(cfgDatatable);
            if(result > 0) {
                return ResultData.success(cfgDatatable);
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
    * 创建或更新数据表
    * @param cfgDatatable
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgDatatable") CfgDatatable cfgDatatable) {
        logger.debug("request : {}", cfgDatatable);
        try {
            ControllerHelper.setDefaultValue(cfgDatatable, "id");
            int result = iCfgDatatableSV.batchOperate(new CfgDatatable[]{cfgDatatable});
            if(result > 0) {
                return ResultData.success(cfgDatatable);
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
    * 删除数据表
    * @param cfgDatatable
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgDatatable") CfgDatatable cfgDatatable) {
        logger.debug("request : {}", cfgDatatable);

        try {
            ControllerHelper.setDefaultValue(cfgDatatable, "id");
            int result = iCfgDatatableSV.delete(cfgDatatable);
            if(result > 0) {
                return ResultData.success(cfgDatatable);
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
 	
	public ICfgDatatableSV getICfgDatatableSV(){
		return iCfgDatatableSV;
	}
	//setter
	public void setICfgDatatableSV(ICfgDatatableSV iCfgDatatableSV){
    	this.iCfgDatatableSV = iCfgDatatableSV;
    }
}
