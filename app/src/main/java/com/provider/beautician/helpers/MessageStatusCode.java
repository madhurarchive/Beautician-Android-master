package com.provider.beautician.helpers;

import android.content.Context;

import com.provider.beautician.R;

/**
 * Created by archive_infotech on 9/20/18.
 */

public class MessageStatusCode {

    public static void showErrorMessageByStatusCode(String code, Context context){
        switch (code){
            case "no_record_found":
                final String no_record_found  = context.getResources().getString(R.string.no_record_found);
                CommonUtils.showSnackbarWithoutView(no_record_found,context,0);
                break;

            case "validation_failed":
                final String validation_failed  = context.getResources().getString(R.string.validation_failed);
                CommonUtils.showSnackbarWithoutView(validation_failed,context,0);
                break;

            case "client_already_exist":
                final String client_already_exist  = context.getResources().getString(R.string.client_already_exist);
                CommonUtils.showSnackbarWithoutView(client_already_exist,context,0);
                break;

            case "invalid_credentials":
                final String invalid_credentials  = context.getResources().getString(R.string.invalid_credentials);
                CommonUtils.showSnackbarWithoutView(invalid_credentials,context,0);
                break;

            case "oops_some_error_occured":
                final String oops_some_error_occured  = context.getResources().getString(R.string.oops_some_error_occured);
                CommonUtils.showSnackbarWithoutView(oops_some_error_occured,context,0);
                break;

            case "email_id_not_exist":
                final String email_id_not_exist  = context.getResources().getString(R.string.email_id_not_exist);
                CommonUtils.showSnackbarWithoutView(email_id_not_exist,context,0);
                break;

            case "user_id_not_exist":
                final String user_id_not_exist  = context.getResources().getString(R.string.user_id_not_exist);
                CommonUtils.showSnackbarWithoutView(user_id_not_exist,context,0);
                break;

            case "something_went_wrong":
                final String something_went_wrong  = context.getResources().getString(R.string.something_went_wrong);
                CommonUtils.showSnackbarWithoutView(something_went_wrong,context,0);
                break;

            case "account_inactive":
                final String account_inactive  = context.getResources().getString(R.string.account_inactive);
                CommonUtils.showSnackbarWithoutView(account_inactive,context,0);
                break;

            case "invalid_old_password":
                final String invalid_old_password  = context.getResources().getString(R.string.invalid_old_password);
                CommonUtils.showSnackbarWithoutView(invalid_old_password,context,0);
                break;

            default:
                CommonUtils.showSnackbarWithoutView(code,context,0);
                break;

        }
    }

}
