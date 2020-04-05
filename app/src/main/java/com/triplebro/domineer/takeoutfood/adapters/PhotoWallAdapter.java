package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.DownloadUtils;

import java.io.File;
import java.util.List;

public class PhotoWallAdapter extends RecyclerView.Adapter<PhotoWallAdapter.ViewHolder> {

    private Context context;
    private List<String> data;
    private OnItemClickListener onItemClickListener;

    public PhotoWallAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PhotoWallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_photo_wall, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        File file = new File(data.get(i));
        if (file.length() > 0){
            Glide.with(context).load(data.get(i)).into(viewHolder.iv_photo_wall);
        }else{
            OssHandler ossHandler = new OssHandler(context, viewHolder.iv_photo_wall);
            DownloadUtils.downloadFileFromOss(file,ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+data.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_photo_wall;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            initView(itemView);
        }

        private void initView(View itemView) {
            iv_photo_wall = itemView.findViewById(R.id.iv_photo_wall);
            iv_photo_wall.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
