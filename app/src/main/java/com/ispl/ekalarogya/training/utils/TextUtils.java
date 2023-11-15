package com.ispl.ekalarogya.training.utils;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ispl.ekalarogya.training.R;

/**
 * Created by sonu on 08/01/18.
 */

public class TextUtils {

    public final static boolean isValidEmail(CharSequence emailId) {
        return (!android.text.TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches());
    }

    public final static boolean isMobileValid(CharSequence mobileNumber, int maxLength) {
        return (!android.text.TextUtils.isEmpty(mobileNumber) && mobileNumber.length() == maxLength);
    }

    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static void setHtmlString(String htmlString, TextView textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(htmlString));
        }
    }

    public static void setHtmlString(String htmlString, Button button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            button.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
        } else {
            button.setText(Html.fromHtml(htmlString));
        }
    }

    public static Spannable changeColorOfString(Context context,String string, int startingIndex, int endIndex) {
        Spannable span = new SpannableString(string);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), startingIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}
