package com.deu.talha.deuyemek.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by talha on 17.02.2016.
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface DEUYemekPrefs {

    @DefaultString(PrefsStatics.USERNAME_DEF)
    String username();

    @DefaultString(PrefsStatics.PASSWORD_DEF)
    String password();

    @DefaultBoolean(PrefsStatics.REMEMBER_ME_DEF)
    boolean akillikartRememberMe();

}
