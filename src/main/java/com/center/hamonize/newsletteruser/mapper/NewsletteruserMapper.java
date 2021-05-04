package com.center.hamonize.newsletteruser.mapper;

import com.center.hamonize.newsletteruser.MailingUserList;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface NewsletteruserMapper {
	public int saveSubscriber (MailingUserList vo) throws Exception;
	public int checkDuplication(MailingUserList vo) throws Exception;	
}
