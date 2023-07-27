package com.taomish.app.android.farmsanta.farmer.activities;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.maps.android.SphericalUtil;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.adapters.LocationNameArrayAdapter;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity;
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants;
import com.taomish.app.android.farmsanta.farmer.controller.NavigationController;
import com.taomish.app.android.farmsanta.farmer.databinding.ActivityMapSelectBinding;
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Coordinate;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FarmerMapSelectActivity extends FarmSantaBaseActivity implements
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public String farmName;
    public String farmLocationName;

    public Farmer farmer;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 121;

    private static int SELECTED_FARM_INDEX = -1;
    private static final int DEFAULT_ZOOM = 20;

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private AutoCompleteTextView autoCompleteTextViewPlaces;

    private ActivityMapSelectBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    private SupportMapFragment mapFragment;

    private LocationNameArrayAdapter placesAdapter;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private MarkerOptions markerOptions;
    private Marker marker;
    private final LatLng mDefaultLocation = new LatLng(-01.08, 34.12);
    private Location mLastKnownLocation;
    private ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>();
    private Polygon mPolygon;
    private Polyline mPolyline;

    private boolean mLocationPermissionGranted;
    private boolean isMarkingFarmBoundary = false;
    private boolean isBeforeDraw = true;
    private boolean isEditingFarm = false;

    private Double landArea;

    @Override
    public void init() {
        SELECTED_FARM_INDEX = getIntent().getIntExtra(
                AppConstants.DataTransferConstants.KEY_FARM_INDEX, -1);
    }

    @SuppressLint("InflateParams")
    @Override
    public View initContentView() {
        binding = ActivityMapSelectBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initUIElements() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_supportMap_map);

        autoCompleteTextViewPlaces = findViewById(R.id.map_autocomplete_places);

        toolbar = findViewById(R.id.toolbar_mapSelect_title);
        appBarLayout = binding.appbarMapSelectTitle;
        toolbar = binding.toolbarMapSelectTitle;

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void initListeners() {
        autoCompleteTextViewPlaces.setThreshold(2);
        toolbar.setNavigationOnClickListener(v -> Log.d(AppConstants.TAG, "Clicked navigation icon back"));
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> onDestinationChange(destination));
    }

    @Override
    public void initData() {
        farmer = DataHolder.getInstance().getSelectedFarmer();

        setNavGraph();

        NavigationUI.setupWithNavController(toolbar, navController);
        NavigationController.getInstance(FarmerMapSelectActivity.this);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder().setFallbackOnNavigateUpListener(() -> {
            finish();
            return true;
        }).build();
        NavigationUI.setupActionBarWithNavController(this, getNavController(), appBarConfiguration);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FarmerMapSelectActivity.this);
        loadMap();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(AppConstants.TAG, "Navigate up" + navController.getCurrentDestination());
        return NavigationUI.navigateUp(navController, appBarConfiguration);
        //return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                getDeviceLocation();
            }
        }
    }

    @Override
    public void onCameraIdle() {
        Log.d(AppConstants.TAG, "Camera is idle");
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (isMarkingFarmBoundary || isBeforeDraw) {
            addMarker(latLng);
        } else {
            markerOptions = new MarkerOptions().position(latLng).title("Location");
            mMap.clear();
            MarkerOptions mo = drawPolygon();
            LatLng center = getCenter();
            if (mo != null && center != null) {
                getLocationNameFromLatLng(center, mo);

                Marker marker = mMap.addMarker(mo);
                if (marker != null)
                    marker.showInfoWindow();
            }
        }
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        Log.d(AppConstants.TAG, "Long Clicked in map");
    }

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        Log.d(AppConstants.TAG, "Map is ready");
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMarkerClickListener(FarmerMapSelectActivity.this);

        mMap = googleMap;
        addMapClickListener();

        // Add a marker in Sydney and move the camera
        LatLng africa = new LatLng(-01.08, 34.12);
        markerOptions = new MarkerOptions().position(africa);

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(africa));

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Prompt the user for permission.
        getLocationPermission();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return true;
    }

    public NavController getNavController() {
        return navController;
    }

    public void updateMap(String from, Object... data) {
        switch (from) {
            case AppConstants.DataTransferConstants.KEY_MAP_CURRENT:
                mLastKnownLocation = (Location) data[0];
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                if ((boolean) data[1]) {
                    isMarkingFarmBoundary = true;
                    isBeforeDraw = true;
                    isEditingFarm = true;
                }
                break;
            case AppConstants.DataTransferConstants.KEY_MAP_START_DRAW:
                mMap.clear();
                isMarkingFarmBoundary = true;
                isBeforeDraw = false;
                break;
            case AppConstants.DataTransferConstants.KEY_MAP_COMPLETE_DRAW:
                mMap.clear();
                MarkerOptions mo = drawPolygon();
                LatLng center = getCenter();
                if (mo != null && center != null) {
                    farmLocationName = getLocationNameFromLatLng(center, mo);

                    Marker marker = mMap.addMarker(mo);
                    if (marker != null)
                        marker.showInfoWindow();
                }
                break;
            case AppConstants.DataTransferConstants.KEY_MAP_LAND_SAVED:
                SELECTED_FARM_INDEX = (int) data[0];
                break;
            case AppConstants.DataTransferConstants.KEY_FARM_NAME:
                farmName = (String) data[0];
                break;
            case AppConstants.DataTransferConstants.KEY_MAP_CANCEL_DRAW:
                mMap.clear();
                markerOptionsArrayList.clear();
            case AppConstants.DataTransferConstants.KEY_MAP_SAVE_PLOT:
            case AppConstants.DataTransferConstants.KEY_MAP_SKIP_DRAW:
                mMap.clear();
                break;
            case AppConstants.DataTransferConstants.KEY_MAP_EDIT_BOUNDARY:
                isMarkingFarmBoundary = true;
                isBeforeDraw = false;
                isEditingFarm = true;
                mMap.clear();
                markerOptionsArrayList.clear();
                break;
            case AppConstants.DataTransferConstants.KEY_MAP_EDIT_LOC:
                isMarkingFarmBoundary = false;
                isBeforeDraw = true;
                isEditingFarm = true;
                mMap.clear();
                markerOptionsArrayList.clear();
                break;
            default:
                break;
        }
    }

    public int getSelectedLandIndex() {
        return SELECTED_FARM_INDEX;
    }

    public ArrayList<MarkerOptions> getMarkerOptionsArrayList() {
        return markerOptionsArrayList;
    }

    public Location getLastKnownLocation() {
        return mLastKnownLocation;
    }

    @SuppressLint("NonConstantResourceId")
    private void onDestinationChange(NavDestination destination) {
        Log.d(AppConstants.TAG, "Destination is ==> " + destination.getId());
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                @SuppressLint("MissingPermission")
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(FarmerMapSelectActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();
                        updateLocation();
                    } else {
                        Log.d(AppConstants.TAG, "Current location is null. Using defaults.");
                        Log.e(AppConstants.TAG, "Exception: %s", task.getException());

                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                    }

                    //getCurrentPlaceLikelihoods();
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void setNavGraph() {
        /*NavInflater inflater = navController.getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.map_navigation);

        if (SELECTED_FARM_INDEX != -1) {
            graph.setStartDestination(R.id.navigation_land_options_add);
            isBeforeDraw = false;
        }

        navController.setGraph(graph);*/
    }

    private void loadMap() {
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission() {
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(AppConstants.TAG, "Permission granted");
            mLocationPermissionGranted = true;

            getDeviceLocation();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private void addMapClickListener() {
        if (mMap != null) {
            Log.d(AppConstants.TAG, "Map click added");
            mMap.setOnMapClickListener(FarmerMapSelectActivity.this);
            mMap.setOnMapLongClickListener(FarmerMapSelectActivity.this);
            mMap.setOnCameraIdleListener(FarmerMapSelectActivity.this);
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(@NonNull Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(@NonNull Marker marker) {

                    LinearLayout info = new LinearLayout(FarmerMapSelectActivity.this);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(FarmerMapSelectActivity.this);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(FarmerMapSelectActivity.this);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });
        }
    }

    private void updateLocation() {
        if (mLastKnownLocation != null) {
            Log.d(AppConstants.TAG, "Latitude: " + mLastKnownLocation.getLatitude());
            Log.d(AppConstants.TAG, "Longitude: " + mLastKnownLocation.getLongitude());

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
            LatLng latLng = new LatLng(
                    mLastKnownLocation.getLatitude(),
                    mLastKnownLocation.getLongitude());
            if (markerOptions == null) {
                markerOptions = new MarkerOptions();
            }
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_white))
                    .position(latLng).snippet("User position");
            mMap.clear();

            getLocationNameFromLatLng(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()),
                    markerOptions);

            marker = mMap.addMarker(markerOptions);
            if (marker != null) {
                marker.showInfoWindow();
            }
            setAutoCompleteAdapter();
            drawPolygonIfEdit();

        } else {
            Log.d(AppConstants.TAG, "Current location is null even in successful task. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
        }
    }

    private void setAutoCompleteAdapter() {
        if (placesAdapter == null) {
            placesAdapter = new LocationNameArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, null);
            autoCompleteTextViewPlaces.setAdapter(placesAdapter);
        }
    }

    private void drawPolygonIfEdit() {
        if (farmer == null)
            return;

        if (SELECTED_FARM_INDEX != -1) {
            //Is edit
            Land land = farmer.getLands().get(SELECTED_FARM_INDEX);
            List<Coordinate> coordinates = land.getCoordinates();
            if (coordinates == null)
                return;
            Collections.sort(coordinates);
            land.setCoordinates(coordinates);
            if (markerOptionsArrayList == null)
                markerOptionsArrayList = new ArrayList<>();

            markerOptionsArrayList.clear();

            for (Coordinate coordinate : coordinates) {
                MarkerOptions options = new MarkerOptions();
                LatLng latLng = new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
                options.position(latLng);

                markerOptionsArrayList.add(options);
            }

            if (markerOptionsArrayList.size() > 2) {
                LatLng center = getCenter();
                if (center != null)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, DEFAULT_ZOOM));

                MarkerOptions mo = drawPolygon();
                if (mo != null && center != null) {
                    mo.title(land.getLandName());
                    getLocationNameFromLatLng(center, mo);

                    marker = mMap.addMarker(mo);
                    if (marker != null) {
                        marker.showInfoWindow();
                    }
                }
            }
        }
    }

    private void addMarker(LatLng latLng) {
        if (isBeforeDraw) {
            MarkerOptions mo = new MarkerOptions().title("Marker").position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.ic_location_white))
                    .snippet("");

            mMap.clear();
            getLocationNameFromLatLng(mo.getPosition(), mo);
            marker = mMap.addMarker(mo);
            if (marker != null) {
                marker.showInfoWindow();
            }
        } else if (isEditingFarm) {
            MarkerOptions mo = new MarkerOptions().title("Marker").position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.ic_location_white))
                    .snippet("");

            mMap.clear();
            getLocationNameFromLatLng(mo.getPosition(), mo);
            marker = mMap.addMarker(mo);
            if (marker != null) {
                marker.showInfoWindow();
            }
            isEditingFarm = false;
            Toast.makeText(this, "You can start drawing now", Toast.LENGTH_SHORT).show();
        } else {
            MarkerOptions mo = new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.ic_map_select_pin))
                    .snippet("");

            mMap.addMarker(mo);
            addMarkerToList(mo);
            addPolyLine();
        }
    }

    private void addPolyLine() {
        if (markerOptionsArrayList == null || markerOptionsArrayList.size() < 2) {
            return;
        }

        PolylineOptions options = new PolylineOptions()
                .width(5).color(Color.WHITE)
                .geodesic(true);

        for (MarkerOptions markerOptions : markerOptionsArrayList) {
            LatLng point = markerOptions.getPosition();
            options.add(point);
        }

        if (mPolyline != null)
            mPolyline.remove();

        mPolyline = mMap.addPolyline(options);
    }

    @SuppressLint("SetTextI18n")
    private MarkerOptions drawPolygon() {
        PolygonOptions options = new PolygonOptions().strokeColor(Color.GREEN);
        ArrayList<LatLng> latLngArrayList = new ArrayList<>();
        options.clickable(true);

        mMap.clear();

        if (markerOptionsArrayList != null && markerOptionsArrayList.size() > 2) {
            for (MarkerOptions mo : markerOptionsArrayList) {
                options.add(mo.getPosition());
                latLngArrayList.add(mo.getPosition());
            }

            if (mPolygon != null) {
                mPolygon.remove();
            }
            if (mPolyline != null) {
                mPolyline.remove();
            }
            mPolygon = mMap.addPolygon(options);
            // Store a data object with the polygon, used here to indicate an arbitrary type.
            mPolygon.setTag("MyPlot");
            landArea = SphericalUtil.computeArea(latLngArrayList);

            Log.d(AppConstants.TAG, "Area = " + landArea);

            //buttonCompleteMapDraw.setText("Save");
            isMarkingFarmBoundary = false;

            LatLng center = getCenter();
            if (center == null)
                center = mDefaultLocation;

            return new MarkerOptions().position(center)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.ic_location_white))
                    .snippet("");
        }

        return null;
    }

    private void addMarkerToList(MarkerOptions markerOptions) {
        if (markerOptionsArrayList == null)
            markerOptionsArrayList = new ArrayList<>();

        markerOptionsArrayList.add(markerOptions);

        if (markerOptionsArrayList.size() > 2) {
            //buttonCompleteMapDraw.setEnabled(true);

        }
    }

    private void removeMarker(Marker marker) {
        LatLng removingLatLng = marker.getPosition();
        for (MarkerOptions markerOptions : markerOptionsArrayList) {
            if (markerOptions.getPosition().longitude == removingLatLng.longitude
                    && markerOptions.getPosition().latitude == removingLatLng.latitude) {
                markerOptionsArrayList.remove(markerOptions);
                break;
            }
        }

        mMap.clear();

        for (MarkerOptions markerOptions : markerOptionsArrayList) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_select_pin));
            mMap.addMarker(markerOptions);
        }

        // buttonCompleteMapDraw.setText(R.string.complete);
    }

    private LatLng getCenter() {
        LatLng centerLatLng;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (markerOptionsArrayList.size() < 2)
            return null;
        for (int i = 0; i < markerOptionsArrayList.size(); i++) {
            builder.include(markerOptionsArrayList.get(i).getPosition());
        }
        LatLngBounds bounds = builder.build();
        centerLatLng = bounds.getCenter();

        return centerLatLng;
    }

    private String getLocationNameFromLatLng(LatLng center, MarkerOptions mo) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String locationName = "";
        try {
            List<Address> fromLocation = geocoder.getFromLocation(center.latitude, center.longitude, 1);
            if (fromLocation != null && fromLocation.size() > 0) {
                locationName = fromLocation.get(0).getLocality() + ", " + fromLocation.get(0).getSubAdminArea();
                mo.snippet(locationName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationName;
    }
}