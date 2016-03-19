package com.deu.talha.deuyemek;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.deu.talha.deuyemek.models.Food;
import com.deu.talha.deuyemek.models.Menu;
import com.nineoldandroids.animation.Animator;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by talha on 12.07.2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Constants {


    @RootContext
    Context context;

    public List<Menu> MENUS;

    public void loadMenus(List<Menu> menus) {
        if (MENUS == null)
            MENUS = new ArrayList<>();
        MENUS.clear();
        for (int i = 0; i < menus.size(); i++) {
            Menu menu = new Menu();
            menu.dateString = menus.get(i).dateString;
            menu.foods = new ArrayList<>();
            for (int j = 0; j < menus.get(i).foods.size(); j++) {
                if (!menus.get(i).foods.get(j).error) {
                    Food food = new Food();
                    food.error = false;
                    food.cal = menus.get(i).foods.get(j).cal;
                    food.name = menus.get(i).foods.get(j).name;
                    menu.foods.add(food);
                }
            }
            MENUS.add(menu);
        }
    }

    public Menu findMenuByDateString(String dateString) {
        for (int i = 0; i < MENUS.size(); i++) {
            if (MENUS.get(i) != null && MENUS.get(i).dateString.equals(dateString)) {
                return MENUS.get(i);
            }
        }
        return null;
    }

    public Menu findMenuByDateString(List<Menu> menus, String dateString) {
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i) != null && menus.get(i).dateString.equals(dateString)) {
                return menus.get(i);
            }
        }
        return null;
    }

    public static void notImplementedYet(Context context) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("Error");
        dialog.setContentText("Not implemented yet!!");
        dialog.setCancelable(true);
        dialog.show();
    }

    public static void showPopupView(View rootView, View popupView, @Nullable Techniques tech) {
        Techniques tt = tech;
        if (tt == null) {
            tt = Techniques.Pulse;
        }


        YoYo.with(tt)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(500)
                .playOn(popupView);

    }

}
