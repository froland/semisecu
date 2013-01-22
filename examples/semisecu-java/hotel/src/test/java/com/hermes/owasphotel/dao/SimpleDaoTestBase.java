package com.hermes.owasphotel.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;

import javax.persistence.NoResultException;

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
		assertEntityEquals(saved, found);

		// test findAll()
		assertTrue("findAll() must contain the object",
				dao.findAll().contains(saved));

		// delete
		dao.delete(found);
		dao.flush();
		try {
			assertNotNull("getById returned null", dao.getById(id));
			fail("Deleted object found");
		} catch (NoResultException e) {
			// object was deleted
		}
		assertFalse("Not deleted from list", dao.findAll().contains(saved));
	}

	/**
	 * Creates an entity to test.
	 * @return An entity
	 * @see #testDAO()
	 */
	protected abstract T createEntity();

	/**
	 * Asserting that the entities are equal.
	 * <p>Used for check a loaded entity.</p>
	 * @param expected The expected entity
	 * @param found The found entity
	 */
	protected void assertEntityEquals(T expected, T found) {
		assertNotNull("No found object", found);
		assertEquals("Not same ID", expected.getId(), found.getId());
		assertEquals(expected, found);
		assertEquals(expected.hashCode(), found.hashCode());
	}

	protected abstract SimpleDao<I, T> getDao();

}
