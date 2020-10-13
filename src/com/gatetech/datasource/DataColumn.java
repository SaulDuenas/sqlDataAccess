package com.gatetech.datasource;

import java.util.LinkedList;

public class DataColumn extends MappingTypes {
	
	private LinkedList<DataColumn> list = new LinkedList<DataColumn>();
	
	public LinkedList<DataColumn> DataColumList() {
		return list;
	}

	public void DataColumList(LinkedList<DataColumn> colums) {
		this.list = colums;
	}
	
	//Constructor
	public DataColumn() {
		
	}
	
	
	public DataColumn(String name,Integer sqlType,String columnTypeName) {
		
		MappingTypes mt = MappingTypes.getMappingType(sqlType);
	
		this._SqlType = mt.SQLType();
		this._DataType = mt.DataType();
		this._Type = mt.Type();
		this.name = name;
	}
	
	private String name = "";
	
	public String get_Name() {
		return this.name;
	}
	
	public void set_Name(String name) {
		this.name = name;
	}
			
}
