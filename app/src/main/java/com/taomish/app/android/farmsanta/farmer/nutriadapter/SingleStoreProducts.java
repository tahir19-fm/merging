package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants;
import com.taomish.app.android.farmsanta.farmer.datamodel.ProductbyShop_Data;

import java.util.ArrayList;

public class SingleStoreProducts extends RecyclerView.Adapter<SingleStoreProducts.SingleItemRowHolder> {

    private ArrayList<ProductbyShop_Data> itemsList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public SingleStoreProducts(Context context, ArrayList<ProductbyShop_Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;

    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_productbysupply, viewGroup, false);
        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        ProductbyShop_Data singleItem = itemsList.get(i);
        String type=singleItem.getType();
        if(type.equals("store")){
         holder.sellerid.setVisibility(View.VISIBLE);
         holder.sellerproduct.setVisibility(View.GONE);
         holder.viewallproduct.setVisibility(View.GONE);
         holder.shopimage.setBackground(mContext.getResources().getDrawable(singleItem.getShopimage()));
         if(singleItem.getBookmark()) {
             holder.bookmark.setBackground(mContext.getResources().getDrawable(R.drawable.bookmarkdone));
         }
         else {
             holder.bookmark.setBackground(mContext.getResources().getDrawable(R.drawable.bookmarkwhite));
         }
         if(singleItem.getVeriants().equals("")) {
             holder.variant.setVisibility(View.GONE);
         }
         else {
             holder.variant.setText(singleItem.getVeriants()+" Variants");
         }
        }
        else if(type.equals("product")){
            holder.sellerid.setVisibility(View.GONE);
            holder.sellerproduct.setVisibility(View.VISIBLE);
            holder.viewallproduct.setVisibility(View.GONE);
            holder.productname.setText(singleItem.getProductname());
            holder.productquality.setText(singleItem.getQuatity());
            holder.marketprice.setText(singleItem.getMrp());
            holder.offerprice.setText(singleItem.getPrice());
            holder.discount.setText(singleItem.getDiscount());
            if(singleItem.getBestSeller()) {
                holder.bestseller.setVisibility(View.VISIBLE);
            }
            else {
                holder.bestseller.setVisibility(View.GONE);
            }

        }
        else if(type.equals("viewall")){
            holder.sellerid.setVisibility(View.GONE);
            holder.sellerproduct.setVisibility(View.GONE);
            holder.viewallproduct.setVisibility(View.VISIBLE);
        }

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
        void onItemClick(View view, int itemPosition, ProductbyShop_Data model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        CardView sellerid,sellerproduct,viewallproduct;
        ImageView shopimage,bookmark,bestseller,productimage;

        protected TextView productname,productquality,marketprice,offerprice,discount,variant;

        protected LinearLayout viewstore;


        public SingleItemRowHolder(View view) {
            super(view);

            this.productname = view.findViewById(R.id.productname);
            this.productquality = view.findViewById(R.id.productquality);
            this.marketprice = view.findViewById(R.id.marketprice);
            this.offerprice = view.findViewById(R.id.offerprice);
            this.discount = view.findViewById(R.id.discount);
            this.viewstore = view.findViewById(R.id.viewstore);
            this.sellerid = view.findViewById(R.id.sellerid);
            this.sellerproduct = view.findViewById(R.id.sellerproduct);
            this.viewallproduct = view.findViewById(R.id.viewallproduct);
            this.shopimage = view.findViewById(R.id.shopimage);
            this.bookmark = view.findViewById(R.id.bookmark);
            this.bestseller = view.findViewById(R.id.bestseller);
            this.productimage = view.findViewById(R.id.productimage);
            this.variant=view.findViewById(R.id.variant);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));
                }
            });


        }

    }

}

