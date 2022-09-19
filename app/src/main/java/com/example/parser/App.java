/*
 *      @author
 *      Nicola Cicio, Simon Merkt
 *
 *      @description
 *      This Class is only to initialize the Keys from the Cloud
 *
 */
package com.example.parser;

import android.app.Application;
import com.parse.Parse;


public class App extends Application {

    @Override
    public void onCreate() {

        // set specific Database Information
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}