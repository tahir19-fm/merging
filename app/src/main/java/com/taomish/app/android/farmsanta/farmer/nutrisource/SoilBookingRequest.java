package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.taomish.app.android.farmsanta.farmer.R;

public class SoilBookingRequest extends AppCompatActivity {
Button vieworder,backtohome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_booking_request);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.soilcolor));
        vieworder=findViewById(R.id.vieworder);
        backtohome=findViewById(R.id.backtohome);
        vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SoilBookingRequest.this, SoilBookingDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(SoilBookingRequest.this);
            }
        });
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SoilBookingRequest.this, MarketPlaceHome.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(SoilBookingRequest.this);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(SoilBookingRequest.this, SoilCartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(SoilBookingRequest.this);
    }
}