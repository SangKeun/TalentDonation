package com.example.talentdonation.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.talentdonation.R;

public class WaitingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
		
		TabHost waitingTabHost= (TabHost)findViewById(R.id.waiting_tabhost);
		waitingTabHost.setup();
		
		TabHost.TabSpec spec = waitingTabHost.newTabSpec("tag1");
		spec.setContent(R.id.guidline);
		spec.setIndicator("Guideline");
		waitingTabHost.addTab(spec);
		
		spec = waitingTabHost.newTabSpec("tag2");
		spec.setContent(R.id.tip);
		spec.setIndicator("Tip");
		waitingTabHost.addTab(spec);
		
		waitingTabHost.setCurrentTab(0);
		
		TextView tv_guide = (TextView)findViewById(R.id.guidline);
		tv_guide.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent loading = new Intent(WaitingActivity.this, ScriptLoadingActivity.class);
				startActivity(loading);
				return false;
			}
		});
	}

}
