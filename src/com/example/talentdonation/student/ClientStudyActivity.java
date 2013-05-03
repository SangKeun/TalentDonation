package com.example.talentdonation.student;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.talentdonation.EvaluationActivity;
import com.example.talentdonation.R;

public class ClientStudyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_study);
		
		ImageView iv_content = (ImageView)findViewById(R.id.iv_content);
		iv_content.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent evaluation = new Intent(ClientStudyActivity.this, EvaluationActivity.class);
				ClientStudyActivity.this.startActivity(evaluation);
			}
			
		});
	}

}
