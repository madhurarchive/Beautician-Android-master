package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.fragment.FragEditStaffWorkingHours;
import com.provider.beautician.model.StaffWorkingMainDataModel;

import java.util.ArrayList;

public class WorkingListAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private ArrayList<StaffWorkingMainDataModel> mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener,mViewDetailListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public WorkingListAdapter(Context mContext, ArrayList<StaffWorkingMainDataModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_working_list, parent, false);
            vh = new ProductViewHolder(v);
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

        if (holder instanceof ProductViewHolder) {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            StaffWorkingMainDataModel data = mDataModelArrayList.get(position);
            productViewHolder.bind(data);
        } else{
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
            progressBar = (ProgressBar) v.findViewById(R.id.listBottomProgress);
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWorkingTime;

        ProductViewHolder(View itemView) {
            super(itemView);
            txtWorkingTime        =   itemView.findViewById(R.id.item_working_list_workingTime);

            txtWorkingTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        private void bind(StaffWorkingMainDataModel data){
            if (data.getData().size()>0) {
                txtWorkingTime.setVisibility(View.VISIBLE);
                txtWorkingTime.setText(String.format("%s - %s", data.getData().get(0).getFirstShiftStart(), data.getData().get(0).getFirstShiftEnd()));
                txtWorkingTime.setBackground(mContext.getResources().getDrawable(R.drawable.ic_rect_working_open_bg,null));
                /*if (data.isOpen()){
                }else {
                    txtWorkingTime.setBackground(mContext.getResources().getDrawable(R.drawable.ic_rect_working_close_bg,null));
                }*/
            }else {
                txtWorkingTime.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }

}
