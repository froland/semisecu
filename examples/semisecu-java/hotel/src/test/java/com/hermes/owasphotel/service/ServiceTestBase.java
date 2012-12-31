package com.hermes.owasphotel.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hermes.owasphotel.dao.DaoTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:/com/hermes/owasphotel/dao/testDaoContext.xml",
		"classpath*:/com/hermes/owasphotel/service/testService.xml" })
public abstract class ServiceTestBase {

	@Autowired
	protected ApplicationContext applicationContext;

	@Before
	public void initializeData() throws Exception {
		DaoTestBase.initializeLiquibase(applicationContext);
	}
}
