package com.hermes.owasphotel.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

	DaoTestBase() {
		// constructor is default to avoid inheriting from non-dao packages
	}

	@Before
	public void initializeLiquibase() throws Exception {
		initializeLiquibase(dataSource, liquibaseChangeLog);
	}

	public static void initializeLiquibase(DataSource dataSource,
			String changeLogFile) throws Exception {
		Connection conn = dataSource.getConnection();
		Database database = DatabaseFactory.getInstance()
				.findCorrectDatabaseImplementation(new JdbcConnection(conn));
		Liquibase liquibase = new Liquibase(changeLogFile,
				new ClassLoaderResourceAccessor(), database);
		liquibase.dropAll();
		liquibase.update("");
		database.close();
	}

	public static void initializeLiquibase(ApplicationContext ctx)
			throws Exception {
		initializeLiquibase(ctx.getBean(DataSource.class),
				ctx.getBean("liquibaseChangeLog", String.class));
	}
}
