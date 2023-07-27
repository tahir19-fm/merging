package com.taomish.app.android.farmsanta.farmer.nutrisource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Cart_Data;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.CartProductAdapter;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.OrderProductAdapter;
import com.taomish.app.android.farmsanta.farmer.nutrifragments.BuyFragment;
import com.taomish.app.android.farmsanta.farmer.utils.DirectionsJSONParser;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.LimitExceededListener;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDetails extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;
    NestedScrollView scrollview;
    ArrayList<LatLng> mMarkerPoints;
    SupportMapFragment mapFragment;
    Button getdirection;
    private RecyclerView cart_recycle;
    LinearLayout callseller,whatsappseller;
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        scrollview=findViewById(R.id.scrollview);
        getdirection=findViewById(R.id.getdirection);
        cart_recycle=findViewById(R.id.cart_recycle);
        callseller=findViewById(R.id.callseller);
        whatsappseller=findViewById(R.id.whatsappseller);
        backbtn=findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OrderDetails.this, MarketPlaceHome.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Animatoo.animateSlideLeft(OrderDetails.this);
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapViewFragment1);
        mapFragment.getMapAsync(this);
        ImageView transparent = (ImageView)findViewById(R.id.imagetrans);
        transparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scrollview.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scrollview.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scrollview.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
        mOrigin = new LatLng(22.466900, 88.360168);
        mDestination = new LatLng(22.5764753, 88.4306861);
        mMarkerPoints = new ArrayList<>();
        mMarkerPoints.add(mOrigin);
        mMarkerPoints.add(mDestination);
        getdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+mDestination.latitude+","+mDestination.longitude));
                startActivity(intent);
            }
        });
        callseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallNow("+918981932961");
            }
        });
        whatsappseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAppMsg("+918981932961");
            }
        });
        setCartItems();
    }
    public void sendAppMsg(String mobile) {
        //Toast.makeText(TeacherDetails.this, mobile, Toast.LENGTH_SHORT).show();
        try {
            Intent sendMsg = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" +mobile+ "&text=" + URLEncoder.encode("Hello!", "UTF-8");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
           /*
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            PackageManager pm = getPackageManager();
            List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
            List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
            for (int i = 0; i < resInfo.size(); i++) {
                Drawable icon = null;
                // Extract the label, append it, and repackage it in a LabeledIntent
                ResolveInfo ri = resInfo.get(i);
                String packageName = ri.activityInfo.packageName;
                if (packageName.equalsIgnoreCase("com.whatsapp")) {
                    sendMsg.setPackage("com.whatsapp");
                } else if (packageName.equalsIgnoreCase("com.whatsapp.w4b")) {
                    sendIntent.setPackage("com.whatsapp.w4b");
                }
                sendMsg.setData(Uri.parse(url));
                if (sendMsg.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendMsg);
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(OrderDetails.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }
    public void CallNow(String phoneNo) {
        try {

            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:"+phoneNo));
            startActivity(callIntent);
        } catch (Exception ex) {
            // Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    private void setCartItems(){
        ArrayList<Cart_Data> ReviewList= new ArrayList<>();
        ReviewList.add(new Cart_Data("Nutrisource Calcium Ammonium Nitrate",10,"$18",1,R.drawable.urea));
        ReviewList.add(new Cart_Data("Nutrisource Calcium Ammonium Nitrate",10,"$18",1,R.drawable.urea));
        ReviewList.add(new Cart_Data("Nutrisource Calcium Ammonium Nitrate",10,"$18",1,R.drawable.urea));
        OrderProductAdapter orderAdapter = new OrderProductAdapter(OrderDetails.this, ReviewList);

        //cart_recycle.setNestedScrollingEnabled(false);
        cart_recycle.setLayoutManager(new GridLayoutManager(OrderDetails.this,1, GridLayoutManager.VERTICAL, false));
        cart_recycle.setAdapter(orderAdapter);
        cart_recycle.setHasFixedSize(true);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            mMap.clear();
        }
        if (ActivityCompat.checkSelfPermission(OrderDetails.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(OrderDetails.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        MarkerOptions optionsstart = new MarkerOptions();
        optionsstart.position(mOrigin);
        optionsstart.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        optionsstart.title("Your Location");
        MarkerOptions optionsend = new MarkerOptions();
        optionsend.position(mDestination);
        optionsend.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        optionsend.title("Shop Location");

        mMap.addMarker(optionsstart);
        mMap.addMarker(optionsend);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDestination, 10));
        mMap.setPadding(20,20,20,20);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        if (mMap != null) {
            CameraUpdate location1 = CameraUpdateFactory.newLatLngZoom(mOrigin, 10);
            CameraUpdate location2 = CameraUpdateFactory.newLatLngZoom(mDestination, 10);
            CameraAnimationManager mAnimationManager = new CameraAnimationManager(mMap);
            mAnimationManager.addAnimation(new CameraAnimationManager.CameraAnimation(location1, 3000) );
            mAnimationManager.addAnimation(new CameraAnimationManager.CameraAnimation(location2, 3000) );
            mAnimationManager.startAnimation();
        }
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        drawRoute();
     /*   mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Already two locations
                if(mMarkerPoints.size()>1){
                    mMarkerPoints.clear();
                    mMap.clear();
                }

                // Adding new item to the ArrayList
                mMarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.

                if(mMarkerPoints.size()==1){
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else if(mMarkerPoints.size()==2){
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }

                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if(mMarkerPoints.size() >= 2){
                    mOrigin = mMarkerPoints.get(0);
                    mDestination = mMarkerPoints.get(1);
                    drawRoute();
                }

            }
        });
*/
    }
    private void drawRoute(){

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);

     DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);

            }else
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
        }
    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(OrderDetails.this, MarketPlaceHome.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.animateSlideLeft(this);
    }
}