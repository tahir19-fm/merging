package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants;
import com.taomish.app.android.farmsanta.farmer.datamodel.ProductbyShop_Data;

import java.util.ArrayList;

public class StoreByProductAdapter extends RecyclerView.Adapter<StoreByProductAdapter.ViewHolder> {

    private final Context mContext;
    private ArrayList<ProductbyShop_Data> modelList;

    private OnItemClickListener mItemClickListener;
    private OnMoreClickListener mMoreClickListener;

    public StoreByProductAdapter(Context context, ArrayList<ProductbyShop_Data> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<ProductbyShop_Data> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_multiple_storeandproducts, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        //Here you can fill your row view

        final ProductbyShop_Data model = getItem(position);
        Log.d(AppConstants.TAG, "Amit " + "Function Call");

        ArrayList<ProductbyShop_Data> singleSectionItems = model.getSingleItemModelArrayList();
        holder.storename.setText(model.getShopname());
        holder.ratingno.setText(model.getRating());
        holder.ratinguser.setText("("+model.getNoofuser()+")");
        SingleStoreProducts itemListDataAdapter = new SingleStoreProducts(mContext, singleSectionItems);
        holder.recyclerViewHorizontalList.setHasFixedSize(true);
        holder.recyclerViewHorizontalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewHorizontalList.setAdapter(itemListDataAdapter);
        holder.recyclerViewHorizontalList.setNestedScrollingEnabled(false);
        itemListDataAdapter.SetOnItemClickListener(new SingleStoreProducts.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, ProductbyShop_Data model) {

                mItemClickListener.onItemClick(view, position, itemPosition, model);

            }
        });
/*
        holder.itemTxtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMoreClickListener.onMoreClick(view, position, model);


            }
        });
*/


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private ProductbyShop_Data getItem(int position) {
        return modelList.get(position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnMoreClickListener(final OnMoreClickListener onMoreClickListener) {
        this.mMoreClickListener = onMoreClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int absolutePosition, int relativePosition, ProductbyShop_Data model);
    }


    public interface OnMoreClickListener {
        void onMoreClick(View view, int position, ProductbyShop_Data model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected RecyclerView recyclerViewHorizontalList;
        protected TextView ratingno,ratinguser;
        private final TextView storename;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.ratingno = itemView.findViewById(R.id.ratingno);
            this.ratinguser = itemView.findViewById(R.id.ratinguser);
            this.storename = itemView.findViewById(R.id.storename);
            this.recyclerViewHorizontalList = itemView.findViewById(R.id.services_horizontal_list);
        }
    }


}



