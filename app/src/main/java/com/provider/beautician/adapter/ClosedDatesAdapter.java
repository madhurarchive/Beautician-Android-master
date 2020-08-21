package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.provider.beautician.R;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.model.ClosedDateModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClosedDatesAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ClosedDateModel> mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public ClosedDatesAdapter(Context mContext, ArrayList<ClosedDateModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_close_date_list, parent, false);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            ClosedDateModel data = mDataModelArrayList.get(position);
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
        private TextView txtDateRange,txtDays,txtLocation,txtDescription;
        public LinearLayout handleView;
        ProductViewHolder(View itemView) {
            super(itemView);
            txtDateRange        =   itemView.findViewById(R.id.item_close_date_txtDateRange);
            txtDays             =   itemView.findViewById(R.id.item_close_date_txtDays);
            txtLocation         =   itemView.findViewById(R.id.item_close_date_txtLocations);
            txtDescription      =   itemView.findViewById(R.id.item_close_date_txtDescription);
            handleView          =   itemView.findViewById(R.id.item_close_date_handleView);

            handleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        private void bind(ClosedDateModel data){
            txtDateRange.setText(getDateRangeString(data.getDatesArray()));
            txtDays.setText(String.valueOf(data.getDayCount()));
            txtLocation.setText(data.getLocationId());
            txtDescription.setText(data.getDescription());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }

    private String getDateRangeString(String jsonStringArray){
        Gson converter = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> list =  converter.fromJson(jsonStringArray, type );
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i==0){
                stringBuilder.append(CommonUtils.getFormattedDate(list.get(i),"yyyy-MM-dd","EEE, dd MMM yyyy"));
            }else if (i==list.size()-1){
                stringBuilder.append(" - ").append(CommonUtils.getFormattedDate(list.get(i),"yyyy-MM-dd","EEE, dd MMM yyyy"));
            }
        }
        return stringBuilder.toString();
    }

}
