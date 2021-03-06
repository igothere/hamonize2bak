package com.center.hamonize.wiki.mapper;


import java.util.List;

import com.center.hamonize.wiki.Wiki;
import com.center.hamonize.wiki.WikiHistory;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface WikiMapper {

	public List<Wiki> getWikiList(Wiki vo) throws Exception;

	public int getWikiListCount(Wiki vo) throws Exception;

	public Wiki getView(int wikino) throws Exception;

	public WikiHistory getHistoryView(int seq) throws Exception;

	public List<WikiHistory> getHistory(int wikino) throws Exception;

	public int getWikiCount(int tagno) throws Exception;

	public int save(Wiki vo) throws Exception;

	public int saveHistory(Wiki vo) throws Exception;

	public int addWikiHistory(Wiki vo) throws Exception;

	public int updateWiki(Wiki vo) throws Exception;

	public int deleteWiki(int wikino) throws Exception;

	public List<Wiki> getWikiListFromCom(Wiki vo) throws Exception;

	public int getWikiListCntFromCom(Wiki vo) throws Exception;
	
	public int checkDuplication(Wiki vo) throws Exception;
}
