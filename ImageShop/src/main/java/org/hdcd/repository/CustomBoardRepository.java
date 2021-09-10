package org.hdcd.repository;

import org.hdcd.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoardRepository {
	public Page<Board> getSearchPage(String searchType, String keyword, Pageable pageable);
}
