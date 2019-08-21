package com.hframework.hamster.sch.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.common.springext.datasource.DataSourceContextHolder;
import com.hframework.common.util.ExampleUtils;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;
import com.hframework.smartsql.client.DBClient;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;
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
import com.hframework.hamster.sch.domain.model.JobExeMeta;
import com.hframework.hamster.sch.domain.model.JobExeMeta_Example;
import com.hframework.hamster.sch.service.interfaces.IJobExeMetaSV;

@Controller
@RequestMapping(value = "/sch/jobExeMeta")
public class JobExeMetaController   {
    private static final Logger logger = LoggerFactory.getLogger(JobExeMetaController.class);

	@Resource
	private IJobExeMetaSV iJobExeMetaSV;


    public void changeToHamsterDW() throws Exception {
        CfgDatasource_Example exmpale = new CfgDatasource_Example();
        exmpale.createCriteria().andDbEqualTo("hamster_dw");
        exmpale.or().andDbEqualTo("hamster_data");
        CfgDatasource dataSource = ServiceFactory.getService(ICfgDatasourceSV.class).getCfgDatasourceListByExample(exmpale).get(0);
        DataSourceContextHolder.setDbInfo("jdbc:mysql://" +dataSource.getUrl().trim()+ "/"+ dataSource.getDb().trim()+"" +
                "?useUnicode=true&tinyInt1isBit=false", dataSource.getUsername().trim(), dataSource.getPassword() != null ? dataSource.getPassword().trim() : null);
    }
    public void changeToHamster() {
        DataSourceContextHolder.clear();
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务调度元数据列表
     * @param jobExeMeta
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("jobExeMeta") JobExeMeta jobExeMeta,
                                      @ModelAttribute("example") JobExeMeta_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", jobExeMeta, example, pagination);
        try{
            changeToHamsterDW();
            ExampleUtils.parseExample(jobExeMeta, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< JobExeMeta> list = iJobExeMetaSV.getJobExeMetaListByExample(example);
            pagination.setTotalCount(iJobExeMetaSV.getJobExeMetaCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }finally {
            changeToHamster();
        }
    }



    /**
     * 查询展示任务调度元数据明细
     * @param jobExeMeta
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("jobExeMeta") JobExeMeta jobExeMeta){
        logger.debug("request : {},{}", jobExeMeta.getId(), jobExeMeta);
        try{
            changeToHamsterDW();
            JobExeMeta result = null;
            if(jobExeMeta.getId() != null) {
                result = iJobExeMetaSV.getJobExeMetaByPK(jobExeMeta.getId());
            }else {
                JobExeMeta_Example example = ExampleUtils.parseExample(jobExeMeta, JobExeMeta_Example.class);
                List<JobExeMeta> list = iJobExeMetaSV.getJobExeMetaListByExample(example);
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
        }finally {
            changeToHamster();
        }
    }

    /**
    * 搜索一个任务调度元数据
    * @param  jobExeMeta
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" jobExeMeta") JobExeMeta  jobExeMeta){
        logger.debug("request : {}",  jobExeMeta);
        try{
            changeToHamsterDW();
            JobExeMeta result = null;
            if(jobExeMeta.getId() != null) {
                result =  iJobExeMetaSV.getJobExeMetaByPK(jobExeMeta.getId());
            }else {
                JobExeMeta_Example example = ExampleUtils.parseExample( jobExeMeta, JobExeMeta_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<JobExeMeta> list =  iJobExeMetaSV.getJobExeMetaListByExample(example);
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
        }finally {
            changeToHamster();
        }
    }

    /**
    * 创建任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("jobExeMeta") JobExeMeta jobExeMeta) {
        logger.debug("request : {}", jobExeMeta);
        try {
            changeToHamsterDW();
            ControllerHelper.setDefaultValue(jobExeMeta, "id");
            int result = iJobExeMetaSV.create(jobExeMeta);
            if(result > 0) {
                return ResultData.success(jobExeMeta);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }finally {
            changeToHamster();
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 批量维护任务调度元数据
    * @param jobExeMetas
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody JobExeMeta[] jobExeMetas) {
        logger.debug("request : {}", jobExeMetas);

        try {
            changeToHamsterDW();
            ControllerHelper.setDefaultValue(jobExeMetas, "id");
            ControllerHelper.reorderProperty(jobExeMetas);

            int result = iJobExeMetaSV.batchOperate(jobExeMetas);
            if(result > 0) {
                return ResultData.success(jobExeMetas);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }finally {
            changeToHamster();
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("jobExeMeta") JobExeMeta jobExeMeta) {
        logger.debug("request : {}", jobExeMeta);
        try {
            changeToHamsterDW();
            ControllerHelper.setDefaultValue(jobExeMeta, "id");
            int result = iJobExeMetaSV.update(jobExeMeta);
            if(result > 0) {
                return ResultData.success(jobExeMeta);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }finally {
            changeToHamster();
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 创建或更新任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("jobExeMeta") JobExeMeta jobExeMeta) {
        logger.debug("request : {}", jobExeMeta);
        try {
            changeToHamsterDW();
            ControllerHelper.setDefaultValue(jobExeMeta, "id");
            int result = iJobExeMetaSV.batchOperate(new JobExeMeta[]{jobExeMeta});
            if(result > 0) {
                return ResultData.success(jobExeMeta);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }finally {
            changeToHamster();
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除任务调度元数据
    * @param jobExeMeta
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("jobExeMeta") JobExeMeta jobExeMeta) {
        logger.debug("request : {}", jobExeMeta);

        try {
            changeToHamsterDW();
            ControllerHelper.setDefaultValue(jobExeMeta, "id");
            int result = iJobExeMetaSV.delete(jobExeMeta);
            if(result > 0) {
                return ResultData.success(jobExeMeta);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }finally {
            changeToHamster();
        }
    }
  	//getter
 	
	public IJobExeMetaSV getIJobExeMetaSV(){
		return iJobExeMetaSV;
	}
	//setter
	public void setIJobExeMetaSV(IJobExeMetaSV iJobExeMetaSV){
    	this.iJobExeMetaSV = iJobExeMetaSV;
    }
}
