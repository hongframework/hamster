package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef_Example;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeDef_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgNodeDefMapper {
    int countByExample(CfgNodeDef_Example example);

    int deleteByExample(CfgNodeDef_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgNodeDef record);

    int insertSelective(CfgNodeDef record);

    List<CfgNodeDef> selectByExample(CfgNodeDef_Example example);

    CfgNodeDef selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgNodeDef record, @Param("example") CfgNodeDef_Example example);

    int updateByExample(@Param("record") CfgNodeDef record, @Param("example") CfgNodeDef_Example example);

    int updateByPrimaryKeySelective(CfgNodeDef record);

    int updateByPrimaryKey(CfgNodeDef record);
}