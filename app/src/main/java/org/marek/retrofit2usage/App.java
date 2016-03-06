package org.marek.retrofit2usage;

import android.app.Application;

/**
 * Created by M on 2016-03-06.
 */
public class App extends Application {
    private static App instance;

    public App() {
        super();
        instance = this;
    }

    /**
     * Returns instance of Application used mainly for getting Context for classes without Activity context
     */
    public static App getInstance() {
        return instance;
    }
}
