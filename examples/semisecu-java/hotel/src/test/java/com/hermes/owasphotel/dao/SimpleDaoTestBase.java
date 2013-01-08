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
		T saved = createEntity();

		// save
		dao.save(saved);
		dao.flush();
		I id = saved.getId();
		assertNotNull("ID not generated", id);

		// load
		dao.clear();
		T found = dao.getById(id);
		assertNotNull("Saved object not found", found);
		checkEquals(saved, found);

		// test findAll()
		assertTrue("findAll() must contain the object",
				dao.findAll().contains(saved));

		// delete
		dao.delete(found);
		dao.flush();
		assertNull("Deleted object found", dao.getById(id));
		assertFalse("Not deleted", dao.findAll().contains(saved));
	}

	protected abstract T createEntity();

	protected void checkEquals(T expected, T found) {
		assertNotNull("No found object", found);
		assertEquals("Not same ID", expected.getId(), found.getId());
		assertEquals(expected, found);
		assertEquals(expected.hashCode(), found.hashCode());
	}

	protected abstract SimpleDao<I, T> getDao();

}
