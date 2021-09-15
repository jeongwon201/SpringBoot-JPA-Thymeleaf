package org.hdcd.common.service;

import java.util.List;

import org.hdcd.common.domain.PerformanceLog;
import org.hdcd.common.repository.PerformanceLogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PerformanceLogServiceImpl implements PerformanceLogService {

	private final PerformanceLogRepository repository;
	
	@Override
	public void register(PerformanceLog performanceLog) throws Exception {
		repository.save(performanceLog);
	}

	@Override
	public List<PerformanceLog> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "logNo"));
	}

}
