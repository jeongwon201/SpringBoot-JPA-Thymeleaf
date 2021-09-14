package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.Item;
import org.hdcd.domain.Member;
import org.hdcd.domain.UserItem;

public interface UserItemService {

	public void register(Member member, Item item) throws Exception;
	
	public List<UserItem> list(Long userNo) throws Exception;
	
	public UserItem read(Long userItemNo) throws Exception;
}
