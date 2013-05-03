package com.example.talentdonation;

import android.app.Application;

public class GlobalApplication extends Application{
	private int tid;		// unique id
	private int qid;		// matching waiting q id
	private int mid;		// matching id
	
	public int getTid() {
		return tid;
	}
	
	public void setTid(int tid) {
		this.tid = tid;
	}
	
	public int getQid() {
		return qid;
	}
	
	public void setQid(int qid) {
		this.qid = qid;
	}
	
	public int getMid() {
		return mid;
	}
	
	public void setMid(int mid) {
		this.mid = mid;
	}	
}