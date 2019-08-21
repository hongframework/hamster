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
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;

@Controller
@RequestMapping(value = "/cfg/cfgDatasource")
public class CfgDatasourceController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgDatasourceController.class);

	@Resource
	private ICfgDatasourceSV iCfgDatasourceSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示数据源列表
     * @param cfgDatasource
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgDatasource") CfgDatasource cfgDatasource,
                                      @ModelAttribute("example") CfgDatasource_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgDatasource, example, pagination);
        try{
            ExampleUtils.parseExample(cfgDatasource, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgDatasource> list = iCfgDatasourceSV.getCfgDatasourceListByExample(example);
            pagination.setTotalCount(iCfgDatasourceSV.getCfgDatasourceCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示数据源明细
     * @param cfgDatasource
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgDatasource") CfgDatasource cfgDatasource){
        logger.debug("request : {},{}", cfgDatasource.getCfgDatasourceId(), cfgDatasource);
        try{
            CfgDatasource result = null;
            if(cfgDatasource.getCfgDatasourceId() != null) {
                result = iCfgDatasourceSV.getCfgDatasourceByPK(cfgDatasource.getCfgDatasourceId());
            }else {
                CfgDatasource_Example example = ExampleUtils.parseExample(cfgDatasource, CfgDatasource_Example.class);
                List<CfgDatasource> list = iCfgDatasourceSV.getCfgDatasourceListByExample(example);
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
    * 搜索一个数据源
    * @param  cfgDatasource
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgDatasource") CfgDatasource  cfgDatasource){
        logger.debug("request : {}",  cfgDatasource);
        try{
            CfgDatasource result = null;
            if(cfgDatasource.getCfgDatasourceId() != null) {
                result =  iCfgDatasourceSV.getCfgDatasourceByPK(cfgDatasource.getCfgDatasourceId());
            }else {
                CfgDatasource_Example example = ExampleUtils.parseExample( cfgDatasource, CfgDatasource_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgDatasource> list =  iCfgDatasourceSV.getCfgDatasourceListByExample(example);
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
    * 创建数据源
    * @param cfgDatasource
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgDatasource") CfgDatasource cfgDatasource) {
        logger.debug("request : {}", cfgDatasource);
        try {
            ControllerHelper.setDefaultValue(cfgDatasource, "cfgDatasourceId");
            int result = iCfgDatasourceSV.create(cfgDatasource);
            if(result > 0) {
                return ResultData.success(cfgDatasource);
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
    * 批量维护数据源
    * @param cfgDatasources
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgDatasource[] cfgDatasources) {
        logger.debug("request : {}", cfgDatasources);

        try {
            ControllerHelper.setDefaultValue(cfgDatasources, "cfgDatasourceId");
            ControllerHelper.reorderProperty(cfgDatasources);

            int result = iCfgDatasourceSV.batchOperate(cfgDatasources);
            if(result > 0) {
                return ResultData.success(cfgDatasources);
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
    * 更新数据源
    * @param cfgDatasource
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgDatasource") CfgDatasource cfgDatasource) {
        logger.debug("request : {}", cfgDatasource);
        try {
            ControllerHelper.setDefaultValue(cfgDatasource, "cfgDatasourceId");
            int result = iCfgDatasourceSV.update(cfgDatasource);
            if(result > 0) {
                return ResultData.success(cfgDatasource);
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
    * 创建或更新数据源
    * @param cfgDatasource
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgDatasource") CfgDatasource cfgDatasource) {
        logger.debug("request : {}", cfgDatasource);
        try {
            ControllerHelper.setDefaultValue(cfgDatasource, "cfgDatasourceId");
            int result = iCfgDatasourceSV.batchOperate(new CfgDatasource[]{cfgDatasource});
            if(result > 0) {
                return ResultData.success(cfgDatasource);
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
    * 删除数据源
    * @param cfgDatasource
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgDatasource") CfgDatasource cfgDatasource) {
        logger.debug("request : {}", cfgDatasource);

        try {
            ControllerHelper.setDefaultValue(cfgDatasource, "cfgDatasourceId");
            int result = iCfgDatasourceSV.delete(cfgDatasource);
            if(result > 0) {
                return ResultData.success(cfgDatasource);
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
 	
	public ICfgDatasourceSV getICfgDatasourceSV(){
		return iCfgDatasourceSV;
	}
	//setter
	public void setICfgDatasourceSV(ICfgDatasourceSV iCfgDatasourceSV){
    	this.iCfgDatasourceSV = iCfgDatasourceSV;
    }
}
