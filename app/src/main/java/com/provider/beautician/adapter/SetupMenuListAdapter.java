package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.model.ListDataModel;

import java.util.ArrayList;

public class SetupMenuListAdapter extends RecyclerView.Adapter {
    private Context                             mContext;
    private ArrayList<ListDataModel>            mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public SetupMenuListAdapter(Context mContext, ArrayList<ListDataModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setup_menu_list, parent, false);
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
            progressBar = v.findViewById(R.id.listBottomProgress);
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView        txtTitle, txtDescription;
        private LinearLayout    root;
        ProductViewHolder(View itemView) {
            super(itemView);

            txtTitle        =   itemView.findViewById(R.id.setup_menu_txt_title);
            txtDescription  =   itemView.findViewById(R.id.setup_menu_txt_desc);
            root            =   itemView.findViewById(R.id.root_setup_menu);

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        private void bind(ListDataModel data){
            txtTitle.setText(data.getHeader());
            txtDescription.setText(data.getSubHeader());
            if (getAdapterPosition()==0 || getAdapterPosition()==1){
                root.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorVeryLightGrey));
            }else {
                root.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorTransparent));
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
