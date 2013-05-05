package com.example.talentdonation.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.talentdonation.R;

public class SelectActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_level);
		
		Button easyButton = (Button) findViewById(R.id.btSelectEasy);
		Button normalButton = (Button) findViewById(R.id.btSelectNormal);
		Button hardButton = (Button) findViewById(R.id.btSelectHard);
		
		easyButton.setOnClickListener(clickListener);
		normalButton.setOnClickListener(clickListener);
		hardButton.setOnClickListener(clickListener);
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String level = null;
			switch(v.getId()) {
				case R.id.btSelectEasy:
					level = "easy";
					break;
				case R.id.btSelectNormal:
					level = "normal";
					break;
				case R.id.btSelectHard:
					level = "hard";
					break;
			}
			
			Intent intent = new Intent(SelectActivity.this, SelectContentsActivity.class);
			intent.putExtra("level", level);
			startActivity(intent);
		}
	};
}
