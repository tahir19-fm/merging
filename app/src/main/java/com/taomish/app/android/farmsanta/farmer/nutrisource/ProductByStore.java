package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.FilterData;
import com.taomish.app.android.farmsanta.farmer.datamodel.ProductbyShop_Data;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.FilterAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.StoreByProductAdapter;

import java.util.ArrayList;

public class ProductByStore extends AppCompatActivity {
    private FilterAdapter filterAdapter;
    private RecyclerView filters,storeandproduct;
    StoreByProductAdapter storeByProductAdapter;
    private final ArrayList<ProductbyShop_Data> productList = new ArrayList<>();
    ImageView backbtn,cart;
    ImageView searchicon;
    Dialog dialog;
    InputMethodManager imm;
    ListView lvSuggestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_store);
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        filters=findViewById(R.id.filters);
        storeandproduct=findViewById(R.id.storeandproduct);
        backbtn=findViewById(R.id.backbtn);
        searchicon=findViewById(R.id.searchicon);
        cart=findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ProductByStore.this, MyCartActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(ProductByStore.this);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ProductByStore.this, SupplierList.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(ProductByStore.this);
            }
        });
        setFliter();
        setProducts();
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });

    }
    private void showSearchDialog() {
        // TODO Auto-generated method stub
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_search);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        findDialogViews(dialog);
        dialog.show();
    }

    private void findDialogViews(final Dialog dialog) {
        // TODO Auto-generated method stub
        ImageView iv = dialog.findViewById(R.id.calc_clear_txt_Prise);
        lvSuggestions = dialog.findViewById(R.id.listView1);
        lvSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ProductByStore.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        final EditText etSearch = dialog.findViewById(R.id.calc_txt_Prise);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(etSearch.getText().toString().isEmpty()){
                    dialog.dismiss();
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
                else
                    etSearch.setText("");
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(ProductByStore.this, s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }
    private void setProducts(){
for(int i=1;i<5;i++) {
    ArrayList<ProductbyShop_Data> singleItemList = new ArrayList<>();
    //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
    singleItemList.add(new ProductbyShop_Data(i +"1","Farmer Store", "4.5", "12", "123", "store", "", "", "", "", "", "", R.drawable.farmstoreimg, R.drawable.farmstoreimg, true, false));
    singleItemList.add(new ProductbyShop_Data(i +"2","Farmer Store", "4.5", "12", "123", "product", "Calcium Ammonium Nitrate", "50 Kg", "$ 30", "$ 20", "10% Off", "3", R.drawable.productimage, R.drawable.ammonium, true, true));
    singleItemList.add(new ProductbyShop_Data(i +"3","Farmer Store", "4.5", "12", "123", "product", "Calcium Ammonium Nitrate", "50 Kg", "$ 30", "$ 20", "10% Off", "3", R.drawable.productimage, R.drawable.ammonium, false, false));
    singleItemList.add(new ProductbyShop_Data(i +"4","Farmer Store", "4.5", "12", "123", "product", "Calcium Ammonium Nitrate", "50 Kg", "$ 30", "$ 20", "10% Off", "3", R.drawable.productimage, R.drawable.ammonium, false, false));
    singleItemList.add(new ProductbyShop_Data(i +"5","Farmer Store", "4.5", "12", "123", "viewall", "", "", "", "", "", "", R.drawable.ammonium, R.drawable.ammonium, false, false));
   /*
    singleItemList.add(new ProductbyShop_Data("Farmer Store", "4.5", "12", "345", "store", "", "", "", "", "", "", R.drawable.farmstoreimg, R.drawable.farmstoreimg, false, false));
    singleItemList.add(new ProductbyShop_Data("Farmer Store", "4.5", "12", "123", "product", "Calcium Ammonium Nitrate", "50 Kg", "$ 30", "$ 20", "10% Off", "3", R.drawable.productimage, R.drawable.ammonium, true, true));
    singleItemList.add(new ProductbyShop_Data("Farmer Store", "4.5", "12", "123", "product", "Calcium Ammonium Nitrate", "50 Kg", "$ 30", "$ 20", "10% Off", "3", R.drawable.productimage, R.drawable.ammonium, false, false));
    singleItemList.add(new ProductbyShop_Data("Farmer Store", "4.5", "12", "123", "product", "Calcium Ammonium Nitrate", "50 Kg", "$ 30", "$ 20", "10% Off", "3", R.drawable.productimage, R.drawable.ammonium, false, false));
    singleItemList.add(new ProductbyShop_Data("Farmer Store", "4.5", "12", "345", "viewall", "", "", "", "", "", "", R.drawable.ammonium, R.drawable.ammonium, false, false));
    */
    //singleItemList.add(new Service_Data("Agricultural Equipment",R.drawable.gardening));
    //singleItemList.add(new Service_Data("Agro Chemicals", R.drawable.pesticide));

    productList.add(new ProductbyShop_Data("Farmer Store", "4.5", "12", singleItemList));
    storeByProductAdapter = new StoreByProductAdapter(ProductByStore.this, productList);
    storeandproduct.setHasFixedSize(true);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setAutoMeasureEnabled(false);
    storeandproduct.setLayoutManager(layoutManager);
    storeandproduct.setAdapter(storeByProductAdapter);
}
        /**************Onclick On Top Doctor**********************
        storeByProductAdapter.SetOnMoreClickListener(new ProductAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(View view, int position, Product_Data model) {
                Toast.makeText(ProductByStore.this, "See more " + position, Toast.LENGTH_SHORT).show();

            }
        });*/
        storeByProductAdapter.SetOnItemClickListener(new StoreByProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int absolutePosition, int relativePosition, ProductbyShop_Data model) {
             Toast.makeText(ProductByStore.this, model.getProductname(), Toast.LENGTH_SHORT).show();
             if(model.getProductname().equals("")){
                Intent intent= new Intent(ProductByStore.this, StoreDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Animatoo.animateSlideLeft(ProductByStore.this);
             }
             else {
                 Intent intent= new Intent(ProductByStore.this, ProductDetails.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(intent);
                 Animatoo.animateSlideLeft(ProductByStore.this);
             }
            }
        });
       /*
        nearestproduct.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                nearestproduct.smoothScrollToPosition(ProductAdapter.getItemCount() - 1);
            }
        });
*/
    }
    private void setFliter() {
        ArrayList<FilterData> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new FilterData("Sort By", R.drawable.filterimg));
        singleItemList.add(new FilterData("Filters", R.drawable.filterimg));
        singleItemList.add(new FilterData("Category",R.drawable.filterimg));
        singleItemList.add(new FilterData("Rating",R.drawable.filterimg));
        singleItemList.add(new FilterData("Pickup Distance",R.drawable.filterimg));
        filterAdapter = new FilterAdapter(ProductByStore.this, singleItemList);
        filters.setHasFixedSize(true);
        filters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filters.setAdapter(filterAdapter);
        filters.setNestedScrollingEnabled(false);
        filterAdapter.SetOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, FilterData model) {
                Toast.makeText(ProductByStore.this, model.getTitle(), Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
        Intent i= new Intent(ProductByStore.this, SupplierList.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
}