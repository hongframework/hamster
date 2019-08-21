package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef_Example;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef;
import com.hframework.hamster.cfg.domain.model.CfgNodeAttrDef_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgNodeAttrDefMapper {
    int countByExample(CfgNodeAttrDef_Example example);

    int deleteByExample(CfgNodeAttrDef_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgNodeAttrDef record);

    int insertSelective(CfgNodeAttrDef record);

    List<CfgNodeAttrDef> selectByExample(CfgNodeAttrDef_Example example);

    CfgNodeAttrDef selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgNodeAttrDef record, @Param("example") CfgNodeAttrDef_Example example);

    int updateByExample(@Param("record") CfgNodeAttrDef record, @Param("example") CfgNodeAttrDef_Example example);

    int updateByPrimaryKeySelective(CfgNodeAttrDef record);

    int updateByPrimaryKey(CfgNodeAttrDef record);
}