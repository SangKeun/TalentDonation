package com.example.talentdonation.contentsmanager;

public class Expression{
	public String SentenceEng;
	public String SentenceKor;
	
	public Expression(String s0, String s1){
		SentenceEng = s0;
		SentenceKor = s1;
	}
	
	@Override
	public String toString(){
		return String.format("Eng:%s // Kor:%s", SentenceEng, SentenceKor);
	}
}
