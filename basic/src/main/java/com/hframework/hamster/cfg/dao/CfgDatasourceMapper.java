package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgDatasourceMapper {
    int countByExample(CfgDatasource_Example example);

    int deleteByExample(CfgDatasource_Example example);

    int deleteByPrimaryKey(Long cfgDatasourceId);

    int insert(CfgDatasource record);

    int insertSelective(CfgDatasource record);

    List<CfgDatasource> selectByExample(CfgDatasource_Example example);

    CfgDatasource selectByPrimaryKey(Long cfgDatasourceId);

    int updateByExampleSelective(@Param("record") CfgDatasource record, @Param("example") CfgDatasource_Example example);

    int updateByExample(@Param("record") CfgDatasource record, @Param("example") CfgDatasource_Example example);

    int updateByPrimaryKeySelective(CfgDatasource record);

    int updateByPrimaryKey(CfgDatasource record);
}