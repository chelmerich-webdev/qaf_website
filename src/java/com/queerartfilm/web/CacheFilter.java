package com.queerartfilm.web;

import com.google.appengine.api.memcache.Expiration;
import com.queerartfilm.dao.MemcacheDAO;
import java.io.ByteArrayOutputStream;
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
 *  
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class CacheFilter implements Filter {

    private static final Logger logger = Logger.getLogger(CacheFilter.class.getName());
    ServletContext sc;
    FilterConfig fc;
//    long cacheTimeout = Long.MAX_VALUE;
    private static final MemcacheDAO dao = new MemcacheDAO(Expiration.byDeltaSeconds(60));

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String key = request.getRequestURI();
        String page = (String) dao.get(key);

        try {
            if (page == null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                CacheResponseWrapper wrappedResponse = new CacheResponseWrapper(response, baos);
                chain.doFilter(req, wrappedResponse);
                // Put rendered page in cache
                dao.put(key, "<!-- Cached: " + new Date() + " -->\n" + baos.toString());
            }
            outputPageFromCache(request, response, key);
        } catch (IOException ioex) {
            logger.warning(ioex.getMessage());
            ioex.printStackTrace();
        }

    }

    private void outputPageFromCache(HttpServletRequest request, HttpServletResponse response, String key) {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        String html = (String)dao.get(key);
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
//        String ct = fc.getInitParameter("cacheTimeout");
//        if (ct != null) {
//            cacheTimeout = 60 * 1000 * Long.parseLong(ct);
//        }
    }

    @Override
    public void destroy() {
        this.sc = null;
        this.fc = null;
    }
}
