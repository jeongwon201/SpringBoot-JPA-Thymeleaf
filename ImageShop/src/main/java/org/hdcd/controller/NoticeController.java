package org.hdcd.controller;

import org.hdcd.domain.Notice;
import org.hdcd.service.NoticeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService service;
	
	@GetMapping("/register")
	@PreAuthorize("hasRole('ADMIN')")
	public void registerForm(Model model) throws Exception {
		Notice notice = new Notice();
		
		model.addAttribute(notice);
	}
	
	@PostMapping("/register")
	@PreAuthorize("hasRole('ADMIN')")
	public String register(Notice notice, RedirectAttributes rttr) throws Exception {
		service.register(notice);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/notice/list";
	}
	
	@GetMapping("/list")
	public void list(Model model) throws Exception {
		model.addAttribute("list", service.list());
	}
	
	@GetMapping("/read")
	public void read(Long noticeNo, Model model) throws Exception {
		model.addAttribute(service.read(noticeNo));
	}
	
	@GetMapping("/modify")
	@PreAuthorize("hasRole('ADMIN')")
	public void modifyForm(Long noticeNo, Model model) throws Exception {
		model.addAttribute(service.read(noticeNo));
	}
	
	@PostMapping("/modify")
	@PreAuthorize("hasRole('ADMIN')")
	public String modify(Notice notice, RedirectAttributes rttr) throws Exception {
		service.modify(notice);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/notice/list";
	}
	
	@PostMapping("/remove")
	@PreAuthorize("hasRole('ADMIN')")
	public String remove(Long noticeNo, RedirectAttributes rttr) throws Exception{
		service.remove(noticeNo);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/notice/list";
	}
}
