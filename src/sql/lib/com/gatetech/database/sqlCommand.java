package com.gatetech.database;

import java.util.LinkedList;

public class sqlCommand {

	public enum CommandType {SQLCommand,StoreProc};
	
	
	private LinkedList<sqlCommand> _sqlCommand =new LinkedList<sqlCommand>();
	
	
	public LinkedList<sqlCommand> sqlCommands() {
		return _sqlCommand;
	}

	public void sqlCommands(LinkedList<sqlCommand> sqlCommand) {
		this._sqlCommand = sqlCommand;
	}
	
	public void Add(sqlCommand sqlCommand) {
		this._sqlCommand.add(sqlCommand);
	}
	
	private String _command;
	
	public String Command() {
		return _command;
	}

	public void Command(String command) {
		this._command = command;
	}

	private sqlParameter _Parameters = new sqlParameter();
	
	public sqlParameter Parameters() {
		return _Parameters;
	}

	public void Parameters(sqlParameter Parameters) {
		this._Parameters = Parameters;
	}
	
}
