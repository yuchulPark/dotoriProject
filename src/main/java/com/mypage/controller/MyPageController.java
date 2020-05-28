package com.mypage.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.member.service.MyPage_MainService;
import com.member.service.MyPage_commentService;
import com.member.vo.MyPage_CommentVo;
import com.member.vo.MyPage_PostVo;
import com.security.config.LoginUser;

@Controller
public class MyPageController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="myPage_commentService")
	private MyPage_commentService myPage_commentService;
	@Resource(name="mypage_mainService")
	private MyPage_MainService mypage_mainService;

	//내가 쓴 글 & 댓글
	@RequestMapping(value = "/member/myPage_Contents.do", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		logger.info("list");
		int mem_no  = 0;
		if(LoginUser.isLogin()) {
			mem_no = LoginUser.getMember_no();
		}
		List<MyPage_PostVo> list_post = myPage_commentService.list_post(mem_no);
		mav.addObject("list_post", list_post);
		List<MyPage_CommentVo> list = myPage_commentService.list(mem_no);
		mav.addObject("list", list);
		return mav;
	}
	
	//내가 쓴 댓글 목록
	@RequestMapping(value = "/member/myPage_Comment.do", method = RequestMethod.GET)
	public String list(Model model) throws Exception{
		logger.info("list");
		int mem_no = 0;
		if(LoginUser.isLogin()) {
			mem_no = LoginUser.getMember_no();
		}
		model.addAttribute("list", myPage_commentService.list(mem_no));
		return "member/myPage_Contents";
	}
	
	//내가 쓴 글 목록
	@RequestMapping(value = "/member/myPage_Post.do", method = RequestMethod.GET)
	public String list_post(Model model) throws Exception{
		logger.info("list");
		int mem_no = 0;
		if(LoginUser.isLogin()) {
			mem_no = LoginUser.getMember_no();
		}
		model.addAttribute("list_post", myPage_commentService.list_post(mem_no));
		return "member/myPage_Contents";
	}


	// 내가 받은 쪽지 목록
	@RequestMapping("/member/myPage_Message.do")
	public ModelAndView myPage_Message() {
		ModelAndView m = new ModelAndView();
		return m;
	}
	
	// 마이페이지 메인
	@RequestMapping("/member/myPage.do")
	public ModelAndView myPage_Main() {
		System.out.println("마이페이지 메인 컨트롤러");
		int mem_no = 0;
		ModelAndView m = new ModelAndView();
		logger.info("list");
		if(LoginUser.isLogin()) {
			mem_no = LoginUser.getMember_no();
		}
		m.addObject("main", mypage_mainService.myPage_Main(mem_no));
		System.out.println("마이페이지 메인 컨트롤러");
		return m;
	}
	
}
