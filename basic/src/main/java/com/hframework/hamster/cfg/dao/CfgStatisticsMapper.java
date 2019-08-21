package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgStatistics;
import com.hframework.hamster.cfg.domain.model.CfgStatistics_Example;
import com.hframework.hamster.cfg.domain.model.CfgStatistics;
import com.hframework.hamster.cfg.domain.model.CfgStatistics_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgStatisticsMapper {
    int countByExample(CfgStatistics_Example example);

    int deleteByExample(CfgStatistics_Example example);

    int deleteByPrimaryKey(Long cfgStatisticsId);

    int insert(CfgStatistics record);

    int insertSelective(CfgStatistics record);

    List<CfgStatistics> selectByExample(CfgStatistics_Example example);

    CfgStatistics selectByPrimaryKey(Long cfgStatisticsId);

    int updateByExampleSelective(@Param("record") CfgStatistics record, @Param("example") CfgStatistics_Example example);

    int updateByExample(@Param("record") CfgStatistics record, @Param("example") CfgStatistics_Example example);

    int updateByPrimaryKeySelective(CfgStatistics record);

    int updateByPrimaryKey(CfgStatistics record);
}