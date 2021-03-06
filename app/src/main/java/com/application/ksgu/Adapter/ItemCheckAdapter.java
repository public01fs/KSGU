package com.application.ksgu.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.application.ksgu.Model.DataNota;
import com.application.ksgu.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemCheckAdapter extends RecyclerView.Adapter<ItemCheckAdapter.MyViewHolder> {
    List<DataNota> daftarpaket;
    List<DataNota> check = new ArrayList<>();
    Context context;

    public ItemCheckAdapter(List<DataNota> daftarpaket) {
        this.daftarpaket = daftarpaket;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check, parent, false);
        return new ItemCheckAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final DataNota model = daftarpaket.get(position);
//        holder.tv_nama.setText(model.getNOTANAME());
        holder.checkBox.setText(model.getNOTANAME());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    if (b){
                        check.add(model);
                        model.setChecked(true);
                    } else {
                        model.setChecked(false);
                        check.remove(model);
                    }
//                    notifyDataSetChanged();
                } catch (Exception e){
                    Log.e("onCheckChanged", e.getMessage());
                }
            }
        });

        if (model.isChecked()){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

//        if (holder.checkBox.isChecked()){
//            daftarpaket.get(position).setIs_aktif("1");
////            notifyDataSetChanged();
//        } else {
//            daftarpaket.get(position).setIs_aktif("0");
////            notifyDataSetChanged();
//        }
//        holder.tv_hapus.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return daftarpaket.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        CheckBox checkBox;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_nama         = itemView.findViewById(R.id.tv_nama);
            checkBox        = itemView.findViewById(R.id.check_pilih);
//            tv_hapus = (TextView) itemView.findViewById(R.id.tv_hapus);
        }
    }

    public List<DataNota> getItems() {
        return daftarpaket;
    }

    public List<DataNota> getCheck(){
        return check;
    }
}
