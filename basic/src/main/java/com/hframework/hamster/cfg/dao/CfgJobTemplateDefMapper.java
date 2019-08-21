package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef_Example;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef;
import com.hframework.hamster.cfg.domain.model.CfgJobTemplateDef_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgJobTemplateDefMapper {
    int countByExample(CfgJobTemplateDef_Example example);

    int deleteByExample(CfgJobTemplateDef_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgJobTemplateDef record);

    int insertSelective(CfgJobTemplateDef record);

    List<CfgJobTemplateDef> selectByExample(CfgJobTemplateDef_Example example);

    CfgJobTemplateDef selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgJobTemplateDef record, @Param("example") CfgJobTemplateDef_Example example);

    int updateByExample(@Param("record") CfgJobTemplateDef record, @Param("example") CfgJobTemplateDef_Example example);

    int updateByPrimaryKeySelective(CfgJobTemplateDef record);

    int updateByPrimaryKey(CfgJobTemplateDef record);
}