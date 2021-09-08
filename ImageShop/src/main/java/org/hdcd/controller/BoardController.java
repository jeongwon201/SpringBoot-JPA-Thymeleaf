package org.hdcd.controller;

import org.hdcd.common.security.domain.CustomUser;
import org.hdcd.domain.Board;
import org.hdcd.domain.Member;
import org.hdcd.domain.PageRequestVO;
import org.hdcd.dto.PaginationDTO;
import org.hdcd.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("/register")
	@PreAuthorize("hasRole('MEMBER')")
	public void registerForm(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		
		Board board = new Board();
		
		board.setWriter(member.getUserId());
		
		model.addAttribute(board);
	}
	
	@PostMapping("/register")
	@PreAuthorize("hasRole('MEMBER')")
	public String register(Board board, RedirectAttributes rttr) throws Exception {
		service.register(board);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public void list(@ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception {
		Page<Board> page = service.list(pageRequestVO);
		
		model.addAttribute("pgntn", new PaginationDTO<Board>(page));
	}
	
	@GetMapping("/read")
	public void read(Long boardNo, @ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception {
		model.addAttribute(service.read(boardNo));
	}
	
	@GetMapping("/modify")
	@PreAuthorize("hasRole('MEMBER')")
	public void modifyForm(Long boardNo, @ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception {
		model.addAttribute(service.read(boardNo));
	}
	
	@PostMapping("/modify")
	@PreAuthorize("hasRole('MEMBER') and principal.username == #board.writer")
	public String modify(Board board, PageRequestVO pageRequestVO, RedirectAttributes rttr) throws Exception {
		service.modify(board);
		
		rttr.addFlashAttribute("page", pageRequestVO.getPage());
		rttr.addFlashAttribute("sizePerPage", pageRequestVO.getSizePerPage());

		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	@PreAuthorize("hasRole('MEMBER') and principal.username == #writer or hasRole('ADMIN')")
	public String remove(Long boardNo, PageRequestVO pageRequestVO, RedirectAttributes rttr, String writer) throws Exception {
		service.remove(boardNo);
		
		rttr.addFlashAttribute("page", pageRequestVO.getPage());
		rttr.addFlashAttribute("sizePerPage", pageRequestVO.getSizePerPage());
		
		rttr.addAttribute("msg", "SUCCESS");
		
		return "redirect:/board/list";
	}
}
