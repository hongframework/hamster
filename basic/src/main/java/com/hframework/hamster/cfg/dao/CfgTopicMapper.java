package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgTopic;
import com.hframework.hamster.cfg.domain.model.CfgTopic_Example;
import com.hframework.hamster.cfg.domain.model.CfgTopic;
import com.hframework.hamster.cfg.domain.model.CfgTopic_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgTopicMapper {
    int countByExample(CfgTopic_Example example);

    int deleteByExample(CfgTopic_Example example);

    int deleteByPrimaryKey(Long cfgTopicId);

    int insert(CfgTopic record);

    int insertSelective(CfgTopic record);

    List<CfgTopic> selectByExample(CfgTopic_Example example);

    CfgTopic selectByPrimaryKey(Long cfgTopicId);

    int updateByExampleSelective(@Param("record") CfgTopic record, @Param("example") CfgTopic_Example example);

    int updateByExample(@Param("record") CfgTopic record, @Param("example") CfgTopic_Example example);

    int updateByPrimaryKeySelective(CfgTopic record);

    int updateByPrimaryKey(CfgTopic record);
}