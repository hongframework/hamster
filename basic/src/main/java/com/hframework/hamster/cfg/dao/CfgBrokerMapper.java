package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgBroker;
import com.hframework.hamster.cfg.domain.model.CfgBroker_Example;
import com.hframework.hamster.cfg.domain.model.CfgBroker;
import com.hframework.hamster.cfg.domain.model.CfgBroker_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgBrokerMapper {
    int countByExample(CfgBroker_Example example);

    int deleteByExample(CfgBroker_Example example);

    int deleteByPrimaryKey(Long cfgBrokerId);

    int insert(CfgBroker record);

    int insertSelective(CfgBroker record);

    List<CfgBroker> selectByExample(CfgBroker_Example example);

    CfgBroker selectByPrimaryKey(Long cfgBrokerId);

    int updateByExampleSelective(@Param("record") CfgBroker record, @Param("example") CfgBroker_Example example);

    int updateByExample(@Param("record") CfgBroker record, @Param("example") CfgBroker_Example example);

    int updateByPrimaryKeySelective(CfgBroker record);

    int updateByPrimaryKey(CfgBroker record);
}