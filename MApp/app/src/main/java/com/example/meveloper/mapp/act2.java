package com.example.meveloper.mapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class act2 extends AppCompatActivity {

    Button b,submit,get;
    ToggleButton tb;
    EditText name,contact,desig;
    ListView l1;
    dbhelper helper;


    List<String> list1 = new ArrayList<String>();
    ArrayAdapter<String> arraydap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);

        b=(Button)findViewById(R.id.button3);
        tb=(ToggleButton)findViewById(R.id.toggleButton);
        submit=(Button)findViewById(R.id.button7);
        get=(Button)findViewById(R.id.button8);
        name=(EditText) findViewById(R.id.editText2);
        contact=(EditText) findViewById(R.id.editText3);
        desig=(EditText) findViewById(R.id.editText4);
        l1=(ListView)findViewById(R.id.lv2);
        helper = new dbhelper(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if (name.getText().toString().isEmpty() && contact.getText().toString().isEmpty() && desig.getText().toString().isEmpty())
                    {
                        Toast.makeText(act2.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        boolean b = helper.insert(name.getText().toString(), contact.getText().toString(), desig.getText().toString());
                        if (b)
                        {
                            Toast.makeText(act2.this, "Row inserted successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(act2.this, "There was some problem inserting the row", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(act2.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Cursor c = helper.GetEmployees();
                    Toast.makeText(act2.this, String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();

                    if (c != null)
                    {
                        if (c.moveToFirst())
                        {
                            do
                            {
                                String id = c.getString(c.getColumnIndex(dbhelper.id));
                                String name = c.getString(c.getColumnIndex(dbhelper.names));
                                String con = c.getString(c.getColumnIndex(dbhelper.contact));
                                String desig = c.getString(c.getColumnIndex(dbhelper.designation));


                                String data =  id + "\n" + name + "\n" + con + "\n" + desig;

                                list1.add(data);
                            }
                            while (c.moveToNext());
                        }
                    }
                    else
                    {
                        Toast.makeText(act2.this, "NoData", Toast.LENGTH_SHORT).show();
                    }

                    arraydap = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list1);
                    l1.setAdapter(arraydap);
                }
                catch (Exception e)
                {
                    Toast.makeText(act2.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });




        Intent i = getIntent();
        String name = i.getStringExtra("Name");

        Toast.makeText(act2.this, name, Toast.LENGTH_SHORT).show();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(act2.this,act3.class);
                startActivity(i);
            }
        });

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = new Intent(getApplicationContext(),serviceclass.class);
                if(b)
                {
                    startService(intent);
                }
                else
                {
                    stopService(intent);
                }
            }
        });

    }
}
