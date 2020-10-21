package com.atguigu.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.mapper.EmployeeMapper;

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
     *          a.编写SpEL
     *      
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp", key = "#id")
    public Employee getEmp(Integer id) {
        System.out.println("查询 " + id + " 号员工");
        Employee employee = employeeMapper.getEmpById(id);
        return employee;
    }
}
