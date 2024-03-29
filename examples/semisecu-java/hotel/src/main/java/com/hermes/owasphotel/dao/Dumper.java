package com.hermes.owasphotel.dao;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Dumper for the database.
 */
@Repository
public class Dumper {

	@Autowired
	private DataSource dataSource;

	private String schemaPattern = "PUBLIC";

	private void writeLine(Writer w, String[] line) throws IOException {
		for (int i = 0; i < line.length; i++) {
			w.write("\"");
			if (line[i] != null)
				w.write(line[i]);
			w.write("\"");
			if (i != line.length - 1)
				w.write(", ");
		}
		w.write("\n");
	}

	/**
	 * Dumps a table to a writer as CSV.
	 * @param tableName The name of the table to dump
	 * @param columns The columns to dump
	 * @param w The writer to dump the table to
	 * @throws IOException when an exception occurred when writing
	 * @throws DataAccessException when the query failed to execute
	 */
	public void dump(String tableName, String[] columns, Writer w)
			throws IOException, DataAccessException {
		if (columns == null || columns.length == 0) {
			columns = listColumns(tableName).toArray(new String[0]);
		}
		JdbcTemplate select = new JdbcTemplate(dataSource);
		StringBuilder query = new StringBuilder("select ");
		for (String col : columns) {
			query.append(col).append(',');
		}
		query.setLength(query.length() - 1);
		query.append(" from ").append(tableName);
		writeLine(w, columns);
		for (Map<String, Object> row : select.queryForList(query.toString())) {
			String[] line = new String[columns.length];
			for (int i = 0; i < line.length; i++) {
				Object o = row.get(columns[i]);
				if (o != null)
					line[i] = o.toString();
			}
			writeLine(w, line);
		}
	}

	/**
	 * Gets the list of tables.
	 * @return The list of the dumpable tables.
	 */
	public List<String> listTables() {
		return new JdbcTemplate(dataSource)
				.execute(new ConnectionCallback<List<String>>() {
					@Override
					public List<String> doInConnection(Connection con)
							throws SQLException, DataAccessException {
						ResultSet rs = null;
						try {
							rs = con.getMetaData().getTables(null,
									schemaPattern, null,
									new String[] { "TABLE" });
							ArrayList<String> names = new ArrayList<String>();
							while (rs.next()) {
								names.add(rs.getString("TABLE_NAME"));
							}
							return names;
						} finally {
							if (rs != null)
								rs.close();
						}
					}
				});
	}

	/**
	 * Gets the list of columns.
	 * @param tableName The name of the table to Dump
	 * @return A list containing the name of the columns of the selected table
	 */
	public List<String> listColumns(final String tableName) {
		return new JdbcTemplate(dataSource)
				.execute(new ConnectionCallback<List<String>>() {
					@Override
					public List<String> doInConnection(Connection con)
							throws SQLException, DataAccessException {
						ResultSet rs = null;
						try {
							rs = con.getMetaData().getColumns(null,
									schemaPattern, tableName, null);
							ArrayList<String> names = new ArrayList<String>();
							while (rs.next()) {
								names.add(rs.getString("COLUMN_NAME"));
							}
							return names;
						} finally {
							if (rs != null)
								rs.close();
						}
					}
				});
	}
}
