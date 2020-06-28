package com.application.ksgu.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.ksgu.Model.SuratLampiranItem;
import com.application.ksgu.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class LampiranAdapter extends RecyclerView.Adapter<LampiranAdapter.MyViewHolder> {
    List<SuratLampiranItem> menuModels;
    Context context;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, SuratLampiranItem obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public LampiranAdapter(List<SuratLampiranItem> menuModels) {
        this.menuModels = menuModels;
    }

    @Override
    public LampiranAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upload, parent, false);
        return new LampiranAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LampiranAdapter.MyViewHolder holder, final int position) {
        SuratLampiranItem menuModel = menuModels.get(position);
        holder.tv_title.setText(menuModel.getCEKLISTNAME());

        holder.rl_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,menuModels.get(position),position);
                }
            }
        });

        if (menuModel.getPATH()!=null && !menuModel.getFILETYPE().toLowerCase().equals("pdf")){
            Glide.with(context).load(menuModel.getSERVERFILE()+menuModel.getPATH()).fitCenter().into(holder.iv_preview);
            holder.ll_unggah.setVisibility(View.GONE);
            holder.iv_preview.setVisibility(View.VISIBLE);
        } else{
            Glide.with(context).load(R.drawable.pdf).fitCenter().into(holder.iv_preview);
            holder.ll_unggah.setVisibility(View.GONE);
            holder.iv_preview.setVisibility(View.VISIBLE);
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
