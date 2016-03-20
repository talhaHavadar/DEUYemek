package com.deu.talha.deuyemek.ogeb;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by talha on 20.03.2016.
 */
public interface OgebApi {

    public class OgebResponse {

    }

    @FormUrlEncoded
    @POST("/DEUWeb/Icerik/gonder_ogeb.php")
    Call<Void> post(@Field("kategori") String kategori,
                                        @Field("grup") String grup,
                                        @Field("birimler") int birimler,
                                        @Field("ad") String ad,
                                        @Field("soyad") String soyad,
                                        @Field("mail") String mail,
                                        @Field("konu") String konu,
                                        @Field("mesaj") String mesaj);

}
