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
import com.hframework.hamster.sec.domain.model.HfmdEnum;
import com.hframework.hamster.sec.domain.model.HfmdEnum_Example;
import com.hframework.hamster.sec.service.interfaces.IHfmdEnumSV;

@Controller
@RequestMapping(value = "/sec/hfmdEnum")
public class HfmdEnumController   {
    private static final Logger logger = LoggerFactory.getLogger(HfmdEnumController.class);

	@Resource
	private IHfmdEnumSV iHfmdEnumSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示字典项列表
     * @param hfmdEnum
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum,
                                      @ModelAttribute("example") HfmdEnum_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", hfmdEnum, example, pagination);
        try{
            ExampleUtils.parseExample(hfmdEnum, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< HfmdEnum> list = iHfmdEnumSV.getHfmdEnumListByExample(example);
            pagination.setTotalCount(iHfmdEnumSV.getHfmdEnumCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示字典项明细
     * @param hfmdEnum
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum){
        logger.debug("request : {},{}", hfmdEnum.getHfmdEnumId(), hfmdEnum);
        try{
            HfmdEnum result = null;
            if(hfmdEnum.getHfmdEnumId() != null) {
                result = iHfmdEnumSV.getHfmdEnumByPK(hfmdEnum.getHfmdEnumId());
            }else {
                HfmdEnum_Example example = ExampleUtils.parseExample(hfmdEnum, HfmdEnum_Example.class);
                List<HfmdEnum> list = iHfmdEnumSV.getHfmdEnumListByExample(example);
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
    * 搜索一个字典项
    * @param  hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" hfmdEnum") HfmdEnum  hfmdEnum){
        logger.debug("request : {}",  hfmdEnum);
        try{
            HfmdEnum result = null;
            if(hfmdEnum.getHfmdEnumId() != null) {
                result =  iHfmdEnumSV.getHfmdEnumByPK(hfmdEnum.getHfmdEnumId());
            }else {
                HfmdEnum_Example example = ExampleUtils.parseExample( hfmdEnum, HfmdEnum_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<HfmdEnum> list =  iHfmdEnumSV.getHfmdEnumListByExample(example);
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
    * 创建字典项
    * @param hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum) {
        logger.debug("request : {}", hfmdEnum);
        try {
            ControllerHelper.setDefaultValue(hfmdEnum, "hfmdEnumId");
            int result = iHfmdEnumSV.create(hfmdEnum);
            if(result > 0) {
                return ResultData.success(hfmdEnum);
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
    * 批量维护字典项
    * @param hfmdEnums
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody HfmdEnum[] hfmdEnums) {
        logger.debug("request : {}", hfmdEnums);

        try {
            ControllerHelper.setDefaultValue(hfmdEnums, "hfmdEnumId");
            ControllerHelper.reorderProperty(hfmdEnums);

            int result = iHfmdEnumSV.batchOperate(hfmdEnums);
            if(result > 0) {
                return ResultData.success(hfmdEnums);
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
    * 更新字典项
    * @param hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum) {
        logger.debug("request : {}", hfmdEnum);
        try {
            ControllerHelper.setDefaultValue(hfmdEnum, "hfmdEnumId");
            int result = iHfmdEnumSV.update(hfmdEnum);
            if(result > 0) {
                return ResultData.success(hfmdEnum);
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
    * 创建或更新字典项
    * @param hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum) {
        logger.debug("request : {}", hfmdEnum);
        try {
            ControllerHelper.setDefaultValue(hfmdEnum, "hfmdEnumId");
            int result = iHfmdEnumSV.batchOperate(new HfmdEnum[]{hfmdEnum});
            if(result > 0) {
                return ResultData.success(hfmdEnum);
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
    * 删除字典项
    * @param hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum) {
        logger.debug("request : {}", hfmdEnum);

        try {
            ControllerHelper.setDefaultValue(hfmdEnum, "hfmdEnumId");
            int result = iHfmdEnumSV.delete(hfmdEnum);
            if(result > 0) {
                return ResultData.success(hfmdEnum);
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
 	
	public IHfmdEnumSV getIHfmdEnumSV(){
		return iHfmdEnumSV;
	}
	//setter
	public void setIHfmdEnumSV(IHfmdEnumSV iHfmdEnumSV){
    	this.iHfmdEnumSV = iHfmdEnumSV;
    }
}
