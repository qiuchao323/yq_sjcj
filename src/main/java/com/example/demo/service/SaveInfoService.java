package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.QueryCjwzs;

@Service
@Component
public class SaveInfoService {
	@Autowired
	private QueryCjwzs queryCjwzs;
	
	public void insertWjnr(String xmmc, String wjljdz, String wynr,String tname, String id) {
		
		queryCjwzs.insertWjnr(xmmc, wjljdz, wynr,tname,id);
		
	}
}
