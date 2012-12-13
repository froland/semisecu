package com.hermes.owasphotel.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.Dumper;
import com.hermes.owasphotel.service.AdminService;


@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	
	//@Autowired
	private Dumper dumper;
	
	@Override
	public String getDumpString(String tableName) throws IOException
	{
		return dumper.dump(tableName);
	}

}
