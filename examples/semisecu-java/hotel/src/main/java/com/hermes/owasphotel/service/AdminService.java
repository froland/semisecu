package com.hermes.owasphotel.service;

import java.io.IOException;
import java.io.Writer;

public interface AdminService {
	
	public String getDumpString(String tableName) throws IOException;
	
	public void dumpToWriter(String tableName, Writer w) throws IOException;

}
