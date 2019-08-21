package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgTaskInst;
import com.hframework.hamster.cfg.domain.model.CfgTaskInst_Example;
import com.hframework.hamster.cfg.domain.model.CfgTaskInst;
import com.hframework.hamster.cfg.domain.model.CfgTaskInst_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgTaskInstMapper {
    int countByExample(CfgTaskInst_Example example);

    int deleteByExample(CfgTaskInst_Example example);

    int deleteByPrimaryKey(Long cfgTaskInstId);

    int insert(CfgTaskInst record);

    int insertSelective(CfgTaskInst record);

    List<CfgTaskInst> selectByExample(CfgTaskInst_Example example);

    CfgTaskInst selectByPrimaryKey(Long cfgTaskInstId);

    int updateByExampleSelective(@Param("record") CfgTaskInst record, @Param("example") CfgTaskInst_Example example);

    int updateByExample(@Param("record") CfgTaskInst record, @Param("example") CfgTaskInst_Example example);

    int updateByPrimaryKeySelective(CfgTaskInst record);

    int updateByPrimaryKey(CfgTaskInst record);
}