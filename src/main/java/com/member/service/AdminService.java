package com.member.service;

import java.util.List;
import java.util.Map;

import com.member.vo.Member_InfoVo;

public interface AdminService {
	// 관리자 - 모든 회원 리스트 출력 + 페이징 처리 + 검색 + 정렬
	List<Member_InfoVo> listMemberAll(Map map);
	
	// 관리자 - 회원 강퇴
	int deleteMember(int mem_no);
	
	// 총 페이지 수 구하기
	int totalRecord(Map map);
}
