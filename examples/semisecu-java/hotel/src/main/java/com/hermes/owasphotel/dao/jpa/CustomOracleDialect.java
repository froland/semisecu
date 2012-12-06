package com.hermes.owasphotel.dao.jpa;

import java.util.Properties;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

/**
 * Uses a sequence generator per table.
 * 
 * <p>
 * Adapted from:
 * 
 * <pre>
 * http://grails.1312388.n4.nabble.com/One-hibernate-sequence-is-used-for-all-Postgres-tables-td1351722.html
 * </pre>
 * 
 * </p>
 */
public class CustomOracleDialect extends Oracle10gDialect {
	/**
	 * Get the native identifier generator class.
	 * 
	 * @return {@link TableNameSequenceGenerator}
	 */
	@Override
	public Class<?> getNativeIdentifierGeneratorClass() {
		return TableNameSequenceGenerator.class;
	}

	/**
	 * Creates a sequence per table instead of the default behavior of one
	 * sequence.
	 */
	public static class TableNameSequenceGenerator extends SequenceGenerator {

		/**
		 * {@inheritDoc} If the parameters do not contain a
		 * {@link SequenceGenerator#SEQUENCE} name, we assign one based on the
		 * table name.
		 */
		@Override
		public void configure(final Type type, final Properties params,
				final Dialect dialect) {
			if (params.getProperty(SEQUENCE) == null
					|| params.getProperty(SEQUENCE).length() == 0) {
				String tableName = params
						.getProperty(PersistentIdentifierGenerator.TABLE);
				if (tableName != null) {
					params.setProperty(SEQUENCE, tableName + "_seq");
				}
			}
			super.configure(type, params, dialect);
		}
	}
}
