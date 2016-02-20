package cache;

import java.util.ArrayList;

import bean.Custom;

import net.sf.ehcache.*;

public class EhcacheUtil {
	private static CacheManager cacheManager=  null;
	static{
		try {
			cacheManager =  CacheManager.create();
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}
	
	public static CacheManager getCacheManager(){
		return cacheManager;
	}
	
	public static ArrayList<Custom> pageByCache(String sql){
		ArrayList<Custom> customs = null;
		int hash = sql.hashCode();
		CacheManager cacheManager = CacheManager.create();
		//得到testCache
		Cache cache = cacheManager.getCache("testCache");
		//查看cache中是否存在缓存
		Element element = cache.get(hash);
		if(element == null){
			//查询数据库
			Custom custom = new Custom();
			customs = custom.page(sql);
			//将results放进cache中
			cache.put(new Element(hash,customs));
			cacheManager.shutdown();
		}else{
			//如果存在，则从缓存中加载
			customs = (ArrayList) element.getObjectValue();
		}
		return customs;
	}
	
	
}
