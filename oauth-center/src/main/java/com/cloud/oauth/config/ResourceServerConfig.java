package com.cloud.oauth.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.cloud.common.constants.PermitAllUrl;

/**
 * 资源服务配置<br>
 * 
 * 注解@EnableResourceServer帮我们加入了org.springframework.security.
 * oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter<br>
 * 该filter帮我们从request里解析出access_token<br>
 * 并通过org.springframework.security.oauth2.provider.token.DefaultTokenServices根据access_token和认证服务器配置里
 * 的TokenStore从redis或者jwt里解析出用户
 * 
 * 注意认证中心的@EnableResourceServer和别的微服务里的@EnableResourceServer有些不同<br> 在OAuth2AuthenticationManager里看
 * 别的微服务是通过org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices来获取用户的,
 * 认证中心是DefaultTokenServices
 * UserInfoTokenServices的方法getMap(String path, String accessToken)
 * 就是通过配置文件里的security.oauth2.resource.user-info-uri: http://127.0.0.1:8080/api-o/user-me
 * 加上token发起了一次get请求
 * 然后把map转换成OAuth2Authentication当前登录用户对象
 * @author liugh 53182347@qq.com
 *
 */
@Configuration
//认证中心同时也是一个资源服务器
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new OAuth2RequestedMatcher()).authorizeRequests()
				.antMatchers(PermitAllUrl.permitAllUrl()).permitAll() // 放开权限的url
				.anyRequest().authenticated();
	}

	/**
	 * 判断来源请求是否包含oauth2授权信息<br>
	 * url参数中含有access_token,或者header里有Authorization
	 */
	private static class OAuth2RequestedMatcher implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			// 请求参数中包含access_token参数
			if (request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) != null) {
				return true;
			}

			// 头部的Authorization值以Bearer开头
			String auth = request.getHeader("Authorization");
			if (auth != null) {
				return auth.startsWith(OAuth2AccessToken.BEARER_TYPE);
			}

			return false;
		}
	}

}
