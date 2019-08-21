package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgLabel;
import com.hframework.hamster.cfg.domain.model.CfgLabel_Example;
import com.hframework.hamster.cfg.domain.model.CfgLabel;
import com.hframework.hamster.cfg.domain.model.CfgLabel_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgLabelMapper {
    int countByExample(CfgLabel_Example example);

    int deleteByExample(CfgLabel_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgLabel record);

    int insertSelective(CfgLabel record);

    List<CfgLabel> selectByExample(CfgLabel_Example example);

    CfgLabel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgLabel record, @Param("example") CfgLabel_Example example);

    int updateByExample(@Param("record") CfgLabel record, @Param("example") CfgLabel_Example example);

    int updateByPrimaryKeySelective(CfgLabel record);

    int updateByPrimaryKey(CfgLabel record);
}