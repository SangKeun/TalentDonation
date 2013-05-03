package com.example.talentdonation;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FullScriptActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_script);
		
		ImageView scriptImageView = (ImageView) findViewById(R.id.scriptImageView);
		TextView loadingTextView = (TextView) findViewById(R.id.LoadingTextView);
		LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
		ListView scriptListView = (ListView) findViewById(R.id.scriptListView);
		
		//visibilty setting
		scriptImageView.setVisibility(View.VISIBLE);
		loadingTextView.setVisibility(View.GONE);
		buttonLayout.setVisibility(View.GONE);
		scriptListView.setVisibility(View.VISIBLE);
		
		final ArrayAdapter<String> adapter;

		//script에 들어갈 내용 받아와서 setting
		final ArrayList<String> scriptSentences =  new ArrayList<String>();
		scriptSentences.add("A: Hello");
		scriptSentences.add("B: Hi");
		scriptSentences.add("A: What is your name?");
		scriptSentences.add("B: My name is Lisa");
		scriptSentences.add("A: What a beautiful name");
		scriptSentences.add("B: Thank you so much");
		scriptSentences.add("A: How old are you?");
		scriptSentences.add("B: I am 7 years old. How about you?");
		scriptSentences.add("A: Me too.");
		scriptSentences.add("B: What a coincidence!");
		scriptSentences.add("A: Oops, I have to go.");
		scriptSentences.add("B: Bye ~");
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scriptSentences);
		
		scriptListView.setAdapter(adapter);
		
	}

}
