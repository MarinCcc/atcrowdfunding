package com.marin.crowd.service.api;

import java.util.List;
import java.util.Map;

import com.marin.crowd.entity.Auth;

public interface AuthService {

	List<Auth> getAll();

	List<Integer> getAssignedAuthByRoleId(Integer roleId);

	void saveRoleAuthRelationship(Map<String, List<Integer>> map);

	List<String> getAssignedAuthNameByAdminId(Integer adminId);


}
