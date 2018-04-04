package com.krescent.org.demogallery.activity;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.krescent.org.demogallery.R;
import com.krescent.org.demogallery.adapter.GalleryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomGalleryActivity extends AppCompatActivity {

    @BindView(R.id.rv_gallery_images)
    RecyclerView rvGalleryImages;

    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery);

        ButterKnife.bind(this);

        init();
    }

    private void init() {

        ArrayList<String> imageUrls = loadPhotosFromNativeGallery();
        galleryAdapter = new GalleryAdapter(imageUrls, getApplicationContext(), new GalleryAdapter.OnImageSelectListener() {
            @Override
            public void onSelect(int position) {
                Toast.makeText(CustomGalleryActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        rvGalleryImages.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvGalleryImages.setAdapter(galleryAdapter);

    }

    private ArrayList<String> loadPhotosFromNativeGallery() {
        /*For Image*/

        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
        ArrayList<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imageUrls.add(imagecursor.getString(dataColumnIndex));
        }

        /*For Video*/
        String[] parameters = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_TAKEN, MediaStore.Video.Thumbnails.DATA};

        Cursor videocursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                parameters, null, null, null);

        for (int i = 0; i < videocursor.getCount(); i++) {
            videocursor.moveToPosition(i);

            int column_index_thumb_data = videocursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA);
            String thumbnail = videocursor.getString(column_index_thumb_data);

            imageUrls.add(thumbnail);
        }
        return imageUrls;
    }

}
