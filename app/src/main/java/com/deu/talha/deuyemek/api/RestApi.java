package com.deu.talha.deuyemek.api;

import com.deu.talha.deuyemek.models.Menu;
import com.google.gson.annotations.SerializedName;

import org.androidannotations.annotations.rest.Rest;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by talha on 09.02.2016.
 */
public interface RestApi {

    public class MenusResponse {
        @SerializedName("success")
        public boolean success;
        @SerializedName("menus")
        public List<Menu> menus;
    }

    public class BalanceResponse {
        @SerializedName("success")
        public boolean success;
        @SerializedName("message")
        public String message;
        @SerializedName("html_response")
        public String htmlResponse;
    }

    @GET("/api")
    Observable<MenusResponse> getMenus();

    @FormUrlEncoded
    @POST("/api/balance")
    Observable<BalanceResponse> getBalance(@Field("username") String username, @Field("password") String password);

}
