package com.example.demo.us.codecraft.webmagic.downloader.selenium;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

/**
 * 属性文件获取工具类(仅json)
 *
 * @author WangZhenkun on 18-8-1
 */
public class PropertiesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
    /*
     * 根据json文件名称获取json配置文件数据
     *
     * @param fileName json文件名称前缀，如果在resource下直接写文件名，如果有路径，请在前面添加路径如："com/xxx/abc"
     */
    public static JSONObject getJsonResource(String fileName){
        fileName+=".json";
        ClassLoader classLoader = getClassLoader();

        Enumeration<URL> resources;
        JSONObject jsonObject = new JSONObject();
        try {
            resources = classLoader.getResources(fileName);
        } catch (IOException e) {
            LOGGER.warn("getJsonResource fail {}", fileName, e);
            return jsonObject;
        }

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            try {
                String json = Resources.toString(url, Charsets.UTF_8);
                jsonObject.putAll(JSON.parseObject(json)); // 有多个的时候，后面的覆盖前面的
            } catch (IOException e) {
                LOGGER.warn("addJsonFile fail url:{}", url, e);
            }
        }
        return jsonObject;
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return PropertiesUtils.class.getClassLoader();
    }

//    /**
//     * 私有构造方法，防止工具类被new
//     */
//    private PropertiesUtils() {
//        throw new IllegalAccessError();
//    }
    
    
    public static String json2String(String path) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = new FileInputStream(path);
        //读取文件上的数据。  
        // 将字节流向字符流的转换。  
        InputStreamReader isr = new InputStreamReader(in,"UTF-8");//读取  
        //创建字符流缓冲区  
        BufferedReader bufr = new BufferedReader(isr);//缓冲  
 
        String line = null;
        while ((line = bufr.readLine()) != null) {
            result.append(System.lineSeparator()+line);
        }
        isr.close();
        return result.toString();
    }
}