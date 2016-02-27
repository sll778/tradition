package bean;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConn {
	private Connection conn;
	
	/**
	 * 数据库加载驱动
	 * @return
	 * 		连接
	 */
	public Connection getConn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tradition","root","sll");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}
