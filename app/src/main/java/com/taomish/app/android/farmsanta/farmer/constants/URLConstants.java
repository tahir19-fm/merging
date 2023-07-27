package com.taomish.app.android.farmsanta.farmer.constants;

import androidx.annotation.NonNull;

import com.taomish.app.android.farmsanta.farmer.BuildConfig;

import org.jetbrains.annotations.Contract;

public final class URLConstants {
    //End points
    public static final String SCOUT_PLANT_PART = "scout_plant_part";

    public static final String BASE_URL = BuildConfig.BASE_URL;

    private static final String BASE_USER_IDENTIFIER = "user/";
    private static final String BASE_POP_IDENTIFIER = "pop/";
    private static final String BASE_MSG_IDENTIFIER = "message/";
    private static final String BASE_COMMON_DATA = "common-data/";
    private static final String BASE_STORAGE = "storage/cms/";
  //  private static final String BASE_POP_SERVICE = ":8092/pop-service/";
  private static final String BASE_POP_SERVICE = "pop-service/";

   // private static final String BASE_FARMER_SERVICE = ":8082/farmer-service/";
   private static final String BASE_FARMER_SERVICE = "farmer-service/";
   // private static final String BASE_MASTER_SERVICE = ":8090/master-data-service/";
   private static final String BASE_MASTER_SERVICE = "master-data-service/";
   // private static final String BASE_USER_SERVICE = ":8093/user-service/";
   private static final String BASE_USER_SERVICE = "user-service/";
  //  private static final String BASE_NEWS_SERVICE = ":8099/news-service/";
  private static final String BASE_NEWS_SERVICE = "news-service/";
   // private static final String BASE_MARKET_PRICE = ":8088/market-price-service/";
   private static final String BASE_MARKET_PRICE = "market-price-service/";
   // private static final String BASE_WEATHER_SERVICE = ":8086/weather-service/";

    private static final String BASE_WEATHER_SERVICE = "weather-service/";
  //  private static final String BASE_MESSAGE_SERVICE = ":8089/farmer-talk-service/";
    private static final String BASE_MESSAGE_SERVICE = "farmer-talk-service/";
   // private static final String BASE_STORAGE_SERVICE = ":8095/storage-service/";
    private static final String BASE_STORAGE_SERVICE = "storage-service/";
    //Multi use main sub urls
    public static String GLOBAL_INDICATORS_DETAILS_BY_GROUP = BASE_URL + BASE_MASTER_SERVICE + "global-indicator-detail-by-group/";
    public static String UNIT_OF_MEASUREMENT_BY_TYPE = BASE_URL + BASE_MASTER_SERVICE + "unitOfMeasurementByType/";

    public static String LANGUAGE_QUERY_PARAM = "?languageId=";

    public static String CROP_CALENDAR_INDENTIFIER = "cropcalendar";
    public static String ADD_CROP_CALENDAR = BASE_URL + BASE_MASTER_SERVICE + CROP_CALENDAR_INDENTIFIER;
    public static String CROP_CALENDARS_BY_USER_ID = BASE_URL + BASE_MASTER_SERVICE + "cropcalendar-by-userid/";
    public static String CROP_CALENDARS_ALL = BASE_URL + BASE_MASTER_SERVICE + "cropcalendar-stage-active/";
    public static String CROP_STAGE_CALENDAR_LIST = BASE_URL + BASE_MASTER_SERVICE + "cropcalendar-stage-list/";

    public static String CROP_CALENDAR_STAGES_INDICATORS = GLOBAL_INDICATORS_DETAILS_BY_GROUP + "crop_calendar_stages" + LANGUAGE_QUERY_PARAM;

    public static final String FARMER_PROFILE = BASE_URL + BASE_FARMER_SERVICE + "farmer-profile";
    public static final String FARMER_PROFILE_SAVE = BASE_URL + BASE_FARMER_SERVICE + "farmer-profile/" + "farmer-profile-save";
    public static final String FARM_SCOUTING = BASE_URL + BASE_FARMER_SERVICE + "farm-scouting/";
    public static final String FARM_SCOUTING_BY_LAND = BASE_URL + BASE_FARMER_SERVICE + "farm-scouting/farm-scouting-by-land";
    public static final String FARM_SCOUTING_BY_FARMER = BASE_URL + BASE_FARMER_SERVICE + "farm-scouting/farm-scouting-by-farmer";
    public static final String FARMER_GROUPS = BASE_URL + BASE_FARMER_SERVICE + "api/user-group-master/v1/associated-user-groups";
    public static final String CURRENT_FARMER = BASE_URL + BASE_FARMER_SERVICE + "farmer-profile/current-farmer-profile";
    public static final String UPDATE_FARMER = BASE_URL + BASE_USER_SERVICE + "api/app/user";


    public static final String FARM_SCOUTING_BY_SEARCH = FARM_SCOUTING + "farm-scouting/search?searchText=";

    public static final String CROP_DIVISION_GLOBAL_INDICATORS = GLOBAL_INDICATORS_DETAILS_BY_GROUP + "crop_division_ind" + LANGUAGE_QUERY_PARAM;
    public static final String EDUCATION_LEVELS_GLOBAL_INDICATORS = GLOBAL_INDICATORS_DETAILS_BY_GROUP + "education_ind" + LANGUAGE_QUERY_PARAM;
    public static final String WATER_SOURCE_GLOBAL_INDICATORS = GLOBAL_INDICATORS_DETAILS_BY_GROUP + "water_source_ind" + LANGUAGE_QUERY_PARAM;

    public static final String LAND_UOM_GLOBAL_INDICATORS = UNIT_OF_MEASUREMENT_BY_TYPE + "Land measurement" + LANGUAGE_QUERY_PARAM;


    public static String SOCIAL_POST = BASE_URL + BASE_MSG_IDENTIFIER + "message";

    public static final String MESSAGE_SERVICE = BASE_URL + BASE_MESSAGE_SERVICE + "message";
    public static final String MESSAGE_SERVICE_SAVE = BASE_URL + BASE_MESSAGE_SERVICE + "message-app" + LANGUAGE_QUERY_PARAM;
    public static final String MESSAGE = BASE_URL + BASE_MESSAGE_SERVICE + "message-by-location";
    public static final String MESSAGE_BY_ID = BASE_URL + BASE_MESSAGE_SERVICE + "message/";
    public static final String MOST_LIKED_MESSAGES = BASE_URL + BASE_MESSAGE_SERVICE + "message-by-location-most-like";
    public static final String MOST_COMMENTED_MESSAGES = BASE_URL + BASE_MESSAGE_SERVICE + "message-by-location-most-comment";
    public static final String MY_MESSAGES = BASE_URL + BASE_MESSAGE_SERVICE + "my-message";
    public static final String LIKED_MESSAGE = BASE_URL + BASE_MESSAGE_SERVICE + "my-liked-message";
    public static final String COMMENTED_MESSAGE = BASE_URL + BASE_MESSAGE_SERVICE + "my-commented-message";
    public static final String COMMENT = BASE_URL + BASE_MESSAGE_SERVICE + "message-comment";
    public static final String COMMENT_DELETE = BASE_URL + BASE_MESSAGE_SERVICE + "message-comment-delete";
    public static final String COMMENT_UPDATE = BASE_URL + BASE_MESSAGE_SERVICE + "message-update";
    public static final String SAVE_MESSAGE_LIKE = BASE_URL + BASE_MESSAGE_SERVICE + "message-like";
    public static final String SAVE_MESSAGE_DISLIKE = BASE_URL + BASE_MESSAGE_SERVICE + "message-dislike";
    public static final String SEARCH_MESSAGES = BASE_URL + BASE_MESSAGE_SERVICE + "message/search/";

    public static String GET_POP_LIST = BASE_URL + BASE_POP_IDENTIFIER + "pop";
    public static String CROP = BASE_URL + BASE_POP_IDENTIFIER + "crop";
    public static String GET_POP_RECOMMENDATION = BASE_URL + BASE_POP_IDENTIFIER + "recommendation";
    public static String POP_SEARCH = BASE_URL + BASE_POP_SERVICE + BASE_POP_IDENTIFIER + "search?searchText=";
    public static String SEED_RATE = BASE_URL + BASE_POP_IDENTIFIER + "calculate";
    public static String REFERENCE_DATA = BASE_URL + BASE_POP_IDENTIFIER + "reference-data";
    public static String POP_SECTIONS = BASE_URL + BASE_POP_SERVICE + "pop-sections/";
    public static String CROPPING_PROCESS = BASE_URL + BASE_MASTER_SERVICE + "cropping-process-cropid/";

    public static String POP_SERVICE = BASE_URL + BASE_POP_SERVICE + "pop";
    //    public static String POP_SERVICE_USER = BASE_URL + BASE_POP_SERVICE + "user-pop";
    public static String POP_SERVICE_USER = BASE_URL + BASE_POP_SERVICE + "pop-by-location" + LANGUAGE_QUERY_PARAM;
    public static String POP_BOOKMARK_SERVICE = BASE_URL + BASE_POP_SERVICE + "bookmarked-pop";
    public static String POP_BOOKMARK = BASE_URL + BASE_POP_SERVICE + "bookmark";

    public static final String BOOKMARK = BASE_URL + BASE_USER_IDENTIFIER + "bookmark";
    public static final String GENERATE_OTP = BASE_URL + BASE_USER_SERVICE + "generate-otp";
    public static final String SEND_RESET_OTP = BASE_URL + BASE_USER_SERVICE + "send-reset-otp";
    public static final String AUTHENTICATE = BASE_URL + BASE_USER_SERVICE + "authenticateV2";
    public static final String REFRESH_TOKEN = BASE_URL + BASE_USER_SERVICE + "refresh/";
    public static final String CURRENT_USER = BASE_URL + BASE_USER_SERVICE + "api/current-user";
    public static final String GET_BOOKMARKS = BASE_URL + BASE_POP_SERVICE + "bookmark";
    public static final String GET_ALL_BOOKMARKS = BASE_URL + BASE_USER_SERVICE + "all-bookmarks";
    public static final String SAVE_BOOKMARK = BASE_URL + BASE_USER_SERVICE + "bookmark";
    public static final String GET_BOOKMARK_TYPE = BASE_URL + BASE_USER_SERVICE + "bookmarks/" + "find-by-bookmarktype";
    //    public static final String DELETE_BOOKMARK = BASE_URL + BASE_USER_SERVICE + "user-bookmark";
    public static final String DELETE_BOOKMARK = BASE_URL + BASE_USER_SERVICE + "bookmark";
    public static final String SAVE_USER = BASE_URL + BASE_USER_SERVICE + "api/user";

    public static String USER_BOOKMARK = BASE_URL + BASE_COMMON_DATA + "bookmarked-pop";
    public static String MY_CROP_POP = BASE_URL + BASE_COMMON_DATA + "pop-for-farmer";
    public static String CROP_GROUP = BASE_URL + BASE_COMMON_DATA + "crop-group";
    public static String POP_FOR_GROUP = BASE_URL + BASE_COMMON_DATA + "pop-for-group";
    public static String SOCIAL_MESSAGES = BASE_URL + BASE_COMMON_DATA + "messages";
    public static String SEED_RATE_CROPS = BASE_URL + BASE_COMMON_DATA + "crop-for-seed-rate";
    public static String USER_POP = BASE_URL + BASE_COMMON_DATA + "pop-for-farmer";
    public static String USER_POP_FOR_GROUP = BASE_URL + BASE_COMMON_DATA + "pop-for-group";

    public static String NEWS_FEED = BASE_URL + BASE_NEWS_SERVICE + "news";

    public static final String CULTIVAR_CONTROLLER = BASE_URL + BASE_MASTER_SERVICE + "cultivar";
    public static final String CROP_LIST = BASE_URL + BASE_MASTER_SERVICE + "crop" + LANGUAGE_QUERY_PARAM;
    public static final String CROP_BY_ID = CROP_LIST + "/";
    public static final String CROPS_BY_SEARCH = BASE_URL + BASE_MASTER_SERVICE + "crop" + "/search?cropName=";
    public static final String SOIL_HEALTH = BASE_URL + BASE_MASTER_SERVICE + "soil-health";
    public static final String REGION = BASE_URL + BASE_MASTER_SERVICE + "region" + LANGUAGE_QUERY_PARAM;
    public static final String REGION_BY_TERRITORY = BASE_URL + BASE_MASTER_SERVICE + "find-region-by-territory-id/";
    public static final String TERRITORY = BASE_URL + BASE_MASTER_SERVICE + "territory-with-phonecode" + LANGUAGE_QUERY_PARAM;
    public static final String LANGUAGE = BASE_URL + BASE_MASTER_SERVICE + "languages";
    public static final String COUNTY_BY_REGION = BASE_URL + BASE_MASTER_SERVICE + "county/region";
    public static final String SUB_COUNTY_BY_COUNTY = BASE_URL + BASE_MASTER_SERVICE + "subcounty/county";
    public static final String ALL_SUB_COUNTRY = BASE_URL + BASE_MASTER_SERVICE + "subcounty" + LANGUAGE_QUERY_PARAM;
    public static final String COUNTRY = BASE_URL + BASE_MASTER_SERVICE + "country";
    public static final String VILLAGE_BY_SUB_COUNTY = BASE_URL + BASE_MASTER_SERVICE + "village/subcounty" + LANGUAGE_QUERY_PARAM;
    public static final String ALL_VILLAGES = BASE_URL + BASE_MASTER_SERVICE + "village" + LANGUAGE_QUERY_PARAM;
    public static final String GLOBAL_INDICATOR_DETAIL = BASE_URL + BASE_MASTER_SERVICE + "global-indicator-detail-by-group/";
    public static final String GLOBAL_INDICATORS = BASE_URL + BASE_MASTER_SERVICE + "global-indicator-detail";
    public static final String CROP_ADVISORY = BASE_URL + BASE_MASTER_SERVICE + "crop-advisory";
    public static final String CROP_ADVISORY_BY_LOCATION = BASE_URL + BASE_MASTER_SERVICE + "crop-advisory-by-location" + LANGUAGE_QUERY_PARAM;
    public static final String CROP_ADVISORY_BY_SEARCH = BASE_URL + BASE_MASTER_SERVICE + "crop-advisory/search?searchText=";
    public static final String SUBSCRIBE_PUSH = BASE_URL + BASE_MASTER_SERVICE + "cloud-message/subscribe-topics";
    public static final String NOTIFICATION = BASE_URL + BASE_MASTER_SERVICE + "notification";
    public static final String CROP_ADVISORY_BY_SCOUTING_ID = BASE_URL + BASE_FARMER_SERVICE + "farm-scouting/find-advisory-for-farm-scouting";
    public static final String CROP_STAGE_BY_CROP_NAME = BASE_URL + BASE_MASTER_SERVICE + "crop-stage-by-crop-name";
    public static final String CROP_STAGE_BY_CROP_ID = BASE_URL + BASE_MASTER_SERVICE + "crop-stage-by-crop-id";
    //    public static final String CROP_STAGE = BASE_URL + BASE_MASTER_SERVICE + "crop-stage";
    public static final String CROP_STAGE = GLOBAL_INDICATOR_DETAIL + "growth_stage_ind";
    public static final String SCOUT_CATEGORY = GLOBAL_INDICATOR_DETAIL + "scouting_img_category_ind";
    public static final String PLANT_PART = GLOBAL_INDICATOR_DETAIL + SCOUT_PLANT_PART + LANGUAGE_QUERY_PARAM;
    public static final String CROP_RECOMMENDATION = BASE_URL + BASE_MASTER_SERVICE + "crop-specific-recommendation";
    public static final String TRENDING_TAGS = MESSAGE_SERVICE + "/trending-tag-by-location";
    public static final String GENDER = GLOBAL_INDICATOR_DETAIL + "gender_ind" + LANGUAGE_QUERY_PARAM;
    public static final String DISEASE = BASE_URL + BASE_MASTER_SERVICE + "disease";
    public static final String GROWTH_STAGES_INDICATORS = GLOBAL_INDICATORS_DETAILS_BY_GROUP + "growth_stage_ind" + LANGUAGE_QUERY_PARAM;


    // Fertilizer APIs
    public static final String FERTILIZER_SOURCE_DETAILS = BASE_URL + BASE_MASTER_SERVICE + "fertilizer-source-calculator-details/find-by-user-location";
    public static final String FERTILIZER_FRUIT_CROPS = BASE_URL + BASE_MASTER_SERVICE + "fertilizer-fruit-calculator-details/find-by-user-location";
    public static final String FERTILIZER_REPORT_GENERATE = BASE_URL + BASE_MASTER_SERVICE + "fertilizer-calculator/app/fertilizer-reportGenerater";
    public static final String FERTILIZER_SAVED_REPORTS = BASE_URL + BASE_MASTER_SERVICE + "fertilizer-calculator-report/findByCurrentUser";
    public static final String FERTILIZER_FRUIT_CALCULATOR_DETAILS = BASE_URL + BASE_MASTER_SERVICE + "fertilizer-fruit-calculator-details";
    public static final String FERTILIZER_CROPS_BY_LOCATION = BASE_URL + BASE_MASTER_SERVICE + "fertilizer-calculator/crop-by-location" + LANGUAGE_QUERY_PARAM;

    public static final String FERTILIZER_CROPS_BY_LOCATION_V2 = BASE_URL + BASE_MASTER_SERVICE + "fertilizer-calculatorV2/crop-by-location" + LANGUAGE_QUERY_PARAM;

    //    public static final String PRICE_FEED = BASE_URL + BASE_MARKET_PRICE + "marketPrice";
    public static final String PRICE_FEED = BASE_URL + BASE_MARKET_PRICE + "marketPriceByLocation" + LANGUAGE_QUERY_PARAM;
    public static final String MARKET_DATA = BASE_URL + BASE_MARKET_PRICE + "marketPriceByDay?";

    public static String WEATHER_FEED = BASE_URL + BASE_WEATHER_SERVICE + "weather-by-zipcode";
    public static final String WEATHER_FEED_LAND = BASE_URL + BASE_WEATHER_SERVICE + "weather-for-location/";
    public static final String WEATHER_FEED_LAND_UPDATES = BASE_URL + BASE_MASTER_SERVICE + "weather-latest-updates";

    // get farm scouting url
    public static final String IMAGE_BASE_URL = "https://storage.googleapis.com/fs-cms-media/";

    public static final String CHAT = "https://b25f7e159195.ngrok.io/" + "chatbot";

    //s3 bucket base image
    public static final String S3_IMAGE_BASE_URL = "https://" + BuildConfig.S3_BUCKET_NAME + ".s3.ap-south-1.amazonaws.com/";

    public static final String S3_PROFILE_IMAGE_BASE_URL = "https://" + BuildConfig.S3_BUCKET_NAME + ".s3.ap-south-1.amazonaws.com/";


    public static final String UPLOAD_FARMER_MEDIA = BASE_URL + BASE_STORAGE_SERVICE + "upload-file/" + BuildConfig.S3_BUCKET_NAME + "/true";
    public static final String UPLOAD_FARM_SCOUT_PIC = BASE_URL + BASE_STORAGE + "upload-media";
    // TODO : add farm scout url to image
    public static String UPLOAD_PROFILE_PIC = BASE_URL + BASE_STORAGE + "upload-media";

    public static final String WATER_SOURCE_GROUP = "water_source_ind";
    public static final String ADVISORY_GROUP = "crop_advisory_tag_ind";
    public static final String CULTIVAR_TYPE = "cultivar_type_ind";
    public static final String CULTIVAR_DURATION = "cultivar_maturity_ind";

    @NonNull
    @Contract(pure = true)
    public static String getPOPShareURL(String id) {
        return "Hey there, Checkout this PoP\n" +
                "http://farmsanta.com/pop/" + id + "\n\n" +
                "If you don't have Farmsanta App you can download it from PlayStore\n" +
                "https://play.google.com/store/apps/details?id=com.farmsanta.farmer";
    }
}
