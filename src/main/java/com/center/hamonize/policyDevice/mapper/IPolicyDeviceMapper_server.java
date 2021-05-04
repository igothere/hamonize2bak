package com.center.hamonize.policyDevice.mapper;

import java.util.List;
import java.util.Map;


public interface IPolicyDeviceMapper_server {
	
	public List<PolicyDeviceVo> deviceList(PolicyDeviceVo vo);
	
	public int deviceSave(Map<String, Object> params);
	
	public int deviceDelete(Map<String, Object> params);
	
	public PolicyDeviceVo deviceApplcView(PolicyDeviceVo vo);
	
	public List<PolicyDeviceVo> dManagePopList(Map<String, Object> params);
	
	public int devicePolicyCount(PolicyDeviceVo vo);
	
	public int devicePopSave(PolicyDeviceVo vo);
	
	public void devicePopDelete(PolicyDeviceVo vo);
	
	public int devicePopCount(PolicyDeviceVo vo);


}
