package com.example.talentdonation;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.talentdonation.teacher.WaitingActivity;
import com.example.talentdonation.utils.CommonValues;
import com.example.talentdonation.utils.MessageUtils;

public class RegisterActivity extends Activity implements RadioGroup.OnCheckedChangeListener{
	private EditText et_name, et_age, et_email, et_phone;
	private RadioGroup rg_gender;
	
	private String name, email, phone, gender = "male";
	private int age;
	
	private Activity me;
	private AQuery aq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		SharedPreferences prefs = getSharedPreferences(CommonValues.prefs_user_info, 0);
		int id = prefs.getInt("id", -1);
		
		if(id != -1){
			Intent intent = new Intent(RegisterActivity.this, WaitingActivity.class);
			startActivity(intent);
		}
		
		me = this;
		aq = new AQuery(this);		
		
		et_name = (EditText)findViewById(R.id.et_name);
		et_age = (EditText)findViewById(R.id.et_age);
		et_email = (EditText)findViewById(R.id.et_email);
		et_phone = (EditText)findViewById(R.id.et_phone);
		
		rg_gender = (RadioGroup)findViewById(R.id.rg_gender);
		
		rg_gender.setOnCheckedChangeListener(this);
		
 		Button bt_register_confirm = (Button)findViewById(R.id.bt_register_confirm);
 		bt_register_confirm.setOnClickListener(new View.OnClickListener() {
 			
			@Override
			public void onClick(View v) {
				name = et_name.getText().toString();
				String str_age = et_age.getText().toString();
				age = Integer.parseInt((str_age.equals("")? "13" : str_age));
				email = et_email.getText().toString();
				phone = et_phone.getText().toString();
				
				registerTeacher(name, age, email, gender, phone);
				
				/**
				 * 	after registering to server, add the user information into a sharedpreferences 
				 * 
				 * 
				 */
			}
		});
	}
	
	protected void handleCallbackResult(int id){
		
		SharedPreferences prefsUserInfo = getSharedPreferences(CommonValues.prefs_user_info, 0);
		Editor editor = prefsUserInfo.edit();
		
		editor.putInt("id", id);
		editor.putString("name", this.name);
		editor.putString("gender", this.gender);
		editor.putInt("age", this.age);
		editor.putString("email", this.email);
		editor.putString("phone", this.phone);
		
		editor.commit();		// commit to sharedPref
		
		// add id to global variables
		GlobalApplication globalApp = (GlobalApplication)getApplication();
		globalApp.setTid(id);
		
		// start waiting activity
		Intent intent = new Intent(RegisterActivity.this, WaitingActivity.class);
		startActivity(intent);
		
	}
	
	protected void registerTeacher(String name, int age, String email, String gender, String phoneNum) {
		String url = "http://" + MessageUtils.SERVER_ADDRESS + MessageUtils.REGISTER_TEACHER;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("age", age);
		params.put("email", email);
		params.put("gender", gender);
		params.put("phone", phoneNum);
		
		Log.e("URL : ", ""+url);
		Log.e("params : ", params.toString());
		
		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object,	AjaxStatus status) {
//				Log.e("status", ""+status.getCode());
//				Log.e("JSON", ""+object);
				String statusResult = null;
				
				Log.e("hello", status.getCode()+"");
				try {
					statusResult = object.getString("status");

					if("success_teacher_register".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "등록되었습니다", Toast.LENGTH_LONG).show();
						int id = object.getJSONObject("teacher").getInt("id");
						handleCallbackResult(id);
					} else if("failed_email_duplicated".equals(statusResult)) { 
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

	@Override
	public void onCheckedChanged(RadioGroup group, int rd_id) {
		if(group == rg_gender){
			if(rd_id == R.id.rd_male){
				gender = "male";
			}
			else if(rd_id == R.id.rd_female){
				gender = "female";
			}
			else{
				
			}
		}
	}
	
	protected void isFullEntered(){
		
		
	}


}
