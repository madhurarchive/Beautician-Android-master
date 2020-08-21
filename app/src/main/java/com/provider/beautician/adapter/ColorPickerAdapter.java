package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.model.ColorPickerModel;

import java.util.ArrayList;

/**
 * Created by archive_infotech on 10/3/18.
 */

public class ColorPickerAdapter extends RecyclerView.Adapter{
    private Context                             mContext;
    private ArrayList<ColorPickerModel>         mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private String TAG = ColorPickerAdapter.class.getSimpleName();

    public ColorPickerAdapter(Context mContext, ArrayList<ColorPickerModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_color, parent, false);
            vh = new ColorPickerViewHolder(v);
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

        if (holder instanceof ColorPickerViewHolder) {
            ColorPickerViewHolder productViewHolder = (ColorPickerViewHolder) holder;
            ColorPickerModel data = mDataModelArrayList.get(position);
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

    private class ColorPickerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgColor,imgCheck;

        ColorPickerViewHolder(View itemView) {
            super(itemView);

            imgColor    =   itemView.findViewById(R.id.appointment_img_color);
            imgCheck    =   itemView.findViewById(R.id.appointment_img_check);
            imgColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(getAdapterPosition());
                    for (ColorPickerModel pickerModel:mDataModelArrayList) {
                        pickerModel.setChecked(false);
                    }
                    mDataModelArrayList.get(getAdapterPosition()).setChecked(true);
                    notifyDataSetChanged();
                }
            });
        }

        private void bind(ColorPickerModel data){
            if (data.isChecked()){
                imgColor.setBackground(ContextCompat.getDrawable(mContext,R.drawable.appointment_color_sel));
                imgCheck.setVisibility(View.VISIBLE);
            }else {
                imgColor.setBackground(null);
                imgCheck.setVisibility(View.GONE);
            }
            imgColor.setImageDrawable(data.getView());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }
}
