package com.taomish.app.android.farmsanta.farmer.nutrisource;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.taomish.app.android.farmsanta.farmer.nutriadapter.MyAdapter_Recycler_View;
import com.taomish.app.android.farmsanta.farmer.datamodel.favouritesClass;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class favourites_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;
    private TextView mperson_name;
    private CircleImageView image;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private String UserId;
    private RecyclerView.Adapter my_adapter;
    //Custom Xml Views (cart Icon)
    private RelativeLayout CustomCartContainer;
    private TextView PageTitle;
    private TextView CustomCartNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_activity);

        mAuth = FirebaseAuth.getInstance();
        AppPrefs appPrefs = new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();

        mToolBar = findViewById(R.id.main_Toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //define Navigation Viewer and got its data
       // DefineNavigation();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Refresh CartIcon
        showCartIcon();

        //to check if the total price is zero or not
        HandleTotalPriceToZeroIfNotExist();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.marketplace_home:
                startActivity(new Intent(favourites_activity.this,MarketPlaceHome.class));
                break;
            case R.id.nmarketplace_order:
                startActivity(new Intent(favourites_activity.this,OrderActivity.class));
                break;
            case R.id.nmarketplace_wishlist:
                startActivity(new Intent(favourites_activity.this, favourites_activity.class));
                break;
            case R.id.nmarketplace_offer:
                // openPopFragment();
                break;
            case R.id.nmarketplace_cart:
                startActivity(new Intent(favourites_activity.this, CartActivity.class));
                break;
        }
        return true;

    }


    private void CheckLogout(){
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(favourites_activity.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /* FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(favourites_activity.this,loginActivity.class);
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



    public void Retrieve_fav() {
        AppPrefs appPrefs = new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();
        LinearLayout mylayout = findViewById(R.id.recyclerViewlayout);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.favourite_recycler_view, mylayout, false);
        final RecyclerView rc = mylayout.findViewById(R.id.recyclerView);
        // rc.setHasFixedSize(true);
        // rc.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager mGridLayoutManager;
        mGridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rc.setLayoutManager(mGridLayoutManager);
        final List<favouritesClass> favourite_list = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("favourites")
                .child(UserId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    favouritesClass fav = new favouritesClass();
                    fav = ds.getValue(favouritesClass.class);
                    favourite_list.add(fav);
                }
                my_adapter = new MyAdapter_Recycler_View(favourite_list);
                rc.setAdapter(my_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }

    private void getNavHeaderData() {
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("users").child(UserId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String photo = dataSnapshot.child("Image").getValue().toString();
                    if (photo.equals("default")) {
                        Picasso.get().load(R.drawable.profile).into(image);
                    } else
                        Picasso.get().load(photo).placeholder(R.drawable.profile).into(image);
                    mperson_name.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        m.addListenerForSingleValueEvent(valueEventListener);
        Retrieve_fav();
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

        PageTitle.setText("Favourites");
        setNumberOfItemsInCartIcon();

        CustomCartContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(favourites_activity.this, CartActivity.class));
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