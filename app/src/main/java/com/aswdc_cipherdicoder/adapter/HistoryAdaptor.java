package com.aswdc_cipherdicoder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aswdc_cipherdicoder.R;
import com.aswdc_cipherdicoder.bean.Bean_History;

import java.util.ArrayList;

public class HistoryAdaptor extends BaseAdapter {

    ArrayList<Bean_History> bean_history;
    Context context;
    LayoutInflater layoutInflater;

    public HistoryAdaptor(ArrayList<Bean_History> bean_history, Context context)
    {
        this.bean_history=bean_history;
        this.context=context;
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bean_history.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=layoutInflater.inflate(R.layout.list_item_history,null);
        TextView tvplaintext=v.findViewById(R.id.tvplaintext);
        TextView tvciphermethod=v.findViewById(R.id.tvciphermethod);
        TextView tvcipherresult=v.findViewById(R.id.tvcipherresult);

        tvplaintext.setText(bean_history.get(position).getPlain_Text());
        tvciphermethod.setText(bean_history.get(position).getCipher_Method());
        tvcipherresult.setText(bean_history.get(position).getCipher_Result());
        return v;
    }
}
