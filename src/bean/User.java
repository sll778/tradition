package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
	private String logname;
	private String name;
	private String password;
	private String email;
	private Long id;
	private int type;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	
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
	
	//注册
	public void register(String logname,String name,String password,String email){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("insert into user(logname,name,password,email,type) values('"+ logname +"','"+ name +"','"+ password +"','"+ email +"',1)");
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
	}
	
	//后台登录
	public Boolean login(String logname,String password,int type){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Boolean flag = false;
		String passwd = "";
		int type2 = 0;
		
		try {
			pre = conn.prepareStatement("select password,type from user where logname='"+logname+"'");
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
	
	//会员列表
	public ArrayList<User> getAllUser(){
		ArrayList<User> users = new ArrayList();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		
		try {
			pre = conn.prepareStatement("select * from user");
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
	
	//根据logname查询id
	public long getIdByLogname(String logname){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Long userId = 0l;
		try {
			pre = conn.prepareStatement("select id from user where logname='"+ logname + "'");
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
	
	public String getLognameById(Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		String logname = "";
		try {
			pre = conn.prepareStatement("select * from user where id ="+ id);
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
	
	//根据id更改密码
	public void changePassById(Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("update user set password = '1234'");
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	
}
