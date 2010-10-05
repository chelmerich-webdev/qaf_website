package com.queerartfilm.web;

import com.google.appengine.api.memcache.Expiration;
import com.queerartfilm.dao.MemcacheDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Retrieves previously rendered page from App Engine Memcache, 
 * and stores page first if not present. Expiration duration is set as an
 * Initialization Parameter for this <code>Filter</code> and passed in as
 * an argument to the <code>MemcacheDAO</code>.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class CacheFilter implements Filter {

    private static final Logger logger = Logger.getLogger(CacheFilter.class.getName());
    ServletContext sc;
    FilterConfig fc;
    private MemcacheDAO dao;
    private static final String EXPIRATION_PARAM = "secondsToExpire";
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String key = request.getRequestURI();
        String page = (String) dao.get(key);

        try {
            if (page == null) {
                BufferedResponseWrapper wrappedResponse = new BufferedResponseWrapper(response);
                chain.doFilter(req, wrappedResponse);
                // Put rendered page in cache
                String marker = "<!-- Cached: " + new Date() + " -->\n";
                dao.put(key, marker + wrappedResponse.getBufferAsString());
            }
            
            outputPageFromCache(response, key);

        } catch (IOException ioex) {
            logger.log(Level.SEVERE, null, ioex);
        }

    }

    private void outputPageFromCache(HttpServletResponse response, String key) {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);

        String html = (String) dao.get(key);
        response.setContentLength(html.length());

        try {
            PrintWriter out = response.getWriter();
            out.print(html);
            out.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.fc = filterConfig;
        this.sc = filterConfig.getServletContext();
        int secondsToExpire = Integer.parseInt(fc.getInitParameter(EXPIRATION_PARAM));
        this.dao = new MemcacheDAO(Expiration.byDeltaSeconds(secondsToExpire));
    }

    @Override
    public void destroy() {
        this.sc = null;
        this.fc = null;
        this.dao = null;
    }
}
