package com.example.meveloper.autosilent;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;

public class act3 extends AppCompatActivity {

    ListView listView;
    dbhelper helper;
    EditText locName;

    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> arrayadap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act3);

        listView = (ListView)findViewById(R.id.listView);
        helper = new dbhelper(this);

        registerForContextMenu(listView);

        try{
            Cursor c = helper.GetLocations();
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String id = c.getString(c.getColumnIndex(dbhelper.id));
                        String name = c.getString(c.getColumnIndex(dbhelper.name));

                        String data ="ID: " + id + "  Location: " +  name;

                        list.add(data);
                        //list.set(iid,data);
                        //list.add(iid,data);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        if(item.getItemId()==R.id.edit){
            View view = LayoutInflater.from(act3.this).inflate(R.layout.edit_location_name_dialog,null);
            locName = (EditText)view.findViewById(R.id.locationname);
            AlertDialog.Builder builder = new AlertDialog.Builder(act3.this);
            builder.setMessage("Rename Location")
                    .setView(view)
                    .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            String rename = locName.getText().toString();
                            String sid = list.get(info.position).substring(4,5);
                            int id = Integer.parseInt(sid);

                            try{
                                helper.update(id,rename);
                                Toast.makeText(act3.this, "Location Successfully Updated", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                Toast.makeText(act3.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            Intent in = new Intent(act3.this,act3.class);
                            finish();
                            startActivity(in);
                        }
                    })
                    .setNegativeButton("Cancel",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if(item.getItemId()==R.id.del){
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String sid =list.get(info.position).substring(4,5);
            int id = Integer.parseInt(sid);

            try {
                helper.delete(id);
            }
            catch (Exception e){
                Toast.makeText(act3.this, e.toString(), Toast.LENGTH_LONG).show();
            }
            list.remove(info.position);
            arrayadap.notifyDataSetChanged();

            //Toast.makeText(act3.this, sid, Toast.LENGTH_SHORT).show();
            //Toast.makeText(act3.this, list.toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(act3.this, listView.findFocus().toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(act3.this, info.targetView.findViewById().toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(act3.this, item.getMenuInfo().toString(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}