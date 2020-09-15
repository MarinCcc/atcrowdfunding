package com.marin.crowd.service.api;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.marin.crowd.entity.Role;


public interface RoleService {

	PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize,String keyword);

	void saveRole(Role role);

	void updateRole(Role role);
	
	void removeRole(List<Integer> list);

	List<Role> getAssignedRole(Integer adminId);

	List<Role> getUnAssignedRole(Integer adminId);
}
