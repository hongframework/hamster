package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail_Example;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail;
import com.hframework.hamster.cfg.domain.model.CfgDeploymentDetail_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgDeploymentDetailMapper {
    int countByExample(CfgDeploymentDetail_Example example);

    int deleteByExample(CfgDeploymentDetail_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgDeploymentDetail record);

    int insertSelective(CfgDeploymentDetail record);

    List<CfgDeploymentDetail> selectByExample(CfgDeploymentDetail_Example example);

    CfgDeploymentDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgDeploymentDetail record, @Param("example") CfgDeploymentDetail_Example example);

    int updateByExample(@Param("record") CfgDeploymentDetail record, @Param("example") CfgDeploymentDetail_Example example);

    int updateByPrimaryKeySelective(CfgDeploymentDetail record);

    int updateByPrimaryKey(CfgDeploymentDetail record);
}