package com.provider.beautician.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.provider.beautician.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragClientTabInvoice extends Fragment {

    public FragClientTabInvoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_client_tab_invoice, container, false);
    }
}
