package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.MyorderModel;

import java.util.List;

public class SoilTestAdapter extends RecyclerView.Adapter<SoilTestAdapter.ViewHolder> {
    private List<MyorderModel> orderItemList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView orderDate, orderNums, orderPrice, orderProducts, OrderCheck,Name,Contact,Address;
        private Button ScanQrCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderNums = itemView.findViewById(R.id.orderNums);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderProducts = itemView.findViewById(R.id.orderProducts);
            OrderCheck = itemView.findViewById(R.id.OrderCheck);
            ScanQrCode = itemView.findViewById(R.id.ScanQRCode);
            Name= itemView.findViewById(R.id.Name);
            Contact= itemView.findViewById(R.id.Contact);
            Address= itemView.findViewById(R.id.Address);
        }
    }


    public SoilTestAdapter(Context context, List<MyorderModel> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderItemview = LayoutInflater.from(context).inflate(R.layout.soiltest_adapter_layout, parent, false);
        return new ViewHolder(orderItemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyorderModel model = orderItemList.get(position);

        holder.orderDate.setText(model.getDate());
        holder.orderNums.setText(model.getOrderNums());
        holder.orderPrice.setText(model.getOrderPrice());
        holder.orderProducts.setText(model.getOrderProducts());
        holder.Name.setText("Lab Name: "+model.getName());
        holder.Contact.setText("Contact No.: "+model.getContact());
        holder.Address.setText("Your Address: "+model.getAddress());
        holder.OrderCheck.setText("Booking Status: Pending");
        holder.ScanQrCode.setVisibility(View.VISIBLE);
       /* if(model.getOrderCheck().equalsIgnoreCase("false")){
            holder.OrderCheck.setText("Order: Pending");
            holder.ScanQrCode.setVisibility(View.VISIBLE);
        }
        else{
            holder.OrderCheck.setText("Order: Received");
            holder.ScanQrCode.setVisibility(View.VISIBLE);
        }*/

        holder.ScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

}
