package com.deu.talha.deuyemek.akillikart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.deu.talha.deuyemek.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_akilli_kart)
public class AkilliKartFragment extends Fragment {

    public static final String TAG = "AkilliKartFragment";

    @ViewById
    WebView webView;

    @FragmentArg
    String htmlBody;

    @AfterViews
    void init() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("http://akillikart.deu.edu.tr", htmlBody, "text/html", "utf-8", null);
    }

}
