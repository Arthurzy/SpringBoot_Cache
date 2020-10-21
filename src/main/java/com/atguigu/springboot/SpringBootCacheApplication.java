package com.atguigu.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

/**
 * A.搭建基本环境
 *  a.导入数据库文件 创建department和employee表
 *  b.创建java bean封装数据
 *  c.整合mybatis操作数据库
 *      i. 配置数据源
 *      ii.使用注解版的mybatis
 *          a.@MapperScan指定需要扫描的mapper接口所在的包
 * B.快速体验缓存
 *  a.步骤
 *      i.开启基于注解的缓存 @EnableCaching
 *      ii.标注缓存注解即可
 *          @Cacheable
            @CacheEvict
            @CachePut
 *  
 * @author YongZhang
 *
 */

@MapperScan("com.atguigu.springboot.mapper")
@EnableCaching
@SpringBootApplication
public class SpringBootCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCacheApplication.class, args);
	}

}
