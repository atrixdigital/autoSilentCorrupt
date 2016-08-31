package com.example.meveloper.mapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class act4 extends AppCompatActivity {

    Button get;
    ListView lv;

    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> arrayadap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act4);

        get=(Button)findViewById(R.id.button9);
        lv=(ListView)findViewById(R.id.listView2);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    list.clear();
                    ContentResolver cr = getContentResolver();
                    Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

                    if(c.moveToFirst())
                    {
                        do
                        {
                            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?", new String[]{id},null);
                            if(pCur.moveToFirst())
                            {
                                String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String name2 = pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                list.add(name2+ " : "+phone);
                            }
                        }
                        while (c.moveToNext());
                    }

                    arrayadap = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
                    lv.setAdapter(arrayadap);
                }
                catch (Exception e)
                {
                    Toast.makeText(act4.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
