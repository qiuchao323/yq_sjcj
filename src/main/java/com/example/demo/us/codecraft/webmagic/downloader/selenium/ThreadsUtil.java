package com.example.demo.us.codecraft.webmagic.downloader.selenium;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.cjwzs;
import com.example.demo.service.QueryCjwzsServcie;
import com.example.demo.service.QueryStatusService;
import com.example.demo.us.codecraft.webmagic.downloader.selenium.PropertiesUtils;
import com.example.demo.us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
@Service("ThreadsUtil")
public class ThreadsUtil implements PageProcessor  {
	private Site site;
	 //停止线程的标记
	private volatile static boolean cancelled;
	@Autowired
	private QueryStatusService queryStatusService;
	@Autowired
	private  QueryCjwzsServcie queryCjwzsServcie;
	
	public void cancel() {
        cancelled = true;
    }
	@Service("ThreadsExTh")
	static class ThreadsExTh extends Thread{
		private int startIndex;
		
		private int endNum;
		
		private CountDownLatch threadsSignal;  
		
		public void ImportThread(CountDownLatch threadsSignal) {  
			this.threadsSignal = threadsSignal;  
		}  
		private List<cjwzs> dateList;
		
		private String StringName;
		 
		
		public int getStartIndex() {
			return startIndex;
		}


		public void setStartIndex(int startIndex) {
			this.startIndex = startIndex;
		}


		public int getEndNum() {
			return endNum;
		}


		public void setEndNum(int endNum) {
			this.endNum = endNum;
		}


		@SuppressWarnings("rawtypes")
		public List getDateList() {
			return dateList;
		}


		public void setDateList(List<cjwzs> dateList) {
			this.dateList = dateList;
		}


		public String getStringName() {
			return StringName;
		}


		public void setStringName(String stringName) {
			StringName = stringName;
		}


		@SuppressWarnings({ "resource"})
		public void run() {
			
			List<cjwzs> list =dateList.subList(startIndex, endNum);
			
			System.out.println("线程"+StringName+"处理"+list.size()+"条");
			for(int i=0;i<list.size();i++) {
				System.out.println(cancelled);
				if(!cancelled) {
				System.out.println(list.get(i).toString());
				JSONObject jsonObject = JSON.parseObject(list.get(i).toString());
				Spider.create(new ThreadsUtil()).thread(1)
				.addUrl(jsonObject.get("wzdz").toString())//生产 C:\Users\admin\AppData\Local\Google\Chrome\Application\chromedriver.exe//本地"C:\\Users\\QiuChao\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
				.setDownloader(new SeleniumDownloader("C:\\chromedriver.exe").setSleepTime(3000).setParams(jsonObject)).run();
				}else {
					System.out.println("*********************程序正在终止"+Thread.currentThread().getName() +"网站的数据采集**********************");
				}	
			}
			
			threadsSignal.countDown();//线程结束时计数器减1  
			System.out.println(Thread.currentThread().getName() + "结束. 还有" + threadsSignal.getCount() + " 个线程");
			
		}
		

		
		@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
		public static void main(String[] args) {
			PropertiesUtils pro=new PropertiesUtils();
			System.out.println(pro.getJsonResource("wz").get("cjwzs"));
			List<cjwzs> wzpoj= (List) pro.getJsonResource("wz").get("cjwzs");
			System.out.println(wzpoj.size());
			
			int nums=wzpoj.size();//总共多少条数据
			int tnums=1;//线程总数
			
			int baseNum=nums/tnums;//基数
			
			int remainderNum =nums%tnums;//余数
			   int end  = 0;
		        for (int i = 0; i < tnums; i++) {
		            int start = end ;
		            end = start + baseNum;
		            if(i == (tnums-1)){
		                end = nums;
		            }else if( i < remainderNum){
		                end = end + 1;
		            }
		            ThreadsExTh thread = new ThreadsExTh();
		            thread.setStringName("线程[" + (i + 1) + "] ");
		            thread.setDateList(wzpoj);
		            thread.setStartIndex(start);
		            thread.setEndNum(end);
		            thread.start();
		        }
			
			
			
			
		}
	}
	
	@SuppressWarnings({ "static-access" })
	public String start(int num,String cs) {
//		PropertiesUtils pro=new PropertiesUtils();
//		System.out.println(pro.getJsonResource("wz").get("cjwzs"));
		//		List wzpoj= (List) pro.getJsonResource("wz").get("cjwzs"); 已配置文件作为采集规则数据源
		List<cjwzs> wzpoj=null;
		if("1".equals(cs)) {
			wzpoj=queryCjwzsServcie.gettest();//测试数据
		}else {
			 wzpoj=queryCjwzsServcie.getAll();//数据库里的采集规则
		}
		
		
		int nums=wzpoj.size();//总共多少条数据
		int tnums=num;//线程总数
		CountDownLatch threadSignal = new CountDownLatch(tnums);//初始化线程数 
		int baseNum=nums/tnums;//基数
		
		int remainderNum =nums%tnums;//余数
		   int end  = 0;
	        for (int i = 0; i < tnums; i++) {
	            int start = end ;
	            end = start + baseNum;
	            if(i == (tnums-1)){
	                end = nums;
	            }else if( i < remainderNum){
	                end = end + 1;
	            }
	            ThreadsExTh thread = new ThreadsExTh();
	            thread.setStringName("线程[" + (i + 1) + "] ");
	            thread.setDateList(wzpoj);
	            thread.setStartIndex(start);
	            thread.setEndNum(end);
	            thread.ImportThread(threadSignal);
	            thread.start();
	        }
	        try {
				threadSignal.await();//等待所有子线程执行完  
				cancelled=false;
				queryStatusService.setStop();
				System.out.println("+++++++++++++++++++++++++++++所有线程执行结束++++++++++++++++++++++");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "ok";
	        
		
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
	            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		return site;
	}
	
//	public int insertWjnr(String xmmc,String wjljdz,String wynr) {
//		// TODO Auto-generated method stub
//		System.out.println("保存项目名称>>>>>>>>"+xmmc);
//		System.out.println("保存文件链接地址>>>>>>>>"+wjljdz);
//		System.out.println(">>>>>>>>"+queryCjwzsServcie);
//		return queryCjwzsServcie.insertWjnr(xmmc,wjljdz,wynr);
//	}

}
