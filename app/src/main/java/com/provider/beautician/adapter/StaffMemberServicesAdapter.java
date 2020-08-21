package com.provider.beautician.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.model.ServicesListModel;

import java.util.ArrayList;

/**
 * Created by archive_infotech on 10/3/18.
 */

public class StaffMemberServicesAdapter extends RecyclerView.Adapter{
    private Context                             mContext;
    private ArrayList<ServicesListModel>        mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private String TAG = StaffMemberServicesAdapter.class.getSimpleName();

    public StaffMemberServicesAdapter(Context mContext, ArrayList<ServicesListModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_member_services, parent, false);
            vh = new ServicesViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_progress_bar, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataModelArrayList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ServicesViewHolder) {
            ServicesViewHolder productViewHolder = (ServicesViewHolder) holder;
            ServicesListModel data = mDataModelArrayList.get(position);
            productViewHolder.bind(data);
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataModelArrayList.size();
    }

    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.listBottomProgress);
        }
    }

    private class ServicesViewHolder extends RecyclerView.ViewHolder {
        private CheckBox        chkService;

        ServicesViewHolder(View itemView) {
            super(itemView);

            chkService =   itemView.findViewById(R.id.smc_chk_service);
            chkService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performCheckBoxOperation(getAdapterPosition(),!mDataModelArrayList.get(getAdapterPosition()).isChecked());
                }
            });
        }

        private void bind(ServicesListModel data){
            chkService.setText(data.getName());
            chkService.setChecked(mDataModelArrayList.get(getAdapterPosition()).isChecked());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }

    private void performCheckBoxOperation(int position,boolean state){
        Log.e(TAG,"position : " + position + " state : " + state + " id : " + mDataModelArrayList.get(position).getId());
        if (mDataModelArrayList.get(position).getId().equals("all")){
            for (ServicesListModel model : mDataModelArrayList) {
                model.setChecked(state);
            }
            notifyDataSetChanged();
        }else {
            mDataModelArrayList.get(position).setChecked(state);
            int checkedCount = 0;
            int unCheckedCount = 0;
            for (int i = 1; i < mDataModelArrayList.size(); i++) {
                if (mDataModelArrayList.get(i).isChecked()){
                    checkedCount++;
                }else {
                    unCheckedCount++;
                }
            }
            Log.e("StaffMember","checkedCount   === " + checkedCount);
            Log.e("StaffMember","unCheckedCount === " + unCheckedCount);
            if (checkedCount==mDataModelArrayList.size()-1){
                mDataModelArrayList.get(0).setChecked(true);
            }else if (unCheckedCount>0){
                mDataModelArrayList.get(0).setChecked(false);
            }
            notifyDataSetChanged();
        }
    }

}
