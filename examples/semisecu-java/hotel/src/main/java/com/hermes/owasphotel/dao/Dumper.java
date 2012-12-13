package com.hermes.owasphotel.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

@Component
public class Dumper {
	
	@Autowired
	private DataSource dataSource;
	
	public void dump(String tableName, File dest) throws IOException
	{
		FileWriter fw = new FileWriter(dest);
		dump(tableName, fw);
	}
	
	public void dump(String tableName, Writer w) throws IOException
	{
		SqlRowSet res = getRows(tableName);
		SqlRowSetMetaData data = res.getMetaData();
		for (int i = 0; i < data.getColumnCount(); i++)
		{
			w.write("\"");
			w.write(data.getColumnLabel(i+1));
			w.write("\"");
			if(i != data.getColumnCount()-1)
				w.write(", ");
		}
		w.write("\n");
		res.beforeFirst();
		while(res.next())
		{
			for (int i = 0; i < data.getColumnCount(); i++)
			{
				w.write("\"");
				w.write(res.getString(i+1));
				w.write("\"");
				if(i != data.getColumnCount()-1)
					w.write(", ");
			}
			w.write("\n");
		}
		w.close();
	}
	
	public String dump(String tableName) throws IOException
	{
		StringWriter sw = new StringWriter();
		dump(tableName, sw);
		return sw.toString();
	}
	
	private SqlRowSet getRows(String tableName)
	{
		  JdbcTemplate select = new JdbcTemplate(dataSource);
		  return select.queryForRowSet("select * from " + tableName);
	}

}
