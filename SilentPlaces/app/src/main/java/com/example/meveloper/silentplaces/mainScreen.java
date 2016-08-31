package com.example.meveloper.silentplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainScreen extends AppCompatActivity {

    Button mrkd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mrkd = (Button)findViewById(R.id.button2);



        mrkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreen.this,markedLocations.class);
                startActivity(intent);
            }
        });
    }
}
