package com.application.ksgu.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.ksgu.Model.Document;
import com.application.ksgu.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DokumenListAdapter extends RecyclerView.Adapter<DokumenListAdapter.ViewHolder> {

    private Context ctx;
    private List<Document> items = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Document obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void changeDataSet(List<Document> dataItems){
        this.items = dataItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_dokumen,tv_nomor,tv_tanggal;
        public LinearLayout lyt_parent;
        public Button btn_download;

        public ViewHolder(View v) {
            super(v);
            tv_dokumen      = v.findViewById(R.id.tv_dokumen);
            tv_nomor        = v.findViewById(R.id.tv_no_dokumen);
            tv_tanggal      = v.findViewById(R.id.tv_tanggal);
            btn_download    = v.findViewById(R.id.btn_download);
            lyt_parent      = v.findViewById(R.id.lyt_parent);
        }

    }

    public DokumenListAdapter(Context ctx, List<Document> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public DokumenListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dokumen_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Document b = items.get(position);

        SimpleDateFormat dateFormatter       = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter1      = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFormatter2      = new SimpleDateFormat("dd-MM-yyyy");

        holder.tv_dokumen.setText(b.getDokumen());
        holder.tv_nomor.setText(b.getNomorDokumen());

        if (TextUtils.isEmpty(b.getFile())){
            holder.btn_download.setVisibility(View.GONE);
        } else {
            holder.btn_download.setVisibility(View.VISIBLE);
        }

        holder.btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,items.get(position),position);
                }
            }
        });

        try {
            Date date   = dateFormatter.parse(b.getTanggalMulaiBerlaku());
            holder.tv_tanggal.setText(dateFormatter2.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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