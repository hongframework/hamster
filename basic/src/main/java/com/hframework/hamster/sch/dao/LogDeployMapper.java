package com.hframework.hamster.sch.dao;

import com.hframework.hamster.sch.domain.model.LogDeploy;
import com.hframework.hamster.sch.domain.model.LogDeploy_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDeployMapper {
    int countByExample(LogDeploy_Example example);

    int deleteByExample(LogDeploy_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(LogDeploy record);

    int insertSelective(LogDeploy record);

    List<LogDeploy> selectByExample(LogDeploy_Example example);

    LogDeploy selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogDeploy record, @Param("example") LogDeploy_Example example);

    int updateByExample(@Param("record") LogDeploy record, @Param("example") LogDeploy_Example example);

    int updateByPrimaryKeySelective(LogDeploy record);

    int updateByPrimaryKey(LogDeploy record);
}