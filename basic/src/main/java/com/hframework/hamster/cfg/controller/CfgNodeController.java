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
import com.hframework.hamster.cfg.domain.model.CfgNode;
import com.hframework.hamster.cfg.domain.model.CfgNode_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeSV;

@Controller
@RequestMapping(value = "/cfg/cfgNode")
public class CfgNodeController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgNodeController.class);

	@Resource
	private ICfgNodeSV iCfgNodeSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示节点列表
     * @param cfgNode
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgNode") CfgNode cfgNode,
                                      @ModelAttribute("example") CfgNode_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgNode, example, pagination);
        try{
            ExampleUtils.parseExample(cfgNode, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgNode> list = iCfgNodeSV.getCfgNodeListByExample(example);
            pagination.setTotalCount(iCfgNodeSV.getCfgNodeCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示节点明细
     * @param cfgNode
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgNode") CfgNode cfgNode){
        logger.debug("request : {},{}", cfgNode.getCfgNodeId(), cfgNode);
        try{
            CfgNode result = null;
            if(cfgNode.getCfgNodeId() != null) {
                result = iCfgNodeSV.getCfgNodeByPK(cfgNode.getCfgNodeId());
            }else {
                CfgNode_Example example = ExampleUtils.parseExample(cfgNode, CfgNode_Example.class);
                List<CfgNode> list = iCfgNodeSV.getCfgNodeListByExample(example);
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
    * 搜索一个节点
    * @param  cfgNode
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgNode") CfgNode  cfgNode){
        logger.debug("request : {}",  cfgNode);
        try{
            CfgNode result = null;
            if(cfgNode.getCfgNodeId() != null) {
                result =  iCfgNodeSV.getCfgNodeByPK(cfgNode.getCfgNodeId());
            }else {
                CfgNode_Example example = ExampleUtils.parseExample( cfgNode, CfgNode_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgNode> list =  iCfgNodeSV.getCfgNodeListByExample(example);
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
    * 创建节点
    * @param cfgNode
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgNode") CfgNode cfgNode) {
        logger.debug("request : {}", cfgNode);
        try {
            ControllerHelper.setDefaultValue(cfgNode, "cfgNodeId");
            int result = iCfgNodeSV.create(cfgNode);
            if(result > 0) {
                return ResultData.success(cfgNode);
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
    * 批量维护节点
    * @param cfgNodes
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgNode[] cfgNodes) {
        logger.debug("request : {}", cfgNodes);

        try {
            ControllerHelper.setDefaultValue(cfgNodes, "cfgNodeId");
            ControllerHelper.reorderProperty(cfgNodes);

            int result = iCfgNodeSV.batchOperate(cfgNodes);
            if(result > 0) {
                return ResultData.success(cfgNodes);
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
    * 更新节点
    * @param cfgNode
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgNode") CfgNode cfgNode) {
        logger.debug("request : {}", cfgNode);
        try {
            ControllerHelper.setDefaultValue(cfgNode, "cfgNodeId");
            int result = iCfgNodeSV.update(cfgNode);
            if(result > 0) {
                return ResultData.success(cfgNode);
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
    * 创建或更新节点
    * @param cfgNode
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgNode") CfgNode cfgNode) {
        logger.debug("request : {}", cfgNode);
        try {
            ControllerHelper.setDefaultValue(cfgNode, "cfgNodeId");
            int result = iCfgNodeSV.batchOperate(new CfgNode[]{cfgNode});
            if(result > 0) {
                return ResultData.success(cfgNode);
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
    * 删除节点
    * @param cfgNode
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgNode") CfgNode cfgNode) {
        logger.debug("request : {}", cfgNode);

        try {
            ControllerHelper.setDefaultValue(cfgNode, "cfgNodeId");
            int result = iCfgNodeSV.delete(cfgNode);
            if(result > 0) {
                return ResultData.success(cfgNode);
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
 	
	public ICfgNodeSV getICfgNodeSV(){
		return iCfgNodeSV;
	}
	//setter
	public void setICfgNodeSV(ICfgNodeSV iCfgNodeSV){
    	this.iCfgNodeSV = iCfgNodeSV;
    }
}
