package com.application.ksgu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.ksgu.Model.UploadFotoModel;
import com.application.ksgu.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class UploadFotoAdapter extends RecyclerView.Adapter<UploadFotoAdapter.MyViewHolder> {
    List<UploadFotoModel> menuModels;
    Context context;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, UploadFotoModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public UploadFotoAdapter(List<UploadFotoModel> menuModels) {
        this.menuModels = menuModels;
    }

    @Override
    public UploadFotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upload, parent, false);
        return new UploadFotoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UploadFotoAdapter.MyViewHolder holder, final int position) {
        UploadFotoModel menuModel = menuModels.get(position);
        holder.tv_title.setText(menuModel.getTitle());

        holder.rl_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,menuModels.get(position),position);
                }
            }
        });

        if (menuModel.getmUri()!=null){
            Glide.with(context).load(menuModel.getmUri().get(0)).fitCenter().into(holder.iv_preview);
            holder.ll_unggah.setVisibility(View.GONE);
            holder.iv_preview.setVisibility(View.VISIBLE);
        }else{
            holder.ll_unggah.setVisibility(View.VISIBLE);
            holder.iv_preview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_preview;
        TextView tv_title;
        LinearLayout ll_unggah;
        RelativeLayout rl_foto;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title    = itemView.findViewById(R.id.tv_title);
            iv_preview  = itemView.findViewById(R.id.iv_preview);
            ll_unggah   = itemView.findViewById(R.id.ll_unggah);
            rl_foto     = itemView.findViewById(R.id.rl_foto);
        }
    }
}
