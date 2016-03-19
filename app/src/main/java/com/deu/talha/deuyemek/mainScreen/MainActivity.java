package com.deu.talha.deuyemek.mainScreen;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.deu.talha.deuyemek.Constants;
import com.deu.talha.deuyemek.R;
import com.deu.talha.deuyemek.akillikart.AkilliKartFragment;
import com.deu.talha.deuyemek.akillikart.AkilliKartFragment_;
import com.deu.talha.deuyemek.prefs.DEUYemekPrefs_;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements GuillotineListener {

    public static final String TAG = "MainActivity";

    FragmentManager mFragmentManager;

    @ViewById
    Toolbar toolbar;
    @Pref
    DEUYemekPrefs_ prefs;
    @ViewById
    ImageView actHamburger;
    @ViewById
    FrameLayout root;
    @ColorRes(R.color.colorPrimary)
    int colorPrimary;
    @ColorRes(R.color.mainBackground)
    int mainBackground;
    @Bean
    Constants constants;
    GuillotineAnimation gAnimation;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment, new MainActivityFragment_(), MainActivityFragment_.TAG)
//                    .addToBackStack(null)
                    .commit();
        }

        View view = LayoutInflater.from(this).inflate(R.layout.layout_menu, null);
        root.addView(view);

        gAnimation = new GuillotineAnimation.GuillotineBuilder(view, view.findViewById(R.id.menuHamburger), actHamburger)
                .setStartDelay(250)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(this)
                .build();
    }

    @AfterViews
    void init() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

    }

    void refresh(View v) {
        Fragment frag = mFragmentManager.findFragmentById(R.id.fragment);
        if (frag != null && frag.isVisible() && frag instanceof MainActivityFragment) {
            ((MainActivityFragment) frag).setBackPhoto();
            ((MainActivityFragment) frag).colorizeBack();
        }
    }

    @Override
    public void onGuillotineOpened() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(colorPrimary);
        }
    }

    @Override
    public void onGuillotineClosed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(mainBackground);
        }
    }

    public DEUYemekPrefs_ getPrefs() {
        return prefs;
    }

    void openFragment(Class<? extends Fragment> fragmentClass, Object... param) {
        if (fragmentClass.equals(MainActivityFragment.class)) {
            MainActivityFragment fragment = MainActivityFragment_.builder().build();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment, fragment, MainActivityFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        } else if (fragmentClass.equals(AkilliKartFragment.class)) {
            AkilliKartFragment fragment = AkilliKartFragment_.builder().htmlBody(param[0].toString())
                    .build();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment, fragment, AkilliKartFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
            return;
        }
        super.onBackPressed();
    }
}
