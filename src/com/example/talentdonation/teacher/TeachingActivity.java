package com.example.talentdonation.teacher;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.talentdonation.GlobalApplication;
import com.example.talentdonation.R;
import com.example.talentdonation.contentsmanager.ContentManager;
import com.example.talentdonation.contentsmanager.Script;
import com.example.talentdonation.contentsmanager.Statement;

public class TeachingActivity extends Activity {
	private ContentManager cm;
	private int currentStep = 0;
	private Script script;
	
	private String[] guideTitles;
	private String[] guideContents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teaching);
		
		cm = new ContentManager();
		cm.ParseContentCSV();
		
		// get contents name that the student selected
		script = cm.GetScript(((GlobalApplication)getApplication()).getContentsName());
		//script = cm.GetScript("1-1");
		
		// make listView
		List<Statement> list_statements = script.getStatements();
		List<String> list_string_statements = new ArrayList<String>();
		
		for(Statement s : list_statements){
			list_string_statements.add(s.toString());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_string_statements);
		
		ListView lv_script = (ListView)findViewById(R.id.lv_script);
		lv_script.setAdapter(adapter);
		
		// set guide line part
		final TextView tv_guide_title = (TextView)findViewById(R.id.tv_guide_title);
		final TextView tv_guide_content = (TextView)findViewById(R.id.tv_guide_content);
		
		guideTitles = getResources().getStringArray(R.array.guide_titles);
		guideContents = getResources().getStringArray(R.array.guide_contents);
		
		tv_guide_title.setText(guideTitles[currentStep]);
		tv_guide_content.setText(guideContents[currentStep]);
		
		// set next step button events
		Button bt_next_step = (Button)findViewById(R.id.bt_next_step);
		bt_next_step.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentStep < 5){
					currentStep++;
					
					tv_guide_title.setText(guideTitles[currentStep]);
					if(currentStep == 2)		// step 3. teaching presentation
						tv_guide_content.setText(guideContents[currentStep] + "\n" +
								script.toStringDescriptions() + "\n" + script.toStringExpressions());
					else
						tv_guide_content.setText(guideContents[currentStep]);
				}
			}
		});

	}

}
