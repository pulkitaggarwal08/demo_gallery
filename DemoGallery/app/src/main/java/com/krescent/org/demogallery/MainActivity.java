package com.krescent.org.demogallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.krescent.org.demogallery.activity.CustomGalleryActivity;
import com.krescent.org.demogallery.adapter.GalleryAdapter;
import com.krescent.org.demogallery.config.AppConstant;
import com.krescent.org.demogallery.util.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_load_gallery)
    Button btn_load_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    /*Through Custom Gallery*/
    @OnClick(R.id.btn_load_gallery)
    public void onClickCustomGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)) {
                customGalleryIntent();
            }
        } else {
            customGalleryIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    customGalleryIntent();
                } else {
                    customGalleryIntent();
                }
                break;
        }
    }

    private void customGalleryIntent() {
        Intent intent = new Intent(getApplicationContext(), CustomGalleryActivity.class);
        startActivity(intent);
    }
}


