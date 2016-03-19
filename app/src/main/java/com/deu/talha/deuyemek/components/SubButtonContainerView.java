package com.deu.talha.deuyemek.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


/**
 * Created by talha on 13.02.2016.
 */
public class SubButtonContainerView extends RelativeLayout {

    private static final String TAG = "SubButtonContainerView";
    private String middleButtonBGColor = "#fafafa";
    private String middleButtonTextColor = "#1565c0";
    private String sbcBGColor = "#2196f3";
    SBCListener mListener;
    Button sbcMiddleButton;
    ImageButton sbcRightButton;
    ImageButton sbcLeftButton;

    public SubButtonContainerView(Context context) {
        this(context, null);
    }

    public SubButtonContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public SubButtonContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void setup() {
        super.setBackgroundColor(Color.parseColor(sbcBGColor));
        // MiddleButton Setup
        setupMiddleButton();
        super.addView(sbcMiddleButton);
        // MiddleButton end
        // LeftButton Setup
        setupLeftButton();
        super.addView(sbcLeftButton);
        // LeftButton end
        // RightButton Setup
        setupRightButton();
        super.addView(sbcRightButton);
        // RightButton end
    }

    public void setListener(SBCListener listener) {
        this.mListener = listener;
    }

    private void setupRightButton() {
        sbcRightButton = new ImageButton(getContext());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(ALIGN_PARENT_RIGHT, TRUE);
        param.addRule(ALIGN_PARENT_TOP, TRUE);
        int margin = (int) dpToPx(1f);
        param.setMargins(margin, 0, margin, 0);
        sbcRightButton.setLayoutParams(param);
        sbcRightButton.setPadding(0,0,0,0);
        sbcRightButton.setBackgroundColor(Color.TRANSPARENT);
        sbcRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightButtonClick((ImageButton) v);
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void setupMiddleButton() {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        sbcMiddleButton = new Button(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int)dpToPx(30f));
        lp.addRule(CENTER_IN_PARENT, TRUE);
        int margin = (int) dpToPx(5f);
        lp.setMargins(margin, margin, margin, margin);
        sbcMiddleButton.setLayoutParams(lp);
        sbcMiddleButton.setPadding((int)dpToPx(5f), 0, (int)dpToPx(5f), 0);
        sbcMiddleButton.setText("Middle Button");
        sbcMiddleButton.setTextColor(Color.parseColor(middleButtonTextColor));
        sbcMiddleButton.setBackgroundColor(Color.parseColor(middleButtonBGColor));
        sbcMiddleButton.setMinHeight((int) dpToPx(30f));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sbcMiddleButton.setBackground(generateGradientDrawable(Color.parseColor(middleButtonBGColor), 5f));
        } else {
            sbcMiddleButton.setBackgroundDrawable(generateGradientDrawable(Color.parseColor(middleButtonBGColor), 5f));
        }
        sbcMiddleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMiddleButtonClick((Button) v);
                }
            }
        });
    }

    private void setupLeftButton() {
        sbcLeftButton = new ImageButton(getContext());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(ALIGN_PARENT_LEFT, TRUE);
        param.addRule(ALIGN_PARENT_TOP, TRUE);
        int margin = (int) dpToPx(1f);
        param.setMargins(margin, 0, margin, 0);
        sbcLeftButton.setLayoutParams(param);
        sbcLeftButton.setPadding(0,0,0,0);
        sbcLeftButton.setBackgroundColor(Color.TRANSPARENT);
        sbcLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLeftButtonClick((ImageButton) v);
                }
            }
        });
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void setLeftButtonImage(int resId) {
        sbcLeftButton.setImageResource(resId);
    }

    public void setRightButtonImage(int resId) {
        sbcRightButton.setImageResource(resId);
    }

    public void setMiddleButtonText(String text) {
        sbcMiddleButton.setText(text);
    }

    public ImageButton getLeftImageButton() {
        return sbcLeftButton;
    }

    public ImageButton getRightImageButton() {
        return sbcRightButton;
    }

    public Button getMiddleButton() {
        return sbcMiddleButton;
    }

    private GradientDrawable generateGradientDrawable(int color, float radius) {
        GradientDrawable gDrawable = new GradientDrawable();
        gDrawable.setCornerRadius(radius);
        gDrawable.setColor(color);
        return gDrawable;
    }
}
