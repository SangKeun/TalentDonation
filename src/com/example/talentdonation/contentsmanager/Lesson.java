package com.example.talentdonation.contentsmanager;
public class Lesson{
	public String LessonName;
	public String Mp3;
	public String Name;
	public int pos_script;
	public int pos_description;
	public int pos_expression;
	public int pos_end;
	
	public Lesson(String s0, String s1, String s2, String s3, String s4, String s5, String s6){
		LessonName = s0;
		Mp3 = s1;
		Name = s2;
		pos_script = Integer.parseInt(s3);
		pos_description = Integer.parseInt(s4);
		pos_expression = Integer.parseInt(s5);
		pos_end = Integer.parseInt(s6);
	}
	
	private Script _script;
	public void SetScript(Script s){
		_script = s;
	}
	
	public Script GetScript(){
		return _script;
	}
	
	@Override
	public String toString(){
		String r = "Lesson " + Name + "\r\n";
		r+= String.format("%s / %s / %d / %d / %d / %d\r\n", LessonName, Mp3, pos_script, pos_description, pos_expression, pos_end);
		r+= "<Script>\r\n";
		r+= _script.toString();
		return r;
	}
}