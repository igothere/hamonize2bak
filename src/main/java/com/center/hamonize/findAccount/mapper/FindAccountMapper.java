package com.center.hamonize.findAccount.mapper;

import com.center.hamonize.findAccount.Account;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FindAccountMapper {
	
	public Account isExistAccount(Account vo)throws Exception;
	
	public int resetUserPw(Account vo) throws Exception;

}
