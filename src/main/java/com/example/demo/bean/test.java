package com.example.demo.bean;

import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import us.codecraft.webmagic.selector.Html;

public class test {
	public static void main(String args[]) throws Exception{
		 final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

	        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
	        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
	        webClient.getOptions().setActiveXNative(false);
	        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
	        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
	        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

	        HtmlPage page = null;
	        try {
	            page = webClient.getPage("http://ggzy.shaoyang.gov.cn/%e6%96%b0%e6%b5%81%e7%a8%8b/%e6%8b%9b%e6%8a%95%e6%a0%87%e4%bf%a1%e6%81%af/jyxx_x.aspx?iq=x&type=%e6%8b%9b%e6%a0%87%e5%85%ac%e5%91%8a&tpid=5da69971f0480686543d551f#title_%E6%8B%9B%E6%A0%87%E5%85%AC%E5%91%8A");//尝试加载上面图片例子给出的网页
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            webClient.close();
	        }
	      
	        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
	        List<HtmlElement> spanList=(List<HtmlElement>) page.getByXPath("//*[@id=\"baseInfo\"]/tbody/tr[9]/td/span/p[1]/text()");
            for(int i=0;i<spanList.size();i++) {
                //asText ==> innerHTML ✔
                System.out.println(i+1+"、"+spanList.get(i));
            }
            List<HtmlElement> nrList=(List<HtmlElement>) page.getByXPath("//*[@id=\"c\"]");
            for(int i=0;i<nrList.size();i++) {
            	//asText ==> innerHTML ✔
            	System.out.println(i+1+"、"+nrList.get(i));
            }
	        
	        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串

	        //TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库

	        Document document = Jsoup.parse(pageXml);//获取html文档
	        System.out.println(">>**************************>>"+pageXml);
	        Html h=null;
			h=new Html(pageXml);
//			System.out.println(">>>>>>html>>>>"+h.xpath("//*[@id=\"c\"]/tbody/tr[2]/td/text()"));
			System.out.println(">>>>>>html>>>>"+h.xpath("//*[@id=\"c\"]"));
	        
//	        List<Element> infoListEle = document.getElementById("feedCardContent").getElementsByAttributeValue("class", "feed-card-item");//获取元素节点等
//	        infoListEle.forEach(element -> {
//	            System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").text());
//	            System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").attr("href"));
//	        });
		
		
	}
}
