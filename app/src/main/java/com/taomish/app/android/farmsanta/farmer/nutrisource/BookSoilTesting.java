package com.taomish.app.android.farmsanta.farmer.nutrisource;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.background.service.FarmsantaMessagingService;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BookSoilTesting extends AppCompatActivity implements Spinner.OnItemSelectedListener,View.OnClickListener{
    String UserId,bcountryname,bstatename,bdistrictname,bsubdistrictname;
    AppPrefs appPrefs;
    EditText farmer_name,mobile_no,crop_name,previous_crop,farm_size,target_yield,farmer_address,village,pincodeno;
    String farmername,cropname,mobileno,previouscrop,fieldsize,targetyield,address,villagename,pincode;
    Button submitbtn;
    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
    SearchableSpinner country,region,district,subdistrict,business_type;
    private TextView subcategory;
    ArrayAdapter businesscatadapter,businesstypeadapter;
    ArrayAdapter countryadapter,regionadapter,districtadapter,subdistrictadapter;
    private String[] countrylist = {"Cameroon","India","Kenya","Malawi","Mozambique","Tanzania","Togo","Zambia","Uganda"};
    private String[] statelist = {"Region"};
    private String[] districtlist = {"District"};
    private String[] subdistrictlist = {"Sub District"};
    String tocken,androidid="NA",userid;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    final int min = 10;
    final int max = 90;
    final int random = new Random().nextInt((max - min) + 1) + min;
    String labname,labprice,labimage,labdetails,labfav,ladid,isoffer,sellerid;
    CharSequence s,today,now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_soil_testing);
        Date d = new Date();
        s  = DateFormat.format("MMddyyhhmmss", d.getTime());
        today=DateFormat.format("dd-MM-yy", d.getTime());

        now=DateFormat.format("hh:mm:ss", d.getTime());
        labname = getIntent().getStringExtra("LabName");
        sellerid=getIntent().getStringExtra("sellerid");
        labprice = getIntent().getStringExtra("LabPrice");
        labimage = getIntent().getStringExtra("LabImage");
        labdetails = getIntent().getStringExtra("LabDetails");
        labfav = getIntent().getStringExtra("LabIsFavorite");
        ladid = getIntent().getStringExtra("LabId");
        isoffer = getIntent().getStringExtra("IsOffered");
      //  Toast.makeText(this, labname, Toast.LENGTH_SHORT).show();
        appPrefs=new AppPrefs(this);
        UserId=appPrefs.getPhoneNumber();
        farmername=appPrefs.getFirstName()+" "+appPrefs.getLastName();
        mobileno=appPrefs.getPhoneNumber();
        farmer_name=findViewById(R.id.farmer_name);
        mobile_no=findViewById(R.id.mobile_no);
        crop_name=findViewById(R.id.crop_name);
        previous_crop=findViewById(R.id.previous_crop);
        farm_size=findViewById(R.id.farm_size);
        target_yield=findViewById(R.id.target_yield);
        farmer_address=findViewById(R.id.farmer_address);
        submitbtn=findViewById(R.id.submitbtn);
        country=findViewById(R.id.country);
        region=findViewById(R.id.region);
        district=findViewById(R.id.district);
        subdistrict=findViewById(R.id.subdistrict);
        village=findViewById(R.id.village);
        pincodeno=findViewById(R.id.pincodeno);
        countryadapter = new ArrayAdapter(this,R.layout.simple_spinner_items,countrylist);
        regionadapter = new ArrayAdapter(this,R.layout.simple_spinner_items,statelist);
        districtadapter = new ArrayAdapter(this,R.layout.simple_spinner_items,districtlist);
        subdistrictadapter = new ArrayAdapter(this,R.layout.simple_spinner_items,subdistrictlist);
        country.setAdapter(countryadapter);
        region.setAdapter(regionadapter);
        district.setAdapter(districtadapter);
        subdistrict.setAdapter(subdistrictadapter);
        farmer_name.setText(farmername);
        mobile_no.setText(mobileno);
        mAwesomeValidation.addValidation(this, R.id.farmer_name, "^(?=\\s*\\S).*$", R.string.err_msg);
        mAwesomeValidation.addValidation(this, R.id.mobile_no, "^(?=\\s*\\S).*$", R.string.err_msg);
        mAwesomeValidation.addValidation(this, R.id.crop_name, "^(?=\\s*\\S).*$", R.string.err_msg);
        mAwesomeValidation.addValidation(this, R.id.farm_size, "^(?=\\s*\\S).*$", R.string.err_msg);
        mAwesomeValidation.addValidation(this, R.id.target_yield, "^(?=\\s*\\S).*$", R.string.err_msg);
        mAwesomeValidation.addValidation(this, R.id.farmer_address, "^(?=\\s*\\S).*$", R.string.err_msg);
        mAwesomeValidation.addValidation(this, R.id.pincodeno, "^(?=\\s*\\S).*$", R.string.err_msg);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAwesomeValidation.validate()) {
                    if(bstatename==null||bstatename.isEmpty()||bstatename.equalsIgnoreCase("region")) {
                        Toast.makeText(BookSoilTesting.this, "Please Select Region", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(BookSoilTesting.this, "Hello", Toast.LENGTH_SHORT).show();
                        cropname=crop_name.getText().toString();
                        previouscrop=previous_crop.getText().toString();
                        fieldsize=farm_size.getText().toString();;
                        targetyield=target_yield.getText().toString();;
                        address=farmer_address.getText().toString();
                        villagename=village.getText().toString();;
                        pincode=pincodeno.getText().toString();
                        farmername=farmer_name.getText().toString();
                        mDatabase= FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("soiltestbook");
                      tocken = FarmsantaMessagingService.getToken(BookSoilTesting.this);
                        androidid = Settings.Secure.getString(BookSoilTesting.this.getContentResolver(), Settings.Secure.ANDROID_ID);

                        Map hashMap = new HashMap();
                        hashMap.put("device_token", tocken);
                        hashMap.put("userid", userid);
                        hashMap.put("farmer_name", farmername);
                        hashMap.put("mobile_no", mobileno);
                        hashMap.put("crop_name", cropname);
                        hashMap.put("previous_crop", previouscrop);
                        hashMap.put("farm_size", fieldsize);
                        hashMap.put("target_yield", targetyield);
                        hashMap.put("farmer_address", address);
                        hashMap.put("country", bcountryname);
                        hashMap.put("region", bstatename);
                        hashMap.put("district", bdistrictname);
                        hashMap.put("subdistrict", bsubdistrictname);
                        hashMap.put("village", villagename);
                        hashMap.put("pincodeno", pincode);
                        hashMap.put("ladid", ladid);
                        hashMap.put("labname", labname);
                        hashMap.put("labdetails", labdetails);
                        hashMap.put("labimage", labimage);
                        hashMap.put("labfav", labfav);
                        hashMap.put("labprice", labprice);
                        hashMap.put("sampleid", String.valueOf(random)+s);
                        hashMap.put("sellerid", sellerid);
                        hashMap.put("document", "default");
                        hashMap.put("package","");
                        hashMap.put("status", "1");
                        hashMap.put("createdate", today.toString());
                        hashMap.put("time", now.toString());
                        hashMap.put("status","pending");

                        mDatabase.child(String.valueOf(random)+s).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task1) {
                                if (task1.isSuccessful()) {
                                    new FancyAlertDialog.Builder(BookSoilTesting.this)
                                            .setTitle("Your request successfully sent. We will response you within 24hours")
                                            .setBackgroundColor(Color.parseColor("#3aa651"))  //Don't pass R.color.colorvalue
                                            .setMessage("Do you want to request another Soil Testing?")
                                            .setNegativeBtnText("Visit Request")
                                            .setPositiveBtnBackground(Color.parseColor("#3aa651"))  //Don't pass R.color.colorvalue
                                            .setPositiveBtnText("New Request")
                                            .setNegativeBtnBackground(Color.parseColor("#FF9800"))  //Don't pass R.color.colorvalue
                                            .setAnimation(Animation.POP)
                                            .isCancellable(true)
                                            .setIcon(R.drawable.app_new_logo_white, Icon.Visible)
                                            .OnPositiveClicked(new FancyAlertDialogListener() {
                                                @Override
                                                public void OnClick() {
                                                    Intent i= new Intent(BookSoilTesting.this, SoilTesting.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(i);
                                                    Animatoo.animateSlideLeft(BookSoilTesting.this);
                                                }
                                            })
                                            .OnNegativeClicked(new FancyAlertDialogListener() {
                                                @Override
                                                public void OnClick() {
                                                    Intent i= new Intent(BookSoilTesting.this, TestReport.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(i);
                                                    Animatoo.animateSlideLeft(BookSoilTesting.this);
                                                }
                                            })
                                            .build();


                                } else {
                                    Toast.makeText(BookSoilTesting.this, "Something went wrong. Please try again!", Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                    }
                }
            }
        });
        country.setOnItemSelectedListener(this);
        region.setOnItemSelectedListener(this);
        district.setOnItemSelectedListener(this);
        subdistrict.setOnItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(BookSoilTesting.this, SoilTesting.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
         if(parent==country) {
            bcountryname = country.getSelectedItem().toString();
            statelist= new String[]{"Region","South Region", "Northwest Region", "Littoral", "Adamawa Region", "South West Region", "North Region", "West Region", "East Region", "Far North Region","Central"};
            regionadapter = new ArrayAdapter(this,R.layout.simple_spinner_items,statelist);
            region.setAdapter(regionadapter);
        }
        else if(parent==region) {
            if (position == 0) {

            } else {
                if (position != 0) {
                    bstatename = region.getSelectedItem().toString();
                    districtlist = new String[]{"District","Dja-et-Lobo","Mvila","Ocean","Vallee-du-Ntem"};
                    districtadapter= new ArrayAdapter(this,R.layout.simple_spinner_items,districtlist);
                    district.setAdapter(districtadapter);
                }
            }
        }
        else if(parent==district) {
            if (position == 0) {

            } else {
                if (position != 0) {
                    bdistrictname = district.getSelectedItem().toString();
                    subdistrictlist =  new String[]{"Sub District","Bengbis","Djoum","Meyomessala","Meyomessi","Mintom","Oveng","Sangmelima (urban)","Sangmelima(rural)","Zoetele"};
                    subdistrictadapter= new ArrayAdapter(this,R.layout.simple_spinner_items,subdistrictlist);
                    subdistrict.setAdapter(subdistrictadapter);
                }
            }
        }
        else if(parent==subdistrict) {
            if (position == 0) {

            } else {
                if (position != 0) {
                    bsubdistrictname = subdistrict.getSelectedItem().toString();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}