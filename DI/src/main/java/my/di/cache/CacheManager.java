package my.di.cache;

import my.di.util.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Hanna
 * Date: 11/12/13
 * Time: 11:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheManager {
    
    private static CacheManager cacheManagerInstance = new CacheManager();
    
    private Map<String,Bean> beanCache = new HashMap<String, Bean>();
    
    public static CacheManager getInstance() {
        return cacheManagerInstance;
    }

    public Map<String, Bean> getBeanCache() {
        return beanCache;
    }
}
