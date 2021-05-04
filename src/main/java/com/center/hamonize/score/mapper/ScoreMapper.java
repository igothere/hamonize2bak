package com.center.hamonize.score.mapper;

import java.util.List;

import com.center.hamonize.cmmn.CmmnMap;
import com.center.hamonize.question.Questions;

public interface ScoreMapper {
	
	public List<Questions> getList(Questions vo) throws Exception;
	
	public int saveScore(CmmnMap param) throws Exception;
	
	public Questions getView(int questionno) throws Exception;
	
	public int updateReanCnt(int questionno) throws Exception;
	
	public int insertHistory(CmmnMap param) throws Exception;

	public List<Questions> getMyList(Questions vo) throws Exception;
}