package com.gatetech.datasource;

public class MappingTypes {

	public static MappingTypes[] _MappingTypes = new MappingTypes[] { 	new MappingTypes("CHAR","String",java.sql.Types.CHAR),
																	  	new MappingTypes("VARCHAR","String",java.sql.Types.VARCHAR),
																	  	new MappingTypes("LONGVARCHAR","String",java.sql.Types.LONGVARCHAR),
																	  	new MappingTypes("NUMERIC","java.math.BigDecimal",java.sql.Types.NUMERIC),
																	  	new MappingTypes("DECIMAL","java.math.BigDecimal",java.sql.Types.DECIMAL),
																	  	new MappingTypes("BIT","boolean",java.sql.Types.BIT),
																	  	new MappingTypes("BOOLEAN","boolean",java.sql.Types.BOOLEAN),
																	  	new MappingTypes("TINYINT","byte",java.sql.Types.TINYINT),
																	  	new MappingTypes("SMALLINT","short",java.sql.Types.SMALLINT),
																	  	new MappingTypes("INTEGER","int",java.sql.Types.INTEGER),
																		new MappingTypes("BIGINT","long",java.sql.Types.BIGINT),
																		new MappingTypes("REAL","float",java.sql.Types.REAL),
																		new MappingTypes("FLOAT","double",java.sql.Types.FLOAT),
																		new MappingTypes("DOUBLE","double",java.sql.Types.DOUBLE),
																		new MappingTypes("BINARY","byte[]",java.sql.Types.BINARY),
																		new MappingTypes("VARBINARY","byte[]",java.sql.Types.VARBINARY),
																		new MappingTypes("LONGVARBINARY","byte[]",java.sql.Types.LONGVARBINARY),
																		new MappingTypes("DATE","java.sql.Date",java.sql.Types.DATE),
																		new MappingTypes("TIME","java.sql.Time",java.sql.Types.TIME),
																		new MappingTypes("TIMESTAMP","java.sql.Timestamp",java.sql.Types.TIMESTAMP),
																		new MappingTypes("CLOB","Clob",java.sql.Types.CLOB),
																		new MappingTypes("BLOB","Blob",java.sql.Types.BLOB),
																		new MappingTypes("ARRAY","Array",java.sql.Types.ARRAY),
																		new MappingTypes("DISTINCT","mapping of underlying type",java.sql.Types.DISTINCT),
																		new MappingTypes("STRUCT","Struct",java.sql.Types.STRUCT),
																		new MappingTypes("REF","Ref",java.sql.Types.REF),
																		new MappingTypes("DATALINK","java.net.URL",java.sql.Types.DATALINK),
																		new MappingTypes("JAVA_OBJECT","underlying Java class",java.sql.Types.JAVA_OBJECT)
																	};

	
	
	public MappingTypes(){
		
	}

	public MappingTypes(String SQLType,String DataType,Integer Type){
		this._SqlType=SQLType;
		this._DataType = DataType;
		this._Type = Type;
	}

	
	static public MappingTypes getMappingType(Integer Type) {

		for (MappingTypes mt: _MappingTypes) {
			
			if (mt.Type().equals(Type) ) {
				return mt;
			}
			
		}
		return null;
	}
	
	
	static public MappingTypes getMappingType(String SQLType) {

		for (MappingTypes mt: _MappingTypes) {
			
			if(mt.SQLType().equals(SQLType)) {
				return mt;
			}
		}
		return null;
	}

	
	protected String _SqlType="";
	
	public String SQLType() {
		return _SqlType;
	}


	public void SQLType(String SQLType) {
		this._SqlType = SQLType;
	}


	protected String _DataType="";
	
	public String DataType() {
		return _DataType;
	}


	public void DataType(String DataType) {
		this._DataType = DataType;
	}


	protected Integer _Type = 0;
	
	public Integer Type() {
		return _Type;
	}


	public void Type(Integer Type) {
		this._Type = Type;
	}
	
}
