package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String logname;
	private String name;
	private String password;
	private String email;
	
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
			pre = conn.prepareStatement("insert into user values('"+ logname +"','"+ name +"','"+ password +"','"+ email +"')");
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean login(String logname,String password){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Boolean flag = false;
		String passwd = null;
		
		try {
			pre = conn.prepareStatement("select password from user where logname='"+logname+"'");
			res = pre.executeQuery();
			if(res.next()){
				passwd = res.getString(1);
			}
			if(passwd.equals(password)){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
