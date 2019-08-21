package com.hframework.datacenter.bootstrap;

import com.google.common.base.Enums;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Grouper;
import com.hframework.common.util.collect.bean.Mapper;
import com.hframework.common.util.file.FileUtils;
import com.hframework.common.util.message.XmlUtils;
import com.hframework.web.config.bean.DataSet;
import com.hframework.web.config.bean.Module;
import com.hframework.web.config.bean.component.Event;
import com.hframework.web.config.bean.dataset.*;
import com.hframework.web.config.bean.module.Component;
import com.hframework.web.config.bean.module.Page;
import com.hframework.web.config.bean.module.SetValue;
import com.hframework.web.context.WebContextHelper;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateDefSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgJobTemplateNodeDefSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeAttrDefSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeDefSV;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@org.springframework.stereotype.Component
public class DynamicWebContextLoader implements ApplicationContextAware, ServletContextAware {
    private static final Logger logger = LoggerFactory.getLogger(DynamicWebContextLoader.class);

    @Autowired
    private ICfgNodeDefSV cfgNodeDefSV;
    @Autowired
    private ICfgNodeAttrDefSV cfgNodeAttrDefSV;
    @Autowired
    private ICfgJobTemplateDefSV cfgJobTemplateDefSV;
    @Autowired
    private ICfgJobTemplateNodeDefSV cfgJobTemplateNodeDefSV;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            logger.info("start dynamic generate ...");
            WebContextHelper contextHelper = new WebContextHelper();
            writeDataSetFile(contextHelper);
            writeModuleFile(contextHelper, "dyn");
        } catch (Exception e) {
            logger.error("start dynamic generate error, {}", ExceptionUtils.getFullStackTrace(e));
        }

    }
    public enum EntityPageSet{

        SINGLE_MGR("_mgr","管理","qlist",new String[]{"qForm"}),
        SINGLE_CREATE("_create","创建","create",new String[]{}),
        SINGLE_EDIT("_edit","修改","edit",new String[]{}),
        SINGLE_DETAIL("_detail","明细","edit",new String[]{}),

        COMPLEX_MGR("_mgr","管理","qlist",new String[]{"qForm"}),
        COMPLEX_CREATE("_create","添加","cComb",new String[]{"cForm","cList"}),
        COMPLEX_EDIT("_edit","修改","eComb",new String[]{"eForm","eList"}),
        COMPLEX_DETAIL("_detail","查看","dComb",new String[]{"dForm","qList"}),

        CATEGORY_MGR("_mgr","管理","editByCategory",new String[]{});

        String id;
        String name;
        String pageTemplate;
        String[] component;

        EntityPageSet(String id, String name, String pageTemplate, String[] component) {
            this.id = id;
            this.name = name;
            this.pageTemplate = pageTemplate;
            this.component = component;
        }
    }
    private void writeModuleFile(WebContextHelper contextHelper, String moduleCode) throws Exception {
        logger.info("start dynamic generate page...");
        Module module = new Module();
        module.setCode(moduleCode);
        List<Page> pages = new ArrayList<Page>();
        module.setPageList(pages);
        List<CfgJobTemplateDef> jobTemplates = cfgJobTemplateDefSV.getCfgJobTemplateDefAll();
        List<CfgJobTemplateNodeDef> cfgJobTemplateNodeDefAll = cfgJobTemplateNodeDefSV.getCfgJobTemplateNodeDefAll();
        List<CfgNodeDef> nodes = cfgNodeDefSV.getCfgNodeDefAll();
        Map<Long, List<CfgJobTemplateNodeDef>> jobNodesMap = CollectionUtils.group(cfgJobTemplateNodeDefAll, new Grouper<Long, CfgJobTemplateNodeDef>() {
            @Override
            public <K> K groupKey(CfgJobTemplateNodeDef cfgJobTemplateNodeDef) {
                return (K) cfgJobTemplateNodeDef.getJobTemplateId();
            }
        });
        Map<Long, CfgNodeDef> nodeMap = CollectionUtils.convert(nodes, new Mapper<Long, CfgNodeDef>() {
            @Override
            public <K> K getKey(CfgNodeDef cfgNodeDef) {
                return (K) cfgNodeDef.getId();
            }
        });
        for (CfgJobTemplateDef jobTemplate : jobTemplates) {
            if(jobTemplate.getStatus() != (byte)1) {
                continue;
            }


            //1. 添加管理页面
            Page page = new Page();
            page.setId("dyn_" + jobTemplate.getCode() + "_mgr");
            page.setName(jobTemplate.getName()+ "管理");
            page.setPageTemplate("qlist");
            page.setDataSet("cfg/cfg_job");
            page.setSubDataSets("");
            page.setComponentList(new ArrayList<Component>());
            Component qForm = new Component();
            qForm.setDataSet("cfg/cfg_job_DS4Q");
            qForm.setTitle(jobTemplate.getName() + "查询");
            qForm.setId("qForm");
            page.getComponentList().add(qForm);
            Component qList = new Component();
            qList.setDataSet("cfg/cfg_job");
            qList.setTitle(jobTemplate.getName() + "列表");
            qList.setId("qList");
            qList.setEventExtend("false");
            qList.setSetValueList(Lists.<SetValue>newArrayList(new SetValue()));
            qList.getSetValueList().get(0).setField("job_template_id");
            qList.getSetValueList().get(0).setValue(String.valueOf(jobTemplate.getId()));
            qList.getSetValueList().get(0).setMethod("absolute");
            qList.setEventList(Lists.newArrayList(XmlUtils.readValue("<event name=\"goto.create\" description=\"新建\">\n" +
            "        <attach anchor=\"BOFC\">\n" +
            "            <appendElement type=\"button\" param='{btnclass:\"btn-primary\",btnText:\" 新 建 \"}'></appendElement>\n" +
            "        </attach>\n" +
            "        <effect type=\"pageFwd\" action='" + "/dyn/dyn_" + jobTemplate.getCode() + "_create.html" + "' isStack=\"true\"></effect>\n" +
            "        <effect type=\"component.reload\"></effect>\n" +
            "    </event>", Event.class) ,  XmlUtils.readValue(
            "<event name=\"goto.edit\" description=\"修改\">\n" +
            "        <attach anchor=\"EOFR\">\n" +
            "            <appendElement type=\"icon\" param='{fillclass:\"btn-success\",iconclass:\"icon-edit\"}'></appendElement>\n" +
            "        </attach>\n" +
            "        <source scope=\"CROW\" param=\"id={id}\"></source>\n" +
            "        <effect type=\"pageFwd\" action='/dyn/dyn_" + jobTemplate.getCode() + "_edit.html' isStack=\"true\"></effect>\n" +
            "        <effect type=\"component.reload\"></effect>\n" +
            "    </event>", Event.class)));
            page.getComponentList().add(qList);
            module.getPageList().add(page);

            //2. 添加新建页面
            page = new Page();
            page.setId("dyn_" + jobTemplate.getCode() + "_create");
            page.setName(jobTemplate.getName()+ "添加");
            page.setPageTemplate("allInOne");
            page.setDataSet("cfg/cfg_job");
            page.setRelPage("dyn_" + jobTemplate.getCode() + "_mgr");
            page.setComponentList(new ArrayList<Component>());
            Component component = new Component();
            component.setId("cForm");
            component.setDataSet("cfg/cfg_job");
            component.setShowTitle("false");
            component.setTitle("任务基本信息");
            component.setSetValueList(Lists.<SetValue>newArrayList(new SetValue()));
            component.getSetValueList().get(0).setField("job_template_id");
            component.getSetValueList().get(0).setValue(String.valueOf(jobTemplate.getId()));
            component.getSetValueList().get(0).setMethod("absolute");

            page.getComponentList().add(component);
            Set<String> componentIds = new LinkedHashSet<>();
            for (CfgJobTemplateNodeDef cfgJobTemplateNodeDef : jobNodesMap.get(jobTemplate.getId())) {
                Long nodeId = cfgJobTemplateNodeDef.getNodeId();
                CfgNodeDef nodeDef = nodeMap.get(nodeId);
                component = new Component();
                component.setId(cfgJobTemplateNodeDef.getMutix() == (byte)1 ? "cList" : "cForm");
                component.setDataSet(moduleCode + "/" + moduleCode + "_" + nodeDef.getCode());
                component.setShowTitle("false");
                component.setTitle(nodeDef.getName());
                component.setEventExtend("absolute_false");
                component.setEventList(Lists.newArrayList(
                        XmlUtils.readValue("<event rel=\"#EOFR.row.insert\" />", Event.class),
                        XmlUtils.readValue("<event rel=\"#EOFR.row.down\" />", Event.class),
                        XmlUtils.readValue("<event rel=\"#EOFR.row.up\" />", Event.class),
                        XmlUtils.readValue(
                                "<event name=\"row.delete\" description=\"删除行\">\n" +
                                "        <attach anchor=\"EOFR\">\n" +
                                "            <appendElement type=\"icon\" param='{fillclass:\"btn-danger\",iconclass:\"icon-trash\"}'></appendElement>\n" +
                                "        </attach>\n" +
                                "        <source scope=\"CROW\" param='id={sourceColumn#id}&amp;id={targetColumn#id}'></source>\n" +
                                "        <effect type=\"confirm\" content=\"是否删除 &lt;span style=&quot;color:red&quot;&gt;该行数据&lt;/span&gt; ?\"></effect>\n" +
                                "        <effect type=\"ajaxSubmit\" action=\"" + "/extend/cfgJobAttr/delete.json"  + "\"></effect>\n" +
                                "        <effect type=\"component.row.delete\"></effect>\n" +
                                "    </event>", Event.class)));



                page.getComponentList().add(component);
                componentIds.add(component.getId());
            }
            component = new Component();
            component.setId("qList");
            component.setDataSet("SYSTEM_EMPTY_DATASET");
            component.setShowTitle("false");
            component.setEventList(Lists.newArrayList(XmlUtils.readValue(
                    "<event name=\"submit\" description=\"提交\">\n" +
                    "        <attach anchor=\"BOFC\">\n" +
                    "          <appendElement type=\"button\" param='{btnclass:\"btn-primary\",btnText:\" 提 交 \"}'></appendElement>\n" +
                    "        </attach>\n" +
                    "        <source scope=\"EOC\" param=\"thisForm\"></source>\n" +
                    "        <effect type=\"ajaxSubmitByJson\" action=\"ajaxSubmits.json\" target-id=\"" + Joiner.on(",").join(componentIds) + "\"></effect>\n" +
                    "        <effect type=\"pageFwd\" action='/dyn/dyn_" + jobTemplate.getCode() + "_mgr.html' isStack=\"true\"></effect>\n" +
                    "      </event>", Event.class)));
            page.getComponentList().add(component);
            page.setExtendScript("try {document.write(\"<script language=javascript src='/static/js/dyn/common.js'><\\/script>\");}catch(err){}");
            module.getPageList().add(page);


            //3. 添加修改页面
            page = new Page();
            page.setId("dyn_" + jobTemplate.getCode() + "_edit");
            page.setName(jobTemplate.getName()+ "修改");
            page.setPageTemplate("allInOne");
            page.setDataSet("cfg/cfg_job");
            page.setRelPage("dyn_" + jobTemplate.getCode() + "_mgr");
            page.setComponentList(new ArrayList<Component>());
            component = new Component();
            component.setId("eForm");
            component.setDataSet("cfg/cfg_job");
            component.setShowTitle("false");
            component.setTitle("任务基本信息");
            page.getComponentList().add(component);
            componentIds = new LinkedHashSet<>();
            for (CfgJobTemplateNodeDef cfgJobTemplateNodeDef : jobNodesMap.get(jobTemplate.getId())) {
                Long nodeId = cfgJobTemplateNodeDef.getNodeId();
                CfgNodeDef nodeDef = nodeMap.get(nodeId);
                component = new Component();
                component.setId(cfgJobTemplateNodeDef.getMutix() == (byte)1 ? "eList" : "eForm");
                component.setDataSet(moduleCode + "/" + moduleCode + "_" + nodeDef.getCode());
                component.setShowTitle("false");
                component.setTitle(nodeDef.getName());
                component.setEventExtend("absolute_false");
                component.setEventList(Lists.newArrayList(
                        XmlUtils.readValue("<event rel=\"#EOFR.row.insert\" />", Event.class),
                        XmlUtils.readValue("<event rel=\"#EOFR.row.down\" />", Event.class),
                        XmlUtils.readValue("<event rel=\"#EOFR.row.up\" />", Event.class),
                        XmlUtils.readValue(
                                "<event name=\"row.delete\" description=\"删除行\">\n" +
                                        "        <attach anchor=\"EOFR\">\n" +
                                        "            <appendElement type=\"icon\" param='{fillclass:\"btn-danger\",iconclass:\"icon-trash\"}'></appendElement>\n" +
                                        "        </attach>\n" +
                                        "        <source scope=\"CROW\" param='id={sourceColumn#id}&amp;id={targetColumn#id}'></source>\n" +
                                        "        <effect type=\"confirm\" content=\"是否删除 &lt;span style=&quot;color:red&quot;&gt;该行数据 &lt;/span&gt; ?\"></effect>\n" +
                                        "        <effect type=\"ajaxSubmit\" action=\"" + "/extend/cfgJobAttr/delete.json"  + "\"></effect>\n" +
                                        "        <effect type=\"component.row.delete\"></effect>\n" +
                                        "    </event>", Event.class)));
                page.getComponentList().add(component);
                componentIds.add(component.getId());
            }
            component = new Component();
            component.setId("qList");
            component.setDataSet("SYSTEM_EMPTY_DATASET");
            component.setShowTitle("false");
            component.setEventList(Lists.newArrayList(XmlUtils.readValue("<event name=\"submit\" description=\"提交\">\n" +
                    "        <attach anchor=\"BOFC\">\n" +
                    "          <appendElement type=\"button\" param='{btnclass:\"btn-primary\",btnText:\" 提 交 \"}'></appendElement>\n" +
                    "        </attach>\n" +
                    "        <source scope=\"EOC\" param=\"thisForm\"></source>\n" +
                    "        <effect type=\"ajaxSubmitByJson\" action=\"ajaxSubmits.json\" target-id=\"" + Joiner.on(",").join(componentIds) + "\"></effect>\n" +
                    "        <effect type=\"pageFwd\" action='/dyn/dyn_" + jobTemplate.getCode() + "_mgr.html' isStack=\"true\"></effect>\n" +
                    "      </event>", Event.class)));
            page.getComponentList().add(component);
            page.setExtendScript("document.write(\"<script language=javascript src='/static/js/dyn/common.js'><\\/script>\");");
            module.getPageList().add(page);

            //4. 添加明细页面
            page = new Page();
            page.setId("dyn_" + jobTemplate.getCode() + "_detail");
            page.setName(jobTemplate.getName()+ "查看");
            page.setPageTemplate("allInOne");
            page.setDataSet("cfg/cfg_job");
            page.setRelPage("dyn_" + jobTemplate.getCode() + "_mgr");
            page.setComponentList(new ArrayList<Component>());
            component = new Component();
            component.setId("dForm");
            component.setDataSet("cfg/cfg_job");
            component.setShowTitle("false");
            component.setTitle("任务基本信息");
            page.getComponentList().add(component);
            for (CfgJobTemplateNodeDef cfgJobTemplateNodeDef : jobNodesMap.get(jobTemplate.getId())) {
                Long nodeId = cfgJobTemplateNodeDef.getNodeId();
                CfgNodeDef nodeDef = nodeMap.get(nodeId);
                component = new Component();
                component.setId(cfgJobTemplateNodeDef.getMutix() == (byte)1 ? "qList" : "dForm");
                component.setDataSet(moduleCode + "/" + moduleCode + "_" + nodeDef.getCode());
                component.setShowTitle("false");
                component.setTitle(nodeDef.getName());
                page.getComponentList().add(component);
            }
            module.getPageList().add(page);
        }

        String moduleString = XmlUtils.writeValueAsString(module);

        URL fileResource = XmlUtils.class.getResource("/" + contextHelper.programConfigModuleDir);

        String filePath = fileResource.getPath() + File.separator + module.getCode() + ".xml";
        FileUtils.writeFile(filePath, moduleString);

        logger.info("finish dynamic generate page...");
    }

    private Page getPage(EntityPageSet entityPage, String moduleCode, String rootEntityCode, String rootEntityName, String[] relEntityCodes, String relPageCode) {
        Page page = new Page();

        page.setId(rootEntityCode + entityPage.id);
        page.setName(rootEntityName + entityPage.name);
        page.setPageTemplate(entityPage.pageTemplate);
        page.setDataSet(moduleCode + "/" + rootEntityCode);
        page.setRelPage(relPageCode);
        page.setComponentList(new ArrayList<com.hframework.web.config.bean.module.Component>());
        String[] components = entityPage.component;
        for (String componentId : components) {
            com.hframework.web.config.bean.module.Component component = new com.hframework.web.config.bean.module.Component();
            component.setId(componentId);
            if(componentId.equals("qForm")) {
                component.setDataSet(moduleCode + "/" + rootEntityCode + "_DS4Q");
            }else if(componentId.equals("cList") && relEntityCodes != null) {
                component.setDataSet(moduleCode + "/" + relEntityCodes[0]);
            }else if(componentId.equals("eList") && relEntityCodes != null) {
                component.setDataSet(moduleCode + "/" + relEntityCodes[0]);
            }else if(componentId.equals("qList") && relEntityCodes != null) {
                component.setDataSet(moduleCode + "/" + relEntityCodes[0]);
            }else {
                component.setDataSet(moduleCode + "/" + rootEntityCode);
            }
            page.getComponentList().add(component);
        }

        return page;
    }

    private void writeDataSetFile(WebContextHelper contextHelper) throws Exception {
        logger.info("start dynamic generate data set...");
        List<CfgNodeDef> nodes = cfgNodeDefSV.getCfgNodeDefAll();
        List<CfgNodeAttrDef> cfgNodeAttrDefAll = cfgNodeAttrDefSV.getCfgNodeAttrDefAll();
        Map<Long, List<CfgNodeAttrDef>> nodeAttrsMap = CollectionUtils.group(cfgNodeAttrDefAll, new Grouper<Long, CfgNodeAttrDef>() {
            @Override
            public <K> K groupKey(CfgNodeAttrDef cfgNodeAttrDef) {
                return (K) cfgNodeAttrDef.getNodeId();
            }
        });

        for (CfgNodeDef node : nodes) {
            if(node.getStatus() != (byte)1) {
                continue;
            }
            DataSet dataSet = new DataSet();
            dataSet.setModule("dyn");
            dataSet.setCode("dyn_" + node.getCode());
            Entity entity = new Entity();
            entity.setText("cfg_job_attr");
            entity.setCode("attr_code");
            entity.setValue("attr_val");
            entity.setModule("cfg");

            dataSet.setEntityList(Lists.newArrayList(entity));
            dataSet.setName(node.getName());


            dataSet.setFields(new Fields());
            dataSet.getFields().setFieldList(new ArrayList<Field>());
            List<Field> fieldList = dataSet.getFields().getFieldList();
            List<Field> hiddenFields = new ArrayList();

            List<CfgNodeAttrDef> attrs = nodeAttrsMap.get(node.getId());

            for (CfgNodeAttrDef attr : attrs) {
                if(attr.getStatus() != (byte)1) {
                    continue;
                }
                Field field = new Field();
//                field.setCode("attr_code." + attr.getCode());
                field.setCode(attr.getCode());
                field.setName(attr.getName());
                switch (Enums.getIfPresent(AttrType.class, attr.getType()).get()) {
                    case date:
                        field.setEditType("datetime");
                        break;
                    case text:
                        field.setEditType("textarea");
                        break;
                    case list:
                        field.setEditType("muti-select");
                        break;
//                    case options:
//                        field.setEditType("select");
//                        field.setEnumClass(new EnumClass());
//                        field.getEnumClass().setCode(attr.getRelatDict());
//                        break;
//                    case relate:
//                        field.setEditType("select");
//                        field.setRel(new Rel());
//                        field.getRel().setEntityCode(attr.getRelatAttr());
//                        break;
                    default:
                        field.setEditType("input");
                }
                if(StringUtils.isNotBlank(attr.getRelatDict())) {
                    if(!"muti-select".equals(field.getEditType())) {
                        field.setEditType("select");
                    }

                    field.setRel(new Rel());
                    if(attr.getRelatDict().trim().toUpperCase().startsWith("URL:")) {
                        field.getRel().setUrl(attr.getRelatDict().trim().substring(4));
                    }else {
                        field.getRel().setEntityCode(attr.getRelatDict().trim());
                    }

                    if(StringUtils.isNotBlank(attr.getRelatAttr())) {
                        field.getRel().setRelField(attr.getRelatAttr().trim());
                        field.getRel().setAddByGlobal("true");
                        field.getRel().setRelScope("page");
                    }
                }else if(StringUtils.isNotBlank(attr.getRelatAttr())) {
                    field.setRel(new Rel());
                    field.getRel().setRelField(attr.getRelatAttr().trim());
                    field.setRelField(attr.getRelatAttr().trim());
                }


                field.setTipinfo(attr.getDescription());
                if(attr.getNecessary() == (byte)1) {
                    field.setNotNull("true");
                }
                fieldList.add(field);

                field = new Field();
                field.setCode(attr.getCode() + "#attr_id");
                field.setDefaultValue(String.valueOf(attr.getId()));
                field.setEditType("hidden");
                hiddenFields.add(field);

                field = new Field();
                field.setCode(attr.getCode() + "#id");
                field.setEditType("hidden");
                hiddenFields.add(field);
            }

            fieldList.addAll(hiddenFields);

            String dataSetString = XmlUtils.writeValueAsString(dataSet);

            URL fileResource = XmlUtils.class.getResource("/" + contextHelper.programConfigDataSetDir);

            String filePath = fileResource.getPath() + File.separator + dataSet.getCode() + ".xml";
            FileUtils.writeFile(filePath, dataSetString);
        }
        logger.info("finish dynamic generate data set...");
    }

    public static enum AttrType{
        number,string,date,text,options,relate,list;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
