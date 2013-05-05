package com.example.talentdonation.student;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.talentdonation.R;
import com.example.talentdonation.contentsmanager.ContentManager;
import com.example.talentdonation.utils.MessageUtils;

public class SelectContentsActivity extends Activity{
	private AQuery aq;
	ContentManager contentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_contents);
		contentManager = new ContentManager();
		contentManager.ParseContentCSV();
		
		aq = new AQuery(this);
		
		String uri = "tel:" + "01051729171".trim();
		Intent intent1 = new Intent(Intent.ACTION_CALL);
		intent1.setData(Uri.parse(uri));
		startActivity(intent1);
		
		//List view setting
		ListView contentListView = (ListView) findViewById(R.id.contentsListView);
		contentListView.setOnItemClickListener(listViewClickListener);
		final ArrayAdapter<String> adapter;
		final ArrayList<String> contentList = new ArrayList<String>();
		
		//content 받아와서 처리
		List<String> list = new ArrayList<String>();
		list = contentManager.getLessonNames();
		
		
		//select level
		Intent intent = getIntent();
		String level = intent.getStringExtra("level");
		if(level.equals("easy")) {
			contentList.addAll(list);
		} else if(level.equals("normal")) {
			contentList.add("content 2");
			contentList.add("content 2");
			contentList.add("content 2");
			contentList.add("content 2");
		} else if(level.equals("hard")) {
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
			contentList.add("content 3");
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contentList);
		contentListView.setAdapter(adapter);
		
	}
	
	/**
	 * list view click listener
	 */
	OnItemClickListener listViewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int position,
				long id) {
			com.example.talentdonation.contentsmanager.Script s = contentManager.GetScript(position);
			Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
			
			studentMatchFind("4", "" + position); //실제로 자신의 student id 가져와야됨
		}
		
	};
	
	/**
	 * 
	 * @param sid
	 * match_id loaded
	 */
	public void studentMatchFind(String sid, String cname) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sid", sid));
		params.add(new BasicNameValuePair("cname", cname));
		
		URI url = null;
		try {
			url = URIUtils.createURI("http", MessageUtils.SERVER_ADDRESS, -1, MessageUtils.STUDENT_MATCH_FIND, URLEncodedUtils.format(params, "UTF-8"), null);
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

					if ("success_student_match_find".equals(statusResult)) {
						Toast.makeText(getApplicationContext(), "매치되었습니다",Toast.LENGTH_LONG).show();
						String matchId = object.getString("match_id");
						// handlecallback()  //match id 프리퍼런스에 저장하기
						String phoneNum = object.getString("phone");
						Log.e("teacher phone:", phoneNum);
						
						String uri = "tel:" + phoneNum.trim();
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse(uri));
						startActivity(intent);
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

}
