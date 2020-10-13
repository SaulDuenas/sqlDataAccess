package com.gatetech.datasource;

public class DataColumn extends MappingTypes {
	
	public Object dataret(String type,Object value) {
		
		if (type.equals("Integer")) {
		return (Integer)value;
		
		}
		else if(type.equals("VARCHAR")) {
			return (String)value;
		}
		return null;
	}
	
	//Constructor
	public DataColumn() {
		
	}
	
	public DataColumn(String name,Integer sqlType) {
		
		MappingTypes mt = MappingTypes.getMappingType(sqlType);
	
		this._SqlType = mt.SQLType();
		this._DataType = mt.DataType();
		this._Type = mt.Type();
		this._Name = name;
		
	}
	
	
	private String _Name = "";
	public String get_Name() {
		return this._Name;
	}
	public void set_Name(String name) {
		this._Name = name;
	}
		
}
