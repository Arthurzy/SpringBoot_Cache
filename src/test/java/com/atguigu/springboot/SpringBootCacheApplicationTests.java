package com.atguigu.springboot;

import java.util.concurrent.TimeUnit;

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
    RedisTemplate<Object, Object> redisTemplate;
    
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
        redisTemplate.opsForValue().set("emp-01", employee);      
    }
    
    @Test
    public void test04() {
        if (redisTemplate.opsForValue().get("emp-01") == null) {
            System.out.println("emp-01不在，换02!");
            Employee employee = employeeMapper.getEmpById(2);
            redisTemplate.opsForValue().set("emp-02", employee);      
        }else {
            System.out.println(redisTemplate.opsForValue().get("emp-01").toString());
        }     
    }
    
    @Test
	void contextLoads() {
	    Employee employee = employeeMapper.getEmpById(1);
	    System.out.println(employee);
	}
    
    @Test
    public void test05() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
			new Thread(new TestRedisMultiTread(), "redis-thread-" + i).start();	
		}
			
		Thread.sleep((long)10000);
		redisTemplate.delete("isSent");
    }
    
    @Test
    public void test06() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Boolean isSent = redisTemplate.opsForValue().setIfAbsent(Thread.currentThread().getName(), 1, 30, TimeUnit.MINUTES);
			        
			        if (null != isSent && isSent) {
//			        	redisTemplate.exec();
			            System.out.println(Thread.currentThread().getName() + "开始发送!!!");
			        }else {
			        	System.out.println(Thread.currentThread().getName() + "不用发送!!!!!!!!!!!!! ");
			            
			        }	    
				}
			}, "redis-thread-" + i).start();	
		}
			
		Thread.sleep((long)10000);
		redisTemplate.delete("isSent");
    }

    
    class TestRedisMultiTread implements Runnable{

		@Override
		public void run() {			
//			redisTemplate.setEnableTransactionSupport(true);
//			redisTemplate.watch("isSent");
//	        redisTemplate.multi();
	        Boolean isSent = redisTemplate.opsForValue().setIfAbsent("isSent", 1, 30, TimeUnit.MINUTES);
	        
	        if (null != isSent && isSent) {
//	        	redisTemplate.exec();
	            System.out.println(Thread.currentThread().getName() + "开始发送!!!");
	        }else {
	        	System.out.println(Thread.currentThread().getName() + "不用发送!!!!!!!!!!!!! ");
	            
	        }	        
		}    	
    }
}
