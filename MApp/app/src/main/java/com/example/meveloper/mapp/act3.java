package com.example.meveloper.mapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class act3 extends AppCompatActivity {

    TextView et;
    TextView et2;
    Button b;
    Button b2;
    ImageView img;
    ActionMode action;
    Button b5;
    Button b6;

    public int hour;
    public int minute;
    public int day;
    public int month;
    public int year;

    public static int dialogid=1;
    public static int dialogid2=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act3);

        et=(TextView) findViewById(R.id.textView3);
        et2=(TextView) findViewById(R.id.textView4);
        b=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button4);
        img=(ImageView)findViewById(R.id.imageView2);
        b5=(Button)findViewById(R.id.button5);
        b6=(Button)findViewById(R.id.button6);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dialogid);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dialogid2);
            }
        });

        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(action!=null)
                {
                    return false;
                }
                else
                {
                    action=act3.this.startSupportActionMode(callback);
                    return true;
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(act3.this,act4.class);
                startActivity(i);
                /*try
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(act3.this);
                    builder.setMessage("Exit?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(act3.this, "Enjoy", Toast.LENGTH_SHORT).show();

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                catch (Exception exp)
                {
                    Toast.makeText(act3.this, exp.toString(), Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

   ActionMode.Callback callback=new ActionMode.Callback() {
       @Override
       public boolean onCreateActionMode(ActionMode mode, Menu menu) {

           MenuInflater actioninflate = getMenuInflater();
           actioninflate.inflate(R.menu.actionmode,menu);
           return true;

       }

       @Override
       public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
           return false;
       }

       @Override
       public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
           return false;
       }

       @Override
       public void onDestroyActionMode(ActionMode mode) {

           action=null;

       }
   };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            et.setText(i+":"+i1);//show in that plane text field
            //Toast.makeText(act3.this, "hello baby", Toast.LENGTH_SHORT).show();
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            et2.setText(i+","+i1+","+i2);
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==dialogid)
        {
            Calendar calendar=Calendar.getInstance();
            hour=calendar.get(Calendar.HOUR);
            minute=calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(this,timeSetListener,hour,minute,false);
        }
        else if(id==dialogid2)
        {
            Calendar calendar=Calendar.getInstance();
            day=calendar.get(Calendar.DAY_OF_MONTH);
            month=calendar.get(Calendar.MONTH);
            year=calendar.get(Calendar.YEAR);

            return new DatePickerDialog(this,dateSetListener,year,month,day);
        }
        else
        {
            return null;
        }
    };



}
