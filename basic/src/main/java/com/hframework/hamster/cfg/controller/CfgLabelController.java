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
import com.hframework.hamster.cfg.domain.model.CfgLabel;
import com.hframework.hamster.cfg.domain.model.CfgLabel_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgLabelSV;

@Controller
@RequestMapping(value = "/cfg/cfgLabel")
public class CfgLabelController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgLabelController.class);

	@Resource
	private ICfgLabelSV iCfgLabelSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示标签列表
     * @param cfgLabel
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgLabel") CfgLabel cfgLabel,
                                      @ModelAttribute("example") CfgLabel_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgLabel, example, pagination);
        try{
            ExampleUtils.parseExample(cfgLabel, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgLabel> list = iCfgLabelSV.getCfgLabelListByExample(example);
            pagination.setTotalCount(iCfgLabelSV.getCfgLabelCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示标签明细
     * @param cfgLabel
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgLabel") CfgLabel cfgLabel){
        logger.debug("request : {},{}", cfgLabel.getId(), cfgLabel);
        try{
            CfgLabel result = null;
            if(cfgLabel.getId() != null) {
                result = iCfgLabelSV.getCfgLabelByPK(cfgLabel.getId());
            }else {
                CfgLabel_Example example = ExampleUtils.parseExample(cfgLabel, CfgLabel_Example.class);
                List<CfgLabel> list = iCfgLabelSV.getCfgLabelListByExample(example);
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
    * 搜索一个标签
    * @param  cfgLabel
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgLabel") CfgLabel  cfgLabel){
        logger.debug("request : {}",  cfgLabel);
        try{
            CfgLabel result = null;
            if(cfgLabel.getId() != null) {
                result =  iCfgLabelSV.getCfgLabelByPK(cfgLabel.getId());
            }else {
                CfgLabel_Example example = ExampleUtils.parseExample( cfgLabel, CfgLabel_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgLabel> list =  iCfgLabelSV.getCfgLabelListByExample(example);
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
    * 创建标签
    * @param cfgLabel
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgLabel") CfgLabel cfgLabel) {
        logger.debug("request : {}", cfgLabel);
        try {
            ControllerHelper.setDefaultValue(cfgLabel, "id");
            int result = iCfgLabelSV.create(cfgLabel);
            if(result > 0) {
                return ResultData.success(cfgLabel);
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
    * 批量维护标签
    * @param cfgLabels
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgLabel[] cfgLabels) {
        logger.debug("request : {}", cfgLabels);

        try {
            ControllerHelper.setDefaultValue(cfgLabels, "id");
            ControllerHelper.reorderProperty(cfgLabels);

            int result = iCfgLabelSV.batchOperate(cfgLabels);
            if(result > 0) {
                return ResultData.success(cfgLabels);
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
    * 更新标签
    * @param cfgLabel
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgLabel") CfgLabel cfgLabel) {
        logger.debug("request : {}", cfgLabel);
        try {
            ControllerHelper.setDefaultValue(cfgLabel, "id");
            int result = iCfgLabelSV.update(cfgLabel);
            if(result > 0) {
                return ResultData.success(cfgLabel);
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
    * 创建或更新标签
    * @param cfgLabel
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgLabel") CfgLabel cfgLabel) {
        logger.debug("request : {}", cfgLabel);
        try {
            ControllerHelper.setDefaultValue(cfgLabel, "id");
            int result = iCfgLabelSV.batchOperate(new CfgLabel[]{cfgLabel});
            if(result > 0) {
                return ResultData.success(cfgLabel);
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
    * 删除标签
    * @param cfgLabel
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgLabel") CfgLabel cfgLabel) {
        logger.debug("request : {}", cfgLabel);

        try {
            ControllerHelper.setDefaultValue(cfgLabel, "id");
            int result = iCfgLabelSV.delete(cfgLabel);
            if(result > 0) {
                return ResultData.success(cfgLabel);
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
 	
	public ICfgLabelSV getICfgLabelSV(){
		return iCfgLabelSV;
	}
	//setter
	public void setICfgLabelSV(ICfgLabelSV iCfgLabelSV){
    	this.iCfgLabelSV = iCfgLabelSV;
    }
}
