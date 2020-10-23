package com.atguigu.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.mapper.EmployeeMapper;

@CacheConfig(cacheNames = "emp") // 抽取缓存的公共配置
@Service
public class EmployeeService {
    
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * i.将方法的运行结果缓存；以后再要有相同的数据，直接从缓存中获取，不用调用方法.
     * CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字
     *  a.几个属性
     *      cacheNames/value： 指定缓存的名字
     *      key:缓存数据时用的key，用它来指定，默认是使用方法参数的的值       1-方法的的返回值
     *          a.编写SpEL: #id 参数id的值
     *      keyGenerator: key生成器；可以自己指定key的生成器的组件id
     *      key/keyGenerator:二选一使用
     *      cacheManager：指定缓存管理器 或者cacheResolver指定获取解析器
     *      condition：指定符合条件的情况下才缓存 
     *          #id > 0
     *      unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存;可以获取到结果进行缓存
     *          #result == null
     *      sync:是否使用异步模式
     *      
     * ii.原理
     *   a.自动配置类：CacheAutoConfiguration
     *   b.缓存的配置类
     *      org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration   
     *   c.SimpleCacheConfiguration 生效
     *   d.给容器中注册了一个CacheManager，ConcurrentMapCacheManager
     *   e.ConcurrentMapCacheManager:可以获取和创建ConcurrentMapCache类型的缓存组件，它的作用是将数据保存在ConcurrentMap中
     *   
     * iii.运行流程
     *   @Cacheable
     *      a.方法运行前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取
     *          (CacheManager先获取相应的缓存)，第一次获取缓存如果没用Cache组件会自动创建
     *      b.去Cache中查找缓存的内容，使用一个key，默认方法就是方法的参数
     *          key是按照某种策略生成的：默认是使用keyGenerator生成的,默认使用SimpleKeyGenerator生成key
     *      c.没用查询到到缓存就调用目标方法
     *      d.将目标方法返回的结果，放进缓存
     *      
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp"/*, keyGenerator = "myKeyGenerator", condition = "#id > 0", unless = "#result == null"*/)
    public Employee getEmp(Integer id) {
        System.out.println("查询 " + id + " 号员工");
        Employee employee = employeeMapper.getEmpById(id);
        return employee;
    }
    
    
    /**
     * @CachePut:即调用方法，又更新缓存数据： 修改了数据库的某个数据同时更新缓存
     * i.方法运行之后
     * ii.同步更新缓存
     * 
     */
    @CachePut(value = "emp")
    public Employee updateEmp(Employee employee) {
        System.out.println("更新员工 " + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }
    
    /**
     * @CacheEvict：缓存清除
     *  key:指定要清楚的数据
     *  allEntries：指定清除这个缓存中的所有数据
     *  beforeInvocation：缓存的清除是否在方法执行之前执行，默认是在方法执行之后执行，如果方法执行异常，缓存不会被清除
     */
    @CacheEvict(/*value = "emp", key = "#id"*/ allEntries = true )
    public void deleteEmp(Integer id) {
        System.out.println("删除 " + id + " 号员工");
//        employeeMapper.deleteEmpById(id);
    }
    
    // @Caching 定义复杂的缓存规则
    @Caching(
        cacheable = {
            @Cacheable(/*value = "emp",*/ key = "#lastName")
        },
        put = {
            @CachePut(/*value = "emp",*/ key = "#result.id"),
            @CachePut(/*value = "emp",*/ key = "#result.email")
        },
        evict = {
            @CacheEvict(/*value = "emp",*/ key = "#lastName")
        }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
