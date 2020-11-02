package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.bean.cjwzs;
import com.example.demo.mapper.QueryCjwzs;

@Service
@Component
public class QueryCjwzsServcie {
	@Autowired
	private QueryCjwzs queryCjwzs;
	
	public List<cjwzs> getAll() {
		System.out.println(queryCjwzs.getAll());
		return queryCjwzs.getAll();
	};
	public int getStop() {
		// TODO Auto-generated method stub
		System.out.println(queryCjwzs.Status().get(0).getStuts());
		return queryCjwzs.Status().get(0).getStuts();
	}
	//测试
	public List<cjwzs> gettest() {
		return queryCjwzs.gettest();
	};
	
}
