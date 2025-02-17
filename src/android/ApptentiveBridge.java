package com.apptentive.cordova;

import android.provider.Settings;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.Log;
import com.apptentive.android.sdk.module.messagecenter.UnreadMessagesListener;
import com.apptentive.android.sdk.module.rating.impl.AmazonAppstoreRatingProvider;
import com.apptentive.android.sdk.module.survey.OnSurveyFinishedListener;
import com.apptentive.cordova.JsonHelper;

import java.io.IOException;
import java.util.Map;
import java.util.Iterator;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApptentiveBridge extends CordovaPlugin {

    public static final String TAG = "ApptentiveBridge";

    private static final String ACTION_DEVICE_READY = "deviceReady";
    private static final String ACTION_RESUME = "resume";
    private static final String ACTION_PAUSE = "pause";
    private static final String ACTION_ADD_AMAZON_SNS_PUSH_INTEGRATION = "addAmazonSnsPushIntegration";
    private static final String ACTION_ADD_CUSTOM_DEVICE_DATA = "addCustomDeviceData";
    private static final String ACTION_ADD_CUSTOM_PERSON_DATA = "addCustomPersonData";
    private static final String ACTION_ADD_INTEGRATION = "addIntegration";
    private static final String ACTION_ADD_PARSE_PUSH_INTEGRATION = "addParsePushIntegration";
    private static final String ACTION_ADD_URBAN_AIRSHIP_INTEGRATION = "addUrbanAirshipPushIntegration";
    private static final String ACTION_ENGAGE = "engage";
    private static final String ACTION_GET_UNREAD_MESSAGE_COUNT = "getUnreadMessageCount";
    private static final String ACTION_HANDLE_OPENED_PUSH_NOTIFICATION = "handleOpenedPushNotification";
    private static final String ACTION_IS_APPTENTIVE_PUSH_NOTIFICATION = "isApptentivePushNotification";
    private static final String ACTION_PUT_RATING_PROVIDER_ARG = "putRatingProviderArg";
    private static final String ACTION_REMOVE_CUSTOM_DEVICE_DATA = "removeCustomDeviceData";
    private static final String ACTION_REMOVE_CUSTOM_PERSON_DATA = "removeCustomPersonData";
    private static final String ACTION_SEND_ATTACHMENT_FILE_URI = "sendAttachmentFileUri";
    private static final String ACTION_SEND_ATTACHMENT_FILE = "sendAttachmentFile";
    private static final String ACTION_SEND_ATTACHMENT_TEXT = "sendAttachmentText";
    private static final String ACTION_SET_INITIAL_USER_EMAIL = "setInitialUserEmail";
    private static final String ACTION_SET_INITIAL_USER_NAME = "setInitialUserName";
    private static final String ACTION_SET_UNREAD_MESSAGE_LISTENER = "setUnreadMessagesListener";
    private static final String ACTION_SET_ON_SURVEY_FINISHED_LISTENER = "setOnSurveyFinishedListener";
    private static final String ACTION_SET_PENDING_PUSH_NOTIFICATION = "setPendingPushNotification";
    private static final String ACTION_SET_RATING_PROVIDER = "setRatingProvider";
    private static final String ACTION_SHOW_MESSAGE_CENTER = "showMessageCenter";
    private static final String ACTION_WILL_SHOW_INTERACTION = "willShowInteraction";

    /**
    * Constructor.
    */
    public ApptentiveBridge() {}

    public boolean execute(final String action, JSONArray args, final CallbackContext callbackContext) throws JSONException  {
        Log.v("Executing action: %s", action);

        if( action.equals(ACTION_DEVICE_READY) ) {
            Apptentive.onStart(cordova.getActivity());
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_PAUSE) ) {
            Apptentive.onStop(cordova.getActivity());
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_RESUME) ) {
            Apptentive.onStart(cordova.getActivity());
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_SHOW_MESSAGE_CENTER) ) {
            if( args.length() > 0 ) {
                Map config = JsonHelper.toMap(args.getJSONObject(0));
                Apptentive.showMessageCenter(cordova.getActivity(), config);
            } else {
                Apptentive.showMessageCenter(cordova.getActivity());
            }
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_ADD_AMAZON_SNS_PUSH_INTEGRATION) ) {
            String regId = args.getString(0);
            Apptentive.addAmazonSnsPushIntegration(cordova.getActivity(), regId);
            return true;

        } else if( action.equals(ACTION_ADD_CUSTOM_DEVICE_DATA) ) {
            String key = args.getString(0);
            String value = args.getString(1);
            Apptentive.addCustomDeviceData(cordova.getActivity(), key, value);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_ADD_CUSTOM_PERSON_DATA) ) {
            String key = args.getString(0);
            String value = args.getString(1);
            Apptentive.addCustomPersonData(cordova.getActivity(), key, value);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_ADD_INTEGRATION) ) {
            String integrationId = args.getString(0);
            Map config = JsonHelper.toMap(args.getJSONObject(1));
            Apptentive.addIntegration(cordova.getActivity(), integrationId, config);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_ADD_PARSE_PUSH_INTEGRATION) ) {
            String deviceToken = args.getString(0);
            Apptentive.addParsePushIntegration(cordova.getActivity(), deviceToken);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_ADD_URBAN_AIRSHIP_INTEGRATION) ) {
            String apid = args.getString(0);
            Apptentive.addUrbanAirshipPushIntegration(cordova.getActivity(), apid);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_ENGAGE) ) {
            String eventId = args.getString(0);
            boolean shown = false;
            if( args.length() > 1 ) {
                Map customData = JsonHelper.toMap(args.getJSONObject(1));
                shown = Apptentive.engage(cordova.getActivity(), eventId, customData);
            } else {
                shown = Apptentive.engage(cordova.getActivity(), eventId);
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, shown);
            callbackContext.sendPluginResult(result);
            return true;

        } else if( action.equals(ACTION_GET_UNREAD_MESSAGE_COUNT) ) {
            callbackContext.success( Apptentive.getUnreadMessageCount(cordova.getActivity()) );
            return true;

        } else if( action.equals(ACTION_HANDLE_OPENED_PUSH_NOTIFICATION) ) {
            Apptentive.handleOpenedPushNotification(cordova.getActivity());
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_PUT_RATING_PROVIDER_ARG) ) {
            String key = args.getString(0);
            String value = args.getString(1);
            Apptentive.putRatingProviderArg(key, value);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_REMOVE_CUSTOM_DEVICE_DATA) ) {
            String key = args.getString(0);
            Apptentive.removeCustomDeviceData(cordova.getActivity(), key);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_REMOVE_CUSTOM_PERSON_DATA) ) {
            String key = args.getString(0);
            Apptentive.removeCustomPersonData(cordova.getActivity(), key);
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_SEND_ATTACHMENT_FILE_URI) ) {
            String uri = args.getString(0);
            Apptentive.sendAttachmentFile( cordova.getActivity(), uri );
            return true;

        } else if( action.equals(ACTION_SEND_ATTACHMENT_FILE) ) {
            byte[] content = args.getString(0).getBytes();
            String mimeType = args.getString(1);
            Apptentive.sendAttachmentFile( cordova.getActivity(), content, mimeType );
            return true;

        } else if( action.equals(ACTION_SEND_ATTACHMENT_TEXT) ) {
            String text = args.getString(0);
            Apptentive.sendAttachmentText(cordova.getActivity(), text );
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_SET_INITIAL_USER_EMAIL) ) {
            String email = args.getString(0);
            Apptentive.setInitialUserEmail(cordova.getActivity(), email );
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_SET_INITIAL_USER_NAME) ) {
            String name = args.getString(0);
            Apptentive.setInitialUserName(cordova.getActivity(), name );
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_SET_RATING_PROVIDER) ) {
            String providerName = args.getString(0);
            if (providerName.equals("amazon")) {
                Apptentive.setRatingProvider(new AmazonAppstoreRatingProvider());
            }
            callbackContext.success();
            return true;

        } else if( action.equals(ACTION_WILL_SHOW_INTERACTION) ) {
            // TODO change this to run on different thread to avoid Plugin Blocking Warnings
            String eventName = args.getString(0);
            boolean willShow = Apptentive.willShowInteraction(cordova.getActivity(), eventName);
            PluginResult result = new PluginResult(PluginResult.Status.OK, willShow);
            callbackContext.sendPluginResult(result);
            return true;

        } else if( action.equals(ACTION_SET_UNREAD_MESSAGE_LISTENER) ) {
            UnreadMessagesListener listener = new UnreadMessagesListener() {
                public void onUnreadMessageCountChanged( int unreadMessages ) {
                    PluginResult result = new PluginResult(PluginResult.Status.OK, unreadMessages);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult( result );
                }
            };
            Apptentive.setUnreadMessagesListener( listener );
            return true;

        } else if( action.equals(ACTION_SET_ON_SURVEY_FINISHED_LISTENER) ) {
            OnSurveyFinishedListener listener = new OnSurveyFinishedListener() {
                public void onSurveyFinished( boolean finished ) {
                    int completed = finished ? 1 : 0;
                    PluginResult result = new PluginResult(PluginResult.Status.OK, completed);
                    // NOTE: result.setKeepCallback(true) allows the callback to be used more than one time
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult( result );
                }
            };
            Apptentive.setOnSurveyFinishedListener( listener );
            return true;
        }

        Log.w("Unhandled action in ApptentiveBridge: %s", action);

        callbackContext.error("Unhandled action in ApptentiveBridge: "+action);
        return false;
    }
}
