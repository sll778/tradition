package cache;

import net.sf.ehcache.*;

public class EhcacheUtil {
	private static CacheManager cacheManager=  null;
	
	
	public static CacheManager getCacheManager(){
		return cacheManager;
	}
	
	public static void cache(CallBack callBack, String sql){
		//cacheManager初始化
		try {
			cacheManager = CacheManager.create();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		//通过CacheManager获取cache实例（拿到了ehcache.xml配置文件中的testCache）
		Cache cache = cacheManager.getCache("testCache");
		int key = sql.hashCode();
		callBack.doSomething(cache, key);
		//关闭
		cacheManager.shutdown();
	}
	
	public static void clearCache(String sql){
		Integer key = sql.hashCode();
		//cacheManager初始化
		try {
			cacheManager = CacheManager.create();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		Cache cache = cacheManager.getCache("testCache");
		cache.remove(key);
	}
	
}
