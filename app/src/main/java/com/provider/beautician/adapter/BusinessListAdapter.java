package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.model.StaffLocationModel;

import java.util.ArrayList;

public class BusinessListAdapter extends RecyclerView.Adapter {
    private Context                             mContext;
    private ArrayList<StaffLocationModel>            mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener,mMenuOptionListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public BusinessListAdapter(Context mContext, ArrayList<StaffLocationModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locations_list, parent, false);
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
            StaffLocationModel data = mDataModelArrayList.get(position);
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
            progressBar = v.findViewById(R.id.listBottomProgress);
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView        txtTitle, txtDescription;
        private ImageView       imgOptions;
        private LinearLayout    root;

        ProductViewHolder(View itemView) {
            super(itemView);

            txtTitle        =   itemView.findViewById(R.id.location_txt_title);
            txtDescription  =   itemView.findViewById(R.id.location_txt_desc);
            imgOptions      =   itemView.findViewById(R.id.location_img_options);
            root            =   itemView.findViewById(R.id.root_location);

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });

            imgOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMenuOptionListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        private void bind(StaffLocationModel data){
            txtTitle.setText(data.getBusineessName());
            txtDescription.setText(data.getInstagram());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public void setOnRecyclerViewMenuOptionClickListener(OnRecyclerViewItemClickListener listener) {
        mMenuOptionListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }

}
