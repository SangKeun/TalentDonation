package com.example.talentdonation.student;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.talentdonation.GlobalApplication;
import com.example.talentdonation.R;
import com.example.talentdonation.contentsmanager.ContentManager;

public class ClientStudyActivity extends Activity {
	String sdPath;
	GlobalApplication var;
	ContentManager contentManager;
	String mp3Name;
	com.example.talentdonation.contentsmanager.Script s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_study);
		
		sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		Button listenButton = (Button)findViewById(R.id.listenAll);
		listenButton.setOnClickListener(clickListener);
		
		//content manager
		var = (GlobalApplication)getApplication();
		contentManager = new ContentManager();
		s = contentManager.GetScript(var.getPosition());
		mp3Name = contentManager.getMp3Name(var.getPosition());
		
		//list view 관련
		ListView listview = (ListView)findViewById(R.id.studentStudyListView);
		listview.setOnItemClickListener(listViewClickListener);
		final ArrayAdapter<String> adapter;
		final List<String> contentList = new ArrayList<String>();
		contentList.addAll(s.getStatement());
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contentList);
		listview.setAdapter(adapter);
	}
	
	/**
	 * list view click listener
	 */
	OnItemClickListener listViewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int position,
				long id) {
			
//			MediaPlayer player = new MediaPlayer();
//
//			try {
//				player.setDataSource(sdPath + "/" + mp3Name);
//				player.prepare();
//				player.start();
//			} catch (Exception e) {
//				Log.e("ERROR", "Error: " + e.getMessage());
//			}
		}
		
	};
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// listen all
			MediaPlayer player = new MediaPlayer();

			try {
				player.setDataSource(sdPath + "/" + mp3Name);
				player.prepare();
				player.start();
			} catch (Exception e) {
				Log.e("ERROR", "Error: " + e.getMessage());
			}
		}
	};

}
