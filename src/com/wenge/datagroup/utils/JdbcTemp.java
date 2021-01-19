package com.wenge.datagroup.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aircom.my.db.base.TimerConnectionPool;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;



/**
 * @author hyw
 * 
 */
public class JdbcTemp {
	
	
	public static TimerConnectionPool pools;
	
	public static String driver = "com.mysql.jdbc.Driver"; 
	
	//远程
	public static String url = "jdbc:mysql://10.99.10.20:3306/" + "meltmedia_data" + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&failOverReadOnly=false";
//								jdbc:mysql://192.168.10.175:3306/yuansousuo?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
	public static String user = "rmt";
	public static String password = "rmt%2020";

	static{
		pools= new TimerConnectionPool(driver, url, user, password);
		pools.setMaxCount(50);
	}
	//java -Djava.ext.dirs=/u01/isi/qtxapp/lib com.wenge.datagroup.main.QTXapp
	public static void main(String[] args) {
		List<String> list = JdbcTemp.showDatabasesForList();
		for(String bases : list){
			System.out.println(bases);
		}
	}
	
	/**
	 * 查询数据库列表
	 * @return
	 */
	public static List<String> showDatabasesForList(){
		
		List<String> resultList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement("show databases");
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				resultList.add(rs.getString(1));
			}
			
		} catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
//				if(conn!=null) conn.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return resultList;
	}
	
	/**
	 * 查询总量
	 * @param sql
	 * @return
	 */
	public static String selectCount(String sql){
//		if(conn==null) conn = new MyConnection().getConnection();
		
		String count = null;
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
//				System.out.println(rs.getString(1));
				count = rs.getString(1);
			}
		} catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
//				if(conn!=null) conn.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return count;
	}
	
	/**
	 * 查询 一个数据库中的表
	 * @return
	 */
	public static List<String> showTablesForList(){
//		if(conn==null) conn = new MyConnection().getConnection();
		
		List<String> resultList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement("show tables");
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
//				System.out.println(rs.getString(1));
				resultList.add(rs.getString(1));
			}
		} catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
//				if(conn!=null) conn.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return resultList;
	}
	
	/**
	 * 查询一条
	 */
	public static Map<String,Object> queryForMap(String sql,Object ...args){
//		if(conn==null) conn = new MyConnection().getConnection();
		
		Map<String, Object> resultMap = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				statement.setObject(i+1, args[i]+"");
			}
			rs = statement.executeQuery();
			String[] sqls = sql.split("[ ]")[1].split(",");
			resultMap = new HashMap<String, Object>();
			while(rs.next()){
				for(int i=0;i<sqls.length;i++){
					resultMap.put(sqls[i], rs.getObject(sqls[i]));
				}
			}
		} catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
				if(rs!=null) rs.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return resultMap;
	}
	
	/**
	 * 查询多条
	 * @param sql
	 * @param args
	 * @return
	 */
	public static List<Map<String,Object>> queryForList(String sql,Object ...args){
		
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				statement.setObject(i+1, args[i]+"");
			}
			rs = statement.executeQuery();
			String[] sqls = sql.split("[ ]")[1].split(",");
			
			while(rs.next()){
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				for(int i=0;i<sqls.length;i++){
					resultMap.put(sqls[i], rs.getObject(sqls[i]));
				}
				resultList.add(resultMap);
			}
		} catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
				if(rs!=null) rs.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return resultList;
	}
	
	/**
	 * 插入数据
	 * @param sql
	 * @param args
	 * @return
	 */
	public static int insertInfo(String sql,Object ...args){
		
		int re = -1;
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				statement.setObject(i+1, args[i]+"");
			}
			statement.execute();
			re=0;
		} catch (MySQLIntegrityConstraintViolationException e) {
			re = -5;
			System.out.println("索引唯一:"+e.getMessage());
		} catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
//				if(conn!=null) conn.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return re;
	}
	
	
	//插入返回主键
	public static int insertInfoReturnId(String sql,Object ...args){
		
		int re = -1;
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			for(int i=0;i<args.length;i++){
				statement.setObject(i+1, args[i]+"");
			}
			statement.executeUpdate();
			ResultSet results = statement.getGeneratedKeys();
            if(results.next()){
            	re = results.getInt(1);
            }
		} catch (MySQLIntegrityConstraintViolationException e) {
			re = -5;
			System.out.println("索引唯一:"+e.getMessage());
		}catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
//			if(conn!=null)
//				try {conn.close();} catch (SQLException e1) {}
//				conn = new MyConnection().getConnection();
		}finally{
			try{
				if(statement!=null) statement.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return re;
	}
	
	
	public static int updateInfo(String sql,Object ...args){
		
		int re = -1;
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pools.fetchConnection();
			statement = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				statement.setObject(i+1, args[i]+"");
			}
			re = statement.executeUpdate();
		} catch (CommunicationsException e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
//				if(conn!=null) conn.close();
				pools.releaseConnection(conn);
			} catch (SQLException e) {
			}
		}
		return re;
	}
	
}
