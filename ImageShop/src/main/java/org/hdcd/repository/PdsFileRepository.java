package org.hdcd.repository;

import java.util.List;

import org.hdcd.domain.PdsFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdsFileRepository extends JpaRepository<PdsFile, Long> {
	public List<PdsFile> findByFullName(String fullName);
}
