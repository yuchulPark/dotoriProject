package com.example.demo.db;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.Member_InfoVo;

public class Member_InfoManager {
	private static SqlSessionFactory factory;
	static {
		try {
			Reader reader = Resources.getResourceAsReader("com/example/demo/db/sqlConfig.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
	}
	public static List<Member_InfoVo> login(){ //로그인 
		SqlSession session = factory.openSession();
		List<Member_InfoVo> list = session.selectList("mem_info.select");
		session.close();
		return list;
	}
	public static int insertJoin(Member_InfoVo vo) { //회원가입
		int re = -1;
		SqlSession session = factory.openSession();
		re = session.insert("mem_info.insert", vo);
		session.commit();
		session.close();
		return re;
	}
	public static int updateMem(Member_InfoVo vo) { //회원정보 수정
		int re = -1;
		SqlSession session = factory.openSession();
		re = session.update("mem_info.update", vo);
		session.commit();
		session.close();
		return re;
	}
	public static int deleteMem(int mem_no) { //회원 탈퇴
		int re = -1;
		SqlSession session = factory.openSession();
		re = session.delete("mem_info.delete", mem_no);
		session.close();
		return re;
	}
}