package com.member.dao;

import java.util.List;


import com.member.vo.Member_InfoVo;
import com.member.vo.MyPage_CommentVo;
import com.member.vo.MyPage_PostVo;

public interface MyPage_commentDao {

	//내가 쓴 댓글 목록
	public List<MyPage_CommentVo> list(int mem_no) throws Exception;
	
	//내가 쓴 글 목록
	public List<MyPage_PostVo> list_post(int mem_no) throws Exception;
}