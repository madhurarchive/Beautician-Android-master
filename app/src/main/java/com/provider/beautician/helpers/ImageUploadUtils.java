package com.provider.beautician.helpers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.provider.beautician.R;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.listners.ImageSelectDialogListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUploadUtils {
    private static String TAG = ImageUploadUtils.class.getSimpleName();

    public static boolean checkPermission(Context mContext) {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED;
    }

    public static void showPictureDialog(final Context context, final ImageSelectDialogListener listener){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle(gettingString(context, R.string.select_image_from));
        String[] pictureDialogItems = {
                gettingString(context,R.string.from_gallery),
                gettingString(context,R.string.from_camera)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                listener.onClickGallery();
                                break;

                            case 1:
                                listener.onClickCamera(takePhotoFromCamera(context));
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public static String gettingString(Context context, int resId){
        return context.getResources().getString(resId);
    }

    public static Intent takePhotoFromCamera (Context mContext) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File sharedFolder = new File(mContext.getFilesDir(), Constant.SHARED_FOLDER);
        sharedFolder.mkdirs();
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(new Date());
            String pictureFile = Constant.FILE_NAME_PREFIX + timeStamp;
            File capturedImageFilePath = File.createTempFile(pictureFile,  Constant.FILE_NAME_EXTENSION, sharedFolder);

            capturedImageFilePath.createNewFile();

            //Log.e(TAG,capturedImageFilePath.getAbsolutePath());

            Uri outputFileUri = FileProvider.getUriForFile(mContext, Constant.SHARED_PROVIDER_AUTHORITY, capturedImageFilePath);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + Constant.SHARED_FOLDER;
                sharedFolder = new File(dir);
                sharedFolder.mkdirs();
                String file = dir+pictureFile;
                capturedImageFilePath = new File(file);
                try {
                    capturedImageFilePath.createNewFile();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                outputFileUri = Uri.fromFile(capturedImageFilePath);
            }
            Log.e(TAG,capturedImageFilePath.getAbsolutePath());
            Log.e(TAG,outputFileUri.getPath());

            cameraIntent.putExtra( "image_path", capturedImageFilePath.getAbsolutePath());
            cameraIntent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG,"path === " + cameraIntent.getStringExtra("image_path"));
        return cameraIntent;
    }
}
