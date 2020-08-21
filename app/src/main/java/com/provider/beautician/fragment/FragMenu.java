package com.provider.beautician.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.activity.ActLogin;
import com.provider.beautician.adapter.HomeMenuAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.listners.TwoButtonAlertDialogListener;
import com.provider.beautician.model.ListDataModel;
import com.provider.beautician.model.LoginDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMenu extends Fragment implements View.OnClickListener {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private RecyclerView                mRecyclerView;
    private HomeMenuAdapter             mAdapter;
    private ArrayList<ListDataModel>    mMenuList = new ArrayList<ListDataModel>();
    private TextView                    txtFirstWord,txtUserName;
    private ImageView                   imgUser;
    private LinearLayout                layoutMyProfile;
    private ImageView                   imgSearch;
    private String                      TAG = FragMenu.class.getSimpleName();
    private static FragMenu             mInstance;

    public FragMenu() {
        // Required empty public constructor
    }

    public static FragMenu getInstance() {
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null){
            rootView = inflater.inflate(R.layout.frag_menu, container, false);
        }
        mInstance = this;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded = true;
            mContext = getActivity();
            initView();
            setUserData();
            setUpRecycleView();
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "",false);
        }
    }

    private void initView() {
        txtFirstWord        =   rootView.findViewById(R.id.frag_menu_txtNameFirstWord);
        txtUserName         =   rootView.findViewById(R.id.frag_menu_txtUserName);
        mRecyclerView       =   rootView.findViewById(R.id.frag_menu_recyclerView);
        imgUser             =   rootView.findViewById(R.id.frag_menu_imgUser);
        layoutMyProfile     =   rootView.findViewById(R.id.frag_menu_layoutMyProfile);
        imgSearch           =   rootView.findViewById(R.id.img_search);


        layoutMyProfile.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_menu_layoutMyProfile:
                ((ActHome)mContext).switchContent(new FragEditProfile(),FragEditProfile.class.getCanonicalName(),true);
                break;

            case R.id.img_search:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setUpRecycleView() {
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.home,mContext), R.mipmap.img_home_icon_1));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.staff,mContext), R.mipmap.img_staff_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.services,mContext), R.mipmap.img_service_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.inventory,mContext), R.mipmap.img_inventory_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.analytics,mContext), R.mipmap.img_analytics_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.setup,mContext), R.mipmap.img_setting_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.app_name,mContext), 0));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.overview,mContext), R.mipmap.img_overview_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.online_booking,mContext), R.mipmap.img_online_booking_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.marketing,mContext), R.mipmap.img_marketing_icon_png));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.client_message,mContext), R.mipmap.img_measge_icon));
        mMenuList.add(new ListDataModel("", 0));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.contact_support,mContext), 0));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.help_center,mContext), 0));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.log_out,mContext), 0));
        mAdapter = new HomeMenuAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new HomeMenuAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                    onClick(position);
            }
        });
    }

    private void onClick(int position) {
        switch (position){
            case 0:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                ((ActHome)mContext).switchContent(new FragStaffMenu(), FragStaffMenu.class.getSimpleName(),true);
                break;
            case 2:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                //((ActHome)mContext).switchContent(new FragServicesMenu(), FragServicesMenu.class.getSimpleName(),true);
                break;
            case 3:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                //((ActHome)mContext).switchContent(new FragInventoryMenu(), FragInventoryMenu.class.getSimpleName(),true);
                break;
            case 4:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                //((ActHome)mContext).switchContent(new FragAnalyticsMenu(), FragAnalyticsMenu.class.getSimpleName(),true);
                break;
            case 5:
                ((ActHome)mContext).switchContent(new FragSetupMenu(), FragSetupMenu.class.getSimpleName(),true);
                break;
            case 7:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
            case 8:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
            case 9:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
            case 10:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
            case 12:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
            case 13:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
            case 14:
                AppDialogHelper.showDialogWithTwoButton(mContext,
                        CommonUtils.gettingString(R.string.log_out, mContext),
                        CommonUtils.gettingString(R.string.are_you_sure_to_logout, mContext),
                        CommonUtils.gettingString(R.string.str_no, mContext),
                        CommonUtils.gettingString(R.string.str_yes, mContext),
                        new TwoButtonAlertDialogListener() {
                            @Override
                            public void onYesBtnClick() {
                                doLogoutApi();
                            }

                            @Override
                            public void onNoBtnClick() {

                            }
                        });
                break;
        }
    }

    private void doLogoutApi() {
        Log.e(TAG, "User id === " + CommonUtils.getUserId(mContext));
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().doLogout(CommonUtils.getSaloonId(mContext), "A").enqueue(new Callback<JsonObjectResponse<LoginDataModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<LoginDataModel>> call, Response<JsonObjectResponse<LoginDataModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            CommonUtils.clearPreferenceOnLogout(mContext);
                            //FirebaseMessaging.getInstance().unsubscribeFromTopic(Constant.GLOBAL_SUBSCRIBE_TOPIC);
                            mContext.startActivity(new Intent(mContext, ActLogin.class));
                            ((ActHome) mContext).finish();
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

    public void setUserData(){
        txtUserName.setText(String.format("%s %s", CommonUtils.getUserFirstName(mContext), CommonUtils.getUserLastName(mContext)));
        txtFirstWord.setText(CommonUtils.getUserFirstName(mContext).substring(0,1));
        String userImage = CommonUtils.getUserProfile(mContext);
        CommonUtils.loadCircleImageWithGlide(imgUser, userImage, mContext);
        if (userImage.trim().isEmpty()){
            txtFirstWord.setVisibility(View.VISIBLE);
            imgUser.setVisibility(View.GONE);
        }else {
            txtFirstWord.setVisibility(View.GONE);
            imgUser.setVisibility(View.VISIBLE);
        }
    }

}
