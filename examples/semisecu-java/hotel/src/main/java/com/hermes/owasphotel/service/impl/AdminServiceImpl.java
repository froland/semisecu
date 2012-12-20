package com.hermes.owasphotel.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.Dumper;
import com.hermes.owasphotel.service.AdminService;

@Service
@Transactional
@PreAuthorize("hasRole('admin')")
public class AdminServiceImpl implements AdminService {
	private static final Logger logger = Logger
			.getLogger(AdminServiceImpl.class);

	@Autowired
	private Dumper dumper;

	@Override
	public String getDumpString(String tableName) {
		StringWriter s = new StringWriter();
		try {
			dumper.dump(tableName, null, s);
		} catch (DataAccessException e) {
			logger.error("Dump failed", e);
		} catch (IOException e) {
			logger.error("Dump failed", e);
		} catch (SQLException e) {
			logger.error("Dump failed", e);
		}
		return s.toString();
	}

	@Override
	public void dumpToWriter(String tableName, String[] columns, Writer w)
			throws IOException {
		try {
			dumper.dump(tableName, columns, w);
		} catch (DataAccessException e) {
			logger.error("Dump failed", e);
		} catch (IOException e) {
			logger.error("Dump failed", e);
		} catch (SQLException e) {
			logger.error("Dump failed", e);
		}
	}

	@Override
	public List<String> listTables() {
		try {
			return dumper.listTables();
		} catch (SQLException e) {
			logger.error("Failed to list tables", e);
			return new ArrayList<String>();
		}
	}

	@Override
	public List<String> listColumns(String tableName) {
		List<String> list;
		try {
			list = dumper.listColumns(tableName);
		} catch (SQLException e) {
			logger.error("Failed to list columns");
			return new ArrayList<String>();
		}
		// mask some columns
		if ("USERS".equalsIgnoreCase(tableName)) {
			list.remove("PASSWORD");
		}
		return list;
	}

}
