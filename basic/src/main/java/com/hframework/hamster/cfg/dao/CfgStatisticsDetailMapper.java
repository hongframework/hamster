package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail_Example;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail;
import com.hframework.hamster.cfg.domain.model.CfgStatisticsDetail_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgStatisticsDetailMapper {
    int countByExample(CfgStatisticsDetail_Example example);

    int deleteByExample(CfgStatisticsDetail_Example example);

    int deleteByPrimaryKey(Long cfgStatisticsDetailId);

    int insert(CfgStatisticsDetail record);

    int insertSelective(CfgStatisticsDetail record);

    List<CfgStatisticsDetail> selectByExampleWithBLOBs(CfgStatisticsDetail_Example example);

    List<CfgStatisticsDetail> selectByExample(CfgStatisticsDetail_Example example);

    CfgStatisticsDetail selectByPrimaryKey(Long cfgStatisticsDetailId);

    int updateByExampleSelective(@Param("record") CfgStatisticsDetail record, @Param("example") CfgStatisticsDetail_Example example);

    int updateByExampleWithBLOBs(@Param("record") CfgStatisticsDetail record, @Param("example") CfgStatisticsDetail_Example example);

    int updateByExample(@Param("record") CfgStatisticsDetail record, @Param("example") CfgStatisticsDetail_Example example);

    int updateByPrimaryKeySelective(CfgStatisticsDetail record);

    int updateByPrimaryKeyWithBLOBs(CfgStatisticsDetail record);

    int updateByPrimaryKey(CfgStatisticsDetail record);
}