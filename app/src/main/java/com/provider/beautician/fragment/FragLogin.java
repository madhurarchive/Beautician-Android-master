package com.provider.beautician.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.provider.beautician.BuildConfig;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.activity.ActLogin;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.PreferenceConnector;
import com.provider.beautician.model.LoginDataModel;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragLogin extends Fragment implements View.OnClickListener{

    private Context             mContext;
    private View                rootView;
    private boolean             mLoaded;
    private EditText            edtEmail,edtPass;
    private TextView            txtForgot,txtLogin,txtSignUp;
    private String              TAG = FragLogin.class.getSimpleName();
    private ImageView           imgFB,imgGmail;
    private CallbackManager     mCallbackManager; //callbackManager to handle fb login responses
    private static final int    RC_SIGN_IN =0;
    private GoogleSignInClient  mGoogleApiClient;
    private String              token ="";

    public FragLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
        rootView = inflater.inflate(R.layout.frag_login, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded     =   true;
            mContext    =   getActivity();
            token       =   PreferenceConnector.readString(mContext,PreferenceConnector.DEVICE_TOKEN,"");
            initView();
            setUpGoogleSignInClient();
        }
    }

    private void initView() {
            edtEmail        =   rootView.findViewById(R.id.frag_login_editEmail);
            edtPass         =   rootView.findViewById(R.id.frag_login_editPassword);
            txtForgot       =   rootView.findViewById(R.id.frag_login_txtForgot);
            txtLogin        =   rootView.findViewById(R.id.frag_login_txtLogin);
            txtSignUp       =   rootView.findViewById(R.id.frag_login_txtSignUp);
            imgFB           =   rootView.findViewById(R.id.frag_login_imgFB);
            imgGmail        =   rootView.findViewById(R.id.frag_login_imgGmail);

            txtForgot.setOnClickListener(this);
            txtLogin.setOnClickListener(this);
            txtSignUp.setOnClickListener(this);
            imgFB.setOnClickListener(this);
            imgGmail.setOnClickListener(this);
            getHashKey();
            Log.e(TAG,"Token ==== "+token);
    }


    private void getHashKey() {
        PackageInfo info;
        try {
            info = mContext.getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_login_txtForgot:
                ((ActLogin)mContext).switchContent(new FragForgetPwd(),FragForgetPwd.class.getSimpleName(),true);
                break;
            case R.id.frag_login_txtLogin:
                    String email = edtEmail.getText().toString().trim();
                    String pass  = edtPass.getText().toString().trim();
                    if (email.isEmpty()){
                        CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_email,mContext),0,mContext);
                    }else if (!CommonUtils.isEmailValid(email)){
                        CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_valid_email,mContext),0,mContext);
                    }else if (pass.isEmpty()){
                        CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_password,mContext),0,mContext);
                    }else {
                        doLogin(email,pass);
                    }
                break;
            case R.id.frag_login_txtSignUp:
                ((ActLogin)mContext).switchContent(new FragSignUp(),FragSignUp.class.getSimpleName(),true);
                break;
            case R.id.frag_login_imgFB:
                signInWithFacebook();
                break;
            case R.id.frag_login_imgGmail:
                signInWithGoogle();
                break;
        }
    }

    private void doLogin(String email, String pass) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().doLogin(email,pass,token).enqueue(new Callback<JsonObjectResponse<LoginDataModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<LoginDataModel>> call, Response<JsonObjectResponse<LoginDataModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            CommonUtils.setUserDetails(mContext,response.body().getObject());
                            PreferenceConnector.writeBoolean(mContext,PreferenceConnector.KEY_IS_LOGIN,true);
                            Intent intent = new Intent(mContext, ActHome.class);
                            startActivity(intent);
                            ((ActLogin)mContext).finish();
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse<LoginDataModel>> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }

    private void setUpGoogleSignInClient(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = GoogleSignIn.getClient(mContext, gso);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleApiClient.signOut()
                .addOnCompleteListener((ActLogin) mContext, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println( "Logout successfully ");
                    }
                });
    }

    private void signInWithFacebook(){
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList( "email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e(TAG,"facebook output is: "+loginResult);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject me, GraphResponse response) {
                                        Log.e(TAG,"facebook response is: " + me);

                                        if (response.getError() != null) {
                                            // handle error
                                            CommonUtils.showSnackbarWithoutView(CommonUtils.gettingString(R.string.fb_login_error,mContext),getActivity());
                                        } else {
                                            disconnectFromFacebook();
                                            // get email and id of the user
                                            String fbId             = me.optString("id");
                                            String name             = me.optString("name");
                                            String first_name       = me.optString("first_name");
                                            String last_name        = me.optString("last_name");
                                            String email            = me.optString("email");
                                            String profileImageUrl  =   "";
                                            try {
                                                profileImageUrl     = new URL("https://graph.facebook.com/" + fbId + "/picture?width=500&height=500").toString();
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }

                                            Log.e(TAG,"fbId             ===" + fbId);
                                            Log.e(TAG,"name             ===" + name);
                                            Log.e(TAG,"first_name       ===" + first_name);
                                            Log.e(TAG,"last_name        ===" + last_name);
                                            Log.e(TAG,"email            ===" + email);
                                            Log.e(TAG,"profileImageUrl  ===" + profileImageUrl);

                                            callDoSocialLoginApi(name, fbId, Constant.SOCIAL_LOGIN_FACEBOOK, email);
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,first_name,last_name"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e(TAG,"facebook output is: cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e(TAG,"facebook output exception is: " + exception);
                    }
                });
    }


    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            System.out.println( "google login: ");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            System.out.println( "google login: exception == "+task.getException());
            handleSignInResult(task);
        }else {
            System.out.println( "facebook login: ");
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        System.out.println( "handleSignInResult:"+completedTask.isSuccessful());
        if (completedTask.isSuccessful()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = completedTask.getResult();
            //call api login
            assert acct != null;
            System.out.println( "account infomation:"+acct.getDisplayName());
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                updateUI(account);
                String name =   acct.getDisplayName();
                String[] separated = new String[0];
                if (name != null) {
                    separated = name.split(" ");
                }
                String fullName       = "";
                if(separated.length == 2){
                    fullName = separated[0] + " " + separated[1];
                }else{
                    fullName       = separated[0];
                }

                String email            = acct.getEmail();
                String personId         = acct.getId();
                Uri personPhoto         = acct.getPhotoUrl();

                Log.e(TAG, "first_name      === " + fullName);
                Log.e(TAG, "email           === " + email);
                Log.e(TAG, "personId        === " + personId);
                Log.e(TAG, "personPhoto     === " + personPhoto);
                signOut();
                callDoSocialLoginApi(fullName, personId, Constant.SOCIAL_LOGIN_GOOGLE, email);
            } catch (ApiException e) {
                Log.e(TAG, "Google login error code    === "+e.getStatusCode());
                Log.e(TAG, "Google login error Message === "+e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(null);
        }
    }

    private void callDoSocialLoginApi(String fullName, String personId, String socialLogin, String email) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().doSocialLogin(fullName,personId,socialLogin,email,token).enqueue(new Callback<JsonObjectResponse<LoginDataModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<LoginDataModel>> call, Response<JsonObjectResponse<LoginDataModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            CommonUtils.setUserDetails(mContext,response.body().getObject());
                            PreferenceConnector.writeBoolean(mContext,PreferenceConnector.KEY_IS_LOGIN,true);
                            Intent intent = new Intent(mContext, ActHome.class);
                            startActivity(intent);
                            ((ActLogin)mContext).finish();
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse<LoginDataModel>> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account!=null) {
            System.out.println( "account successfully login: ");
        } else {
            System.out.println( "something went wrong ");
        }
    }

}
