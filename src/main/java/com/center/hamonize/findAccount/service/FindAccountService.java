package com.center.hamonize.findAccount.service;

import java.util.Random;

import com.center.hamonize.findAccount.Account;
import com.center.hamonize.findAccount.mapper.FindAccountMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FindAccountService {

	@Autowired
	FindAccountMapper mapper;
	
	public Account isExistAccount(Account vo)throws Exception{
		return mapper.isExistAccount(vo);		
	};
	
	public int resetUserPw(Account vo) throws Exception{
		StringBuffer tmpRnd = new StringBuffer();
		Random rnd = new Random();
		//입력한 이메일의 계정이 활성화 된경우만 메일 발송
		
		if(vo!=null) {
		
			if(vo.getActiveat() != 1) {
			    System.out.println("계정 조회 성공!");
				for (int i = 0; i < 6; i++) {
				    int rIndex = rnd.nextInt(3);
				    switch (rIndex) {
				    case 0:
				        // a-z
				    	tmpRnd.append((char) ((int) (rnd.nextInt(26)) + 97));
				        break;
				    case 1:
				        // A-Z
				    	tmpRnd.append((char) ((int) (rnd.nextInt(26)) + 65));
				        break;
				    case 2:
				        // 0-9
				    	tmpRnd.append((rnd.nextInt(10)));
				        break;
				    }
				}
				// 버퍼에있는 값 String으로 변경
				String rndPw = tmpRnd.toString();		
				vo.setUserpassword(rndPw);
	
				System.out.println("비밀번호 초기화 성공 ===> "+vo.getUserpassword());
	
				PostMail sendMail = new PostMail();
				sendMail.inviteMail(vo);							
	
				System.out.println("등록된 메일"+ vo.getUseremail()+"로 초기화된 비밀번호-->+"+vo.getUserpassword()+" 발송 성공!");
				
				//비밀번호 재인코딩
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		       vo.setUserpassword(passwordEncoder.encode(vo.getUserpassword()));
		    
				System.out.println("비밀번호 초기화 후 재인코딩 성공 ===> "+vo.getUserpassword());
			
			}else {
			    System.out.println("비활성화된 계정입니다. 비밀번호 조회/변경 실패!");
			}
		} 
		
		return mapper.resetUserPw(vo);
		
	}
}
