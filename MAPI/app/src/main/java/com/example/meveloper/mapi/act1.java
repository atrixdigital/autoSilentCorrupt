package com.example.meveloper.mapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class act1 extends YouTubeBaseActivity {

    //Button play;
    YouTubePlayerView yt;
    YouTubePlayer.OnInitializedListener lis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);

        //play=(Button)findViewById(R.id.button);
        yt=(YouTubePlayerView)findViewById(R.id.view);

        lis= new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


                youTubePlayer.loadVideo("C_RaGSxkC8I");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


                Toast.makeText(act1.this, "Not gonna play", Toast.LENGTH_SHORT).show();
            }
        };

        /*play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/
        yt.initialize("AIzaSyB9Bav7TRojU3CKrb7D7Eor1goFwKRq9_8",lis);
    }
}
