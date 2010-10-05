package com.queerartfilm.dao;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import java.util.logging.Logger;

/**
 * DAO that handles CRUD services with the Google App Engine memcache
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class MemcacheDAO {
    private static final Logger logger = Logger.getLogger(MemcacheDAO.class.getName());
    private MemcacheService cache;
    private Expiration expiration;
    private static final Expiration MAX_EXPIRATION =
            Expiration.byDeltaSeconds(60 * 60 * 4); // 4 hours;

    public MemcacheDAO(MemcacheService cache, Expiration expiration) {
        this.cache = cache;
        this.expiration = expiration;
    }

    public MemcacheDAO(MemcacheService cache) {
        this(cache, MAX_EXPIRATION);
    }

    public MemcacheDAO(Expiration expiration) {
        this();
        this.expiration = expiration;
    }
    
    public MemcacheDAO() {
        this(MemcacheServiceFactory.getMemcacheService(), MAX_EXPIRATION);
    }

    public void put(Object key, Object value) {
        cache.put(key, value, expiration);
        logger.fine("Write to memcache");
    }

    public Object get(Object key) {
        return cache.get(key);
    }

    public boolean delete(Object key) {
        return cache.delete(key);
    }
}
