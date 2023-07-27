package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.content.Intent;
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
import com.taomish.app.android.farmsanta.farmer.datamodel.Soil_Lab_data;
import com.taomish.app.android.farmsanta.farmer.nutrisource.Checkout;
import com.taomish.app.android.farmsanta.farmer.nutrisource.OrderRequestSent;
import com.taomish.app.android.farmsanta.farmer.nutrisource.SoilTestingDetails;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;

public class SoilServicesAdapter extends RecyclerView.Adapter<SoilServicesAdapter.SingleItemRowHolder> {

    private ArrayList<Soil_Lab_data> itemsList;
    private Context mContext;

    public SoilServicesAdapter(Context context, ArrayList<Soil_Lab_data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_soil_services, viewGroup, false);
        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        Soil_Lab_data singleItem = itemsList.get(i);
        holder.productname.setText(singleItem.getProductname());
        holder.productimage.setImageResource(singleItem.getProductimg());
        holder.productprice.setText(singleItem.getPrice());
        holder.testservices.setText("Test Content : "+singleItem.getTestservices());
        holder.reportday.setText("Report  : "+singleItem.getReportday());
        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showBottomSheetDialog();
                Intent i=new Intent(mContext, SoilTestingDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(i);
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


    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView productname;
        protected ImageView productimage;
        LinearLayout PrContainer;
        protected TextView productprice,testservices,reportday;
        Button viewdetails;
        public SingleItemRowHolder(View view) {
            super(view);
            this.productname = (TextView) view.findViewById(R.id.productname);
            this.productimage = (ImageView) view.findViewById(R.id.productimage);
            this.productprice=view.findViewById(R.id.productprice);
            this.PrContainer = view.findViewById(R.id.PrContainer);
            this.testservices = (TextView) view.findViewById(R.id.testservices);
            this.reportday= (TextView) view.findViewById(R.id.reportday);
            this.viewdetails=  view.findViewById(R.id.viewdetails);
           


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
