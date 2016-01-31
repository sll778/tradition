package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Message {
	private long id;
	private long fixId;
	private long userId;
	private String reason;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFixId() {
		return fixId;
	}
	public void setFixId(long fixId) {
		this.fixId = fixId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	//添加一条消息记录
	public void add(Long userId,Long fixId,String reason){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("insert into message (userId,fixId,reason) values("+ userId + ", "+ fixId+",'"+ reason +"')" );
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		
	}
	
	public ArrayList<Message> getAllMessage(Long userId){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		ArrayList<Message> messages = new ArrayList();
		try {
			pre = conn.prepareStatement("select * from message where userId = " + userId);
			res = pre.executeQuery();
			while(res.next()){
				Message message = new Message();
				message.setId(res.getLong(1));
				message.setUserId(res.getLong(2));
				message.setFixId(res.getLong(3));
				message.setReason(res.getString(4));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return messages;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	
}
