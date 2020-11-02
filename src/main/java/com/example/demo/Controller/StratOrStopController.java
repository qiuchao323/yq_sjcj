package com.example.demo.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.cjwzs;
import com.example.demo.service.QueryCjwzsServcie;
import com.example.demo.service.QueryStatusService;
import com.example.demo.us.codecraft.webmagic.downloader.selenium.ThreadsUtil;

@RestController
public class StratOrStopController {
	@Autowired
	private  ThreadsUtil th;
	@Autowired
	private QueryStatusService queryStatusService;
	@Autowired
	private QueryCjwzsServcie queryCjwzsServcie;
	@RequestMapping("/strat")
	public int Start(@RequestBody JSONObject params) {
		 Integer num = params.getInteger("num");
		return queryStatusService.setStartr(num);
		
	}
	@RequestMapping("/strartTask")
	public void strartTask(@RequestBody JSONObject params) {
		Integer num = params.getInteger("num");
		if(num==null) {
			num=3;
		}
		th.start(num,"0");
		
	}
	@RequestMapping("/stopt")
	public int Stopt() {
		th.cancel();
	  return queryStatusService.setStop();
		
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryStatus")
	public List queryStatus() {
		return queryStatusService.Status();
		
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryCjwzs")
	public List queryCjwzs() {
		return queryCjwzsServcie.getAll();
		
	}
	@RequestMapping("/test")
	public void test(@RequestBody JSONObject params) {
		Integer num = params.getInteger("num");
		if(num==null) {
			num=3;
		}
		th.start(num,"1");
	}
}
