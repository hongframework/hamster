package com.hframework.hamster.cfg.dao;

import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail_Example;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail;
import com.hframework.hamster.cfg.domain.model.CfgSubscribeDetail_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgSubscribeDetailMapper {
    int countByExample(CfgSubscribeDetail_Example example);

    int deleteByExample(CfgSubscribeDetail_Example example);

    int deleteByPrimaryKey(Long cfgSubscribeDetailId);

    int insert(CfgSubscribeDetail record);

    int insertSelective(CfgSubscribeDetail record);

    List<CfgSubscribeDetail> selectByExample(CfgSubscribeDetail_Example example);

    CfgSubscribeDetail selectByPrimaryKey(Long cfgSubscribeDetailId);

    int updateByExampleSelective(@Param("record") CfgSubscribeDetail record, @Param("example") CfgSubscribeDetail_Example example);

    int updateByExample(@Param("record") CfgSubscribeDetail record, @Param("example") CfgSubscribeDetail_Example example);

    int updateByPrimaryKeySelective(CfgSubscribeDetail record);

    int updateByPrimaryKey(CfgSubscribeDetail record);
}