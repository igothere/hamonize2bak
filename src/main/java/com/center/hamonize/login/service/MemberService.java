package com.center.hamonize.login.service;

import com.center.hamonize.login.mapper.LoginMapper;
import com.center.hamonize.login.vo.LoginVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	LoginMapper lMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	try {
    			LoginVO vo = lMapper.getUser(username);
			return new SecurityMember(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return null;
    	
    }
}
