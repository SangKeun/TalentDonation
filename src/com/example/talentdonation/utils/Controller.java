package com.example.talentdonation.utils;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Controller {
	
	HttpClients client;
	
	public Controller() {
		client = new HttpClients();
	}
	
	/**
	 * 
	 * @param tid
	 * @param time
	 * @return
	 */
	public String makeTeacherQueue(String tid, int time) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tid", tid));
		params.add(new BasicNameValuePair("time", ""+time));
		String data = client.getUrlToJson(MessageUtils.MAKE_TEACHER, params);
		return client.getResultString(data, "queue_id");
	}
}
