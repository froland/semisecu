package com.hermes.owasphotel.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/com/hermes/owasphotel/dao/testDaoContext.xml")
@Transactional
public abstract class DaoTestBase {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private String liquibaseChangeLog;

	private Liquibase liquibase;

	DaoTestBase() {
		// constructor is default to avoid inheriting from non-dao packages
	}

	@Before
	public void initializeDB() throws Exception {
		liquibase = initializeLiquibase(dataSource, liquibaseChangeLog);
		liquibase.update("");
	}

	@After
	public void closeDB() throws DatabaseException {
		liquibase.getDatabase().close();
	}

	public static Liquibase initializeLiquibase(DataSource dataSource,
			String changeLogFile) throws Exception {
		Connection conn = dataSource.getConnection();
		Database database = DatabaseFactory.getInstance()
				.findCorrectDatabaseImplementation(new JdbcConnection(conn));
		return new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(),
				database);
	}
}
