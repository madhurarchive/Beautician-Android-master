package com.provider.beautician.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.AutoCompleteAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.listners.AlertDialogListener;
import com.provider.beautician.model.StaffLocationModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragAddLocations extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;

    private EditText                    edtLocation,edtContactNumber,
                                        edtContactEmail, edtOptionalAddress,
                                        edtCity,edtState, edtZipCode;
    private Button                      btnSave;
    private AutoCompleteTextView        edtStreetAddress;
    private AutoCompleteAdapter         adapter;
    PlacesClient                        placesClient;
    private ImageView                   imgBack;
    private TextView                    txtHeader;
    private String                      TAG = FragAddLocations.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView==null){
            rootView = inflater.inflate(R.layout.frag_add_new_location, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded     =   true;
            mContext    =   getActivity();
            initView();
            initAutoCompleteTextView();
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        edtLocation         =   rootView.findViewById(R.id.location_edt_location_name);
        edtContactNumber    =   rootView.findViewById(R.id.location_edt_contact_no);
        edtContactEmail     =   rootView.findViewById(R.id.location_edt_contact_email);
        edtStreetAddress    =   rootView.findViewById(R.id.location_edt_street_address);
        edtOptionalAddress  =   rootView.findViewById(R.id.location_edt_optional_address);
        edtCity             =   rootView.findViewById(R.id.location_edt_city);
        edtState            =   rootView.findViewById(R.id.location_edt_state);
        edtZipCode          =   rootView.findViewById(R.id.location_edt_zip_code);
        btnSave             =   rootView.findViewById(R.id.location_btn_save);

        txtHeader.setText(CommonUtils.gettingString(R.string.new_location,mContext));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location         =   edtLocation.getText().toString().trim();
                String number           =   edtContactNumber.getText().toString().trim();
                String email            =   edtContactEmail.getText().toString().trim();
                String streetAddress    =   edtStreetAddress.getText().toString().trim();
                String optionAddress    =   edtOptionalAddress.getText().toString().trim();
                String city             =   edtCity.getText().toString().trim();
                String state            =   edtState.getText().toString().trim();
                String zipCode          =   edtZipCode.getText().toString().trim();
                if (location.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.plz_enter_location_name,mContext),0,mContext);
                }else if (number.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_mobile,mContext),0,mContext);
                }else if (email.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_email,mContext),0,mContext);
                }else if (!CommonUtils.isEmailValid(email)){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_valid_email,mContext),0,mContext);
                }else if (streetAddress.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_street_address,mContext),0,mContext);
                }else if (city.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_city,mContext),0,mContext);
                }else if (state.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_state,mContext),0,mContext);
                }else if (zipCode.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_zip_code,mContext),0,mContext);
                }else {
                    addNewLocation(location,number,email,streetAddress,optionAddress,city,state,zipCode);
                }
            }
        });
    }

    private void addNewLocation(String location, String number, String email, String streetAddress, String optionAddress, String city, String state, String zipCode) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().addSaloonLocation(CommonUtils.getSaloonId(mContext),location,number,email,streetAddress,optionAddress,city,state,zipCode,"1").enqueue(new Callback<JsonObjectResponse<StaffLocationModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<StaffLocationModel>> call, Response<JsonObjectResponse<StaffLocationModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            AppDialogHelper.showAlertDialog(mContext, CommonUtils.gettingString(R.string.success, mContext),
                                    CommonUtils.gettingString(R.string.location_add_success, mContext), new AlertDialogListener() {
                                        @Override
                                        public void onCloseBtnClick() {
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
                public void onFailure(Call<JsonObjectResponse<StaffLocationModel>> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }

    private void initAutoCompleteTextView() {
        edtStreetAddress.setThreshold(1);
        edtStreetAddress.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(mContext, placesClient);
        edtStreetAddress.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = adapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {
                            LatLng latLng = task.getPlace().getLatLng();
                            if (latLng!=null){
                                edtStreetAddress.setText(task.getPlace().getName());
                                edtStreetAddress.setSelection(edtStreetAddress.getText().length());
                                getAddressByLatLng(latLng.latitude,latLng.longitude);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private void getAddressByLatLng(double latitude,double longitude){
        Geocoder geocoder = new Geocoder(mContext, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String cityName     =   addresses.get(0).getLocality();
            String stateName    =   addresses.get(0).getAdminArea();
            String postalCode   =   addresses.get(0).getPostalCode();

            edtCity.setText(cityName);
            edtState.setText(stateName);
            edtZipCode.setText(postalCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
