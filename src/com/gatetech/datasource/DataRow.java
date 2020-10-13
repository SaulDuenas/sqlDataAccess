package com.gatetech.datasource;

import java.util.LinkedList;

public class DataRow {
	
	private LinkedList<Object> _Values = new LinkedList<Object>();

	public LinkedList<Object> get_Values() {
		return _Values;
	}

	public void set_Values(LinkedList<Object> Values) {
		this._Values = Values;
	}
	
}
