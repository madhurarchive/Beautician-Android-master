package com.provider.beautician.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.activity.ActLogin;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.ImageUploadUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.listners.AlertDialogListener;
import com.provider.beautician.listners.ImageSelectDialogListener;
import com.provider.beautician.model.LoginDataModel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragEditProfile extends Fragment implements View.OnClickListener {
    private Context mContext;
    private View rootView;
    private boolean mLoaded;
    private ImageView imgBack;
    private TextView txtHeader, txtSave;
    private LinearLayout layoutNotification, layoutImage;
    private ImageView imgUser, imgAdd;
    private File uploadImageFile = null;
    private Uri picUri;
    private String TAG = FragEditProfile.class.getSimpleName(), userId = "";
    private EditText edtFName, edtLName, edtMobile, edtEmail, edtCurrentPass, edtNewPass, edtVerifyPass;

    public FragEditProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_edit_profile, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded = true;
            mContext = getActivity();
            userId = CommonUtils.getUserId(mContext);
            initView();
            setData();
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "", true);
        }
    }

    private void setData() {
        edtFName.setText(CommonUtils.getUserFirstName(mContext));
        edtLName.setText(CommonUtils.getUserLastName(mContext));
        edtEmail.setText(CommonUtils.getUserEmail(mContext));
        edtMobile.setText(CommonUtils.getUserMobile(mContext));
        CommonUtils.loadCircleImageWithGlide(imgUser, CommonUtils.getUserProfile(mContext), mContext);
    }

    private void initView() {
        imgBack = rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader = rootView.findViewById(R.id.toolbar2_txtHeaderName);
        layoutNotification = rootView.findViewById(R.id.frag_edit_profile_layoutNotification);
        layoutImage = rootView.findViewById(R.id.frag_edit_profile_layoutImage);
        imgUser = rootView.findViewById(R.id.frag_edit_profile_imgUser);
        imgAdd = rootView.findViewById(R.id.frag_edit_profile_imgAdd);
        edtFName = rootView.findViewById(R.id.frag_edit_profile_editFirstName);
        edtLName = rootView.findViewById(R.id.frag_edit_profile_editLastName);
        edtMobile = rootView.findViewById(R.id.frag_edit_profile_editMobile);
        edtEmail = rootView.findViewById(R.id.frag_edit_profile_editEmail);
        edtCurrentPass = rootView.findViewById(R.id.frag_edit_profile_editCurrentPassword);
        edtNewPass = rootView.findViewById(R.id.frag_edit_profile_editNewPassword);
        edtVerifyPass = rootView.findViewById(R.id.frag_edit_profile_editVerifyPassword);
        txtSave = rootView.findViewById(R.id.frag_edit_profile_txtSave);

        txtHeader.setText("My Setting");
        imgBack.setOnClickListener(this);
        layoutNotification.setOnClickListener(this);
        layoutImage.setOnClickListener(this);
        txtSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar2_imgBack:
                ((ActHome) mContext).onBackPressed();
                break;
            case R.id.frag_edit_profile_layoutImage:
                onChangePhotoClick();
                break;
            case R.id.frag_edit_profile_layoutNotification:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                //((ActHome) mContext).switchContent(new FragNotificationSetting(), FragNotificationSetting.class.getSimpleName(), true);
                break;
            case R.id.frag_edit_profile_txtSave:
                String fName = edtFName.getText().toString().trim();
                String lName = edtLName.getText().toString().trim();
                String mobile = edtMobile.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String cPass = edtCurrentPass.getText().toString().trim();
                String nPass = edtNewPass.getText().toString().trim();
                String vPass = edtVerifyPass.getText().toString().trim();
                if (fName.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_first_name, mContext), 0, mContext);
                } else if (lName.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_last_name, mContext), 0, mContext);
                }else if (mobile.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_mobile, mContext), 0, mContext);
                }else if (email.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_email, mContext), 0, mContext);
                }else if (!CommonUtils.isEmailValid(email)) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_valid_email, mContext), 0, mContext);
                }else if (cPass.isEmpty() && (nPass.length() > 0 || vPass.length() > 0)) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_cpass, mContext), 0, mContext);
                }else if (nPass.isEmpty() && (vPass.length() > 0 || cPass.length() > 0)) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_npass, mContext), 0, mContext);
                }else if (!nPass.isEmpty() && (nPass.length() <6)) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_password_length, mContext), 0, mContext);
                }else if (vPass.isEmpty() && nPass.length() > 0) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_vpass, mContext), 0, mContext);
                }else if (!vPass.equals(nPass) && nPass.length() > 0) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_same_password, mContext), 0, mContext);
                }else {
                    updateUserDetail(uploadImageFile,fName,lName,mobile,email,cPass,nPass);
                }

                break;
        }
    }

    private void updateUserDetail(File user_image, String fName, String lName, String mobile, String email, String cPass, String nPass) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            RequestBody userId_body       =   RequestBody.create(MediaType.parse("text/plain"), CommonUtils.getSaloonId(mContext));
            RequestBody name_body         =   RequestBody.create(MediaType.parse("text/plain"), fName);
            RequestBody lname_body         =   RequestBody.create(MediaType.parse("text/plain"), lName);
            RequestBody phone_body        =   RequestBody.create(MediaType.parse("text/plain"), mobile);
            RequestBody email_body        =   RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody cpass_body       =   RequestBody.create(MediaType.parse("text/plain"), cPass);
            RequestBody npass_body       =   RequestBody.create(MediaType.parse("text/plain"), nPass);

            List<MultipartBody.Part> parts = new ArrayList<>();
            if(user_image != null){
                try {
                    RequestBody requestImageFile = RequestBody.create(MediaType.parse("image/*"),user_image);
                    String fileName= URLEncoder.encode(user_image.getName(), "utf-8");
                    parts.add(MultipartBody.Part.createFormData("profile_image",fileName,requestImageFile));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            ApiClient.getClient().updateUserProfile(parts,userId_body,name_body,lname_body,phone_body,email_body,cpass_body,npass_body).enqueue(new Callback<JsonObjectResponse<LoginDataModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<LoginDataModel>> call, Response<JsonObjectResponse<LoginDataModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            CommonUtils.setUserDetails(mContext,response.body().getObject());
                            AppDialogHelper.showAlertDialog(mContext, CommonUtils.gettingString(R.string.success, mContext),
                                    CommonUtils.gettingString(R.string.your_profile_update_successfully, mContext), new AlertDialogListener() {
                                        @Override
                                        public void onCloseBtnClick() {
                                            FragMenu.getInstance().setUserData();
                                            ((ActHome)mContext).onBackPressed();
                                        }
                                    });
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

    private void onChangePhotoClick() {
        if (ImageUploadUtils.checkPermission(mContext)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.MY_CAMERA_PERMISSION_CODE);
            }
        } else {
            openChooseImageDialog();
        }
    }

    private void openChooseImageDialog() {
        ImageUploadUtils.showPictureDialog(
                mContext,
                new ImageSelectDialogListener() {
                    @Override
                    public void onClickGallery() {
                        choosePhotoFromGallery();
                    }

                    @Override
                    public void onClickCamera(Intent cameraIntent) {
                        uploadImageFile = new File(cameraIntent.getStringExtra("image_path"));
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        startActivityForResult(cameraIntent, Constant.CAPTURE_IMAGE_REQUEST_CODE);
                    }
                });
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, Constant.GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                openChooseImageDialog();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == Constant.GALLERY_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            getImagePathFromUri(data.getData());
        } else if (requestCode == Constant.CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.e(TAG, "uploadImageFile ====" + uploadImageFile);
            Bitmap bitmap = CommonUtils.decodeFile(uploadImageFile);
            Uri selected_camera_uri = CommonUtils.getImageUri(mContext, bitmap, Constant.GALLERY_IMAGE_MAX_WIDTH, Constant.GALLERY_IMAGE_MAX_HEIGHT);
            uploadImageFile = new File(CommonUtils.getPath(selected_camera_uri, mContext));
            CommonUtils.loadCircleImageWithGlide(imgUser, uploadImageFile.getAbsolutePath(), mContext);
        }
    }

    private void getImagePathFromUri(Uri contentURI) {
        if (null != contentURI) {
            picUri = contentURI;
            Log.e(TAG, "==== picUri ====" + picUri);
            uploadImageFile = new File(CommonUtils.getPath(contentURI, mContext));
            Bitmap bitmap = CommonUtils.decodeFile(uploadImageFile);
            Uri selected_camera_uri = CommonUtils.getImageUri(mContext, bitmap, Constant.GALLERY_IMAGE_MAX_WIDTH, Constant.GALLERY_IMAGE_MAX_HEIGHT);
            uploadImageFile = new File(CommonUtils.getPath(selected_camera_uri, mContext));
            CommonUtils.loadCircleImageWithGlide(imgUser, uploadImageFile.getAbsolutePath(), mContext);
        }
    }
}
