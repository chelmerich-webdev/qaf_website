/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.film;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class LinkTest {

    @Test
    public void testIsYouTubeId() {
        Link link = new Link();
        String id = "1234567890";
        link.setUrl(id);
        assertFalse(link.isYouTubeId());
        id += "1";
        link.setUrl(id);
        assertTrue(link.isYouTubeId());
        id += "2";
        link.setUrl(id);
        assertFalse(link.isYouTubeId());

        id = "aBcDeFgHiJk";
        link.setUrl(id);
        assertTrue(link.isYouTubeId());

        id = id.replace("k", "&");
        link.setUrl(id);
        assertFalse(id, link.isYouTubeId());

        id = id.replace("&", "$");
        link.setUrl(id);
        assertFalse(link.isYouTubeId());

        id = "_-_-_-_-_-_";
        link.setUrl(id);
        assertTrue(link.isYouTubeId());

    }

}