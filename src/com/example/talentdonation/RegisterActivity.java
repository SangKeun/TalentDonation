package com.example.talentdonation;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity implements RadioGroup.OnCheckedChangeListener{
	private EditText et_name, et_age, et_email, et_phone;
	private RadioGroup rg_gender;
	
	private String name, email, phone, gender;
	private int age;
	
	private Activity me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		me = this;
		
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
				
				Toast toast = Toast.makeText(me, "register", Toast.LENGTH_SHORT);
				toast.show();
				
//				Intent intent = new Intent(packageContext, cls)
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
