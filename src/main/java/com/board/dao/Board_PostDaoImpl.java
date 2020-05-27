package com.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.board.vo.Board_PostListVo;
import com.board.vo.Board_PostSearchVo;
import com.board.vo.Board_PostVo;
import com.information.vo.SearchConditionVo;
@Repository("board_postDao")
public class Board_PostDaoImpl implements Board_PostDao {
	@Inject
	protected SqlSessionTemplate sqlSessionTemplate;
	@Override
	 public List<Board_PostListVo> listBoard_Post(String str) {
	      // TODO Auto-generated method stub
	      System.out.println("Board_PostDaoImpl str : " + str);
	      
	      System.out.println("Board_PostDaoImpl 체크포인트1 str: " + str);
	      
	      String keyWord = str;
	      String str1 = "";     //검색 옵션
	      String str2 = "";     //키워드
	      String str3 = "0";	//페이징 시작값
	      String str4 = "10";	//페이징 끝값
	      
	      Map map = new HashMap();
	      
	      if(str != null) {
	         String getStr[] = keyWord.split("@");
	         for(int i=0; i<getStr.length; i++) {
	            
	            if(i == 0) {
	               str1 = getStr[0];   
	            }
	            
	            if(i == 1) {
	               str2 = getStr[1];
	               System.out.println("Board_PostDaoImpl 체크포인트3 str1 : "+ str1 + " str2 : " + str2);
	            }
	            
	            if(i == 2) {
	               
	               if(getStr[2].equals("1")) {
	                  
	                  System.out.println("Board_PostDaoImpl 체크포인트3 페이징처리 getStr[2] : "+ getStr[2]);
	                  
	                  str3 = Integer.toString(0);
	                  str4 = Integer.toString(10);
	                  
	               }else {
	                  
	                  System.out.println("Board_PostDaoImpl 체크포인트3 페이징처리 getStr[2] : "+ getStr[2]);
	                  
	                  str3 = getStr[2];
	                  int startPage = Integer.parseInt(str3) * 10;
	                  int EndPage = startPage + 10;
	                  
	                  str3 = Integer.toString(startPage);
	                  str4 = Integer.toString(EndPage);
	               }
	                              
	            }
	         }
	         
	         
	         
	         

	                  
	         System.out.println("Board_PostDaoImpl 체크포인트3 페이징처리 str3 : "+ str3 + " str4 : " + str4);
	         
	         map.put("str1", str1);
	         map.put("str3", str3);
	         map.put("str4", str4);
	         
	         if(!"".equals(str2)) {
	            map.put("str2", str2);
	            System.out.println("Board_PostDaoImpl 체크포인트4 map.get() : "+ map.get("str1") + " : " + map.get("str2"));
	         }
	         

	         List<Board_PostVo> aaa = sqlSessionTemplate.selectList("board_post.select", map);      
	         System.out.println("Board_PostDaoImpl 쿼리결과#1 : " + aaa);
	         return sqlSessionTemplate.selectList("board_post.select", map);         
	         
	      }else {

	         map.put("str3", str3);
	         map.put("str4", str4);
	         
	         List<Board_PostVo> aaa = sqlSessionTemplate.selectList("board_post.select", map);      
	         System.out.println("Board_PostDaoImpl 쿼리결과#2 : " + aaa);
	         return sqlSessionTemplate.selectList("board_post.select", map);            
	         
	      }
	               

	      


	   }

//	public List<Board_PostVo> listBoard_Post() {
//		// TODO Auto-generated method stub
//		return sqlSessionTemplate.selectList("board_post.select");
//	}

	@Override
	public int insertBoard_Post(Board_PostVo vo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("board_post.insert", vo);
	}

	@Override
	public Board_PostVo detailBoard_Post(int board_no) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("board_post.detail", board_no);
	}

	@Override
	public int updateBoard_Post(Board_PostVo vo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("board_post.update", vo);
	}

	@Override
	public int deleteBoard_Post(Board_PostVo vo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("board_post.delete", vo);
	}

	@Override
	public void updateHit(int board_no) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update("board_post.updateHit", board_no);
	}
	
	@Override
	public List<Board_PostVo> mainBoard_Post1(Board_PostVo vo){
		return sqlSessionTemplate.selectList("board_post.selectMain1", vo);
	}
	
	@Override
	public List<Board_PostVo> mainBoard_Post2(Board_PostVo vo){
		return sqlSessionTemplate.selectList("board_post.selectMain2", vo);
	}

	@Override
	public List<Board_PostVo> mainBoard_Post3(Board_PostVo vo){
		return sqlSessionTemplate.selectList("board_post.selectMain3", vo);
	}

	@Override
	public List<Board_PostVo> mainBoard_Post4(Board_PostVo vo){
		return sqlSessionTemplate.selectList("board_post.selectMain4", vo);
	}
	// 통합검색화면에서 검색된 게시판정보
	@Override
	public List<Map> unifiedBoardSearch(Map map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("unified_search.unifiedBoardSearch", map);
	}
	// 통합검색화면에서 게시판으로 조회된 검색조건
	@Override
	public List<SearchConditionVo> unifiedBoardCondition(Map map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("unified_search.unifiedBoardCondition", map);
	}
	// 게시판, 검색조건(제목, 본문, 댓글, 말머리)에 해당하는 게시글 리스트 가져오기
	@Override
	public List<Board_PostSearchVo> searchBoard(Map map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("unified_search.searchBoard", map);
	}
}
