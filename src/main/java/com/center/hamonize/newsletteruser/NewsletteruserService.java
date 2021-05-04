package com.center.hamonize.newsletteruser;

import com.center.hamonize.newsletteruser.mapper.NewsletteruserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsletteruserService {
	@Autowired
	NewsletteruserMapper mapper;
	
	public int saveSubscriber(MailingUserList vo) throws Exception{		
		return mapper.saveSubscriber(vo);
	}
	
	public int checkDuplication(MailingUserList vo) throws Exception{		
		return mapper.checkDuplication(vo);
	}

}
