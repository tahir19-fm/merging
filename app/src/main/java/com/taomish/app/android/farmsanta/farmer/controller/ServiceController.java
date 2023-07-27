package com.taomish.app.android.farmsanta.farmer.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants;
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.libs.service.ServiceCall;
import com.taomish.app.android.farmsanta.farmer.libs.service.constants.ServiceConstants;
import com.taomish.app.android.farmsanta.farmer.libs.service.enums.RequestMethod;
import com.taomish.app.android.farmsanta.farmer.models.api.ErrorResponse;
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.Calculate;
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.Properties;
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.SeedRateCrop;
import com.taomish.app.android.farmsanta.farmer.models.api.chat.ChatReply;
import com.taomish.app.android.farmsanta.farmer.models.api.chat.Query;
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropCalendar;
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropStageCalendar;
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar;
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryItem;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CultivationType;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting;
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerCropsResponse;
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerFruitDetails;
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerGeneratedReport;
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerSourceDetails;
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.GenerateFertilizerReportPayload;
import com.taomish.app.android.farmsanta.farmer.models.api.gender.GenderItem;
import com.taomish.app.android.farmsanta.farmer.models.api.home.MarketDto;
import com.taomish.app.android.farmsanta.farmer.models.api.home.News;
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price;
import com.taomish.app.android.farmsanta.farmer.models.api.login.User;
import com.taomish.app.android.farmsanta.farmer.models.api.master.CountryMaster;
import com.taomish.app.android.farmsanta.farmer.models.api.master.County;
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster;
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Language;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region;
import com.taomish.app.android.farmsanta.farmer.models.api.master.SubCounty;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory;
import com.taomish.app.android.farmsanta.farmer.models.api.master.UOMType;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Village;
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment;
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message;
import com.taomish.app.android.farmsanta.farmer.models.api.message.MessageLikeDto;
import com.taomish.app.android.farmsanta.farmer.models.api.notification.AdvisoryTag;
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory;
import com.taomish.app.android.farmsanta.farmer.models.api.notification.Subscribe;
import com.taomish.app.android.farmsanta.farmer.models.api.pop.CroppingProcessDto;
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDetailsDTO;
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto;
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage;
import com.taomish.app.android.farmsanta.farmer.models.api.profile.UploadedFile;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.CropRecommendation;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.SoilHealth;
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark;
import com.taomish.app.android.farmsanta.farmer.models.api.user.MobileOtp;
import com.taomish.app.android.farmsanta.farmer.models.api.user.UserToken;
import com.taomish.app.android.farmsanta.farmer.models.api.weather.WeatherData;
import com.taomish.app.android.farmsanta.farmer.utils.JwtUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class ServiceController {
    private static final String TAG = "skt";

    private final AppPrefs appPrefs;
    private final Gson gson;

    public ServiceController(Context context) {
        appPrefs = new AppPrefs(context);
        gson = new GsonBuilder().create();
    }

    /**
     * Adding a trust certificate to avoid SSL handshake exception in lower SDK version clients
     *
     * @param callingActivity Calling activity to be passed. In INATA it is single activity
     */
    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void updateAndroidSecurityProvider(Activity callingActivity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            return;

        try {
            ProviderInstaller.installIfNeeded(callingActivity);
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                 | NoSuchAlgorithmException | KeyManagementException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            //GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
            e.printStackTrace();
        }
    }

    public FarmScouting saveFarmScouting(FarmScouting farmScouting) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.FARM_SCOUTING, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.setBody(gson.toJson(farmScouting));
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, FarmScouting.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteBookMark(String uuid) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.DELETE_BOOKMARK + "/" + uuid, true);
        serviceCall.setRequestMethod(RequestMethod.DELETE);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*Get Bookmarked Pop List API*/
    public ArrayList<BookMark> getBookmarkedPopList() {
        String uRL = URLConstants.GET_BOOKMARKS;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<BookMark>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BookMark> getAllBookMarks() {
        String uRL = URLConstants.GET_ALL_BOOKMARKS;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<BookMark>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BookMark> getBookMarksByType(String type) {
        String uRL = URLConstants.GET_BOOKMARK_TYPE;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("bookMarkType", type);
        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<BookMark>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BookMark saveToBookMark(BookMark bookMark) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SAVE_BOOKMARK, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.setBody(gson.toJson(bookMark));
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, BookMark.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object saveCropCalendar(CropCalendar calendar) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.ADD_CROP_CALENDAR, false);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.setBody(gson.toJson(calendar));
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        String execute = "";
        try {
            execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CropCalendar.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        ErrorResponse response = gson.fromJson(execute, ErrorResponse.class);
        if (response != null) {
            return response;
        }
        return execute;
    }


    public Object updateCropCalendar(CropCalendar calendar) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.CROP_CALENDAR_UPDATE, false);
        serviceCall.setRequestMethod(RequestMethod.PUT);
        serviceCall.setBody(gson.toJson(calendar));
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        String execute = "";
        try {
            execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CropCalendar.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        ErrorResponse response = gson.fromJson(execute, ErrorResponse.class);
        if (response != null) {
            return response;
        }
        return execute;
    }

    public boolean deleteCropCalendar(String uuid) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.CROP_CALENDAR_DELETE + "/" + uuid, true);
        serviceCall.setRequestMethod(RequestMethod.DELETE);
        try {
            serviceCall.execute();
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
            Log.d("Delete Crop","Try");
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("Delete Crop","Catch");
        }
        return false;
    }

    public ArrayList<CropCalendar> getCropCalendarsById() {
        String uRL = URLConstants.CROP_CALENDARS_BY_USER_ID;
        Farmer farmer = DataHolder.getInstance().getSelectedFarmer();
        if (farmer != null) {
            uRL += farmer.getUserId();
        }
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<ArrayList<CropCalendar>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<CropStageCalendar> getCropsStageCalendarListById(String cropId, String id) {
        Farmer farmer = DataHolder.getInstance().getSelectedFarmer();
        String uRL = URLConstants.CROP_STAGE_CALENDAR_LIST;
        if (farmer != null) {
            uRL += farmer.getUserId() + "/" + cropId + "/" + id +
                    URLConstants.LANGUAGE_QUERY_PARAM + appPrefs.getLanguageId();
        }
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {

                Type listType = new TypeToken<ArrayList<CropStageCalendar>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public News[] getNews() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.NEWS_FEED, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();
            News[] response = gson.fromJson(execute, News[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return response;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Price[] getPrice() {
        String uRL = URLConstants.PRICE_FEED + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();
            Price[] response = gson.fromJson(execute, Price[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return response;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public MarketDto getMarketData(String commodityName, String commodityType, String period) {
        String uRL = URLConstants.MARKET_DATA +
                "commodityName=" + commodityName + "&" +
                "commodityType=" + commodityType + "&" +
                "type=" + period + "&" +
                "languageId=" + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String response = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(response, MarketDto.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ChatReply chat(Query query) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.CHAT, true);
        serviceCall.setRequestMethod(RequestMethod.POST);

        String json = gson.toJson(query);

        serviceCall.setBody(json);

        try {
            String execute = serviceCall.execute();
            ChatReply chatReply = gson.fromJson(execute, ChatReply.class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return chatReply;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean saveUserPost(Message message) {
        String uRL = URLConstants.MESSAGE_SERVICE_SAVE + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        String body = gson.toJson(message);
        serviceCall.setBody(body);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Territory[] getTerritories() {
        String uRL = URLConstants.TERRITORY + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Territory[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Language[] getLanguages() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.LANGUAGE, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Language[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public CountryMaster[] getCountries() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.COUNTRY, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CountryMaster[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean saveUserComment(Comment message) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.COMMENT, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        String body = gson.toJson(message);
        serviceCall.setBody(body);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean saveUserEditedComment(Comment message) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.COMMENT_UPDATE + "/" + message.getMessageId(), true);
        serviceCall.setRequestMethod(RequestMethod.PUT);

        String body = gson.toJson(message);
        serviceCall.setBody(body);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteUserComment(String uuid) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.COMMENT_DELETE + "/" + uuid, true);
        serviceCall.setRequestMethod(RequestMethod.DELETE);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Properties calculateSeedRate(Calculate calculate) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SEED_RATE, true);
        serviceCall.setRequestMethod(RequestMethod.POST);

        String body = gson.toJson(calculate);
        serviceCall.setBody(body);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Properties.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CultivationType[] getCultivationType() {
        String url = URLConstants.REFERENCE_DATA + "/"
                + ApiConstants.ReferenceData.CULTIVATION_TYPE;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CultivationType[].class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CultivationType[] getCropVariety() {
        String url = URLConstants.REFERENCE_DATA + "/"
                + ApiConstants.ReferenceData.CROP_VARIETY;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CultivationType[].class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*Get Water Source API*/
    public ArrayList<GlobalIndicatorDTO> getWaterSource() {
        String url = URLConstants.WATER_SOURCE_GLOBAL_INDICATORS + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CultivationType[] getUnitOfMeasure() {
        String url = URLConstants.REFERENCE_DATA + "/"
                + ApiConstants.ReferenceData.UNIT_OF_MEASURE;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CultivationType[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Message> getPosts(String tag) {
        String url = tag != null ? URLConstants.MESSAGE_SERVICE + "/findby-trending-tag" : URLConstants.MESSAGE;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        if (tag != null) {
            serviceCall.addQueryParam("searchTag", tag);
        }
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            if (tag != null && !tag.isEmpty()) {
                Thread.sleep(1000L);
            }
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Message[] messages = gson.fromJson(execute, Message[].class);
                List<Message> list = new ArrayList<>();
                if (messages != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
                    for (Message message : messages) {
                        try {
                            message.createdDate = sdf.parse(message.getCreatedTimestamp());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.addAll(list, messages);
                    Collections.sort(list);
                }

                return messages != null ? new ArrayList<>(list) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Message> getMostLikedPosts() {
        String url = URLConstants.MOST_LIKED_MESSAGES;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Message[] messages = gson.fromJson(execute, Message[].class);
                List<Message> list = new ArrayList<>();
                if (messages != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
                    for (Message message : messages) {
                        try {
                            message.createdDate = sdf.parse(message.getCreatedTimestamp());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.addAll(list, messages);
                }

                return messages != null ? new ArrayList<>(list) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Message> getMostCommentedPosts() {
        String url = URLConstants.MOST_COMMENTED_MESSAGES;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Message[] messages = gson.fromJson(execute, Message[].class);
                List<Message> list = new ArrayList<>();
                if (messages != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
                    for (Message message : messages) {
                        try {
                            message.createdDate = sdf.parse(message.getCreatedTimestamp());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.addAll(list, messages);
                }

                return messages != null ? new ArrayList<>(list) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Message> getMyPosts() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.MY_MESSAGES, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Message[] messages = gson.fromJson(execute, Message[].class);
                List<Message> list = new ArrayList<>();
                if (messages != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
                    for (Message message : messages) {
                        try {
                            message.createdDate = sdf.parse(message.getCreatedTimestamp());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.addAll(list, messages);
                    Collections.sort(list);
                }

                return messages != null ? new ArrayList<>(list) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Message> getCommentedPosts() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.COMMENTED_MESSAGE, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Message[] messages = gson.fromJson(execute, Message[].class);
                List<Message> list = new ArrayList<>();
                if (messages != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
                    for (Message message : messages) {
                        try {
                            message.createdDate = sdf.parse(message.getCreatedTimestamp());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.addAll(list, messages);
                    Collections.sort(list);
                }

                return messages != null ? new ArrayList<>(list) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Message> getLikedPosts() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.LIKED_MESSAGE, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Message[] messages = gson.fromJson(execute, Message[].class);
                List<Message> list = new ArrayList<>();
                if (messages != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
                    for (Message message : messages) {
                        try {
                            message.createdDate = sdf.parse(message.getCreatedTimestamp());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.addAll(list, messages);
                    Collections.sort(list);
                }

                return messages != null ? new ArrayList<>(list) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteMyPost(String uuid) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.MESSAGE_SERVICE + "/" + uuid, true);
        serviceCall.setRequestMethod(RequestMethod.DELETE);

        try {
            serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }


    public SeedRateCrop[] getSeedRateCropList() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SEED_RATE_CROPS, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            SeedRateCrop[] crops = gson.fromJson(execute, SeedRateCrop[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return crops;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String uploadProfilePic(File file) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.UPLOAD_FARMER_MEDIA, true);
        serviceCall.setRequestMethod(RequestMethod.MULTI);

        serviceCall.addMultipart("file", file);
        serviceCall.addParams("name", "farmer_profile_" + System.currentTimeMillis());

        try {
            String execute = serviceCall.execute();
            UploadedFile res = gson.fromJson(execute, UploadedFile.class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return res.getFileName();
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[] uploadUserDoc(File file, String fileName) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.UPLOAD_PROFILE_PIC, true);
        serviceCall.setRequestMethod(RequestMethod.MULTI);

        Gson gson = new Gson();

        serviceCall.addMultipart("file", file);
        serviceCall.addParams("name", "farmer_doc_" + fileName + System.currentTimeMillis());

        try {
            String execute = serviceCall.execute();
            String[] res = gson.fromJson(execute, String[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public MobileOtp getOTP(String phone) {
        String url = URLConstants.GENERATE_OTP + "/"
                + ApiConstants.UserType.FARMER + "/"
                + phone;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                //return true;
                return gson.fromJson(execute, MobileOtp.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MobileOtp data = new MobileOtp();
        data.setResponseCode(serviceCall.getResponseCode());
        return data;
    }

    public boolean getResetOTP(String phone) {
        String url = URLConstants.SEND_RESET_OTP + "/"
                + ApiConstants.UserType.FARMER + "/"
                + phone;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public UserToken authenticateUser(User user) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(
                URLConstants.AUTHENTICATE + "/"
                        + ApiConstants.UserType.FARMER,
                false);
        serviceCall.setRequestMethod(RequestMethod.POST);

        String body = gson.toJson(user);
        serviceCall.setBody(body);
        String execute;
        ErrorResponse errorResponse = null;

        try {
            execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, UserToken.class);
            }
            errorResponse = gson.fromJson(execute, ErrorResponse.class);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        UserToken userToken = new UserToken();
        if (errorResponse != null) {
            userToken.setErrorMessage(errorResponse.getMessage());
        }
        userToken.setResponseCode(serviceCall.getResponseCode());
        return userToken;
    }

    public List<String> getUserGroups() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.FARMER_GROUPS, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<String>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public UploadedFile uploadFarmScoutImage(File file) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.UPLOAD_FARMER_MEDIA, true);

        serviceCall.setRequestMethod(RequestMethod.MULTI);
        serviceCall.addMultipart("file", file);
        serviceCall.addParams("name", file.getName());
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, UploadedFile.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*Get list Farm scouting(image names) by land Id */
    public FarmScouting[] getFarmScouting(String landId, String farmerId) {
        String url = URLConstants.FARM_SCOUTING;
        if (landId != null && landId.length() > 0) {
            url = URLConstants.FARM_SCOUTING_BY_LAND + "/" + landId;
        } else if (farmerId != null && farmerId.length() > 0) {
            url = URLConstants.FARM_SCOUTING_BY_FARMER + "/" + farmerId;
        }

        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, FarmScouting[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public FarmScouting getFarmScouting(String uuid) {
        String url = URLConstants.FARM_SCOUTING + "farm-scouting/" + uuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, FarmScouting.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public PopDto[] getPopList() {
        String uRL = URLConstants.POP_SERVICE_USER + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, PopDto[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Farmer saveFarmer(Farmer farmer) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.FARMER_PROFILE_SAVE, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.setBody(gson.toJson(farmer));
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Farmer.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserToken createFarmerProfile(Farmer farmer) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.FARMER_PROFILE + "/farmer-profile-save", false);
        serviceCall.setRequestMethod(RequestMethod.POST);
        String jsonBody = gson.toJson(farmer);
        serviceCall.setBody(jsonBody);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, UserToken.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Get crop stages by crop uuid
    public CropStage[] getCropStageByCropId(String uuid) {
        String url = URLConstants.CROP_STAGE_BY_CROP_ID + "/" + uuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CropStage[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Get crop stages by crop uuid
    public CropStage[] getAllCropStages() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.CROP_STAGE, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CropStage[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<GlobalIndicatorDTO> getPlantPart() {
        String uRL = URLConstants.PLANT_PART + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BookMark deleteBookMarkedPop(String bookMarkId) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.POP_SERVICE + "/" + bookMarkId, true);
        serviceCall.setRequestMethod(RequestMethod.DELETE);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, BookMark.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<GlobalIndicatorDTO> getGrowthStages() {
        String uRL = URLConstants.GROWTH_STAGES_INDICATORS + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type typeToken = new TypeToken<ArrayList<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, typeToken);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<SoilHealth> getSoilHealth() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SOIL_HEALTH, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                SoilHealth[] soilHealths = gson.fromJson(execute, SoilHealth[].class);
                ArrayList<SoilHealth> result = null;
                if (soilHealths != null) {
                    result = new ArrayList<>(Arrays.asList(soilHealths));
                }
                return result;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> uploadLandDoc(ArrayList<File> files) {
        ArrayList<String> uploadedFileList = new ArrayList<>();
        for (File file : files) {
            ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.UPLOAD_FARMER_MEDIA, true);
            serviceCall.setRequestMethod(RequestMethod.MULTI);

            serviceCall.addMultipart("file", file);
            serviceCall.addParams("name", "farmer_land_doc_" + System.currentTimeMillis());

            try {
                String execute = serviceCall.execute();
                UploadedFile res = gson.fromJson(execute, UploadedFile.class);

                if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                        || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                    uploadedFileList.add(res.getFileName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (uploadedFileList.size() > 0) {
            return uploadedFileList;
        } else {
            return null;
        }
    }

    public ArrayList<Cultivar> getCultivar() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.CULTIVAR_CONTROLLER, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            Type listType = new TypeToken<List<Cultivar>>() {
            }.getType();
            ArrayList<Cultivar> response = gson.fromJson(execute, listType);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return response;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*Get Water source from global indicator*/
    public ArrayList<GlobalIndicatorDTO> getCultivarType() {
        String uRL = URLConstants.GLOBAL_INDICATOR_DETAIL + URLConstants.CULTIVAR_TYPE;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*Get all from global indicator*/
    public ArrayList<GlobalIndicatorDTO> getAllGlobalIndicator() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.GLOBAL_INDICATORS, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*Get Water source from global indicator*/
    public ArrayList<GlobalIndicatorDTO> getCultivarDuration() {
        String uRL = URLConstants.GLOBAL_INDICATOR_DETAIL + URLConstants.CULTIVAR_DURATION;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<GlobalIndicatorDTO> getCropsGlobalIndicators() {
        String uRL = URLConstants.CROP_DIVISION_GLOBAL_INDICATORS + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<GlobalIndicatorDTO> getEducationLevels() {
        String uRL = URLConstants.EDUCATION_LEVELS_GLOBAL_INDICATORS + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<UOMType> getLandUnitsOfMeasurements() {
        String uRL = URLConstants.LAND_UOM_GLOBAL_INDICATORS + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<UOMType>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<CropMaster> getAllCrops() {
        String uRL = URLConstants.CROP_LIST + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<CropMaster>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public PopDetailsDTO getPopDetailsResponse(String uuid) {
        String uRL = URLConstants.POP_SECTIONS + uuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, PopDetailsDTO.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<CroppingProcessDto> getCroppingProcessById(String uuid) {
        String uRL = URLConstants.CROPPING_PROCESS + uuid + URLConstants.LANGUAGE_QUERY_PARAM + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<ArrayList<CroppingProcessDto>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CropMaster getCropById(String uuid) {
        String uRL = URLConstants.CROP_BY_ID + uuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<CropMaster>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<CropMaster> getSearchedCrops(String keyword) {
        String uRL = URLConstants.CROPS_BY_SEARCH + keyword;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<CropMaster>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<CropAdvisory> getSearchedAdvisories(String keyword) {
        String uRL = URLConstants.CROP_ADVISORY_BY_SEARCH + keyword;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<CropAdvisory>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public FarmScouting[] getSearchedScouting(String keyword) {
        String uRL = URLConstants.FARM_SCOUTING_BY_SEARCH + keyword;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<FarmScouting>>() {
                }.getType();
                return gson.fromJson(execute, FarmScouting[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<PopDto> getSearchedPops(String keyword) {
        String uRL = URLConstants.POP_SEARCH + keyword;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<PopDto>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Message> getSearchedTalks(String keyword) {
        String uRL = URLConstants.SEARCH_MESSAGES;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("searchText", keyword);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<Message>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Region[] getRegions() {
        String uRL = URLConstants.REGION + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();
            Region[] regions = gson.fromJson(execute, Region[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return regions;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Region[] getRegionsByTerritory(String uuid) {
        String uRL = URLConstants.REGION_BY_TERRITORY + uuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            Region[] regions = gson.fromJson(execute, Region[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return regions;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public County[] getCountiesByRegion(String regionUuid) {
        String uRL = URLConstants.COUNTY_BY_REGION + "?languageId=" + appPrefs.getLanguageId() + "&" + "regionid=" + regionUuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);


        try {
            String execute = serviceCall.execute();
            County[] counties = gson.fromJson(execute, County[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return counties;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public SubCounty[] getSubCountiesByCounty(String countyId) {
        String uRL = URLConstants.SUB_COUNTY_BY_COUNTY + "?countyid=" + countyId + "&" + "languageId=" + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();
            SubCounty[] subCounties = gson.fromJson(execute, SubCounty[].class);

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return subCounties;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public CropAdvisory[] getCropAdvisories() {
        String uRL = URLConstants.CROP_ADVISORY_BY_LOCATION + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CropAdvisory[].class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CropAdvisory getCropAdvisory(String uuid, String languageId) {
        String url = URLConstants.CROP_ADVISORY + "/" + uuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", languageId);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CropAdvisory.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public AdvisoryTag[] getAdvisoryTags() {
        String url = URLConstants.GLOBAL_INDICATOR_DETAIL + URLConstants.ADVISORY_GROUP;
        ServiceCall serviceCall = createCommonHeaderServiceCall(url, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, AdvisoryTag[].class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*Example : Get Weather API URL: https://api-dev.farmsanta.com/weather-service/weather-by-zipcode/IN/577205*/
    public WeatherData getAllWeatherInfo(String lat, String lon) {
        String uRL = URLConstants.WEATHER_FEED_LAND_UPDATES;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        serviceCall.addQueryParam("latitude", lat);
        serviceCall.addQueryParam("longitude", lon);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, WeatherData.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Farmer getCurrentFarmer() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.CURRENT_FARMER, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Farmer.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Farmer updateFarmer(Farmer farmer) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.UPDATE_FARMER, true);
        serviceCall.setRequestMethod(RequestMethod.PUT);
        serviceCall.setBody(gson.toJson(farmer));
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Farmer.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean subscribeToPush(Subscribe subscribe) {
        ArrayList<String> topics = new ArrayList<String>();
        topics.add("weather");
        topics.add("crop-calender");
        topics.add("crop-advisory");
        topics.add("pop");
        subscribe.setTopics(topics);
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SUBSCRIBE_PUSH, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.setBody(gson.toJson(subscribe));

        try {
            serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<CropRecommendation> getCropSpecificRecommendations() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.CROP_RECOMMENDATION, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                CropRecommendation[] cropSpecificRecommendations = gson.fromJson(execute, CropRecommendation[].class);
                ArrayList<CropRecommendation> result = null;
                if (cropSpecificRecommendations != null) {
                    result = new ArrayList<>(Arrays.asList(cropSpecificRecommendations));
                }
                return result;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean saveMessageLike(MessageLikeDto message, String languageId) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SAVE_MESSAGE_LIKE, true);
        serviceCall.setRequestMethod(RequestMethod.POST);

        String body = gson.toJson(message);
        serviceCall.setBody(body);
        serviceCall.addQueryParam("languageId", languageId);

        try {
            serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean saveMessageDisLike(String uuid, String languageId) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SAVE_MESSAGE_DISLIKE + "/" + uuid, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.addQueryParam("languageId", languageId);

        try {
            serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return true;
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Advisory getAdvisory(String scoutingUUID) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.NOTIFICATION, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Advisory[] advisories = gson.fromJson(execute, Advisory[].class);

                for (Advisory advisory : advisories) {
                    if (advisory != null && advisory.getImages() != null) {
                        for (com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Image image : advisory.getImages()) {
                            if (image.getScoutingID() != null && image.getScoutingID().equals(scoutingUUID)) {
                                return advisory;
                            }
                        }
                    }
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Advisory[] getAdvisoryById(String notifUuid) {
        String uRL = URLConstants.CROP_ADVISORY_BY_SCOUTING_ID + "/" + notifUuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, false);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();
            Log.d("SCOUTBUG", "getAdvisoryById: data is "+execute);
            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Advisory[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ServiceCall createCommonHeaderServiceCall(String uRL, boolean addToken) throws SecurityException {
        String token = appPrefs.getUserToken();

        ServiceCall serviceCall = new ServiceCall(uRL)
                .addHeader(
                        ServiceConstants.HeaderKeys.contentType,
                        ServiceConstants.HeaderValues.applicationJson);
        if (token != null && token.length() > 0 && addToken) {
            boolean isTokenValid = new JwtUtil().isTokenValid(token);
            if (!isTokenValid) {
                UserToken userToken = getNewAccessToken();
                if (userToken != null && userToken.getToken() != null) {
                    appPrefs.setUserToken(userToken.getToken());
                    appPrefs.setRefreshToken(userToken.getRefreshToken());
                    serviceCall.addHeader(ServiceConstants.HeaderKeys.authorization, userToken.getToken());
                } else {
                    throw new SecurityException("Could not get a valid token. You might have to logout and login again");
                }
            } else {
                serviceCall.addHeader(ServiceConstants.HeaderKeys.authorization, token);//"Bearer " +
            }
        }

        return serviceCall;
    }

    private UserToken getNewAccessToken() {
        String refreshToken = appPrefs.getRefreshToken();
        ServiceCall call = new ServiceCall(URLConstants.REFRESH_TOKEN + refreshToken);
        call.addHeader(ServiceConstants.HeaderKeys.contentType, ServiceConstants.HeaderValues.applicationJson);
        try {
            String execute = call.execute();
            if (call.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || call.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, UserToken.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CategoryItem[] getScoutingCategory() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.SCOUT_CATEGORY, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, CategoryItem[].class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[] getTrendingTags() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.TRENDING_TAGS, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, String[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GenderItem[] getGenderData() {
        String uRL = URLConstants.GENDER + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String response = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return new Gson().fromJson(response, GenderItem[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public FertilizerSourceDetails[] getFertilizerSourceDetails() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.FERTILIZER_SOURCE_DETAILS, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String response = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return new Gson().fromJson(response, FertilizerSourceDetails[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public FertilizerGeneratedReport getFertilizerReport(GenerateFertilizerReportPayload payload) {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.FERTILIZER_REPORT_GENERATE, true);
        serviceCall.setRequestMethod(RequestMethod.POST);
        serviceCall.setBody(gson.toJson(payload));
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        try {
            String response = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return new Gson().fromJson(response, FertilizerGeneratedReport.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public FertilizerGeneratedReport[] getSavedFertilizerReports() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.FERTILIZER_SAVED_REPORTS, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        try {
            String response = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return new Gson().fromJson(response, FertilizerGeneratedReport[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Disease[] getAllCropDiseases() {
        ServiceCall serviceCall = createCommonHeaderServiceCall(URLConstants.DISEASE, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());
        try {
            String response = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return new Gson().fromJson(response, Disease[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public FertilizerFruitDetails[] getFertilizerFruitDetails() {
        String uRL = URLConstants.FERTILIZER_FRUIT_CROPS;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", appPrefs.getLanguageId());

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, FertilizerFruitDetails[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public FertilizerCropsResponse getFertilizerCropsByLocation() {
        String uRL = URLConstants.FERTILIZER_CROPS_BY_LOCATION_V2 + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, FertilizerCropsResponse.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Village[] getVillagesBySubCounty(String id) {
        String uRL = URLConstants.VILLAGE_BY_SUB_COUNTY + appPrefs.getLanguageId() + "&subcountyid=" + id;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Village[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public SubCounty[] getAllSubCounties() {
        String uRL = URLConstants.ALL_SUB_COUNTRY + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, SubCounty[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Village[] getAllVillages() {
        String uRL = URLConstants.ALL_VILLAGES + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                return gson.fromJson(execute, Village[].class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<GlobalIndicatorDTO> getCropCalendarStagesIndicators() {
        String uRL = URLConstants.CROP_CALENDAR_STAGES_INDICATORS + appPrefs.getLanguageId();
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {
                Type listType = new TypeToken<List<GlobalIndicatorDTO>>() {
                }.getType();
                return gson.fromJson(execute, listType);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Message getMessageById(String uuid, String languageId) {
        String uRL = URLConstants.MESSAGE_BY_ID + uuid;
        ServiceCall serviceCall = createCommonHeaderServiceCall(uRL, true);
        serviceCall.setRequestMethod(RequestMethod.GET);
        serviceCall.addQueryParam("languageId", languageId);

        try {
            String execute = serviceCall.execute();

            if (serviceCall.getResponseCode() == HttpsURLConnection.HTTP_OK
                    || serviceCall.getResponseCode() == HttpsURLConnection.HTTP_CREATED) {

                return gson.fromJson(execute, Message.class);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

}
