package com.queerartfilm.web.deprecated;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.ConfigDAO;
import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.dao.QAFSeriesDAO;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.film.Rating;
import com.queerartfilm.film.Venue;
import com.queerartfilm.series.QAFSeries;
import com.queerartfilm.validation.IsDateP;
import com.queerartfilm.web.Config;
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
public class ListInitializerListener1 implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ListInitializerListener1.class.getName());
//    private static final String initFilmJSON = "{title: Initialization Film}";

    private Config initDB(FeaturedFilmDAO featureDAO, QAFSeriesDAO qafSeriesDAO,
            ConfigDAO configDAO, String year) {

//        // if no film in database, add one. Get filmKey for PresentationEvent
//        if (filmDAO.getAllItemKeys().isEmpty()) {
//            logger.info("Film DB is empty");
//            try {
//                Film film = FilmFactory.get().newFilm(new JSONObject(initFilmJSON));
//                filmDAO.save(film);
//            } catch (JSONException ex) {
//                logger.warning(ex.getMessage());
//            }
//        }
//        Key<Film> filmKey = filmDAO.getAllItemKeys().get(0);
//        logger.info("Film DB OK");

        //  check if no Featured Film entities in datastore
        if (featureDAO.getAllItemKeys().isEmpty()) {
            logger.info("Feature DB is empty");
            FeaturedFilm feature = new FeaturedFilm();
            feature.setTitle("Initialized Feature");
            featureDAO.save(feature);
        }
        Key<FeaturedFilm> featureKey = featureDAO.getAllItemKeys().get(0);
        logger.info("Feature DB OK");

        // check if no QAF Series entities in datastore
        if (qafSeriesDAO.getAllItemKeys().isEmpty()) {
            logger.info("QAFSeries DB is empty");
            QAFSeries qafSeries = new QAFSeries();
            qafSeries.setTitle("Initialized QAF Series");
            qafSeries.addFilmKey(featureKey);
            qafSeriesDAO.update(qafSeries);
        }
        Key<QAFSeries> qafSeriesKey = qafSeriesDAO.getAllItemKeys().get(0);
        logger.info("QAFSeries DB OK id = " + qafSeriesKey.getId());

//        // check if no events in database
//        if (eventDAO.getAllItemKeys().isEmpty()) {
//            logger.info("Event DB is empty");
//            try {
//                String initEventJSON = new JSONStringer().object().key("filmKey").value(filmKey.getName()).endObject().toString();
//
//                Event event = EventFactory.get().newEvent(new JSONObject(initEventJSON));
//                eventDAO.save(event);
//            } catch (JSONException ex) {
//                logger.warning(ex.getMessage());
//            }
//        }
//        Key<Event> eventKey = eventDAO.getAllItemKeys().get(0);
//        logger.info("Events DB OK");
//
//        if (seriesDAO.getAllItemKeys().isEmpty()) {
//            logger.info("Series DB is empty");
//            try {
//                String initSeriesJSON = new JSONStringer().object().key("eventKey").value(eventKey.getId()).endObject().toString();
//
//                Series series = new Series(new JSONObject(initSeriesJSON));
//
//                seriesDAO.save(series);
//
//            } catch (JSONException ex) {
//                logger.warning(ex.getMessage());
//            }
//        }
//        Key<Series> seriesKey = seriesDAO.getAllItemKeys().get(0);
//        logger.info("Series DB OK");

        if (configDAO.getAllItemKeys().isEmpty()) {
            logger.info("Config DB is empty");
//            Config newConfig = new Config();
//            newConfig.setFeaturedFilmKey(featureKey);
//            newConfig.setQafSeriesKey(qafSeriesKey);
////            newConfig.setSeries(seriesKey);
////            newConfig.setEvent(eventKey);
//            newConfig.setYear(year);
//
//            configDAO.save(newConfig);
        }
        Config config = configDAO.getConfig(); //configDAO.getAllItems().get(0);
        logger.info("Config DB OK");
        return config;
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        // Today's date
        Date today = new Date();
        // Today's year as an integer
        String thisYear = IsDateP.dfYear.format(today);

//        SeriesDAO seriesDAO = new SeriesDAO();
//        EventDAO eventDAO = new EventDAO();
//        FilmDAO filmDAO = new FilmDAO();

        FeaturedFilmDAO featureDAO = new FeaturedFilmDAO();
        QAFSeriesDAO qafSeriesDAO = new QAFSeriesDAO();
        ConfigDAO configDAO = new ConfigDAO();

        Config current = initDB(featureDAO, qafSeriesDAO, configDAO, thisYear);
        logger.info("Current Config: " + current);
        context.setAttribute("current", current);


        // List of all the months
        List<String> monthList = Arrays.asList(
                "January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December");
        context.setAttribute("monthList", monthList);


        // Enum values aren't available to EL, so pass in as attributes
        // Venue enum values
        List<Rating> ratingsList = Arrays.asList(Rating.values());
        context.setAttribute("ratingsList", ratingsList);

        // Rating enum values
        List<Venue> venueList = Arrays.asList(Venue.values());
        context.setAttribute("venueList", venueList);


        Map<String, String> menuMap = new HashMap<String, String>();
        String[] labels = context.getInitParameter("com.queerartfilm.menu.labels").split(",");
        String[] uris = context.getInitParameter("com.queerartfilm.menu.uris").split(",");
        int len = Math.min(labels.length, uris.length); // prevents index error
        for (int i = 0; i < len; i++) {
            menuMap.put(labels[i], uris[i]);
        }
        context.setAttribute("menuMap", menuMap);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
