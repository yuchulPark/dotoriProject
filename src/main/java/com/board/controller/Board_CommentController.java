package com.board.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.board.service.Board_CommentService;
import com.board.service.Board_PostService;
import com.board.vo.Board_CommentVo;
import com.google.gson.Gson;

@Controller
public class Board_CommentController {
	@Resource(name="board_commentService")
	private Board_CommentService board_commentService;
	@Resource(name="board_postService")
	private Board_PostService board_postService;
	//@Resource(name="member_infoService")
	//private Member_InfoService member_infoService;
	
	// 마이페이지에서 회원id가 #{mem_id}인 사람이 쓴 댓글들 보기
	@RequestMapping("/myPage/myPageListBoard_Comment.do")
	public ModelAndView myPageListBoard_Comment() {
		ModelAndView m = new ModelAndView();
		m.addObject("c_list", board_commentService.listBoard_Comment());
		
		// 회원 테이블도 고려해야할듯
		// m.addObject("m_list", member_infoService.메소드명();
		
		return m;
	}
	
	//글 상세보기 화면에서 글 번호에 해당하는 댓글 목록 가져오기
	@RequestMapping("/board/listBoardComment.do/{board_no}")
	@ResponseBody
	public String listBoardComment(@PathVariable("board_no")int board_no) {
		String commentList = "";
		//댓글 목록을 Json 문자열로 변환
		commentList = (new Gson()).toJson(board_commentService.listComment(board_no));
		System.out.println(commentList);
		//Json문자열 반환
		return commentList;
	}
	
	// 댓글 번호가 x번인 사람이 게시글에 올린 댓글(들) (부정확)
	@RequestMapping("/board/detailListBoard_Comment.do")
	public ModelAndView listBoard_Comment(int comment_no) {
		ModelAndView m = new ModelAndView();
		m.addObject("c_list", board_commentService.detailBoard_Comment(comment_no));
		// m.addObject("b_list", board_postService.detailBoard_Post());
		
		// 회원 테이블도 고려해야할듯
		// m.addObject("m_list", member_infoService.메소드명();
		
		return m;
	}
	
	// x번 회원이 x번 게시물에 대한 댓글을 달음 (부정확)
	@RequestMapping(value="/board/insertBoard_Comment.do", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public int insertBoard_Comment(Board_CommentVo vo) {
		ModelAndView m = new ModelAndView();
		System.out.println(vo);
		//다음에 저장할 댓글번호 가져오기
		int nextCommentNo = board_commentService.selectNextCommentNo(vo.getBoard_no());
		//board_ref에 다음 댓글번호 일단 저장
		int board_ref = nextCommentNo;
		//board_ref가 0이 아니면 대댓글 작성으로 간주
		if(vo.getBoard_ref() != 0) {
			board_ref = vo.getBoard_ref();
		}
		//vo에 다음에 저장할 댓글번호 넣기
		vo.setComment_no(nextCommentNo);
		//vo에 참조되는 댓글번호 넣기
		vo.setBoard_ref(board_ref);
		
		int re = -1; 
		re = board_commentService.insertBoard_Comment(vo);
		return re;
	}
	
	// x번 회원이 x번 게시글에 쓴 댓글을 수정 (첨부파일 수정은 고려 안함)
	@RequestMapping("/board/updateBoard_Comment.do")
	@ResponseBody
	public int updateBoard_Comment(Board_CommentVo vo) {
		int re = board_commentService.updateBoard_Comment(vo);
		return re;
	}
	
	// x번 회원이 x번 게시글에 쓴 댓글을 삭제 (첨부파일 삭제는 고려 안함)
	@RequestMapping("/board/deleteBoard_Comment.do")
	@ResponseBody
	public int deleteBoard_Comment(Board_CommentVo vo) {
		int re = board_commentService.deleteBoard_Comment(vo);
		return re;
	}
}
