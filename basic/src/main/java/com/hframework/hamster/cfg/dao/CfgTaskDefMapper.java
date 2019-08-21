package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgTaskDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskDef_Example;
import com.hframework.hamster.cfg.domain.model.CfgTaskDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskDef_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgTaskDefMapper {
    int countByExample(CfgTaskDef_Example example);

    int deleteByExample(CfgTaskDef_Example example);

    int deleteByPrimaryKey(Long cfgTaskDefId);

    int insert(CfgTaskDef record);

    int insertSelective(CfgTaskDef record);

    List<CfgTaskDef> selectByExample(CfgTaskDef_Example example);

    CfgTaskDef selectByPrimaryKey(Long cfgTaskDefId);

    int updateByExampleSelective(@Param("record") CfgTaskDef record, @Param("example") CfgTaskDef_Example example);

    int updateByExample(@Param("record") CfgTaskDef record, @Param("example") CfgTaskDef_Example example);

    int updateByPrimaryKeySelective(CfgTaskDef record);

    int updateByPrimaryKey(CfgTaskDef record);
}