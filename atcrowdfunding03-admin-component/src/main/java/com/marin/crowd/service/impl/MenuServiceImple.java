package com.marin.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marin.crowd.entity.Menu;
import com.marin.crowd.entity.MenuExample;
import com.marin.crowd.mapper.MenuMapper;
import com.marin.crowd.service.api.MenuService;
@Service
public class MenuServiceImple implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	
	public List<Menu> getAll() {
		return menuMapper.selectByExample(new MenuExample());
	}

	
	public void saveMenu(Menu menu) {
		menuMapper.insert(menu);
	}

	public void updateMenu(Menu menu) {
		
		// 由于pid没有传入，一定要使用有选择的更新，保证“pid”字段不会被置空
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	
	public void removeMenu(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}
	
}
