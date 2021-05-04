package com.center.hamonize.curl.server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.IActAgentDeviceMapper;
import com.mapper.IActAgentFirewallMapper;
import com.mapper.IActAgentNxssMapper;
import com.mapper.IActAgentProgrmMapper;
import com.mapper.IGetAgentJobMapper;
import com.mapper.IGetAgentRecoveryMapper;
import com.mapper.server.IActAgentDeviceMapper_Server;
import com.mapper.server.IActAgentFirewallMapper_Server;
import com.mapper.server.IActAgentNxssMapper_Server;
import com.mapper.server.IActAgentProgrmMapper_Server;
import com.mapper.server.IGetAgentJobMapper_Server;
import com.mapper.server.IGetAgentRecoveryMapper_Server;
import com.model.ActAgentBackupRecoveryVo;
import com.model.ActAgentDeviceVo;
import com.model.ActAgentFirewallVo;
import com.model.ActAgentNxssVo;
import com.model.ActAgentProgrmVo;
import com.model.GetAgentJobVo;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/actServer")
public class Server_ActAgentFirewallController {

	private final static Logger logger = Logger.getGlobal();
	
	
	@Autowired
	private IGetAgentJobMapper_Server agentJobMapper;


	@Autowired
	private IActAgentNxssMapper_Server actAgentNxssMapper;
	
	
	@Autowired
	private IActAgentFirewallMapper_Server actAgentFirewallMapper ;

	@Autowired
	private IActAgentDeviceMapper_Server actAgentDeviceMapper ;

	@Autowired
	private IActAgentProgrmMapper_Server actAgentProgrmMapper;

	
	@Autowired
	private IGetAgentRecoveryMapper_Server getAgentRecoveryMapper;
	
	
	@RequestMapping("/firewallAct")
	public String firewallAct(HttpServletRequest request ) throws Exception {
		logger.info("firewallAct===============================[start]");
		// 출력 변수
		String output = "";

		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        logger.info("Error reading JSON string: " + e.toString());
	        logger.info("Error reading JSON string: " + e.toString());
	    }
	    
	    logger.info("json===> "+ json);
	    
	    JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        ActAgentFirewallVo inputVo = new ActAgentFirewallVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
            inputVo.setDatetime(tempObj.get("datetime").toString());
            inputVo.setUuid(tempObj.get("uuid").toString().trim());
            inputVo.setHostname(tempObj.get("hostname").toString());
            inputVo.setStatus(tempObj.get("status").toString());
            inputVo.setStatus_yn(tempObj.get("status_yn").toString());
            inputVo.setRetport(tempObj.get("retport").toString());
            
        }
        
        int uuid = sgbUUID(inputVo.getUuid());
        inputVo.setOrgseq(uuid);
        
        int retVal = actAgentFirewallMapper.insertActAgentFirewall(inputVo);
        logger.info("retVal ==== "+ retVal);
        
		logger.info("//===================================");
		logger.info("//result data is : " + inputVo);
		logger.info("firewallAct===============================[END]");
		logger.info("//===================================");
		
		return output;
	}

	
	
	@RequestMapping("/deviceAct")
	public String deviceAct(HttpServletRequest request ) throws Exception {
		logger.info("deviceAct===============================[start]");
		// 출력 변수
		String output = "";

		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        logger.info("Error reading JSON string: " + e.toString());
	    }
	    
	    logger.info("json===> "+ json);
	    
	    JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        ActAgentDeviceVo inputVo = new ActAgentDeviceVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
            inputVo.setUuid(tempObj.get("uuid").toString().trim());
            inputVo.setHostname(tempObj.get("hostname").toString());
            inputVo.setStatus_yn(tempObj.get("statusyn").toString());
            inputVo.setProduct(tempObj.get("procut").toString());
            inputVo.setVendorCode(tempObj.get("vendorCode").toString());
            inputVo.setProductCode(tempObj.get("productCode").toString());
            
        }
        
        int uuid = sgbUUID(inputVo.getUuid());
        inputVo.setOrgseq(uuid);
        
        int retVal = actAgentDeviceMapper.insertActAgentDevice(inputVo);
        logger.info("retVal ==== "+ retVal);
        
		logger.info("//===================================");
		logger.info("//result data is : " + inputVo);
		logger.info("deviceAct===============================[END]");
		logger.info("//===================================");
		
		return output;
	}
	

	
	@RequestMapping("/progrmAct")
	public String progrmAct(HttpServletRequest request ) throws Exception {
		logger.info("progrmAct===============================[start]");
		// 출력 변수
		String output = "";
		int uuid = 0;
		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        logger.info("Error reading JSON string: " + e.toString());
	    }
	    
	    
	    JSONParser Parser = new JSONParser();
	    JSONObject jsonObj = (JSONObject) Parser.parse(json.toString());
	    
	    
	    /** ======================================
	     * insresert : 프로그램 정책 적용 (ins:적용, del:해제)
	     *=======================================*/
	    
	    JSONArray insArray = (JSONArray) jsonObj.get("insresert");
	    ActAgentProgrmVo[] inputVo = new ActAgentProgrmVo[insArray.size()];
	    if( insArray.size() != 0 ) {
		    for (int i = 0; i < insArray.size(); i++) {          
		    	JSONObject tempObj = (JSONObject) insArray.get(i);
		    	inputVo[i] = new ActAgentProgrmVo();
				inputVo[i].setUuid(tempObj.get("uuid").toString().trim());
				inputVo[i].setHostname(tempObj.get("hostname").toString());
				inputVo[i].setStatus(tempObj.get("status").toString());
				inputVo[i].setStatus_yn(tempObj.get("status_yn").toString());
				inputVo[i].setProgrmname(tempObj.get("progrmname").toString());
				inputVo[i].setDatetime(tempObj.get("datetime").toString());
				inputVo[i].setOrgseq(sgbUUID(tempObj.get("uuid").toString().trim()));
		    }  
	    }
	    
	    logger.info("inputVo]======================================");
		for (int i = 0; i < inputVo.length; i++) {
			logger.info("updtVo[i]=======" + inputVo[i].toString());
		}
	    
		Map<String, Object> insertDataMap = new HashMap<String, Object>();
		insertDataMap.put("list", inputVo);
		
		if (inputVo.length != 0) {
			actAgentProgrmMapper.insertActAgentProgrm(insertDataMap);
		}

		logger.info("progrmAct===============================[END]");
		
		return output;
	}
	
	

	@RequestMapping("/nxssAct")
	public String nxssAct(HttpServletRequest request ) throws Exception {
		logger.info("nxssAct===============================[start]");
		// 출력 변수
		String output = "";

		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        logger.info("Error reading JSON string: " + e.toString());
	    }
	    
	    logger.info("json===> "+ json);
	    
	    JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        ActAgentNxssVo inputVo = new ActAgentNxssVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
            inputVo.setUuid(tempObj.get("uuid").toString().trim());
            inputVo.setHostname(tempObj.get("hostname").toString());
            inputVo.setFile_gubun(tempObj.get("file_gubun").toString());
            inputVo.setFileDate(tempObj.get("fileDate").toString());
            
        }
        
        int uuid = sgbUUID(inputVo.getUuid());
        inputVo.setOrgseq(uuid);
        
        int retVal = actAgentNxssMapper.insertActAgentNxss(inputVo);
        logger.info("retVal ==== "+ retVal);
        
		logger.info("//===================================");
		logger.info("//result data is : " + inputVo);
		logger.info("nxssAct===============================[END]");
		logger.info("//===================================");
		
		return output;
	}
	
	
	
	@RequestMapping("/stBackupRecoveryAct")
	public String stBackupRecoveryAct(HttpServletRequest request ) throws Exception {
		logger.info("stBackupRecoveryAct===============================[start]");
		// 출력 변수
		String output = "";

		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        logger.info("Error reading JSON string: " + e.toString());
	    }
	    
	    logger.info("json===> "+ json);
	    
	    JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        ActAgentBackupRecoveryVo inputVo = new ActAgentBackupRecoveryVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
            inputVo.setDatetime(tempObj.get("datetime").toString());
            inputVo.setUuid(tempObj.get("uuid").toString().trim());
            inputVo.setHostname(tempObj.get("hostname").toString());
            inputVo.setAction_status(tempObj.get("action_status").toString());
            inputVo.setResult(tempObj.get("result").toString());
            
        }
        
        int uuid = sgbUUID(inputVo.getUuid());
        inputVo.setOrgseq(uuid);
        
        int retVal = getAgentRecoveryMapper.insertActAgentBackupRecovery(inputVo);
        logger.info("retVal ==== "+ retVal);
        
		logger.info("//===================================");
		logger.info("//result data is : " + inputVo);
		logger.info("stBackupRecoveryAct===============================[END]");
		logger.info("//===================================");
		
		return output;
	}
	
	
	@RequestMapping("/checkRecovery")
	public String chkeckRecovery(@RequestParam(value = "name", required = false) String sgbUuid,
			@RequestParam(value = "wget", required = false) String sgbWget) throws Exception {
		
		String output = "";
		ActAgentBackupRecoveryVo inputVo = new ActAgentBackupRecoveryVo();
		int segSeq = sgbUUID(sgbUuid.trim());
		
		inputVo.setUuid(sgbUuid.trim());
		inputVo.setOrgseq(segSeq);
		logger.info("segSeq=========>"+ segSeq);
		if( segSeq == 0 ) {
			return  output;
		}else {
			/**
			 * 복구 실행 후 기존 작업 내역 삭제 
			 */
			ActAgentBackupRecoveryVo retVo = getAgentRecoveryMapper.getDataActAgentBackupRecovery(inputVo);
			logger.info("retVo========"+ retVo);
			logger.info("retVo==result======"+ retVo.getResult());
//			output = retVo.getResult();
			
			if( "N".equals(retVo.getResult()) ) {
//				1. 업데이트 정책 삭제 tbl_updt_agent_job ::: org_seq, pcm_uuid 
//				2. 프로그램 정책 삭제 tbl_progrm_agent_job ::: org_seq, pcm_uuid
//				3. 방화벽 포트 정책 삭제 tbl_frwl_agent_job ::: org_seq, pcm_uuid
//				4. 디바이시 정책 삭제 tbl_device_agent_job ::: org_seq, pcm_uuid
//				5. 유해사이트 tbl_site_agent_job ::: pc_uuid
				try {
					int delPolicyVal = getAgentRecoveryMapper.deleteSgbPolicy(inputVo);
					logger.info("delPolicyVal======> "+ delPolicyVal);
					getAgentRecoveryMapper.updateDataActAgentBackupRecovery(inputVo);
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("e==============="+e.getMessage());
					return "error";
				}
				
			}

			
			
			
			
		}
		
		return "aa";
//		return output;
	}
	
	
	/**
	 * 사지방 UUID로 부대 seq 가져오기
	 * 
	 * @param sgbUuid
	 * @return 부대seq
	 */
	public int sgbUUID(String sgbUuid) {
		GetAgentJobVo agentVo = new GetAgentJobVo();
		agentVo.setSgb_pc_uuid(sgbUuid);
		agentVo = agentJobMapper.getAgentJobPcUUID(agentVo);
		int segSeq = 0;
		if(agentVo != null ) {
			segSeq = agentVo.getSeq();	
		}
		return segSeq;
	}

}





