package com.queerartfilm.model;

import com.google.common.base.Function;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 * Static utility classes
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class Utils {

    private static final char HYPHEN = '-';
    private static final String[] notKeywords = {"a-", "an-", "the-" };
    private static final Function<String, String> createIdFunction;


    public static String doubleZero(int n) {

        return String.format("%02d", n);
    }
    
    static {
        createIdFunction = new Function<String, String>() {

            @Override
            public String apply(String id) {
                boolean isEmpty = true;
                StringBuilder sb = new StringBuilder();
                // loop through each token
                for (StringTokenizer st = new StringTokenizer(id.trim().toLowerCase()); st.hasMoreTokens();) {
                    char prev = HYPHEN;
                    isEmpty = true;

                    // add each letter and digit in the token, or a HYPHEN if previous was not a HYPHEN
                    for (char c : st.nextToken().toCharArray()) {
                        if (Character.isLetterOrDigit(c) || (c == HYPHEN && prev != HYPHEN)) {
                            sb.append(c);
                            prev = c;
                            isEmpty = false;
                        }
                    }
                    if (isEmpty) {
                        continue;
                    } else {
                        sb.append(HYPHEN);
                    }
                }
                
                String result = sb.toString().trim();
                if (result.endsWith(String.valueOf(HYPHEN))) {
                    result = result.substring(0, result.length() - 1);
                }
                if (result.indexOf(String.valueOf(HYPHEN)) > 0) {
                    for (String s : notKeywords) {
                        if (result.startsWith(s)) {
                            result = result.substring(s.length(), result.length());
                            break;
                        }
                    }
                }
                return result;
            }
        };
    }
    public static String createId(String... ids) {
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append(id + HYPHEN);
        }
        return createId(sb.toString());
    }

    public static String createId(String id) {
        return createIdFunction.apply(id);
    }
    // default format for date strings; 6/30/09 8:00PM
    public static final DateFormat formatter = DateFormat.getDateTimeInstance(
            DateFormat.SHORT, DateFormat.SHORT);

    public static Date parse(String dateString) throws ParseException {
        return formatter.parse(dateString);
    }

    public static String getLastUriToken(HttpServletRequest request) {
        String uriToken = request.getRequestURI();
        int idx = uriToken.lastIndexOf("/");
        if (idx >= 0) {
            uriToken = uriToken.substring(idx + 1);
        }
        return uriToken;
    }
}
