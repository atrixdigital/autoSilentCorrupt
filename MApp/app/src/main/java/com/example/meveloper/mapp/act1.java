package com.example.meveloper.mapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class act1 extends AppCompatActivity {

    //variables
    Button b;
    EditText et;
    RadioButton r;
    ListView lv;

    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> arrayadap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);

        //get variables' data y'know
        b=(Button)findViewById(R.id.button);
        et=(EditText)findViewById(R.id.editText);
        r=(RadioButton)findViewById(R.id.radioButton);
        lv=(ListView)findViewById(R.id.listView);

        Intent sms = new Intent(getApplicationContext(),sms_service.class);
        startService(sms);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText().toString().isEmpty())
                {
                    Toast.makeText(act1.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name = et.getText().toString();
                    Intent i = new Intent(act1.this,act2.class);
                    i.putExtra("Name",name);
                    
                    startActivity(i);
                }
            }
        });

        r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(act1.this, "Radio Button Checked", Toast.LENGTH_SHORT).show();
            }
        });

        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");

        arrayadap = new ArrayAdapter<String>(act1.this,R.layout.layoutresfile1,R.id.List,list);
        registerForContextMenu(lv);

        lv.setAdapter(arrayadap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(act1.this, list.get(i)+" Clicked", Toast.LENGTH_SHORT).show();
             /*   if(list.get(i)=="A")
                {
                    Toast.makeText(act1.this, "A Clicked", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Checkpermission(this,android.Manifest.permission.RECEIVE_SMS,1);


    }

    public void Checkpermission(Activity activity, String permission, int requestcode)
    {
        if(ActivityCompat.checkSelfPermission(activity,permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity, new String[]{permission},requestcode);
        }
        else
        {
            startService(new Intent(this,sms_service.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                startService(new Intent(this,sms_service.class));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        if(item.getItemId()==R.id.red)
        {
            relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
        else if(item.getItemId()==R.id.green)
        {
            relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
        else if(item.getItemId()==R.id.blue)
        {
            relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        }
        else if(item.getItemId()==R.id.orange)
        {
            relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add)
        {
            Toast.makeText(act1.this, "Added", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.del)
        {
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            list.remove(info.position);
            arrayadap.notifyDataSetChanged();

            Toast.makeText(act1.this, "Deleted", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.edit)
        {
            Toast.makeText(act1.this, "Edited", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
