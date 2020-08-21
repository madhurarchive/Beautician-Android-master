package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.provider.beautician.R;
import com.provider.beautician.model.ListDataModel;
import java.util.ArrayList;


public class NotifyLocationAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private ArrayList<ListDataModel> mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener,mViewDetailListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public NotifyLocationAdapter(Context mContext, ArrayList<ListDataModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify_location_list, parent, false);
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
            ListDataModel data = mDataModelArrayList.get(position);
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
        private TextView txtLocationName;
        private LinearLayout root;
        private CheckBox checkBox;
        ProductViewHolder(View itemView) {
            super(itemView);

            txtLocationName       =   itemView.findViewById(R.id.item_notify_location_txtLocationName);
            root                  =   itemView.findViewById(R.id.item_notify_location_layoutRoot);
            checkBox              =   itemView.findViewById(R.id.item_notify_location_checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        private void bind(ListDataModel data){
            txtLocationName.setText(data.getHeader());
            checkBox.setChecked(data.isChecked());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }

}