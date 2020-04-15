package com.application.ksgu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.application.ksgu.R;
import com.artcak.artcakbase.model.ItemMenu;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CustomMenuAdapter extends RecyclerView.Adapter<CustomMenuAdapter.MyViewHolder> {
    List<ItemMenu> menuModels;
    Context context;

    public CustomMenuAdapter(List<ItemMenu> menuModels) {
        this.menuModels = menuModels;
    }

    @Override
    public CustomMenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_custom_menu, parent, false);
        return new CustomMenuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomMenuAdapter.MyViewHolder holder, int position) {
        ItemMenu menuModel = menuModels.get(position);
        holder.tv_title.setText(menuModel.getJudul());
        Glide.with(context).load(menuModel.getGambar_id()).fitCenter().into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}

