package com.deu.talha.deuyemek.components;

import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by talha on 13.02.2016.
 */
public interface SBCListener {

    void onLeftButtonClick(ImageButton lButton);
    void onRightButtonClick(ImageButton rButton);
    void onMiddleButtonClick(Button mButton);
}
