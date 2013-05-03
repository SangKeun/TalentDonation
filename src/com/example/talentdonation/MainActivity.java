package com.example.talentdonation;

import java.util.HashMap;
import java.util.Map;

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
        RegisterTeacher("신신신", 23, "asdf@adsfasdfa.sss", "male", "01027434713");
        
        
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
	public void RegisterTeacher(String name, int age, String email, String gender, String phoneNum) {
		String url = "http://" + MessageUtils.SERVER_ADDRESS + MessageUtils.REGISTER_TEACHER;
		
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
