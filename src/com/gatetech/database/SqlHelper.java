package com.gatetech.database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import com.gatetech.database.sqlCommand.CommandType;
import com.gatetech.datasource.DataColumn;
import com.gatetech.datasource.DataRow;
import com.gatetech.datasource.DataTable;


public class SqlHelper {
		
	private static long BATCH_SIZE = 1000;
	
	
	private static void AttachParameters(PreparedStatement PreparedStatement,Connection Conn,Object[] Parameters) throws SQLException {
			
		Array array = null;
		
		int pos=0;
		
		for (Object obj : Parameters)
		{
			
			if (obj.getClass().isArray()) {
				if (obj instanceof String[]){
					array = Conn.createArrayOf("VARCHAR", (String[]) obj);
				}
					
				else if (obj instanceof byte[] || obj instanceof Byte[]) {
					array = Conn.createArrayOf("TINYINT",(Byte[]) obj);
				}
				
				else if (obj instanceof int[] || obj instanceof Integer[]) {
					array = Conn.createArrayOf("INTEGER",(Integer[]) obj);
				}
				
				else if (obj instanceof long[] || obj instanceof Long[]) {
					array = Conn.createArrayOf("BIGINT",(Long[]) obj);
				}
					
				else if (obj instanceof float[] || obj instanceof Float[]) {
					array = Conn.createArrayOf("FLOAT",(Float[]) obj);
				}
				else if (obj instanceof double[] || obj instanceof Double[]) {
					array = Conn.createArrayOf("DOUBLE",(Double[]) obj);
				}
				else if (obj instanceof Date[]) {
					array = Conn.createArrayOf("DATE",(Date[]) obj);
				}
				
				PreparedStatement.setArray(++pos,array);
			}
			else {
				String value =  escapeSql(obj.toString());
					
				if (obj instanceof Integer){
					PreparedStatement.setInt(++pos,Integer.parseInt(value));
				}
				
				else if (obj instanceof String){
					PreparedStatement.setString(++pos, value);
				}
				else if (obj instanceof Float){
					PreparedStatement.setFloat(++pos, Float.parseFloat(value));
				}
				else if (obj instanceof Double) {
					PreparedStatement.setDouble(++pos, Double.parseDouble(value));
				}
				
				else if (obj instanceof Long ) {
					PreparedStatement.setLong(++pos, Long.parseLong(value));
				}
				
				else if (obj instanceof Date) {
					PreparedStatement.setDate(++pos,new java.sql.Date(((Date) obj).getTime()));
				}
		
			}
		}	
		
	}
	
	
	private static PreparedStatement PreparedCommand(Connection Conn,String sqlCommand,CommandType commandType,Object[] Parameters) throws SQLException{
		
		//Connection Conn = null;
		PreparedStatement PreparedStatement = null;
		
		if (!Conn.isClosed()) {
			//Conn= basicDataConn.getConnection();
		
			PreparedStatement = Conn.prepareStatement(sqlCommand,
												      ResultSet.TYPE_SCROLL_SENSITIVE,
													  ResultSet.CONCUR_READ_ONLY,
													  Statement.RETURN_GENERATED_KEYS);
			
		}
		
		if (Parameters != null) {
			AttachParameters(PreparedStatement,Conn,Parameters);
		}
		
		return PreparedStatement;
		
	}
	
	
	/**
	 *  ExecuteDataTable – Fetching data return with DataTable. 
	 *  
	 *  @param conn -> jdbc Connection 
	 *  @param Parameters -> Parameters list
	 *  @return true = ResultSet de la consulta
	 * @throws SQLException 
	*/
	public static DataTable ExecuteDataTable(DataAccess da,String sqlCommand,Object[] Parameters) throws SQLException {
		DataTable dt=new DataTable();
		try {
				Connection Conn=da.DataSource().getConnection();
				PreparedStatement PreparedStatement = PreparedCommand(Conn,sqlCommand,CommandType.SQLCommand,Parameters);
				ResultSet rs = PreparedStatement.executeQuery();
				
				int nColumn = rs.getMetaData().getColumnCount();
				// Obtain Metadata Query
				for (int x=1;x<=nColumn;x++) {
					
					dt.get_Colums().add( new DataColumn(rs.getMetaData().getColumnName(x), rs.getMetaData().getColumnType(x)));
				}
				
				// obtain data
				while(rs.next()) {
				    DataRow rw = new DataRow(); 
					// ... get column values from this record
					for (int x=1;x<=nColumn;x++) {
						Object obj = rs.getObject(x);
						rw.get_Values().add(obj);
					}
					dt.get_DataRows().add(rw);
				}
				
				Conn.close();
		
		} catch (SQLException e) {
			throw new SQLException("An error occurred while performing the requested operation." + 
									"\r DataAcess: " + da.get_name() +				
									"\r SQLCommand: " + sqlCommand +			   
									"\r Message: " + e.getMessage() +
								    "\r SqlState: " + e.getSQLState() +
								    "\r ErrorCode: " + e.getErrorCode()); 
		}
		
		return dt;
	}

	
	
	/**
	 *  ExecuteReader – Fetching data using a ResultSet. 
	 *  
	 *  @param conn -> jdbc Connection 
	 *  @param sqlCommand -> Consulta SQL 
	 *  @param Parameters -> Parameters list
	 *  @return true = ResultSet de la consulta
	 * @throws SQLException 
	*/
	public static ResultSet ExecuteReader(Connection Conn,String sqlCommand,CommandType CommandType,Object[] Parameters) throws SQLException {
		//Connection Conn=basicDataConn.getConnection();
		
		PreparedStatement lPreparedStatement = PreparedCommand(Conn,sqlCommand,CommandType,Parameters);
		ResultSet rs = lPreparedStatement.executeQuery();
		return rs;
	}


	/**
	 * ExecuteScalar – Fetching single cell value. Generally helpful when we need to fetch SCOPE_IDENTITY value. 
	 *  
	 *  @param conn -> jdbc Connection 
	 *  @param sqlCommand -> Query command example. SELECT COUNT(*) FROM dbo.region"
	 *  @param Parameters -> Parameters list 
	 *  @return  Return Value
	 * @throws SQLException 
	*/
	public static Object ExecuteScalar(DataAccess da,String sqlCommand,CommandType CommandType,Object[] Parameters) throws SQLException {
		Object _obj=null;
	
		try {
				Connection Conn=da.DataSource().getConnection();
				
				PreparedStatement PreparedStatement = PreparedCommand(Conn,sqlCommand,CommandType,Parameters);
				ResultSet rs = PreparedStatement.executeQuery();
				if (rs != null && rs.next()){
					_obj = rs.getObject(1);
				}
				
				PreparedStatement.close();
				if(Conn != null && !Conn.isClosed()) {
					Conn.close();
				}
				
		} catch (SQLException e) {
			throw new SQLException("An error occurred while performing the requested operation." + 
								   "\r DataAcess: " + da.get_name() +					
								   "\r SQLCommand: " + sqlCommand +			   
								   "\r Message: " + e.getMessage() +
								   "\r SqlState: " + e.getSQLState() +
								   "\r ErrorCode: " + e.getErrorCode()); 
		}
		
		return _obj;
	}
	
	
		
	
	/**
	ExecuteNonQuery – Executing INSERT, UPDATE and DELETE statements.
     *
     *  @param conn -> jdbc Connection 
	 *  @param sqlCommand -> Query command INSERT, UPDATE and DELETE.
	 *  @param Parameters -> Parameters list 
	 *  @return  Return Value
	 *  @throws SQLException 
	*/
	@SuppressWarnings("unused")
	public Long ExecuteNonQuery(DataAccess da,String sqlCommand,CommandType CommandType,Object[] Parameters) throws SQLException{
		
		Long autoGenerated=null;
		
		Long rowsAffected=null;
		//Statement lcommand=null;

		try {
				Connection Conn=da.DataSource().getConnection();
				
				PreparedStatement PreparedStatement = PreparedCommand(Conn,sqlCommand,CommandType,Parameters);
				rowsAffected = (long) PreparedStatement.executeUpdate();
				
				ResultSet rs = PreparedStatement.getGeneratedKeys();
				if (rs != null && rs.next()) {
					// Autoincrement
					autoGenerated = rs.getLong(1);
				}
				
				PreparedStatement.close();
				if(Conn != null && !Conn.isClosed()) {
					Conn.close();
				}
		
		} catch (SQLException e) {
			throw new SQLException("An error occurred while performing the requested operation." + 
									"\r DataAcess: " + da.get_name() +				
									"\r SQLCommand: " + sqlCommand +			   
									"\r Message: " + e.getMessage() +
								    "\r SqlState: " + e.getSQLState() +
								    "\r ErrorCode: " + e.getErrorCode()); 
		}
		
		return autoGenerated;
	}
	
	
	/**
	 ExecuteNonQuery – Executing INSERT, UPDATE and DELETE statements.
	 * @throws Exception 
	*/
	public static void TransExecuteNonQuery(DataAccess da,Collection<String> sqlCommand)throws Exception{
		
		PreparedStatement lPreparedStatement = null;
		Connection Conn=da.DataSource().getConnection();
		
		long batch_counter=0;
			
		try{
			
			if (Conn.getAutoCommit() ) {
				da.DataSource().setDefaultAutoCommit(false);
			}
			
			Conn.setAutoCommit(false);
			for (String sql : sqlCommand){
				batch_counter ++;
				lPreparedStatement = Conn.prepareStatement(sql);
				//lPreparedStatement.executeUpdate();
				lPreparedStatement.addBatch();
				
				if (batch_counter % BATCH_SIZE == BATCH_SIZE - 1) {
					lPreparedStatement.executeBatch();
					Conn.commit();
				}
				
			}
			if (sqlCommand.size() % BATCH_SIZE != 0) {
				lPreparedStatement.executeBatch();
				Conn.commit();
			}
			
		
		} catch(SQLException ex) {
		
			try {
				Conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new SQLException("An error occurred while performing the requested operation." + 
						"\r DataAcess: " + da.get_name() +				
						"\r SQLCommand: " + sqlCommand +			   
						"\r Message: " + e.getMessage() +
					    "\r SqlState: " + e.getSQLState() +
					    "\r ErrorCode: " + e.getErrorCode());
			}
			throw new SQLException("An error occurred while performing the requested operation." + 
					"\r DataAcess: " + da.get_name() +				
					"\r SQLCommand: " + sqlCommand +			   
					"\r Message: " + ex.getMessage() +
				    "\r SqlState: " + ex.getSQLState() +
				    "\r ErrorCode: " + ex.getErrorCode());
				
		} finally {
			if (lPreparedStatement != null) { lPreparedStatement.close(); }
			lPreparedStatement=null;
			if (Conn != null) {Conn.close(); }
			da.DataSource().setDefaultAutoCommit(true);
		}
			
	}
	
	public static void TransExecuteNonQuery(DataAccess da,sqlCommand cmdList)throws Exception{
		
		String qry = ""; 
		PreparedStatement lPreparedStatement = null;
		Connection Conn = null;
		Long batch_counter=(long) 0;
			
		try{
			Conn=da.DataSource().getConnection();
			LinkedList<sqlCommand> sqlCmdList = cmdList.sqlCommands();
			if (Conn.getAutoCommit() ) {
				da.DataSource().setDefaultAutoCommit(false);
			}
			
			Conn.setAutoCommit(false);
			for (sqlCommand cmd : sqlCmdList){
				batch_counter ++;
				qry = cmd.Command();
				lPreparedStatement = Conn.prepareStatement(qry);
				// ADD PARAMETERS
				AttachParameters(lPreparedStatement,Conn,cmd.Parameters().Values());
				
				lPreparedStatement.addBatch();
				
				if (batch_counter % BATCH_SIZE == BATCH_SIZE - 1) {
					lPreparedStatement.executeBatch();
					//Conn.commit();
				}
				
			}
			if (sqlCmdList.size() % BATCH_SIZE != 0) {
				lPreparedStatement.executeBatch();
			}
			lPreparedStatement.close();
			
			Conn.commit();
			da.DataSource().setDefaultAutoCommit(true);
		
		} catch(SQLException ex) {
		
			try {
				if (Conn != null) {Conn.rollback();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new SQLException("An error occurred while performing the requested operation." + 
										"\r DataAcess: " + da.get_name() +				
										"\r Batch Number:" + batch_counter.toString() +
										"\r SQLCommand: " + qry +			   
										"\r Message: " + e.getMessage() +
									    "\r SqlState: " + e.getSQLState() +
									    "\r ErrorCode: " + e.getErrorCode());
			}
			throw new SQLException("An error occurred while performing the requested operation." + 
									"\r DataAcess: " + da.get_name() +				
									"\r Batch Number:" + batch_counter.toString() +
									"\r SQLCommand: " + qry +			   
									"\r Message: " + ex.getMessage() +
								    "\r SqlState: " + ex.getSQLState() +
								    "\r ErrorCode: " + ex.getErrorCode());
				
		} finally {
			if (lPreparedStatement != null) { lPreparedStatement.close(); }
			lPreparedStatement=null;
			if (Conn != null) {Conn.close(); }
			da.DataSource().setDefaultAutoCommit(true);
		}
			
	}
	
	public static String escapeSql(String str) {
		 if (str == null) {
			 return null;
		 }
	     return str.replace("'", "''");
	}

}