package com.provider.beautician.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.provider.beautician.R;
import com.provider.beautician.adapter.AppointmentDetailsAdapter;
import com.provider.beautician.adapter.AppointmentHistoryAdapter;
import com.provider.beautician.model.AppointmentDetailsModel;

import java.util.ArrayList;

public class CheckoutFragment extends Fragment {
    View v;
    RecyclerView rv, rvAppointmentHistory;
    AppointmentDetailsAdapter appointmentDetailsAdapter;
    AppointmentHistoryAdapter appointmentHistoryAdapter;
    ArrayList<AppointmentDetailsModel> appointmentDetailsModels, appointmentHistoryModels;
    ImageView ivDialogOpen;
    BottomSheetDialog alertDialog;
    Button btNewAppointment;
    View dialogView;
    RelativeLayout rlConfirmed, rlArrived, rlStarted, rlClose, rlClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_checkout, container, false);
        btNewAppointment = v.findViewById(R.id.btNewAppointment);
        ivDialogOpen = v.findViewById(R.id.ivDialogOpen);
        rlClient = v.findViewById(R.id.rlClient);
        setClickListners();
        rv = v.findViewById(R.id.rv);

        rvAppointmentHistory = v.findViewById(R.id.rvAppointmentHistory);
        rvAppointmentHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAppointmentHistory.setAdapter(appointmentHistoryAdapter = new AppointmentHistoryAdapter(getActivity(), appointmentHistoryModels = new ArrayList<>()));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(appointmentDetailsAdapter = new AppointmentDetailsAdapter(getActivity(), appointmentDetailsModels));
        return v;
    }

    public void setClickListners() {
        if (btNewAppointment != null) {
            btNewAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btNewAppointment.setCompoundDrawables(null, null, getActivity().getResources().getDrawable(R.drawable.ic_right_arrow), null);
                    dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_aapointment, null);
                    rlConfirmed = dialogView.findViewById(R.id.rlConfirmed);
                    rlArrived = dialogView.findViewById(R.id.rlArrived);
                    rlStarted = dialogView.findViewById(R.id.rlStarted);
                    rlClose = dialogView.findViewById(R.id.rlClose);

                    rlConfirmed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            appointmentConfirmed();
                        }
                    });
                    rlArrived.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            appointmentArrived();
                        }
                    });

                    rlStarted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            appointmentStarted();
                        }
                    });

                    rlClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            closeAppointmentDialog();
                        }
                    });

                    alertDialog = new BottomSheetDialog(getActivity());
                    alertDialog.setContentView(dialogView);
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                }
            });
        }
    }

    public void appointmentConfirmed() {
    }

    public void appointmentArrived() {
    }

    public void appointmentStarted() {
    }

    public void closeAppointmentDialog() {
        alertDialog.dismiss();
        btNewAppointment.setCompoundDrawables(null, null, getActivity().getResources().getDrawable(R.drawable.down_arrow_small_appointment_button), null);
    }
}
