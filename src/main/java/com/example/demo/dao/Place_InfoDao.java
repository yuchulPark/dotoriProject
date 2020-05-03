package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.Place_InfoVo;

@Repository
public class Place_InfoDao {
	public List<Place_InfoVo> listPlace_Info(){
		return DBManager.listPlace_Info();
	}
	
	public List<Place_InfoVo> listPlace_InfoPage(HashMap map){
		return DBManager.listPlace_InfoPage(map);
	}
	
	public Place_InfoVo detailPlace_Info(int place_no) {
		return DBManager.detailPlace_Info(place_no);
	}
	
}
