package com.gatetech.datasource;
import java.util.LinkedList;

public class DataTable {

	
	public LinkedList<DataColumn> DataColumList() {
		return _DataColums;
	}

	public void DataColumList(LinkedList<DataColumn> colums) {
		this._DataColums = colums;
	}

	
	public String ColumnstoString(String delimiter,String leftEnclosure,String rightEnclousure) {
		StringBuilder str = new StringBuilder();
		
		for (int i=0;i<=(this._DataColums.size()-1);i++ ) {
			str.append(leftEnclosure).append(this._DataColums.get(i).get_Name()).append(rightEnclousure);
			if (i <(this._DataColums.size()-1)) {str.append(delimiter); }
		}
		
		return str.toString();
	}
	
	public LinkedList<DataRow> DataRowList() {
		return _DataRows;
	}

	public void DataRowList(LinkedList<DataRow> dataRows) {
		this._DataRows = dataRows;
	}

	private LinkedList<DataColumn> _DataColums = new LinkedList<DataColumn>();

	private LinkedList<DataRow> _DataRows = new LinkedList<DataRow>();

}
