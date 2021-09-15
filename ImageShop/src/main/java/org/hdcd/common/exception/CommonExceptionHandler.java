package org.hdcd.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {
	@ExceptionHandler(NotEnoughCoinException.class)
	public String handleNotEnoughCoinException(NotEnoughCoinException ex, WebRequest request) {
		log.info("handleNotEnoughCoinException");
		
		return "redirect:/coin/notEnoughCoin";
	}
	
	@ExceptionHandler(NotMyItemException.class)
	public String handleNotMyItemException(NotMyItemException ex, WebRequest request) {
		
		return "redirect:/useritem/notMyItem";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(isAjax(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			throw ex;
		}
	}
	
	@ExceptionHandler(Exception.class)
	public String handle(Exception ex) {
		log.info("handle ex " + ex.toString());
		
		return "error/errorCommon";
	}
	
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
