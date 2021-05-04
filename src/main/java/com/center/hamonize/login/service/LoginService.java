package com.center.hamonize.login.service;

import com.center.hamonize.login.mapper.LoginMapper;
import com.center.hamonize.login.vo.LoginHistoryVO;
import com.center.hamonize.login.vo.LoginVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
	LoginMapper mapper;
	
	public LoginVO getUserInfo(String username) throws Exception{
		return mapper.getUserInfo(username);
	}
	
	public LoginVO getSocialUserInfo(String username) throws Exception{
		return mapper.getSocialUserInfo(username);
	}
	
	public int saveLoginHistory(LoginHistoryVO vo) throws Exception{
		return mapper.saveLoginHistory(vo);
	}
	
}
