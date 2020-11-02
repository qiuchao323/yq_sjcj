package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.example.demo.bean.Index;

@Mapper
@Component
public interface QueryStatus {
	@Select("select stuts,thnums from selnium_startorstop_table")
	public List<Index> Status();
	@Update("update selnium_startorstop_table t set t.stuts=1,t.thnums=#{nums}")
	public int setStartr(@Param("nums") Integer num);
	@Update("update selnium_startorstop_table t set t.stuts=0")
	public int setStop();

}
