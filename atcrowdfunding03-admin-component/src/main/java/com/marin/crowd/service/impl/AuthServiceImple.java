package com.marin.crowd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marin.crowd.entity.Auth;
import com.marin.crowd.entity.AuthExample;
import com.marin.crowd.mapper.AuthMapper;
import com.marin.crowd.service.api.AuthService;
@Service
public class AuthServiceImple implements AuthService {

	@Autowired
	private AuthMapper authMapper;

	public List<Auth> getAll() {
		return authMapper.selectByExample(new AuthExample());
	}

	public List<Integer> getAssignedAuthByRoleId(Integer roleId) {
		return authMapper. getAssignedAuthByRoleId(roleId);
	}

	public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
		List<Integer> roleList = map.get("roleId");
		Integer roleId = roleList.get(0);
		
		authMapper.deleteOldRelationship(roleId);
		
		List<Integer> authIdList = map.get("authIdArray");
		
		if(authIdList !=null && authIdList.size()>0) {
			authMapper.insertNewRelationship(roleId,authIdList);
		}
	}

	public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
		return authMapper.getAssignedAuthNameByAdminId(adminId);
	}

	
}
