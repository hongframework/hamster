package com.hframework.hamster.sch.controller;

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
import com.hframework.hamster.sch.domain.model.LogDeploy;
import com.hframework.hamster.sch.domain.model.LogDeploy_Example;
import com.hframework.hamster.sch.service.interfaces.ILogDeploySV;

@Controller
@RequestMapping(value = "/sch/logDeploy")
public class LogDeployController   {
    private static final Logger logger = LoggerFactory.getLogger(LogDeployController.class);

	@Resource
	private ILogDeploySV iLogDeploySV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示发布日志列表
     * @param logDeploy
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("logDeploy") LogDeploy logDeploy,
                                      @ModelAttribute("example") LogDeploy_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", logDeploy, example, pagination);
        try{
            ExampleUtils.parseExample(logDeploy, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< LogDeploy> list = iLogDeploySV.getLogDeployListByExample(example);
            pagination.setTotalCount(iLogDeploySV.getLogDeployCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示发布日志明细
     * @param logDeploy
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("logDeploy") LogDeploy logDeploy){
        logger.debug("request : {},{}", logDeploy.getId(), logDeploy);
        try{
            LogDeploy result = null;
            if(logDeploy.getId() != null) {
                result = iLogDeploySV.getLogDeployByPK(logDeploy.getId());
            }else {
                LogDeploy_Example example = ExampleUtils.parseExample(logDeploy, LogDeploy_Example.class);
                List<LogDeploy> list = iLogDeploySV.getLogDeployListByExample(example);
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
    * 搜索一个发布日志
    * @param  logDeploy
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" logDeploy") LogDeploy  logDeploy){
        logger.debug("request : {}",  logDeploy);
        try{
            LogDeploy result = null;
            if(logDeploy.getId() != null) {
                result =  iLogDeploySV.getLogDeployByPK(logDeploy.getId());
            }else {
                LogDeploy_Example example = ExampleUtils.parseExample( logDeploy, LogDeploy_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<LogDeploy> list =  iLogDeploySV.getLogDeployListByExample(example);
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
    * 创建发布日志
    * @param logDeploy
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("logDeploy") LogDeploy logDeploy) {
        logger.debug("request : {}", logDeploy);
        try {
            ControllerHelper.setDefaultValue(logDeploy, "id");
            int result = iLogDeploySV.create(logDeploy);
            if(result > 0) {
                return ResultData.success(logDeploy);
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
    * 批量维护发布日志
    * @param logDeploys
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody LogDeploy[] logDeploys) {
        logger.debug("request : {}", logDeploys);

        try {
            ControllerHelper.setDefaultValue(logDeploys, "id");
            ControllerHelper.reorderProperty(logDeploys);

            int result = iLogDeploySV.batchOperate(logDeploys);
            if(result > 0) {
                return ResultData.success(logDeploys);
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
    * 更新发布日志
    * @param logDeploy
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("logDeploy") LogDeploy logDeploy) {
        logger.debug("request : {}", logDeploy);
        try {
            ControllerHelper.setDefaultValue(logDeploy, "id");
            int result = iLogDeploySV.update(logDeploy);
            if(result > 0) {
                return ResultData.success(logDeploy);
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
    * 创建或更新发布日志
    * @param logDeploy
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("logDeploy") LogDeploy logDeploy) {
        logger.debug("request : {}", logDeploy);
        try {
            ControllerHelper.setDefaultValue(logDeploy, "id");
            int result = iLogDeploySV.batchOperate(new LogDeploy[]{logDeploy});
            if(result > 0) {
                return ResultData.success(logDeploy);
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
    * 删除发布日志
    * @param logDeploy
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("logDeploy") LogDeploy logDeploy) {
        logger.debug("request : {}", logDeploy);

        try {
            ControllerHelper.setDefaultValue(logDeploy, "id");
            int result = iLogDeploySV.delete(logDeploy);
            if(result > 0) {
                return ResultData.success(logDeploy);
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
 	
	public ILogDeploySV getILogDeploySV(){
		return iLogDeploySV;
	}
	//setter
	public void setILogDeploySV(ILogDeploySV iLogDeploySV){
    	this.iLogDeploySV = iLogDeploySV;
    }
}
