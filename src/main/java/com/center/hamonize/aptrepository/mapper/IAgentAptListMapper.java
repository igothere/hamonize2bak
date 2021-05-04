package com.center.hamonize.aptrepository.mapper;

import java.util.List;

import com.center.hamonize.admin.Admin;

public interface IAgentAptListMapper {
	
	public List<Admin> adminList(Admin vo);
	
	public int saveApt(List<String> list);

}
