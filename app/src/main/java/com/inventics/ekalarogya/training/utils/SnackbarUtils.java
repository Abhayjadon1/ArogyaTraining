package com.inventics.ekalarogya.training.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.Snackbar;
import com.inventics.ekalarogya.training.R;

/**
 * Created by sonu on 11:47, 05/03/19
 * Copyright (c) 2019 . All rights reserved.
 */
public class SnackbarUtils {

    public static void showSnackBar(@NonNull View view, String snackbarMessage, int duration) {
        Snackbar snackbar = Snackbar.make(view, snackbarMessage, duration);
        configSnackbar(snackbar);
        snackbar.show();
    }

    public static void showSnackBarWithAction(@NonNull View view, String snackbarMessage, int duration, String actionText, View.OnClickListener actionClickListener) {
        Snackbar snackbar = Snackbar.make(view, snackbarMessage, duration).setAction(actionText, actionClickListener);
        configSnackbar(snackbar);
        snackbar.show();
    }

    private static void configSnackbar(Snackbar snack) {
        addMargins(snack);
        setRoundBordersBg(snack);
        ViewCompat.setElevation(snack.getView(), 6f);
    }

    private static void addMargins(Snackbar snack) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snack.getView().getLayoutParams();
        params.setMargins(12, 12, 12, 12);
        snack.getView().setLayoutParams(params);
    }

    private static void setRoundBordersBg(Snackbar snackbar) {
        snackbar.getView().setBackgroundResource(R.drawable.snackbar_bg);
    }
}
