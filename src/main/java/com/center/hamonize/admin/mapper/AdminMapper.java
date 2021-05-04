package com.center.hamonize.admin.mapper;

import java.util.List;

import com.center.hamonize.admin.Admin;

public interface AdminMapper {
	//센터 관리자
	public List<Admin> adminList(Admin vo);
	
	public int countListInfo(Admin vo);
	
	public Admin adminView(Admin vo);
	
	public int adminSave(Admin vo);
	
	public int adminModify(Admin vo);
	
	public int adminDelete(Admin vo);
	
	public int adminIdCheck(Admin vo);

}
