package com.application.ksgu.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.ksgu.Model.Surat;
import com.application.ksgu.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SuratListAdapter extends RecyclerView.Adapter<SuratListAdapter.ViewHolder> {

    private Context ctx;
    private List<Surat> items = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    Typeface font;

    public interface OnItemClickListener {
        void onItemClick(View view, Surat obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView status;
        public TextView date;
        public TextView tv_kapal;
        public TextView tv_jenis;
        public TextView tv_pemilik;
        public TextView type;
        public TextView principal;
        public TextView img_jenis;
        public TextView img_princ;
        public TextView img_type;
        public TextView img_ship;
        public TextView img_pemilik;
        public LinearLayout lyt_parent;



        public ViewHolder(View v) {
            super(v);
            status          = (TextView) v.findViewById(R.id.status);
            date            = (TextView) v.findViewById(R.id.date);
            tv_kapal        = (TextView) v.findViewById(R.id.tv_kapal);
            tv_jenis        = (TextView) v.findViewById(R.id.tv_jenis);
            tv_pemilik      = (TextView) v.findViewById(R.id.tv_pemilik);
            img_jenis       = (TextView) v.findViewById(R.id.img_jenis);
            img_ship        = (TextView) v.findViewById(R.id.img_ship);
            img_pemilik     = (TextView) v.findViewById(R.id.img_pemilik);
            lyt_parent      = (LinearLayout) v.findViewById(R.id.lyt_parent);
        }

    }

    public SuratListAdapter(Context ctx, List<Surat> items) {
        this.ctx = ctx;
        this.items = items;
        font = Typeface.createFromAsset( ctx.getAssets(), "fontawesome-webfont.ttf" );
    }

    @Override
    public SuratListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void changeDataSet(List<Surat> dataItems){
        this.items = dataItems;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Surat b = items.get(position);

//        String tanggal = b.getCreatedAt();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String tanggal1 = null;

        try {
            Date tanggal = simpleDateFormat.parse(b.getSURATINPUTTGL());
            tanggal1 = simpleDateFormat1.format(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//
//
////        holder.date.setText(b.date);
//        holder.kode_booking.setText(b.getKodeBooking());
//        holder.do_number.setText(b.getBookingRef());
        holder.tv_jenis.setText(b.getJENISNAME());
        holder.tv_pemilik.setText(b.getKAPALPEMILIK());
        holder.tv_kapal.setText(b.getKAPALNAME());
        holder.img_jenis.setTypeface(font);
        holder.img_ship.setTypeface(font);
        holder.img_pemilik.setTypeface(font);
////        holder.shipper_name.setText(b.getShipperName());
//        holder.principal.setText(b.getPrincipal());
//        holder.type.setText(b.getType());
        holder.date.setText(tanggal1);
//
//        if (b.getStatus() == 1){
//            holder.status.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.shape_rectangle_canceled));
//        } else if (b.getStatus() == 10){
//            holder.status.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.shape_rectangle_finished));
//        } else {
//            holder.status.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.shape_rectangle_ongoing));
//        }
//
        holder.status.setText(b.getStatusNotaDesc());

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,items.get(position),position);
                }
            }
        });
//
////        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                if (mOnItemClickListener != null) {
////                    mOnItemClickListener.onItemClick(view, b, position);
////                }
////            }
////        });
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