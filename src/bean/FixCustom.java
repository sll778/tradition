package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FixCustom {
	private long id;
	private String fixContent;
	private long customId;
	private boolean isPass;
	private long userId;
	private boolean status;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	//增加修改信息
	public void addFixInfo(String fixContent,long customId,long userId){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("insert into fix_custom (fix_content,custom_id,user_id) values('"+ fixContent +"',"+ customId +","+ userId +")");
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
	}
	
	//得到所有的审批信息
	public ArrayList<FixCustom> getAll(){
		ArrayList<FixCustom> fixCustoms = new ArrayList();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("select * from fix_custom");
			res = pre.executeQuery();
			while(res.next()){
				FixCustom fixCustom = new FixCustom();
				fixCustom.setId(res.getLong(1));
				fixCustom.setFixContent(res.getString(2));
				fixCustom.setCustomId(res.getLong(3));
				fixCustom.setPass(res.getBoolean(4));
				fixCustom.setUserId(res.getLong(5));
				fixCustom.setStatus(res.getBoolean(6));
				fixCustoms.add(fixCustom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return fixCustoms;
	}
	
	//审批通过
	public void applyPass(Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("update fix_custom set isPass = 1 where id=" + id );
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
	}
	
	//改为已审批状态
	public void isApprove(Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("update fix_custom set status = 1 where id=" + id );
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
	}
	
	//根据id找到fixId
	public Long getUserIdById(Long fixId){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Long userId = 0l;
		try {
			pre = conn.prepareStatement("select user_id from fix_custom where id="+ fixId);
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
	
	public String getContentById(Long fixId){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		String content = "";
		try {
			pre = conn.prepareStatement("select fix_content from fix_custom where id="+ fixId);
			res = pre.executeQuery();
			while(res.next()){
				content = res.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return content;
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

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFixContent() {
		return fixContent;
	}

	public void setFixContent(String fixContent) {
		this.fixContent = fixContent;
	}

	public long getCustomId() {
		return customId;
	}

	public void setCustomId(long customId) {
		this.customId = customId;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
