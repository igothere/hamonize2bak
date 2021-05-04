package com.center.hamonize.admin;

import java.util.List;

import com.center.hamonize.admin.mapper.AdminMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	//센터 관리자
	public List<Admin> adminList(Admin vo){
		return adminMapper.adminList(vo);	
	}
	
	public int countListInfo(Admin vo) {
		return adminMapper.countListInfo(vo);
	}
	
	public Admin adminView(Admin vo) {
		return adminMapper.adminView(vo);	
	}
	
	public int adminSave(Admin vo) {
		return adminMapper.adminSave(vo);
	}
	
	public int adminModify(Admin vo) {
		return adminMapper.adminModify(vo);
	}
	
	public int adminDelete(Admin vo) {
		return adminMapper.adminDelete(vo);
	}
	
	public int adminIdCheck(Admin vo) {
		return adminMapper.adminIdCheck(vo);
	}
	
}
