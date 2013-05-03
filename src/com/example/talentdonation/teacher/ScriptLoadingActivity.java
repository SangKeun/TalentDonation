package com.example.talentdonation.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.talentdonation.R;

public class ScriptLoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_script_loading);
		
		Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				startActivity(new Intent(ScriptLoadingActivity.this, TeachingActivity.class));
				finish();
			}
			
		};
		
		handler.sendEmptyMessageDelayed(0, 10000);
	}

}
