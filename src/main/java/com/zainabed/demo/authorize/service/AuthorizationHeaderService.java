package com.zainabed.demo.authorize.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.zainabed.demo.authorize.entity.UserCredentailImpl;

/**
 * 
 * @author shaikzai
 *
 */
@Component
public interface AuthorizationHeaderService {

	String AUTH_TYPE_BEARER = "Bearer ";
	String AUTH_TYPE_BASIC = "Basic ";
	String AUTH_HEADER = "Authorization";

	/**
	 * 
	 * @param type
	 * @return
	 */
	String getValue(HttpServletRequest request, String type);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	UserCredentailImpl getBasicUserCredentials(HttpServletRequest request);
}
