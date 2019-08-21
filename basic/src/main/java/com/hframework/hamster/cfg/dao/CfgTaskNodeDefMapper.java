package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef_Example;
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgTaskNodeDef_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgTaskNodeDefMapper {
    int countByExample(CfgTaskNodeDef_Example example);

    int deleteByExample(CfgTaskNodeDef_Example example);

    int deleteByPrimaryKey(Long cfgTaskNodeDefId);

    int insert(CfgTaskNodeDef record);

    int insertSelective(CfgTaskNodeDef record);

    List<CfgTaskNodeDef> selectByExample(CfgTaskNodeDef_Example example);

    CfgTaskNodeDef selectByPrimaryKey(Long cfgTaskNodeDefId);

    int updateByExampleSelective(@Param("record") CfgTaskNodeDef record, @Param("example") CfgTaskNodeDef_Example example);

    int updateByExample(@Param("record") CfgTaskNodeDef record, @Param("example") CfgTaskNodeDef_Example example);

    int updateByPrimaryKeySelective(CfgTaskNodeDef record);

    int updateByPrimaryKey(CfgTaskNodeDef record);
}