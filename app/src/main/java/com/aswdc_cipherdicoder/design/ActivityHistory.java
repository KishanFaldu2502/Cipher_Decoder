package com.aswdc_cipherdicoder.design;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.aswdc_cipherdicoder.R;
import com.aswdc_cipherdicoder.adapter.HistoryAdaptor;
import com.aswdc_cipherdicoder.bean.Bean_History;
import com.aswdc_cipherdicoder.dbHelper.DBHistory;

import java.util.ArrayList;

public class ActivityHistory extends AppCompatActivity {

    ListView listhistory;
    ArrayList<Bean_History> historyList;
    DBHistory dbHistory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.list_history);
        init();
        listhistory=findViewById(R.id.listhistory_list_lst);
        historyList=dbHistory.gethistory();
        listhistory.setAdapter(new HistoryAdaptor(historyList,this));

    }
    void init()
    {
        dbHistory=new DBHistory(this);
    }
}
