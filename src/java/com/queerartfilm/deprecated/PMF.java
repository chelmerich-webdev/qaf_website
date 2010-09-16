package com.queerartfilm.deprecated;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

@Deprecated // using Objectify library
public final class PMF {

    private static final PersistenceManagerFactory pmfInstance =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {
    }

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
