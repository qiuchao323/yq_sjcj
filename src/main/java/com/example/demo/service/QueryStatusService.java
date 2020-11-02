package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.QueryStatus;


@Service
public class QueryStatusService {
	@Autowired
	private QueryStatus queryStatus;
	@SuppressWarnings("rawtypes")
	public List Status() {
		System.out.println(queryStatus.Status());
		return queryStatus.Status();
	}
	public int setStartr(Integer num) {
		// TODO Auto-generated method stub
		return queryStatus.setStartr(num);
	}
	public int setStop() {
		// TODO Auto-generated method stub
		return queryStatus.setStop();
	}
}
