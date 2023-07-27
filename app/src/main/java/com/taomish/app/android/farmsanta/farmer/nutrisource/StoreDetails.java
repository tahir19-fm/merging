package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.FilterData;
import com.taomish.app.android.farmsanta.farmer.datamodel.Seller_Product_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.Shop_Data;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.FilterAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.SellerProductAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.ShopAdapter;

import java.util.ArrayList;

public class StoreDetails extends AppCompatActivity {
    private RecyclerView filters;
    private FilterAdapter filterAdapter;
    RecyclerView storeandproduct;
    SellerProductAdapter sellerProductAdapter;
    ImageView backbtn,cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        filters=findViewById(R.id.filters);
        backbtn=findViewById(R.id.backbtn);
        cart=findViewById(R.id.cart);
        storeandproduct=findViewById(R.id.storeandproduct);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(StoreDetails.this, ProductByStore.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(StoreDetails.this);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(StoreDetails.this, MyCartActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(StoreDetails.this);
            }
        });
        setFliter();
        setProductList();
    }
    private void setProductList(){
        // ShopList.clear();
        ArrayList<Seller_Product_Data> ProductList= new ArrayList<>();
        ProductList.add(new Seller_Product_Data("1",R.drawable.urea,"Nutrisource Calcium Ammonium","5kg","$ 1950","3","4.5","12K"));
        ProductList.add(new Seller_Product_Data("2",R.drawable.urea,"Nutrisource Calcium Ammonium","5kg","$ 1950","3","4.5","12K"));
        ProductList.add(new Seller_Product_Data("3",R.drawable.urea,"Nutrisource Calcium Ammonium","5kg","$ 1950","3","4.5","12K"));
        ProductList.add(new Seller_Product_Data("4",R.drawable.urea,"Nutrisource Calcium Ammonium","5kg","$ 1950","3","4.5","12K"));
        ProductList.add(new Seller_Product_Data("5",R.drawable.urea,"Nutrisource Calcium Ammonium","5kg","$ 1950","3","4.5","12K"));
        ProductList.add(new Seller_Product_Data("6",R.drawable.urea,"Nutrisource Calcium Ammonium","5kg","$ 1950","3","4.5","12K"));
        ProductList.add(new Seller_Product_Data("7",R.drawable.urea,"Nutrisource Calcium Ammonium","5kg","$ 1950","3","4.5","12K"));
        sellerProductAdapter = new SellerProductAdapter(StoreDetails.this, ProductList);
        storeandproduct.setHasFixedSize(true);
        storeandproduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        storeandproduct.setAdapter(sellerProductAdapter);
        sellerProductAdapter.SetOnItemClickListener(new SellerProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, Seller_Product_Data model) {
             //   Toast.makeText(StoreDetails.this, model.getProductname(), Toast.LENGTH_SHORT).show();
                Intent i= new Intent(StoreDetails.this, ProductDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(StoreDetails.this);
            }
        });
    }
    private void setFliter() {
        ArrayList<FilterData> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new FilterData("All Products", R.drawable.filterimg));
        singleItemList.add(new FilterData("Fertilizers", R.drawable.filterimg));
        singleItemList.add(new FilterData("Farm Tools",R.drawable.filterimg));
        singleItemList.add(new FilterData("Filters",R.drawable.filterimg));
        filterAdapter = new FilterAdapter(StoreDetails.this, singleItemList);
        filters.setHasFixedSize(true);
        filters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filters.setAdapter(filterAdapter);
        filters.setNestedScrollingEnabled(false);
        filterAdapter.SetOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, FilterData model) {
                Toast.makeText(StoreDetails.this, model.getTitle(), Toast.LENGTH_SHORT).show();
                showBottomSheetDialog();
            }
        });
        /**************Onclick On Top Doctor************************/
        filters.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                filters.smoothScrollToPosition(filterAdapter.getItemCount() - 1);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(StoreDetails.this, ProductByStore.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

        LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);
        LinearLayout share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
        LinearLayout upload = bottomSheetDialog.findViewById(R.id.uploadLinearLayout);
        LinearLayout download = bottomSheetDialog.findViewById(R.id.download);
        LinearLayout delete = bottomSheetDialog.findViewById(R.id.delete);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Copy is Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Share is Clicked", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Upload is Clicked", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Download is Clicked", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Delete is Clicked", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
}