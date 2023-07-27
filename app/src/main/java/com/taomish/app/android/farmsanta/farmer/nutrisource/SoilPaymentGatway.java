package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.razorpay.PaymentResultListener;
import com.taomish.app.android.farmsanta.farmer.R;

import org.json.JSONObject;

public class SoilPaymentGatway extends AppCompatActivity  implements PaymentResultListener {
    TextView paynow;
    LinearLayout viewitems,viewstore,viewcontact;
    ExpandableWeightLayout expandorder,expandaddress,expandcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_payment_gatway);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.soilcolor));
        paynow=findViewById(R.id.paynow);
        viewitems=findViewById(R.id.viewitems);
        viewstore=findViewById(R.id.viewstore);
        viewcontact=findViewById(R.id.viewcontact);
        expandorder=findViewById(R.id.expandorder);
        expandorder.collapse();
        expandaddress=findViewById(R.id.expandaddress);
        expandaddress.collapse();
        expandcontact=findViewById(R.id.expandcontact);
        expandcontact.collapse();
        viewitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandorder.toggle();
            }
        });
        viewstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandaddress.toggle();
            }
        });
        viewcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandcontact.toggle();
            }
        });
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Amit","farmsantait@gmail.com","+919876543210","80","USD");
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(SoilPaymentGatway.this, SoilBookingDetails.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
    public void startPayment(String Name,String email,String contact,String amount,String currency) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final com.razorpay.Checkout co = new com.razorpay.Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", Name);
            options.put("description", "Farm Santa Market Place");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://i.ibb.co/X3rxdc1/logo.png");
            options.put("currency", currency);
            options.put("amount", String.valueOf(100*Integer.parseInt(amount)));
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", contact);

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
            Intent i=new Intent(SoilPaymentGatway.this, PaidSuccess.class);
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