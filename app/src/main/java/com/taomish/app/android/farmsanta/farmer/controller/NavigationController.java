package com.taomish.app.android.farmsanta.farmer.controller;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity;
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment;
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants;
import com.taomish.app.android.farmsanta.farmer.fragments.AddFarmLocationFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.AddFarmerFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.CropAdvisoryInboxFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.CroppingStageDetailsFragment;
import com.taomish.app.android.farmsanta.farmer.fragments.DiseasesFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.FarmCalculatorFragment;
import com.taomish.app.android.farmsanta.farmer.fragments.FarmScoutImageFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.FertilizerCalcFragment;
import com.taomish.app.android.farmsanta.farmer.fragments.FertilizerCalcFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.HomeFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.IPMDiseaseManagementFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.IPMInsectManagementFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.IPMWeedManagementFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.MyBookMarksPopsFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.MyCropsFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.NutriSourceFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.PesticideCalcFragment;
import com.taomish.app.android.farmsanta.farmer.fragments.PopDetailsFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.PopLibraryFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.ProfileEditFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.ProfileFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.SavedNutriSourceFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.SeedRateFragment;
import com.taomish.app.android.farmsanta.farmer.fragments.SelectFarmerLocationFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.SelectLanguageFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.SignUpFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.ViewPopFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.fragments.WeatherFragmentDirections;
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnFragmentInteractionListener;

public class  NavigationController {
    private static NavigationController instance;

    private FragmentActivity activity;

    private NavigationController(FragmentActivity activity) {
        this.activity = activity;
    }

    public static NavigationController getInstance(FragmentActivity activity) {
        if (instance == null)
            instance = new NavigationController(activity);

        if (instance.activity != activity)
            instance.activity = activity;

        return instance;
    }

    public static NavigationController getInstance() {
        if (instance == null || instance.activity == null)
            throw new IllegalStateException("Not yet initialized");

        return instance;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void goToFragment(Fragment fragment, String tag, boolean addToBackStack,
                             boolean clearBackStack, boolean replace) {
        FragmentManager fm = activity.getSupportFragmentManager();
        if (fm.isDestroyed()) {
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }

        if (replace) {
            ft.replace(getFragmentContainerId(), fragment, tag);
        } else {
            ft.add(getFragmentContainerId(), fragment, tag);
        }

        if (clearBackStack) {
            doNotLoadBackgroundFlag();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }

        ft.commit();
    }

    public void onFragmentChange(String tag) {
        switch (tag) {
            case AppConstants.FragmentConstants.LANDING_FRAGMENT:
                goToLandingFragment();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_SELECT_LANGUAGE:
                goToSelectLanguageFragment();
                break;
            case AppConstants.FragmentConstants.PROFILE_TO_LANDING:
                goToLandingFromProfileFragment();
                break;
            case AppConstants.FragmentConstants.WELCOME_FARMER_FRAGMENT:
                goToWelcomeFragmentFromConfirmOtpFragment();
                break;
            case AppConstants.FragmentConstants.FARMER_HOME_FRAGMENT:
                goToHomeFragment();
                break;
            case AppConstants.FragmentConstants.FARMER_TALKS_FRAGMENT:
                goToTalksFragment();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_DISEASES:
                goToDiseasesFromHome();
                break;
            case AppConstants.FragmentConstants.FARMER_NEW_POST_FRAGMENT:
                goToNewSocialWallPostFragment();
                break;
            case AppConstants.FragmentConstants.FARMER_NEW_POST_FRAGMENT_FROM_HOME:
                goToNewSocialWallPostFromHome();
                break;
            case AppConstants.FragmentConstants.FARMER_MY_ACTIVITIES:
                goToMyActivitiesFragmentFromSocialWall();
                break;
            case AppConstants.FragmentConstants.FARMER_SAVED_POSTS:
                goToSavedPostsFragmentFromSocialWall();
                break;
            case AppConstants.FragmentConstants.OTHER_FARMER_PROFILE:
                goToOtherFarmerProfileFragmentFromSocialWall();
                break;
            case AppConstants.FragmentConstants.FARMER_FARM_CALC_FRAGMENT:
                goToFarmCalculatorFragment();
                break;
            case AppConstants.FragmentConstants.SEED_RATE_FRAGMENT:
                goToSeedRateFragment();
                break;
            case AppConstants.FragmentConstants.PESTICIDE_CALC_FRAGMENT:
                goToPesticideCalcFragment();
                break;
            case AppConstants.FragmentConstants.FERTILIZER_CALC_FRAGMENT:
                goToFertilizerCalcFragment();
                break;
            case AppConstants.FragmentConstants.SOCIAL_MESSAGE_FRAGMENT:
                goToMessageDetailsFragment();
                break;
            case AppConstants.FragmentConstants.MSG_DETAILS_FROM_HOME:
                goToSocialMessageFromHomeFragment();
                break;
            case AppConstants.FragmentConstants.LAND_CROP:
                goToLandCropListFragment();
                break;
            case AppConstants.FragmentConstants.FARMER_PROFILE:
                goToProfileFragment();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_LOGIN_MOBILE_NO:
                goToLoginWithMobileNoFragment();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_SIGN_UP:
                goToLoginToSignUpFragment();
                break;
            case AppConstants.FragmentConstants.POP_HOME:
                goToPopHomeFragment();
                break;
            case AppConstants.FragmentConstants.FARMER_MY_BOOKMARKS_POPS:
                goToMyBookmarksPopsFromPopHome();
                break;
            case AppConstants.FragmentConstants.VIEW_OTHER_FARMER_POPS:
                goToViewOtherFarmerPopsFromPopHome();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_MARKET_ANALYSIS:
                goToMarketAnalysisFromHome();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_MARKET_ANALYSIS_FROM_MY_CROPS:
                goToMarketAnalysisFromMyCrops();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_FERTILIZER_CALCULATOR:
                goToFertilizerCalculatorFromHome();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_NUTRI_SOURCE:
                goToNutriSourceFromHome();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_SAVED_NUTRI_SOURCE:
                goToSavedNutriSourceFromNutriSource();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_MY_CROPS:
                goToMyCropsFromHome();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_PROFILE_EDIT:
                goToEditProfileFragment();
                break;
            case AppConstants.FragmentConstants.GO_BACK:
                goBack();
                break;
            case AppConstants.FragmentConstants.ALL_NEWS:
                goToAllNews();
                break;
            case AppConstants.FragmentConstants.CROP_ADVISORY_INBOX:
                goToCropAdvisoryInbox();
                break;
            case AppConstants.FragmentConstants.CROP_ADVISORY_INBOX_FROM_MY_CROPS:
                goToCropAdvisoryInboxFromMyCrops();
                break;
            case AppConstants.FragmentConstants.SAVED_CROP_ADVISORIES:
                goToSavedAdvisoriesFragment();
                break;
            case AppConstants.FragmentConstants.WEATHER_DETAILS:
                goToWeatherDetails();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_WEATHER_FORECAST:
                goToWeatherForecast();
                break;
            case AppConstants.FragmentConstants.LOGOUT:
                logoutAndRestart();
                break;
            case AppConstants.FragmentConstants.HOME_FRAGMENT:
                goToNavigationHome();
                break;
            case AppConstants.FragmentConstants.FARMER_CROP_CALENDAR:
                goToCropCalendarFromHome();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_ADD_CROP_CALENDAR:
                goToAddCropCalendarFromCropCalendarHome();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_CROPPING_STAGE_DETAILS:
                goToCroppingStageDetailsFromCropCalendarHome();
                break;
            case AppConstants.FragmentConstants.DISEASE_DETAIL_TO_ASK_QUERY:
                goToAskQueryFromDiseaseDetail();
                break;
            case AppConstants.FragmentConstants.FERTILIZER_CALC_FROM_CROP_CROP_CALENDAR:
                goToFertilizerCalcFromCropCalendar();
                break;
            case AppConstants.FragmentConstants.FERTILIZER_CALC_FROM_CROP_STAGE_DETAILS:
                goToFertilizerCalcFromCroppingStageDetails();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_ABOUT_US:
                goToAboutUs();
                break;
            case AppConstants.FragmentConstants.FRAGMENT_QUERY_SENT:
                goToQuerySent();
                break;
            case AppConstants.FragmentConstants.LANGUAGES_BOTTOM_SHEET:
                openLanguagesSheet();
                break;
        }
    }

    public void onFragmentData(Object... data) {
        if (data[0] instanceof String tag) {
            switch (tag) {
                case AppConstants.FragmentConstants.FRAGMENT_SELECT_LANGUAGE_TO_LOGIN:
                    goToLoginFromSelectLanguageFragment((int) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_ADD_FARM_LOCATION:
                    goToAddFarmLocationFragment((Boolean) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_ADD_CROP:
                    goToAddCropFragment();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_ADD_CROP_FROM_PROFILE:
                    goToAddCropFragmentFromEditProfile();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_ADD_FARM_LOCATION_FROM_EDIT_PROFILE:
                    goToAddFarmLocationFragmentFromEditProfile((Boolean) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_MAP_VIEW_FROM_EDIT_PROFILE:
                    goToMapViewFragmentFromProfileEdit((int) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_MAP_VIEW_FROM_ADD_FARMER:
                    goToMapViewFragmentFromAddFarmer((int) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_MAP_VIEW_FROM_ADD_FARM_LOCATION:
                    goToMapViewFragmentFromAddFarmLocation((int) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_VIEW_POP:
                    goToViewPopFromPopHome((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_ZOOM_IMAGE_FROM_VIEW_POP:
                    goToZoomImageFromViewPop((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_ZOOM_IMAGE_FROM_POP_DETAILS:
                    goToZoomImageFromPopDetails((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_VIEW_POP_FROM_HOME:
                    goToViewPopFromHome((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_VIEW_POP_FROM_MY_CROPS:
                    goToViewPopFromMyCrops((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FARMER_POP_DET_FRAGMENT:
                    goToPopDetailsFragment((int) data[1], (int) data[2]);
                    break;
                case AppConstants.FragmentConstants.IPM_INSECT_MANAGEMENT_FRAGMENT:
                    goToIPMInsectManagementFragment((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.IPM_DISEASE_MANAGEMENT_FRAGMENT:
                    goToIPMDiseaseManagementFragment((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.IPM_WEED_MANAGEMENT_FRAGMENT:
                    goToIPMWeedManagementFragment((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_FERTILIZER_RECOMMENDATION:
                    goToFertilizerRecommendationFromCalculator();
                    break;
                case AppConstants.FragmentConstants.FARMER_MAP_FRAGMENT:
                    goToFarmerMapFragmentFromProfile((Integer) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_DISEASE_DETAILS:
                    goToDiseaseDetailsFromDiseaseHome();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_DISEASE_DETAILS_FROM_MY_CROPS:
                    goToDiseaseDetailsFromMyCrops();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_DISEASE_DETAILS_FROM_HOME:
                    goToDiseaseDetailsFromHome();
                    break;
                case AppConstants.FragmentConstants.SCOUT_FARM_IMG_DETAILS_FROM_HOME:
                    goToFarmScoutImageDetailsFromHome((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.SCOUT_FARM_IMG_DETAILS_FROM_MY_CROPS:
                    goToFarmScoutImageDetailsFromMyCrops((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.LOGIN_TO_CONFIRM_OTP_FRAGMENT:
                    goToConfirmOtpFragmentFromLogin();
                    break;
                case AppConstants.FragmentConstants.SIGN_UP_TO_CONFIRM_OTP_FRAGMENT:
                    goToConfirmOtpFragmentFromSignUp((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_SELECT_FARMER_LOCATION:
                    goToSelectFarmerLocation();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_ADD_FARMER:
                    goToAddFarmerFragment((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.SCOUTING_LIST_TO_ADD_SCOUT:
                    goToAddScoutFromScoutingList();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_VIEW_ADVISORY_FROM_HOME:
                    goToAdvisoryDetailsFromHome();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_VIEW_ADVISORY_FROM_ADVISORY_LIST:
                    goToAdvisoryDetailsFromAdvisoryInbox();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_VIEW_ADVISORY_FROM_MY_CROPS:
                    goToAdvisoryDetailsFromMyCrops();
                    break;
                case AppConstants.FragmentConstants.SCOUTING_DETAILS_HOME:
                    goToScoutingDetailsFromHome((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.SCOUTING_DETAILS_FROM_SCOUTING_LIST:
                    goToScoutingDetailsFromScoutingList();
                    break;
                case AppConstants.FragmentConstants.HOME_LIST_FARM_SCOUT:
                    goToListFarmScoutingFromHome((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_FARM_SCOUTING_LIST_FROM_MY_CROPS:
                    goToListFarmScoutingFromMyCrops((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_NUTRI_SOURCE_DETAILS:
                    goToNutriSourceDetailsFromNutriSource();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_NUTRI_SOURCE_DETAILS_FROM_SAVED:
                    goToNutriSourceDetailsFromSavedNutriSource();
                    break;
                case AppConstants.FragmentConstants.FRAGMENT_DISEASES_FROM_MY_CROPS:
                    goToDiseasesFromMyCrops((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.VIEW_POP_FROM_BOOKMARKED_POPS:
                    goToViewPopFromBookmarkedPops((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.ZOOM_IMAGE_FRAGMENT_FROM_IPM_DISEASE:
                    goToZoomImageFromIPMDisease((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.ZOOM_IMAGE_FRAGMENT_FROM_IPM_INSECTS:
                    goToZoomImageFromIPMInsects((String) data[1]);
                    break;
                case AppConstants.FragmentConstants.ZOOM_IMAGE_FRAGMENT_FROM_IPM_WEEDS:
                    goToZoomImageFromIPMWeed((String) data[1]);
                    break;
            }

        }
    }

    private final OnFragmentInteractionListener onFragmentInteractionListener = new OnFragmentInteractionListener() {
        @Override
        public void onFragmentChange(String tag) {
            switch (tag) {
                case AppConstants.FragmentConstants.FARMER_FARM_CALC_FRAGMENT:
                    goToFarmCalculatorFragment();
                    break;
                case AppConstants.FragmentConstants.SEED_RATE_FRAGMENT:
                    goToSeedRateFragment();
                    break;
                case AppConstants.FragmentConstants.PESTICIDE_CALC_FRAGMENT:
                    goToPesticideCalcFragment();
                    break;
                case AppConstants.FragmentConstants.FERTILIZER_CALC_FRAGMENT:
                    goToFertilizerCalcFragment();
                    break;
            }
        }

        @Override
        public void onFragmentData(Object... data) {
            if (data[0] instanceof String tag) {
                if (AppConstants.FragmentConstants.FARMER_OTP_FRAGMENT.equals(tag)) {
                    DataHolder.getInstance().setDataObject(new Object[]{data[1], data[2], data[3]});
                }
            }
        }
    };


    private void logoutAndRestart() {
        ((MainActivity) activity).getNavController().navigate(R.id.navigation_landing);
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    private void goToHomeFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_otp_to_home);
//        activity.recreate();
    }

    private void goToNavigationHome() {
        ((MainActivity) activity).getNavController().navigate(R.id.navigation_home);
    }

    private void goToLandingFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_onboard_to_landing);
    }

    private void goToSelectLanguageFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_navigation_landing_to_selectLanguageFragment);
    }

    private void goToLoginFromSelectLanguageFragment(int phoneCode) {
        NavDirections options = SelectLanguageFragmentDirections
                .actionSelectLanguageToLogin()
                .setPhoneCode(phoneCode);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToLandingFromProfileFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_profile_to_landing);
    }

    private void goToWelcomeFragmentFromConfirmOtpFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_confirmOtp_to_welcomeFarmSantaFragment);
    }


    private void goToLoginWithMobileNoFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_landing_to_login);
    }

    private void goToLoginToSignUpFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_navigation_login_to_signUpFragment);
    }


    private void goToProfileFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.navigation_profile);
    }

    private void goToFarmCalculatorFragment() {
        FarmSantaBaseFragment fragment = new FarmCalculatorFragment();
        fragment.setOnFragmentInteractionListener(onFragmentInteractionListener);

        goToFragment(fragment, AppConstants.FragmentConstants.FARMER_FARM_CALC_FRAGMENT,
                true, false, true);
        hideActionBar();
    }

    private void goToSeedRateFragment() {
        FarmSantaBaseFragment fragment = new SeedRateFragment();
        fragment.setOnFragmentInteractionListener(onFragmentInteractionListener);

        goToFragment(fragment, AppConstants.FragmentConstants.SEED_RATE_FRAGMENT,
                true, false, true);
        hideActionBar();
    }

    private void goToPesticideCalcFragment() {
        FarmSantaBaseFragment fragment = new PesticideCalcFragment();
        fragment.setOnFragmentInteractionListener(onFragmentInteractionListener);

        goToFragment(fragment, AppConstants.FragmentConstants.PESTICIDE_CALC_FRAGMENT,
                true, false, true);
        hideActionBar();
    }

    private void goToFertilizerCalcFragment() {
        FarmSantaBaseFragment fragment = new FertilizerCalcFragment();
        fragment.setOnFragmentInteractionListener(onFragmentInteractionListener);

        goToFragment(fragment, AppConstants.FragmentConstants.FERTILIZER_CALC_FRAGMENT,
                true, false, true);
        hideActionBar();
    }

    private void goToMessageDetailsFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_social_wall_to_view_post);
    }

    private void goToSocialMessageFromHomeFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_view_post);
    }


    private void goToEditProfileFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_navigation_profile_to_profileEditFragment);
    }


    private void goToFarmerMapFragmentFromProfile(int selectedIndex) {
        NavDirections actionPlotMap = ProfileFragmentDirections.actionProfileToPlotMap()
                .setSelectedLandIndex(selectedIndex);
        ((MainActivity) activity).getNavController().navigate(actionPlotMap);
    }

    private void goToNewSocialWallPostFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_social_wall_to_create_post);
    }

    private void goToNewSocialWallPostFromHome() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_create_new_post);
    }

    private void goToLandCropListFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_land_crop_details);
    }

    private void goToAddCropFragment() {
        NavDirections options = AddFarmerFragmentDirections
                .actionNavigationAddFarmerToAddCropsFragment();
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToAddCropFragmentFromEditProfile() {
        NavDirections options = ProfileEditFragmentDirections
                .actionProfileEditToAddCrops();
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToAddFarmLocationFragment(Boolean isEditFarmer) {
        NavDirections options = AddFarmerFragmentDirections
                .actionNavigationAddFarmerToAddFarmLocationFragment()
                .setIsEditFarmer(isEditFarmer);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToMapViewFragmentFromAddFarmLocation(int selectedMapIndex) {
        NavDirections options = AddFarmLocationFragmentDirections
                .actionAddFarmLocationToMapView()
                .setSelectedLandIndex(selectedMapIndex);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToMapViewFragmentFromProfileEdit(int selectedMapIndex) {
        NavDirections options = ProfileEditFragmentDirections
                .actionProfileEditToMapView()
                .setSelectedLandIndex(selectedMapIndex);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToMapViewFragmentFromAddFarmer(int selectedMapIndex) {
        NavDirections options = AddFarmerFragmentDirections
                .actionAddFarmerToMapView()
                .setSelectedLandIndex(selectedMapIndex);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToAddFarmLocationFragmentFromEditProfile(Boolean isEditFarmer) {
        NavDirections options = ProfileEditFragmentDirections
                .actionProfileEditToAddFarmLocation()
                .setIsEditFarmer(isEditFarmer);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToConfirmOtpFragmentFromLogin() {
        NavDirections options = com.taomish.app.android.farmsanta.farmer.fragments.LoginWithMobileFragmentDirections.actionLoginToConfirmOtpFragment().setIsSigningUp(false);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToConfirmOtpFragmentFromSignUp(String mobileNumber) {
        NavDirections options = SignUpFragmentDirections
                .actionSignUpFragmentToConfirmOtpFragment()
                .setMobileNumber(mobileNumber)
                .setIsSigningUp(true);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToSelectFarmerLocation() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_confirm_otp_fragment_to_select_farmer_location);
    }

    private void goToDiseasesFromHome() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_diseases);
    }

    private void goToDiseasesFromMyCrops(String uuid) {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsToDiseases()
                .setSelectedCrop(uuid);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToDiseaseDetailsFromDiseaseHome() {
        NavDirections options = DiseasesFragmentDirections
                .actionDiseasesHomeToDiseaseDetails();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToDiseaseDetailsFromMyCrops() {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsToDiseaseDetails();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToDiseaseDetailsFromHome() {
        NavDirections options = HomeFragmentDirections
                .actionHomeToDiseaseDetails();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToMarketAnalysisFromHome() {
        NavDirections options = HomeFragmentDirections
                .actionHomeToMarketAnalysis();
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToMarketAnalysisFromMyCrops() {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsToMarketAnalysis();
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToFertilizerCalculatorFromHome() {
        NavDirections options = HomeFragmentDirections
                .actionHomeToFertilizerCalculator();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToFertilizerRecommendationFromCalculator() {
        NavDirections options = FertilizerCalcFragmentDirections
                .actionFertilizerCalcToFertilizerRecommendation();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToMyCropsFromHome() {
        NavDirections options = HomeFragmentDirections
                .actionHomeToMyCrops();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToNutriSourceFromHome() {
        NavDirections options = HomeFragmentDirections
                .actionHomeToNutriSource();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToNutriSourceDetailsFromNutriSource() {
        NavDirections options = NutriSourceFragmentDirections
                .actionNutriSourceToNutriSourceDetails();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToNutriSourceDetailsFromSavedNutriSource() {
        NavDirections options = SavedNutriSourceFragmentDirections
                .actionSavedNutriSourceToNutriSourceDetails();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToSavedNutriSourceFromNutriSource() {
        NavDirections options = NutriSourceFragmentDirections
                .actionNutriSourceToSavedNutriSource();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToAddFarmerFragment(String mobileNumber) {
        NavDirections options = SelectFarmerLocationFragmentDirections
                .actionSelectLocationToNavigationAddFarmer()
                .setMobileNumber(mobileNumber);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToAddScoutFromScoutingList() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_scout_list_to_add_scout_new);
    }

    private void goToListFarmScoutingFromHome(String landId) {
        NavDirections options = HomeFragmentDirections
                .actionScoutingImageList()
                .setLandId(landId);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToListFarmScoutingFromMyCrops(String landId) {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsScoutingImageList()
                .setLandId(landId);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToTalksFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_social_wall);
    }

    private void goToPopDetailsFragment(int index, int tab) {
        NavDirections options = ViewPopFragmentDirections
                .actionViewPopToPopDetails()
                .setIndex(index)
                .setTab(tab);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToIPMInsectManagementFragment(String name) {
        NavDirections options = PopDetailsFragmentDirections
                .actionPopDetailsToIPMInsectManagementFragment()
                .setName(name);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToIPMDiseaseManagementFragment(String name) {
        NavDirections options = PopDetailsFragmentDirections
                .actionPopDetailsToIPMDiseaseManagementFragment()
                .setName(name);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToIPMWeedManagementFragment(String name) {
        NavDirections options = PopDetailsFragmentDirections
                .actionPopDetailsToIPMWeedManagementFragment()
                .setName(name);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToAllNews() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_all_news);
    }

    private void goToCropAdvisoryInbox() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_crop_advisory);
    }

    private void goToCropAdvisoryInboxFromMyCrops() {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsToCropAdvisory();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToSavedAdvisoriesFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_navigation_crop_advisory_to_savedAdvisoriesFragment);
    }


    private void goToAdvisoryDetailsFromHome() {
        NavDirections options = HomeFragmentDirections
                .actionHomeToViewAdvisoryFragment();
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToAdvisoryDetailsFromAdvisoryInbox() {
        NavDirections options = CropAdvisoryInboxFragmentDirections
                .actionAdvisoryInboxToViewAdvisory();
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToAdvisoryDetailsFromMyCrops() {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsToViewAdvisory();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToScoutingDetailsFromHome(String uuid) {
        NavDirections options = HomeFragmentDirections
                .actionHomeToScoutDetails()
                .setScoutingUuid(uuid);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToScoutingDetailsFromScoutingList() {
        NavDirections options = FarmScoutImageFragmentDirections
                .actionScoutListToDetailsHome();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToWeatherDetails() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_weather_details);
    }

    private void goToWeatherForecast() {
        NavDirections options = WeatherFragmentDirections
                .actionNavigationWeatherDetailsToWeatherForecastFragment();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goBack() {
        ((MainActivity) activity).getNavController().popBackStack();
    }

    private void doNotLoadBackgroundFlag() {

    }

    private void hideActionBar() {

    }

    private void goToPopHomeFragment() {
        ((MainActivity) activity).getNavController().navigate(R.id.navigation_pop);
    }

    private void goToMyBookmarksPopsFromPopHome() {
        NavDirections options = PopLibraryFragmentDirections
                .actionPopHomeToMyBookMarksFragment();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToViewPopFromPopHome(String uuid) {
        NavDirections options = PopLibraryFragmentDirections
                .actionPopLibraryToViewPopFragment()
                .setUuid(uuid);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToZoomImageFromViewPop(String url) {
        NavDirections options = ViewPopFragmentDirections
                .actionViewPopToZoomImage()
                .setImageUrl(url);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToZoomImageFromPopDetails(String url) {
        NavDirections options = PopDetailsFragmentDirections
                .actionNavigationPopDetailsToZoomImageFragment()
                .setImageUrl(url);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToViewPopFromHome(String index) {
        NavDirections options = HomeFragmentDirections
                .actionNavigationHomeToViewPopFragment()
                .setUuid(index);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToViewPopFromMyCrops(String index) {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsToViewPop()
                .setUuid(index);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToCropCalendarFromHome() {
        NavDirections options = HomeFragmentDirections
                .actionHomeToCropCalendar();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToAddCropCalendarFromCropCalendarHome() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_crop_calendar_to_add_crop_calendar);
    }

    private void goToCroppingStageDetailsFromCropCalendarHome() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_crop_calendar_to_cropping_stage_details);
    }

    private void goToViewOtherFarmerPopsFromPopHome() {
        NavDirections options = PopLibraryFragmentDirections
                .actionPopLibraryToViewOtherFarmerPopsFragment();
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToViewPopFromBookmarkedPops(String uuid) {
        NavDirections options = MyBookMarksPopsFragmentDirections
                .actionBookmarkedPopsToViewPop()
                .setUuid(uuid);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToFarmScoutImageDetailsFromHome(String uuid) {
        NavDirections options = HomeFragmentDirections
                .actionHomeToScoutDetails()
                .setScoutingUuid(uuid);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToFarmScoutImageDetailsFromMyCrops(String uuid) {
        NavDirections options = MyCropsFragmentDirections
                .actionMyCropsToScoutDetails()
                .setScoutingUuid(uuid);
        ((MainActivity) activity).getNavController().navigate(options);
    }


    private void goToMyActivitiesFragmentFromSocialWall() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_navigation_social_wall_to_myActivitiesFragment);
        activity.recreate();
    }

    private void goToOtherFarmerProfileFragmentFromSocialWall() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_navigation_social_wall_to_otherFarmerProfileFragment);
        activity.recreate();
    }

    private void goToSavedPostsFragmentFromSocialWall() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_navigation_social_wall_to_savedPostsFragment);
        activity.recreate();
    }

    private int getFragmentContainerId() {
        return 0;
    }


    private void goToAskQueryFromDiseaseDetail() {
        ((MainActivity) activity).getNavController().navigate(R.id.disease_detail_to_ask_query);
    }

    private void goToFertilizerCalcFromCropCalendar() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_crop_calendar_to_fertilizer_calculator);
    }


    private void goToFertilizerCalcFromCroppingStageDetails() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_crop_stage_details_to_fertilizer_calculator);
    }

    private void goToAboutUs() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_about_us);
    }

    private void goToQuerySent() {
        NavController navController = ((MainActivity) activity).getNavController();
        NavOptions options = new NavOptions.Builder()
                .setPopUpTo(R.id.navigation_home, false)
                .build();
        navController.navigate(R.id.action_image_details_farm_scout_in_home_to_query_sent, null, options);
    }

    private void openLanguagesSheet() {
        ((MainActivity) activity).getNavController().navigate(R.id.action_home_to_languages_bottom_sheet);
    }

    private void goToZoomImageFromIPMDisease(String imageUrl) {
        NavDirections options = IPMDiseaseManagementFragmentDirections
                .actionIPMDiseaseManagementFragmentToZoomImageFragment()
                .setImageUrl(imageUrl);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToZoomImageFromIPMInsects(String imageUrl) {
        NavDirections options = IPMInsectManagementFragmentDirections
                .actionIPMInsectManagementFragmentToZoomImageFragment()
                .setImageUrl(imageUrl);
        ((MainActivity) activity).getNavController().navigate(options);
    }

    private void goToZoomImageFromIPMWeed(String imageUrl) {
        NavDirections options = IPMWeedManagementFragmentDirections
                .actionIPMWeedManagementFragmentToZoomImageFragment()
                .setImageUrl(imageUrl);
        ((MainActivity) activity).getNavController().navigate(options);
    }
}
