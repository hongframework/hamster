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
import com.hframework.hamster.cfg.domain.model.CfgJob;
import com.hframework.hamster.cfg.domain.model.CfgJob_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobSV;

@Controller
@RequestMapping(value = "/cfg/cfgJob")
public class CfgJobController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgJobController.class);

	@Resource
	private ICfgJobSV iCfgJobSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务定义列表
     * @param cfgJob
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgJob") CfgJob cfgJob,
                                      @ModelAttribute("example") CfgJob_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgJob, example, pagination);
        try{
            ExampleUtils.parseExample(cfgJob, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgJob> list = iCfgJobSV.getCfgJobListByExample(example);
            pagination.setTotalCount(iCfgJobSV.getCfgJobCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务定义明细
     * @param cfgJob
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgJob") CfgJob cfgJob){
        logger.debug("request : {},{}", cfgJob.getId(), cfgJob);
        try{
            CfgJob result = null;
            if(cfgJob.getId() != null) {
                result = iCfgJobSV.getCfgJobByPK(cfgJob.getId());
            }else {
                CfgJob_Example example = ExampleUtils.parseExample(cfgJob, CfgJob_Example.class);
                List<CfgJob> list = iCfgJobSV.getCfgJobListByExample(example);
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
    * 搜索一个任务定义
    * @param  cfgJob
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgJob") CfgJob  cfgJob){
        logger.debug("request : {}",  cfgJob);
        try{
            CfgJob result = null;
            if(cfgJob.getId() != null) {
                result =  iCfgJobSV.getCfgJobByPK(cfgJob.getId());
            }else {
                CfgJob_Example example = ExampleUtils.parseExample( cfgJob, CfgJob_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgJob> list =  iCfgJobSV.getCfgJobListByExample(example);
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
    * 创建任务定义
    * @param cfgJob
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgJob") CfgJob cfgJob) {
        logger.debug("request : {}", cfgJob);
        try {
            ControllerHelper.setDefaultValue(cfgJob, "id");
            int result = iCfgJobSV.create(cfgJob);
            if(result > 0) {
                return ResultData.success(cfgJob);
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
    * 批量维护任务定义
    * @param cfgJobs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgJob[] cfgJobs) {
        logger.debug("request : {}", cfgJobs);

        try {
            ControllerHelper.setDefaultValue(cfgJobs, "id");
            ControllerHelper.reorderProperty(cfgJobs);

            int result = iCfgJobSV.batchOperate(cfgJobs);
            if(result > 0) {
                return ResultData.success(cfgJobs);
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
    * 更新任务定义
    * @param cfgJob
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgJob") CfgJob cfgJob) {
        logger.debug("request : {}", cfgJob);
        try {
            ControllerHelper.setDefaultValue(cfgJob, "id");
            int result = iCfgJobSV.update(cfgJob);
            if(result > 0) {
                return ResultData.success(cfgJob);
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
    * 创建或更新任务定义
    * @param cfgJob
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgJob") CfgJob cfgJob) {
        logger.debug("request : {}", cfgJob);
        try {
            ControllerHelper.setDefaultValue(cfgJob, "id");
            int result = iCfgJobSV.batchOperate(new CfgJob[]{cfgJob});
            if(result > 0) {
                return ResultData.success(cfgJob);
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
    * 删除任务定义
    * @param cfgJob
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgJob") CfgJob cfgJob) {
        logger.debug("request : {}", cfgJob);

        try {
            ControllerHelper.setDefaultValue(cfgJob, "id");
            int result = iCfgJobSV.delete(cfgJob);
            if(result > 0) {
                return ResultData.success(cfgJob);
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
 	
	public ICfgJobSV getICfgJobSV(){
		return iCfgJobSV;
	}
	//setter
	public void setICfgJobSV(ICfgJobSV iCfgJobSV){
    	this.iCfgJobSV = iCfgJobSV;
    }
}
