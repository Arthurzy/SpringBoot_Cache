package com.atguigu.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.mapper.EmployeeMapper;

@SpringBootTest
class SpringBootCacheApplicationTests {
    
    @Autowired
    EmployeeMapper employeeMapper;
    
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;
    
    /**
     * String(字符串)、List(列表)、Set(集合)、Hash(散列)、ZSet(有序集合)
     */
    @Test
    public void test01() {
        stringRedisTemplate.opsForValue().append("msg", "hello");
    }
    
    @Test
    public void test02() {
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }

    @Test
    public void test03() {
        Employee employee = employeeMapper.getEmpById(1);
        empRedisTemplate.opsForValue().set("emp-01", employee);      
    }
    
	@Test
	void contextLoads() {
	    Employee employee = employeeMapper.getEmpById(1);
	    System.out.println(employee);
	}

}
