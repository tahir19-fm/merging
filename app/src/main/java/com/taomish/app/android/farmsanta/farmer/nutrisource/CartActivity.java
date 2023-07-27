package com.taomish.app.android.farmsanta.farmer.nutrisource;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private TextView mPerson_name;
    private CircleImageView mPerson_image;
    private RelativeLayout CustomCartContainer;
    private TextView PageTitle;
    public static Activity fa;
    AppPrefs appPrefs;
    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        fa=this;
        appPrefs=new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();
        mToolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.Cartframe,new MyCartFragment()).commit();

    }



    @Override
    protected void onStart() {
        super.onStart();
        drawerLayout = findViewById(R.id.cartDrawer);
   /*   navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    View headerView = navigationView.getHeaderView(0);
        TextView user_name = headerView.findViewById(R.id.user_name);
        TextView user_mobile=headerView.findViewById(R.id.user_mobile);
        ImageView user_image=headerView.findViewById(R.id.user_image);
        user_name.setText(appPrefs.getFirstName()+" "+appPrefs.getLastName());
        user_mobile.setText(appPrefs.getPhoneNumber());
*/

        showCartIcon();
    }


    private void getNavHeaderData(){
        FirebaseAuth mAuth ;
        mAuth = FirebaseAuth.getInstance();
        String CurrentUser = mAuth.getCurrentUser().getUid();
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference m = root.child("users").child(CurrentUser);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
     /*   if(id==R.id.Home){
            startActivity(new Intent(CartActivity.this,MainActivity.class));
        }
        else if(id==R.id.Profile){
            startActivity(new Intent(CartActivity.this,UserProfileActivity.class));
        }
        else if(id == R.id.favourites){
            startActivity(new Intent(CartActivity.this, favourites_activity.class));
        }
        else if(id == R.id.MyOrders){
            startActivity(new Intent(CartActivity.this, OrderActivity.class));
        }
        else if(id==R.id.fruits){
            Intent intent =new Intent(CartActivity.this,CategoryActivity.class);
            intent.putExtra("Category Name","Fruits");
            startActivity(intent);
        }
        else if(id==R.id.vegetables){
            Intent intent =new Intent(CartActivity.this,CategoryActivity.class);
            intent.putExtra("Category Name","Vegetables");
            startActivity(intent);
        }
        else if(id==R.id.meats){
            Intent intent =new Intent(CartActivity.this,CategoryActivity.class);
            intent.putExtra("Category Name","Meats");
            startActivity(intent);
        }
        else if(id==R.id.electronics){
            Intent intent =new Intent(CartActivity.this,CategoryActivity.class);
            intent.putExtra("Category Name","Electronics");
            startActivity(intent);
        }
        else if(id==R.id.Logout){
            CheckLogout();
        }*/
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    private void CheckLogout(){
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(CartActivity.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /* FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(CartActivity.this,loginActivity.class);
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
        //actionBar.setCustomView(view);

        //************custom action items xml**********************
        CustomCartContainer = findViewById(R.id.CustomCartIconContainer);
        PageTitle = findViewById(R.id.PageTitle);
        PageTitle.setVisibility(View.GONE);
        CustomCartContainer.setVisibility(View.GONE);

    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(CartActivity.this, MarketPlaceHome.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
}