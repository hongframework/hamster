package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic_Example;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsTopic_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgStatisticsTopicMapper {
    int countByExample(CfgStatisticsTopic_Example example);

    int deleteByExample(CfgStatisticsTopic_Example example);

    int deleteByPrimaryKey(Long cfgStatisticsTopicId);

    int insert(CfgStatisticsTopic record);

    int insertSelective(CfgStatisticsTopic record);

    List<CfgStatisticsTopic> selectByExample(CfgStatisticsTopic_Example example);

    CfgStatisticsTopic selectByPrimaryKey(Long cfgStatisticsTopicId);

    int updateByExampleSelective(@Param("record") CfgStatisticsTopic record, @Param("example") CfgStatisticsTopic_Example example);

    int updateByExample(@Param("record") CfgStatisticsTopic record, @Param("example") CfgStatisticsTopic_Example example);

    int updateByPrimaryKeySelective(CfgStatisticsTopic record);

    int updateByPrimaryKey(CfgStatisticsTopic record);
}