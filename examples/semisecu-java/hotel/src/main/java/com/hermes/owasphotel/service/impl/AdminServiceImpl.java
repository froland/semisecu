package com.hermes.owasphotel.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.Dumper;
import com.hermes.owasphotel.service.AdminService;

@Service
@Transactional
@PreAuthorize("hasRole('admin')")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private Dumper dumper;

	public Dumper getDumper() {
		return dumper;
	}

	public void setDumper(Dumper dumper) {
		this.dumper = dumper;
	}

	@Override
	public void dumpToWriter(String tableName, String[] columns, Writer w)
			throws IOException {
		dumper.dump(tableName, columns, w);
	}

	@Override
	public List<String> listTables() {
		return dumper.listTables();
	}

	@Override
	public List<String> listColumns(String tableName) {
		List<String> list = dumper.listColumns(tableName);
		// mask some columns
		if ("USER".equalsIgnoreCase(tableName)) {
			list.remove("PASSWORD");
		}
		return list;
	}

}
