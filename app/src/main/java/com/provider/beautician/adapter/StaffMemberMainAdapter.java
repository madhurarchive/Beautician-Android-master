package com.provider.beautician.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.provider.beautician.R;
import com.provider.beautician.listners.ItemTouchHelperAdapter;
import com.provider.beautician.listners.ItemTouchHelperViewHolder;
import com.provider.beautician.listners.OnStartDragListener;
import com.provider.beautician.model.StaffMemberDetailModel;
import java.util.ArrayList;
import java.util.Collections;

public class StaffMemberMainAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {
    private Context                                 mContext;
    private ArrayList<StaffMemberDetailModel>       mDataModelArrayList;
    private OnRecyclerViewItemClickListener         mListener;
    private final OnStartDragListener               mDragStartListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public StaffMemberMainAdapter(Context mContext, ArrayList<StaffMemberDetailModel> mDataModelArrayList,OnStartDragListener dragStartListener) {
        this.mContext               =   mContext;
        mDragStartListener          = dragStartListener;
        this.mDataModelArrayList    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_member_main, parent, false);
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
            StaffMemberDetailModel data = mDataModelArrayList.get(position);
            productViewHolder.bind(data);
        } else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

        ((ProductViewHolder)holder).handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataModelArrayList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDataModelArrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDataModelArrayList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        //mDataModelArrayList.remove(position);
        //notifyItemRemoved(position);
    }

    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.listBottomProgress);
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private TextView        txtName,txtMobile,txtEmail;
        private ImageView       imgAppointmentColor;
        public LinearLayout     handleView,root;

        ProductViewHolder(View itemView) {
            super(itemView);
            txtName             =   itemView.findViewById(R.id.item_staff_member_main_txtName);
            txtMobile           =   itemView.findViewById(R.id.item_staff_member_main_txtMobile);
            txtEmail            =   itemView.findViewById(R.id.item_staff_member_main_txtEmail);
            imgAppointmentColor =   itemView.findViewById(R.id.appointment_img_color);
            handleView          =   itemView.findViewById(R.id.item_staff_member_main_handleView);
            root                =   itemView.findViewById(R.id.root_staff_member_list);

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        private void bind(StaffMemberDetailModel data){
            txtName.setText(data.getStaffName());
            txtMobile.setText(data.getMobile());
            txtEmail.setText(data.getEmail());
            Log.e("StaffMember","color : " + data.getStaffAppointmentColor());
            setAppointmentColor(data.getStaffAppointmentColor(),imgAppointmentColor);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(int position);
    }

    private void setAppointmentColor(String appointmentColor,ImageView imgAppointmentColor){
        if (appointmentColor!=null && !appointmentColor.trim().isEmpty()&& !appointmentColor.startsWith("-")){
            imgAppointmentColor.setImageDrawable(customView(Color.parseColor(appointmentColor)));
        }else {
            imgAppointmentColor.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.color_picker_view_1));
        }
    }

    private GradientDrawable customView(int backgroundColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        shape.setColor(backgroundColor);
        return shape;
    }

}
