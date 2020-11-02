package com.example.demo.us.codecraft.webmagic.downloader.selenium;

import org.springframework.context.ApplicationContext;

import com.example.demo.service.SaveInfoService;

public class SpringDataBeanUtil {
	//获取ApplicationContext的实例
    private ApplicationContext applicationContext = SpringUtil.getApplicationContext();
    //通过applicationContext和反射机制来获取对象
	public SaveInfoService saveInfoService = applicationContext.getBean(SaveInfoService.class);

}
