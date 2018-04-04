package com.krescent.org.demogallery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.krescent.org.demogallery.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pulkit on 3/4/18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> imagesList;
    private OnImageSelectListener onImageSelectListener;
    private Context context;

    public interface OnImageSelectListener {
        void onSelect(int position);
    }

    public GalleryAdapter(List<String> imagesList, Context context, OnImageSelectListener onImageSelectListener) {
        this.imagesList = imagesList;
        this.onImageSelectListener = onImageSelectListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gallery_images_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final GalleryViewHolder holder = (GalleryViewHolder) viewHolder;

        final String imageUrl = imagesList.get(position);

        if (imageUrl.contains("jpg") || imageUrl.contains("png")) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.tvVideo.setVisibility(View.GONE);

        }
        if (imageUrl.contains("mp4")) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.tvVideo.setVisibility(View.VISIBLE);

        }

        Glide.with(context)
                .load("file://" + imageUrl)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_gallery_image)
        ImageView imageView;

        @BindView(R.id.tv_video)
        TextView tvVideo;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_gallery_image)
        public void onClickImage() {
            onImageSelectListener.onSelect(getLayoutPosition());
        }
    }
}
