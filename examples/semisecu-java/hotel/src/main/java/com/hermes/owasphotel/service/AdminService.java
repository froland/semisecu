package com.hermes.owasphotel.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface AdminService {

	public String getDumpString(String tableName);

	public void dumpToWriter(String tableName, String[] columns, Writer w) throws IOException;

	public List<String> listTables();

	public List<String> listColumns(String tableName);

}
