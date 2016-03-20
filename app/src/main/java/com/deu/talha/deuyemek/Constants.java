package com.deu.talha.deuyemek;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.deu.talha.deuyemek.models.Food;
import com.deu.talha.deuyemek.models.Menu;
import com.deu.talha.deuyemek.ogeb.DeuFeedBack;
import com.deu.talha.deuyemek.ogeb.DeuFeedBackData;
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

    public static DeuFeedBackData checkOgebParameters(Context context, String notificationType, String status, String name, String surname,
                                              String email, String subject, String body) {
        if (TextUtils.isEmpty(notificationType) || notificationType.equals("Seçiniz..")) {
            Toast.makeText(context, "Lütfen bildirim türünü seçiniz..", Toast.LENGTH_SHORT).show();
            return null;
        } else if (TextUtils.isEmpty(status) || status.equals("Seçiniz..")) {
            Toast.makeText(context, "Lütfen durumunuzu seçiniz..", Toast.LENGTH_SHORT).show();
            return null;
        } else if (TextUtils.isEmpty(name.trim())) {
            Toast.makeText(context, "Lütfen adınızı yazınız..", Toast.LENGTH_SHORT).show();
            return null;
        } else if (TextUtils.isEmpty(surname.trim())) {
            Toast.makeText(context, "Lütfen soyadınızı yazınız..", Toast.LENGTH_SHORT).show();
            return null;
        } else if (TextUtils.isEmpty(email.trim())) {
            Toast.makeText(context, "Lütfen emailinizi yazınız..", Toast.LENGTH_SHORT).show();
            return null;
        } else if (TextUtils.isEmpty(subject.trim())) {
            Toast.makeText(context, "Lütfen konuyu yazınız..", Toast.LENGTH_SHORT).show();
            return null;
        } else if (TextUtils.isEmpty(body.trim())) {
            Toast.makeText(context, "Lütfen mesajınızı yazınız..", Toast.LENGTH_SHORT).show();
            return null;
        }

        DeuFeedBackData data = new DeuFeedBackData();
        if (notificationType.equalsIgnoreCase("Memnuniyet")) {
            data.category = DeuFeedBack.kategoriler.Memnuniyet;
        } else if (notificationType.equalsIgnoreCase("Eleştiri")) {
            data.category = DeuFeedBack.kategoriler.Elestiri;
        } else if (notificationType.equalsIgnoreCase("Öneri")) {
            data.category = DeuFeedBack.kategoriler.Oneri;
        }
        if (status.equalsIgnoreCase("Akademisyen")) {
            data.status = DeuFeedBack.durumlar.Akademisyen;
        } else if (status.equalsIgnoreCase("İdari personel")) {
            data.status = DeuFeedBack.durumlar.IdariPersonel;
        } else if (status.equalsIgnoreCase("Öğrenci")) {
            data.status = DeuFeedBack.durumlar.Ogrenci;
        } else if (status.equalsIgnoreCase("Diğer")) {
            data.status = DeuFeedBack.durumlar.Diger;
        }
        data.name = name;
        data.surname = surname;
        data.email = email;
        data.subject = subject;
        data.message = body;
        return data;
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
