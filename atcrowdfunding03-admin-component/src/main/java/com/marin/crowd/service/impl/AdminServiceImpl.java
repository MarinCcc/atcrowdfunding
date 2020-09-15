package com.marin.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.marin.crowd.constant.CrowdConstant;
import com.marin.crowd.entity.Admin;
import com.marin.crowd.entity.AdminExample;
import com.marin.crowd.entity.AdminExample.Criteria;
import com.marin.crowd.exception.LoginAcctAlreadyInUseException;
import com.marin.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.marin.crowd.exception.LoginFailedException;
import com.marin.crowd.mapper.AdminMapper;
import com.marin.crowd.service.api.AdminService;
import com.marin.crowd.util.CrowdUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void saveAdmin(Admin admin) {
		
		String pwd = admin.getUserPswd();
//		pwd = CrowdUtil.md5(pwd);
		pwd = passwordEncoder.encode(pwd);
		admin.setUserPswd(pwd);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = format.format(date);
		admin.setCreateTime(createTime);
		
		try {
		adminMapper.insert(admin);	
		}catch(Exception e) {
			if(e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
		
	}


	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
		
		AdminExample adminExample = new AdminExample();
		
		Criteria criteria = adminExample.createCriteria();
		
		criteria.andLoginAcctEqualTo(loginAcct);
		
		List<Admin> list = adminMapper.selectByExample(adminExample);
		
		if(list==null || list.size()==0) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		if(list.size()>1) {
			throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
		}
		
		Admin admin = list.get(0);
		
		if(admin==null) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		String userPswdDB = admin.getUserPswd();
		
		String userPswdForm = CrowdUtil.md5(userPswd);
		
		if(!Objects.equals(userPswdForm,userPswdDB)) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		return admin;
	}


	public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
		// 1.开启分页功能 
		PageHelper.startPage(pageNum, pageSize);
		
		// 2.查询 Admin 数据
		List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
		
//		// ※辅助代码:打印 adminList 的全类名
//		Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
//		logger.debug("adminList 的全类名是:"+adminList.getClass().getName()); 
//		
//		// 3.为了方便页面使用将 adminList 封装为 PageInfo
//		PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
		
		
		return new PageInfo<Admin>(adminList);
	}


	public void remove(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
	}


	public Admin getadminById(Integer adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}


	public void updateAdmin(Admin admin) {
		try {
		adminMapper.updateByPrimaryKeySelective(admin);
		}catch(Exception e) {
			e.printStackTrace();
			if(e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}


	public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
		adminMapper.deleteOldRelationship(adminId);
		
		if(roleIdList !=null && roleIdList.size()>0) {
			adminMapper.insertNewRelationship(adminId,roleIdList);
		}
		
	}


	public Admin getAdminByLoginAcct(String username) {
		AdminExample example = new AdminExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andLoginAcctEqualTo(username);
		
		
		
		return adminMapper.selectByExample(example).get(0);
	}

	
}
