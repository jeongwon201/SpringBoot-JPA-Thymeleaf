package org.hdcd.common.service;

import java.util.List;

import org.hdcd.common.domain.AccessLog;

public interface AccessLogService {
	
	public void register(AccessLog accessLog) throws Exception;
	
	public List<AccessLog> list() throws Exception;
}
