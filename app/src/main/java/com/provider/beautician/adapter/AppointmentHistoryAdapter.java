package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.fragment.CheckoutFragment;
import com.provider.beautician.model.AppointmentDetailsModel;
import com.provider.beautician.model.AppointmentHistoryModel;

import java.util.ArrayList;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryAdapter.AppointmentHistoryViewHolder> {
    Context context;
    ArrayList<AppointmentDetailsModel> list;
    View view;
    public AppointmentHistoryAdapter(Context context, ArrayList<AppointmentDetailsModel> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public AppointmentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view=LayoutInflater.from(context).inflate(R.layout.item_appointment_history,parent,false);
        return new AppointmentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public class AppointmentHistoryViewHolder extends RecyclerView.ViewHolder {
        public AppointmentHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
