package com.queerartfilm.web;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.ConfigDAO;
import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.dao.QAFSeriesDAO;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.series.QAFSeries;
import com.queerartfilm.validation.IsDateP;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 * @author ch67dotnet
 */
public class ListInitializerListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ListInitializerListener.class.getName());
//    private static final String initFilmJSON = "{title: Initialization Film}";

    private Config initDB(FeaturedFilmDAO featureDAO, QAFSeriesDAO qafSeriesDAO,
            ConfigDAO configDAO, String year) {

        // Check for config first. If present,skip other checks 
        // in order to optimize load time performance
        if (!configDAO.getAllItemKeys().isEmpty()) {
            return configDAO.getConfig();
        }

        //  check if no Featured Film entities in datastore
        if (featureDAO.getAllItemKeys().isEmpty()) {
            logger.info("Feature DB is empty");
            FeaturedFilm feature = new FeaturedFilm();
            feature.setTitle("Initialization Feature Film");
            featureDAO.save(feature);
        }
        Key<FeaturedFilm> featureKey = featureDAO.getAllItemKeys().get(0);
        logger.info("Feature DB OK");

        // check if no QAF Series entities in datastore
        if (qafSeriesDAO.getAllItemKeys().isEmpty()) {
            logger.info("QAFSeries DB is empty");
            QAFSeries qafSeries = new QAFSeries();
            qafSeries.setTitle("Initialization QAF Series");
            qafSeries.addFilmKey(featureKey);
            qafSeriesDAO.update(qafSeries);
        }
        Key<QAFSeries> qafSeriesKey = qafSeriesDAO.getAllItemKeys().get(0);
        logger.info("QAFSeries DB OK id = " + qafSeriesKey.getId());

        if (configDAO.getAllItemKeys().isEmpty()) {
            logger.info("Config DB is empty");
            Config newConfig = new Config();
            newConfig.setFeaturedFilmKey(featureKey);
            newConfig.setQafSeriesKey(qafSeriesKey);
            newConfig.setYear(year);

            configDAO.save(newConfig);
        }
        Config config = configDAO.getConfig(); //configDAO.getAllItems().get(0);
        logger.info("Config DB OK");
        return config;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        // Today's date
        Date today = new Date();
        // Today's year as an integer
        String thisYear = IsDateP.dfYear.format(today);

        FeaturedFilmDAO featureDAO = new FeaturedFilmDAO();
        QAFSeriesDAO qafSeriesDAO = new QAFSeriesDAO();
        ConfigDAO configDAO = new ConfigDAO();

        Config current = initDB(featureDAO, qafSeriesDAO, configDAO, thisYear);
        logger.info("Current Config: " + current);
        context.setAttribute("current", current);

        Map<String, List<String>> menusMap = new HashMap<String, List<String>>();
        String menuParam = "com.queerartfilm.menu.";
        String[] keys = context.getInitParameter(menuParam + "keys").split(",");
        int len = keys.length;
        for (int i = 0; i < len; i++) {
            String[] menuValues = context.getInitParameter(menuParam + keys[i]).split(",");
            menusMap.put(keys[i], Arrays.asList(menuValues));
        }
        context.setAttribute("menusMap", menusMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
