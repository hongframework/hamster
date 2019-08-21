package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgDeployment;
import com.hframework.hamster.cfg.domain.model.CfgDeployment_Example;
import com.hframework.hamster.cfg.domain.model.CfgDeployment;
import com.hframework.hamster.cfg.domain.model.CfgDeployment_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgDeploymentMapper {
    int countByExample(CfgDeployment_Example example);

    int deleteByExample(CfgDeployment_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgDeployment record);

    int insertSelective(CfgDeployment record);

    List<CfgDeployment> selectByExample(CfgDeployment_Example example);

    CfgDeployment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgDeployment record, @Param("example") CfgDeployment_Example example);

    int updateByExample(@Param("record") CfgDeployment record, @Param("example") CfgDeployment_Example example);

    int updateByPrimaryKeySelective(CfgDeployment record);

    int updateByPrimaryKey(CfgDeployment record);
}