package com.center.hamonize.policyProgram.mapper;

import java.util.List;
import java.util.Map;

import com.model.PolicyProgramVo;

public interface IPolicyProgramMapper_server {
	
	public List<PolicyProgramVo> programList(PolicyProgramVo vo);
	
	public int programSave(Map<String, Object> params);
	
	public int programDelete(Map<String, Object> params);
	
	public PolicyProgramVo programApplcView(PolicyProgramVo vo);

}
