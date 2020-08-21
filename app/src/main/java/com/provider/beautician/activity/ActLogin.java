package com.provider.beautician.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.provider.beautician.R;
import com.provider.beautician.fragment.FragHomeSlider;
import com.provider.beautician.fragment.FragLogin;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.PreferenceConnector;

import java.util.List;

public class ActLogin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        if (PreferenceConnector.readBoolean(this,PreferenceConnector.KEY_IS_FIRST_TIME,true)) {
            switchContent(new FragHomeSlider(), FragHomeSlider.class.getSimpleName(), false);
        }else {
            switchContent(new FragLogin(), FragLogin.class.getSimpleName(), false);
        }
    }


    public void switchContent(Fragment fragment, String tag, boolean isBackToStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction switch_Ft = fragmentManager.beginTransaction();
        if (isBackToStack) {
            switch_Ft.replace(R.id.container_body_login, fragment).addToBackStack(tag).commit();
        } else {
            switch_Ft.replace(R.id.container_body_login, fragment, tag).commit();
        }
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {

                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

}
