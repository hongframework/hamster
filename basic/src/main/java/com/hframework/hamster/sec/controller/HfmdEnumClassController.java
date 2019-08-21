package com.hframework.hamster.sec.controller;

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
import com.hframework.hamster.sec.domain.model.HfmdEnumClass;
import com.hframework.hamster.sec.domain.model.HfmdEnumClass_Example;
import com.hframework.hamster.sec.service.interfaces.IHfmdEnumClassSV;

@Controller
@RequestMapping(value = "/sec/hfmdEnumClass")
public class HfmdEnumClassController   {
    private static final Logger logger = LoggerFactory.getLogger(HfmdEnumClassController.class);

	@Resource
	private IHfmdEnumClassSV iHfmdEnumClassSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示字典列表
     * @param hfmdEnumClass
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("hfmdEnumClass") HfmdEnumClass hfmdEnumClass,
                                      @ModelAttribute("example") HfmdEnumClass_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", hfmdEnumClass, example, pagination);
        try{
            ExampleUtils.parseExample(hfmdEnumClass, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< HfmdEnumClass> list = iHfmdEnumClassSV.getHfmdEnumClassListByExample(example);
            pagination.setTotalCount(iHfmdEnumClassSV.getHfmdEnumClassCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示字典明细
     * @param hfmdEnumClass
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("hfmdEnumClass") HfmdEnumClass hfmdEnumClass){
        logger.debug("request : {},{}", hfmdEnumClass.getHfmdEnumClassId(), hfmdEnumClass);
        try{
            HfmdEnumClass result = null;
            if(hfmdEnumClass.getHfmdEnumClassId() != null) {
                result = iHfmdEnumClassSV.getHfmdEnumClassByPK(hfmdEnumClass.getHfmdEnumClassId());
            }else {
                HfmdEnumClass_Example example = ExampleUtils.parseExample(hfmdEnumClass, HfmdEnumClass_Example.class);
                List<HfmdEnumClass> list = iHfmdEnumClassSV.getHfmdEnumClassListByExample(example);
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
    * 搜索一个字典
    * @param  hfmdEnumClass
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" hfmdEnumClass") HfmdEnumClass  hfmdEnumClass){
        logger.debug("request : {}",  hfmdEnumClass);
        try{
            HfmdEnumClass result = null;
            if(hfmdEnumClass.getHfmdEnumClassId() != null) {
                result =  iHfmdEnumClassSV.getHfmdEnumClassByPK(hfmdEnumClass.getHfmdEnumClassId());
            }else {
                HfmdEnumClass_Example example = ExampleUtils.parseExample( hfmdEnumClass, HfmdEnumClass_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<HfmdEnumClass> list =  iHfmdEnumClassSV.getHfmdEnumClassListByExample(example);
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
    * 创建字典
    * @param hfmdEnumClass
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("hfmdEnumClass") HfmdEnumClass hfmdEnumClass) {
        logger.debug("request : {}", hfmdEnumClass);
        try {
            ControllerHelper.setDefaultValue(hfmdEnumClass, "hfmdEnumClassId");
            int result = iHfmdEnumClassSV.create(hfmdEnumClass);
            if(result > 0) {
                return ResultData.success(hfmdEnumClass);
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
    * 批量维护字典
    * @param hfmdEnumClasss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody HfmdEnumClass[] hfmdEnumClasss) {
        logger.debug("request : {}", hfmdEnumClasss);

        try {
            ControllerHelper.setDefaultValue(hfmdEnumClasss, "hfmdEnumClassId");
            ControllerHelper.reorderProperty(hfmdEnumClasss);

            int result = iHfmdEnumClassSV.batchOperate(hfmdEnumClasss);
            if(result > 0) {
                return ResultData.success(hfmdEnumClasss);
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
    * 更新字典
    * @param hfmdEnumClass
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("hfmdEnumClass") HfmdEnumClass hfmdEnumClass) {
        logger.debug("request : {}", hfmdEnumClass);
        try {
            ControllerHelper.setDefaultValue(hfmdEnumClass, "hfmdEnumClassId");
            int result = iHfmdEnumClassSV.update(hfmdEnumClass);
            if(result > 0) {
                return ResultData.success(hfmdEnumClass);
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
    * 创建或更新字典
    * @param hfmdEnumClass
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("hfmdEnumClass") HfmdEnumClass hfmdEnumClass) {
        logger.debug("request : {}", hfmdEnumClass);
        try {
            ControllerHelper.setDefaultValue(hfmdEnumClass, "hfmdEnumClassId");
            int result = iHfmdEnumClassSV.batchOperate(new HfmdEnumClass[]{hfmdEnumClass});
            if(result > 0) {
                return ResultData.success(hfmdEnumClass);
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
    * 删除字典
    * @param hfmdEnumClass
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("hfmdEnumClass") HfmdEnumClass hfmdEnumClass) {
        logger.debug("request : {}", hfmdEnumClass);

        try {
            ControllerHelper.setDefaultValue(hfmdEnumClass, "hfmdEnumClassId");
            int result = iHfmdEnumClassSV.delete(hfmdEnumClass);
            if(result > 0) {
                return ResultData.success(hfmdEnumClass);
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
 	
	public IHfmdEnumClassSV getIHfmdEnumClassSV(){
		return iHfmdEnumClassSV;
	}
	//setter
	public void setIHfmdEnumClassSV(IHfmdEnumClassSV iHfmdEnumClassSV){
    	this.iHfmdEnumClassSV = iHfmdEnumClassSV;
    }
}
