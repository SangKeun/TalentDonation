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

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.talentdonation.GlobalApplication;
import com.example.talentdonation.student.ClientStudyActivity;
import com.example.talentdonation.utils.MessageUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class DetectingIncomingCallReceiver extends BroadcastReceiver{
	private Context brContext;
	private boolean firstCall = true;
    
    private AQuery aq;
    private BroadcastReceiver br;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.brContext = context;
		
		aq = new AQuery(context);
		
		
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		int events = PhoneStateListener.LISTEN_CALL_STATE;
		
		tm.listen(new PhoneStateListener(){

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
	            switch (state) {
		            case TelephonyManager.CALL_STATE_IDLE:
		                break;
		            case TelephonyManager.CALL_STATE_RINGING:
		                break;
		            case TelephonyManager.CALL_STATE_OFFHOOK:
		            	Log.e("DetectingIncomingCallReceiver :", "call activity");
		            	
		            	//preference에서 자신이 학생인지 선생인지 가져오는게 필요, 컨텐츠
		            	
		            	/**
		            	 * 선생쪽
		            	 
		            	GlobalApplication application = (GlobalApplication)brContext.getApplicationContext();
		            	String tid = "" + application.getTid();
		            	String qid = "" + application.getQid();
		            	teacherMatchJoin(tid, qid);
		            	*/
		            	
		    			Intent intent = new Intent(brContext, ClientStudyActivity.class);
		            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            	brContext.startActivity(intent);
		            	
		            	
		            	
		       /*     	if(firstCall){
							Intent intent = new Intent(brContext, TeachingActivity.class);
			            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			            	brContext.startActivity(intent);
			            	firstCall = false;
		            	}*/
		            	
		            	/*Thread thread = new Thread(){

							@Override
							public void run() {
								try {
									sleep(3000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								Intent intent = new Intent(brContext, ShowActivity.class);
				            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				            	brContext.startActivity(intent);
							}
		            		
		            	};
		            	thread.start();*/
		            	
		                break;
	            }
				
				super.onCallStateChanged(state, incomingNumber);
			}
			
		}, events);
	}
	
	private void handleRequestJoin(String contentsName, String matchId){
		GlobalApplication application = (GlobalApplication)brContext.getApplicationContext();
		
		application.setContentsName(contentsName);
		application.setMid(Integer.parseInt(matchId));
		
		if(firstCall){
			Intent intent = new Intent(brContext, TeachingActivity.class);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	brContext.startActivity(intent);
        	firstCall = false;
    	}
	}
	
	
	private void teacherMatchJoin(String tid, String qid) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tid", tid));
		params.add(new BasicNameValuePair("qid", qid));
		
		URI url = null;
		try {
			url = URIUtils.createURI("http", MessageUtils.SERVER_ADDRESS, -1, MessageUtils.TEACHER_MATCH_JOIN, URLEncodedUtils.format(params, "UTF-8"), null);
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

					if ("success_teacher_match_join".equals(statusResult)) {
						Toast.makeText(brContext, "연결 되었습니다",Toast.LENGTH_LONG).show();
						String contentName = object.getString("content_name");
						String matchId = object.getString("match_id");
						
						handleRequestJoin(contentName, matchId);
						// handlecallback()
					} else if ("failed_teacher_does_not_exists"
							.equals(statusResult)) {
						Toast.makeText(brContext,"해당 선생님이 존재하지 않습니다", Toast.LENGTH_LONG).show();
					} else if ("failed_queue_does_not_exists"
							.equals(statusResult)) {
						Toast.makeText(brContext,"큐가 존재하지 않습니다", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(brContext, "시스템 에러",Toast.LENGTH_LONG).show();
						Log.e("error", statusResult);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});
	}
}
