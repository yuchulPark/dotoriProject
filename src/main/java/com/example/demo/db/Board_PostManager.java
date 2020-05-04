package com.example.demo.db;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.Board_PostVo;
import com.example.demo.vo.Member_InfoVo;

public class Board_PostManager {
	private static SqlSessionFactory factory;
	static {
		try {
			Reader reader = Resources.getResourceAsReader("com/example/demo/db/sqlMapConfig.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//커뮤니티 글 목록
	public static List<Board_PostVo> listBoard_Post(){
		SqlSession session = factory.openSession();
		List<Board_PostVo> list = session.selectList("board_post.selectAll");
		session.close();		
		return list;
	}
	
	//커뮤니티 글 등록
	public static int insertBoard_Post(Board_PostVo vo) {
		int re = -1;
		SqlSession session = factory.openSession();
		re = session.insert("board_post.insert", vo);
		session.commit();
		session.close();
		return re;
	}
	
	//커뮤니티 글 수정
	public static int updateBoard_Post(Board_PostVo vo) {
		int re = -1;
		SqlSession session = factory.openSession();
		re = session.update("board_post.update", vo);
		session.commit();
		session.close();
		return re;
	}
	
	//커뮤니티 글 삭제
	public static int deleteBoard_Post(Board_PostVo vo) {
		int re = -1;
		SqlSession session = factory.openSession();
		re = session.delete("board_post.delete", vo);
		session.commit();
		session.close();
		return re;
	}
}