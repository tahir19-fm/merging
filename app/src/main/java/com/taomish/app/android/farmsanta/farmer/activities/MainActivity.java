package com.taomish.app.android.farmsanta.farmer.activities;

import static com.taomish.app.android.farmsanta.farmer.constants.ApiConstants.Language.EN;
import static com.taomish.app.android.farmsanta.farmer.constants.ApiConstants.Language.FR;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.taomish.app.android.farmsanta.farmer.BuildConfig;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.adapters.NavigationMenu;
import com.taomish.app.android.farmsanta.farmer.adapters.SideMenuAdapter;
import com.taomish.app.android.farmsanta.farmer.adapters.SideMenuItem;
import com.taomish.app.android.farmsanta.farmer.background.GetCropListTask;
import com.taomish.app.android.farmsanta.farmer.background.GetCurrentFarmerTask;
import com.taomish.app.android.farmsanta.farmer.background.GetPopListTask;
import com.taomish.app.android.farmsanta.farmer.background.GetRegionsTask;
import com.taomish.app.android.farmsanta.farmer.background.GetTerritoryTask;
import com.taomish.app.android.farmsanta.farmer.background.db.ClearDBTask;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity;
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants;
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants;
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants;
import com.taomish.app.android.farmsanta.farmer.controller.NavigationController;
import com.taomish.app.android.farmsanta.farmer.databinding.ActivityMainBinding;
import com.taomish.app.android.farmsanta.farmer.fragments.WebViewFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.dialog.ConfirmLogoutDialog;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnDialogClickListener;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory;
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto;
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel;
import com.taomish.app.android.farmsanta.farmer.utils.ExtensionsKt;
import com.taomish.app.android.farmsanta.farmer.utils.Share;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


@SuppressWarnings("ALL")
public class MainActivity extends FarmSantaBaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private AppPrefs appPrefs;

    private static boolean isHomeNavGraphSet = false;
    private HomeViewModel homeViewModel;

    private SlidingRootNav slidingRootNav;
    private SideMenuAdapter sideMenuAdapter;


    @Override
    public void init() {
        appPrefs = new AppPrefs(this);
        String languageId = appPrefs.getLanguageId();
        if (languageId != null) {
            String country = Locale.getDefault().getCountry();
            String language = (languageId.equals("1") ? EN : FR) + "-" + country;
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language));
        }
        requestPermission();
        isHomeNavGraphSet = false;
        MapsInitializer.initialize(getApplicationContext());
    }

    @Override
    public View initContentView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void initUIElements() {
        bottomNavigationView = binding.bottomNavView;
        appBarLayout = binding.appbar;
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        fetchTerritory();
        fetchRegionList();
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(220)
                .withRootViewScale(0.82f)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withGravity(SlideGravity.LEFT)
                .withContentClickableWhenMenuOpened(false)
                .withMenuLayout(R.layout.sliding_menu_layout)
                .inject();

        RecyclerView menu = findViewById(R.id.rv_menu);
        sideMenuAdapter = new SideMenuAdapter();
        sideMenuAdapter.setMenuItems(getMenuItems());
        sideMenuAdapter.setOnItemClick(sideMenuItem -> {
            if (sideMenuItem.getAction() != null) {
                performAction(sideMenuItem.getAction());
            }
            return null;
        });
        menu.setAdapter(sideMenuAdapter);
        TextView versionText = slidingRootNav.getLayout().findViewById(R.id.tv_version);
        versionText.setText(String.format("v %s", BuildConfig.VERSION_NAME));
        setHeaderValue();
    }

    private void performAction(Integer action) {
        switch (action) {
            case NavigationMenu.HOME:
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                break;
            case NavigationMenu.ABOUT:
                openAboutus();
                break;
            case NavigationMenu.LOGOUT:
                confirmLogout();
                break;
            case NavigationMenu.MY_QUERIES:
                bottomNavigationView.setSelectedItemId(R.id.navigation_list_farm_scouting_home);
                break;
            case NavigationMenu.POP:
                bottomNavigationView.setSelectedItemId(R.id.navigation_pop);
                break;
            case NavigationMenu.FARM_TALK:
                bottomNavigationView.setSelectedItemId(R.id.navigation_social_wall);
                break;
            case NavigationMenu.CROP_CALENDAR:
                openCropCalendar();
                break;
            case NavigationMenu.MARKET_ANALYSIS:
                oepnMarketAnalysis();
                break;
            case NavigationMenu.ADVISORY:
                openAdvisory();
                break;
            case NavigationMenu.FERTILIZER_CALCULATOR:
                openFertilizerCalculator();
                break;
            case NavigationMenu.NUTRI_SOURCE:
                openNutrisource();
                break;
            case NavigationMenu.INVITE:
                invite();
                break;
            case NavigationMenu.LANGUAGE:
                openLanguagesBottomSheet();
                break;
        }
        slidingRootNav.closeMenu();
    }


    private List<SideMenuItem> getMenuItems() {
        ArrayList<SideMenuItem> menuItems = new ArrayList<>();
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_home_white,
                        getString(R.string.home),
                        NavigationMenu.HOME
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_my_queries_1,
                        getString(R.string.my_queries),
                        NavigationMenu.MY_QUERIES
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_farm_talk_white,
                        getString(R.string.farm_talk),
                        NavigationMenu.FARM_TALK
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_crop_calendar_menu,
                        getString(R.string.crop_calendar),
                        NavigationMenu.CROP_CALENDAR
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_advisory_menu,
                        getString(R.string.advisory),
                        NavigationMenu.ADVISORY
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_pop_white_new,
                        getString(R.string.pop),
                        NavigationMenu.POP
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_market_analysis_new,
                        getString(R.string.market_analysis),
                        NavigationMenu.MARKET_ANALYSIS
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.menu_fertilizer_calculator,
                        getString(R.string.fertilizer_calculator),
                        NavigationMenu.FERTILIZER_CALCULATOR
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.menu_nutrisource,
                        getString(R.string.nutri_source_catalogue),
                        NavigationMenu.NUTRI_SOURCE
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_invite,
                        getString(R.string.invite),
                        NavigationMenu.INVITE
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.menu_language,
                        getString(R.string.language),
                        NavigationMenu.LANGUAGE
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_invite,
                        getString(R.string.invite),
                        NavigationMenu.INVITE
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_about_us,
                        getString(R.string.about),
                        NavigationMenu.ABOUT
                )
        );
        menuItems.add(
                new SideMenuItem(
                        R.drawable.ic_logout_menu,
                        getString(R.string.log_out),
                        NavigationMenu.LOGOUT
                )
        );
        return menuItems;
    }

    @Override
    public void initListeners() {
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_pop, R.id.action_scouting_image_list,
                R.id.navigation_social_wall, R.id.navigation_profile)
                .build();
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> onDestinationChange(destination));
    }

    @Override
    public void initData() {
        setNavGraph();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        handleNotification();
        getCurrentFarmer();
        fetchCrops();
        fetchPopList();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("skt", "Started");
        setNavGraph();
    }

    @Override
    protected void onResume() {
        NavDestination destination = Objects.requireNonNull(navController.getCurrentBackStackEntry()).getDestination();
        if (destination.getId() == R.id.navigation_landing ||
                destination.getId() == R.id.navigation_onboard ||
                destination.getId() == R.id.navigation_login ||
                destination.getId() == R.id.signUpFragment ||
                destination.getId() == R.id.selectLanguageFragment) {
            appBarLayout.setVisibility(View.GONE);
        }
        Uri uri = getIntent().getData();
        if (uri != null) {
            if (!appPrefs.isUserProfileCompleted()) {
                navController.navigate(R.id.navigation_login);
            } else {
                navController.navigate(R.id.viewPopFragment);
            }
        }
        super.onResume();
    }

    @SuppressLint("Res1trictedApi")
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

    @Override
    public void onBackPressed() {
        if (slidingRootNav.isMenuOpened()) {
            slidingRootNav.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                openHomeFragment();
                break;
            case R.id.action_scouting_image_list:
                openScout();
                break;
            case R.id.navigation_social_wall:
                openFarmTalkFragment();
                break;
            case R.id.navigation_pop:
                openPopFragment();
                break;
            case R.id.navigation_profile:
                openProfileFragment();
                break;
        }
        return true;
    }

    private void openScout() {
        navController.navigate(R.id.action_scouting_image_list);
    }

    @Override
    public void doTheseOnDestroy() {
        isHomeNavGraphSet = false;
        super.doTheseOnDestroy();
    }

    public NavController getNavController() {
        return navController;
    }

    @SuppressLint("NonConstantResourceId")
    private void onDestinationChange(NavDestination destination) {
        switch (destination.getId()) {
            case R.id.navigation_profile:
                sideMenuAdapter.setSelectedPosition(NavigationMenu.NONE);
                handleBottomNavigation();
                appBarLayout.setVisibility(View.GONE);
                break;
            case R.id.navigation_pop:
                sideMenuAdapter.setSelectedPosition(NavigationMenu.POP);
                handleBottomNavigation();
                appBarLayout.setVisibility(View.GONE);
                break;
            case R.id.navigation_social_wall:
                sideMenuAdapter.setSelectedPosition(NavigationMenu.FARM_TALK);
                handleBottomNavigation();
                appBarLayout.setVisibility(View.GONE);
                break;
            case R.id.navigation_home:
                sideMenuAdapter.setSelectedPosition(NavigationMenu.HOME);
                handleBottomNavigation();
                appBarLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.navigation_list_farm_scouting_home:
                sideMenuAdapter.setSelectedPosition(NavigationMenu.MY_QUERIES);
                handleBottomNavigation();
                appBarLayout.setVisibility(View.GONE);
                break;
            case R.id.navigation_landing:
            case R.id.navigation_onboard:
                appBarLayout.setVisibility(View.GONE);
                hideBottomNavigation();
                break;
            default:
//                binding.textViewTitle.setVisibility(View.VISIBLE);
                appBarLayout.setVisibility(View.GONE);
                hideBottomNavigation();
        }
        slidingRootNav.setMenuLocked(appBarLayout.getVisibility() != View.VISIBLE);
    }

    private void handleBottomNavigation() {
        new Handler().postDelayed(() -> toolbar.setNavigationIcon(R.drawable.ic_drawer), 0);
//        binding.textViewTitle.setVisibility(View.VISIBLE);
        appBarLayout.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        setHeaderValue();
    }

    private void hideBottomNavigation() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    private void setNavGraph() {
        if (isHomeNavGraphSet) return;

        NavInflater inflater = navController.getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.mobile_navigation);

        if (!appPrefs.isUserProfileCompleted()) {
            graph.setStartDestination(R.id.navigation_introduction);
        }
        isHomeNavGraphSet = true;

        navController.setGraph(graph);
    }

    private void setHeaderValue() {
        TextView userNameTextView = findViewById(R.id.tv_name);
        RoundedImageView riv = findViewById(R.id.iv_profile);

        String phoneNumber = "+" + appPrefs.getPhoneNumber();
        homeViewModel.getProfile().observe(this, farmer -> {
            Glide.with(this)
                    .load(URLConstants.S3_PROFILE_IMAGE_BASE_URL + farmer.getProfileImage())
                    .placeholder(R.mipmap.ic_avatar)
                    .apply(new RequestOptions().circleCrop())
                    .into(riv);
            userNameTextView.setText(String.format("%s %s", farmer.getFirstName(), farmer.getLastName()));
        });
        userNameTextView.setText(String.format("%s %s", appPrefs.getFirstName(), appPrefs.getLastName()));
    }

    private void changeToLandingPage() {
        new AppPrefs(this).clearUserDetails();
        DataHolder.clearInstance();
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.LOGOUT);
    }

    @WorkerThread
    private void logout() {
        ClearDBTask task = new ClearDBTask();
        task.setContext(this);
        task.setOnTaskCompletionListener(new OnTaskCompletionListener() {

            @Override
            public void onTaskSuccess(Object data) {
                changeToLandingPage();
            }

            @Override
            public void onTaskFailure(String reason, String errorMessage) {
                Toast.makeText(
                        MainActivity.this,
                        "Could not clear your data. Please try after some time",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        task.setLoadingMessage("Clearing data");
        task.setShowLoading(true);
        task.execute();
    }

    private void openProfileFragment() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.FARMER_PROFILE);

    }

    private void openHomeFragment() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.HOME_FRAGMENT);
    }

    private void openFarmTalkFragment() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.FARMER_TALKS_FRAGMENT);
    }

    private void openPopFragment() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.POP_HOME);
    }

    private void openCropCalendar() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.FARMER_CROP_CALENDAR);
    }

    private void oepnMarketAnalysis() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_MARKET_ANALYSIS);
    }

    private void openAdvisory() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.CROP_ADVISORY_INBOX);
    }

    private void openFertilizerCalculator() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_FERTILIZER_CALCULATOR);
    }


    private void openAboutus() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_ABOUT_US);
    }


    private void openNutrisource() {
        navController.navigate(
                WebViewFragmentDirections.actionToWebviewFragment()
                        .setTitle(getString(R.string.nutri_source))
                        .setUrl("")
        );
    }


    private void openLanguagesBottomSheet() {
        NavigationController.getInstance(this)
                .onFragmentChange(AppConstants.FragmentConstants.LANGUAGES_BOTTOM_SHEET);
    }


    private void invite() {
        String message = getString(R.string.invite_msg) + "\n" +
                "https://play.google.com/store/apps/details?id=com.farmsanta.farmer";
        Share.INSTANCE.share(this, message);
    }


    private void confirmLogout() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Create and show the dialog.
        ConfirmLogoutDialog newFragment = new ConfirmLogoutDialog();
        newFragment.setCancelable(false);
        newFragment.setOnDialogClickListener(new OnDialogClickListener() {
            @Override
            public void onYesClick(String... params) {
                logout();
            }

            @Override
            public void onNoClick() {
                sideMenuAdapter.setSelectedPosition(
                        sideMenuAdapter.getPreviousPosition()
                );
            }
        });
        newFragment.show(ft, "dialog");
    }


    private void getCurrentFarmer() {
        GetCurrentFarmerTask task = new GetCurrentFarmerTask();
        task.setContext(this);
        task.setShowLoading(false);
        task.setOnTaskCompletionListener(new OnTaskCompletionListener() {
            @Override
            public void onTaskSuccess(Object data) {
                if (data instanceof Farmer) {
                    DataHolder.getInstance().setSelectedFarmer((Farmer) data);
                }
            }

            @Override
            public void onTaskFailure(String reason, String errorMessage) {

            }
        });
        task.execute();
    }


    private void fetchRegionList() {
        GetRegionsTask task = new GetRegionsTask();
        task.setContext(this);
        task.setShowLoading(false);
        task.setOnTaskCompletionListener(new OnTaskCompletionListener() {
            @Override
            public void onTaskSuccess(Object data) {
                if (data instanceof Region[]) {
                    DataHolder.getInstance().setRegions((Region[]) data);
                }
            }

            @Override
            public void onTaskFailure(String reason, String errorMessage) {

            }
        });
        task.execute();
    }

    private void fetchTerritory() {
        if (DataHolder.getInstance().getAllTerritories() != null) return;
        GetTerritoryTask task = new GetTerritoryTask();
        task.setContext(this);
        task.setShowLoading(false);
        task.setOnTaskCompletionListener(new OnTaskCompletionListener() {
            @Override
            public void onTaskSuccess(Object data) {
                if (data instanceof Array[]) {
                    DataHolder.getInstance().setAllTerritories((Territory[]) data);
                }
            }

            @Override
            public void onTaskFailure(String reason, String errorMessage) {

            }
        });
        task.execute();
    }


    private void fetchPopList() {
        GetPopListTask task = new GetPopListTask();
        task.setContext(this);
        task.setShowLoading(false);
        task.setOnTaskCompletionListener(new OnTaskCompletionListener() {
            @Override
            public void onTaskSuccess(Object data) {
                if (data instanceof Array[]) {
                    ArrayList<PopDto> list = new ArrayList<>();
                    Collections.addAll(list, (PopDto[]) data);
                    DataHolder.getInstance().setPopDtoArrayList(list);
                }
            }

            @Override
            public void onTaskFailure(String reason, String errorMessage) {

            }
        });
        task.execute();
    }


    private void fetchCrops() {
        GetCropListTask task = new GetCropListTask();
        task.setContext(this);
        task.setShowLoading(false);
        task.execute();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        navController.handleDeepLink(intent);
    }

    /**
     * Requests post notification permission on Android 13+
     */
    private void requestPermission() {
        //checking API version if it is API level 33+, need to ask for POST_NOTIFICATIONS
        //permission in order to get notification.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
                    int messageId = result ? R.string.permission_granted_msg : R.string.notification_permission_denied_msg;
                    ExtensionsKt.showToast(this, messageId);
                }).launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }


    private void handleNotification() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            String type = intent.getStringExtra(ApiConstants.Notification.TYPE);
            String uuid = intent.getStringExtra(ApiConstants.Notification.UUID);
            String post = intent.getStringExtra(ApiConstants.Notification.POST);
            String languageId = intent.getStringExtra(ApiConstants.Notification.LANGUAGE_ID);
            if (type != null && uuid != null) {
                int destination = getDestination(type);
                Bundle arguments = getArguments(type, uuid, languageId);
                navController.navigate(destination, arguments);
            }
        }
    }


    /**
     * Returns the destination id in navigation graph by type.
     */
    private int getDestination(String type) {
        if (!new AppPrefs(this).isUserProfileCompleted()) return R.id.navigation_introduction;
        int destination = R.id.navigation_home;
        if (type.equalsIgnoreCase(ApiConstants.NotificationType.FARM_TALK)) {
            destination = R.id.navigation_social_post_details;
        } else if (type.equalsIgnoreCase(ApiConstants.NotificationType.CROP_ADVISORY)) {
            destination = R.id.viewAdvisoryFragment;
        } else if (type.equalsIgnoreCase(ApiConstants.NotificationType.SCOUTING)) {
            destination = R.id.navigation_farm_scout_details_home;
        }
        return destination;
    }

    /**
     * Returns the argument has to be supplied to the destination.
     */
    private Bundle getArguments(String type, String uuid, String languageId) {
        Bundle args = new Bundle();
        if (!new AppPrefs(this).isUserProfileCompleted()) return args;
        switch (type) {
            case ApiConstants.NotificationType.FARM_TALK:
            case ApiConstants.NotificationType.CROP_ADVISORY:
                args.putString("uuid", uuid);
                break;
            case ApiConstants.NotificationType.SCOUTING:
                args.putString("scouting_uuid", uuid);
                break;
        }
        args.putString(ApiConstants.Notification.LANGUAGE_ID, languageId);
        return args;
    }
}