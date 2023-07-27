package com.taomish.app.android.farmsanta.farmer.nutrisource;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartCheckActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PaymentResultListener {
    String ttlPrice , DelPrice , ttlPrice2 , saved;
    DatabaseReference root;
    String CurrentUser;
    TextView totalPrice , DelivaryPrice , totalPrice2 , savedamount ;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private TextView mPerson_name;
    private CircleImageView mPerson_image;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUsr;
    private String UserId;
    //Custom Xml Views (cart Icon)
    private RelativeLayout CustomCartContainer;
    private TextView PageTitle;
    private TextView CustomCartNumber;
    private EditText name,contactno,email,address;
    private String Name="",Contact="",Email="",Address="";
    AppPrefs appPrefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_check);
       // mAuth = FirebaseAuth.getInstance();
        // CurrentUsr = mAuth.getCurrentUser();
        appPrefs=new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();

        mToolbar = findViewById(R.id.cartCheckToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button Done = findViewById(R.id.Done);
        Button Cancel = findViewById(R.id.Cancel);
        totalPrice = findViewById(R.id.Ttl_ItemsPrice);
        DelivaryPrice = findViewById(R.id.DelivaryPrice);
        totalPrice2 = findViewById(R.id.TotalAmount);
        savedamount=findViewById(R.id.SavedAmount);
        name=findViewById(R.id.name);
        name.setText(appPrefs.getFirstName()+" "+appPrefs.getLastName());
        contactno=findViewById(R.id.contactno);
        contactno.setText(appPrefs.getPhoneNumber());
        email=findViewById(R.id.email);
        email.setText(appPrefs.getEmail());
        address=findViewById(R.id.address);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=name.getText().toString();
                Contact=contactno.getText().toString();
                Email=email.getText().toString();
                Address=address.getText().toString();
                if(!Name.equals("")&&!Contact.equals("")&&!Address.equals("")) {
                    startPayment(totalPrice2.getText().toString(),"INR");
                }
                else {
                    Toast.makeText(CartCheckActivity.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getCheckData();

    }

    private void getUserProfileData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("users").child(UserId);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                 //   String Name = snapshot.child("Name").getValue().toString();
                    String Phone = snapshot.child("Phone").getValue().toString();
                  //  name.setText(Name);
                    contactno.setText(Phone);
                    email.setText(CurrentUsr.getEmail());

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        m.addListenerForSingleValueEvent(valueEventListener);
    }

    private void  getCheckData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        FirebaseAuth mAuth ;
        mAuth = FirebaseAuth.getInstance();
        String CurrentUser = UserId;
        DatabaseReference m = root.child("cart").child(CurrentUser);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ttlPrice = dataSnapshot.child("totalPrice").getValue().toString();
                    Log.d("ttl" , ttlPrice);
                    totalPrice.setText(ttlPrice);
                    DelivaryPrice.setText("Free");
                    totalPrice2.setText(ttlPrice);
                    savedamount.setVisibility(View.INVISIBLE);
                    getUserProfileData();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        m.addListenerForSingleValueEvent(eventListener);
    }
    private void savedDate(String status,String paymentid,String paymentmsg)
    {
        root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        FirebaseAuth mAuth ;
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = UserId;
        DatabaseReference x = root.child("cart").child(CurrentUser);
        x.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseDatabase t = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app");
                String key  = t.getReference("order").push().getKey();
                root.child("order").child(CurrentUser).child(key).child("orderproducts").setValue(snapshot.getValue());
                root.child("order").child(CurrentUser).child(key).child("totalPrice").setValue(snapshot.child("totalPrice").getValue());
                root.child("order").child(CurrentUser).child(key).child("orderproducts").child("totalPrice").removeValue();
                root.child("order").child(CurrentUser).child(key).child("Date").setValue(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Calendar.getInstance().getTime()));
                root.child("order").child(CurrentUser).child(key).child("IsChecked").setValue(status);
                root.child("order").child(CurrentUser).child(key).child("Paymentid").setValue(paymentid);
                root.child("order").child(CurrentUser).child(key).child("Paymentmsg").setValue(paymentmsg);
                root.child("order").child(CurrentUser).child(key).child("sellerid").setValue(paymentmsg);
                root.child("order").child(CurrentUser).child(key).child("Name").setValue(Name);
                root.child("order").child(CurrentUser).child(key).child("Email").setValue(Email);
                root.child("order").child(CurrentUser).child(key).child("Phone").setValue(Contact);
                root.child("order").child(CurrentUser).child(key).child("Address").setValue(Address);
                root.child("order").child(CurrentUser).child(key).child("Sellerid").setValue(Address);

                Toast.makeText( getApplicationContext() ,"Confermed Completed" , Toast.LENGTH_LONG).show();
                root.child("cart").child(CurrentUser).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        drawerLayout = findViewById(R.id.cartCheckDrawer);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView user_name = headerView.findViewById(R.id.user_name);
        TextView user_mobile=headerView.findViewById(R.id.user_mobile);
        ImageView user_image=headerView.findViewById(R.id.user_image);
        user_name.setText(appPrefs.getFirstName()+" "+appPrefs.getLastName());
        user_mobile.setText(appPrefs.getPhoneNumber());


       // getNavHeaderData();

        //Refresh CartIcon
        showCartIcon();

        //to check if the total price is zero or not
        HandleTotalPriceToZeroIfNotExist();

    }


    private void getNavHeaderData(){
        FirebaseAuth mAuth ;
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = UserId;
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
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
    /*    if(id==R.id.Home){
            startActivity(new Intent(CartCheckActivity.this,MainActivity.class));
        }
        else if(id==R.id.Profile){
            startActivity(new Intent(CartCheckActivity.this,UserProfileActivity.class));
        }
        else if(id == R.id.favourites){
            startActivity(new Intent(CartCheckActivity.this, favourites_activity.class));
        }
        else if(id == R.id.Cart){
            startActivity(new Intent(CartCheckActivity.this, CartActivity.class));
        }
        else if(id == R.id.MyOrders){
            startActivity(new Intent(CartCheckActivity.this, OrderActivity.class));
        }
        else if(id==R.id.fruits){
            Intent intent =new Intent(CartCheckActivity.this,CategoryActivity.class);
            intent.putExtra("Category Name","Fruits");
            startActivity(intent);
        }
        else if(id==R.id.vegetables){
            Intent intent =new Intent(CartCheckActivity.this,CategoryActivity.class);
            intent.putExtra("Category Name","Vegetables");
            startActivity(intent);
        }
        else if(id==R.id.meats){
            Intent intent =new Intent(CartCheckActivity.this,CategoryActivity.class);
            intent.putExtra("Category Name","Meats");
            startActivity(intent);
        }
        else if(id==R.id.electronics){
            Intent intent =new Intent(CartCheckActivity.this,CategoryActivity.class);
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
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(CartCheckActivity.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(CartCheckActivity.this,loginActivity.class);
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

        PageTitle.setText("Check Order");
        setNumberOfItemsInCartIcon();

        CustomCartContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartCheckActivity.this, CartActivity.class));
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
    public void startPayment(String amount,String currency) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", Name);
            options.put("description", "Farm Santa Market Place");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://i.ibb.co/X3rxdc1/logo.png");
            options.put("currency", currency);
            options.put("amount", String.valueOf(100*Integer.parseInt(amount)));
            JSONObject preFill = new JSONObject();
            preFill.put("email", Email);
            preFill.put("contact", Contact);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        String status="true";
        String orderid=razorpayPaymentID;
        try {
            String msg="Payment Successful: " + razorpayPaymentID;
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            savedDate(status,orderid,msg);
            CartActivity.fa.finish();
            Intent i=new Intent(CartCheckActivity.this, OrderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        } catch (Exception e) {
            Log.e("Payment", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        String status="false";
        String orderid=String.valueOf(code);
        try {
            String msg="Payment failed: " + code + " " + response;
            //setpayment();
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Payment", "Exception in onPaymentError", e);
        }
    }
}
