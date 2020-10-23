package com.atguigu.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * A.搭建基本环境
 *  a.导入数据库文件 创建department和employee表
 *  b.创建java bean封装数据
 *  c.整合mybatis操作数据库
 *      i. 配置数据源
 *      ii.使用注解版的mybatis
 *          a.@MapperScan指定需要扫描的mapper接口所在的包
 * 
 * B.快速体验缓存
 *  a.步骤
 *      i.开启基于注解的缓存 @EnableCaching
 *      ii.标注缓存注解即可
 *          @Cacheable  主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
 *          @CacheEvict 清空缓存
 *          @CachePut   保证方法被调用，又希望结果被缓存
 *          
 * C.默认使用的是ConcurrentMapCacheManager，开发中使用缓存中间件：redis，ehcache，memcache
 *   a.整合redis
 *      i.安装redis，使用docker
 *      ii.引入redis的starter
 *      iii.配置redis
 *      iv.测试缓存
 *          a).原理 CacheManager创建Cache缓存组件来实际缓存中存储数据
 *          b).RedisCacheManager 默认使用JDK序列化机制
 *              
 * 
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
