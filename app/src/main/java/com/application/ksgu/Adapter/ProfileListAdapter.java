package com.application.ksgu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.ksgu.Model.Profile;
import com.application.ksgu.Model.UploadFotoModel;
import com.application.ksgu.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.MyViewHolder> {
    List<Profile> profiles;
    Context context;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Profile obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public ProfileListAdapter(Context context,List<Profile> profiles) {
        this.context    = context;
        this.profiles   = profiles;
    }

    @Override
    public ProfileListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_item, parent, false);
        return new ProfileListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileListAdapter.MyViewHolder holder, final int position) {
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
