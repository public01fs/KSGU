package com.application.ksgu.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.ksgu.Model.Document;
import com.application.ksgu.Model.Item;
import com.application.ksgu.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.sephiroth.android.library.easing.Linear;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private Context ctx;
    private List<Item> items = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Item obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public ImageView iv_gambar;
        public LinearLayout ll_parent;

        public ViewHolder(View v) {
            super(v);
            tv_title    = v.findViewById(R.id.tv_title);
            iv_gambar   = v.findViewById(R.id.iv_image);
            ll_parent   = v.findViewById(R.id.ll_parent);
        }

    }

    public ItemListAdapter(Context ctx, List<Item> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item b = items.get(position);

        holder.tv_title.setText(b.getJudul());
        Glide.with(ctx).load(b.getGambar()).fitCenter().into(holder.iv_gambar);

        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,b,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}