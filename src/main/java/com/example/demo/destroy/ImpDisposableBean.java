package com.example.demo.destroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.InsertWjnr;

/**
 * 容器销毁的时候执行某些操作可以在这里敲代码
 * @author QiuChao
 *
 */
@Component
public class ImpDisposableBean implements DisposableBean,ExitCodeGenerator{
	 InsertWjnr inert=new InsertWjnr();
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		inert.alertstatus();
		System.out.println("**************************容器被销毁了*****************************");
	}

	@Override
	public int getExitCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
