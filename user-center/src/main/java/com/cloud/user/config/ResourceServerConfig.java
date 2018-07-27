package com.cloud.user.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.cloud.common.constants.PermitAllUrl;

/**
 * 资源服务配置
 * 
 * @author liugh 53182347@qq.com
 *
 */
//注解@EnableResourceServer帮我们加入了org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter<br>
@EnableResourceServer //每个url就是一种资源
//spring security的注解  prePostEnabled = true表示controller层的 @PreAuthorize("hasAuthority('back:role:save')")开启注解
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().
				//异常的时候处理 返回SC_UNAUTHORIZED --> 401状态码未授权异常
				exceptionHandling().authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().authorizeRequests()
				//放行注解url
				.antMatchers(PermitAllUrl.permitAllUrl("/users-anon/**", "/wechat/**")).permitAll() // 放开权限的url
				.anyRequest().authenticated().and().httpBasic();
	}

	//spring security内置密码加密的bean
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
