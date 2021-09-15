package org.hdcd.controller;

import java.util.Locale;

import org.hdcd.common.security.domain.CustomUser;
import org.hdcd.domain.ChargeCoin;
import org.hdcd.domain.Member;
import org.hdcd.service.CoinService;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/coin")
public class CoinController {
	
	private final CoinService service;
	
	private final MessageSource messageSource;
	
	@GetMapping("/charge")
	@PreAuthorize("hasRole('MEMBER')")
	public void chargeForm(Model model) throws Exception {
		ChargeCoin chargeCoin = new ChargeCoin();
		chargeCoin.setAmount(1000);
		
		model.addAttribute(chargeCoin);
	}
	
	@PostMapping("/charge")
	@PreAuthorize("hasRole('MEMBER')")
	public String charge(int amount, RedirectAttributes rttr, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		
		Long userNo = member.getUserNo();
		
		ChargeCoin chargeCoin = new ChargeCoin();
		chargeCoin.setUserNo(userNo);
		chargeCoin.setAmount(amount);
		
		service.charge(chargeCoin);
		
		String message = messageSource.getMessage("coin.chargingComplete", null, Locale.KOREAN);
		rttr.addFlashAttribute("msg", message);
		
		return "redirect:/coin/success";
	}
	
	@GetMapping("/success")
	public String success() throws Exception {
		return "coin/success";
	}
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MEMBER')")
	public void list(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();
		
		Long userNo = member.getUserNo();
		
		model.addAttribute("list", service.list(userNo));
	}
	
	@GetMapping("/listPay")
	@PreAuthorize("hasRole('MEMBER')")
	public void listPayHistory(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();
		
		Long userNo = member.getUserNo();
		
		model.addAttribute("list", service.listPayHistory(userNo));
	}
	
	@GetMapping("/notEnoughCoin")
	@PreAuthorize("hasRole('MEMBER')")
	public void notEnoughCoin(Model model) throws Exception {
		
	}
}
