package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Seller_Product_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.Seller_Product_Data;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;

public class SellerProductAdapter extends RecyclerView.Adapter<SellerProductAdapter.SingleItemRowHolder> {

    private ArrayList<Seller_Product_Data> itemsList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public SellerProductAdapter(Context context, ArrayList<Seller_Product_Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_seller_product, viewGroup, false);
        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        Seller_Product_Data singleItem = itemsList.get(i);
        holder.productname.setText(singleItem.getProductname());
        holder.productimage.setImageResource(singleItem.getProductimg());
        holder.productprice.setText(singleItem.getPrice());
        holder.productrating.setText(singleItem.getRating());
        holder.ratinguser.setText("("+singleItem.getRatingusers()+")");

        holder.quatity.setText(singleItem.getQuantity());
        holder.variant.setText(singleItem.getVariants()+" Variants");
        holder.addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
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
        void onItemClick(View view, int itemPosition, Seller_Product_Data model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView productname;
        protected ImageView productimage;
        LinearLayout addproduct;
        protected TextView productrating,productprice,ratinguser,quatity,variant,viewmore;
        public SingleItemRowHolder(View view) {
            super(view);
            this.productname = (TextView) view.findViewById(R.id.productname);
            this.productimage = (ImageView) view.findViewById(R.id.productimage);
            this.productprice = (TextView) view.findViewById(R.id.productprice);
            this.productrating = (TextView) view.findViewById(R.id.productrating);
            this.ratinguser= (TextView) view.findViewById(R.id.ratinguser);
            this.quatity= (TextView) view.findViewById(R.id.quatity);
            this.variant= (TextView) view.findViewById(R.id.variant);
            this.viewmore=(TextView) view.findViewById(R.id.viewmore);
            this.addproduct=view.findViewById(R.id.addproduct);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));
                }
            });


        }

    }
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.add_to_cart);
        NumberPicker numberPicker = (NumberPicker)bottomSheetDialog.findViewById(R.id.number_picker);
        numberPicker.setMax(15);
        numberPicker.setMin(1);
        numberPicker.setUnit(1);
        numberPicker.setValue(1);
        TextView quatity1 = bottomSheetDialog.findViewById(R.id.quatity1);
        TextView quatity2 = bottomSheetDialog.findViewById(R.id.quatity2);
        TextView quatity3 = bottomSheetDialog.findViewById(R.id.quatity3);
        Button addtocart = bottomSheetDialog.findViewById(R.id.addtocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Cart Added", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        quatity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "1Kg", Toast.LENGTH_LONG).show();
                //bottomSheetDialog.dismiss();
            }
        });

        quatity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "1.5Kg", Toast.LENGTH_LONG).show();
               // bottomSheetDialog.dismiss();
            }
        });

        quatity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "2Kg", Toast.LENGTH_LONG).show();
             //   bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }
}
