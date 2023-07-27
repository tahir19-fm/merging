package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.taomish.app.android.farmsanta.farmer.R;

public class PaidSuccess extends AppCompatActivity {
    Thread SplashThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_success);

        setSplashy();
    }
    public void  setSplashy()
    {

        // Listener for completion of splash screen
        SplashThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                int waited = 0;
                while (waited < 3000) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }
                PaidSuccess.this.finish();

                Intent i = new Intent(PaidSuccess.this, SoilBookingConfirm.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        };
        SplashThread.start();
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(PaidSuccess.this, SoilBookingDetails.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
}