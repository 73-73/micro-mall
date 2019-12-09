package com.micromall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author pan
 * @create 2019-12-09-8:37
 */
@SpringBootApplication
/*@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到改服务。
不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。*/
//@EnableEurekaClient
@EnableDiscoveryClient
public class mallItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(mallItemServiceApplication.class,args);
    }
}
