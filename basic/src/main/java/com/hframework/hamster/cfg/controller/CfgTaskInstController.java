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
import com.hframework.hamster.cfg.domain.model.CfgTaskInst;
import com.hframework.hamster.cfg.domain.model.CfgTaskInst_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskInstSV;

@Controller
@RequestMapping(value = "/cfg/cfgTaskInst")
public class CfgTaskInstController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgTaskInstController.class);

	@Resource
	private ICfgTaskInstSV iCfgTaskInstSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务实例列表
     * @param cfgTaskInst
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgTaskInst") CfgTaskInst cfgTaskInst,
                                      @ModelAttribute("example") CfgTaskInst_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgTaskInst, example, pagination);
        try{
            ExampleUtils.parseExample(cfgTaskInst, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgTaskInst> list = iCfgTaskInstSV.getCfgTaskInstListByExample(example);
            pagination.setTotalCount(iCfgTaskInstSV.getCfgTaskInstCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务实例明细
     * @param cfgTaskInst
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgTaskInst") CfgTaskInst cfgTaskInst){
        logger.debug("request : {},{}", cfgTaskInst.getCfgTaskInstId(), cfgTaskInst);
        try{
            CfgTaskInst result = null;
            if(cfgTaskInst.getCfgTaskInstId() != null) {
                result = iCfgTaskInstSV.getCfgTaskInstByPK(cfgTaskInst.getCfgTaskInstId());
            }else {
                CfgTaskInst_Example example = ExampleUtils.parseExample(cfgTaskInst, CfgTaskInst_Example.class);
                List<CfgTaskInst> list = iCfgTaskInstSV.getCfgTaskInstListByExample(example);
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
    * 搜索一个任务实例
    * @param  cfgTaskInst
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgTaskInst") CfgTaskInst  cfgTaskInst){
        logger.debug("request : {}",  cfgTaskInst);
        try{
            CfgTaskInst result = null;
            if(cfgTaskInst.getCfgTaskInstId() != null) {
                result =  iCfgTaskInstSV.getCfgTaskInstByPK(cfgTaskInst.getCfgTaskInstId());
            }else {
                CfgTaskInst_Example example = ExampleUtils.parseExample( cfgTaskInst, CfgTaskInst_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgTaskInst> list =  iCfgTaskInstSV.getCfgTaskInstListByExample(example);
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
    * 创建任务实例
    * @param cfgTaskInst
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgTaskInst") CfgTaskInst cfgTaskInst) {
        logger.debug("request : {}", cfgTaskInst);
        try {
            ControllerHelper.setDefaultValue(cfgTaskInst, "cfgTaskInstId");
            int result = iCfgTaskInstSV.create(cfgTaskInst);
            if(result > 0) {
                return ResultData.success(cfgTaskInst);
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
    * 批量维护任务实例
    * @param cfgTaskInsts
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgTaskInst[] cfgTaskInsts) {
        logger.debug("request : {}", cfgTaskInsts);

        try {
            ControllerHelper.setDefaultValue(cfgTaskInsts, "cfgTaskInstId");
            ControllerHelper.reorderProperty(cfgTaskInsts);

            int result = iCfgTaskInstSV.batchOperate(cfgTaskInsts);
            if(result > 0) {
                return ResultData.success(cfgTaskInsts);
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
    * 更新任务实例
    * @param cfgTaskInst
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgTaskInst") CfgTaskInst cfgTaskInst) {
        logger.debug("request : {}", cfgTaskInst);
        try {
            ControllerHelper.setDefaultValue(cfgTaskInst, "cfgTaskInstId");
            int result = iCfgTaskInstSV.update(cfgTaskInst);
            if(result > 0) {
                return ResultData.success(cfgTaskInst);
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
    * 创建或更新任务实例
    * @param cfgTaskInst
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgTaskInst") CfgTaskInst cfgTaskInst) {
        logger.debug("request : {}", cfgTaskInst);
        try {
            ControllerHelper.setDefaultValue(cfgTaskInst, "cfgTaskInstId");
            int result = iCfgTaskInstSV.batchOperate(new CfgTaskInst[]{cfgTaskInst});
            if(result > 0) {
                return ResultData.success(cfgTaskInst);
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
    * 删除任务实例
    * @param cfgTaskInst
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgTaskInst") CfgTaskInst cfgTaskInst) {
        logger.debug("request : {}", cfgTaskInst);

        try {
            ControllerHelper.setDefaultValue(cfgTaskInst, "cfgTaskInstId");
            int result = iCfgTaskInstSV.delete(cfgTaskInst);
            if(result > 0) {
                return ResultData.success(cfgTaskInst);
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
 	
	public ICfgTaskInstSV getICfgTaskInstSV(){
		return iCfgTaskInstSV;
	}
	//setter
	public void setICfgTaskInstSV(ICfgTaskInstSV iCfgTaskInstSV){
    	this.iCfgTaskInstSV = iCfgTaskInstSV;
    }
}
