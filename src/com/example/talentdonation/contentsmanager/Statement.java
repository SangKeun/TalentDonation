package com.example.talentdonation.contentsmanager;

public class Statement{
	public String Name;
	public String StatementEng;
	public String StatementKor;
	public int sound_start;
	public int sound_end;
	
	public Statement(String s0, String s1, String s2, String s3, String s4){
		Name = s0;
		StatementEng = s1;
		StatementKor = s2;
		sound_start = Integer.parseInt(s3);
		sound_end = Integer.parseInt(s4);
	}
	
	@Override
	public String toString(){
		return String.format("%s:%s // %s (%d-%d초)", Name, StatementEng, StatementKor, sound_start, sound_end);
	}
}
