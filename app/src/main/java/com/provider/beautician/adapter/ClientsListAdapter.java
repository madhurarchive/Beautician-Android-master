package com.provider.beautician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.model.ClientModel;

import java.util.ArrayList;
import java.util.List;

public class ClientsListAdapter extends RecyclerView.Adapter implements Filterable {
    private Context mContext;
    private ArrayList<ClientModel> mDataModelArrayList;
    private OnRecyclerViewItemClickListener     mListener;
    private OnRecyclerViewFilterListener mDataFilterListener;
    private ItemFilter     mFilter      = new ItemFilter();
    private List<ClientModel> originalData = null;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public ClientsListAdapter(Context mContext, ArrayList<ClientModel> mDataModelArrayList) {
        this.mContext               =   mContext;
        this.mDataModelArrayList    =   mDataModelArrayList;
        this.originalData    =   mDataModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clients_list, parent, false);
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
            ClientModel data = mDataModelArrayList.get(position);
            productViewHolder.bind(data);
        } else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.listBottomProgress);
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName,txtEmail,txtFirstWord;
        private LinearLayout root;
        ProductViewHolder(View itemView) {
            super(itemView);

            txtFirstWord    =   itemView.findViewById(R.id.frag_menu_txtNameFirstWord);
            txtName         =   itemView.findViewById(R.id.item_clients_list_txtName);
            txtEmail        =   itemView.findViewById(R.id.item_clients_list_txtEmail);
            root            =   itemView.findViewById(R.id.item_clients_list_root);

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition()!=-1)
                    mListener.onItemClicked(mDataModelArrayList.get(getAdapterPosition()));
                }
            });
        }

        private void bind(ClientModel data){
            txtFirstWord.setText(data.getName().substring(0,1));
            txtName.setText(String.format("%s %s", data.getName(), data.getLastName()));
            txtEmail.setText(data.getEmail());
        }
    }

    public class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults filterResults  = new FilterResults();
            final List<ClientModel> list = originalData;
            int count = list.size();
            final ArrayList<ClientModel> nlist = new ArrayList<ClientModel>(count);
            if (filterString.length() > 0) {
                for (int i = 0; i < count; i++) {
                    String filterableName = list.get(i).getName()+" "+list.get(i).getLastName();
                    String filterableEmail = list.get(i).getEmail();
                    String filterableMobile = list.get(i).getMobileNo();
                    if (filterableName.toLowerCase().contains(filterString) || filterableEmail.toLowerCase().contains(filterString) || filterableMobile.toLowerCase().contains(filterString)) {
                        nlist.add(list.get(i));
                    }
                }
            }else{
                for (int i = 0; i < count; i++) {
                    nlist.add(list.get(i));
                }
            }

            filterResults.values = nlist;
            filterResults.count = nlist.size();
            return filterResults ;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataModelArrayList = (ArrayList<ClientModel>) results.values;
            notifyDataSetChanged();
            mDataFilterListener.onFilter();
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public void setOnRecyclerViewFilterListener(OnRecyclerViewFilterListener listener) {
        mDataFilterListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(ClientModel model);
    }

    public interface OnRecyclerViewFilterListener {
        void onFilter();
    }

    public int getAdapterListSize(){
        return mDataModelArrayList.size();
    }

}
