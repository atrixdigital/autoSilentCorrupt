package com.example.meveloper.autosilent1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class act3 extends AppCompatActivity {

    ListView listView;
    dbhelper helper;

    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> arrayadap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act3);

        listView = (ListView)findViewById(R.id.listView);
        helper = new dbhelper(this);

        try{
            Cursor c = helper.GetLocations();
            Toast.makeText(act3.this, String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String id = c.getString(c.getColumnIndex(dbhelper.id));
                        String name = c.getString(c.getColumnIndex(dbhelper.name));


                        String data =  id + ":" + name + "\n";

                        list.add(data);
                    }
                    while (c.moveToNext());
                }
            }
            else
            {
                Toast.makeText(act3.this, "NoData", Toast.LENGTH_SHORT).show();
            }
            arrayadap = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(arrayadap);
        }
        catch (Exception e){
            Toast.makeText(act3.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}