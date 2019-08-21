package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat;
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat_Example;
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat;
import com.hframework.hamster.cfg.domain.model.CfgNodeTaskRelat_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgNodeTaskRelatMapper {
    int countByExample(CfgNodeTaskRelat_Example example);

    int deleteByExample(CfgNodeTaskRelat_Example example);

    int deleteByPrimaryKey(Long cfgNodeTaskRelatId);

    int insert(CfgNodeTaskRelat record);

    int insertSelective(CfgNodeTaskRelat record);

    List<CfgNodeTaskRelat> selectByExample(CfgNodeTaskRelat_Example example);

    CfgNodeTaskRelat selectByPrimaryKey(Long cfgNodeTaskRelatId);

    int updateByExampleSelective(@Param("record") CfgNodeTaskRelat record, @Param("example") CfgNodeTaskRelat_Example example);

    int updateByExample(@Param("record") CfgNodeTaskRelat record, @Param("example") CfgNodeTaskRelat_Example example);

    int updateByPrimaryKeySelective(CfgNodeTaskRelat record);

    int updateByPrimaryKey(CfgNodeTaskRelat record);
}