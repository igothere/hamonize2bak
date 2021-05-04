package com.center.hamonize.signup.mapper;

import com.center.hamonize.enterprise.Enterprises;
import com.center.hamonize.user.UsersDetail;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignupMapper {

	public int saveEnterprisebizno(Enterprises vo) throws Exception;
	public Integer bizNoCheck(Enterprises vo) throws Exception;
	public int getEnterpriseno(Enterprises vo) throws Exception;
	public int saveDetail(UsersDetail vo) throws Exception;

	
}
