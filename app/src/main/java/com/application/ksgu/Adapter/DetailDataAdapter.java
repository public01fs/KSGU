package com.application.ksgu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.ksgu.Model.Profile;
import com.application.ksgu.R;

import java.util.List;

public class DetailDataAdapter extends RecyclerView.Adapter<DetailDataAdapter.MyViewHolder> {
    List<Profile> profiles;
    Context context;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Profile obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public DetailDataAdapter(Context context, List<Profile> profiles) {
        this.context    = context;
        this.profiles   = profiles;
    }

    @Override
    public DetailDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_item, parent, false);
        return new DetailDataAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailDataAdapter.MyViewHolder holder, final int position) {
        Profile profile = profiles.get(position);
        holder.tv_title.setText(profile.getTitle());
        holder.tv_value.setText(profile.getValue());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_value;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title    = itemView.findViewById(R.id.tv_title);
            tv_value    = itemView.findViewById(R.id.tv_value);
        }
    }
}
