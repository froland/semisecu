package com.hermes.owasphotel.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DumperTest extends DaoTestBase {
	@Autowired
	private Dumper dumper;

	@Test
	public void testListTablesAndColumns() throws Exception {
		List<String> tables = dumper.listTables();
		assertFalse("No tables found", tables.isEmpty());
		for (String table : tables) {
			List<String> columns = dumper.listColumns(table);
			assertFalse("No columns in table: " + table, columns.isEmpty());
		}
	}

	@Test
	public void testDumpTable() throws Exception {
		StringWriter w = new StringWriter();
		String table = dumper.listTables().get(0); // get some table
		List<String> columns = dumper.listColumns(table);
		dumper.dump(table, columns.toArray(new String[0]), w);
		String dump = w.toString();

		assertTrue("Failed to found column", dump.contains(columns.get(0)));
	}
}
