package com.center.hamonize.curl.server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.IBackupCycleMapper;
import com.mapper.IGetAgentJobMapper;
import com.mapper.IHmProgrmUpdtMapper;
import com.mapper.IHmSecurityMapper;
import com.mapper.IHmprogramMapper;
import com.mapper.IOrgMapper;
import com.mapper.IPcMangrMapper;
import com.mapper.IPcMangrMapper_server;
import com.mapper.ISvrlstMapper;
import com.mapper.server.IPackageInfoMapper_Server;
import com.mapper.server.ISvrlstMapper_Server;
import com.model.ActAgentProgrmVo;
import com.model.GetAgentJobVo;
import com.model.PcMangrVo;
import com.model.PcPackageVo;
import com.model.SvrlstVo;
import com.util.AdLdapUtils;
import com.util.FileUtil;

@RestController
@RequestMapping("/hmsvr")
public class ServerCurlController {

	
	@Autowired
	private IPcMngrMapper_server pcMangrMapper;

	@Autowired
	private IOrgMapper orgMapper;
	
	@Autowired
	private IGetAgentJobMapper agentJobMapper;

	@Autowired
	private IHmprogramMapper hmprogramMapper;
	
	@Autowired
	private IHmProgrmUpdtMapper hmProgrmUpdtMapper;

	@Autowired
	private IHmSecurityMapper hmSecurityMapper;
	
	@Autowired
	private IBackupCycleMapper backupCycleMapper;
	

	@Autowired
	private ISvrlstMapper_Server svrlstMapper;

	
	@Autowired
	private IPackageInfoMapper_Server packageInfoMapper_Server;
	
	@RequestMapping("/test2")
	public String  ssssrr(@RequestParam(value = "name", required = false) String name) {
		String str = "PROGRM:hamonia, office";
		return str;
	}
	
	@RequestMapping("/test")
	public String  greeqwewqting(@RequestBody HashMap<String, String> map)  {
		System.out.println(map.get("retdata"));
		String str = "PROGRM:hamonia, office";
		return str;
	}
	
	
	@RequestMapping("/setpcinfo")
	public Boolean setpcinfo(@RequestBody String retData, HttpServletRequest request) {
		System.out.println("setpcinfo===============ivs============");
		StringBuffer json = new StringBuffer();
		int retVal = 0;
		
	    try {

	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse( retData.toString());
	        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

	        PcMangrVo hdVo = new PcMangrVo();
	        System.out.println("hmdArray.size()==="+hmdArray.size());
	        
	        
	        
	        for(int i=0 ; i<hmdArray.size() ; i++){
	            JSONObject tempObj = (JSONObject) hmdArray.get(i);
	        	
//	            hdVo.setFirst_date(tempObj.get("datetime").toString());
//	            hdVo.setSgb_pc_cpu_id(tempObj.get("cpuid").toString());
	            
	            hdVo.setSgb_pc_uuid(tempObj.get("uuid").toString());
	            hdVo.setSgb_pc_cpu(tempObj.get("cpuinfo").toString());
	            hdVo.setSgb_pc_disk_id(tempObj.get("hddid").toString());
	            hdVo.setSgb_pc_disk(tempObj.get("hddinfo").toString());
	            hdVo.setSgb_pc_macaddress(tempObj.get("macaddr").toString());
	            hdVo.setSgb_pc_ip(tempObj.get("ipaddr").toString());
	            hdVo.setSgb_pc_vpnip(tempObj.get("vpnipaddr").toString());
	            hdVo.setSgb_pc_hostname(tempObj.get("hostname").toString());
	            hdVo.setSgb_pc_os(tempObj.get("sgbpcos").toString());
	            hdVo.setSgb_pc_memory(tempObj.get("memory").toString() +"G");
	            hdVo.setSgbname(tempObj.get("sgbname").toString());	// 대대번
	            hdVo.setSgbpcname(tempObj.get("sgbpcname").toString());	// 서버일련번호
	            hdVo.setSgb_public_ip(tempObj.get("publicip").toString());	// 공인IP
	            	
	            
	       }
	        
	      int DuplserverPc = pcMangrMapper.inserPcInfoInServerChk(hdVo);
	      System.out.println("DuplserverPc===="+DuplserverPc +"===>"+ hdVo.getSgb_pc_vpnip() );
	       
	      PcMangrVo orgNumChkVo =  pcMangrMapper.chkPcOrgNum(hdVo);
	      System.out.println("orgNumChkVo===="+orgNumChkVo);
	      if( orgNumChkVo != null  ) {
	    	  hdVo.setOrg_seq(orgNumChkVo.getSeq()); 
	       }else {
	    	   hdVo.setOrg_seq(8307); 
	       }
	      
	       
	      if(DuplserverPc >= 1 ) {
	    	  	retVal = pcMangrMapper.updatePcInfoInServer(hdVo);
	        	System.out.println("update retVal=== " + retVal);
	      }else {
	        	retVal = pcMangrMapper.inserPcInfoInServer(hdVo);
		      	System.out.println("insert retVal=== " + retVal);
	      }
	        
	        
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
		
	    Boolean isAddPcInfo = true;
	    if( retVal == 1 ) {
	    	isAddPcInfo = true;
	    }else {
	    	isAddPcInfo = false;
	    }
        System.out.println("isAddPcInfo==="+isAddPcInfo);
		return isAddPcInfo;
	}
	
	

	@RequestMapping("/setVpnUpdate")
	public Boolean setVpnUpdate(@RequestBody String retData, HttpServletRequest request) {
		
		StringBuffer json = new StringBuffer();
		int retVal = 0;
		
	    try {

	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse( retData.toString());
	        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

	        PcMangrVo hdVo = new PcMangrVo();
	        System.out.println("hmdArray.size()==="+hmdArray.size());
	        for(int i=0 ; i<hmdArray.size() ; i++){
	            JSONObject tempObj = (JSONObject) hmdArray.get(i);
	        	
	            
	            System.out.println("tempObj.get(\"uuid\").toString()==="+tempObj.get("uuid").toString());
	            System.out.println("tempObj.get(\"vpnipaddr\").toString()==="+tempObj.get("vpnipaddr").toString());
	            
//	            hdVo.setFirst_date(tempObj.get("datetime").toString());
//	            hdVo.setSgb_pc_cpu_id(tempObj.get("cpuid").toString());
	            
	            hdVo.setSgb_pc_uuid(tempObj.get("uuid").toString());
	            hdVo.setSgb_pc_vpnip(tempObj.get("vpnipaddr").toString());
	            hdVo.setSgb_pc_hostname(tempObj.get("hostname").toString());
	            
	            System.out.println("hdVo==="+ hdVo.toString());
	            
	            
	        }
	        
	    	   retVal = pcMangrMapper.updateVpnInfo(hdVo);
	        	System.out.println("update retVal=== " + retVal);
	        	
	        
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
		
	    Boolean isAddPcInfo = true;
	    if( retVal == 1 ) {
	    	isAddPcInfo = true;
	    }else {
	    	isAddPcInfo = false;
	    }
        System.out.println("isAddPcInfo==="+isAddPcInfo);
		return isAddPcInfo;
	}
	

	/**
	 * 서버 기본 정보 셋팅 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/commInfoData")
//	public Boolean Test(@RequestBody String retData, HttpServletRequest request) {
	public String getAgentJob(HttpServletRequest request) throws Exception {

		String output = "";
		
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonList = new JSONObject();
		JSONArray itemList = new JSONArray();

		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
	    
	    
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
		JSONArray inetvalArray = (JSONArray) jsonObj.get("events");
		JSONObject object = (JSONObject) inetvalArray.get(0);
    	
		System.out.println("====> "+ object.get("uuid").toString());
		
		//int segSeq = sgbUUID(object.get("uuid").toString());

		List<SvrlstVo> svrlstVo = svrlstMapper.getSvrlstDataList();
		

		for( SvrlstVo svrlstData : svrlstVo ){
			System.out.println("svrlstData===>=="+ svrlstData.getSvr_port()+"=="+ svrlstData.getSvr_domain() +"=="+ svrlstData.getSvr_ip());
			
			JSONObject tmpObject = new JSONObject();
			
			tmpObject.put("sgbname", svrlstData.getSvr_nm());
			tmpObject.put("sgbdomain", svrlstData.getSvr_domain());
			  
			if( "N".equals(svrlstData.getSvr_port()) ) {
				tmpObject.put("sgbip", svrlstData.getSvr_ip());	
			}else {
				tmpObject.put("sgbip", svrlstData.getSvr_ip() +":"+ svrlstData.getSvr_port());
			}
			
			
			itemList.add(tmpObject);
		}
		jsonObject.put("sgbdata", itemList);

		output = jsonObject.toJSONString();
		
		System.out.println("//===================================");
		System.out.println("//commInfoData result data is : " + output);
		System.out.println("//===================================");
		
		return output;
	}

	
	

	@RequestMapping("/getPackageInfo")
	public Boolean getPackageInfo(@RequestBody String retData, @RequestParam Map<String, Object> params, HttpServletRequest request) {
		
		
		StringBuffer json = new StringBuffer();
		int retVal = 0;
		
	    try {

	        JSONParser jsonParser = new JSONParser();
	        System.out.println("retData.toString()========++++"+retData.toString());
	        JSONObject jsonObj = (JSONObject) jsonParser.parse( retData.toString());
	        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

	        PcPackageVo pcPackageVo = new PcPackageVo();
	        System.out.println("hmdArray.size()==="+hmdArray.size());
	        
	        PcPackageVo[] inputVo = new PcPackageVo[hmdArray.size()];
	        for(int i=0 ; i<hmdArray.size() ; i++){
	        	JSONObject tempObj = (JSONObject) hmdArray.get(i);
	        	inputVo[i] = new PcPackageVo();
	        	
	        	
	        	System.out.println("tempObj.get(\"pcuuid\").toString()==="+tempObj.get("pcuuid").toString());
	        	System.out.println("tempObj.get(\"name\").toString()==="+tempObj.get("name").toString());
	        	System.out.println("tempObj.get(\"version\").toString()==="+tempObj.get("version").toString());
	        	System.out.println("tempObj.get(\"status\").toString()==="+tempObj.get("status").toString());
	        	System.out.println("tempObj.get(\"short_description\").toString()==="+tempObj.get("short_description").toString());
	        	
	        	
	        	inputVo[i].setUuid(tempObj.get("pcuuid").toString());
	        	inputVo[i].setPackage_name(tempObj.get("name").toString());
	        	inputVo[i].setPackage_version(tempObj.get("version").toString());
	        	inputVo[i].setPackage_status(tempObj.get("status").toString());
	        	inputVo[i].setPackage_desc("");
//	        	inputVo[i].setPackage_desc(tempObj.get("short_description").toString());
	        	
	            
	        	System.out.println("pcPackageVo=========+++"+ pcPackageVo.toString());
	        	
	        	
	        }
	       
	        Map<String, Object> insertDataMap = new HashMap<String, Object>();
	        insertDataMap.put("list", inputVo);
            
    		
	        int insertRetVal = packageInfoMapper_Server.insertPackageInfo(insertDataMap);
	        System.out.println("insertRetVal===="+insertRetVal);
    		
	        
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
		
	    Boolean isAddPcInfo = true;
		return isAddPcInfo;
	}
	
	
	@RequestMapping("/sgbPcInfoChange")
	public String  sgbPcLoginout(HttpServletRequest request) throws Exception {
		System.out.println("sgbPcInfoChange=====================");
		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	        	System.out.println("line===> "+ line);
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
	    
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        System.out.println("=sgbPcInfoChange====client pc device info =====");
        
        PcMangrVo hdVo = new PcMangrVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
            hdVo.setFirst_date(tempObj.get("datetime").toString());
            hdVo.setSgb_pc_macaddress(tempObj.get("macaddr").toString());
            hdVo.setSgb_pc_ip(tempObj.get("ipaddr").toString());
            hdVo.setSgb_pc_vpnip(tempObj.get("vpnipaddr").toString());
            hdVo.setSgb_pc_hostname(tempObj.get("hostname").toString());
            hdVo.setSgb_pc_cpu_id(tempObj.get("user").toString());
            hdVo.setSgb_pc_uuid(tempObj.get("pcuuid").toString());
            hdVo.setStatus(tempObj.get("action").toString());
        }

        
        PcMangrVo chkPcMangrVo = pcMangrMapper.chkPcinfo(hdVo);
        System.out.println("sgbPcInfoChange chkPcMangrVo===="+chkPcMangrVo.toString());
        
        int retVal = 0;
        if( !chkPcMangrVo.getSgb_pc_vpnip().equals(hdVo.getSgb_pc_vpnip())) {
        	
        	hdVo.setOld_pc_ip(chkPcMangrVo.getSgb_pc_ip());
        	hdVo.setOld_pc_vpnip(chkPcMangrVo.getSgb_pc_vpnip());
        	hdVo.setOld_pc_macaddr(chkPcMangrVo.getSgb_pc_macaddress());
        
        	retVal = pcMangrMapper.updatePcinfo(hdVo);
        	System.out.println("sgbPcInfoChange retVal====="+retVal);
        	pcMangrMapper.pcIpchnLog(hdVo);
        	
        	
//        	AdLdapUtils aldp = new AdLdapUtils();
//        	String retOU = aldp.adComputerSearchUseCn(hdVo.getSgb_pc_hostname());
//        	Boolean isBool = aldp.computerModify( retOU, hdVo.getSgb_pc_hostname() , hdVo.getSgb_pc_macaddress());
        	
        }
        System.out.println("sgbpcloginout info === "+ hdVo.toString());
        
        return "retval:"+retVal;
	}
	
	
	public int sgbUUID(String sgbUuid) {
		GetAgentJobVo agentVo = new GetAgentJobVo();
		agentVo.setSgb_pc_uuid(sgbUuid);
		agentVo = agentJobMapper.getAgentJobPcUUID(agentVo);
		int segSeq = agentVo.getSeq();
		return segSeq;
	}

	
	
}


