package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.CategoryProductInfo;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.taomish.app.android.farmsanta.farmer.nutrisource.ProductInfoActivity;

import java.util.List;

public class OtherProductInfoAdapter extends RecyclerView.Adapter<OtherProductInfoAdapter.ViewHolder> {
    private OtherProductClickListener listener;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private String UserId;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout PrContainer;
        private ImageView ProductImage;
        private TextView ProductName;
        private TextView ProductPrice;
        private TextView ProductExpiryDate;
        private ImageView PrFavoriteImage;
        public Button AddToCart,DeleteCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            PrContainer = (LinearLayout)itemView.findViewById(R.id.PrContainer);
            ProductImage = (ImageView)itemView.findViewById(R.id.PrImage);
            ProductName = (TextView)itemView.findViewById(R.id.PrName);
            ProductPrice = (TextView)itemView.findViewById(R.id.PrPrice);
            ProductExpiryDate = (TextView)itemView.findViewById(R.id.PrExpiryDate);
            PrFavoriteImage = (ImageView)itemView.findViewById(R.id.PrFavoriteImage);
            AddToCart = (Button) itemView.findViewById(R.id.AddToCart);
            DeleteCart= (Button) itemView.findViewById(R.id.DeleteCart);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
       //     listener.onClick(view, getAdapterPosition());
        }
    }


    private Context context;
    private List<CategoryProductInfo> ProductList;

    public OtherProductInfoAdapter(Context context, List<CategoryProductInfo> ProductList, OtherProductClickListener listener){
        this.context = context;
        this.ProductList = ProductList;
        this.listener =listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_products_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final CategoryProductInfo product = ProductList.get(position);

        Picasso.get().load(product.getProductImage()).into(holder.ProductImage);
        holder.ProductName.setText(product.getProductName());
        holder.ProductPrice.setText("Price: "+product.getProductPrice()+" INR");
        holder.ProductExpiryDate.setText("Expiry Date: "+product.getProductExpiryDate());
        holder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshContainers(product.getProductName(),holder.AddToCart,holder.DeleteCart);
            }
        });
        holder.DeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshContainers(product.getProductName(),holder.AddToCart,holder.DeleteCart);
            }
        });
        if(product.getProductExpiryDate().equalsIgnoreCase("null")) holder.ProductExpiryDate.setVisibility(View.INVISIBLE);
        else holder.ProductExpiryDate.setVisibility(View.VISIBLE);

        if(product.getIsFavorite())holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24);
        else holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_shadow_24);

        holder.PrContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductInfoActivity.class);
                intent.putExtra("Product Name",product.getProductName());
                intent.putExtra("Product Details",product.getProductDetails());
                intent.putExtra("Product Price",product.getProductPrice());
                intent.putExtra("Product Image",product.getProductImage());
                intent.putExtra("Product ExpiryDate",product.getProductExpiryDate());
                intent.putExtra("supplierid",product.getSellerid());
                intent.putExtra("supplierImage",product.getSupplerimage());
                intent.putExtra("supplierName",product.getsupplierName());
                intent.putExtra("supplierLocation",product.getsupplierLocation());
                intent.putExtra("supplierDetails",product.getsupplierDetails());
                intent.putExtra("Product IsFavorite",String.valueOf(product.getIsFavorite()));
                intent.putExtra("Is Offered","no");
                context.startActivity(intent);
            }
        });
        //on clicking to favorite icon
        holder.PrFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                CurrentUser = mAuth.getCurrentUser();
                UserId = CurrentUser.getUid();

                if(product.getIsFavorite()){
                    holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_shadow_24);
                    product.setFavorite(false);
                    //here delete isFavorite from firebase
                    DatabaseReference x= FirebaseDatabase.getInstance().getReference().child("favourites").child(UserId);
                    x.child(product.getProductName()).removeValue();
                }
                else{
                    holder.PrFavoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24);
                    product.setFavorite(true);
                    //here save isFavorite in firebase
                    DatabaseReference x= FirebaseDatabase.getInstance().getReference().child("favourites").child(UserId).child(product.getProductName());
                    x.child("checked").setValue(true);
                    x.child("productimage").setValue(product.getProductImage());
                    x.child("productprice").setValue("INR "+product.getProductPrice());
                    x.child("producttitle").setValue(product.getProductName());

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
    private void RefreshContainers(String ProductName, Button add, Button remove){
        AppPrefs appPrefs = new AppPrefs(context);
        UserId=appPrefs.getPhoneNumber();
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference x = root.child("cart").child(UserId).child(ProductName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    add.setVisibility(View.GONE);
                    remove.setVisibility(View.VISIBLE);
                }
                else{
                    add.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        x.addListenerForSingleValueEvent(valueEventListener);

    }
    @Override
    public int getItemCount() {
        return ProductList.size();
    }


    public interface OtherProductClickListener{
        void onClick(View view, int position);
    }

}
