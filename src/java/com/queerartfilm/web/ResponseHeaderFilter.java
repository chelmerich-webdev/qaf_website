package com.queerartfilm.web;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds specified init parameter headers to the HTTP Response. Code if from
 * an O'Reilly OnJava article:
 * http://onjava.com/pub/a/onjava/2004/03/03/filters.html?page=1
 *
 * @author Jayson Falkner
 */
@SuppressWarnings("unchecked")
public class ResponseHeaderFilter implements Filter {

    private FilterConfig fc;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        // set the provided HTTP response parameters
        for (Enumeration<String> e = fc.getInitParameterNames();
                e.hasMoreElements();) {
            String headerName = e.nextElement();
            response.addHeader(headerName,
                    fc.getInitParameter(headerName));
        }
        // pass the request/response on
        chain.doFilter(req, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.fc = filterConfig;
    }

    @Override
    public void destroy() {
        this.fc = null;
    }
}

