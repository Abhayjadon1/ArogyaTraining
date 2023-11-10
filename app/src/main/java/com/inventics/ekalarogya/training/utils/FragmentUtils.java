package com.inventics.ekalarogya.training.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Sonu on 14/08/17.
 */

public class FragmentUtils {

    public static final String PROGRESS_DIALOG_FRAGMENT = "progress_dialog_fragment";
    public static final String HOME_FRAGMENT = "home_fragment";
    public static final String ADDRESS_FRAGMENT = "address_fragment";
    public static final String WALLET_FRAGMENT = "wallet_fragment";
    public static final String BOOKING_HISTORY_FRAGMENT = "booking_history_fragment";
    public static final String ADD_ADDRESS_FRAGMENT = "add_address_fragment";
    public static final String OFFERS_FRAGMENT = "offers_fragment";
    public static final String PROFILE_FRAGMENT = "profile_fragment";
    public static final String REFERRAL_FRAGMENT = "referral_fragment";
    public static final String REFERRAL_EARNINGS_FRAGMENT = "referral_earnings_fragment";
    public static final String REFERRAL_LIST_FRAGMENT = "referral_list_fragment";
    public static final String MEMBERSHIP_PLANS = "membership_plan";
    public static final String CURRENT_MEMBERSHIP_PLANS = "current_membership_plan";
    public static final String SUBSCRIPTION_PLANS = "subscription_plans";
    public static final String LOYALITY_POINTS ="loyalty_points" ;
    public static final String TXN_DETAILS = "txn_detail";

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment, String tag, boolean isAddToBackStack) {
        if (isFragmentReplaced(fragmentManager, tag) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(containerId, fragment, tag);

            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        }
    }

    public static Fragment isFragmentReplaced(FragmentManager fragmentManager, String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }
}
