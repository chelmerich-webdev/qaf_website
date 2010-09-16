package com.queerartfilm.dao;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.series.QAFSeries;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class QAFSeriesDAOTest {

    private QAFSeriesDAO dao;
    private QAFSeries series;
    private long id;
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        dao = new QAFSeriesDAO();
        series = new QAFSeries();
        id = 2;

    }

    @After
    public void tearDown() {
        helper.tearDown();
        dao = null;
        series = null;
    }

    @Test
    public void testSave() {
        assertNull(dao.get(id));
        series.setId(id);
        Key<QAFSeries> key = dao.save(series);
        assertNotNull(dao.get(id));

        Key<QAFSeries> expected = new Key<QAFSeries>(QAFSeries.class, id);
        assertEquals(expected, key);

    }

    @Test
    public void testDelete() {
        FeaturedFilmDAO filmDAO = new FeaturedFilmDAO();
        Key<FeaturedFilm> keyA = filmDAO.save(new FeaturedFilm());

        series.setId(id);
        series.addFilmKey(keyA);
        dao.update(series);

        assertNotNull(dao.get(id));
        assertTrue(series.getId() == dao.ofy().get(keyA).getSeriesKey().getId());

        dao.delete(series);
        assertNull(dao.get(id));
        assertNull(dao.ofy().get(keyA).getSeriesKey());
    }

    @Test
    public void testGet() {
        series.setId(id);
        Key<QAFSeries> key = dao.save(series);
        assertNotNull(dao.get(id));
        Key<QAFSeries> expected = new Key<QAFSeries>(QAFSeries.class, id);
        assertTrue(id == dao.get(id).getId());
    }

    @Test
    public void testUpdateUnstoredSeries() {
        assertNull(series.getId());
        series.setId(id);
        assertTrue(id == series.getId());

        assertNull(dao.ofy().find(QAFSeries.class, series.getId()));

        FeaturedFilmDAO filmDAO = new FeaturedFilmDAO();
        Key<FeaturedFilm> keyA = filmDAO.save(new FeaturedFilm());
        Key<FeaturedFilm> keyB = filmDAO.save(new FeaturedFilm());
        Key<FeaturedFilm> keyC = filmDAO.save(new FeaturedFilm());

        SortedSet<Key<FeaturedFilm>> keyset = new TreeSet<Key<FeaturedFilm>>();
        keyset.add(keyA);
        keyset.add(keyB);
        keyset.add(keyC);

        series.setFilmKeys(keyset);
        assertTrue(keyset.size() == series.getFilmKeys().size());

        FeaturedFilm filmA = filmDAO.get(keyA.getId());
        FeaturedFilm filmB = filmDAO.get(keyB.getId());
        FeaturedFilm filmC = filmDAO.get(keyC.getId());

        assertNull(filmA.getSeriesKey());
        assertNull(filmB.getSeriesKey());
        assertNull(filmC.getSeriesKey());

        dao.update(series);
        assertNotNull(dao.ofy().find(QAFSeries.class, series.getId()));
        
        filmA = filmDAO.get(keyA.getId());
        filmB = filmDAO.get(keyB.getId());
        filmC = filmDAO.get(keyC.getId());

        assertNotNull(filmA.getSeriesKey());
        assertNotNull(filmB.getSeriesKey());
        assertNotNull(filmC.getSeriesKey());

    }

    @Test
    public void testUpdate() {

        FeaturedFilmDAO filmDAO = new FeaturedFilmDAO();

        Key<FeaturedFilm> keyA = filmDAO.save(new FeaturedFilm());
        Key<FeaturedFilm> keyB = filmDAO.save(new FeaturedFilm());
        Key<FeaturedFilm> keyC = filmDAO.save(new FeaturedFilm());

        SortedSet<Key<FeaturedFilm>> keyset = new TreeSet<Key<FeaturedFilm>>();
        keyset.add(keyA);
        keyset.add(keyB);
        keyset.add(keyC);

        series.setFilmKeys(keyset);

        FeaturedFilm filmA = filmDAO.get(keyA.getId());
        FeaturedFilm filmB = filmDAO.get(keyB.getId());
        FeaturedFilm filmC = filmDAO.get(keyC.getId());

        dao.update(series);
        series = dao.get(series.getId());

        assertTrue(series.getFilmKeys().contains(keyA));
        assertTrue(series.getFilmKeys().contains(keyB));
        assertTrue(series.getFilmKeys().contains(keyC));

        series.removeFilmKey(keyB);

        // updated has one less child
        dao.update(series);
        series = dao.get(series.getId());

        assertTrue(series.getFilmKeys().contains(keyA));
        assertFalse(series.getFilmKeys().contains(keyB));
        assertTrue(series.getFilmKeys().contains(keyC));
        filmB = filmDAO.get(keyB.getId());
        assertNull(filmB.getSeriesKey());

        series.removeFilmKey(keyA);
        series.removeFilmKey(keyC);
        assertTrue(series.getFilmKeys().isEmpty());

        // updated has no children
        dao.update(series);
        series = dao.get(series.getId());
        assertFalse(series.getFilmKeys().contains(keyA));
        assertFalse(series.getFilmKeys().contains(keyB));
        assertFalse(series.getFilmKeys().contains(keyC));

        // children have no parent
        filmA = filmDAO.get(keyA.getId());
        filmB = filmDAO.get(keyB.getId());
        filmC = filmDAO.get(keyC.getId());
        assertNull(filmA.getSeriesKey());
        assertNull(filmB.getSeriesKey());
        assertNull(filmC.getSeriesKey());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateOnNull() {
        dao.update(null);
    }
}
