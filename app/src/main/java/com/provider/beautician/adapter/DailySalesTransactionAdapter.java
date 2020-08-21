package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.model.DailySalesTransactionModel;

import java.util.ArrayList;
import java.util.Locale;

public class DailySalesTransactionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<DailySalesTransactionModel> mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener,mViewDetailListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public DailySalesTransactionAdapter(Context mContext, ArrayList<DailySalesTransactionModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_sales_transation, parent, false);
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
            DailySalesTransactionModel data = mDataModelArrayList.get(position);
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
        private TextView txtItem,txtSales,txtRefund,txtGross;
        ProductViewHolder(View itemView) {
            super(itemView);
            txtItem     =   itemView.findViewById(R.id.item_daily_sales_transaction_txtItem);
            txtSales    =   itemView.findViewById(R.id.item_daily_sales_transaction_txtSales);
            txtRefund   =   itemView.findViewById(R.id.item_daily_sales_transaction_txtRefund);
            txtGross    =   itemView.findViewById(R.id.item_daily_sales_transaction_txtGross);
        }

        private void bind(DailySalesTransactionModel data){
            txtItem.setText(data.getType());
            txtSales.setText(String.format(Locale.ENGLISH,"%d", data.getSaleQty()));
            txtRefund.setText(data.getRefund());
            txtGross.setText(String.format(Locale.ENGLISH,"%s%d", CommonUtils.gettingString(R.string.rupee_symbol, mContext), data.getGross()));
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }

}


