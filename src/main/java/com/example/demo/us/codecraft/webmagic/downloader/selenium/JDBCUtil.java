package com.example.demo.us.codecraft.webmagic.downloader.selenium;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
public class JDBCUtil {
/**
* JDBCUtil 类  使用时方便连接数据库的一个实体类,在需要使用数据库读写数据得时候,读或者写数据，需要访问
* 数据库的时候,只需要调用JDBCUtils中的方法即可完成对数据库的访问。
*/
//大明测试
//private static String driver="com.mysql.jdbc.Driver"; //驱动
//private static String url="jdbc:mysql://127.0.0.1:3306/mysql?serverTimezone=UTC&characterEncoding=utf8"; //访问数据库的url
//private static String user="root"; //用户名
//private static String password="123"; //密码

////生产
//private static String driver="com.mysql.jdbc.Driver"; //驱动
//private static String url="jdbc:mysql://192.168.1.121:3306/datasource?serverTimezone=UTC&characterEncoding=utf8"; //访问数据库的url
//private static String user="dataclean"; //用户名
//private static String password="yangqi"; //密码

private static String driver; //驱动
private static String url; //访问数据库的url
private static String user; //用户名
private static String password; //密码
//private static String driver="com.mysql.jdbc.Driver"; //驱动
//private static String url="jdbc:mysql://127.0.0.1:3306/sjcj?serverTimezone=UTC&characterEncoding=utf8"; //访问数据库的url
//private static String user="root"; //用户名
//private static String password="root"; //密码

//private static Connection conn = null; //全局的数据库连接


//生成一个构造方法
	public JDBCUtil() {
		super();
	}



	public static Connection getConnection(){
		//设置一个静态代码块
			try {
				/**
				* 1.首先要读取数据可的配置文件,该配置文件中保存了数据库需要使用的驱动名称和数
				* 据库名称和数据库密码等一些信息
				*/
				ClassPathResource resource = new ClassPathResource("db.properties");
				InputStream fis = resource.getInputStream();
				System.out.println("resource>>"+resource);
				System.out.println("getProperty>>>"+System.getProperty("db.properties"));
				//FileReader fr = new FileReader(System.getProperty("db.properties")); //使用JavaIO流读取配置文件
				
				//				/**
//				* 2.读取配置文件后,就是加载配置文件到程序中来
//				*/
				Properties properties = new Properties();
				properties.load(fis); //使用load()函数加载配置文件到程序中来
//				/**
//				* 3.加载配置文件完成,以下就是来读取配置文件中的一些变量的值
//				*/
				driver = properties.getProperty("driver");
				url = properties.getProperty("url");
				user = properties.getProperty("username");
				password = properties.getProperty("password");
				System.out.println("driver>>"+driver);
				System.out.println("url>>"+url);
				System.out.println("user>>"+user);
				System.out.println("password>>"+password);
//				
				} catch (Exception e) {
				e.printStackTrace();
			}
			
			/**
			* 4.定义个全局变量都有值后,开始加载驱动类,获得连接对像
			*/
			Connection conn = null;
			try {
				
				Class.forName(driver); //在本程序中加载驱动类
					conn = (Connection)DriverManager.getConnection(url,user,password); //获得数据可连接对象
					} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("数据库连接失败");
				}
		return conn;
	}
	
	public static void close(ResultSet rs,Statement stat,Connection conn){
		if(rs!=null){
			try {
				rs.close();
				} catch (Exception e) {
				e.printStackTrace();
				}
		}
	
		if(stat != null){
			try {
				stat.close();
				} catch (Exception e) {
				e.printStackTrace();
				}
		}
		
		if(conn != null){
			try {
			conn.close();
			} catch (Exception e) {
			e.printStackTrace();
			}
		}
		}
	
	public static void main(String[] args) {
//		JDBCUtil jd=new JDBCUtil();
//		System.out.println(jd.getConnection());
				
	}

}
