package com.hermes.owasphotel.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Administration service
 *
 */
public interface AdminService {

	/**
	 * Dump a Table to a writer
	 * 
	 * @param tableName The name of the table to dump
	 * @param columns The name of the columns to dump
	 * @param w The destination writer
	 * @throws IOException 
	 */
	public void dumpToWriter(String tableName, String[] columns, Writer w)
			throws IOException;

	/**
	 * List the available tables
	 * 
	 * @return The list of the available tables
	 */
	public List<String> listTables();

	/**
	 * List the columns of a given table
	 * 
	 * @param tableName the name of the table
	 * @return the list of the column
	 */
	public List<String> listColumns(String tableName);

}
