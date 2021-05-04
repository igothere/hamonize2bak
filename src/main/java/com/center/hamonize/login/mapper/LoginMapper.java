package com.center.hamonize.login.mapper;


import com.center.hamonize.login.vo.LoginHistoryVO;
import com.center.hamonize.login.vo.LoginVO;

import org.apache.ibatis.annotations.Mapper;





@Mapper
public interface LoginMapper {

	public LoginVO getUser(String username) throws Exception;

	public LoginVO getUserInfo(String username) throws Exception;

	public LoginVO getUserUUIDInfo(LoginVO vo) throws Exception;

	public LoginVO getUserChk(LoginVO vo) throws Exception;

	public int updateUserUUID(LoginVO vo) throws Exception;

	public LoginVO getSocialUserInfo(String username) throws Exception;

	public int saveLoginHistory(LoginHistoryVO vo) throws Exception;
}
