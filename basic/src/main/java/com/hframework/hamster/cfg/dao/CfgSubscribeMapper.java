package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgSubscribe;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe_Example;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe;
import com.hframework.hamster.cfg.domain.model.CfgSubscribe_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgSubscribeMapper {
    int countByExample(CfgSubscribe_Example example);

    int deleteByExample(CfgSubscribe_Example example);

    int deleteByPrimaryKey(Long cfgSubscribeId);

    int insert(CfgSubscribe record);

    int insertSelective(CfgSubscribe record);

    List<CfgSubscribe> selectByExample(CfgSubscribe_Example example);

    CfgSubscribe selectByPrimaryKey(Long cfgSubscribeId);

    int updateByExampleSelective(@Param("record") CfgSubscribe record, @Param("example") CfgSubscribe_Example example);

    int updateByExample(@Param("record") CfgSubscribe record, @Param("example") CfgSubscribe_Example example);

    int updateByPrimaryKeySelective(CfgSubscribe record);

    int updateByPrimaryKey(CfgSubscribe record);
}