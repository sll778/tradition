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

public class Message {
	private Long id;
	private Long fixId;
	private Long userId;
	private String reason;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	/**
	 * 添加一条消息记录
	 * @param userId
	 * 		用户编号
	 * @param fixId
	 * 		建议修改编号
	 * @param reason
	 * 		审批内容（原因）
	 */
	public void add(Long userId,Long fixId,String reason){
		String sql = "insert into message (userId,fixId,reason) values(?,?,?)";
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			pre.setLong(1, userId);
			pre.setLong(2, fixId);
			pre.setString(3, reason);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		EhcacheUtil.clearCache(sql);
	}
	
	/**
	 * 使用cache得到所有的审批信息
	 * @param userId
	 * 		用户编号
	 * @return
	 * 		审批信息
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessageByCache(Long userId){
		final String sql = "select * from message where userId = " + userId;
		final List<Message> result = new ArrayList<Message>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key){
				Element element = cache.get(key); //根据key值取出element
				if(element == null){
					result.addAll(getAllMessage(sql));
					element = new Element(key, result);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					result.addAll((List<Message>)element.getObjectValue());
				}
			}
		}, sql);
		return result;
	}
	
	/**
	 * 得到所有的消息
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		消息List
	 */
	public List<Message> getAllMessage(String sql){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		List<Message> messages = new ArrayList<Message>();
		try {
			pre = conn.prepareStatement(sql);
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFixId() {
		return fixId;
	}
	public void setFixId(Long fixId) {
		this.fixId = fixId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
