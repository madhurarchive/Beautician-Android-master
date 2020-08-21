package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.CustomViewPager;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.listners.AlertDialogListener;
import com.provider.beautician.listners.TwoButtonAlertDialogListener;
import com.provider.beautician.model.ClientModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragClientProfile extends Fragment implements View.OnClickListener{
    private Context                     mContext;
    private View                        rootView,viewAppointment,viewProducts,viewInvoices;
    private boolean                     mLoaded;
    private CustomViewPager             tabViewPager;
    private ViewPagerAdapter            adapter;
    private TextView                    txtHeaderName,txtFirstWork,txtName,txtEmail,txtGender,txtNotification;
    private ImageView                   imgBack,imgMenu;
    private LinearLayout                layoutAppointment,layoutProducts,layoutInvoice;
    private BottomSheetDialog           mBottom;
    public ClientModel                  model;
    private String                      TAG = FragClientProfile.class.getSimpleName();

    public FragClientProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_client_profile, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded = true;
            mContext = getActivity();
            initView();
            setUpViewPager();
        }
        if (mContext!=null){
            ((ActHome)mContext).setUpToolBar(this,"",true);
        }
    }

    private void initView() {
        imgBack                 =   rootView.findViewById(R.id.toolbar2_imgBack);
        imgMenu                 =   rootView.findViewById(R.id.toolbar2_imgMenu);
        txtHeaderName           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        tabViewPager            =   rootView.findViewById(R.id.frag_client_profile_viewPager);
        layoutAppointment       =   rootView.findViewById(R.id.frag_clients_profile_layoutAppointments);
        layoutProducts          =   rootView.findViewById(R.id.frag_clients_profile_layoutProducts);
        layoutInvoice           =   rootView.findViewById(R.id.frag_clients_profile_layoutInvoices);
        viewAppointment         =   rootView.findViewById(R.id.frag_clients_profile_viewAppointments);
        viewProducts            =   rootView.findViewById(R.id.frag_clients_profile_viewProducts);
        viewInvoices            =   rootView.findViewById(R.id.frag_clients_profile_viewInvoices);
        txtFirstWork            =   rootView.findViewById(R.id.frag_client_profile_txtNameFirstWord);
        txtName                 =   rootView.findViewById(R.id.frag_client_profile_txtUserName);
        txtEmail                =   rootView.findViewById(R.id.frag_client_profile_txtEmail);
        txtGender               =   rootView.findViewById(R.id.frag_client_profile_txtGender);
        txtNotification         =   rootView.findViewById(R.id.frag_client_profile_txtNotification);

        imgBack.setVisibility(View.VISIBLE);
        imgMenu.setVisibility(View.VISIBLE);
        imgMenu.setImageResource(R.drawable.ic_there_dots);
        txtHeaderName.setText(CommonUtils.gettingString(R.string.client_profile,mContext));

        imgBack.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        layoutAppointment.setOnClickListener(this);
        layoutProducts.setOnClickListener(this);
        layoutInvoice.setOnClickListener(this);

        if (model!=null){
            txtFirstWork.setText(model.getName().substring(0,1));
            txtName.setText(model.getName());
            txtEmail.setText(model.getEmail());
            if (model.getAcceptMarketingNotification().equals("Y")){
                txtNotification.setText(CommonUtils.gettingString(R.string.accepts_marketing_notifications,mContext));
            }else {
                txtNotification.setText(CommonUtils.gettingString(R.string.dont_accepts_marketing_notification,mContext));
            }
            if (model.getGender().equals("M")){
                txtGender.setText(CommonUtils.gettingString(R.string.male,mContext));
            }else if (model.getGender().equals("F")){
                txtGender.setText(CommonUtils.gettingString(R.string.female,mContext));
            }else {
                txtGender.setText(CommonUtils.gettingString(R.string.unknown,mContext));
            }
        }
    }

    private void setUpViewPager(){

        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new FragClientTabAppointment(), CommonUtils.gettingString(R.string.appointments,mContext));
        adapter.addFragment(new FragClientTabProducts(), CommonUtils.gettingString(R.string.products,mContext));
        adapter.addFragment(new FragClientTabInvoice(), CommonUtils.gettingString(R.string.invoices,mContext));
        tabViewPager.setPagingEnabled(false);
        tabViewPager.setAdapter(adapter);
        tabViewPager.addOnPageChangeListener(onPageChangeListener);
        tabViewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar2_imgBack:
                ((ActHome)mContext).onBackPressed();
                break;
            case R.id.toolbar2_imgMenu:
                showDialogHeaderAction();
                break;
            case R.id.frag_clients_profile_layoutAppointments:
                viewAppointment.setVisibility(View.VISIBLE);
                viewProducts.setVisibility(View.INVISIBLE);
                viewInvoices.setVisibility(View.INVISIBLE);
                tabViewPager.setCurrentItem(0);
                break;
            case R.id.frag_clients_profile_layoutProducts:
                viewAppointment.setVisibility(View.INVISIBLE);
                viewProducts.setVisibility(View.VISIBLE);
                viewInvoices.setVisibility(View.INVISIBLE);
                tabViewPager.setCurrentItem(1);
                break;
            case R.id.frag_clients_profile_layoutInvoices:
                viewAppointment.setVisibility(View.INVISIBLE);
                viewProducts.setVisibility(View.INVISIBLE);
                viewInvoices.setVisibility(View.VISIBLE);
                tabViewPager.setCurrentItem(2);
                break;
        }
    }

    private void showDialogHeaderAction() {
        try {
            final View sheetView = ((ActHome)mContext).getLayoutInflater().inflate(R.layout.bottom_client_profile_menu, null);
            mBottom = new BottomSheetDialog(mContext);
            mBottom.setContentView(sheetView);
            mBottom.show();
            FrameLayout bottomSheet = (FrameLayout) mBottom.findViewById(R.id.design_bottom_sheet);
            LinearLayout txtEdit            =   sheetView.findViewById(R.id.bottom_client_profile_layoutEdit);
            LinearLayout layoutDelete       =   sheetView.findViewById(R.id.bottom_client_profile_layoutDelete);

            txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragAddNewClient fragAddNewClient = new FragAddNewClient();
                    fragAddNewClient.model = model;
                    ((ActHome) mContext).switchContent(fragAddNewClient, FragAddNewClient.class.getSimpleName(), true);
                    mBottom.dismiss();
                }
            });

            layoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottom.dismiss();
                    AppDialogHelper.showDialogWithTwoButton(mContext, CommonUtils.gettingString(R.string.confimation, mContext),
                            CommonUtils.gettingString(R.string.are_you_sure_to_delete, mContext),
                            CommonUtils.gettingString(R.string.cancel, mContext),
                            CommonUtils.gettingString(R.string.ok, mContext),
                            new TwoButtonAlertDialogListener() {
                                @Override
                                public void onYesBtnClick() {
                                    deleteUser(model.getUserId());
                                }

                                @Override
                                public void onNoBtnClick() {

                                }
                            });
                }
            });

            bottomSheet.setBackground(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(String userId) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().deleteClient(userId).enqueue(new Callback<JsonObjectResponse<ClientModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<ClientModel>> call, Response<JsonObjectResponse<ClientModel>> response) {
                    CommonUtils.dismissLoader();
                    if (response.code() == 200 && response.body() != null) {
                        Log.e(TAG, "url       ===== " + call.request().url());
                        Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            AppDialogHelper.showAlertDialog(mContext, CommonUtils.gettingString(R.string.success, mContext)
                                    , CommonUtils.gettingString(R.string.client_delete_successfully, mContext), new AlertDialogListener() {
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
                public void onFailure(Call<JsonObjectResponse<ClientModel>> call, Throwable t) {
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                    Log.e(TAG, "Throwable === " + t.getMessage());
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
     /*       Fragment fragment = adapter.getItem(position);
            ((Theme2_Frag_Review)fragment).callReviewApi();*/
        }

        @Override
        public void onPageScrollStateChanged(int position) {

        }
    };

}
