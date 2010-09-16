package com.queerartfilm.deprecated;

import com.google.appengine.api.datastore.KeyFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

/**
 * Google App Engine DAO class for the <code>QafSeriesEventJDO</code> datastore.
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Deprecated // second of three iterations, this one using JDO and the second data scheme
// the QafScreening Event, which i am still using
public class QafSeriesEventDAO {

    private static final QafSeriesEventDAO daoInstance = new QafSeriesEventDAO();

    private QafSeriesEventDAO() {
    }

    public static QafSeriesEventDAO get() {
        return daoInstance;
    }



    public void addEvent(QafSeriesEventJDO event) {
        String title = event.getFilm().getTitle();
        if ( title == null) {
            return;
        }
        event.setKey(KeyFactory.createKey("QafSeriesEvent", createID(title)));

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(event);
        } finally {
            pm.close();
        }
    }

    public QafSeriesEventJDO getEvent(String id) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        return pm.getObjectById(QafSeriesEventJDO.class, id);
    }

    public List<QafSeriesEventJDO> getAllEvents() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Extent<QafSeriesEventJDO> extent = pm.getExtent(QafSeriesEventJDO.class);

        List<QafSeriesEventJDO> events = new ArrayList<QafSeriesEventJDO>();
        for (QafSeriesEventJDO event : extent) {
            events.add(event);
        }

        extent.closeAll();
        return events;
    }

    public void removeEvent(QafSeriesEventJDO event) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        pm.deletePersistent(event);
    }

        public static String createID(String title) {
        char dash = '-';
        boolean isEmpty = true;
        StringBuilder sb = new StringBuilder();
        // loop through each token
        for (StringTokenizer st = new StringTokenizer(title.trim().toLowerCase()); st.hasMoreTokens();) {
            char prev = dash;
            isEmpty = true;

            // add each letter and digit, or a dash if previous was not a dash
            for (char c : st.nextToken().toCharArray()) {
                if (Character.isLetterOrDigit(c) || (c == dash && prev != dash)) {
                    sb.append(c);
                    prev = c;
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                continue;
            }
            sb.append(dash);
        }
        String result = sb.toString().trim();
        return (result.length() > 0 ? result.substring(0, result.length() - 1) : "");  // remove trailing hyphen

    }
}
