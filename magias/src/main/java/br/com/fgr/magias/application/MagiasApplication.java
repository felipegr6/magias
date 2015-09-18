package br.com.fgr.magias.application;

import android.app.Application;

import com.firebase.client.Firebase;

public class MagiasApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        Firebase.setAndroidContext(this);

    }

}