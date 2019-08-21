package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgJobAttr;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr_Example;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr;
import com.hframework.hamster.cfg.domain.model.CfgJobAttr_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgJobAttrMapper {
    int countByExample(CfgJobAttr_Example example);

    int deleteByExample(CfgJobAttr_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgJobAttr record);

    int insertSelective(CfgJobAttr record);

    List<CfgJobAttr> selectByExample(CfgJobAttr_Example example);

    CfgJobAttr selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgJobAttr record, @Param("example") CfgJobAttr_Example example);

    int updateByExample(@Param("record") CfgJobAttr record, @Param("example") CfgJobAttr_Example example);

    int updateByPrimaryKeySelective(CfgJobAttr record);

    int updateByPrimaryKey(CfgJobAttr record);
}