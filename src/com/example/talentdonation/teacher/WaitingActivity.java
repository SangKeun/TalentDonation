package com.example.talentdonation.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;

import com.example.talentdonation.GlobalApplication;
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
		
		
		
		Button btn_confirm = (Button)findViewById(R.id.bt_waiting_time);
		Spinner sp_waiting = (Spinner)findViewById(R.id.sp_waiting_time);
		
		btn_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/* 
				 * add waiting status to server
				 * it means that the teacher is waiting to teach
				 * 
				 */
				ProgressBar probar = (ProgressBar)findViewById(R.id.probar_loading);
				probar.setVisibility(View.VISIBLE);
	//			handleResponse(1);
			}
		});
	}
	
	private void handleResponse(int queueId){
		GlobalApplication globalApp = (GlobalApplication)getApplication();
		globalApp.setQid(queueId);
		
		
	}
}
