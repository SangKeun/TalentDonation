package com.example.talentdonation;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.talentdonation.utils.MessageUtils;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private AQuery aq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		aq = new AQuery(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
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
	public void RegisterTeacher(String name, int age, String email, String gender, String phoneNum) {
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
//				Log.e("status", ""+status.getCode());
//				Log.e("JSON", ""+object);
				String statusResult = null;
				try {
					statusResult = object.getString("status");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if("success_teacher_register".equals(statusResult)) {
					Toast.makeText(getApplicationContext(), "등록되었습니다", Toast.LENGTH_LONG).show();
				} else if("failed_email_duplicated".equals(statusResult)) { 
					Toast.makeText(getApplicationContext(), "이미 등록된 이메일 입니다.", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "등록에 실패했습니다", Toast.LENGTH_LONG).show();
					Log.e("error", statusResult);
				}
				
			}
		});
	}

}
