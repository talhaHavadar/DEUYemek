package com.deu.talha.deuyemek.mainScreen;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deu.talha.deuyemek.Constants;
import com.deu.talha.deuyemek.R;
import com.deu.talha.deuyemek.akillikart.AkilliKartFragment;
import com.deu.talha.deuyemek.api.RestApi;
import com.deu.talha.deuyemek.app.App;
import com.deu.talha.deuyemek.components.SBCListener;
import com.deu.talha.deuyemek.components.SubButtonContainerView;
import com.deu.talha.deuyemek.listeners.MenuLoadListener;
import com.deu.talha.deuyemek.models.Food;
import com.deu.talha.deuyemek.models.Menu;
import com.deu.talha.deuyemek.ogeb.DeuFeedBack;
import com.deu.talha.deuyemek.ogeb.DeuFeedBackData;
import com.deu.talha.deuyemek.ogeb.DeuFeedBackResponse;
import com.deu.talha.deuyemek.prefs.DEUYemekPrefs;
import com.deu.talha.deuyemek.prefs.DEUYemekPrefs_;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import java.util.ArrayList;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
public class MainActivityFragment extends Fragment implements MenuLoadListener, SBCListener {


    private enum Action {
        REFRESH, OGEB
    }

    private static final int DIALOG_DISMISS_DELAY = 1000;
    public static final String TAG = "MainActivityFragment";
    private RestApi service;
    private static final int BACK_PHOTO_COUNT = 6;
    private static Action sbcRightButtonAction = Action.OGEB;

    //private View notificationsView;
    @ViewById
    TextView foods;
    static Menu todayMenu;
    @ViewById
    TextView title;
    @ViewById
    SubButtonContainerView subButtonContainer;
    @ViewById
    ImageView ivBack;
    @ViewById
    RelativeLayout fragmentRoot;
    @ViewById
    RelativeLayout fragmentContentRoot;
    @ViewById
    LinearLayout contentContainer;
    @ColorRes(R.color.mainBackground)
    int mainBackground;
    @Bean
    Constants constants;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @AfterViews
    void init() {
        subButtonContainer.setLeftButtonImage(R.mipmap.ic_card);
        subButtonContainer.setRightButtonImage(R.mipmap.ic_strike);
        subButtonContainer.setListener(this);
        subButtonContainer.setMiddleButtonText(getResources().getString(R.string.notifications_button_text));
        if (todayMenu == null)
            getMenus();
        else
            onMenuLoadSuccess(null, null);

        setBackPhoto();
        colorizeBack();

        App.getEventBus().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof DeuFeedBackResponse) {
                            if (((DeuFeedBackResponse) o).success) {
                                Toast.makeText(getContext(), "Mesajınız gönderildi!!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Bir şeyler ters gitti lutfen tekrar deneyin!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


    void getMenus() {
        if (service == null)
            service = App.getRetrofit().create(RestApi.class);
        final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Yükleniyor..");
        dialog.setCancelable(false);
        dialog.show();
        service.getMenus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RestApi.MenusResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onMenuLoadError(dialog, "Beklenmedik bir sorun oluştu. " + e.getMessage());
                    }

                    @Override
                    public void onNext(RestApi.MenusResponse menusResponse) {
                        if (menusResponse.success) {
                            constants.loadMenus(menusResponse.menus);
                            onMenuLoadSuccess(dialog, "Yüklendi");
                        } else {
                            onMenuLoadError(dialog, "Beklenmedik bir sorun oluştu.");
                        }

                    }
                });
    }

    @Override
    public void onMenuLoadError(@Nullable SweetAlertDialog dialog, @Nullable String errorMessage) {
        if (dialog != null) {
            dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText("Hata");
            dialog.setContentText(errorMessage);
            dialog.setConfirmText("Tamam");
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
        title.setText("Ooops!!");
        foods.setText("Bir şeyler ters gitti..");
        subButtonContainer.setRightButtonImage(R.mipmap.ic_refresh);
        sbcRightButtonAction = Action.REFRESH;
    }

    @Override
    public void onMenuLoadSuccess(@Nullable SweetAlertDialog dialog, @Nullable String successMessage) {
        if (dialog != null) {
            dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            dialog.setTitleText(successMessage);
            dialog.setConfirmText("Tamam");
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
        if (constants.MENUS != null && constants.MENUS.size() > 0) {
            todayMenu = new Menu();
            todayMenu.dateString = constants.MENUS.get(0).dateString;
            todayMenu.foods = new ArrayList<>();
            for (int i = 0; i < constants.MENUS.get(0).foods.size(); i++) {

                if (!constants.MENUS.get(0).foods.get(i).error) {
                    Food temp = new Food();
                    temp.name = constants.MENUS.get(0).foods.get(i).name;
                    temp.cal = constants.MENUS.get(0).foods.get(i).cal;
                    temp.error = true;
                    todayMenu.foods.add(temp);
                }
            }
        }
        if (todayMenu != null) {
            title.setText(todayMenu.dateString);
            foods.setText(todayMenu.getFoodsString());
        }
        subButtonContainer.setRightButtonImage(R.mipmap.ic_strike);
        sbcRightButtonAction = Action.OGEB;
    }


    // For show balance
    @Override
    public void onLeftButtonClick(ImageButton lButton) {
        View loginLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_akillikart_login, null);
        final TextView errorMessage = (TextView) loginLayout.findViewById(R.id.errorMessage);
        final EditText etNumber = (EditText) loginLayout.findViewById(R.id.etNumber);
        final EditText etPassword = (EditText) loginLayout.findViewById(R.id.etPassword);
        final Button btLogin = (Button) loginLayout.findViewById(R.id.btLogin);
        final CheckBox cbRememberMe = (CheckBox) loginLayout.findViewById(R.id.cbRememberMe);
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(loginLayout))
                .setGravity(Gravity.CENTER)
                .setContentHeight(RelativeLayout.LayoutParams.WRAP_CONTENT)
                .setContentWidth(RelativeLayout.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(android.R.color.transparent)
                .create();

        DEUYemekPrefs_ prefs = getMainActivity().getPrefs();

        etNumber.setText(prefs.username().getOr(""));
        etPassword.setText(prefs.password().getOr(""));
        cbRememberMe.setChecked(prefs.akillikartRememberMe().getOr(false));

        dialog.show();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNumber.getText().toString())) {
                    etNumber.setError("Lütfen bu alanı doldurunuz.");
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    etPassword.setError("Lütfen bu alanı doldurunuz.");
                    return;
                }
                btLogin.setEnabled(false);
                final SweetAlertDialog loadingDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                loadingDialog.setCancelable(false);
                loadingDialog.setTitleText("Giriş yapılıyor..");
                loadingDialog.show();
                service.getBalance(etNumber.getText().toString(), etPassword.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<RestApi.BalanceResponse>() {
                            @Override
                            public void onCompleted() {
                                btLogin.setEnabled(true);
                                if (loadingDialog.isShowing())
                                    loadingDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setText("Beklenmedik bir hata oluştu!");
                                btLogin.setEnabled(true);
                                if (loadingDialog.isShowing())
                                    loadingDialog.dismiss();
                            }

                            @Override
                            public void onNext(RestApi.BalanceResponse balanceResponse) {
                                if (loadingDialog.isShowing())
                                    loadingDialog.dismiss();
                                if (balanceResponse.success) {
                                    if (cbRememberMe.isChecked()) {
                                        DEUYemekPrefs_ prefs = getMainActivity().getPrefs();
                                        prefs.edit()
                                                .username()
                                                .put(etNumber.getText().toString())
                                                .password()
                                                .put(etPassword.getText().toString())
                                                .akillikartRememberMe()
                                                .put(true)
                                                .apply();

                                    } else {
                                        DEUYemekPrefs_ prefs = getMainActivity().getPrefs();
                                        prefs.edit()
                                                .username()
                                                .put("")
                                                .password()
                                                .put("")
                                                .akillikartRememberMe()
                                                .put(false)
                                                .apply();
                                    }
                                    dialog.dismiss();
                                    getMainActivity().openFragment(AkilliKartFragment.class, balanceResponse.htmlResponse);
                                } else {
                                    errorMessage.setText(balanceResponse.message);
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onRightButtonClick(ImageButton rButton) {
        switch (sbcRightButtonAction) {
            case REFRESH:
                getMenus();
                break;
            case OGEB:
                // Do ogeb stuff
                View ogebLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_ogeb, null);
                final EditText etName = (EditText) ogebLayout.findViewById(R.id.etName);
                final EditText etSurname = (EditText) ogebLayout.findViewById(R.id.etSurname);
                final EditText etEmail = (EditText) ogebLayout.findViewById(R.id.etEmail);
                final EditText etSubject = (EditText) ogebLayout.findViewById(R.id.etSubject);
                final EditText etBody = (EditText) ogebLayout.findViewById(R.id.etBody);
                final Spinner spnStatus = (Spinner) ogebLayout.findViewById(R.id.spnStatus);
                final Spinner spnNotification = (Spinner) ogebLayout.findViewById(R.id.spnNotificationType);
                Button btSend = (Button) ogebLayout.findViewById(R.id.btSend);
                final DialogPlus dialog = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(ogebLayout))
                        .setGravity(Gravity.CENTER)
                        .setContentHeight(RelativeLayout.LayoutParams.WRAP_CONTENT)
                        .setContentWidth(RelativeLayout.LayoutParams.WRAP_CONTENT)
                        .setContentBackgroundResource(android.R.color.transparent)
                        .create();
                dialog.show();

                btSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeuFeedBackData data = Constants.checkOgebParameters(getContext(),spnNotification.getSelectedItem().toString()
                                , spnStatus.getSelectedItem().toString(), etName.getText().toString(), etSurname.getText().toString(),
                                etEmail.getText().toString(),etSubject.getText().toString(), etBody.getText().toString());
                        if (data != null) {
                            DeuFeedBack.sendPost(data);
                            dialog.dismiss();
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void onMiddleButtonClick(Button mButton) {
        Constants.notImplementedYet(getContext());
        /*
        if (notificationsView == null) {
            LinearLayout .LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_notifications, null);
            notificationsView = view;
            notificationsView.setLayoutParams(lp);
            fragmentContentRoot.addView(notificationsView);
            notificationsView.setVisibility(View.GONE);
        }
        if (notificationsView.getVisibility() != View.VISIBLE) {
            YoYo.with(Techniques.BounceInDown)
                    .duration(300)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            notificationsView.setVisibility(View.VISIBLE);
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
                    .playOn(notificationsView);
        } else {
            YoYo.with(Techniques.TakingOff)
                    .duration(300)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            notificationsView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .playOn(notificationsView);
        }
        */
    }

    void setBackPhoto() {
        Random rand = new Random();
        int photoSuffix = rand.nextInt(BACK_PHOTO_COUNT) + 1;
        int photoId = getResources().getIdentifier("img" + photoSuffix, "drawable", getContext().getPackageName());
        ivBack.setImageResource(photoId);
    }

    void colorizeBack() {
        Bitmap bitmap = ((BitmapDrawable)ivBack.getDrawable()).getBitmap();
        Palette palette = Palette.from(bitmap).generate();
        fragmentRoot.setBackgroundColor(palette.getLightMutedColor(mainBackground));
        int backColor = palette.getDarkMutedColor(0x77333333);
        contentContainer.setBackgroundColor(Color.argb(180, Color.red(backColor), Color.green(backColor), Color.blue(backColor)));
    }



    MainActivity_ getMainActivity() {
        return (MainActivity_) getActivity();
    }

}
