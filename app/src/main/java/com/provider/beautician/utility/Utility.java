package com.provider.beautician.utility;

import android.app.Activity;
import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.provider.beautician.R;
import com.provider.beautician.activity.CheckoutActivity;
import com.provider.beautician.helpers.CommonUtils;

public class Utility {
    public static void setFragment(Fragment fragment, boolean removeStack, CheckoutActivity activity, int mContainer) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer, fragment);
            ftTransaction.addToBackStack(null);
        } else {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(mContainer, fragment, "fragmentTag")
                    .disallowAddToBackStack()
                    .commit();
        }
    }
}
