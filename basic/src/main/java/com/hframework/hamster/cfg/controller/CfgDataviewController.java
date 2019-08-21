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
import com.hframework.hamster.cfg.domain.model.CfgDataview;
import com.hframework.hamster.cfg.domain.model.CfgDataview_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDataviewSV;

@Controller
@RequestMapping(value = "/cfg/cfgDataview")
public class CfgDataviewController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgDataviewController.class);

	@Resource
	private ICfgDataviewSV iCfgDataviewSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示数据视图列表
     * @param cfgDataview
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgDataview") CfgDataview cfgDataview,
                                      @ModelAttribute("example") CfgDataview_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgDataview, example, pagination);
        try{
            ExampleUtils.parseExample(cfgDataview, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgDataview> list = iCfgDataviewSV.getCfgDataviewListByExample(example);
            pagination.setTotalCount(iCfgDataviewSV.getCfgDataviewCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示数据视图明细
     * @param cfgDataview
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgDataview") CfgDataview cfgDataview){
        logger.debug("request : {},{}", cfgDataview.getId(), cfgDataview);
        try{
            CfgDataview result = null;
            if(cfgDataview.getId() != null) {
                result = iCfgDataviewSV.getCfgDataviewByPK(cfgDataview.getId());
            }else {
                CfgDataview_Example example = ExampleUtils.parseExample(cfgDataview, CfgDataview_Example.class);
                List<CfgDataview> list = iCfgDataviewSV.getCfgDataviewListByExample(example);
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
    * 搜索一个数据视图
    * @param  cfgDataview
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgDataview") CfgDataview  cfgDataview){
        logger.debug("request : {}",  cfgDataview);
        try{
            CfgDataview result = null;
            if(cfgDataview.getId() != null) {
                result =  iCfgDataviewSV.getCfgDataviewByPK(cfgDataview.getId());
            }else {
                CfgDataview_Example example = ExampleUtils.parseExample( cfgDataview, CfgDataview_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgDataview> list =  iCfgDataviewSV.getCfgDataviewListByExample(example);
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
    * 创建数据视图
    * @param cfgDataview
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgDataview") CfgDataview cfgDataview) {
        logger.debug("request : {}", cfgDataview);
        try {
            ControllerHelper.setDefaultValue(cfgDataview, "id");
            int result = iCfgDataviewSV.create(cfgDataview);
            if(result > 0) {
                return ResultData.success(cfgDataview);
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
    * 批量维护数据视图
    * @param cfgDataviews
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgDataview[] cfgDataviews) {
        logger.debug("request : {}", cfgDataviews);

        try {
            ControllerHelper.setDefaultValue(cfgDataviews, "id");
            ControllerHelper.reorderProperty(cfgDataviews);

            int result = iCfgDataviewSV.batchOperate(cfgDataviews);
            if(result > 0) {
                return ResultData.success(cfgDataviews);
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
    * 更新数据视图
    * @param cfgDataview
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgDataview") CfgDataview cfgDataview) {
        logger.debug("request : {}", cfgDataview);
        try {
            ControllerHelper.setDefaultValue(cfgDataview, "id");
            int result = iCfgDataviewSV.update(cfgDataview);
            if(result > 0) {
                return ResultData.success(cfgDataview);
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
    * 创建或更新数据视图
    * @param cfgDataview
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgDataview") CfgDataview cfgDataview) {
        logger.debug("request : {}", cfgDataview);
        try {
            ControllerHelper.setDefaultValue(cfgDataview, "id");
            int result = iCfgDataviewSV.batchOperate(new CfgDataview[]{cfgDataview});
            if(result > 0) {
                return ResultData.success(cfgDataview);
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
    * 删除数据视图
    * @param cfgDataview
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgDataview") CfgDataview cfgDataview) {
        logger.debug("request : {}", cfgDataview);

        try {
            ControllerHelper.setDefaultValue(cfgDataview, "id");
            int result = iCfgDataviewSV.delete(cfgDataview);
            if(result > 0) {
                return ResultData.success(cfgDataview);
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
 	
	public ICfgDataviewSV getICfgDataviewSV(){
		return iCfgDataviewSV;
	}
	//setter
	public void setICfgDataviewSV(ICfgDataviewSV iCfgDataviewSV){
    	this.iCfgDataviewSV = iCfgDataviewSV;
    }
}
