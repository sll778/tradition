package cache;


import net.sf.ehcache.Cache;

public interface CallBack {
	public void doSomething(Cache cache, Integer key);
}
