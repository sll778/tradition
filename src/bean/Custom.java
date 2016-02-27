package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import cache.CallBack;
import cache.EhcacheUtil;


/**
 * @author sll
 * 习俗类
 *
 */
public class Custom {
	
	private Long id;
	private String name;
	private String content;
	private Long kindId;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	/**
	 * 习俗的分页显示
	 * @param pageSize
	 * 		某一页显示的记录条数
	 * @param pageNow
	 * 		当前页
	 * @return
	 * 		习俗List
	 */
	@SuppressWarnings("unchecked")
	public List<Custom> pageByCache(Integer pageSize, Integer pageNow){
		final String sql = "select id,name,content,kindId from custom limit "+ pageSize*(pageNow-1) + "," + pageSize*pageNow;
		final List<Custom> result = new ArrayList<Custom>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key) {
				Element element = cache.get(key); //根据key值取出element
				if(element == null){
					result.addAll(page(sql));
					element = new Element(key, result);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					result.addAll((List<Custom>) element.getObjectValue());
				}
			}
		}, sql);
		
		return result;
	}
	
	/**
	 * 根据页面大小和当前的页码显示记录（数据库操作）
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		习俗List
	 */
	public List<Custom> page(String sql){
		List<Custom> customs = new ArrayList<Custom>();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
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
	
	/**
	 * 向习俗中增加记录
	 * @param name
	 * 		习俗名
	 * @param content
	 * 		习俗内容
	 * @param customKind
	 * 		习俗类别
	 */
	public void add(String name,String content,Long customKind){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		String sql = "insert into custom (name,content,kindId) values(?,?,?)";
		try {
			pre = conn.prepareStatement(sql);
			pre.setString(1, name);
			pre.setString(2, content);
			pre.setLong(3, customKind);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		EhcacheUtil.clearCache(sql);
	}
	
	
	/**
	 * 删除习俗
	 * @param id
	 * 		习俗编号
	 */
	public void delete(Long id){
		String sql = "delete from custom where id =?";
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			pre.setLong(1, id);
			pre.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		EhcacheUtil.clearCache(sql);
	}
	
	/**
	 * 使用cache的方法查找习俗
	 * @param id
	 * 		习俗编号
	 * @return
	 * 		习俗
	 */
	public Custom viewByCache(Long id){
		final String sql="select id,name,content from custom where id="+ id;
		//将对象定义成final常量时，不能给对象赋值，所以先定义一个list，将对象放进list中
		final List<Custom> result = new ArrayList<Custom>();
		
		EhcacheUtil.cache(new CallBack() {
			public void doSomething(Cache cache, Integer key) {
				Custom custom = new Custom();
				Element element = cache.get(key); //根据key值取出element
				if(element == null){
					custom = view(sql);
					element = new Element(key, custom);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					custom = (Custom) element.getObjectValue();
				}
				result.add(custom);
			}
		}, sql);
		
		return result.get(0);
	}
	
	/**
	 * 查看某一个习俗
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		习俗
	 */
	public Custom view(String sql){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		Custom custom = new Custom();
		try {
			pre=conn.prepareStatement(sql);
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
	
	/**
	 * 使用cache的方法实现搜索
	 * @param keyword
	 * 		关键词（习俗名）
	 * @return
	 * 		习俗List
	 */
	@SuppressWarnings("unchecked")
	public List<Custom> searchByCache(String keyword){
		final String sql = "select * from custom where name like '%"+ keyword +"%'";
		final List<Custom> result = new ArrayList<Custom>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key){
				Element element = cache.get(key); //根据key值取出element
				if(element == null){
					result.addAll(search(sql));
					element = new Element(key, result);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					result.addAll((List<Custom>)element.getObjectValue());
				}
			}
		}, sql);
		return result;
	}
	
	/**
	 * 习俗搜索功能
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		习俗List
	 */
	public List<Custom> search(String sql){
		List<Custom> customs = new ArrayList<Custom>();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			res = pre.executeQuery();
			while(res.next()){
				Custom custom = new Custom();
				custom.setId(res.getLong(1));
				custom.setName(res.getString(2));
				custom.setContent(res.getString(3));
				custom.setKindId(res.getLong(5));
				customs.add(custom);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return customs;
	}
	
	/**
	 * 对习俗编辑信息的更新
	 * @param name
	 * 		习俗名
	 * @param content
	 * 		习俗内容
	 * @param kindId
	 * 		习俗种类编号
	 * @param id
	 * 		习俗编号
	 */
	public void update(String name,String content,Long kindId,Long id){
		String sql = "update custom set name=?,content=?,kindId=? where id=?";
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			pre.setString(1, name);
			pre.setString(2, content);
			pre.setLong(3, kindId);
			pre.setLong(4, id);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		EhcacheUtil.clearCache(sql);
	}
	
	/**
	 * 关闭数据库
	 */
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

	public Long getKindId() {
		return kindId;
	}

	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}


}
