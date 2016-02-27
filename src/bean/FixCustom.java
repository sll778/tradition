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

/*
 * 申请修改的内容
 */
public class FixCustom {
	private Long id;
	private String fixContent;
	private Long customId;
	private Boolean isPass;
	private Long userId;
	private Boolean status;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	/**
	 * 增加修改建议（会员对习俗内容有意义，提出建议）
	 * @param fixContent
	 * 		修改建议内容
	 * @param customId
	 * 		习俗编号
	 * @param userId
	 * 		用户编号
	 */
	public void addFixInfo(String fixContent,Long customId,Long userId){
		String sql = "insert into fix_custom (fix_content,custom_id,user_id) values(?,?,?)";
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			pre.setString(1, fixContent);
			pre.setLong(2, customId);
			pre.setLong(3, userId);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		EhcacheUtil.clearCache(sql);
	}
	
	/**
	 * 使用cache得到所有建议修改信息
	 * @return
	 * 		建议修改信息List
	 */
	@SuppressWarnings("unchecked")
	public List<FixCustom> getAllByCache(){
		final String sql = "select * from fix_custom order by id desc";
		final List<FixCustom> result = new ArrayList<FixCustom>();
		
		EhcacheUtil.cache(new CallBack() {
			public void doSomething(Cache cache, Integer key){
				Element element = cache.get(key); //根据key值取出element
				if(element == null){
					result.addAll(getAll(sql));
					element = new Element(key, result);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					result.addAll((List<FixCustom>)element.getObjectValue());
				}
			}
		}, sql);
		
		return result;
	}
	
	/**
	 * 得到所有的审批信息 
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		建议修改信息List
	 */
	public List<FixCustom> getAll(String sql){
		List<FixCustom> fixCustoms = new ArrayList<FixCustom>();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
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
	
	/**
	 * 审批通过
	 * @param id
	 * 		建议修改编号
	 */
	public void applyPass(Long id){
		String sql = "update fix_custom set isPass = 1 where id=?";
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
	
	/**
	 * 将建议修改改为已审批状态
	 * @param id
	 * 		建议修改编号
	 */
	public void isApprove(Long id){
		String sql = "update fix_custom set status = 1 where id=?";
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
	
	/**
	 * 根据建议修改编号找到得到提出建议的用户编号
	 * @param fixId
	 * 		修改编号
	 * @return
	 * 		用户编号
	 */
	public Long getUserIdById(Long fixId){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Long userId = 0l;
		try {
			pre = conn.prepareStatement("select user_id from fix_custom where id=?");
			pre.setLong(1, fixId);
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
	 * 根据id找出具体的申请内容
	 * @param fixId
	 * 		建议修改编号
	 * @return
	 * 		建议修改内容
	 */
	public String getContentById(Long fixId){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		String content = "";
		try {
			pre = conn.prepareStatement("select fix_content from fix_custom where id=?");
			pre.setLong(1, fixId);
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
	
	/**
	 * 使用cache根据编号得到建议修改
	 * @param id
	 * 		编号
	 * @return
	 * 		建议修改
	 */
	public FixCustom getByIdByCache(Long id){
		final String sql = "select * from fix_custom where id=" + id;
		final List<FixCustom> result = new ArrayList<FixCustom>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key){
				Element element = cache.get(key); //根据key值取出element
				FixCustom fixCustom = new FixCustom();
				if(element == null){
					fixCustom = getById(sql);
					element = new Element(key, fixCustom);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					fixCustom = (FixCustom)element.getObjectValue();
				}
				result.add(fixCustom);
			}
		}, sql);
		
		return result.get(0);
	}
	
	/**
	 * 根据编号得到建议修改
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		建议修改
	 */
	public FixCustom getById(String sql){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		FixCustom fixCustom = new FixCustom();
		try {
			pre = conn.prepareStatement(sql);
			res = pre.executeQuery();
			while(res.next()){
				fixCustom.setId(res.getLong(1));
				fixCustom.setFixContent(res.getString(2));
				fixCustom.setCustomId(res.getLong(3));
				fixCustom.setPass(res.getBoolean(4));
				fixCustom.setUserId(res.getLong(5));
				fixCustom.setStatus(res.getBoolean(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fixCustom;
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
