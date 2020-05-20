package com.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.member.service.JoinService;
import com.member.service.LoginService;
import com.member.util.AES256Util;
import com.member.util.MailSenderService;
import com.member.vo.Member_InfoVo;


@Controller
public class JoinController {
	@Autowired
	AES256Util aes256;
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="mailSenderService")
	MailSenderService mailSenderService;
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
	@Resource(name="joinService")
	private JoinService joinService;

	@RequestMapping("/member/joinForm.do")
	public void joinForm(HttpServletRequest request) {
		
	}
	
	//회원가입 컨트롤러
	@RequestMapping(value = "/member/join.do", method = RequestMethod.POST)
//		public ModelAndView joinMem(Member_InfoVo vo, Model model, HttpServletRequest request, HttpSession session) {
	public ModelAndView joinMem(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String mem_id = request.getParameter("mem_id");
		String mem_pwd = request.getParameter("mem_pwd");
		String mem_name = request.getParameter("mem_name");
		String mem_nickname = request.getParameter("mem_nickname");
		String mem_email = request.getParameter("mem_email");
//			int mem_emailagree = (int)request.getParameter("mem_emailagree");
		Member_InfoVo vo = new Member_InfoVo(0, mem_id, mem_pwd, mem_email, 0, mem_name, 0, "", mem_nickname
				, "", "", "ROLE_USER", 0);
		
		System.out.println(vo.getMem_id());
		System.out.println(vo.getMem_name());
		System.out.println(vo.getMem_nickname());
		System.out.println(vo.getMem_pwd());
		System.out.println(vo.getMem_email());
		// 회원가입 메소드
		int re = joinService.joinMember(vo); //저장된 객체 그대로 DB로 보낸다
		System.out.println("re : " + re);
		if (re > 0) {
			mav.setViewName("joinSuccess");
			mav.addObject("ok", vo.getMem_name());
			AES256Util aes256 = null;
			try {
				aes256 = new AES256Util();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mailSenderService.sendAuthMail(vo.getMem_id(), request.getServletContext(), aes256);
		}
		return mav;
	}

	// id 중복 체크 컨트롤러
	@RequestMapping(value = "/member/idCheck", method = RequestMethod.GET)
	@ResponseBody
	public int idCheck(HttpServletRequest request,@RequestParam("mem_id") String mem_id) {
		System.out.println("아이디 중복 컨트롤러 실행");
		return joinService.checkOverId(mem_id);
	}

//	 닉네임 중복 체크 컨트롤러
	@RequestMapping(value = "/member/nickCheck", method = RequestMethod.GET)
	@ResponseBody
	public int nickNameCheck(HttpServletRequest request,@RequestParam("mem_nickname") String mem_nickname) {
		System.out.println("닉네임중복 컨트롤러 실행");
		return joinService.checkOverNickName(mem_nickname);
	}
	
//	 이메일 중복 체크 컨트롤러
	@RequestMapping(value = "/member/emailCheck", method = RequestMethod.GET)
	@ResponseBody
	public int emailCheck(HttpServletRequest request,@RequestParam("mem_email") String mem_email) {
		System.out.println("이메일중복 컨트롤러 실행");
		return joinService.checkOverEmail(mem_email);
	}
	//이메일 인증 메일 클릭 후 인증 여부 변경
	@RequestMapping(value="/mailAuth.do", method=RequestMethod.GET)
	public String mailAuth(HttpServletRequest request, String mem_id, String key) {	//파라미터로 암호화된 아이디와 암호화된 uuid가 넘어옴
		ServletContext context = request.getServletContext();
		System.out.println("mem_id : " + mem_id);
		System.out.println("key : " + key);
		String reqMem_id = request.getParameter("mem_id");
		String reqKey = request.getParameter("key");
		System.out.println("reqMem_id : " + reqMem_id);
		System.out.println("reqKey : " + reqKey);
		String decMem_id = "";
		String decKey = "";
		Member_InfoVo vo = null; 
		String url = "redirect:/authFail.do";		//인증 실패 페이지
		try {
			decMem_id = aes256.aesDecode(mem_id);	//암호화된 아이디 복호화
			decKey = aes256.aesDecode(key);			//암호화된 uuid 복호화
			Map<String, String> map = (HashMap<String, String>)context.getAttribute(decMem_id);
			System.out.println("mailAuth map : " + map);
			String mapDecMem_id = aes256.aesDecode(map.get("encMem_id"));	//메일 보낼 당시 암호화된 mem_id
			String mapDecUUID = aes256.aesDecode(map.get("encUUID"));		//메일 보낼 당시 암호화된 uuid
			vo = loginService.loginById(mapDecMem_id);
			vo.setMem_auth(1);
			//
			if(mapDecMem_id.equals(decMem_id) && mapDecUUID.equals(decKey)) {	//파라미터와 map에 있는 mem_id, UUID를 복호화하여 일치하면 인증 완료
				System.out.println(decMem_id + " 인증 완료");
				context.removeAttribute(decMem_id);
				joinService.emailAuth(vo);
				url = "redirect:/main.do";
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return url;
	}
	
}