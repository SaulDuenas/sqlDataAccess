package com.gatetech.database;

import java.util.LinkedList;

public class sqlParameter {

	public enum Direction {Input,Output};


	private String _Name="";

	private Object _Value=null;
	
	private Direction _direction=null;
	
	private LinkedList<sqlParameter> _Parameters =new LinkedList<sqlParameter>();
	
	
	public String Name() {
		return _Name;
	}

	public void Name(String Name) {
		this._Name = Name;
	}

	public Object Value() {
		return _Value;
	}

	public Object[] Values() {
		LinkedList<Object> obj = new LinkedList<Object>();
		
		for( sqlParameter parameter : _Parameters) {
			obj.add(parameter.Value());
			
		}
		
		return obj.toArray();
		
	}
	public void Value(Object Value) {
		this._Value = Value;
	}

	
	
	public Direction Direction() {
		return _direction;
	}

	public void Direction(Direction direction) {
		this._direction = direction;
	}

	
	public LinkedList<sqlParameter> Parameters() {
		return _Parameters;
	}

	public void Parameters(LinkedList<sqlParameter> Parameters) {
		this._Parameters = Parameters;
	}

	sqlParameter(){
		
	}
	
	sqlParameter(String Name,Object Value){
		this._Name=Name;
		this._Value=Value;
		this._direction=Direction.Input;
	}
	
	sqlParameter(String Name,Object Value,Direction direction){
		this._Name=Name;
		this._Value=Value;
		this._direction = direction;
	}
	
	public void Add(sqlParameter Parameter) {
		this._Parameters.add(Parameter);
	}
}
 