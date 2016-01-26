package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FixCustom {
	private long id;
	private String fixContent;
	private long customId;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	//增加修改信息
	public void addFixInfo(String fixContent,long customId){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("insert into fix_custom (fix_content,custom_id) values('"+ fixContent +"',"+ customId +")");
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
