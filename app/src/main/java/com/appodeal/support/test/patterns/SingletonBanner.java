package com.appodeal.support.test.patterns;

import android.app.Activity;

import com.appodeal.ads.Appodeal;

public final class SingletonBanner {
    private static SingletonBanner instance = null;

    private SingletonBanner(Activity activity) {
        Appodeal.show(activity, Appodeal.BANNER_TOP);
    }

    public static synchronized SingletonBanner getInstance(Activity activity) {
        if (instance == null)
            instance = new SingletonBanner(activity);
        return instance;
    }
}
