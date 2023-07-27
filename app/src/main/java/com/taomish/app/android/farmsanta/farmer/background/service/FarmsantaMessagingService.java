package com.taomish.app.android.farmsanta.farmer.background.service;

import static com.taomish.app.android.farmsanta.farmer.constants.AppConstants.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity;
import com.taomish.app.android.farmsanta.farmer.background.SubscribeToPushTask;
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants;
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer;
import com.taomish.app.android.farmsanta.farmer.models.api.notification.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FarmsantaMessagingService extends FirebaseMessagingService {
    private static final String UNKNOWN = "Unknown";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);

        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        /*LocalBroadcastManager.getInstance(this)
                .sendBroadcast(new Intent(AppConstants.DataTransferConstants.KEY_NOTIFICATION_RECEIVED));*/

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> messageData = remoteMessage.getData();
            boolean containsType = messageData.containsKey(ApiConstants.Notification.TYPE);
            boolean containsUUID = messageData.containsKey(ApiConstants.Notification.UUID);
            Log.d("MessagingService", "Message data payload: " + messageData);

            if (false) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                String message = messageData.get("message");
                String type = containsType ? messageData.get(ApiConstants.Notification.TYPE) : UNKNOWN;
                String uuid = containsUUID ? messageData.get(ApiConstants.Notification.UUID) : UNKNOWN;
                String languageId = messageData.get(ApiConstants.Notification.LANGUAGE_ID);
                // Check if message contains a notification payload.
                if (remoteMessage.getNotification() != null) {
                    message = remoteMessage.getNotification().getBody();
                    Log.d(AppConstants.TAG, "Message: " + message + "type:" + type + "uuid: " + uuid);
                }
                if (DataHolder.getInstance().getSelectedFarmer() != null) {
                    handleNow(message, type, uuid, languageId);
                }
            }
        }
        super.onMessageReceived(remoteMessage);
    }

    private void sendRegistrationToServer(String token) {
        AppPrefs prefs = new AppPrefs(this);
        prefs.setFirebaseToken(token);
        Farmer profile = DataHolder.getInstance().getSelectedFarmer();
        if (profile != null && profile.getFarmerGroup() != null) {
            Subscribe subscribe = new Subscribe();
            List<String> tokenList = new ArrayList<>();
            tokenList.add(token);

            subscribe.setTokens(tokenList);

            SubscribeToPushTask task = new SubscribeToPushTask(subscribe);
            task.setContext(this);
            task.setOnTaskCompletionListener(new OnTaskCompletionListener() {
                @Override
                public void onTaskSuccess(Object data) {
                    prefs.setIsTokenSent((boolean) data);
                }

                @Override
                public void onTaskFailure(String reason, String errorMessage) {
                    prefs.setIsTokenSent(false);
                }
            });
            task.setShowLoading(false);
            task.execute();
        }
    }

    /**
     * Schedule async work using WorkManager.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(FirebaseWorker.class)
                .build();
        WorkManager.getInstance(this).beginWith(work).enqueue();
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     *
     * @param uuid    UUID of the notification type
     * @param type    type of notification received
     * @param message Message from firebase notification
     */
    private void handleNow(String message, String type, String uuid, String languageId) {
        Log.d(AppConstants.TAG, "Short lived task is done.");
        sendNotification(message, type, uuid, languageId);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String type, String uuid, String languageId) {
        Log.d(TAG, "Message body = " + messageBody);
        if (messageBody == null || messageBody.length() == 0)
            messageBody = "You have a new advisory from farmsanta";

        int destination = getDestination(type);
        Bundle arguments = getArguments(type, uuid, languageId);

        PendingIntent pendingIntent = new NavDeepLinkBuilder(this)
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(destination, arguments)
                .createPendingIntent();

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_crop_notif)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Farmsanta",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        int id = (int) (System.currentTimeMillis() / 10000);
        notificationManager.notify(id, builder.build());
    }


    private int getDestination(String type) {
        if (!new AppPrefs(this).isUserProfileCompleted()) return R.id.navigation_introduction;
        int destination = R.id.navigation_home;
        if (type.equalsIgnoreCase(ApiConstants.NotificationType.FARM_TALK)) {
            Log.d("MessagingService", "Type : FARM_TALK");
            destination = R.id.navigation_social_post_details;
        } else if (type.equalsIgnoreCase(ApiConstants.NotificationType.CROP_ADVISORY)) {
            Log.d("MessagingService", "Type : CROP_ADVISORY");
            destination = R.id.viewAdvisoryFragment;
        } else if (type.equalsIgnoreCase(ApiConstants.NotificationType.SCOUTING)) {
            Log.d("MessagingService", "Type : SCOUTING");
            destination = R.id.navigation_farm_scout_details_home;
        }
        return destination;
    }

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
    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty");
    }
}
