package com.example.demo.us.codecraft.webmagic.downloader.selenium;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mozilla.universalchardet.UniversalDetector;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.InsertWjnr;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

/**
 * 使用Selenium调用浏览器进行渲染。目前仅支持chrome。<br>
 * 需要下载Selenium driver支持。<br>
 *
 * @author code4crafter@gmail.com <br>
 *         Date: 13-7-26 <br>
 *         Time: 下午1:37 <br>
 */
@Service("SeleniumDownloader")
@Component
public class SeleniumDownloader implements Downloader, Closeable {
	
	 InsertWjnr inert=new InsertWjnr();
	
	private volatile WebDriverPool webDriverPool;

	private Logger logger = Logger.getLogger(getClass());

	private int sleepTime = 0;
	
	@SuppressWarnings("unused")
	private int xqsleepTime = 0;

	private int poolSize = 1;
	//自定义规则
	private JSONObject json;

	@SuppressWarnings("unused")
	private static final String DRIVER_PHANTOMJS = "phantomjs";

	/**
	 * 新建
	 *
	 * @param chromeDriverPath chromeDriverPath
	 */
	public SeleniumDownloader(String chromeDriverPath) {
		System.getProperties().setProperty("webdriver.chrome.driver",
				chromeDriverPath);
	}

	/**
	 * Constructor without any filed. Construct PhantomJS browser
	 * 
	 * @author bob.li.0718@gmail.com
	 */
	public SeleniumDownloader() {
		// System.setProperty("phantomjs.binary.path",
		// "/Users/Bingo/Downloads/phantomjs-1.9.7-macosx/bin/phantomjs");
	}

	/**
	 * set sleep time to wait until load success
	 * 设置睡眠时间以等待加载成功
	 *
	 * @param sleepTime sleepTime
	 * @return this
	 */
	public SeleniumDownloader setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
		return this;
	}
	/**
	 * set sleep time to wait until load success
	 * 设置睡眠时间以等待详情页加载成功
	 *
	 * @param xqsleepTime xqsleepTime
	 * @return this
	 */
	public SeleniumDownloader xqsleepTime(int xqsleepTime) {
		this.xqsleepTime = xqsleepTime;
		return this;
	}
	//规则参数
	public SeleniumDownloader setParams(JSONObject json) {
		this.json = json;
		return this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Page download(Request request, Task task) {
		String wz=request.getUrl();
		checkInit();
		WebDriver webDriver;
		try {
			webDriver = webDriverPool.get();
			webDriver.manage().window().maximize();
		} catch (InterruptedException e) {
			logger.warn("interrupted", e);
			return null;
		}
		logger.info("downloading page " + request.getUrl());
		webDriver.get(wz);
//		 @SuppressWarnings("unused")
//		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		try {
			//通过js判断页面是否加载完
//			while(!"complete".equals(js.executeScript("return document.readyState"))) {
				Thread.sleep(sleepTime);
//			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebDriver.Options manage = webDriver.manage();
		Site site = task.getSite();
		if (site.getCookies() != null) {
			for (Map.Entry<String, String> cookieEntry : site.getCookies()
					.entrySet()) {
				Cookie cookie = new Cookie(cookieEntry.getKey(),
						cookieEntry.getValue());
				manage.addCookie(cookie);
			}
		}

		/*
		 * TODO You can add mouse event or other processes
		 * 
		 * 可以添加鼠标事件或其他进程
		 * 
		 * @author: bob.li.0718@gmail.com
		 */
//			webDriver=webDriverPool.get();
			
			
//			webDriver.get(page.getRequest().getUrl());
			//获取当前窗口头部信息
			String winsowhand=webDriver.getWindowHandle();
			//智能等待3秒加载元素 已加载不等待
//			webDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
//			String[] xp= {"/html/body/div[3]/div[4]/div[1]/div[1]/span/a"};//点击步骤 cjgz
			//是否有搜索关键字
			/**暂时屏蔽
			if(!"null".equals(json.get("sechword").toString())) {
				String keyword=json.get("sechword").toString().split("_")[1];
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+keyword);
				try {
					Thread.sleep(sleepTime);
					WebElement element = (new WebDriverWait(webDriver,10))
			                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(json.get("sechwordxpth").toString())));
					element.sendKeys(keyword);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			}**/
			@SuppressWarnings("rawtypes")
			List xp=(List) json.get("cjgz");
			for(int i=0;i<xp.size();i++) {
			//	getNew (webDriver,winsowhand);
				//获取列表页面窗口信息
				WebElement element = (new WebDriverWait(webDriver,20))
		                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp.get(i).toString())));
//				WebElement element=webDriver.findElement(By.xpath(xp.get(i).toString()));
				element.click();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				getNew (webDriver,winsowhand);
				/**
				//获取文件列表
				List<WebElement> listli=webDriver.findElements(By.xpath(json.get("cjlb").toString()));//列表目标cjlb
				System.out.println("******************每页"+listli.size()+"条***********************");
				for(int j=0;j<listli.size();j++) {
					//保存当前页面句柄
					String nowWindows=webDriver.getWindowHandle();
					System.out.println("当前页面url:"+webDriver.getCurrentUrl());
					System.out.println(">>>>>>>>>>>>>>当前采集第"+j+"条>>>>>>>>>>>>>>>>>>>>>>>>>>");
//					WebElement elements = (new WebDriverWait(webDriver,10))
//			                .until(ExpectedConditions.presenceOfElementLocated((By) listli.get(j)));
//					elements.click();
					listli.get(j).click();
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					getNew (webDriver,nowWindows);
					//保存
					savePage(webDriver) ;
					if(!nowWindows.equals(webDriver.getWindowHandle())) {
						//关闭当前页面
						webDriver.close();
						//跳回列表页面
						webDriver.switchTo().window(nowWindows);
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//返回前一页 特别注意 返回后的页面虽然页面代码没有发生变化 但是dom元素的属性发生了变化 会导致for循环只能点击第一次！！！！！！！！！！！！坑！！
						webDriver.navigate().back();
						
					}
					//解决上述问题的关键,清空之前的dom元素数组重新定位
					listli.isEmpty();
					listli=webDriver.findElements(By.xpath(json.get("cjlb").toString()));//列表目标cjlb
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				**/
				//执行规则的过程中可能会匹配到列表 匹配到就采集
//				try {
//					webDriver.findElements(By.xpath(json.get("cjlb").toString()));//列表目标cjlb
//					getWjLi(webDriver);
//					cLickPage(webDriver) ;
//					getNew (webDriver,winsowhand);
//				}catch (Exception e) {
//					continue;
//				}
				
				//执行规则的过程中可能会匹配到列表 此操作 只采集最后一个点击事件后的文件列表
				if(i==xp.size()-1) {
					if(!json.get("lbiframe").equals("")) {
						WebElement elementifr=webDriver.findElement(By.xpath(json.get("lbiframe").toString()));
						webDriver=webDriver.switchTo().frame(elementifr);
					}
					getWjLi(webDriver);
					cLickPage(webDriver) ;
					getNew (webDriver,winsowhand);
				}
				
//				webDriver.findElement(By.xpath("nextPage")).click();
				
				
				
			
			}
		
//		WebElement webElement = webDriver.findElement(By.xpath("/html"));
//		String content = webElement.getAttribute("outerHTML");
//		webDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
//		try {
//			Thread.sleep(sleepTime);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String html=webDriver.getPageSource();
////		System.out.println("html>>>>>>>>>>>>>>>>>>>>>>>>>"+html);
//		Html h=new Html(html);
//		String wjnr=h.xpath("//*[@id=\"bodyMain\"]/div").get();
//		Selectable bt=h.xpath("//*[@id=\"bodyMain\"]/div/div/div[2]/div[1]/h1/text()");
//		System.out.println("标题>>>>>>>>>>>>>>>>>>>>>>>>>"+bt);
//		System.out.println("内容>>>>>>>>>>>>>>>>>>>>>>>>>"+wjnr);
//		
		Page page = new Page();
		page.setRawText(webDriver.getPageSource());
		page.setHtml(new Html(webDriver.getPageSource(), request.getUrl()));
		page.setUrl(new PlainText(request.getUrl()));
		page.setRequest(request);
		webDriver.close();
		return page;
		
	}
	
		/**
		 * 判断是否跳转页面
		 * @param args
		 */
		public void getNew (WebDriver driver,String handle) {
			 for(String handles:driver.getWindowHandles()){
			        //与第一个页面内的句柄比较  当句柄不予第一个相等时 就把权限赋予给了新窗口 只适用于两个窗口的情况
			        if(handles.equals(handle)){
			            continue;
			        }else{
			            //给要跳转的新界面进行权限赋予  要传入新页面的句柄
			            driver.switchTo().window(handles);
			        }
			    }
		}
	/**
	 * 返回前一页
	 * @param webDriver
	 */
	@SuppressWarnings("unused")
	public void getBack(WebDriver webDriver) throws ScriptException {
		String script = "window.history.go(-1);"; 
				JavascriptExecutor js = (JavascriptExecutor)webDriver;
				Object classToCall = js.executeScript(script);
	}
	/**
	 * 保存html
	 * @param webDriver
	 * @param wjbt 
	 * @param list
	 * @throws ScriptException
	 */
	@SuppressWarnings("unchecked")
	public void savePage(WebDriver webDriver, String wjbt)  {
		
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
 
        HtmlPage page = null;
        try {
            page = webClient.getPage(webDriver.getCurrentUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
//        long xqsleepTime= ("".equals(json.get("xqsleepTime").toString()) ? sleepTime:(int) json.get("xqsleepTime"));
        webClient.waitForBackgroundJavaScript(sleepTime);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        String bt=wjbt;
//        List<HtmlElement> spanList=(List<HtmlElement>) page.getByXPath(json.get("wjbt").toString());
//        for(int i=0;i<spanList.size();i++) {
//            //asText ==> innerHTML ✔
//        	bt=spanList.get(i)+"";
//        }
//        String nr="";
//        List<HtmlElement> nrList=(List<HtmlElement>) page.getByXPath(json.get("wjnr").toString());
//        for(int i=0;i<nrList.size();i++) {
//        	//asText ==> innerHTML ✔
//        	nr=nrList.get(i).asXml();
//        }
        
        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串

        //TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库

		Html h=null;
		h=new Html(webDriver.getPageSource());
		String savepath="D://wzhtml//"+json.get("wzdz").toString().split("/")[2];
		/**20190724屏蔽改成存到数据库
		File dir=new File(savepath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		 */
//		File f=new File(savepath+"//"+h.xpath(json.get("wjbt").toString())+".html");//文件标题 wjbt
		//20190724屏蔽改成存到数据库
		//File f=new File(savepath+"//"+wjbt+".html");//文件标题 wjbt
		/**20190724屏蔽改成存到数据库
	    PrintStream p;
		p = new PrintStream(new FileOutputStream(f));
		p.println(h.xpath(json.get("wjnr").toString()).get());//文件内容 wjnr
		p.close();
		*/
		//采集内容插入数据库
		try {
			if(!json.get("iframe").equals("")) {
				try {
					webDriver=webDriver.switchTo().frame(webDriver.findElement(By.xpath(json.get("iframe").toString())));
					h=new Html(webDriver.getPageSource());
//					System.out.println(">>>>>"+h);
					inert.insertWjnr(bt, webDriver.getCurrentUrl(), h.toString(),json.get("insertTablename").toString(),json.get("id").toString());
					//收集页面a标签 和 img 标签的url
					inert.insertUrl(bt,webDriver.findElements(By.tagName("a")),webDriver.findElements(By.tagName("img")),getDomain(webDriver.getCurrentUrl()));
				}catch (Exception e) {
					System.out.println("》》》》》》》》》》》》》没有找到iframe《《《《《《《《《《《《《《《《《《《");
				}
				
			}else {
				inert.insertWjnr(bt, webDriver.getCurrentUrl(), pageXml,json.get("insertTablename").toString(),json.get("id").toString());
				//收集页面a标签 和 img 标签的url
				inert.insertPageUrl(bt, page.getElementsByTagName("a"),page.getElementsByTagName("img"),getDomain(webDriver.getCurrentUrl()));
			}
//			System.out.println(h);
//			System.out.println(">>>>>标题"+h.xpath(json.get("wjbt").toString()).toString());
			//收集页面a标签 和 img 标签的url
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("》》》》》》》》》》》》》保存异常《《《《《《《《《《《《《《《《《《《");
			e.printStackTrace();
		}
		//判断是否需要下载附件
		if(!"null".equals(json.get("fjlb"))) {
			System.out.println("需要下载附件");
			String htmlStr=webDriver.getPageSource();
			String importFileRole = "<a target=.*? href=\"(.*?)\">(.*?)</a>";//正则表达式
			Pattern pm = Pattern.compile(importFileRole);//获取正则表达式中的分组，每一组小括号为一组
			Matcher m = pm.matcher(htmlStr);//进行匹配
			
			
			@SuppressWarnings("rawtypes")
			List<Map> list = new ArrayList<>();
	        while (m.find()) {
	        	HashMap<Object, Object> map=new HashMap<>();
	        	map.put("url", m.group(1));
	        	map.put("title", m.group(2));
	            list.add(map);
	        }
	        System.out.println(list);
			if(list.size()>0) {
				for(Map<String, String> map: list) {
					String fjdirp=savepath+"//附件";
					File fjdir=new File(fjdirp);
					if(!fjdir.exists()) {
						fjdir.mkdirs();
					}
					System.out.println(">>>>>>>>>>>>>附件名称"+map.get("title"));
					System.out.println(">>>>>>>>>>>>>附件名称"+map.get("url"));
					downloadFj(fjdirp+"//",map.get("url"),map.get("title"));
				}
				
			}
			
//			List<WebElement> fjlist=webDriver.findElements(By.xpath(json.get("fjlb").toString()));//列表目标cjlb
//			for(int j=0;j<fjlist.size();j++) {
//				System.out.println(fjlist.get(j).getText());
//				System.out.println(fjlist.get(j).getText().lastIndexOf("."));
//				String fjmc=fjlist.get(j).getText().substring(0,fjlist.get(j).getText().lastIndexOf("."));
//				System.out.println("附件名称》》》》》》》》》》》》》"+fjmc);
//				String fjpath=fjlist.get(j).getAttribute("href");
//				String fjdirp=savepath+"//附件";
//				File fjdir=new File(fjdirp);
//				if(!fjdir.exists()) {
//					fjdir.mkdirs();
//				}
//				downloadFj(fjdirp,fjpath,fjmc);
//			}
			
		}else {
			System.out.println("不需要下载附件");
		}
		
		//下载附件
		//downloadFj();
		
	}
	
	private void checkInit() {
		if (webDriverPool == null) {
			synchronized (this) {
				webDriverPool = new WebDriverPool(poolSize);
			}
		}
	}

	@Override
	public void setThread(int thread) {
		this.poolSize = thread;
	}

	@Override
	public void close() throws IOException {
		webDriverPool.closeAll();
	}
	
	/**
	 * 循环采集文件列表
	 */
	
	public void getWjLi(WebDriver webDriver ) {
		List<WebElement> listli=null;
//			System.out.println(webDriver.getPageSource());
			listli=webDriver.findElements(By.xpath(json.get("cjlb").toString()));//列表目标cjlb
			System.out.println(listli);
			System.out.println("******************每页"+listli.size()+"条***********************");
		
		//保存当前页面句柄
		String nowWindows=webDriver.getWindowHandle();
		for(int j=0;j<listli.size();j++) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>nums"+json.get("nums").toString());
			int size= ("0".equals(json.get("nums").toString()) ? listli.size():(int) json.get("nums"));
			
			//详情页等待时间
			long xqsleepTime= ("".equals(json.get("xqsleepTime").toString()) ? sleepTime:(int) json.get("xqsleepTime"));
			//控制每页采集几条
			if(j<size) {
			
			
				System.out.println("当前页面url:"+webDriver.getCurrentUrl());
				System.out.println(">>>>>>>>>>>>>>当前采集第"+j+"条>>>>>>>>>>>>>>>>>>>>>>>>>>");
//				WebElement elements = (new WebDriverWait(webDriver,10))
//		                .until(ExpectedConditions.presenceOfElementLocated((By) listli.get(j)));
//				elements.click();
				String wjbt=listli.get(j).getText().replaceAll("</?[^>]+>", "").replaceAll("\\s*|\t|\r|\n", "").replaceAll("/", "");//剔出<html>的标签去除字符串中的空格,回车,换行符,制表符
				System.out.println(!"null".equals(json.get("sechword")));
				if(!"null".equals(json.get("sechword"))) {
					wjbt=json.get("sechword").toString();
				}
				
				System.out.println(wjbt);
//				getNew (webDriver,nowWindows);
				
				try {
					listli.get(j).click();
					Thread.sleep(xqsleepTime);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					continue;
				}
			//	System.out.println(webDriver.getPageSource());
				getNew (webDriver,nowWindows);
				
				//保存
				savePage(webDriver,wjbt) ;
				if(!nowWindows.equals(webDriver.getWindowHandle())) {
					//关闭当前页面
					webDriver.close();
					//跳回列表页面
					webDriver.switchTo().window(nowWindows);
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					
					//返回前一页 特别注意 返回后的页面虽然页面代码没有发生变化 但是dom元素的属性发生了变化 会导致for循环只能点击第一次！！！！！！！！！！！！坑！！
					webDriver.navigate().back();
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				//解决上述问题的关键,清空之前的dom元素数组重新定位
				listli.isEmpty();
				if(!json.get("lbiframe").equals("")) {
					WebElement element=webDriver.findElement(By.xpath(json.get("lbiframe").toString()));
					webDriver=webDriver.switchTo().frame(element);
					listli=webDriver.findElements(By.xpath(json.get("cjlb").toString()));//列表目标cjlb
				}else {
					listli=webDriver.findElements(By.xpath(json.get("cjlb").toString()));//列表目标cjlb
				}
				
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			}
			
	}
	
	/**
	 * 分页
	 */
	
	public void cLickPage(WebDriver webDriver ) {
		System.out.println("获取几页："+Integer.parseInt(json.get("pageNum").toString()));
		String nowWindows=webDriver.getWindowHandle();
		for(int i=1;i<Integer.parseInt(json.get("pageNum").toString());i++) {
//			System.out.println(new Html(webDriver.getPageSource()).xpath(json.get("nextPage").toString()));
//			WebElement elements = (new WebDriverWait(webDriver,10))
//	                .until(ExpectedConditions.presenceOfElementLocated(By.linkText(json.get("nextPage").toString())));
//			elements.click();
			String nextPage=json.get("nextPage").toString();
			String jsnextPage=json.get("jsNextPage").toString();
			System.out.println(nextPage); 
			System.out.println(webDriver.getPageSource().toString());
			
			 //捕获下一页按钮时 By.linkText() 只能捕获带有链接的a标签，该异常捕获用于处理下一页按钮不带链接的元素 点击事件
			try {
//				if(!json.get("lbiframe").equals("")) {
//					WebElement element=webDriver.findElement(By.xpath(json.get("lbiframe").toString()));
//					webDriver=webDriver.switchTo().frame(element);
//				}
				if(nextPage.contains("//")||nextPage.contains("*")||nextPage.contains("@")||nextPage.contains("=")) {
					 WebElement webElement = webDriver.findElement(By.xpath(nextPage));
				        webElement.click();
					Thread.sleep(sleepTime);
				}else {
					 WebElement webElement = webDriver.findElement(By.linkText(nextPage));
				        webElement.click();
					Thread.sleep(sleepTime);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("******************找不到下一页链接！开始执行JS模拟点击***********************");
				JavascriptExecutor js = (JavascriptExecutor) webDriver;
				js.executeScript(jsnextPage); 
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("******************JS模拟点击完成***********************");
				
				e.printStackTrace();
			}
			//判断点击下一页是否页面是否发生变化
			if(!nowWindows.equals(webDriver.getWindowHandle())) {
				webDriver.switchTo().window(webDriver.getWindowHandle());
			}
//			System.out.println(new Html(webDriver.getPageSource()).xpath(json.get("cjlb").toString()));
			getWjLi(webDriver);
	}
	}
	
	/**
	 * 附件下载
	 * @param path
	 * @param url
	 */
	public static void downloadFj(String path, String url,String downloadName) {
		File file = null;
		FileOutputStream fos = null;
//		String downloadName = url.substring(url.lastIndexOf("/") + 1);
		HttpURLConnection httpCon = null;
		URLConnection con = null;
		URL urlObj = null;
		InputStream in = null;
		byte[] size = new byte[1024];
		int num = 0;
		try {
			
			if (url.startsWith("http")) {
				urlObj = new URL(url);
				con = urlObj.openConnection();
				httpCon = (HttpURLConnection) con;
				in = httpCon.getInputStream();
				System.out.println(getEncoding(InputStreamToByte(in)));
				byte[] b = new byte[4];
				in.read(b, 0, b.length);
				String typestr =checkType(bytesToHexString(b));
				if(!downloadName.contains("."+typestr)) {
					downloadName+="."+typestr;
				}
				System.out.println(downloadName);
				file = new File(path + downloadName);
//				if (!file.exists()) {
					file.createNewFile();
					fos = new FileOutputStream(file);
					while ((num = in.read(size)) != -1) {
						for (int i = 0; i < num; i++)
							fos.write(size[i]);
					}
					
//				} 
				
			}
			else {
 
			}
		} catch (Exception e) {
			System.out.println(url);
		} catch (Throwable t) {
			System.out.println(url);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// return;
			}
		}
	}
	
	
	
	private static byte[] InputStreamToByte(InputStream is) throws IOException {
		  ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		  byte[] buffer=new byte[1024];
		  int ch;
		  /**
		   * 
		   * */
		  while ((ch = is.read(buffer)) != -1) {
		   bytestream.write(buffer,0,ch);
		  }
		  byte data[] = bytestream.toByteArray();
		  bytestream.close();
		  return data;
		 }
	
	public static byte[] readInputStream(InputStream in) throws IOException {
		byte[] buffer =new byte[1024];
		int len=0;
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		while((len=in.read(buffer))!=-1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		
		return bos.toByteArray();
	}
	
	
	
	/**
	 * 将要读取文件头信息的文件的byte数组转换成string类型表示
	 * 
	 * @param src
	 *            要读取文件头信息的文件的byte数组
	 * @return 文件头信息
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder builder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < src.length; i++) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}
//		System.out.println(builder.toString());
		return builder.toString();
	}
	
	//文件类型与String 之间的对应关系
	
	public static String checkType(String str) {
	        
	        switch (str) {
	        case "FFD8FF": return "jpg";
	        case "89504E": return "png";
	        case "474946": return "jif";
	        case "504B0304": return "zip";
	        case "255044462D312E": return "pdf";
	        case "D0CF11E0": return "doc";
	        default: return "0000";
	        }
	    }
	

	    /**
	     * 获取文件编码类型
	     *
	     * @param bytes 文件bytes数组
	     * @return      编码类型
	     */
	    public static String getEncoding(byte[] bytes) {
	        String defaultEncoding = "UTF-8";
	        UniversalDetector detector = new UniversalDetector(null);
	        detector.handleData(bytes, 0, bytes.length);
	        detector.dataEnd();
	        String encoding = detector.getDetectedCharset();
	        detector.reset();
	        System.out.println("字符编码是："+ encoding);
	        if (encoding == null) {
	            encoding = defaultEncoding;
	        }
	        return encoding;
	    }	
	
	public static void main(String[] args) {
		 downloadFj("D://png//", "http://www.lnxggzyjyzx.cn/lnjyzx/ReadAttachFile.aspx?AttachID=9ab3e6f4-e4bf-4ef7-ab55-f43f494f2947", "a投标保证金退还申请表.doc");
//		
//		String str= 
//				"<body>\n" + 
//				"<table height=\"171\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tab top\">\n" + 
//				"  <tr>\n" + 
//				"    <td><embed width=\"990\" height=\"171\" src=\"/lnjyzx/Template/Default/images/flash.swf\"></embed></td>\n" + 
//				"  </tr>\n" + 
//				"</table>\n" + 
//				"<table height=\"35\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tab\" background=\"/lnjyzx/Template/Default/images/nav1.jpg\">\n" + 
//				"  <tr>\n" + 
//				"    <td width=\"39\"></td>\n" + 
//				"    <td width=\"100\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/../../\" target=\"_parent\">首页</a></td>\n" + 
//				"    <td width=\"2\" class=\"line\"></td>\n" + 
//				"    <td width=\"114\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/../../zwgk\" target=\"_parent\">政务公开</a></td>\n" + 
//				"    <td width=\"2\" class=\"line\"></td>\n" + 
//				"    <td width=\"114\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/../../jyxx\" target=\"_parent\">交易信息</a></td>\n" + 
//				"    <td width=\"2\" class=\"line\"></td>\n" + 
//				"    <td width=\"114\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/../../bszn\" target=\"_parent\">办事指南</a></td>\n" + 
//				"    <td width=\"2\" class=\"line\"></td>\n" + 
//				"    <td width=\"114\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/../../zcfg\" target=\"_parent\">政策法规</a></td>\n" + 
//				"    <td width=\"2\" class=\"line\"></td>\n" + 
//				"    <td width=\"114\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/../../fwpt\" target=\"_parent\">服务平台</a></td>\n" + 
//				"    <td width=\"2\" class=\"line\"></td>\n" + 
//				"    <td width=\"114\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/../../xzzx\" target=\"_parent\">下载中心</a></td>\n" + 
//				"    <td width=\"2\" class=\"line\"></td>\n" + 
//				"    <td width=\"114\" class=\"nav\"><a href=\"/lnjyzx/Template/Default/lxwm.htm\" target=\"_parent\">联系我们</a></td>\n" + 
//				"    <td width=\"39\"></td>\n" + 
//				"  </tr>\n" + 
//				"</table>\n" + 
//				"<table height=\"31\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tab\" background=\"/lnjyzx/Template/Default/images/line1.jpg\">\n" + 
//				"  <tr>\n" + 
//				"    <td width=\"127\" background=\"/lnjyzx/Template/Default/images/pd.jpg\">&nbsp;</td>\n" + 
//				"    <td width=\"18\"></td>\n" + 
//				"    <td width=\"106\" class=\"link\"><a href=\"/lnjyzx/Template/Default/../../jyxx/002001\" target=\"_parent\">建设工程</a></td>\n" + 
//				"    <td width=\"106\" class=\"link\"><a href=\"/lnjyzx/Template/Default/../../jyxx/002002\" target=\"_parent\">政府采购</a></td>\n" + 
//				"    <td width=\"106\" class=\"link\"><a href=\"/lnjyzx/Template/Default/../../jyxx/002003\" target=\"_parent\">产权交易</a></td>\n" + 
//				"    <td width=\"130\" class=\"link\"><a href=\"/lnjyzx/Template/Default/../../jyxx/002004\" target=\"_parent\">国土资源交易</a></td>\n" + 
//				"    <td width=\"29\" background=\"/lnjyzx/Template/Default/images/pd2.jpg\"></td>\n" + 
//				"    <td width=\"29\" align=\"right\" background=\"/lnjyzx/Template/Default/images/line.jpg\"><img src=\"/lnjyzx/Template/Default/images/search.jpg\"/></td>\n" + 
//				"    <td width=\"63\" align=\"center\" background=\"/lnjyzx/Template/Default/images/line.jpg\">全文检索：</td>\n" + 
//				"    <td width=\"127\" background=\"/lnjyzx/Template/Default/images/line.jpg\"><INPUT style=\"WIDTH:120px; HEIGHT: 16px;line-height:16px;\" type=\"text\" size=\"10\" name=\"txtKeys\" class=\"Inputtxt\" id=\"txtKeys\" value=\"请输入搜索内容\"  onClick=\"Clear();\"></td>\n" + 
//				"    <td width=\"90\" background=\"/lnjyzx/Template/Default/images/line.jpg\"><SELECT style=\"WIDTH:88px; font-size:14px;\n" + 
//				"    font-family:'隶书';color:#333333\" name=\"searchtype\" id=\"searchtype\"><OPTION value=\"title\" selected>标题检索</OPTION><OPTION value=\"titlewithcontent\" >全文搜索</OPTION></SELECT></td>\n" + 
//				"    <td width=\"59\" background=\"/lnjyzx/Template/Default/images/line.jpg\"><input type=\"submit\" value=\"\" class=\"sub\" onclick=\"search1()\"/></td>\n" + 
//				"  </tr>\n" + 
//				"</table>\n" + 
//				"\n" + 
//				"</body>\n" + 
//				"</html>\n" + 
//				"</td>\n" + 
//				"  </tr>\n" + 
//				"</table>\n" + 
//				"\n" + 
//				"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tab\" bgcolor=\"#ffffff\">\n" + 
//				"  <tr>\n" + 
//				"    <td>\n" + 
//				"    <table align=\"center\"   border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"\n" + 
//				"        class=\"tab topd\">\n" + 
//				"        <tr>\n" + 
//				"            <td width=\"990\">\n" + 
//				"                <table width=\"990\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" + 
//				"                    <tr>\n" + 
//				"                        <td width=\"46\" height=\"28\" background=\"/lnjyzx/Template/Default/images/index_2.jpg\">\n" + 
//				"                            <div align=\"center\">\n" + 
//				"                                <img src=\"/lnjyzx/Template/Default/images/hulu.png\" width=\"20\" height=\"16\" /></div>\n" + 
//				"                        </td>\n" + 
//				"                        <td width=\"916\" background=\"/lnjyzx/Template/Default/images/index_2.jpg\" align=\"left\">\n" + 
//				"                            <table cellSpacing=\"0\" cellPadding=\"0\" border=\"0\"><TR><TD align=\"center\" width=\"15\"></TD><TD align=\"left\"><FONT color=\"#2f3f56\" class=currentpostionfont><b>您现在的位置：</b></FONT><A href='/lnjyzx/'><FONT color=\"#2f3f56\" class=currentpostionfont>首页</FONT></A><FONT color=\"#CE1002\"> >> <a href='/lnjyzx/xzzx'><FONT color=\"#CE1002\" class=currentpostionfont>下载中心</font></a> >> <a href='/lnjyzx/xzzx/006002'><FONT color=\"#CE1002\" class=currentpostionfont>政府采购</font></a></FONT></TD></TR></table>\n" + 
//				"                        </td>\n" + 
//				"                    </tr>\n" + 
//				"                    <tr>\n" + 
//				"                        <td colspan=\"2\" height=\"11\" background=\"/lnjyzx/Template/Default/images/itali.png\">\n" + 
//				"                        </td>\n" + 
//				"                    </tr>\n" + 
//				"                </table>\n" + 
//				"                <table width=\"990\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n" + 
//				"                    <tr>\n" + 
//				"                        <td height=\"120\" background=\"/lnjyzx/Template/Default/images/3zw_01.gif\" width=\"990\">\n" + 
//				"                            <table width=\"990\" height=\"120\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + 
//				"                                <tr>\n" + 
//				"                                    <td id=\"tdTitle\" align=\"center\" runat=\"server\">\n" + 
//				"                                        <div id=\"detail_title_div\" style=\"width: 900px; word-break: break-word; word-wrap: break-word;\n" + 
//				"                                            padding-top: 20px; overflow: hidden;\">\n" + 
//				"                                            <font color=\"#333333\" style=\"font-size: 22px; font-weight: bold;\">\n" + 
//				"                                                <font style='font-family:;'>抽取专家所需材料</font>\n" + 
//				"                                            </font>\n" + 
//				"                                        </div>\n" + 
//				"                                        <div style=\"display: ;\">\n" + 
//				"                                            \n" + 
//				"                                        </div>\n" + 
//				"                                        <div style=\"height: 20px;\">\n" + 
//				"                                        </div>\n" + 
//				"                                        <div align=\"center\" style=\"height: 26px; width: 870px; line-height: 26px; background-color: #EFEFEF;\n" + 
//				"                                            font-size: 12px;\">\n" + 
//				"                                             <font color=\"#000000\" class=\"webfont\">【信息时间：\n" + 
//				"                                                2018/6/6\n" + 
//				"                                                &nbsp;&nbsp;阅读次数：\n" + 
//				"                                                <script src=\"/lnjyzx/Upclicktimes.aspx?InfoID=84c6d7ff-177f-41d5-bd4f-30f5657c2d6f\"></script>\n" + 
//				"                                                】 <a href=\"javascript:void(0)\" onclick=\"window.print();\"><font color=\"#000000\" class=\"webfont\">\n" + 
//				"                                                    【我要打印】</font></a><a href=\"javascript:window.close()\"><font color=\"#000000\" class=\"webfont\">【关闭】</font></a></font><font\n" + 
//				"                                                        color=\"#000000\"> </font>\n" + 
//				"                                        </div>\n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                                <tr>\n" + 
//				"                                    <td height=\"25\">\n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                            </table>\n" + 
//				"                        </td>\n" + 
//				"                    </tr>\n" + 
//				"\n" + 
//				"                    <tr>\n" + 
//				"                        <td height=\"500\" background=\"/lnjyzx/Template/Default/imagesgh/3zw_02.gif\" align=\"center\" valign=\"top\">\n" + 
//				"                            <table width=\"990\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"tab3\">\n" + 
//				"                                <tr>\n" + 
//				"                                    <td valign=\"top\" align=\"center\" class=\"infodetail\" id=\"TDContent\">\n" + 
//				"                                        <div id=\"newscontentdiv\" style=\"width: 870px; overflow: hidden; word-break: break-word;\n" + 
//				"                                            word-wrap: break-word; text-align: left;\">\n" + 
//				"                                            <img border=\"0\" src=\"http://www.lyggzyjy.cn/TPFrame/eWebEditor/sysimage/icon16/doc.gif\"><a target=\"_blank\" href=\"http://www.lyggzyjy.cn/TPFrame/eWebEditor/uploadfile/20180606155549113.doc\">抽取专家所需材料.doc</a>\n" + 
//				"                                            <img border=\"0\" src=\"http://www.lyggzyjy.cn/TPFrame/eWebEditor/sysimage/icon16/doc.gif\"><a target=\"_blank\" href=\"http://www.lyggzyjy.cn/TPFrame/eWebEditor/uploadfile/20180606155549113.doc\">抽取专家所需材料.doc</a>\n" + 
//				"                                        </div>\n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                                <tr>\n" + 
//				"                                    <td>\n" + 
//				"                                        \n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                                <tr>\n" + 
//				"                                    <td height=\"10\">\n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                                <tr>\n" + 
//				"                                    <td>\n" + 
//				"                                        \n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                                <tr>\n" + 
//				"                                    <td align=\"center\">\n" + 
//				"                                        <div style=\"width: 870px\" align=\"left\">\n" + 
//				"                                            \n" + 
//				"                                        </div>\n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                                <tr>\n" + 
//				"                                    <td align=\"center\" height=\"22\">\n" + 
//				"                                        <div style=\"width: 870px\" align=\"right\">\n" + 
//				"                                            \n" + 
//				"                                        </div>\n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                                <tr>\n" + 
//				"                                    <td height=\"4\">\n" + 
//				"                                    </td>\n" + 
//				"                                </tr>\n" + 
//				"                            </table>\n" + 
//				"                        </td>\n" + 
//				"                    </tr>\n" + 
//				"                </table>\n" + 
//				"            </td>\n" + 
//				"        </tr>\n" + 
//				"    </table>\n" + 
//				"    \n" + 
//				"    \n" + 
//				"   \n" + 
//				"<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tab topd\" background=\"/lnjyzx/Template/Default/images/foot.jpg\">\n" + 
//				"  <tr>\n" + 
//				"    <td height=\"103\" align=\"center\" class=\"white\"><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
//				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + 
//				"<head>\n" + 
//				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\n" + 
//				"<title>洛宁县公共资源交易中心</title>\n" + 
//				"<link href=\"/lnjyzx/Template/Default/css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" + 
//				"</head>\n"; 
//				
//		String importFileRole = "<a target=.*? href=\"(.*?)\">(.*?)</a>";//正则表达式
////		String importFileRole = "<body>.*";//正则表达式
//		Pattern pm = Pattern.compile(importFileRole);//获取正则表达式中的分组，每一组小括号为一组
//		Matcher m = pm.matcher(str);//进行匹配
//		System.out.println(m.find());
//		System.out.println(m.group());
//		
//		List<Map> list = new ArrayList<>();
//        while (m.find()) {
//        	HashMap<Object, Object> map=new HashMap<>();
//        	map.put("url", m.group(1));
//        	map.put("title", m.group(2));
//            list.add(map);
//        }
//        if(list.size()>0) {
//			for(Map<String, String> map: list) {
//				System.out.println(">>>>>>>>>>>>>附件名称"+map.get("title"));
//				System.out.println(">>>>>>>>>>>>>附件名称"+map.get("url"));
//			}
//			
//		}
////        System.out.println(list.get(1).get("url"));
////		if (m.find()) {//判断正则表达式是否匹配到
////		    System.out.println("**************>>>>>>>>>>>>>>>>"+m.groupCount()); ;//通过group来获取每个分组的值，group(0)代表正则表达式匹配到的所有内容，1代表第一个分组
////		    //System.out.println("引入文件是:"+ importFileName);
////		}
		
	}
	
	/**
    * 获取url对应的域名
    *
    * @param url
    * @return
    */
   public String getDomain(String url) {
       String result = "";
       int j = 0, startIndex = 0, endIndex = 0;
       for (int i = 0; i < url.length(); i++) {
           if (url.charAt(i) == '/') {
               j++;
               if (j == 2)
                   startIndex = i;
               else if (j == 3)
                   endIndex = i;
           }

       }
       result = url.substring(startIndex + 1, endIndex);
       return result;
   }

	
}
