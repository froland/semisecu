package com.hermes.owasphotel.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Test;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Identifiable;

public abstract class SimpleDaoTestBase<I extends Serializable, T extends Identifiable<I>>
		extends DaoTestBase {
	@Test
	public void testDAO() {
		SimpleDao<I, T> dao = getDao();
		T obj = createEntity();
		T saved = dao.save(obj);
		assertNotNull("No saved object returned", saved);
		assertNotNull("ID not generated", saved.getId());
		assertTrue("FindAll must contains DAO", dao.findAll().contains(saved));
		T found = dao.getById(saved.getId());
		assertNotNull("Saved object not found", found);
		checkEquals(saved, found);
		dao.delete(found);
		assertFalse("Not deleted", dao.findAll().contains(saved));
		assertNull("Deleted object found", dao.getById(saved.getId()));
	}

	protected void checkEquals(T expected, T found) {
		if (expected == found)
			return;
		assertNotNull("No found object", found);
		assertEquals("Not same ID", expected.getId().equals(found.getId()));
	}

	protected abstract SimpleDao<I, T> getDao();

	protected abstract T createEntity();
}
