package com.backendlessstringconverter;

import android.app.Application;

import com.backendless.Backendless;
import com.facebook.FacebookSdk;

/**
 * Created by lfqdt on 10.08.2016.
 */
public class App extends Application {

    public static final String BAKENDLESS_API_KEY = "EC555921-760F-4EA6-FF2F-B3BA317F3500";
    public static final String BAKENDLESS_SECRET_KEY = "605E1C7C-228D-B89E-FF3A-311B252E4E00";
    public static final String BAKENDLESS_API_VERSION = "v1";

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.initApp(getBaseContext(), BAKENDLESS_API_KEY, BAKENDLESS_SECRET_KEY, BAKENDLESS_API_VERSION);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
