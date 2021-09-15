package org.hdcd.common.repository;

import org.hdcd.common.domain.PerformanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceLogRepository extends JpaRepository<PerformanceLog, Long> {

}
