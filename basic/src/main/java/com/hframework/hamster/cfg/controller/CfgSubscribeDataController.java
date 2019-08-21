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
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgSubscribeDataSV;

@Controller
@RequestMapping(value = "/cfg/cfgSubscribeData")
public class CfgSubscribeDataController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgSubscribeDataController.class);

	@Resource
	private ICfgSubscribeDataSV iCfgSubscribeDataSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示订阅数据列表
     * @param cfgSubscribeData
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgSubscribeData") CfgSubscribeData cfgSubscribeData,
                                      @ModelAttribute("example") CfgSubscribeData_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgSubscribeData, example, pagination);
        try{
            ExampleUtils.parseExample(cfgSubscribeData, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgSubscribeData> list = iCfgSubscribeDataSV.getCfgSubscribeDataListByExample(example);
            pagination.setTotalCount(iCfgSubscribeDataSV.getCfgSubscribeDataCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示订阅数据明细
     * @param cfgSubscribeData
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgSubscribeData") CfgSubscribeData cfgSubscribeData){
        logger.debug("request : {},{}", cfgSubscribeData.getCfgSubscribeDataId(), cfgSubscribeData);
        try{
            CfgSubscribeData result = null;
            if(cfgSubscribeData.getCfgSubscribeDataId() != null) {
                result = iCfgSubscribeDataSV.getCfgSubscribeDataByPK(cfgSubscribeData.getCfgSubscribeDataId());
            }else {
                CfgSubscribeData_Example example = ExampleUtils.parseExample(cfgSubscribeData, CfgSubscribeData_Example.class);
                List<CfgSubscribeData> list = iCfgSubscribeDataSV.getCfgSubscribeDataListByExample(example);
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
    * 搜索一个订阅数据
    * @param  cfgSubscribeData
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgSubscribeData") CfgSubscribeData  cfgSubscribeData){
        logger.debug("request : {}",  cfgSubscribeData);
        try{
            CfgSubscribeData result = null;
            if(cfgSubscribeData.getCfgSubscribeDataId() != null) {
                result =  iCfgSubscribeDataSV.getCfgSubscribeDataByPK(cfgSubscribeData.getCfgSubscribeDataId());
            }else {
                CfgSubscribeData_Example example = ExampleUtils.parseExample( cfgSubscribeData, CfgSubscribeData_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgSubscribeData> list =  iCfgSubscribeDataSV.getCfgSubscribeDataListByExample(example);
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
    * 创建订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgSubscribeData") CfgSubscribeData cfgSubscribeData) {
        logger.debug("request : {}", cfgSubscribeData);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribeData, "cfgSubscribeDataId");
            int result = iCfgSubscribeDataSV.create(cfgSubscribeData);
            if(result > 0) {
                return ResultData.success(cfgSubscribeData);
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
    * 批量维护订阅数据
    * @param cfgSubscribeDatas
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgSubscribeData[] cfgSubscribeDatas) {
        logger.debug("request : {}", cfgSubscribeDatas);

        try {
            ControllerHelper.setDefaultValue(cfgSubscribeDatas, "cfgSubscribeDataId");
            ControllerHelper.reorderProperty(cfgSubscribeDatas);

            int result = iCfgSubscribeDataSV.batchOperate(cfgSubscribeDatas);
            if(result > 0) {
                return ResultData.success(cfgSubscribeDatas);
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
    * 更新订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgSubscribeData") CfgSubscribeData cfgSubscribeData) {
        logger.debug("request : {}", cfgSubscribeData);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribeData, "cfgSubscribeDataId");
            int result = iCfgSubscribeDataSV.update(cfgSubscribeData);
            if(result > 0) {
                return ResultData.success(cfgSubscribeData);
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
    * 创建或更新订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgSubscribeData") CfgSubscribeData cfgSubscribeData) {
        logger.debug("request : {}", cfgSubscribeData);
        try {
            ControllerHelper.setDefaultValue(cfgSubscribeData, "cfgSubscribeDataId");
            int result = iCfgSubscribeDataSV.batchOperate(new CfgSubscribeData[]{cfgSubscribeData});
            if(result > 0) {
                return ResultData.success(cfgSubscribeData);
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
    * 删除订阅数据
    * @param cfgSubscribeData
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgSubscribeData") CfgSubscribeData cfgSubscribeData) {
        logger.debug("request : {}", cfgSubscribeData);

        try {
            ControllerHelper.setDefaultValue(cfgSubscribeData, "cfgSubscribeDataId");
            int result = iCfgSubscribeDataSV.delete(cfgSubscribeData);
            if(result > 0) {
                return ResultData.success(cfgSubscribeData);
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
 	
	public ICfgSubscribeDataSV getICfgSubscribeDataSV(){
		return iCfgSubscribeDataSV;
	}
	//setter
	public void setICfgSubscribeDataSV(ICfgSubscribeDataSV iCfgSubscribeDataSV){
    	this.iCfgSubscribeDataSV = iCfgSubscribeDataSV;
    }
}
