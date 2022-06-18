package com.itwillbs.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.service.MemberService;

@Controller
public class MemberController {

	//MemberService memberService=new MemberServiceImpl()객체생성
	@Inject
	private MemberService memberService;
	
	@RequestMapping(value = "/member/insert", method = RequestMethod.GET)
	public String insert() {
		// /WEB-INF/views/member/insertForm.jsp
		return "member/insertForm";
	}
	
	@RequestMapping(value = "/member/insertPro", method = RequestMethod.POST)
	public String insertPro(MemberDTO memberDTO) {
		//회원가입 메서드호출
		memberService.insertMember(memberDTO);
		
		return "redirect:/member/login";
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String login() {
		
		return "member/loginForm";
	}
	@RequestMapping(value = "/member/loginPro", method = RequestMethod.POST)
	public String loginPro(MemberDTO memberDTO,HttpSession session) {
		
		MemberDTO memberDTO2 = memberService.userCheck(memberDTO);
		if(memberDTO2 != null) {
			session.setAttribute("id",memberDTO.getId());
		}else {
			return "member/msg";
		}
		return "redirect:/member/main";
	}
	@RequestMapping(value = "/member/main", method = RequestMethod.GET)
	public String main() {
		return "member/main";
	}
	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/member/login";
	}
	@RequestMapping(value = "/member/info", method = RequestMethod.GET)
	public String info(HttpSession session,Model model) {
		String id = (String)session.getAttribute("id");
		
		
		MemberDTO memberDTO = memberService.getMember(id);
		model.addAttribute("memberDTO", memberDTO);
		
		return "member/info";
	}
	@RequestMapping(value = "/member/update", method = RequestMethod.GET)
	public String update(HttpSession session,Model model) {
		String id = (String)session.getAttribute("id");
		
		MemberDTO memberDTO = memberService.getMember(id);
		model.addAttribute("memberDTO", memberDTO);
		return "member/updateForm";
	}
	@RequestMapping(value = "/member/updatePro", method = RequestMethod.POST)
	public String updatePro(MemberDTO memberDTO) {
		
		memberService.updateMember(memberDTO);
		return "redirect:/member/login";
	}

//	 카카오 로그인 API //
		
//	@REQUESTMAPPING(VALUE="/MEMBER/KAKAOLOGIN", METHOD=REQUESTMETHOD.GET)
//	PUBLIC STRING KAKAOLOGIN(@REQUESTPARAM(VALUE = "CODE", REQUIRED = FALSE) STRING CODE) THROWS EXCEPTION {
//	SYSTEM.OUT.PRINTLN("#########" + CODE);
//
//	 STRING ACCESS_TOKEN = MS.GETACCESSTOKEN(CODE);
//	 SYSTEM.OUT.PRINTLN("###ACCESS_TOKEN#### : " + ACCESS_TOKEN);
//	
//	 HASHMAP<STRING, OBJECT> USERINFO = MS.GETUSERINFO(ACCESS_TOKEN);
//		SYSTEM.OUT.PRINTLN("###ACCESS_TOKEN#### : " + ACCESS_TOKEN);
//		SYSTEM.OUT.PRINTLN("###NICKNAME#### : " + USERINFO.GET("NICKNAME"));
//		SYSTEM.OUT.PRINTLN("###EMAIL#### : " + USERINFO.GET("EMAIL"));
//     
//	  RETURN "MEMBER/KAKAOLOGIN";
//	}
}
