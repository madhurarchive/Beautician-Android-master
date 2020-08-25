package com.provider.beautician.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.provider.beautician.R;
import com.provider.beautician.databinding.ActivityNewAppointmentBinding;
import com.provider.beautician.fragment.CheckoutFragment;
import com.provider.beautician.fragment.NewApppointmentFragment;
import com.provider.beautician.utility.Utility;

public class NewAppointmentActivity extends AppCompatActivity {
    ActivityNewAppointmentBinding binding;
    NewApppointmentFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_appointment);
        Utility.setFragmentNewAppointmentActivity(fragment=new NewApppointmentFragment(),false,this,R.id.flCheckoutContainer);

    }
}