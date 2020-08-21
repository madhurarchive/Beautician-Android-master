package com.provider.beautician.listners;

import android.content.Intent;

public interface ImageSelectDialogListener {
    void onClickGallery();
    void onClickCamera(Intent cameraIntent);
}
