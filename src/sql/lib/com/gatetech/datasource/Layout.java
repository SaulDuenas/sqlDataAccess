package com.gatetech.datasource;

import java.util.LinkedList;


public class Layout{
	
	private LinkedList<Layout> list = new LinkedList<Layout>();
	
	private int position;
	private String format;
	
	
	public LinkedList<Layout> getlist() {
		return list;
	}

	public void setlist(LinkedList<Layout> Values) {
		this.list = Values;
	}
	
	
	public Layout () {
		
	}
	
	public Layout (int position,String format){
		 this.position = position;
		 this.format = format;	 
	}
	
	public int getPosition() {
		return this.position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getFormat() {
		return this.format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

}