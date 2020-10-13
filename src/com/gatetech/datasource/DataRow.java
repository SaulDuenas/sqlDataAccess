package com.gatetech.datasource;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;

public class DataRow {
	
	private LinkedList<Object> list = new LinkedList<Object>();

	public LinkedList<Object> getlist() {
		return list;
	}

	public void setlist(LinkedList<Object> Values) {
		this.list = Values;
	}
	
	public Object GetValue(int index) {
		
		return this.list.get(index);
	} 
	
	
	public String toString(String delimiter,String leftEnclosure,String rightEnclousure,LinkedList<Layout> laoyut_list) {
		StringBuilder str = new StringBuilder();
		Integer i=0;
		for (Layout la : laoyut_list) {
			str.append(leftEnclosure).append( toString( this.list.get(la.getPosition()) ,la.getFormat()) ).append(rightEnclousure);
			if (i <(laoyut_list.size()-1)) {str.append(delimiter); }
		}
			
		return str.toString();
	}
	
	
	public String toString(String delimiter,String leftEnclosure,String rightEnclousure,Integer[] Order_list) {
		StringBuilder str = new StringBuilder();
		Integer i=0;
		for (Integer pos : Order_list) {
			str.append(leftEnclosure).append(toString(this.list.get(pos),"")).append(rightEnclousure);
			if (i <(Order_list.length-1)) {str.append(delimiter); }
		}
			
		return str.toString();
	}
	
	
	public String toString(String delimiter,String leftEnclosure,String rightEnclousure) {
		StringBuilder str = new StringBuilder();
		
		for (int i=0;i<=(this.list.size()-1);i++ ) {
			str.append(leftEnclosure).append(this.list.get(i).toString()).append(rightEnclousure);
			if (i <(this.list.size()-1)) {str.append(delimiter); }
		}
		
		return str.toString();
	}
	
	public String toString(int index) {
		
		return this.list.get(index).toString();
	}
	
	public String toString(Object value,String format) {
		String retval="";
		
		if(format == null || format.equals("")) {
			 retval=value.toString();
		}
		else if (value instanceof Date || value instanceof Timestamp) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			retval = sdf.format(value);
		}
		//	int, long, double, float, BigInteger, BigDecimal
		else if(value instanceof Integer || value instanceof Long|| value instanceof Double ||
		   value instanceof Float || value instanceof BigInteger|| value instanceof BigDecimal) {
			DecimalFormat df = new DecimalFormat(format);
			retval = df.format (value);
		}
		
		return retval;
	}

	
}
