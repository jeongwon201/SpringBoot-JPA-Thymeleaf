package org.hdcd.service;

import java.util.ArrayList;
import java.util.List;

import org.hdcd.domain.CodeGroup;
import org.hdcd.dto.CodeLabelValue;
import org.hdcd.repository.CodeGroupRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService{

	private final CodeGroupRepository repository;

	@Override
	public List<CodeLabelValue> getCodeGroupList() throws Exception {
		List<CodeGroup> codeGroups = repository.findAll(Sort.by(Direction.ASC, "groupCode"));
		
		List<CodeLabelValue> codeGroupList = new ArrayList<CodeLabelValue>();
		
		for(CodeGroup codeGroup : codeGroups) {
			codeGroupList.add(new CodeLabelValue(codeGroup.getGroupCode(), codeGroup.getGroupName()));
		}
		
		return codeGroupList;
	}
}
