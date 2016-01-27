package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Custom {
	
	private long id;
	private String name;
	private String content;
	private long kindId;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	public void add(String name,String content,Long customKind){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		
		try {
			pre = conn.prepareStatement("insert into custom (name,content,kindId) values('"+ name +"','"+ content +"',"+ customKind +")");
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		
	}
	
	//根据页面大小和当前的页码显示记录
	public ArrayList<Custom> page(int pageSize,int pageNow ){
		ArrayList<Custom> customs = new ArrayList();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("select id, name,content,kindId from custom limit "+ pageSize*(pageNow-1) + "," + pageSize*pageNow);
			res = pre.executeQuery();
			while(res.next()){
				Custom custom  = new Custom();
				custom.setId(res.getInt(1));
				custom.setName(res.getString(2));
				custom.setContent(res.getString(3));
				custom.setKindId(res.getLong(4));
				customs.add(custom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return customs;
	}
	
	public void delete(Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("delete from custom where id ="+ id);
			pre.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
	}
	
	//查看某个习俗的详情
	public Custom view(Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Custom custom = new Custom();
		try {
			pre=conn.prepareStatement("select id,name,content from custom where id="+ id);
			res = pre.executeQuery();
			while(res.next()){
				custom.setId(res.getLong(1));
				custom.setName(res.getString(2));
				custom.setContent(res.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return custom;
	}
	
	//搜索功能
	public ArrayList<Custom> search(String keyword){
		ArrayList<Custom> customs = new ArrayList();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement("select * from custom where name like '%"+ keyword +"%'");
			res = pre.executeQuery();
			while(res.next()){
				Custom custom = new Custom();
				custom.setId(res.getLong(1));
				custom.setName(res.getString(2));
				custom.setContent(res.getString(3));
				custom.setKindId(res.getLong(4));
				customs.add(custom);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return customs;
	}
	
	//对习俗编辑信息的更新
	public void update(String name,String content,Long kindId,Long id){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Custom custom = new Custom();
		try {
			pre = conn.prepareStatement("update custom set name='"+ name +"',content='"+ content +"',kindId="+ kindId +" where id="+id);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

}
