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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

	public List<String> listTables() {
		ArrayList<String> names = new ArrayList<String>();
		Connection c = null;
		ResultSet rs = null;
		try {
			c = dataSource.getConnection();
			rs = c.getMetaData().getTables(null, schemaPattern, null,
					new String[] { "TABLE" });
			while (rs.next()) {
				names.add(rs.getString("TABLE_NAME"));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DataAccessException("Failed to list tables") {
				private static final long serialVersionUID = 1L;
			};
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (c != null)
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return names;
	}

	public List<String> listColumns(String tableName) {
		ArrayList<String> names = new ArrayList<String>();
		Connection c = null;
		ResultSet rs = null;
		try {
			c = dataSource.getConnection();
			rs = c.getMetaData().getColumns(null, schemaPattern, tableName,
					null);
			while (rs.next()) {
				names.add(rs.getString("COLUMN_NAME"));
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to list columns of table "
					+ tableName) {
				private static final long serialVersionUID = 1L;
			};
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (c != null)
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return names;
	}
}
