package org.hdcd.common.util;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetUtils {
	
	public static String getIp(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Forwarded-For");
		
		log.info(">>>> X-Forwarded-For : " + ip);
		
		if(ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			log.info(">>>> Proxy-Client-IP" + ip);
		}
		
		if(ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			log.info(">>>> WL-Proxy-Client-IP" + ip);
		}
		
		if(ip == null) {
			ip = request.getHeader("HTTP-Client-IP");
			log.info(">>>> HTTP-Client-IP" + ip);
		}
		
		if(ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			log.info(">>>> HTTP_X_FORWARDED_FOR" + ip);
		}
		
		if(ip == null) {
			ip = request.getRemoteAddr();
		}
		log.info(">>>> Result : IP Address : " + ip);
		
		return ip;
	}
}
