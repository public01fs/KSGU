package com.application.ksgu.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.application.ksgu.Model.Disposisi;
import com.application.ksgu.Model.Surat;
import com.application.ksgu.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DisposisiListAdapter extends RecyclerView.Adapter<DisposisiListAdapter.ViewHolder> {

    private Context ctx;
    private List<Disposisi> items = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Disposisi obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_status,tv_tanggal,tv_jam;
        public TextView tv_dari,tv_kepada,tv_keterangan;
        public CardView cv_parent;



        public ViewHolder(View v) {
            super(v);
            tv_status       = v.findViewById(R.id.tv_status);
            tv_tanggal      = v.findViewById(R.id.tv_tanggal);
            tv_jam          = v.findViewById(R.id.tv_jam);
            tv_dari         = v.findViewById(R.id.tv_dari);
            tv_kepada       = v.findViewById(R.id.tv_kepada);
            tv_keterangan   = v.findViewById(R.id.tv_keterangan);
            cv_parent       = v.findViewById(R.id.cv_parent);
        }

    }

    public DisposisiListAdapter(Context ctx, List<Disposisi> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public DisposisiListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disposisi, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void changeDataSet(List<Disposisi> dataItems){
        this.items = dataItems;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Disposisi b = items.get(position);

        SimpleDateFormat simpleDateFormat   = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat tanggalFormat      = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat jamFormat          = new SimpleDateFormat("HH:mm");

        String tanggal = null, jam = null;

        try {
            Date disposisitgl   = simpleDateFormat.parse(b.getDISPOSISITGL());
            tanggal             = tanggalFormat.format(disposisitgl);
            jam                 = jamFormat.format(disposisitgl);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tv_tanggal.setText(tanggal);
        holder.tv_jam.setText(jam);
        holder.tv_dari.setText(b.getDISPOSISIDR());
        holder.tv_kepada.setText(b.getDISPOSISIKPD());
        holder.tv_keterangan.setText(b.getDISPOSISINOTE());
        holder.tv_status.setText(position+1+". "+b.getSTATUSNAME());
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