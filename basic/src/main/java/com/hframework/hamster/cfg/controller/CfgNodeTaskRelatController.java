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
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat;
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeTaskRelatSV;

@Controller
@RequestMapping(value = "/cfg/cfgNodeTaskRelat")
public class CfgNodeTaskRelatController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgNodeTaskRelatController.class);

	@Resource
	private ICfgNodeTaskRelatSV iCfgNodeTaskRelatSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务节点部署列表
     * @param cfgNodeTaskRelat
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgNodeTaskRelat") CfgNodeTaskRelat cfgNodeTaskRelat,
                                      @ModelAttribute("example") CfgNodeTaskRelat_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgNodeTaskRelat, example, pagination);
        try{
            ExampleUtils.parseExample(cfgNodeTaskRelat, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgNodeTaskRelat> list = iCfgNodeTaskRelatSV.getCfgNodeTaskRelatListByExample(example);
            pagination.setTotalCount(iCfgNodeTaskRelatSV.getCfgNodeTaskRelatCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务节点部署明细
     * @param cfgNodeTaskRelat
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgNodeTaskRelat") CfgNodeTaskRelat cfgNodeTaskRelat){
        logger.debug("request : {},{}", cfgNodeTaskRelat.getCfgNodeTaskRelatId(), cfgNodeTaskRelat);
        try{
            CfgNodeTaskRelat result = null;
            if(cfgNodeTaskRelat.getCfgNodeTaskRelatId() != null) {
                result = iCfgNodeTaskRelatSV.getCfgNodeTaskRelatByPK(cfgNodeTaskRelat.getCfgNodeTaskRelatId());
            }else {
                CfgNodeTaskRelat_Example example = ExampleUtils.parseExample(cfgNodeTaskRelat, CfgNodeTaskRelat_Example.class);
                List<CfgNodeTaskRelat> list = iCfgNodeTaskRelatSV.getCfgNodeTaskRelatListByExample(example);
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
    * 搜索一个任务节点部署
    * @param  cfgNodeTaskRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgNodeTaskRelat") CfgNodeTaskRelat  cfgNodeTaskRelat){
        logger.debug("request : {}",  cfgNodeTaskRelat);
        try{
            CfgNodeTaskRelat result = null;
            if(cfgNodeTaskRelat.getCfgNodeTaskRelatId() != null) {
                result =  iCfgNodeTaskRelatSV.getCfgNodeTaskRelatByPK(cfgNodeTaskRelat.getCfgNodeTaskRelatId());
            }else {
                CfgNodeTaskRelat_Example example = ExampleUtils.parseExample( cfgNodeTaskRelat, CfgNodeTaskRelat_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgNodeTaskRelat> list =  iCfgNodeTaskRelatSV.getCfgNodeTaskRelatListByExample(example);
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
    * 创建任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgNodeTaskRelat") CfgNodeTaskRelat cfgNodeTaskRelat) {
        logger.debug("request : {}", cfgNodeTaskRelat);
        try {
            ControllerHelper.setDefaultValue(cfgNodeTaskRelat, "cfgNodeTaskRelatId");
            int result = iCfgNodeTaskRelatSV.create(cfgNodeTaskRelat);
            if(result > 0) {
                return ResultData.success(cfgNodeTaskRelat);
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
    * 批量维护任务节点部署
    * @param cfgNodeTaskRelats
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgNodeTaskRelat[] cfgNodeTaskRelats) {
        logger.debug("request : {}", cfgNodeTaskRelats);

        try {
            ControllerHelper.setDefaultValue(cfgNodeTaskRelats, "cfgNodeTaskRelatId");
            ControllerHelper.reorderProperty(cfgNodeTaskRelats);

            int result = iCfgNodeTaskRelatSV.batchOperate(cfgNodeTaskRelats);
            if(result > 0) {
                return ResultData.success(cfgNodeTaskRelats);
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
    * 更新任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgNodeTaskRelat") CfgNodeTaskRelat cfgNodeTaskRelat) {
        logger.debug("request : {}", cfgNodeTaskRelat);
        try {
            ControllerHelper.setDefaultValue(cfgNodeTaskRelat, "cfgNodeTaskRelatId");
            int result = iCfgNodeTaskRelatSV.update(cfgNodeTaskRelat);
            if(result > 0) {
                return ResultData.success(cfgNodeTaskRelat);
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
    * 创建或更新任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgNodeTaskRelat") CfgNodeTaskRelat cfgNodeTaskRelat) {
        logger.debug("request : {}", cfgNodeTaskRelat);
        try {
            ControllerHelper.setDefaultValue(cfgNodeTaskRelat, "cfgNodeTaskRelatId");
            int result = iCfgNodeTaskRelatSV.batchOperate(new CfgNodeTaskRelat[]{cfgNodeTaskRelat});
            if(result > 0) {
                return ResultData.success(cfgNodeTaskRelat);
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
    * 删除任务节点部署
    * @param cfgNodeTaskRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgNodeTaskRelat") CfgNodeTaskRelat cfgNodeTaskRelat) {
        logger.debug("request : {}", cfgNodeTaskRelat);

        try {
            ControllerHelper.setDefaultValue(cfgNodeTaskRelat, "cfgNodeTaskRelatId");
            int result = iCfgNodeTaskRelatSV.delete(cfgNodeTaskRelat);
            if(result > 0) {
                return ResultData.success(cfgNodeTaskRelat);
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
 	
	public ICfgNodeTaskRelatSV getICfgNodeTaskRelatSV(){
		return iCfgNodeTaskRelatSV;
	}
	//setter
	public void setICfgNodeTaskRelatSV(ICfgNodeTaskRelatSV iCfgNodeTaskRelatSV){
    	this.iCfgNodeTaskRelatSV = iCfgNodeTaskRelatSV;
    }
}
