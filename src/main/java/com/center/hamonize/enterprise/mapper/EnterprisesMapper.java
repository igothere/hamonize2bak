package com.center.hamonize.enterprise.mapper;


import java.util.List;

import com.center.hamonize.enterprise.EnterprisePwVO;
import com.center.hamonize.enterprise.Enterprises;
import com.center.hamonize.question.Questions;
import com.center.hamonize.user.Users;
import com.center.hamonize.user.UsersDetail;
import com.center.hamonize.wiki.Wiki;


public interface EnterprisesMapper {

	public int updateEnterprises(Enterprises vo);

	public int updateEnterprisePw(EnterprisePwVO vo);

	public void updateEnterprisesimg(Enterprises vo);

	public List<Users> getMembersList(UsersDetail vo);

	public List<Users> getActiveatMembersList(UsersDetail vo);

	public boolean updateActivaet(UsersDetail vo);

	public boolean updateUserat(UsersDetail vo);

	public int cntQuestionsById(int seq);

	public int cntAnswerById(int seq);

	public int cntTagAndWikiById(Wiki vo);

	public List<Wiki> findTagAndWikiByUserno(Wiki vo);

	public Integer getScore(List<Users> memList);

	public List<Enterprises> getPromteList();

	public String getScoregraph(List<Users> memList);

	public List<Questions> getQuestionList(Questions vo);

	public int getQuestionListCnt(Questions vo);

}