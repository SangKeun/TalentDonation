package com.example.talentdonation;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        startActivity(new Intent(this, FullScriptActivity.class));
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        MediaPlayer player = new MediaPlayer();
        
        try {
        player.setDataSource(sdPath + "/test1.mp3");
        player.prepare();
        player.start();
        player.seekTo(100000);
        } catch (Exception e) {
        	Log.e("ERROR", "Error: " + e.getMessage());
        }
        player.stop();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
