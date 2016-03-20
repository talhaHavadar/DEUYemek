package com.deu.talha.deuyemek.ogeb;

import android.util.Log;

import com.deu.talha.deuyemek.app.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by talha on 3.3.2015.
 */
public class DeuFeedBack {

    private static final String TAG = "DeuFeedBack";
    private static Retrofit retrofit;
    private static final String POST_URL = "http://www.deu.edu.tr/DEUWeb/Icerik/gonder_ogeb.php";
    ///////////////////Form Elements/////////////////////////////////////////////////////////////
    private static final String KATEGORI = "kategori";
    private static final String GRUP = "grup";
    private static final String BIRIMLER = "birimler";
    private static final String AD = "ad";
    private static final String SOYAD = "soyad";
    private static final String MAIL = "mail";
    private static final String KONU = "konu";
    private static final String MESAJ = "mesaj";
    ////////////////////////////////////////////////////////////////////////////////////////////
    //######Enums######

    public enum durumlar{
        Akademisyen,IdariPersonel,
        Ogrenci,Diger
    }

    public enum kategoriler{
        Memnuniyet,Elestiri,Oneri
    }
    //#################


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.deu.edu.tr")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void sendPost(DeuFeedBackData data) {
        kategoriler kategoriEnum = data.category;
        durumlar durumEnum = data.status;
        String ad = data.name.trim();
        String soyad = data.surname.trim();
        String mail = data.email.trim();
        String konu = data.subject.trim();
        String mesaj = data.message.trim();

        String kategori = "";
        if (kategoriEnum == kategoriler.Elestiri) {
            kategori = "Elestiri";
        } else if(kategoriler.Memnuniyet == kategoriEnum) {
            kategori = "Memnuniyet";
        } else if (kategoriEnum == kategoriler.Oneri) {
            kategori = "Öneri";
        }
        String grup = "";
        if (durumlar.Akademisyen == durumEnum) {
            grup = "Akademisyen";
        } else if (durumlar.IdariPersonel == durumEnum) {
            grup = "İdari personel";
        } else if (durumlar.Ogrenci == durumEnum) {
            grup = "Öğrenci";
        } else if (durumlar.Diger == durumEnum) {
            grup = "Diğer";
        }

        OgebApi api = getRetrofit().create(OgebApi.class);
        api.post(kategori, grup, 0,
                ad, soyad, mail, konu, mesaj)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Response<Void> response) {

                        DeuFeedBackResponse res = new DeuFeedBackResponse();
                        res.success = response.isSuccess();
                        App.getEventBus().send(res);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        DeuFeedBackResponse res = new DeuFeedBackResponse();
                        res.success = false;
                        App.getEventBus().send(res);
                    }
                });
    }


}
