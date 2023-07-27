package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.SupplierDataModel;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.taomish.app.android.farmsanta.farmer.nutrisource.ProductList;

import java.util.List;

public class SuppilerAdapter extends RecyclerView.Adapter<SuppilerAdapter.ViewHolder> {
    private RecyclerViewClickListener listener;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private String UserId;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout PrContainer;
        private ImageView ProductImage;
        private TextView ProductName;
        private TextView ProductPrice;
        private TextView ProductExpiryDate;
        private ImageView PrFavoriteImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            PrContainer = (LinearLayout)itemView.findViewById(R.id.PrContainer);
            ProductImage = (ImageView)itemView.findViewById(R.id.PrImage);
            ProductName = (TextView)itemView.findViewById(R.id.PrName);
            ProductPrice = (TextView)itemView.findViewById(R.id.PrPrice);
            ProductExpiryDate = (TextView)itemView.findViewById(R.id.PrExpiryDate);
            PrFavoriteImage = (ImageView)itemView.findViewById(R.id.PrFavoriteImage);

        }


    }


    private Context context;
    private List<SupplierDataModel> SupplierList;

    public SuppilerAdapter(Context context, List<SupplierDataModel> SupplierList, RecyclerViewClickListener listener){
        this.context = context;
        this.SupplierList = SupplierList;
        this.listener =listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lab_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SupplierDataModel product = SupplierList.get(position);

        Picasso.get().load(product.getSupplierImage()).into(holder.ProductImage);
        holder.ProductName.setText(product.getSupplierName());
        holder.ProductPrice.setText(product.getSupplierLocation());
        holder.ProductExpiryDate.setText(product.getSupplierDetails());
        holder.PrContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductList.class);
                intent.putExtra("product",product.getCategory());
                intent.putExtra("supplierid",product.getSupplierid());
                intent.putExtra("supplierImage",product.getSupplierImage());
                intent.putExtra("supplierName",product.getSupplierName());
                intent.putExtra("supplierLocation",product.getSupplierLocation());
                intent.putExtra("supplierDetails",product.getSupplierDetails());
                intent.putExtra("IsFavorite",product.getIsFavorite());
                context.startActivity(intent);
            }
        });
        if(product.getIsFavorite())holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24);
        else holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_shadow_24);

        //on clicking to favorite icon
        holder.PrFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPrefs appPrefs=new AppPrefs(view.getContext());
                UserId=appPrefs.getPhoneNumber();
                if(product.getIsFavorite()){
                    holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_shadow_24);
                    product.setFavorite(false);
                    //here delete isFavorite from firebase
                    DatabaseReference x= FirebaseDatabase.getInstance().getReference().child("favouritessuppler").child(UserId);
                    x.child(product.getSupplierid()).removeValue();
                }
                else{
                    holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24);
                    product.setFavorite(true);
                    //here save isFavorite in firebase
                    DatabaseReference x= FirebaseDatabase.getInstance().getReference().child("favouritessuppler").child(UserId).child(product.getSupplierid());
                    x.child("checked").setValue(true);
                    x.child("suppliertimage").setValue(product.getSupplierImage());
                    x.child("supplierlocation").setValue(product.getSupplierLocation());
                    x.child("suppliername").setValue(product.getSupplierName());
                    x.child("supplierdetails").setValue(product.getSupplierDetails());
                    x.child("supplierid").setValue(product.getSupplierid());
                }
            }
        });

        //animation
        holder.PrContainer.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.ProductImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.ProductName.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.ProductPrice.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.ProductExpiryDate.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.PrFavoriteImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));


    }

    @Override
    public int getItemCount() {
        return SupplierList.size();
    }


    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }

}
