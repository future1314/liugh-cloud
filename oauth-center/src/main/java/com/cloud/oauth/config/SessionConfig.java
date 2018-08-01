package com.cloud.oauth.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 开启session共享,借助redis实现
 * 
 * @author liugh 53182347@qq.com
 *
 */
//请求信息会被自动记录到redis里
@EnableRedisHttpSession
public class SessionConfig {

}
