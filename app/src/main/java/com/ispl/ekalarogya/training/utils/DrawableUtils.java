package com.ispl.ekalarogya.training.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by sonu on 26/12/17.
 */

public class DrawableUtils {

    /**
     * NOTE : For ##RADIO BUTTON## background and text selector use ##android.R.attr.state_checked## instead of ##android.R.attr.state_pressed##
     */

    /**
     * To change background color for the views(Button,ImageView,TextView,RadioButton) during click events, use StateListDrawable.
     *
     * @param context
     * @param normal
     * @param pressed
     * @return
     */
    public static StateListDrawable selectorBackgroundColor(Context context
            , int normal, int pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                new ColorDrawable(pressed));
        states.addState(new int[]{}, new ColorDrawable(normal));
        return states;
    }

    /**
     * To change text color for the views(Button,ImageView,TextView,RadioButton) during click events, use ColorStateList.
     *
     * @param context
     * @param normal
     * @param pressed
     * @return
     */
    public static ColorStateList selectorText(Context context
            , int normal, int pressed) {
        ColorStateList colorStates = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        pressed,
                        normal});
        return colorStates;
    }
}
