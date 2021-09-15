package org.hdcd.common.service;

import java.util.List;

import org.hdcd.common.domain.PerformanceLog;

public interface PerformanceLogService {
	
	public void register(PerformanceLog performanceLog) throws Exception;
	
	public List<PerformanceLog> list() throws Exception;
}
