package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cache.EhcacheUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class Test {
	private long id;
	private String test;
	
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet res = null;
	
	public ArrayList<Test> getAll(){
		ArrayList results = new ArrayList();
		String code="";
		//创建CacheManager
		CacheManager cacheManager = CacheManager.create();
		//将mcache放进cacheManager
		cacheManager.addCache("testCache");
		//得到testCache
		Cache cache = cacheManager.getCache("testCache");
		
		String sql = "select * from test ";
		int hash = sql.hashCode();
		//查看cache中是否存在缓存
		Element element = cache.get(hash);
		
		//如果不存在，则从数据库中查询
		if(element == null){
			try {
				DbConn dbc = new DbConn();
				conn = dbc.getConn();
				pre = conn.prepareStatement(sql);
				res = pre.executeQuery();
				while(res.next()){
					Test test = new Test();
					test.setId(res.getLong(1));
					test.setTest(res.getString(2));
					results.add(test);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				this.closeAll();
			}
			//将results放进cache中
			cache.put(new Element(hash,results));
			cacheManager.shutdown();
		}else{
			//如果存在，则从缓存中加载
			results = (ArrayList) element.getObjectValue();
		}
		return results;
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

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
