package com.gatetech.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataAccess {

	private String _url="";
	
	public String get_url() {
		return _url;
	}

	public void set_url(String url) {
		this._url = url;
	}

	
	private String _driverClassName="";
	
	public String get_driverClassName() {
		return _driverClassName;
	}

	public void set_driverClassName(String driverClassName) {
		this._driverClassName = driverClassName;
	}

	private String _name="";
	
	public String get_name() {
		return _name;
	}

	public void set_name(String name) {
		this._name = name;
	}

	
	private String _user="";
	
	public String get_user() {
		return _user;
	}

	public void set_user(String user) {
		this._user = user;
	}

	private String _password=""; 
	
	public String get_password() {
		return _password;
	}

	public void set_password(String password) {
		this._password = password;
	}
	
	private ArrayList<DataAccess> _dataconn = new ArrayList<DataAccess>(); 
	
	
	public ArrayList<DataAccess> get_dataconn() {
		return _dataconn;
	}

	private BasicDataSource _ds = null;

	public BasicDataSource DataSource() {
		return this._ds;
	}
	
	private boolean _PreparedStatements = true;

	
	public void set_BasicDataSource(BasicDataSource datasource) {
		
		this._ds = datasource;
	}


	//Constructor
	public DataAccess(){
		
	}
	
	public DataAccess getDataAccess (String name) throws SQLException {
		//private StkDataConn _conn;
		if (this._dataconn != null) {
			for (DataAccess ds: this._dataconn) {
				if(ds.get_name().equals(name) ) {
					
					return ds;
				}
				
			}
		}
		// no encontro la conexión regresa nulo
		return null;
	}
	
	
	/**
	 *  ExecuteDataTable – Fetching data return with DataTable. 
	 *  
	 *  @name setConnection 
	 *  @param name 
	 *  @param DriverClassName 
	 *  @param url -> jdbc:oracle:thin:@HOSTNAME:PORTNUMBER:ServiceName
	 *  			  jdbc:sqlserver://HOSTNAME:PORTNUMBER;databaseName=DATABASE_NAME;instance=INSTANCE_NAME;
	 *  			  jdbc:mysql://host:PORTNUMBER/DATABASE_NAME;instance=INSTANCE_NAME;
	 *  @param user
	 *  @param password
	 *  @return None
	 * @throws SQLException 
	*/
	public void setConnection(String name,String driverClassName, String url, String user, String password,String Validation_Query) {
		
		this._name = name;
		this._driverClassName = driverClassName;
		this._url = url;
		this._user = user;
		this._password = password;
	
		this._ds = null;
		this._ds = new BasicDataSource();
		
		this._ds.setDriverClassName(driverClassName);
		this._ds.setUrl(url);
		this._ds.setUsername(user);
		this._ds.setPassword(password);
		//this._ds.setMaxIdle(20);
		//this._ds.setInitialSize(33);
		this._ds.setValidationQuery(Validation_Query);
		this._ds.setPoolPreparedStatements(this._PreparedStatements);
		
		DataAccess dataConn =  new DataAccess();
		
		dataConn.set_name(name);
		dataConn.set_BasicDataSource(this._ds);
		dataConn.set_driverClassName(driverClassName);
		dataConn.set_url(url);
		dataConn.set_user(user);
		dataConn.set_password(password);
		
		this._dataconn.add(dataConn);
		
	//	try {
	//		this._ds.getConnection();
	//		this._ds.close();
		
	//} catch (SQLException err) {
		
//		err.printStackTrace();
//	}	
		
		
	}
	
	
	public Connection GetConnection() throws SQLException {
		Connection conn = null;
		try {
			 conn = this._ds.getConnection();
		} catch (SQLException e) {
			throw new SQLException("An error occurred while performing the requested operation." + 
									"\r DataAcess: " + this._name +			   
									"\r Message: " + e.getMessage() +
								    "\r SqlState: " + e.getSQLState() +
								    "\r ErrorCode: " + e.getErrorCode()); 
		}
		
		return conn;
		
	}
	
	public boolean close_datasource() throws SQLException
	 {
		boolean lRetVal=true;
		
		if (this._ds != null)
		{
		   if (!this._ds.isClosed()){
			  
			   this._ds.close();
		   }
		}
		else
		{
			lRetVal = false;
		}
		return lRetVal;
	}
	
}
