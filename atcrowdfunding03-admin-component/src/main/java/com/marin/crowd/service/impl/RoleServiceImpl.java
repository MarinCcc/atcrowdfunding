package com.marin.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.marin.crowd.entity.Role;
import com.marin.crowd.entity.RoleExample;
import com.marin.crowd.entity.RoleExample.Criteria;
import com.marin.crowd.mapper.RoleMapper;
import com.marin.crowd.service.api.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<Role> list = roleMapper.selectRoleByKeyword(keyword);
		
		return new PageInfo<Role>(list);
	}

	public void saveRole(Role role) {
		roleMapper.insert(role);
		
	}

	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
		
	}

	public void removeRole(List<Integer> list) {
		
		RoleExample example = new RoleExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		
		roleMapper.deleteByExample(example);
		
	}

	public List<Role> getAssignedRole(Integer adminId) {
		return roleMapper.getAssignedRole(adminId);
	}

	public List<Role> getUnAssignedRole(Integer adminId) {
		return roleMapper.getUnassignedRole(adminId);
	}
	
	
}
