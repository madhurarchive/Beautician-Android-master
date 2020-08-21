package com.provider.beautician.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.provider.beautician.R;
import com.provider.beautician.databinding.ActivityCheckoutBinding;
import com.provider.beautician.fragment.CheckoutFragment;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.utility.Utility;

public class CheckoutActivity extends AppCompatActivity {
Fragment fragment;
ActivityCheckoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_checkout);
        Utility.setFragment(fragment=new CheckoutFragment(),false,this,R.id.flCheckoutContainer);
    }
}