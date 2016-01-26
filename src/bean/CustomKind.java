package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomKind {
	private Long id;
	private String name;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<CustomKind> getAllKind(){
		ArrayList<CustomKind> customKinds = new ArrayList();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		
		try {
			pre = conn.prepareStatement("select * from custom_kind");
			res = pre.executeQuery();
			while(res.next()){
				CustomKind customKind = new CustomKind();
				customKind.setId(res.getLong(1));
				customKind.setName(res.getString(2));
				customKinds.add(customKind);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customKinds;
			
	}
	
}
