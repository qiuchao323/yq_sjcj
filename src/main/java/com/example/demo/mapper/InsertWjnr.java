package com.example.demo.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.example.demo.us.codecraft.webmagic.downloader.selenium.JDBCUtil;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;

public class InsertWjnr {
	JDBCUtil db=new JDBCUtil();
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	int a;
	public int insertWjnr(String xmmc,String wjljdz,String wynr, String tname, String id) throws SQLException {
		// TODO Auto-generated method stub
		 Connection con=JDBCUtil.getConnection();
		 String sql = "insert into "+tname+" (xmmc,wyljdz,body,cjsj) values(?,?,?,now())";		//插入sql语句
			try {
				ps = con.prepareStatement(sql);

				ps.setString(1, xmmc);
				ps.setString(2, wjljdz);
				ps.setString(3, wynr);
				 a=ps.executeUpdate();			//执行sql语句
				System.out.println(xmmc+"插入成功(*￣︶￣)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				con.close();
			}


		return a;
	}

	/**
	 * 查询系统参数设置
	 * @throws SQLException
	 */

	public List<Map<String, String>> getSysPrams() throws SQLException {
		List<Map<String, String>> sysparamlist=new ArrayList<Map<String, String>>() ;
		 Connection con=JDBCUtil.getConnection();
		 String sql = "select * from sysparams";		//插入sql语句
			try {
				ps = con.prepareStatement(sql);
				 rs=ps.executeQuery();			//执行sql语句


				 while(rs.next()){
					HashMap<String, String> map=new HashMap<String, String>();
				 	map.put("sftc", rs.getString("sftc"));
				 	sysparamlist.add(map);
	                }
				System.out.println("插入成功(*￣︶￣)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				con.close();
			}

		return sysparamlist;
		// TODO Auto-generated method stub

	}

	public void alertstatus() throws SQLException {
		// TODO Auto-generated method stub
		 Connection con=JDBCUtil.getConnection();
		 String sql = "update selnium_startorstop_table t set t.stuts='0'";		//插入sql语句
			try {
				ps = con.prepareStatement(sql);
				 a=ps.executeUpdate();			//执行sql语句
				System.out.println("状态修改为关闭");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				con.close();
			}

	}


	public int insertUrl(String bt, List<WebElement> findElements, List<WebElement> findElements2, String ym) throws SQLException {
		// TODO Auto-generated method stub
		 Connection con=JDBCUtil.getConnection();
		 con.setAutoCommit(false);
		 String sql = "insert into selnium_save_url (wjbt,url,ctime) values(?,?,now())";		//插入sql语句
			try {
			ps = con.prepareStatement(sql);
			 for(int i=0;i<findElements.size();i++){
				 String href=findElements.get(i).getAttribute("href");
				 if(href.endsWith(".doc")||href.endsWith(".docx")
						 ||href.endsWith(".xls")||href.endsWith(".xlsx")||
						 href.endsWith(".png")||href.endsWith(".jpg")
						 ||href.endsWith(".jpeg")||href.endsWith(".pdf")) {
					 	ps.setString(1, bt);
						ps.setString(2, href.startsWith("http") ? href:ym+href);
			            ps.addBatch();

				 }

		        }
			 for(int i=0;i<findElements2.size();i++){
				 String href=findElements2.get(i).getAttribute("src");
				 if(href.endsWith(".doc")||href.endsWith(".docx")
						 ||href.endsWith(".xls")||href.endsWith(".xlsx")||
						 href.endsWith(".png")||href.endsWith(".jpg")
						 ||href.endsWith(".jpeg")||href.endsWith(".pdf")) {
					 	ps.setString(1, bt);
						ps.setString(2, href.startsWith("http") ? href:ym+href);
			            ps.addBatch();

				 }
			 }
				ps.executeBatch();//执行sql语句
				con.commit();
				System.out.println("url插入成功(*￣︶￣)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				con.close();
			}

		return a;
	}


	public int insertPageUrl(String bt, DomNodeList<DomElement> domNodeList, DomNodeList<DomElement> domNodeList2, String ym) throws SQLException {
		// TODO Auto-generated method stub
		 Connection con=JDBCUtil.getConnection();
		 con.setAutoCommit(false);
		 String sql = "insert into selnium_save_url (wjbt,url,ctime) values(?,?,now())";		//插入sql语句
			try {
			ps = con.prepareStatement(sql);
			 for(int i=0;i<domNodeList.size();i++){
				 String href=domNodeList.get(i).getAttribute("href");
				 if(href.endsWith(".doc")||href.endsWith(".docx")
						 ||href.endsWith(".xls")||href.endsWith(".xlsx")||
						 href.endsWith(".png")||href.endsWith(".jpg")
						 ||href.endsWith(".jpeg")||href.endsWith(".pdf")) {
					 	ps.setString(1, bt);
						ps.setString(2, href.startsWith("http") ? href:ym+href);
			            ps.addBatch();

				 }
		        }
			 for(int i=0;i<domNodeList2.size();i++){
				 String href=domNodeList2.get(i).getAttribute("src");
				 if(href.endsWith(".doc")||href.endsWith(".docx")
						 ||href.endsWith(".xls")||href.endsWith(".xlsx")||
						 href.endsWith(".png")||href.endsWith(".jpg")
						 ||href.endsWith(".jpeg")||href.endsWith(".pdf")) {
					 	ps.setString(1, bt);
						ps.setString(2, href.startsWith("http") ? href:ym+href);
			            ps.addBatch();

				 }
			 }
				ps.executeBatch();//执行sql语句
				con.commit();
				System.out.println("url插入成功(*￣︶￣)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				con.close();
			}

		return a;
	}


}
