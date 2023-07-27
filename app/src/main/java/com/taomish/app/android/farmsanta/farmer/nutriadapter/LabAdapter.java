package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Shop_Data;

import java.util.ArrayList;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.SingleItemRowHolder> {

    private final ArrayList<Shop_Data> itemsList;
    private final Context mContext;
    private OnItemClickListener mItemClickListener;

    public LabAdapter(Context context, ArrayList<Shop_Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lab, viewGroup, false);
        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        Shop_Data singleItem = itemsList.get(i);
        holder.StoreName.setText(singleItem.getShoptitle());
        holder.StoreImage.setBackground(mContext.getResources().getDrawable(singleItem.getShopimage()));
        holder.productlist.setText(singleItem.getProductlist());
        holder.rating.setText(singleItem.getShoprating());
        holder.storeaddress.setText(singleItem.getShopaddress());
        if(singleItem.getNearest().equals("1")) {
            holder.nearest.setVisibility(View.VISIBLE);
            holder.nearme.setVisibility(View.VISIBLE);
        }
        else {
            holder.nearest.setVisibility(View.GONE);
            holder.nearme.setVisibility(View.GONE);
        }
        holder.productno.setText(singleItem.getProductsno());
        holder.rating.setText(singleItem.getShoprating());
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int itemPosition, Shop_Data model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView StoreName;
        protected LinearLayout StoreImage;
        protected TextView rating,storeaddress,nearest,productno,productlist,nearme;
        public SingleItemRowHolder(View view) {
            super(view);
            this.StoreName = view.findViewById(R.id.StoreName);
            this.StoreImage = view.findViewById(R.id.StoreImage);
            this.rating = view.findViewById(R.id.rating);
            this.storeaddress = view.findViewById(R.id.storeaddress);
            this.nearest= view.findViewById(R.id.nearest);
            this.productno= view.findViewById(R.id.productno);
            this.productlist= view.findViewById(R.id.productlist);
            this.nearme= view.findViewById(R.id.nearme);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));
                }
            });


        }

    }

}
