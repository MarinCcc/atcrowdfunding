package com.marin.crowd.service.api;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.marin.crowd.entity.Admin;

public interface AdminService {

	void saveAdmin(Admin admin);

	Admin getAdminByLoginAcct(String loginAcct, String userPswd);
	
	PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

	void remove(Integer adminId);

	Admin getadminById(Integer adminId);

	void updateAdmin(Admin admin);

	void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

	Admin getAdminByLoginAcct(String username);
}
