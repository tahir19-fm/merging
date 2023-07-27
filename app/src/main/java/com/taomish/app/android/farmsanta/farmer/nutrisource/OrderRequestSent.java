package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.taomish.app.android.farmsanta.farmer.R;

public class OrderRequestSent extends AppCompatActivity {
    Button backtohome,vieworder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request_sent);
        backtohome=findViewById(R.id.backtohome);
        vieworder=findViewById(R.id.vieworder);
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OrderRequestSent.this, MarketPlaceHome.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(OrderRequestSent.this);
            }
        });
        vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OrderRequestSent.this, OrderDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(OrderRequestSent.this);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(OrderRequestSent.this, MarketPlaceHome.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
}