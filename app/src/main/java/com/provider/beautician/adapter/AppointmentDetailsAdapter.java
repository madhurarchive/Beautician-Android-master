package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.model.AppointmentDetailsModel;

import java.util.ArrayList;

public class AppointmentDetailsAdapter extends RecyclerView.Adapter<AppointmentDetailsAdapter.AppointmentDetailsViewHolder> {
    ArrayList<AppointmentDetailsModel> appoint_details_list;
    Context context;
    View view;

    public AppointmentDetailsAdapter(Context context, ArrayList<AppointmentDetailsModel> appoint_details_list) {
        this.appoint_details_list = appoint_details_list;
        this.context=context;
    }

    @NonNull
    @Override
    public AppointmentDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.appointment_item,parent,false);
        return new AppointmentDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentDetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class AppointmentDetailsViewHolder extends RecyclerView.ViewHolder {
        public AppointmentDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
