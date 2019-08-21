package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgDatatable;
import com.hframework.hamster.cfg.domain.model.CfgDatatable_Example;
import com.hframework.hamster.cfg.domain.model.CfgDatatable;
import com.hframework.hamster.cfg.domain.model.CfgDatatable_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgDatatableMapper {
    int countByExample(CfgDatatable_Example example);

    int deleteByExample(CfgDatatable_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgDatatable record);

    int insertSelective(CfgDatatable record);

    List<CfgDatatable> selectByExample(CfgDatatable_Example example);

    CfgDatatable selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgDatatable record, @Param("example") CfgDatatable_Example example);

    int updateByExample(@Param("record") CfgDatatable record, @Param("example") CfgDatatable_Example example);

    int updateByPrimaryKeySelective(CfgDatatable record);

    int updateByPrimaryKey(CfgDatatable record);
}