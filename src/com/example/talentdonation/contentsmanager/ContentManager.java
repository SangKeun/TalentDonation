package com.example.talentdonation.contentsmanager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import android.os.Environment;
import android.util.Log;

import com.example.talentdonation.contentsmanager.opencsv.CSVReader;



public class ContentManager {
	private HashMap<String, Lesson>  Lessons = new HashMap<String, Lesson> ();
		
	public void ParseContentCSV(){
		try {
			//
			String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			CSVReader content_reader = new CSVReader (new FileReader(rootPath + "/content.csv"));
			CSVReader script_reader = new CSVReader (new FileReader(rootPath + "/script.csv"));
			
			//I fix opencsv readall function return type to arraylist. get by index is O(1)
			ArrayList<String[]> raw_contents = content_reader.readAll();
			ArrayList<String[]> raw_scripts = script_reader.readAll();
			
			for(int i=6; i<raw_contents.size(); ++i)
			{
				String[] data = raw_contents.get(i);
				Lesson lesson = new Lesson(data[0],data[1],data[2],data[3],data[4],data[5],data[6]);
				
				Script script = new Script();
				for(int j=lesson.pos_script; j<lesson.pos_description; ++j){
					String[] token = raw_scripts.get(j+6);
					Statement s = new Statement(token[0], token[1], token[2], token[3], token[4]);
					script.AddStatement(s);
				}
				for(int j=lesson.pos_description; j<lesson.pos_expression; ++j){
					String[] token = raw_scripts.get(j+6);
					script.AddDescription(token[0].replace('\n', '\0'));
				}
				for(int j=lesson.pos_expression; j<lesson.pos_end; ++j){
					String[] token = raw_scripts.get(j+6);
					Expression e = new Expression(token[0], token[1]);
					script.AddExpression(e);
				}
				
				lesson.SetScript(script);
				Lessons.put(lesson.Name, lesson);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Get functions
	
	public List<Lesson> GetLessonList(){
		return new ArrayList<Lesson>(Lessons.values());
	}
	
	public List<String> getLessonNames() {
		SortedSet<String> keys = new TreeSet<String>(Lessons.keySet());
		List<String> result = new ArrayList<String>();
		for (String key : keys) { 
			result.add(Lessons.get(key).LessonName);
		   // do something
		}
		return result;
	}
		
	public Script GetScript(int index){
		SortedSet<String> keys = new TreeSet<String>(Lessons.keySet());		
		List<String> result = new ArrayList<String>();
		for (String key : keys) { 
			result.add(Lessons.get(key).Name);
		   // do something
		}
		return Lessons.get(result.get(index)).GetScript();
	}
	
	public Script GetScript(String name){
		return Lessons.get(name).GetScript();
	}
	
	public String getMp3Name(int index) {
		SortedSet<String> keys = new TreeSet<String>(Lessons.keySet());		
		for (String key : keys) {
			if(index == 0)
				return key;
			index--;
		}
		return null;
	}

	
	public void test(){
		SortedSet<String> keys = new TreeSet<String>(Lessons.keySet());
		for (String key : keys) { 
			System.out.print(Lessons.get(key).toString());
		   // do something
		}
	}
	
	
}
