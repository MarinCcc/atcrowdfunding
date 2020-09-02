package com.marin.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marin.crowd.entity.Admin;
import com.marin.crowd.mapper.AdminMapper;
import com.marin.crowd.service.api.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	public void saveAdmin(Admin admin) {
		adminMapper.insert(admin);	
	}

	
}
