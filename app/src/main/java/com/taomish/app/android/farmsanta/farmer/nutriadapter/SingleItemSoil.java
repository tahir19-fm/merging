package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Product_Data;
import com.taomish.app.android.farmsanta.farmer.nutrisource.SoilLabDetails;
import com.taomish.app.android.farmsanta.farmer.nutrisource.SoilSupplierList;

import java.util.ArrayList;

public class SingleItemSoil extends RecyclerView.Adapter<SingleItemSoil.SingleItemRowHolder> {

    private ArrayList<Product_Data> itemsList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;


    public SingleItemSoil(Context context, ArrayList<Product_Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_soil_lab, viewGroup, false);

        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Product_Data singleItem = itemsList.get(i);

        holder.itemCardTxtTitle.setText(singleItem.getTitle());
        holder.itemCardImg.setBackground(mContext.getResources().getDrawable(singleItem.getImage()));
        holder.booksoiltest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext, SoilLabDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
                Animatoo.animateSlideLeft(mContext);
            }
        });
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
        void onItemClick(View view, int itemPosition, Product_Data model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemCardTxtTitle;

        protected LinearLayout itemCardImg;
        Button booksoiltest;

        public SingleItemRowHolder(View view) {
            super(view);

            this.itemCardTxtTitle = (TextView) view.findViewById(R.id.item_card_txt_title);
            this.itemCardImg = view.findViewById(R.id.item_card_img);
            this.booksoiltest=view.findViewById(R.id.booksoiltest);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));
                }
            });


        }

    }

}

