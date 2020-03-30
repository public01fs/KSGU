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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemCheckAdapter extends RecyclerView.Adapter<ItemCheckAdapter.MyViewHolder> {
    List<DataNota> daftarpaket;
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
        DataNota model = daftarpaket.get(position);
        holder.tv_nama.setText(model.getNOTANAME());

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                try {
//                    if (b){
//                        daftarpaket.get(position).setIs_aktif("1");
//                    } else {
//                        daftarpaket.get(position).setIs_aktif("0");
//                    }
//                    notifyDataSetChanged();
//                } catch (Exception e){
//                    Log.e("onCheckChanged", e.getMessage());
//                }
//            }
//        });

//        if (holder.checkBox.isChecked()){
//            daftarpaket.get(position).setIs_aktif("1");
////            notifyDataSetChanged();
//        } else {
//            daftarpaket.get(position).setIs_aktif("0");
////            notifyDataSetChanged();
//        }
//
//        if (model.getIs_aktif().equals("1")){
//            holder.checkBox.setChecked(true);
//        } else if (model.getIs_aktif().equals("0")){
//            holder.checkBox.setChecked(false);
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
}
