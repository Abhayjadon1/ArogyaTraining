package com.ispl.ekalarogya.training.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

import com.ispl.ekalarogya.training.R;


public class DialogHelper {
    public static void showMessageOKCancel(Context context, String message, String positiveButton, String negativeButton, OnClickListener okListener, OnClickListener cancelListener) {
        new AlertDialog.Builder(context, R.style.MyAlertDialogStyle).setMessage(message)
                .setPositiveButton(positiveButton, okListener)
                .setNegativeButton(negativeButton, cancelListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showMessageOKCancel(Context context, String title, String message, String positiveButton, String negativeButton, OnClickListener okListener, OnClickListener cancelListener) {
        new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, okListener)
                .setNegativeButton(negativeButton, cancelListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showListDialog(Context context, String title, String[] itemList, OnClickListener okListener) {
        new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setTitle(title)
                .setItems(itemList, okListener)
                .setCancelable(true)
                .create()
                .show();
    }

    public static void showAlertDialogWithNeutralButton(Context context, String message, String positiveButton) {
        new AlertDialog.Builder(context, R.style.MyAlertDialogStyle).setMessage(message)
                .setPositiveButton(positiveButton, null)
                .setCancelable(true)
                .create()
                .show();
    }
}
