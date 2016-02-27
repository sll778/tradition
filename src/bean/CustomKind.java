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

public class CustomKind {
	private Long id;
	private String name;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	/**
	 * 使用cache得到所有的种类
	 * @return
	 * 		习俗种类List
	 */
	@SuppressWarnings("unchecked")
	public List<CustomKind> getAllKindByCache(){
		final String sql = "select * from custom_kind";
		final List<CustomKind> result = new ArrayList<CustomKind>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key){
				Element element = cache.get(key); //根据key值取出element
				if(element == null){
					result.addAll(getAllKind(sql));
					element = new Element(key, result);
					cache.put(element);
				} else {
					//从缓存中加载所需要的值
					result.addAll((List<CustomKind>)element.getObjectValue());
				}
			}
		},sql);
		return result;
	}
	
	/**
	 * 得到所有的习俗
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		习俗
	 */
	public List<CustomKind> getAllKind(String sql){
		List<CustomKind> customKinds = new ArrayList<CustomKind>();
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		try {
			pre = conn.prepareStatement(sql);
			res = pre.executeQuery();
			while(res.next()){
				CustomKind customKind = new CustomKind();
				customKind.setId(res.getLong(1));
				customKind.setName(res.getString(2));
				customKinds.add(customKind);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return customKinds;
	}
	
	/**
	 * 使用cache根据Id得到习俗种类
	 * @param id
	 * 		习俗种类编号
	 * @return
	 * 		习俗种类
	 */
	public CustomKind getByIdByCache(Long id){
		final String sql = "select * from custom_kind where id=" + id;
		final List<CustomKind> result = new ArrayList<CustomKind>();
		
		EhcacheUtil.cache(new CallBack(){
			public void doSomething(Cache cache, Integer key){
				CustomKind customKind = new CustomKind();
				Element element = cache.get(key);
				if(element == null){
					customKind = getById(sql);
					element = new Element(key, customKind);
					cache.put(element);
				} else {
					customKind = (CustomKind)element.getObjectValue();
				}
				result.add(customKind);
			}
		}, sql);
		
		return result.get(0);
	}
	
	/**
	 * 这个方法在习俗list界面时需要用到（展示类别）
	 * @param sql
	 * 		sql语句
	 * @return
	 * 		习俗种类
	 */
	public CustomKind getById(String sql){
		DbConn dbc = new DbConn();
		conn = dbc.getConn();
		CustomKind customKind = new CustomKind();
		try {
			pre = conn.prepareStatement(sql);
			res = pre.executeQuery();
			while(res.next()){
				customKind.setId(res.getLong(1));
				customKind.setName(res.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return customKind;
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
	
}
