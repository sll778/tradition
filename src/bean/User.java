package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cache.CallBack;
import cache.EhcacheUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class User {
	private 	String 				logname;
	private 	String 				name;
	private 	String 				password;
	private 	String 				email;
	private 	Long 				id;
	private 	Integer 			type;
	
	private 	Connection 			conn 		= 	null;
	private 	PreparedStatement 	pre 		= 	null;
	private 	ResultSet 			res 		= 	null;
	
	/**
	 * 注册
	 * @param logname
	 * 		登录名
	 * @param name
	 * 		姓名
	 * @param password
	 * 		密码
	 * @param email
	 * 		邮箱
	 */
	public void register(String logname,String name,String password,String email){
		String sql = "insert into user(logname,name,password,email,type) values(?,?,?,?,?)";
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			pre.setString(1, logname);
			pre.setString(2, name);
			pre.setString(3, password);
			pre.setString(4, email);
			//type为1，即为前台用户
			pre.setInt(5, 1);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		EhcacheUtil.clearCache(sql);
	}
	
	/**
	 * 后台用户登录
	 * @param logname
	 * 		登录名
	 * @param password
	 * 		密码
	 * @param type
	 * 		用户类型
	 * @return
	 * 		是否登录成功
	 */
	public Boolean login(String logname,String password,Integer type){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Boolean flag = false;
		String passwd = "";
		Integer type2 = 0;
		
		try {
			pre = conn.prepareStatement("select password,type from user where logname=?");
			pre.setString(1, logname);
			res = pre.executeQuery();
			if(res.next()){
				passwd = res.getString(1);
				type2 = res.getInt(2);
			}
			if((passwd.equals(password)) && (type==type2)){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return flag;
	}
	
	/**
	 * 使用cache得到所有的用户（会员管理）
	 * @return
	 * 		用户List
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUserByCache(){
		final String sql = "select * from user";
		final List<User> result = new ArrayList<User>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key) {
				Element element = cache.get(key); //根据key值取出element
				if(element == null){
					result.addAll(getAllUser(sql));
					element = new Element(key, result);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					result.addAll((List<User>)element.getObjectValue());
				}
			}
		}, sql);
		return result;
	}
	
	/**
	 * 得到所有的用户
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		用户List
	 */
	public List<User> getAllUser(String sql){
		List<User> users = new ArrayList<User>();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		
		try {
			pre = conn.prepareStatement(sql);
			res = pre.executeQuery();
			while(res.next()){
				User user = new User();
				user.setId(res.getLong(1));
				user.setLogname(res.getString(2));
				user.setName(res.getString(3));
				user.setEmail(res.getString(5));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return users;
	} 
	
	
	/**
	 * 使用cache根据登录名得到用户编号
	 * @param logname
	 * 		登录名
	 * @return
	 * 		用户编号
	 */
	public long getIdByLognameByCache(String logname){
		final String sql = "select id from user where logname='"+ logname + "'";
		final List<Long> result = new ArrayList<Long>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key){
				Long userId = null;
				Element element = cache.get(key);
				if(element == null){
					userId = getIdByLogname(sql);
					element = new Element(key, userId);
					cache.put(element);
				} else {
					userId = (Long)element.getObjectValue();
				}
				result.add(userId);
			}
		}, sql);
		return result.get(0);
	}
	
	/**
	 * 根据登录名得到用户编号
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		用户编号
	 */
	public Long getIdByLogname(String sql){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Long userId = 0l;
		try {
			pre = conn.prepareStatement(sql);
			res = pre.executeQuery();
			while(res.next()){
				userId = res.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return userId;
	}
	
	/**
	 * 根据用户编号得到登录名
	 * @param id
	 * 		用户编号
	 * @return
	 * 		登录名
	 */
	public String getLognameById(Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		String logname = "";
		try {
			pre = conn.prepareStatement("select * from user where id =?");
			pre.setLong(1, id);
			res = pre.executeQuery();
			while(res.next()){
				logname = res.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return logname;
	}
	
	/**
	 * 根据id更改密码
	 * @param id
	 * 		用户编号
	 */
	public void changePassById(Long id){
		String sql = "update user set password = '1234' where id=?";
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			pre.setLong(1, id);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		EhcacheUtil.clearCache(sql);
	}
	
	public void closeAll(){
		
		if(pre!=null){
			try {
				pre.close();
				pre=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
				conn=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getLogname() {
		return logname;
	}
	public void setLogname(String logname) {
		this.logname = logname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	
}
