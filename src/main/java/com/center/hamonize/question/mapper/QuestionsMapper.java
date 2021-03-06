package com.center.hamonize.question.mapper;


import java.util.List;

import com.center.hamonize.cmmn.CmmnMap;
import com.center.hamonize.question.Questions;


public interface QuestionsMapper {

	public List<Questions> getList(Questions vo) throws Exception;

	public int getListCount(Questions vo) throws Exception;

	public Questions getView(int questionno) throws Exception;

	public int updateReanCnt(int questionno) throws Exception;

	public int insertHistory(CmmnMap param) throws Exception;

	public List<Questions> getMyAnswerList(Questions vo) throws Exception;
	
	public List<Questions> getMyQuestionList(Questions vo) throws Exception;

	public List<Questions> getMyAnswerListEnter(Questions vo) throws Exception;
	
	public List<Questions> getMyQuestionListEnter(Questions vo) throws Exception;
	
	public List<Questions> getCompQuestionList(String useruuid) throws Exception;

	public int getCompQuestionListCount(String useruuid) throws Exception;

	public Questions getAnswerComplete() throws Exception;
}