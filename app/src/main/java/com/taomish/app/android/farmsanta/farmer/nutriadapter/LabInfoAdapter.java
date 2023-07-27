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
import com.taomish.app.android.farmsanta.farmer.datamodel.Labdata;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.taomish.app.android.farmsanta.farmer.nutrisource.BookSoilTesting;

import java.util.List;

public class LabInfoAdapter extends RecyclerView.Adapter<LabInfoAdapter.ViewHolder> {
    private LabClickListener listener;
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
    private List<Labdata> ProductList;

    public LabInfoAdapter(Context context, List<Labdata> ProductList, LabClickListener listener){
        this.context = context;
        this.ProductList = ProductList;
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
        final Labdata product = ProductList.get(position);

        Picasso.get().load(product.getLabImage()).into(holder.ProductImage);
        holder.ProductName.setText(product.getLabName());
        holder.ProductPrice.setText("Price: "+product.getLabPrice()+" INR");
        holder.ProductExpiryDate.setText(product.getState()+", "+product.getDistrict());
        holder.PrContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, product.getLabName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BookSoilTesting.class);
                intent.putExtra("LabName",product.getLabName());
                intent.putExtra("LabPrice",product.getLabPrice());
                intent.putExtra("LabImage",product.getLabImage());
                intent.putExtra("LabDetails",product.getTestdetails());
                intent.putExtra("LabIsFavorite",String.valueOf(product.getIsFavorite()));
                intent.putExtra("LabId",String.valueOf(product.getLabid()));
                intent.putExtra("IsOffered","no");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                    DatabaseReference x= FirebaseDatabase.getInstance().getReference().child("favourites").child(UserId);
                    x.child(product.getLabid()).removeValue();
                }
                else{
                    holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24);
                    product.setFavorite(true);
                    //here save isFavorite in firebase
                    DatabaseReference x= FirebaseDatabase.getInstance().getReference().child("favourites").child(UserId).child(product.getLabid());
                    x.child("checked").setValue(true);
                    x.child("productimage").setValue(product.getLabImage());
                    x.child("productprice").setValue("INR "+product.getLabPrice());
                    x.child("producttitle").setValue(product.getLabName());
                    x.child("productid").setValue(product.getLabid());
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
        return ProductList.size();
    }


    public interface LabClickListener{
        void onClick(View view, int position);
    }

}
