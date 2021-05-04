package com.center.hamonize.vote.mapper;

import com.center.hamonize.vote.Vote;

public interface VoteMapper {
	
	public int likes(Vote vo) throws Exception;
	
	public int dislikes(Vote vo) throws Exception;
	
	public int save(Vote vo) throws Exception;
	
	public int dislike() throws Exception;
	
	public Vote getSeq(Vote vo) throws Exception;
	
	public int saveVoteUser(Vote vo) throws Exception;
	
	public int voteCheck(Vote vo) throws Exception;

}
