package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.FilterData;
import com.taomish.app.android.farmsanta.farmer.datamodel.Product_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.Service_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.Shop_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.Store_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.SupplierDataModel;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.FarmerStoreAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.FilterAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.LabAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.OfferItemServices;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.ProductAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.ProductTypeAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.ShopAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.SoilLabAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.SuppilerAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SoilSupplierList extends AppCompatActivity implements Spinner.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, SuppilerAdapter.RecyclerViewClickListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;
    private TextView mPerson_name;
    private CircleImageView mPerson_image;
    //------------------------------------
    private String CategoryName;
    private RecyclerView recyclerView,producttype,offers,starseller,neareststore,shoplist,fliters;
    private ArrayList<SupplierDataModel> CategoryProducts;

    private SuppilerAdapter adapter;
    private LabAdapter labAdapter;
    private FarmerStoreAdapter farmerStoreAdapter;
    //------------------------------------
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private String UserId;
    private SuppilerAdapter.RecyclerViewClickListener listener;
    //Custom Xml Views (cart Icon)
    private RelativeLayout CustomCartContainer;
    private TextView PageTitle, CustomCartNumber;
    private final ArrayList<Service_Data> producttypeList = new ArrayList<>();
    private final ArrayList<Service_Data> offerlistdata = new ArrayList<>();
    private final ArrayList<Product_Data> productList = new ArrayList<>();
    private final ArrayList<Product_Data> rentproductList = new ArrayList<>();
    private SoilLabAdapter soilLabAdapter;
    private ProductTypeAdapter productTypeAdapter;
    private FilterAdapter filterAdapter;
    private OfferItemServices offerItemServices;
    AppPrefs appPrefs;
    private final String[] pickupdistance = {"1-5Km","1-10Km","1-15km","1-20Km","1-30Km","1-40Km","1-50Km","1-100Km","Any Location"};
    ArrayAdapter distanceadapter;
    Spinner distancespinner;
    BottomNavigationView bottomNavigationView;
    RadioGroup OrderTypeGroup;
    RadioButton self,labexpert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_supplier_list);
        Window window = SoilSupplierList.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(SoilSupplierList.this,R.color.soilcolor));
        appPrefs=new AppPrefs(this);
        OrderTypeGroup = findViewById(R.id.OrderTypeGroup);
        self = findViewById(R.id.self);
        labexpert = findViewById(R.id.labexpert);
        bottomNavigationView=findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        producttype=findViewById(R.id.producttype);
        offers=findViewById(R.id.offers);
        starseller=findViewById(R.id.starseller);
        fliters=findViewById(R.id.fliters);
        neareststore=findViewById(R.id.neareststore);
        shoplist=findViewById(R.id.shoplist);
        UserId=appPrefs.getPhoneNumber();
        CategoryName = getIntent().getStringExtra("product");
        distancespinner=findViewById(R.id.distancespinner);
        distanceadapter=new ArrayAdapter(this,R.layout.simple_spinner_items,pickupdistance);
        distancespinner.setAdapter(distanceadapter);
        // Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        distancespinner.setOnItemSelectedListener(this);
        //on clicking any product (go to ProductInfo Activity to show it's info)
        addListenerOnButton();
        setProductType();
        setOffers();
        setSeller();
        setNeareststore();
        setFliter();
        setShopList();
    }
    public void addListenerOnButton() {
        OrderTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.pickup) {
                    self.setTextColor(getResources().getColor(R.color.soiltextcolor));
                    self.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.soiltextcolor)));
                    labexpert.setTextColor(getResources().getColor(R.color.white));
                    labexpert.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
                else{
                    self.setTextColor(getResources().getColor(R.color.white));
                    self.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    labexpert.setTextColor(getResources().getColor(R.color.soiltextcolor));
                    labexpert.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.soiltextcolor)));
                }
            }
        });


    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        int itemId = item.getItemId();
        if (itemId == R.id.nmarketplace_order) {
            Intent i= new Intent(SoilSupplierList.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        } else if (itemId == R.id.nmarketplace_wishlist) {
            Intent i= new Intent(SoilSupplierList.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        } else if (itemId == R.id.nmarketplace_offer) {
            Intent i= new Intent(SoilSupplierList.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        }
        else if (itemId == R.id.nmarketplace_cart) {
            Intent i= new Intent(SoilSupplierList.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        }
        // It will help to replace the
        // one fragment to other.

        return true;
    };

    private void setShopList(){
        // ShopList.clear();
        ArrayList<Shop_Data> ShopList= new ArrayList<>();
        ShopList.add(new Shop_Data("Soil Testing Lab 1",R.drawable.soillabimg,"4.5","Village Market Road","1","45","Carbon| Nitrogen | Phosphorus ..."));
        ShopList.add(new Shop_Data("Soil Testing Lab 2",R.drawable.soillabimg,"4.5","Village Market Road","1","35","Carbon| Nitrogen | Phosphorus ..."));
        ShopList.add(new Shop_Data("Soil Testing Lab",R.drawable.soillabimg,"4.5","Village Market Road","1","41","Carbon| Nitrogen | Phosphorus ..."));
        ShopList.add(new Shop_Data("Soil Testing Lab",R.drawable.soillabimg,"4.5","Village Market Road","0","55","Carbon| Nitrogen | Phosphorus ..."));
        ShopList.add(new Shop_Data("Soil Testing Lab",R.drawable.soillabimg,"4.5","Village Market Road","0","48","Carbon| Nitrogen | Phosphorus ..."));
        ShopList.add(new Shop_Data("Soil Testing Lab",R.drawable.soillabimg,"4.5","Village Market Road","0","33","Carbon| Nitrogen | Phosphorus ..."));
        ShopList.add(new Shop_Data("Soil Testing Lab",R.drawable.soillabimg,"4.5","Village Market Road","0","44","Carbon| Nitrogen | Phosphorus ..."));
        labAdapter = new LabAdapter(SoilSupplierList.this, ShopList);
        shoplist.setHasFixedSize(true);
        shoplist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        shoplist.setAdapter(labAdapter);
    }
    private void setSeller(){
        ArrayList<Product_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.soillabimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.soillabimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.soillabimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.soillabimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        //singleItemList.add(new Service_Data("Agricultural Equipment",R.drawable.gardening));
        //singleItemList.add(new Service_Data("Agro Chemicals", R.drawable.pesticide));
        productList.add(new Product_Data("Services", singleItemList));
        soilLabAdapter = new SoilLabAdapter(SoilSupplierList.this, productList);
        starseller.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        starseller.setLayoutManager(layoutManager);
        starseller.setAdapter(soilLabAdapter);
        /**************Onclick On Top Doctor************************/
        soilLabAdapter.SetOnMoreClickListener(new SoilLabAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(View view, int position, Product_Data model) {
                Toast.makeText(SoilSupplierList.this, "See more " + position, Toast.LENGTH_SHORT).show();

            }
        });
        soilLabAdapter.SetOnItemClickListener(new SoilLabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int absolutePosition, int relativePosition, Product_Data model) {
                if(relativePosition==0){
                    Intent intent= new Intent(SoilSupplierList.this, SoilLabDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(SoilSupplierList.this);
                }
                else if(relativePosition==1) {
                    Toast.makeText(SoilSupplierList.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
/*
        starseller.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                starseller.smoothScrollToPosition(ProductAdapter.getItemCount() - 1);
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
        filterAdapter = new FilterAdapter(SoilSupplierList.this, singleItemList);
        fliters.setHasFixedSize(true);
        fliters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fliters.setAdapter(filterAdapter);
        fliters.setNestedScrollingEnabled(false);
        filterAdapter.SetOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, FilterData model) {
                Toast.makeText(SoilSupplierList.this, model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        /**************Onclick On Top Doctor************************/
        fliters.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                fliters.smoothScrollToPosition(filterAdapter.getItemCount() - 1);
            }
        });
    }
    private void setNeareststore() {
        ArrayList<Store_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Store_Data("Soil Testing Lab", R.drawable.testlab,"4.5","12K"));
        singleItemList.add(new Store_Data("Soil Testing Lab", R.drawable.testlab,"4.5","12K"));
        singleItemList.add(new Store_Data("Soil Testing Lab",R.drawable.testlab,"4.5","12K"));
        singleItemList.add(new Store_Data("Soil Testing Lab",R.drawable.testlab,"4.5","12K"));
        singleItemList.add(new Store_Data("Soil Testing Lab",R.drawable.testlab,"4.5","12K"));
        farmerStoreAdapter = new FarmerStoreAdapter(SoilSupplierList.this, singleItemList);
        neareststore.setHasFixedSize(true);


        neareststore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        neareststore.setAdapter(farmerStoreAdapter);
        neareststore.setNestedScrollingEnabled(false);
        farmerStoreAdapter.SetOnItemClickListener(new FarmerStoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, Store_Data model) {
                Toast.makeText(SoilSupplierList.this, model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        /**************Onclick On Top Doctor************************/

        neareststore.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                neareststore.smoothScrollToPosition(farmerStoreAdapter.getItemCount() - 1);
            }
        });
    }
    private void setOffers() {
        ArrayList<Service_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Service_Data("Book Now", R.drawable.soilofferimage));
        singleItemList.add(new Service_Data("Book Now", R.drawable.soilofferimage));
        singleItemList.add(new Service_Data("Book Now",R.drawable.soilofferimage));
        singleItemList.add(new Service_Data("Book Now",R.drawable.soilofferimage));
        singleItemList.add(new Service_Data("Book Now",R.drawable.soilofferimage));
        offerlistdata.add(new Service_Data("Book Now", singleItemList));
        offerItemServices = new OfferItemServices(SoilSupplierList.this, singleItemList);
        offers.setHasFixedSize(true);


        offers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        offers.setAdapter(offerItemServices);
        offers.setNestedScrollingEnabled(false);
        offerItemServices.SetOnItemClickListener(new OfferItemServices.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, Service_Data model) {
                Toast.makeText(SoilSupplierList.this, model.getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

        /**************Onclick On Top Doctor************************/

        offers.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                offers.smoothScrollToPosition(offerItemServices.getItemCount() - 1);
            }
        });
    }
    private void setProductType() {
        ArrayList<Service_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Service_Data("Fertilizers", R.drawable.ptype1));
        singleItemList.add(new Service_Data("Seeds", R.drawable.ptype2));
        singleItemList.add(new Service_Data("Tools",R.drawable.ptype3));
        singleItemList.add(new Service_Data("Manure",R.drawable.ptype4));
        singleItemList.add(new Service_Data("Machinery",R.drawable.ptype5));
        producttypeList.add(new Service_Data("Services", singleItemList));
        productTypeAdapter = new ProductTypeAdapter(SoilSupplierList.this, producttypeList);
        producttype.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        producttype.setLayoutManager(layoutManager);

        producttype.setAdapter(productTypeAdapter);
        productTypeAdapter.SetOnMoreClickListener(new ProductTypeAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(View view, int position, Service_Data model) {
                Toast.makeText(SoilSupplierList.this, "See more " + position, Toast.LENGTH_SHORT).show();

            }
        });
        productTypeAdapter.SetOnItemClickListener(new ProductTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int absolutePosition, int relativePosition, Service_Data model) {
                // Toast.makeText(SoilSupplierList.this, model.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SoilSupplierList.this,ProductByStore.class);
                intent.putExtra("type",model.getTitle());
                startActivity(intent);
            }
        });
        /**************Onclick On Top Doctor************************/

        producttype.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                producttype.smoothScrollToPosition(productTypeAdapter.getItemCount() - 1);
            }
        });
    }
    private void showCartIcon(){
        //toolbar & cartIcon
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.main2_toolbar,null);
        //actionBar.setCustomView(view);

        //************custom action items xml**********************
        CustomCartContainer = findViewById(R.id.CustomCartIconContainer);
        PageTitle = findViewById(R.id.PageTitle);
        PageTitle.setVisibility(View.GONE);
        CustomCartContainer.setVisibility(View.GONE);

    }
    @Override
    protected void onStart() {
        super.onStart();
    /*    drawerLayout = (DrawerLayout) findViewById(R.id.cartDrawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView user_name = headerView.findViewById(R.id.user_name);
        TextView user_mobile=headerView.findViewById(R.id.user_mobile);
        ImageView user_image=headerView.findViewById(R.id.user_image);
        user_name.setText(appPrefs.getFirstName()+" "+appPrefs.getLastName());
        user_mobile.setText(appPrefs.getPhoneNumber());
        setCategoryData();
        //define Navigation Viewer and got its data
        // DefineNavigation();
        //Refresh CartIcon
        showCartIcon();
        //to check if the total price is zero or not
        HandleTotalPriceToZeroIfNotExist();
*/
    }
    @Override
    public void onClick(View view, int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        SupplierDataModel product = CategoryProducts.get(position);

        Intent intent = new Intent(SoilSupplierList.this,ProductList.class);
        intent.putExtra("product",CategoryName);
        intent.putExtra("supplierid",product.getSupplierid());
        intent.putExtra("supplierImage",product.getSupplierImage());
        intent.putExtra("supplierName",product.getSupplierName());
        intent.putExtra("supplierLocation",product.getSupplierLocation());
        intent.putExtra("supplierDetails",product.getSupplierDetails());
        intent.putExtra("IsFavorite",product.getIsFavorite());
        startActivity(intent);
    }

    private void setCategoryData(){
        //toolbar
        mToolBar = findViewById(R.id.CategoryTooBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.CategoryRecycler);
        CategoryProducts = new ArrayList<>();

        adapter = new SuppilerAdapter(SoilSupplierList.this,CategoryProducts,listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(SoilSupplierList.this));
        recyclerView.setAdapter(adapter);

        getProductsData();

        showCartIcon();

    }

    private void getProductsData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("businessinfo").getRef();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        final String supplierid = dataSnapshot.getKey();
                        final String supplierName = dataSnapshot.child("businessname").getValue().toString();
                        final String supplierImage = dataSnapshot.child("logo").getValue().toString();
                        final String supplierLocation = dataSnapshot.child("businessdistrict").getValue().toString();
                        final String supplierDetails = dataSnapshot.child("businessdetails").getValue().toString();
                        //check favorites
                        DatabaseReference Root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                        DatabaseReference x = Root.child("favouritessuppler").child(UserId).child(supplierid);
                        ValueEventListener vvalueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    CategoryProducts.add(new SupplierDataModel(CategoryName,supplierid,supplierImage,supplierName,supplierLocation,supplierDetails,true));
                                }
                                else{
                                    CategoryProducts.add(new SupplierDataModel(CategoryName,supplierid,supplierImage,supplierName,supplierLocation,supplierDetails,false));
                                }
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        };
                        x.addListenerForSingleValueEvent(vvalueEventListener);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        m.addListenerForSingleValueEvent(valueEventListener);
    }

    /*
    
        private void DefineNavigation(){
            drawerLayout = (DrawerLayout) findViewById(R.id.CategoryDrawer);
            navigationView = (NavigationView) findViewById(R.id.CategoryNavigation);
    
            //navigation header
            navigationView.setNavigationItemSelectedListener(this);
            View view = navigationView.getHeaderView(0);
            mPerson_name = view.findViewById(R.id.persname);
            mPerson_image = view.findViewById(R.id.circimage);
    
            mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
            drawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();
    
            getNavHeaderData();
        }
    */
    private void getNavHeaderData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("users").child(UserId);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String Name = snapshot.child("Name").getValue().toString();
                    String Image = snapshot.child("Image").getValue().toString();
                    mPerson_name.setText(Name);
                    if (Image.equals("default")) {
                        Picasso.get().load(R.drawable.profile).into(mPerson_image);
                    } else
                        Picasso.get().load(Image).placeholder(R.drawable.profile).into(mPerson_image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        m.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(SoilSupplierList.this, MarketPlaceHome.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
      /*  if(id==R.id.Home){
            startActivity(new Intent(SoilSupplierList.this,MainActivity.class));
        }
        else if(id==R.id.Profile){
            startActivity(new Intent(SoilSupplierList.this,UserProfileActivity.class));
        }
        else if(id == R.id.Cart){
            startActivity(new Intent(SoilSupplierList.this, CartActivity.class));
        }
        else if(id == R.id.MyOrders){
            startActivity(new Intent(SoilSupplierList.this, OrderActivity.class));
        }
        else if(id == R.id.favourites){
            startActivity(new Intent(SoilSupplierList.this, favourites_activity.class));
        }
        else if(id==R.id.fruits){
            CategoryName = "Fruits";
            setCategoryData();
        }
        else if(id==R.id.vegetables){
            CategoryName = "Vegetables";
            setCategoryData();
        }
        else if(id==R.id.meats){
            CategoryName = "Meats";
            setCategoryData();
        }
        else if(id==R.id.electronics){
            CategoryName = "Electronics";
            setCategoryData();
        }
        else if(id==R.id.Logout){
            CheckLogout();
        }*/
        //drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void CheckLogout(){
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(SoilSupplierList.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(SoilSupplierList.this,loginActivity.class);
                        startActivity(intent);
                        finish();*/
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = checkAlert.create();
        alert.setTitle("LogOut");
        alert.show();

    }





    private void setNumberOfItemsInCartIcon(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("cart").child(UserId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if(dataSnapshot.getChildrenCount()==1){
                        CustomCartNumber.setVisibility(View.GONE);
                    }
                    else {
                        CustomCartNumber.setVisibility(View.VISIBLE);
                        CustomCartNumber.setText(String.valueOf(dataSnapshot.getChildrenCount()-1));
                    }
                }
                else{
                    CustomCartNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        m.addListenerForSingleValueEvent(eventListener);
    }

    private void HandleTotalPriceToZeroIfNotExist(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("cart").child(UserId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("cart").child(UserId).child("totalPrice").setValue("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        m.addListenerForSingleValueEvent(eventListener);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        if(parent==distancespinner) {
            String distance = distancespinner.getSelectedItem().toString();
            Toast.makeText(this, distance, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}