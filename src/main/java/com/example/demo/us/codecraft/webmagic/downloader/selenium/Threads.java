package com.example.demo.us.codecraft.webmagic.downloader.selenium;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


public class Threads implements Runnable,PageProcessor{
	
	public JSON json;
	
	public int count;
	
	public String jsonstr;
	private Site site;
	
	@SuppressWarnings("rawtypes")
	public List getWzpoj() {
		return wzpoj;
	}

	public void setWzpoj(@SuppressWarnings("rawtypes") List wzpoj) {
		this.wzpoj = wzpoj;
	}

	@SuppressWarnings("rawtypes")
	public List wzpoj;
	
	public JSON getJson() {
		return json;
	}

	public void setJson(JSON json) {
		this.json = json;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getJsonstr() {
		return jsonstr;
	}

	public void setJsonstr(String jsonstr) {
		this.jsonstr = jsonstr;
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(jsonstr);
		
		for(int i=0 ;i<wzpoj.size();i++) {
		JSONObject jsonObject = JSON.parseObject(wzpoj.get(i).toString());
		Spider.create(new Threads())
		.addUrl(jsonObject.get("wzdz").toString())//黑龙江页面加载不出来(原因有反扒技术) 模拟点击'进入'解决  http://www.hljcg.gov.cn
		//.addPipeline(new FilePipeline("D:\\webMagicDate\\"))
		.setDownloader(new SeleniumDownloader().setSleepTime(3000).setParams(jsonObject)).run();
		
		}
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Site getSite() {
		site = Site
	            .me().setCharset("UTF-8")
	            .setCycleRetryTimes(3)
	            .setSleepTime(3 * 1000)
	            .addHeader("Connection", "keep-alive")
	            .addHeader("Cookie", "JSESSIONID=JN9XccTL4vGc6p6Tk3pKThkpyLvQrsxLVdXWLGvnMQLnMq1ThTWY!-309478555")
	            .addHeader("Cache-Control", "max-age=0")
	            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
		return site;
	}

}
