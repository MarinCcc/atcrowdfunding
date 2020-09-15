package com.marin.crowd.mvc.handler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.marin.crowd.constant.CrowdConstant;
import com.marin.crowd.entity.Admin;
import com.marin.crowd.service.api.AdminService;

@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService;
	
	
	@RequestMapping("admin/to/edit/page")
	public String toEditPage(@RequestParam("adminId") Integer adminId,
			ModelMap modelMap) {
		
		Admin admin = adminService.getadminById(adminId);
		
		modelMap.addAttribute("admin", admin);
		
		return "admin-edit";
	}
	
	@RequestMapping("admin/update.html")
	public String update(Admin admin,
			@RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword) {
		
		adminService.updateAdmin(admin);
		
		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}
	
	
	
	@RequestMapping("admin/save.html")
	public String save(Admin admin) {
		
		adminService.saveAdmin(admin);
		
		return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
	}
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String remove(@PathVariable("adminId") Integer adminId,
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("keyword") String keyword) {
		
		adminService.remove(adminId);
		
		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}
	
	@RequestMapping("/admin/do/login.html")
	public String doLogin(@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd,
			HttpSession session) {
		
		Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
		
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
		
		return "redirect:/admin/to/main/page.html";
		
	}
	
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/admin/to/login/page.html";
	}
	
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(
			@RequestParam(value="keyword", defaultValue="") String keyword,
			@RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
			ModelMap modelMap
			) {
		PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
		return "admin-page";
	}
}
