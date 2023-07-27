package com.taomish.app.android.farmsanta.farmer.nutrisource;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.LabInfoAdapter;
import com.taomish.app.android.farmsanta.farmer.datamodel.Labdata;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;

import java.util.ArrayList;

public class SoilTesting extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView addid;
    RecyclerView ProductsRecycler;
    private ArrayList<Labdata> LabList;
    private LabInfoAdapter.LabClickListener listener;
    String UserId;
    AppPrefs appPrefs;
    private LabInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_testing);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.soilcolor));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        addid=findViewById(R.id.addid);
        appPrefs=new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();
        setLabData();
        addid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SoilTesting.this, TestReport.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateFade(SoilTesting.this);

            }
        });
        onClickAnyLab();
    }
    private void setLabData(){
        //toolbar
        LabList = new ArrayList<>();
        ProductsRecycler=findViewById(R.id.expert_list);
        adapter = new LabInfoAdapter(SoilTesting.this,LabList,listener);
        ProductsRecycler.setLayoutManager(new LinearLayoutManager(SoilTesting.this));
        ProductsRecycler.setAdapter(adapter);
        getLabData();

    }
    private void getLabData(){
        DatabaseReference root = FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference m = root.child("labinfo").getRef();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        final String Productid = dataSnapshot.getKey().toString();
                        final String ProductName = dataSnapshot.child("labname").getValue().toString();
                        final String ProductImage = dataSnapshot.child("labimage").getValue().toString();
                        final String ProductDetails = dataSnapshot.child("labdetails").getValue().toString();
                       // Toast.makeText(SoilTesting.this, ProductName, Toast.LENGTH_SHORT).show();
                        //check favorites
                        final String sellerid = dataSnapshot.child("sellerid").getValue().toString();
                        final String ProductState = dataSnapshot.child("labstate").getValue().toString();
                        final String ProductDistrict = dataSnapshot.child("labdistrict").getValue().toString();
                        DatabaseReference Root = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference x = Root.child("favourites").child(UserId).child(Productid);
                        ValueEventListener vvalueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    LabList.add(new Labdata(sellerid,ProductState,ProductDistrict,Productid,ProductImage,ProductName,"200",ProductDetails,true));
                                }
                                else{
                                    LabList.add(new Labdata(sellerid,ProductState,ProductDistrict,Productid,ProductImage,ProductName,"200",ProductDetails,false));
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
    private void onClickAnyLab(){
        listener = new LabInfoAdapter.LabClickListener() {
            @Override
            public void onClick(View view, int position) {
                Labdata labdata = LabList.get(position);

                Intent intent = new Intent(SoilTesting.this, BookSoilTesting.class);
                intent.putExtra("Lab Name",labdata.getLabName());
                intent.putExtra("Lab Price",labdata.getLabPrice());
                intent.putExtra("Lab Image",labdata.getLabImage());
                intent.putExtra("Lab Details",labdata.getTestdetails());
                intent.putExtra("Lab IsFavorite",String.valueOf(labdata.getIsFavorite()));
                intent.putExtra("sellerid",labdata.getSellerid());
                intent.putExtra("Lab Id",String.valueOf(labdata.getLabid()));
                intent.putExtra("Is Offered","no");
                startActivity(intent);
            }
        };
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(SoilTesting.this, MarketPlaceHome.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
}