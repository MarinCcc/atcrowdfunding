package com.marin.crowd.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.marin.crowd.entity.Admin;
import com.marin.crowd.mapper.AdminMapper;

//指定 Spring 给 Junit 提供的运行器类 
@RunWith(SpringJUnit4ClassRunner.class)
//加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Test
	public void testMapper() {
		Admin admin = new Admin(null,"tom","123123","tom","tom@qq.com",null);
		adminMapper.insert(admin);
	}
	
	@Test
	public void testDataSource() throws SQLException {
	// 1.通过数据源对象获取数据源连接
	Connection connection = dataSource.getConnection();
	// 2.打印数据库连接 System.out.println(connection);
	}

}
