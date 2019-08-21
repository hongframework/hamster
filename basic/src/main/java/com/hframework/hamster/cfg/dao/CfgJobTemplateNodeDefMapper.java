package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef_Example;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateNodeDef_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgJobTemplateNodeDefMapper {
    int countByExample(CfgJobTemplateNodeDef_Example example);

    int deleteByExample(CfgJobTemplateNodeDef_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgJobTemplateNodeDef record);

    int insertSelective(CfgJobTemplateNodeDef record);

    List<CfgJobTemplateNodeDef> selectByExample(CfgJobTemplateNodeDef_Example example);

    CfgJobTemplateNodeDef selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgJobTemplateNodeDef record, @Param("example") CfgJobTemplateNodeDef_Example example);

    int updateByExample(@Param("record") CfgJobTemplateNodeDef record, @Param("example") CfgJobTemplateNodeDef_Example example);

    int updateByPrimaryKeySelective(CfgJobTemplateNodeDef record);

    int updateByPrimaryKey(CfgJobTemplateNodeDef record);
}