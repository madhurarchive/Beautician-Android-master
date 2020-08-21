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

import com.google.android.gms.common.internal.service.Common;
import com.provider.beautician.R;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.model.ListDataModel;
import java.util.ArrayList;

public class HomeMenuAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private ArrayList<ListDataModel> mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener,mViewDetailListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public HomeMenuAdapter(Context mContext, ArrayList<ListDataModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_menu, parent, false);
            vh = new ProductViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_home_menu, parent, false);
            vh = new ProductViewHolder(v);
        }
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        //return mDataModelArrayList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        if (position ==6 || position == 11){
            return VIEW_PROG;
        }else {
            return VIEW_ITEM;
        }
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
        private TextView txtName;
        private ImageView   imgIcon;
        private LinearLayout root;
        ProductViewHolder(View itemView) {
            super(itemView);

            txtName         =   itemView.findViewById(R.id.item_home_menu_txtName);
            imgIcon         =   itemView.findViewById(R.id.item_home_menu_imgIcon);
            root            =   itemView.findViewById(R.id.item_home_menu_root);

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition()!=6 || getAdapterPosition()!=11) {
                        mListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }

        private void bind(ListDataModel data){
            txtName.setText(data.getHeader());
            if (data.getIcon()!=0) {
                imgIcon.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(data.getIcon());
            }else if (data.getHeader().equals(CommonUtils.gettingString(R.string.app_name,mContext))){
                imgIcon.setVisibility(View.INVISIBLE);
            }else {
                imgIcon.setVisibility(View.GONE);
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
