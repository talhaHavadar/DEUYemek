package com.deu.talha.deuyemek.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by talha on 08.02.2016.
 */
public class App extends Application {

    private static RxBus eventBus;
    private static Retrofit retrofit;
    public static Typeface CANARO_FONT;
    @Override
    public void onCreate() {
        super.onCreate();
        CANARO_FONT = Typeface.createFromAsset(getAssets(),"fonts/canaro_extra_bold.otf");
    }

    public static RxBus getEventBus() {
        if (eventBus == null) {
            eventBus = new RxBus();
        }
        return eventBus;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://deuyemek.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
