package com.hframework.hamster.sec.dao;

import com.hframework.hamster.sec.domain.model.HfsecMenu;
import com.hframework.hamster.sec.domain.model.HfsecMenu_Example;
import com.hframework.hamster.sec.domain.model.HfsecMenu;
import com.hframework.hamster.sec.domain.model.HfsecMenu_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HfsecMenuMapper {
    int countByExample(HfsecMenu_Example example);

    int deleteByExample(HfsecMenu_Example example);

    int deleteByPrimaryKey(Long hfsecMenuId);

    int insert(HfsecMenu record);

    int insertSelective(HfsecMenu record);

    List<HfsecMenu> selectByExample(HfsecMenu_Example example);

    HfsecMenu selectByPrimaryKey(Long hfsecMenuId);

    int updateByExampleSelective(@Param("record") HfsecMenu record, @Param("example") HfsecMenu_Example example);

    int updateByExample(@Param("record") HfsecMenu record, @Param("example") HfsecMenu_Example example);

    int updateByPrimaryKeySelective(HfsecMenu record);

    int updateByPrimaryKey(HfsecMenu record);
}