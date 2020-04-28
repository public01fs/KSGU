package com.application.ksgu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.application.ksgu.Model.Kapal;
import com.application.ksgu.Model.Negara;
import com.application.ksgu.R;

import java.util.ArrayList;
import java.util.List;

public class KapalAdapter extends BaseAdapter implements Filterable {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Kapal> newsItems;
    private List<Kapal> supp;
    private List<Kapal> suppall;
    //private Filter filter = new CustomFilter();

    public KapalAdapter(Activity activity) {
        this.activity = activity;
        this.newsItems = new ArrayList<>();
    }

    public void setData(List<Kapal> list) {
        newsItems.clear();
        newsItems.addAll(list);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((Kapal) resultValue).getValue();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = newsItems;
                    filterResults.count = newsItems .size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    notifyDataSetInvalidated();
                }

            }
        };
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    public Kapal getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_auto, null);

        try {
            TextView nama = (TextView) convertView.findViewById(R.id.tv_auto);

            Kapal m = getItem(position);

            nama.setText(m.getLabel()
            );

        } catch (Exception ex) {
            //Toast.makeText(parent.getContext(), "Error : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return convertView;

    }

    private static class ViewHolder {
        TextView nama;
    }


}
