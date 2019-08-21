package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgJob;
import com.hframework.hamster.cfg.domain.model.CfgJob_Example;
import com.hframework.hamster.cfg.domain.model.CfgJob;
import com.hframework.hamster.cfg.domain.model.CfgJob_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgJobMapper {
    int countByExample(CfgJob_Example example);

    int deleteByExample(CfgJob_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgJob record);

    int insertSelective(CfgJob record);

    List<CfgJob> selectByExample(CfgJob_Example example);

    CfgJob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgJob record, @Param("example") CfgJob_Example example);

    int updateByExample(@Param("record") CfgJob record, @Param("example") CfgJob_Example example);

    int updateByPrimaryKeySelective(CfgJob record);

    int updateByPrimaryKey(CfgJob record);
}