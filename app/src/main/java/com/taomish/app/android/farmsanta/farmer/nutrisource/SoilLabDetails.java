package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.FilterData;
import com.taomish.app.android.farmsanta.farmer.datamodel.Seller_Product_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.Soil_Lab_data;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.FilterAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.SellerProductAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.SoilServicesAdapter;

import java.util.ArrayList;

public class SoilLabDetails extends AppCompatActivity {
    private RecyclerView filters;
    private FilterAdapter filterAdapter;
    RecyclerView storeandproduct;
    SoilServicesAdapter soilServicesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_lab_details);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.soilcolor));
        filters=findViewById(R.id.filters);
        storeandproduct=findViewById(R.id.storeandproduct);
        setFliter();
        setLabList();
    }
    private void setLabList(){
        // ShopList.clear();
        ArrayList<Soil_Lab_data> ProductList= new ArrayList<>();
        ProductList.add(new Soil_Lab_data("1",R.drawable.soilservicesimg,"Soil Testing Service","$ 50/Sample","Ph Level, Minerals ...","2 Days"));
        ProductList.add(new Soil_Lab_data("2",R.drawable.soilservicesimg,"Soil Testing Service","$ 50/Sample","Ph Level, Minerals ...","2 Days"));
        ProductList.add(new Soil_Lab_data("3",R.drawable.soilservicesimg,"Soil Testing Service","$ 50/Sample","Ph Level, Minerals ...","2 Days"));
        ProductList.add(new Soil_Lab_data("4",R.drawable.soilservicesimg,"Soil Testing Service","$ 50/Sample","Ph Level, Minerals ...","2 Days"));
        ProductList.add(new Soil_Lab_data("5",R.drawable.soilservicesimg,"Soil Testing Service","$ 50/Sample","Ph Level, Minerals ...","2 Days"));
        ProductList.add(new Soil_Lab_data("6",R.drawable.soilservicesimg,"Soil Testing Service","$ 50/Sample","Ph Level, Minerals ...","2 Days"));
        ProductList.add(new Soil_Lab_data("7",R.drawable.soilservicesimg,"Soil Testing Service","$ 50/Sample","Ph Level, Minerals ...","2 Days"));
        soilServicesAdapter = new SoilServicesAdapter(SoilLabDetails.this, ProductList);
        storeandproduct.setHasFixedSize(true);
        storeandproduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        storeandproduct.setAdapter(soilServicesAdapter);

    }
    private void setFliter() {
        ArrayList<FilterData> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new FilterData("All Products", R.drawable.filterimg));
        singleItemList.add(new FilterData("Fertilizers", R.drawable.filterimg));
        singleItemList.add(new FilterData("Farm Tools",R.drawable.filterimg));
        singleItemList.add(new FilterData("Filters",R.drawable.filterimg));
        filterAdapter = new FilterAdapter(SoilLabDetails.this, singleItemList);
        filters.setHasFixedSize(true);
        filters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filters.setAdapter(filterAdapter);
        filters.setNestedScrollingEnabled(false);
        filterAdapter.SetOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, FilterData model) {
                Toast.makeText(SoilLabDetails.this, model.getTitle(), Toast.LENGTH_SHORT).show();
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
        Intent i= new Intent(SoilLabDetails.this, SoilSupplierList.class);
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