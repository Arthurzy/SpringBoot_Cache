package com.atguigu.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.mapper.EmployeeMapper;

@SpringBootTest
class SpringBootCacheApplicationTests {
    
    @Autowired
    EmployeeMapper employeeMapper;

	@Test
	void contextLoads() {
	    Employee employee = employeeMapper.getEmpById(1);
	    System.out.println(employee);
	}

}
