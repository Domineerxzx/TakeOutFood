package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.DownloadUtils;

import java.io.File;
import java.util.List;

public class SubmitAdapter extends RecyclerView.Adapter<SubmitAdapter.ViewHolder> {

    private Context context;
    private List<String> data;
    private OnItemClickListener onItemClickListener;

    public SubmitAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SubmitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_submit, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (data.size() > 0 && data.get(i).length() > 0) {
            File file = new File(data.get(i));
            if (file.length() > 0) {
                Glide.with(context).load(data.get(i)).into(viewHolder.iv_submit);
            }else{
                OssHandler ossHandler = new OssHandler(context, viewHolder.iv_submit);
                DownloadUtils.downloadFileFromOss(file,ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+data.get(i));
            }
            viewHolder.iv_delete_submit.setVisibility(View.VISIBLE);
        } else {
            Glide.with(context).load(R.drawable.submit).into(viewHolder.iv_submit);
            viewHolder.iv_delete_submit.setVisibility(View.GONE);
        }
        viewHolder.iv_delete_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(i);
                notifyDataSetChanged();
            }
        });
        viewHolder.ll_delete_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 1 : data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_submit;
        private ImageView iv_delete_submit;
        private LinearLayout ll_delete_submit;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            initView(itemView);
        }

        private void initView(View itemView) {
            iv_submit = itemView.findViewById(R.id.iv_submit);
            iv_delete_submit = itemView.findViewById(R.id.iv_delete_submit);
            ll_delete_submit = itemView.findViewById(R.id.ll_delete_submit);
            iv_submit.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
