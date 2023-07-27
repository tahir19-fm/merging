package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.razorpay.PaymentResultListener;
import com.taomish.app.android.farmsanta.farmer.R;

import org.json.JSONObject;

public class Checkout extends AppCompatActivity implements PaymentResultListener {
    LinearLayout viewitems,viewstore,viewcontact;
    ExpandableWeightLayout expandorder,expandaddress,expandcontact;
    TextView paynow;
    ImageView backbtn;
    RadioGroup paymentGroup;
    RadioButton mpasa,upiid,card,paytm,phonepe,gpay,netbanking,cod;
    int paymentmethod=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        paynow=findViewById(R.id.paynow);
        viewitems=findViewById(R.id.viewitems);
        viewstore=findViewById(R.id.viewstore);
        viewcontact=findViewById(R.id.viewcontact);
        expandorder=findViewById(R.id.expandorder);
        backbtn=findViewById(R.id.backbtn);
        paymentGroup=findViewById(R.id.paymentGroup);
        mpasa=findViewById(R.id.mpasa);
        upiid=findViewById(R.id.upiid);
        card=findViewById(R.id.card);
        paytm=findViewById(R.id.paytm);
        phonepe=findViewById(R.id.phonepe);
        gpay=findViewById(R.id.gpay);
        netbanking=findViewById(R.id.netbanking);
        cod=findViewById(R.id.cod);
        addListenerOnButton();
        expandorder.collapse();
        expandaddress=findViewById(R.id.expandaddress);
        expandaddress.collapse();
        expandcontact=findViewById(R.id.expandcontact);
        expandcontact.collapse();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Checkout.this, MyCartActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(Checkout.this);
            }
        });
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
                if(paymentmethod==1) {
                    startPayment("Amit", "farmsantait@gmail.com", "+919876543210", "80", "USD");
                } else if (paymentmethod==2) {
                    Intent i=new Intent(Checkout.this, OrderRequestSent.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(Checkout.this, "Please Select Payment Method", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void addListenerOnButton() {
        paymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.cod) {
                  paymentmethod=2;
                }
                else{
                   paymentmethod=1;
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(Checkout.this, MyCartActivity.class);
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
            Intent i=new Intent(Checkout.this, ConfirmedOrder.class);
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