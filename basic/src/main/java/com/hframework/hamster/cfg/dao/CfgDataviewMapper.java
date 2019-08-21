package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgDataview;
import com.hframework.hamster.cfg.domain.model.CfgDataview_Example;
import com.hframework.hamster.cfg.domain.model.CfgDataview;
import com.hframework.hamster.cfg.domain.model.CfgDataview_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgDataviewMapper {
    int countByExample(CfgDataview_Example example);

    int deleteByExample(CfgDataview_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgDataview record);

    int insertSelective(CfgDataview record);

    List<CfgDataview> selectByExample(CfgDataview_Example example);

    CfgDataview selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgDataview record, @Param("example") CfgDataview_Example example);

    int updateByExample(@Param("record") CfgDataview record, @Param("example") CfgDataview_Example example);

    int updateByPrimaryKeySelective(CfgDataview record);

    int updateByPrimaryKey(CfgDataview record);
}