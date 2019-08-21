package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgSubscribeData;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData_Example;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeData_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgSubscribeDataMapper {
    int countByExample(CfgSubscribeData_Example example);

    int deleteByExample(CfgSubscribeData_Example example);

    int deleteByPrimaryKey(Long cfgSubscribeDataId);

    int insert(CfgSubscribeData record);

    int insertSelective(CfgSubscribeData record);

    List<CfgSubscribeData> selectByExample(CfgSubscribeData_Example example);

    CfgSubscribeData selectByPrimaryKey(Long cfgSubscribeDataId);

    int updateByExampleSelective(@Param("record") CfgSubscribeData record, @Param("example") CfgSubscribeData_Example example);

    int updateByExample(@Param("record") CfgSubscribeData record, @Param("example") CfgSubscribeData_Example example);

    int updateByPrimaryKeySelective(CfgSubscribeData record);

    int updateByPrimaryKey(CfgSubscribeData record);
}