package com.sqlhelper.events;

import java.util.EventListener;


public interface sqlhelperEventListener extends EventListener{

	void rows_bulkcopy_alert(long process_rows,long total_rows);
	
	void connect_database();
	
}
