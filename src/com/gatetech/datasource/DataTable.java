package com.gatetech.datasource;
import java.util.LinkedList;

public class DataTable {

	public LinkedList<DataColumn> get_Colums() {
		return _DataColums;
	}

	public void set_Colums(LinkedList<DataColumn> colums) {
		this._DataColums = colums;
	}

	public LinkedList<DataRow> get_DataRows() {
		return _DataRows;
	}

	public void set_DataRows(LinkedList<DataRow> dataRows) {
		this._DataRows = dataRows;
	}

	private LinkedList<DataColumn> _DataColums = new LinkedList<DataColumn>();

	private LinkedList<DataRow> _DataRows = new LinkedList<DataRow>();

}
