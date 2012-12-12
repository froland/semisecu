package com.hermes.owasphotel.service;

import com.hermes.owasphotel.domain.User;

public interface UserService {
	
	public User find(Integer id);
	
	public User find(String name);
	
	public boolean isUsed(String name);
	
	public boolean isMatching(Integer id, String name);

}
