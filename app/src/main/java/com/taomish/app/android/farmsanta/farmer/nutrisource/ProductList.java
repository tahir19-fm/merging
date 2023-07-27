package com.taomish.app.android.farmsanta.farmer.nutrisource;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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
import com.taomish.app.android.farmsanta.farmer.nutriadapter.CategoryProductInfoAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.OtherProductInfoAdapter;
import com.taomish.app.android.farmsanta.farmer.datamodel.CategoryProductInfo;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductList  extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;

    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;
    private TextView mPerson_name;
    private CircleImageView mPerson_image;
    //------------------------------------
    private String CategoryName,supplierid,supplierImage,supplierName,supplierLocation,supplierDetails;
    private boolean IsFavorite;
    private RecyclerView recyclerView,CategoryRecycler1;
    private ArrayList<CategoryProductInfo> CategoryProducts,OtherCategoryProducts;
    private CategoryProductInfoAdapter adapter;
    private OtherProductInfoAdapter otheradapter;
    //------------------------------------
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private String UserId;
    private CategoryProductInfoAdapter.RecyclerViewClickListener listener;
    private OtherProductInfoAdapter.OtherProductClickListener listener1;
    //Custom Xml Views (cart Icon)
    private RelativeLayout CustomCartContainer;
    private TextView PageTitle, CustomCartNumber,name,description,productcategory;
    AppPrefs appPrefs;
    ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        appPrefs=new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();

        CategoryName = getIntent().getStringExtra("product");
        supplierid = getIntent().getStringExtra("supplierid");
        supplierImage = getIntent().getStringExtra("supplierImage");
        supplierName = getIntent().getStringExtra("supplierName");
        supplierLocation = getIntent().getStringExtra("supplierLocation");
        supplierDetails = getIntent().getStringExtra("supplierDetails");
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        productcategory=findViewById(R.id.productcategory);
        ivImage=findViewById(R.id.ivImage);
        name.setText(supplierName);
        Picasso.get().load(supplierImage).into(ivImage);
        productcategory.setText(CategoryName);
        description.setText(supplierDetails);
        //on clicking any product (go to ProductInfo Activity to show it's info)
        onClickAnyProduct();

    }

    @Override
    protected void onStart() {
        super.onStart();
        drawerLayout = findViewById(R.id.cartDrawer);
        navigationView = findViewById(R.id.nav_view);

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

    }

    private void onClickAnyProduct(){
        listener = new CategoryProductInfoAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                CategoryProductInfo product = CategoryProducts.get(position);

                Intent intent = new Intent(ProductList.this,ProductInfoActivity.class);
                intent.putExtra("Product Name",product.getProductName());
                intent.putExtra("Product Details",product.getProductDetails());
                intent.putExtra("Product Price",product.getProductPrice());
                intent.putExtra("Product Image",product.getProductImage());
                intent.putExtra("Product ExpiryDate",product.getProductExpiryDate());
                intent.putExtra("supplierid",supplierid);
                intent.putExtra("supplierImage",supplierImage);
                intent.putExtra("supplierName",supplierName);
                intent.putExtra("supplierLocation",supplierLocation);
                intent.putExtra("supplierDetails",supplierDetails);
                intent.putExtra("Product IsFavorite",String.valueOf(product.getIsFavorite()));
                intent.putExtra("Is Offered","no");
                startActivity(intent);
            }
        };
    }

    private void onClickOtherProduct(){
        listener1 = new OtherProductInfoAdapter.OtherProductClickListener() {
            @Override
            public void onClick(View view, int position) {
                CategoryProductInfo product = CategoryProducts.get(position);

                Intent intent = new Intent(ProductList.this,ProductInfoActivity.class);
                intent.putExtra("Product Name",product.getProductName());
                intent.putExtra("Product Details",product.getProductDetails());
                intent.putExtra("Product Price",product.getProductPrice());
                intent.putExtra("Product Image",product.getProductImage());
                intent.putExtra("Product ExpiryDate",product.getProductExpiryDate());
                intent.putExtra("supplierid",supplierid);
                intent.putExtra("supplierImage",supplierImage);
                intent.putExtra("supplierName",supplierName);
                intent.putExtra("supplierLocation",supplierLocation);
                intent.putExtra("supplierDetails",supplierDetails);
                intent.putExtra("Product IsFavorite",String.valueOf(product.getIsFavorite()));
                intent.putExtra("Is Offered","no");
                startActivity(intent);
            }
        };
    }
    private void setCategoryData(){
        //toolbar
        mToolBar = findViewById(R.id.CategoryTooBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.CategoryRecycler);
        CategoryProducts = new ArrayList<>();
        OtherCategoryProducts= new ArrayList<>();
        CategoryRecycler1= findViewById(R.id.CategoryRecycler1);
        adapter = new CategoryProductInfoAdapter(ProductList.this,CategoryProducts,listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductList.this));
        recyclerView.setAdapter(adapter);
        otheradapter= new OtherProductInfoAdapter(ProductList.this,OtherCategoryProducts,listener1);
        CategoryRecycler1.setLayoutManager(new LinearLayoutManager(ProductList.this));
        CategoryRecycler1.setAdapter(otheradapter);
        getProductsData();
        getOtherProductsData();
        showCartIcon();

    }
    private void getOtherProductsData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("product").getRef();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                        final String ProductcatName = dataSnapshot1.getKey();

                        DatabaseReference ss = root.child("product").child(ProductcatName).orderByChild("sellerid").equalTo(supplierid).getRef();
                        ValueEventListener valueEventListener1 = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                        final String ProductName = dataSnapshot.getKey();
                                        final String ProductDetails = dataSnapshot.child("details").getValue().toString();
                                        final String sellerid = dataSnapshot.child("selletid").getValue().toString();
                                        final String ProductPrice = dataSnapshot.child("price").getValue().toString();
                                        final String ProductImage = dataSnapshot.child("image").getValue().toString();
                                        final String ProductExpiryDate = dataSnapshot.child("expired").getValue().toString();

                                        //check favorites
                                        DatabaseReference Root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                                        DatabaseReference x = Root.child("favourites").child(UserId).child(ProductName);
                                        ValueEventListener vvalueEventListener = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    OtherCategoryProducts.add(new CategoryProductInfo(ProductDetails,sellerid,supplierImage,supplierName, supplierLocation,supplierDetails,ProductImage,ProductName, ProductPrice, ProductExpiryDate, true));
                                                } else {
                                                    OtherCategoryProducts.add(new CategoryProductInfo(ProductDetails,sellerid,supplierImage,supplierName, supplierLocation,supplierDetails,ProductImage, ProductName, ProductPrice, ProductExpiryDate, false));
                                                }
                                                otheradapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        };
                                        x.addListenerForSingleValueEvent(vvalueEventListener);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        };
                        ss.addListenerForSingleValueEvent(valueEventListener1);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        m.addListenerForSingleValueEvent(valueEventListener);
    }
    private void getProductsData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("product").child(CategoryName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        final String ProductName = dataSnapshot.getKey();
                        final String ProductDetails = dataSnapshot.child("details").getValue().toString();
                        final String sellerid = dataSnapshot.child("selletid").getValue().toString();
                        final String ProductPrice = dataSnapshot.child("price").getValue().toString();
                        final String ProductImage = dataSnapshot.child("image").getValue().toString();
                        final String ProductExpiryDate = dataSnapshot.child("expired").getValue().toString();

                        //check favorites
                        DatabaseReference Root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                        DatabaseReference x = Root.child("favourites").child(UserId).child(ProductName);
                        ValueEventListener vvalueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    CategoryProducts.add(new CategoryProductInfo(ProductDetails,sellerid,supplierImage,supplierName, supplierLocation,supplierDetails,ProductImage,ProductName,ProductPrice,ProductExpiryDate,true));
                                }
                                else{
                                    CategoryProducts.add(new CategoryProductInfo(ProductDetails,sellerid,supplierImage,supplierName, supplierLocation,supplierDetails,ProductImage,ProductName,ProductPrice,ProductExpiryDate,false));
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
        Intent i= new Intent(ProductList.this, MarketPlaceHome.class);
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
            startActivity(new Intent(ProductList.this,MainActivity.class));
        }
        else if(id==R.id.Profile){
            startActivity(new Intent(ProductList.this,UserProfileActivity.class));
        }
        else if(id == R.id.Cart){
            startActivity(new Intent(ProductList.this, CartActivity.class));
        }
        else if(id == R.id.MyOrders){
            startActivity(new Intent(ProductList.this, OrderActivity.class));
        }
        else if(id == R.id.favourites){
            startActivity(new Intent(ProductList.this, favourites_activity.class));
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
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void CheckLogout(){
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(ProductList.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(ProductList.this,loginActivity.class);
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


    private void showCartIcon(){
        //toolbar & cartIcon
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.main2_toolbar,null);
        actionBar.setCustomView(view);

        //************custom action items xml**********************
        CustomCartContainer = findViewById(R.id.CustomCartIconContainer);
        PageTitle = findViewById(R.id.PageTitle);
        CustomCartNumber = findViewById(R.id.CustomCartNumber);

        PageTitle.setText(CategoryName);
        setNumberOfItemsInCartIcon();

        CustomCartContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ProductList.this, CartActivity.class));
            }
        });

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

}