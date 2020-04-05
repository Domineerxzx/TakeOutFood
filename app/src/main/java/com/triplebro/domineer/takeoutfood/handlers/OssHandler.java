package com.triplebro.domineer.takeoutfood.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;

import java.io.File;

public class OssHandler extends Handler {

    private Context context;
    private ImageView imageView;
    private boolean isUserHead;

    public OssHandler(Context context) {
        this.context = context;
    }

    public OssHandler(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public OssHandler(Context context, ImageView imageView, boolean isUserHead) {
        this.context = context;
        this.imageView = imageView;
        this.isUserHead = isUserHead;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case ProjectProperties.WHAT_FAILED_UPLOAD:
                String uploadFailed = (String) msg.obj;
                Toast.makeText(context, uploadFailed, Toast.LENGTH_SHORT).show();
                break;
            case ProjectProperties.WHAT_SUCCESS_UPLOAD:
                String uploadSuccess = (String) msg.obj;
                Toast.makeText(context, uploadSuccess, Toast.LENGTH_SHORT).show();
                break;
            case ProjectProperties.WHAT_SUCCESS_DOWNLOAD:
                if(isUserHead){
                    File file = (File) msg.obj;
                    Glide.with(context).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                }else{
                    File file = (File) msg.obj;
                    Glide.with(context).load(file).into(imageView);
                }
                break;
            case ProjectProperties.WHAT_FAILED_DOWNLOAD:
                String downloadFailed = (String) msg.obj;
                Toast.makeText(context, downloadFailed, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
