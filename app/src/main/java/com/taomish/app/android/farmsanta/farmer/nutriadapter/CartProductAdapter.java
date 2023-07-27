package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.CartItemModel;
import com.taomish.app.android.farmsanta.farmer.datamodel.Cart_Data;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.LimitExceededListener;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.SingleItemRowHolder> implements ValueChangedListener, LimitExceededListener {

    private final ArrayList<Cart_Data> itemsList;
    private final Context mContext;
    private OnItemClickListener mItemClickListener;

    public CartProductAdapter(Context context, ArrayList<Cart_Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart_product, viewGroup, false);

        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Cart_Data singleItem = itemsList.get(i);

        holder.PrName.setText(singleItem.getProductname());
        holder.PrImage.setImageResource(singleItem.getProductimg());
        holder.price.setText(singleItem.getPrice());
        holder.numberPicker.setValue(singleItem.getUnit());
        holder.numberPicker.setMax(singleItem.getStock());
        holder.PrDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(mContext, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Remove Item?")
                        .setTitleTextGravity(Gravity.START) //you can specify your own gravity
                        .setContentText("Do you want to remove item?")
                        .setConfirmClickListener("Yes", null)
                        .setCancelClickListener("No",null)
                        .show();
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

    @Override
    public void limitExceeded(int limit, int exceededValue) {

    }

    @Override
    public void valueChanged(int value, ActionEnum action) {

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int itemPosition, Cart_Data model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView PrName;
        NumberPicker numberPicker;
        protected ImageView PrImage;
        protected TextView price;
        ImageView PrDelete;
        public SingleItemRowHolder(View view) {
            super(view);

            this.PrName = view.findViewById(R.id.PrName);
            this.PrImage = view.findViewById(R.id.PrImage);
            this.price = view.findViewById(R.id.price);
            this.PrDelete = view.findViewById(R.id.PrDelete);
            this.numberPicker=view.findViewById(R.id.number_picker);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));
                }
            });


        }

    }

}
