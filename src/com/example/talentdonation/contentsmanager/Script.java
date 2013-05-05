package com.example.talentdonation.contentsmanager;

import java.util.ArrayList;
import java.util.List;

public class Script{
	public List<Statement> Statements;
	public List<String> Descriptions;
	public List<Expression> Expressions;
	
	public List<Statement> getStatements() {
		return Statements;
	}

	public void setStatements(List<Statement> statements) {
		Statements = statements;
	}

	public List<String> getDescriptions() {
		return Descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		Descriptions = descriptions;
	}

	public List<Expression> getExpressions() {
		return Expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		Expressions = expressions;
	}

	public Script(){
		Statements = new ArrayList<Statement>();
		Descriptions = new ArrayList<String>();
		Expressions = new ArrayList<Expression>();
	}
	
	public void AddStatement(Statement s){
		Statements.add(s);
	}
	
	public void AddDescription(String s){
		Descriptions.add(s);
	}
	
	public void AddExpression(Expression e){
		Expressions.add(e);
	}
	
	public List<String> getStatement() {
		List<String> result = new ArrayList<String>();
		for(Statement s : Statements) {
			result.add(s.toString());
		}
		return null;
	}

	@Override
	public String toString(){
		String r = "대화\r\n";
		for(Statement s : Statements){
			r += s.toString() + "\r\n";
		}
		r += "설명\r\n";
		for(String s : Descriptions){
			r += s.toString() + "\r\n";
		}
		r += "표현\r\n";
		for(Expression s : Expressions){
			r += s.toString() + "\r\n";
		}
		return r;
	}
	
	public String toStringDescriptions(){
		String r = "";
		for(String s : Descriptions){
			r += s.toString() + "\r\n";
		}
		return r;
	}
	
	public String toStringExpressions(){
		String r = "";
		for(Expression s : Expressions){
			r += s.toString() + "\r\n";
		}
		return r;
	}
}
