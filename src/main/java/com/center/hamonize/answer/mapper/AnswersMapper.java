package com.center.hamonize.answer.mapper;


import java.util.List;

import com.center.hamonize.answer.Answers;
import com.center.hamonize.cmmn.CmmnMap;
import com.center.hamonize.question.Questions;


public interface AnswersMapper {

	public List<Answers> getList(int questionno) throws Exception;

	public int getListCount(int questionno) throws Exception;

	public Answers getView(int answerno) throws Exception;

	public int updateReanCnt(int questionno) throws Exception;

	public int insertHistory(CmmnMap param) throws Exception;

	public List<Questions> getMyList(Questions vo) throws Exception;

	public List<Questions> getCompQuestionList(String useruuid) throws Exception;

	public int getCompQuestionListCount(String useruuid) throws Exception;

	public Questions getAnswerComplete() throws Exception;

	public List<Answers> getAnswerList(Answers vo);

	public int getAnswerListCnt(Answers vo);

}