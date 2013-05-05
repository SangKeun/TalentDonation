package com.example.talentdonation.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class Controller {
	
	private AQuery aq;
	
	public Controller(Activity activity) {
		aq = new AQuery(activity);
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
		
//		aq.ajax(url, params, JSONObject.class, "cbTeacherRegister", );
	}
}
