package com.example.talentdonation.teacher;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.talentdonation.GlobalApplication;
import com.example.talentdonation.R;
import com.example.talentdonation.utils.MessageUtils;

public class WaitingActivity extends Activity {
	
	private AQuery aq;
	private int waitingMinutes = 5;
	private Activity me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
		
		aq = new AQuery(this);
		me = this;
		
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

		// get view to set events to them
		Button btn_confirm = (Button)findViewById(R.id.bt_waiting_time);
		Spinner sp_waiting = (Spinner)findViewById(R.id.sp_waiting_time);
		
		// handle spinner event
		sp_waiting.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Log.e("pos", pos + "");
				Log.e("id", id + "");
				waitingMinutes = Integer.parseInt(parent.getItemAtPosition(pos).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// button event
		btn_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GlobalApplication globalApp = (GlobalApplication)getApplication();
				int tid = globalApp.getTid();
				
				// send info to server
				teacherMatchMake(tid+"", waitingMinutes);
			}
		});
	}
	
	// handle when making match request succeeded
	private void handleMatchMakeResponse(int queueId){
		// add queue id to global app
		GlobalApplication globalApp = (GlobalApplication)getApplication();
		globalApp.setQid(queueId);

		// start loading bar
		ProgressBar probar = (ProgressBar)findViewById(R.id.probar_loading);
		probar.setVisibility(View.VISIBLE);
	}
	
	// send a request making match waiting to server
	private void teacherMatchMake(String tid, int time) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tid", tid));
		params.add(new BasicNameValuePair("time", ""+time));
		
		Log.e("tid", "" + tid);
		Log.e("time", "" + time);
		
		URI url = null;
		try {
			url = URIUtils.createURI("http", MessageUtils.SERVER_ADDRESS, -1, MessageUtils.TEACHER_MATCH_MAKE, URLEncodedUtils.format(params, "UTF-8"), null);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		Log.e("URL : ", ""+url);
		
		aq.ajax(""+url, JSONObject.class, new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object,	AjaxStatus status) {
				Log.e("status", ""+status.getCode());
				Log.e("JSON", ""+object);
				String statusResult = null;
				try {
					statusResult = object.getString("status");

					if ("success_teacher_match_make".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "연결 되었습니다",Toast.LENGTH_LONG).show();
						String qid = object.getString("queue_id");
						
						// handle the response
						handleMatchMakeResponse(Integer.parseInt(qid));
					} else if ("failed_teacher_does_not_exists".equals(statusResult)) {
						Toast.makeText(getApplicationContext(),"선생님이 존재하지 않습니다", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "시스템 에러",Toast.LENGTH_LONG).show();
						Log.e("error", statusResult);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});
	}
}
