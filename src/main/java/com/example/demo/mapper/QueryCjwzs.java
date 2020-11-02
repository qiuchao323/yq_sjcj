package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.example.demo.bean.Index;
import com.example.demo.bean.cjwzs;

@Mapper
@Component
public interface QueryCjwzs {
	@Select("select * from selnium_cjgz_table")
	public List<cjwzs> getAll();
	@Select("select stuts from selnium_startorstop_table")
	public List<Index> Status();
	@Insert("insert into #{tname} (xmmc,wyljdz,wynr) values(#{xmmc},#{wyljdz},#{wynr})")
	public int insertWjnr(String xmmc, String wjljdz, String wynr,String tname,String id);
	@Select("select * from selnium_cjgz_table t where t.test=1 ")
	public List<cjwzs> gettest();
	
}
