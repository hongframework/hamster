package com.hframework.hamster.sch.dao;

import com.hframework.hamster.sch.domain.model.JobExeMeta;
import com.hframework.hamster.sch.domain.model.JobExeMeta_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExeMetaMapper {
    int countByExample(JobExeMeta_Example example);

    int deleteByExample(JobExeMeta_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(JobExeMeta record);

    int insertSelective(JobExeMeta record);

    List<JobExeMeta> selectByExample(JobExeMeta_Example example);

    JobExeMeta selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JobExeMeta record, @Param("example") JobExeMeta_Example example);

    int updateByExample(@Param("record") JobExeMeta record, @Param("example") JobExeMeta_Example example);

    int updateByPrimaryKeySelective(JobExeMeta record);

    int updateByPrimaryKey(JobExeMeta record);
}