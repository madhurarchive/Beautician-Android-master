package com.provider.beautician.constant;

import com.provider.beautician.BuildConfig;

/**
 * Created by archive_infotech on 9/20/18.
 */

public class Constant {
    public static final String API_BASE_URL                 =   "http://archiveinfotechdevelopers.com/snip/ProvidersApi/";

    public static final String PACKAGE_NAME                 =   "com.metime.beautician";
    public static final String SHARED_FOLDER                =   "shared";
    public static final String SHARED_PROVIDER_AUTHORITY    =   BuildConfig.APPLICATION_ID + ".myfileprovider";

    //Dynamic Permission Request Code
    public static final int GALLERY_REQUEST_CODE            =   1965;
    public static final int CAPTURE_IMAGE_REQUEST_CODE      =   1966;

    // Fragment TAG
    public static final String FRAG_HOME                    =   "FRAG_HOME";
    public static final String RESPONSE_RESULT_YES          =   "YES";

    public static final String DEVICE_TYPE_ANDROID          =   "Android";
    public static final int FCM_UPDATE_TIMER_INTERVAL       =   24*60*60;

    //LANGUAGE CODE
    public static final String LANGUAGE_CODE_EN             =   "en";
    public static final String LANGUAGE_CODE_AR             =   "ar";

    //FCM Push Notification Constants
    public static final String FCM_CHANNEL_ID               =   "channel-01";
    public static final String FCM_CHANNEL_NAME             =   "Skeleton Channel";
    public static final String APP_NOTIFICATION_RECEIVE     =   "APP_NOTIFICATION_RECEIVE";
    public static final String APP_NOTIFICATION_COMES       =   "APP_NOTIFICATION_COMES";

    public static final String FILE_NAME_PREFIX             =   "BEAUTICIAN_";
    public static final String FILE_NAME_EXTENSION          =   ".jpg";

    //Dynamic Permission Request Code
    public static final int MY_CAMERA_PERMISSION_CODE       =   101;
    public static final float GALLERY_IMAGE_MAX_WIDTH       =    1024.0f;
    public static final float GALLERY_IMAGE_MAX_HEIGHT      =    768.0f;
    public static final String SOCIAL_LOGIN_GOOGLE          =   "GMAIL";
    public static final String SOCIAL_LOGIN_FACEBOOK        =   "FB";
    public static final String PRIVACY_POLICY_URL           =   API_BASE_URL + "webviewcontent/en/privacy_policy";
    public static final String TERMS_N_CONDITION_URL        =   API_BASE_URL + "webviewcontent/en/terms_and_conditions";
}

