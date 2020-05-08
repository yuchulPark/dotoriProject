package com.example.demo.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.db.Member_InfoManager;
import com.example.demo.vo.Member_InfoVo;

@Repository
public class Member_InfoDao {
	
	private static Member_InfoDao instance;
	//싱글톤 패턴
		public Member_InfoDao() {}
		public static Member_InfoDao getInstance() {
			if(instance == null)
				instance = new Member_InfoDao();
			return instance;
		}
	
	
	public Member_InfoVo login(Member_InfoVo vo) {
		return Member_InfoManager.login(vo);
	}
	public int insert(Member_InfoVo vo) { //회원가입
		return Member_InfoManager.insertJoin(vo);
	}
	public int update(Member_InfoVo vo) {
		return Member_InfoManager.updateMem(vo);
	}
	public int delete(int mem_no) {
		return Member_InfoManager.deleteMem(mem_no);
	}
	//유저 회원가입 매소드
	public int joinMem(Member_InfoVo vo) {
		return Member_InfoManager.insertJoin(vo);
	}
	//회원임시 비밀번호 변경 메소드
	public int changeTempPwd(Member_InfoVo vo) {
		return Member_InfoManager.chageTempPwd(vo);
	}
	//아이디 중복체크
	public int checkOverId(String mem_id) {
		return Member_InfoManager.checkOverId(mem_id);
	}
//	//닉네임 중복체크
//	public int checkOverNickName(String mem_nickname) {
//		
//	}
//	//유저 로그인 메소드
//	public Member_InfoVo loginMem(@Param("mem_id")String mem_id) {
//		
//	}
//	public Member_InfoVo memInfo(String mem_id) {
//		
//	}
}
