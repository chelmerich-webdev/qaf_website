package com.queerartfilm.dao;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class MemcacheDAOTest {

    private MemcacheDAO dao;
    private long id;
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        dao = new MemcacheDAO(MemcacheServiceFactory.getMemcacheService());
    }

    @After
    public void tearDown() {
        helper.tearDown();
        dao = null;
    }

    @Test
    public void testExpiration() {
        int seconds = 5;
        MemcacheDAO expDAO = new MemcacheDAO(MemcacheServiceFactory.getMemcacheService(),
                Expiration.byDeltaSeconds(seconds));
        String key = "key";
        assertNull(expDAO.get(key));
        expDAO.put(key, "value");
        assertNotNull(expDAO.get(key));

        try {
            Thread.sleep(seconds * 2 * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MemcacheDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertNull(expDAO.get(key));

    }

    // run this test twice to prove we're not leaking any state across tests
    private void doTest() {
        MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
        assertFalse(ms.contains("yar"));
        dao.put("yar", "foo");
        assertTrue(ms.contains("yar"));
    }

    @Test
    public void testInsert1() {
        doTest();
    }

    @Test
    public void testInsert2() {
        doTest();
    }

    @Test
    public void testPut() {
        String aKey = "key";
        Serializable aValue = "value";
        dao.put(aKey, aValue);
        assertNotNull(dao.get(aKey));
    }

    @Test
    public void testIsEmpty() {
        String aKey = "key";
        assertNull(dao.get(aKey));
    }
}
