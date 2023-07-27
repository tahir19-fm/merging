package com.taomish.app.android.farmsanta.farmer.nutrisource;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maxwell.speechrecognition.OnSpeechRecognitionListener;
import com.maxwell.speechrecognition.OnSpeechRecognitionPermissionListener;
import com.maxwell.speechrecognition.SpeechRecognition;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome;
import com.mindorks.editdrawabletext.EditDrawableText;
import com.squareup.picasso.Picasso;
import com.stx.xmarqueeview.XMarqueeView;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity;
import com.taomish.app.android.farmsanta.farmer.datamodel.Rent_Data;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.MarketViewAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.ProductAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.QuickLinkAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.RentAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.ServiceAdapter;
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel;
import com.taomish.app.android.farmsanta.farmer.datamodel.Config;
import com.taomish.app.android.farmsanta.farmer.datamodel.Product_Data;
import com.taomish.app.android.farmsanta.farmer.datamodel.Quick_Links;
import com.taomish.app.android.farmsanta.farmer.datamodel.Service_Data;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.w9jds.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MarketPlaceHome extends AppCompatActivity  implements QuickLinkAdapter.ItemListener, OnSpeechRecognitionPermissionListener, OnSpeechRecognitionListener, NavigationView.OnNavigationItemSelectedListener,
      NavigationBarView.OnItemSelectedListener,View.OnClickListener {
    boolean doubleBackToExitPressedOnce = false;
    FloatingSearchView floating_search_view;
    FloatingActionMenu fab;
    Autocomplete autocomplete;
    SpeechRecognition speechRecognition;
    EditDrawableText searchet;
    ProgressBar pro1,pr4;
    LinearLayout notfound,shareshow;
    TextView location,user_name,user_mobile,changelocation,check;
    ImageView locationset,user_image,image;
    DrawerLayout drawer;
    private RecyclerView services,quicklinks,nearestproduct,rentservices,nearestrent;
    ArrayList quicklinksarray;
    private ArrayList<Service_Data> modelList = new ArrayList<>();
    private ArrayList<Rent_Data> rentList = new ArrayList<>();
    private ArrayList<Product_Data> productList = new ArrayList<>(), rentproductList = new ArrayList<>();
    private ServiceAdapter serviceAdapter;
    private ProductAdapter productAdapter,rentproductAdapter;
    private RentAdapter rentAdapter;
    List<String> marketdata = new ArrayList<>();
    XMarqueeView marketprice;
    private int notificationCount = 0,friendsCount=0;

    private final int count = 12;
    String UserId;
    AppPrefs appPrefs;
    LinearLayout farmersupport,buy_product,soil_testing;

BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplacehome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        check=findViewById(R.id.check);
        SignUpAndEditProfileViewModel signUpAndEditProfileViewModel=new SignUpAndEditProfileViewModel();
        appPrefs=new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();
        check.setText(UserId);
      //  Toast.makeText(this, UserId, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buy_product=findViewById(R.id.buy_product);
        soil_testing=findViewById(R.id.soil_testing);
        marketprice=findViewById(R.id.marketprice);
        farmersupport=findViewById(R.id.farmersupport);
        floating_search_view=findViewById(R.id.floating_search_view);
        searchet=findViewById(R.id.search);
        bottomNavigationView=findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        pro1=findViewById(R.id.pro1);
        pro1.setVisibility(View.GONE);
        services = findViewById(R.id.services);
        rentservices=findViewById(R.id.rentservices);
        nearestproduct= findViewById(R.id.nearestproduct);
        nearestrent=findViewById(R.id.nearestrent);
        quicklinks=findViewById(R.id.quicklinks);
      //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        searchet.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        marketdata.add(" Maua-Red sorghum Sorghum 70Ksh/1Kg \u2193 0.00 %");
        marketdata.add(" Ahero-Rice pishori Rice 250Ksh/1Kg \u2191 0.00 %");
        marketdata.add(" Chuka-Rice IRR Rice 94Ksh/1Kg \u2193 0.00 %");
        marketdata.add(" Maua-Red sorghum Sorghum 70Ksh/1Kg \u2193 0.00 %");
        marketdata.add(" Ahero-Rice pishori Rice 250Ksh/1Kg \u2191 0.00 %");
        marketprice.setAdapter(new MarketViewAdapter(marketdata, this));

        //marqueeViewAdapter.setData(data);
        farmersupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MarketPlaceHome.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(MarketPlaceHome.this);
            }
        });
        shareshow=findViewById(R.id.shareshow);
        searchet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        searchet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchvalue= searchet.getText().toString();
                    Toast.makeText(MarketPlaceHome.this, searchvalue, Toast.LENGTH_SHORT).show();

                    return true;
                }
                return false;
            }
        });
        pr4=findViewById(R.id.pro4);
        notfound= findViewById(R.id.notfound);
        changelocation= findViewById(R.id.changelocation);
        changelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int ADDRESS_PICKER_REQUEST = 1020;

            }
        });
        notfound.setVisibility(View.GONE);
        buy_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MarketPlaceHome.this,SupplierList.class);
                // i.putExtra("product","Biofertilizer");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(MarketPlaceHome.this);
            }
        });
        soil_testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MarketPlaceHome.this,SoilSupplierList.class);
                // i.putExtra("product","Biofertilizer");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(MarketPlaceHome.this);
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        user_name=headerView.findViewById(R.id.user_name);
        user_mobile=headerView.findViewById(R.id.user_mobile);
        user_image=headerView.findViewById(R.id.user_image);
        user_name.setText(appPrefs.getFirstName()+" "+appPrefs.getLastName());
        user_mobile.setText(appPrefs.getPhoneNumber());
        // setData(45,100);
        getUserProfileData();
        setNumberOfItemsInCartIcon();
        setQuicklinks();
        setServices();
        setRentServices();
        setRentProducts();
        setProducts();

    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        int itemId = item.getItemId();
        if (itemId == R.id.nmarketplace_order) {
            Intent i= new Intent(MarketPlaceHome.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        } else if (itemId == R.id.nmarketplace_wishlist) {
            Intent i= new Intent(MarketPlaceHome.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        } else if (itemId == R.id.nmarketplace_offer) {
            Intent i= new Intent(MarketPlaceHome.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        }
        else if (itemId == R.id.nmarketplace_cart) {
            Intent i= new Intent(MarketPlaceHome.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
        }
        // It will help to replace the
        // one fragment to other.

        return true;
    };
    private void setNumberOfItemsInCartIcon(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("cart").child(UserId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if(dataSnapshot.getChildrenCount()==1){
                        notificationCount=0;
                    }
                    else {

                        notificationCount= (int) (dataSnapshot.getChildrenCount()-1);
                    }
                }
                else{
                    notificationCount=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        m.addListenerForSingleValueEvent(eventListener);
    }
    private void setQuicklinks() {
        quicklinks.setHasFixedSize(true);
        quicklinks.setLayoutManager(new GridLayoutManager(this, 4));//Linear Items
        quicklinksarray = new ArrayList();
        quicklinksarray.add(new Quick_Links("Fertilizer",R.drawable.fertilizer));
        quicklinksarray.add(new Quick_Links("Bio-fertilizer",R.drawable.biofertilizer));
        quicklinksarray.add(new Quick_Links("Insecticide",R.drawable.insecticide));
        quicklinksarray.add(new Quick_Links("Fungicide",R.drawable.fungicide));
        quicklinksarray.add(new Quick_Links("Nematicide",R.drawable.nematicide));
        quicklinksarray.add(new Quick_Links("Acaricide",R.drawable.cubax));
        quicklinksarray.add(new Quick_Links("Herbicide",R.drawable.herbicide));
        quicklinksarray.add(new Quick_Links("Plough",R.drawable.plow));

        quicklinksarray.add(new Quick_Links("Tractor",R.drawable.tractor2));
        quicklinksarray.add(new Quick_Links("Harrow",R.drawable.harrow));
        quicklinksarray.add(new Quick_Links("Sprayer",R.drawable.sprayer));
        quicklinksarray.add(new Quick_Links("Seed Drill",R.drawable.seeddrill));

        quicklinksarray.add(new Quick_Links("Baler",R.drawable.baler));
        quicklinksarray.add(new Quick_Links("Mower",R.drawable.mower));
        quicklinksarray.add(new Quick_Links("Sickle",R.drawable.sickle));
        quicklinksarray.add(new Quick_Links("Cultipacker",R.drawable.cultipacker));
        QuickLinkAdapter quickLinkAdapter = new QuickLinkAdapter(this, quicklinksarray,  this);
        quicklinks.setAdapter(quickLinkAdapter);// set adapter on recyclerview
    }
    private void getUserProfileData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("users").child("seller"+ Config.userid);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String Name = snapshot.child("name").getValue().toString();
                    String Image = snapshot.child("image").getValue().toString();
                    String Phone = snapshot.child("phone").getValue().toString();
                    user_name.setText(Name);
                    user_mobile.setText(Phone);
                    if (Image.equals("default")) {
                        Picasso.get().load(R.drawable.profile).into(user_image);
                    } else
                        Picasso.get().load(Image).placeholder(R.drawable.profile).into(user_image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        m.addListenerForSingleValueEvent(valueEventListener);
    }
    @Override
    public void onItemClick(Quick_Links item) {
        Intent i= new Intent(MarketPlaceHome.this,SupplierList.class);
           i.putExtra("product",item.title);
       // i.putExtra("product","Biofertilizer");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateSlideLeft(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setNumberOfItemsInCartIcon();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        //  walletbalance = menu.findItem(R.id.walletbalance);
        ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), FontAwesome.Icon.faw_bell1, ActionItemBadge.BadgeStyles.RED, notificationCount);
        //ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge1), FontAwesome.Icon.faw_comments, ActionItemBadge.BadgeStyles.BLUE, friendsCount);
      /*  if (notificationCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), FontAwesome.Icon.faw_bell1, ActionItemBadge.BadgeStyles.RED, notificationCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        }
        if (friendsCount > 0) {

            ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge1), FontAwesome.Icon.faw_user1, ActionItemBadge.BadgeStyles.RED, friendsCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge1));
        }*/
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.walletpayment) {
            Intent i= new Intent(MarketPlaceHome.this, Payment.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateCard(MarketPlaceHome.this);
        }
        else if (id == R.id.walletbalance) {
            Intent i= new Intent(MarketPlaceHome.this, Payment.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Animatoo.animateCard(MarketPlaceHome.this);
        }*/
        if (item.getItemId() == R.id.item_samplebadge) {
            if(notificationCount>0) {
                notificationCount=0;
                //seennotification();
            }
            else {
                Toast.makeText(this, "You are all caught up!", Toast.LENGTH_SHORT).show();
                /*Intent i5 = new Intent(MarketPlaceHome.this, NotificationList.class);
                i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i5);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/
            }
            ActionItemBadge.update(item, notificationCount);
        }
        if (item.getItemId() == R.id.item_samplebadge1) {
          /*  if(friendsCount>0) {
                friendsCount=0;
                seenrequest();
            }
            else {
                Toast.makeText(this, "You have no new student request!", Toast.LENGTH_SHORT).show();
                Intent i5 = new Intent(MarketPlaceHome.this, LinkRequest.class);
                i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i5);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            ActionItemBadge.update(item, friendsCount);*/
            AlertDialog.Builder builder = new AlertDialog.Builder(MarketPlaceHome.this);

            // Set a title for alert dialog
            builder.setTitle("Write To Us!");

            // Ask the final question
            builder.setMessage(Html.fromHtml("<font color='#000000'>Write To Us your feedback and comments or suggestions</font>"));

            // Set the alert dialog yes button click listener
            builder.setPositiveButton("Write", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do something when user clicked the Yes button
                    // Set the TextView visibility GONE
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto","contact@doctorsector.com", null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                    startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                    // Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
                }
            });

            // Set the alert dialog no button click listener
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do something when No button clicked

                }
            });

            AlertDialog dialog = builder.create();
            // Display the alert dialog on interface
            dialog.show();

        }
        if(item.getItemId()==R.id.about_us){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.doctorsector.com/"));
            startActivity(browserIntent);
        }
        if(item.getItemId()==R.id.share){
        }
        if (item.getItemId() == R.id.writetous) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.doctorsector.nearest.doctorfinder")));

        }

        if(item.getItemId()==R.id.more_app){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6620625663518497622"));
            startActivity(browserIntent);
        }
        if(item.getItemId()==R.id.exit){
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);// finish activity
            finish();
        }
        if(item.getItemId()==R.id.logout){


        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
    private void setRentProducts(){

        ArrayList<Product_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Product_Data("S&M Rent Store", R.drawable.sm, "5 Km away","Tractor,Tools,Machine"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.farmstore, "10 Km away","Fertilizers,Seeds,Manure"));
        singleItemList.add(new Product_Data("S&M Rent Store", R.drawable.sm, "15 Km away","Tractor,Tools,Machine"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.farmstore, "20 Km away","Fertilizers,Seeds,Manure"));
        //singleItemList.add(new Service_Data("Agricultural Equipment",R.drawable.gardening));
        //singleItemList.add(new Service_Data("Agro Chemicals", R.drawable.pesticide));


        rentproductList.add(new Product_Data("Services", singleItemList));
        rentproductAdapter = new ProductAdapter(MarketPlaceHome.this, rentproductList);
        nearestrent.setHasFixedSize(true);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        nearestrent.setLayoutManager(layoutManager);

        nearestrent.setAdapter(rentproductAdapter);

        /**************Onclick On Top Doctor************************/
        rentproductAdapter.SetOnMoreClickListener(new ProductAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(View view, int position, Product_Data model) {
                Toast.makeText(MarketPlaceHome.this, "See more " + position, Toast.LENGTH_SHORT).show();

            }
        });
        rentproductAdapter.SetOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int absolutePosition, int relativePosition, Product_Data model) {
                if(relativePosition==0){
                    Intent intent= new Intent(MarketPlaceHome.this, SoilTesting.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(MarketPlaceHome.this);
                }
                else if(relativePosition==1) {
                    Toast.makeText(MarketPlaceHome.this, "", Toast.LENGTH_SHORT).show();
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
    private void setProducts(){

        ArrayList<Product_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.productimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.productimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.productimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        singleItemList.add(new Product_Data("Farmer Store", R.drawable.productimage, "Village Market Road","Fertilizers,Seeds,Manure"));
        //singleItemList.add(new Service_Data("Agricultural Equipment",R.drawable.gardening));
        //singleItemList.add(new Service_Data("Agro Chemicals", R.drawable.pesticide));


        productList.add(new Product_Data("Services", singleItemList));
        productAdapter = new ProductAdapter(MarketPlaceHome.this, productList);
        nearestproduct.setHasFixedSize(true);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        nearestproduct.setLayoutManager(layoutManager);

        nearestproduct.setAdapter(productAdapter);

        /**************Onclick On Top Doctor************************/
        productAdapter.SetOnMoreClickListener(new ProductAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(View view, int position, Product_Data model) {
                Toast.makeText(MarketPlaceHome.this, "See more " + position, Toast.LENGTH_SHORT).show();

            }
        });
        productAdapter.SetOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int absolutePosition, int relativePosition, Product_Data model) {
                if(relativePosition==0){
                    Intent intent= new Intent(MarketPlaceHome.this, SoilTesting.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(MarketPlaceHome.this);
                }
                else if(relativePosition==1) {
                    Toast.makeText(MarketPlaceHome.this, "", Toast.LENGTH_SHORT).show();
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
    private void setServices() {
        ArrayList<Service_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Service_Data("Fertilizers", R.drawable.ferti));
        singleItemList.add(new Service_Data("Seeds", R.drawable.seeds));
        singleItemList.add(new Service_Data("Organic Manure",R.drawable.organic));
        singleItemList.add(new Service_Data("Insurance",R.drawable.ferti));
        //singleItemList.add(new Service_Data("Agricultural Equipment",R.drawable.gardening));
       //singleItemList.add(new Service_Data("Agro Chemicals", R.drawable.pesticide));


        modelList.add(new Service_Data("Services", singleItemList));
        serviceAdapter = new ServiceAdapter(MarketPlaceHome.this, modelList);
        services.setHasFixedSize(true);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        services.setLayoutManager(layoutManager);

        services.setAdapter(serviceAdapter);

        serviceAdapter.SetOnMoreClickListener(new ServiceAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(View view, int position, Service_Data model) {
                Toast.makeText(MarketPlaceHome.this, "See more " + position, Toast.LENGTH_SHORT).show();

            }
        });
        /**************Onclick On Top Doctor************************/
        serviceAdapter.SetOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int absolutePosition, int relativePosition, Service_Data model) {
                if(relativePosition==0){
                    Intent intent= new Intent(MarketPlaceHome.this, SoilTesting.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(MarketPlaceHome.this);
                }
                else if(relativePosition==1) {
                    Toast.makeText(MarketPlaceHome.this, "", Toast.LENGTH_SHORT).show();
                }




            }});
        services.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                services.smoothScrollToPosition(serviceAdapter.getItemCount() - 1);
            }
        });

    }
    private void setRentServices() {
        ArrayList<Rent_Data> singleItemList = new ArrayList<>();
        //   singleItemList.add(new Service_Data("Seeds & Seedlings",R.drawable.seedbag));
        singleItemList.add(new Rent_Data("Harvesting Machine", R.drawable.harvestingmachine));
        singleItemList.add(new Rent_Data("Tractors", R.drawable.tractor));
        singleItemList.add(new Rent_Data("Seed Driller",R.drawable.seeddrill));
        singleItemList.add(new Rent_Data("Harvesting Machine", R.drawable.harvestingmachine));
        singleItemList.add(new Rent_Data("Tractors", R.drawable.tractor));
        singleItemList.add(new Rent_Data("Seed Driller",R.drawable.seeddrill));
        //singleItemList.add(new Service_Data("Agricultural Equipment",R.drawable.gardening));
        //singleItemList.add(new Service_Data("Agro Chemicals", R.drawable.pesticide));


        rentList.add(new Rent_Data("Rent", singleItemList));
        rentAdapter = new RentAdapter(MarketPlaceHome.this, rentList);
        rentservices.setHasFixedSize(true);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        rentservices.setLayoutManager(layoutManager);

        rentservices.setAdapter(rentAdapter);

        rentAdapter.SetOnMoreClickListener(new RentAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(View view, int position, Rent_Data model) {
                Toast.makeText(MarketPlaceHome.this, "See more " + position, Toast.LENGTH_SHORT).show();

            }
        });
        /**************Onclick On Top Doctor************************/
        rentAdapter.SetOnItemClickListener(new RentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int absolutePosition, int relativePosition, Rent_Data model) {
                if(relativePosition==0){
                    Intent intent= new Intent(MarketPlaceHome.this, SoilTesting.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(MarketPlaceHome.this);
                }
                else if(relativePosition==1) {
                    Toast.makeText(MarketPlaceHome.this, "", Toast.LENGTH_SHORT).show();
                }




            }});
        rentservices.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                services.smoothScrollToPosition(rentAdapter.getItemCount() - 1);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(MarketPlaceHome.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        marketprice.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marketprice.stopFlipping();
    }
    @Override
    public void onClick(View view) {

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.marketplace_home:
                Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nmarketplace_order:
                startActivity(new Intent(MarketPlaceHome.this,OrderActivity.class));
                break;
            case R.id.nmarketplace_wishlist:
                startActivity(new Intent(MarketPlaceHome.this, favourites_activity.class));
                break;
            case R.id.nmarketplace_offer:
               // openPopFragment();
                break;
            case R.id.nmarketplace_cart:
                startActivity(new Intent(MarketPlaceHome.this, CartActivity.class));
                break;
        }

        return true;

    }

    @Override
    public void OnSpeechRecognitionStarted() {
        Toast.makeText(MarketPlaceHome.this, "Start Speaking", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void OnSpeechRecognitionStopped() {
        //  Toast.makeText(MarketPlaceHome.this, "Stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSpeechRecognitionFinalResult(String s) {
        String searchvalue= s;
        Toast.makeText(MarketPlaceHome.this, "Result: "+searchvalue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSpeechRecognitionCurrentResult(String s) {
        String searchvalue= s;
        searchet.setText(searchvalue);
    }

    @Override
    public void OnSpeechRecognitionError(int i, String s) {

    }

    @Override
    public void onPermissionGranted() {
        speechRecognition.startSpeechRecognition();
    }

    @Override
    public void onPermissionDenied() {

    }


}
