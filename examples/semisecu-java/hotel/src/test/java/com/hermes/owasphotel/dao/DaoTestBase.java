package com.hermes.owasphotel.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = "classpath*:/com/hermes/owasphotel/dao/testDaoContext.xml")
@Transactional
public abstract class DaoTestBase {
	@Autowired
	private DataSource dataSource;

	protected String getChangeLogFile() {
		return "data/changelog.xml";
	}

	@Before
	public void initializeLiquibase() throws Exception {
		Connection conn = dataSource.getConnection();
		Database database = DatabaseFactory.getInstance()
				.findCorrectDatabaseImplementation(new JdbcConnection(conn));
		Liquibase liquibase = new Liquibase(getChangeLogFile(),
				new ClassLoaderResourceAccessor(), database);
		liquibase.dropAll();
		liquibase.update("");
		database.close();
	}
}
