package com.deu.talha.deuyemek.listeners;

import android.support.annotation.Nullable;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by talha on 11.02.2016.
 */
public interface MenuLoadListener {

    void onMenuLoadError(@Nullable SweetAlertDialog dialog, @Nullable String errorMessage);
    void onMenuLoadSuccess(@Nullable SweetAlertDialog dialog, @Nullable String successMessage);

}
