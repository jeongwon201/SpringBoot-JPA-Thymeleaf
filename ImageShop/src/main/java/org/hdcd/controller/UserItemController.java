package org.hdcd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.hdcd.common.exception.NotMyItemException;
import org.hdcd.common.security.domain.CustomUser;
import org.hdcd.domain.Member;
import org.hdcd.domain.UserItem;
import org.hdcd.prop.ShopProperties;
import org.hdcd.service.UserItemService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/useritem")
public class UserItemController {
	
	private final UserItemService service;
	
	private final ShopProperties shopProperties;
	
	@GetMapping("/list")
	@PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
	public void list(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();
		
		Long userNo = member.getUserNo();
		
		model.addAttribute("list", service.list(userNo));
	}
	
	@GetMapping("/read")
	@PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
	public void read(Long userItemNo, Model model) throws Exception {
		model.addAttribute(service.read(userItemNo));
	}
	
	@ResponseBody
	@GetMapping("/download")
	@PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
	public ResponseEntity<byte[]> download(Long userItemNo, Authentication authentication) throws Exception {
		UserItem userItem = service.read(userItemNo);
		
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();
		
		if(userItem.getUserNo() != member.getUserNo()) {
			throw new NotMyItemException("It is Not My Item.");
		}
		
		String fullName = userItem.getPictureUrl();
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		try {
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(shopProperties.getUploadPath() + File.separator + fullName);
			
			String fileName = fullName.substring(fullName.indexOf("_") + 1);
			
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		
		return entity;
	}
	
	@GetMapping("/notMyItem")
	@PreAuthorize("hasRole('ADMIN', 'MEMBER')")
	public void notMyItem(Model model) throws Exception {
		
	}
	

}