package com.example.talentdonation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.talentdonation.utils.HttpClients;
import com.example.talentdonation.utils.MessageUtils;

public class MainActivity extends Activity {
	private AQuery aq;
	HttpClients client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new HttpClients();
        aq = new AQuery(this);
//        RegisterTeacher("신신신", 23, "asdf@adsfasdfa.sss", "male", "01027434713");
//        teacherMatchMake("123", 20); 	
        teacherMatchJoin("123", "32");
//        studentRegister("잼잼", 22, "male", "01023232323");
//        studentMatchFind("2");  //수정중
        
        
//        startActivity(new Intent(this, FullScriptActivity.class));
//        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        MediaPlayer player = new MediaPlayer();
//        
//        try {
//        player.setDataSource(sdPath + "/test1.mp3");
//        player.prepare();
//        player.start();
//        player.seekTo(100000);
//        } catch (Exception e) {
//        	Log.e("ERROR", "Error: " + e.getMessage());
//        }
//        player.stop();
        
    }
    
	/**
	 * register teacher account
	 * @param name
	 * @param age
	 * @param email
	 * @param gender
	 * @param phoneNum
	 * @return status code
	 */
	public void teacherRegister(String name, int age, String email, String gender, String phoneNum) {
		String url = "http://" + MessageUtils.SERVER_ADDRESS + MessageUtils.TEACHER_REGISTER;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("age", age);
		params.put("email", email);
		params.put("gender", gender);
		params.put("phone", phoneNum);
		
		Log.e("URL : ", ""+url);
		
		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object,	AjaxStatus status) {
				// Log.e("status", ""+status.getCode());
				// Log.e("JSON", ""+object);
				String statusResult = null;
				try {
					statusResult = object.getString("status");

					if ("success_teacher_register".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "등록되었습니다", Toast.LENGTH_LONG).show();
						String id = object.getString("queue_id");
						// handlecallback()
					} else if ("failed_email_duplicated".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "이미 등록된 이메일 입니다.", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "등록에 실패했습니다", Toast.LENGTH_LONG).show();
						Log.e("error", statusResult);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	/**
	 * 
	 * @param tid
	 * @param time
	 */
	public void teacherMatchMake(String tid, int time) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tid", tid));
		params.add(new BasicNameValuePair("time", ""+time));
		
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
						String id = object.getString("queue_id");
						// handlecallback()
					} else if ("failed_teacher_does_not_exists"
							.equals(statusResult)) {
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
	
	/**
	 * @param tid
	 * @param qid
	 */
	public void teacherMatchJoin(String tid, String qid) {
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
						Toast.makeText(getApplicationContext(), "연결 되었습니다",Toast.LENGTH_LONG).show();
						String contentName = object.getString("content_name");
						String matchId = object.getString("match_id");
						// handlecallback()
					} else if ("failed_teacher_does_not_exists"
							.equals(statusResult)) {
						Toast.makeText(getApplicationContext(),"해당 선생님이 존재하지 않습니다", Toast.LENGTH_LONG).show();
					} else if ("failed_queue_does_not_exists"
							.equals(statusResult)) {
						Toast.makeText(getApplicationContext(),"큐가 존재하지 않습니다", Toast.LENGTH_LONG).show();
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
	
	/**
	 * register student account
	 * @param name
	 * @param age
	 * @param gender
	 * @param phone
	 */
	public void studentRegister(String name, int age, String gender, String phone) {
		String url = "http://" + MessageUtils.SERVER_ADDRESS + MessageUtils.STUDENT_REGISTER;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("age", age);
		params.put("gender", gender);
		params.put("phone", phone);
		
		Log.e("URL : ", ""+url);
		
		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object,	AjaxStatus status) {
				Log.e("status", ""+status.getCode());
				Log.e("JSON", ""+object);
				String statusResult = null;
				try { 
					statusResult = object.getString("status");

					if ("success_student_register".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "등록 되었습니다",Toast.LENGTH_LONG).show();
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
	
	/**
	 * 
	 * @param sid
	 */
	public void studentMatchFind(String sid) {
		String url = "http://" + MessageUtils.SERVER_ADDRESS + MessageUtils.STUDENT_MATCH_FIND;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sid", sid);
		
		Log.e("URL : ", ""+url);
		
		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object,	AjaxStatus status) {
				Log.e("status", ""+status.getCode());
				Log.e("JSON", ""+object);
				String statusResult = null;
				try { 
					statusResult = object.getString("status");

					if ("success_student_match_find".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "매치되었습니다",Toast.LENGTH_LONG).show();
					} else if("failed_student_does_not_exists".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "학생 계정이 존재하지 않습니다",Toast.LENGTH_LONG).show();
					} else if("failed_no_teacher_to_match".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "선생님을 찾지 못했습니다",Toast.LENGTH_LONG).show();
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
	
	public void studentMatchFinish(String sid, String mid, String comment) {
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
