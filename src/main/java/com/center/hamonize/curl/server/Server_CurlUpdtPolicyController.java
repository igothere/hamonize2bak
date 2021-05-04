package com.center.hamonize.curl.server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.server.IUpdtPollicyMapper_Server;
import com.model.UpdtPolicyVo;

@RestController
@RequestMapping("/hmsvcServer")
public class Server_CurlUpdtPolicyController {

	private final static Logger logger = Logger.getGlobal();


	@Autowired
	IUpdtPollicyMapper_Server updtPollicyMapper;
	

	@RequestMapping("/updtpolicy")
	public String getAgentJob(HttpServletRequest request) throws Exception {
		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	        	logger.info("line===> "+ line);
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        logger.info("Error reading JSON string: " + e.toString());
	    }
	    //String test = "{\"insresert\":[{\"state\":\"1\",\"path\":\"\"}],\"updtresert\":[],\"delresert\":[],\"uuid\":\"69539dee19fb40f58b7e26453d8f9bb3\"}";
	    JSONParser Parser = new JSONParser();
	    JSONObject jsonObj = (JSONObject) Parser.parse(json.toString());
	    //JSONObject jsonObj = (JSONObject) Parser.parse(test);
	  
	    
	    /** ======================================
	     * deb insert 
	     *=======================================*/
	    JSONArray insArray = (JSONArray) jsonObj.get("insresert");
	    UpdtPolicyVo[] updtVo = new UpdtPolicyVo[insArray.size()];
	    if( insArray.size() != 0 ) {
		    for (int i = 0; i < insArray.size(); i++) {          
		    	JSONObject object = (JSONObject) insArray.get(i);
		    	updtVo[i] = new UpdtPolicyVo();
		    	updtVo[i].setDebname(object.getOrDefault("debname","").toString());
		    	updtVo[i].setDebver(object.getOrDefault("debver", "").toString());
		    	updtVo[i].setState(object.getOrDefault("state","").toString());
		    	updtVo[i].setPath(object.getOrDefault("path","").toString());
		    	updtVo[i].setGubun("INSTALL");
		    	updtVo[i].setPc_uuid(jsonObj.getOrDefault("uuid","").toString());
		    
		    	
		    	UpdtPolicyVo insDataVo = new UpdtPolicyVo();
		    	insDataVo.setDebname(object.getOrDefault("debname","").toString());
		    	updtPollicyMapper.updtInsertProgrm(insDataVo);
		    	
		    }  
		    
		    
		    
		    //logger.info("====uuuuuu==="+ jsonObj.get("uuid").toString());
		    // deb insert & program add
//		    Map<String, Object> mapInsert = new HashMap<String, Object>();
//		    mapInsert.put("list", updtVo);
//		    updtPollicyMapper.updtInsertProgrm(mapInsert);
	    }
	    
	    
	    /** ======================================
	     * deb update 
	     *=======================================*/
	    JSONArray updtArray = (JSONArray) jsonObj.get("updtresert");        
	    UpdtPolicyVo[] updtVo2 = new UpdtPolicyVo[updtArray.size()];
	    
	    if( updtArray.size() != 0 ) {
		    for (int i = 0; i < updtArray.size(); i++) {          
		    	JSONObject object = (JSONObject) updtArray.get(i);
		    	//logger.info("debname :" + object.get("debname"));   
		    	//logger.info("state : " + object.get("state"));   
		    	//logger.info("path" + object.get("path"));      
		    	updtVo2[i] = new UpdtPolicyVo();
		    	updtVo2[i].setDebname(object.getOrDefault("debname","").toString());
		    	updtVo2[i].setDebver(object.getOrDefault("debver","").toString());
		    	updtVo2[i].setState(object.getOrDefault("state","").toString());
		    	updtVo2[i].setPath(object.getOrDefault("path","").toString());
		    	updtVo2[i].setGubun("UPGRADE");
		    	updtVo2[i].setPc_uuid(jsonObj.getOrDefault("uuid","").toString());
		    	
		    }
	    }
	    
	    
	    /** ======================================
	     * deb delete 
	     *=======================================*/
	    JSONArray delArray = (JSONArray) jsonObj.get("delresert");        
	    UpdtPolicyVo[] updtVo3 = new UpdtPolicyVo[delArray.size()];
	    
	    if( delArray.size() != 0 ) {
		    	
		    for (int i = 0; i < delArray.size(); i++) {          
		    	JSONObject object = (JSONObject) delArray.get(i);
		    	
		    	updtVo3[i] = new UpdtPolicyVo();
		    	updtVo3[i].setDebname(object.getOrDefault("debname","").toString());
		    	updtVo3[i].setDebver(object.getOrDefault("debver","").toString());
		    	updtVo3[i].setState(object.getOrDefault("state","").toString());
		    	updtVo3[i].setPath(object.getOrDefault("path","").toString());
		    	updtVo3[i].setGubun("DELETE");
		    	updtVo3[i].setPc_uuid(jsonObj.getOrDefault("uuid","").toString());
		    	
		    }
		 // deb insert & program del
		    Map<String, Object> mapDelete = new HashMap<String, Object>();
		    mapDelete.put("list", updtVo3);
		    updtPollicyMapper.updtDeleteProgrm(mapDelete);
	}
	    
	    
	    

	    UpdtPolicyVo[] updtVoSum = new UpdtPolicyVo[updtArray.size() + insArray.size() + delArray.size() ];
	    logger.info("updtVoSum======"+ updtVoSum.length);
	    logger.info("=====>"+ updtArray.size() +"==" +  insArray.size() + "==" + delArray.size());
	    System.arraycopy(updtVo, 0, updtVoSum, 0, updtVo.length);
	    System.arraycopy(updtVo2, 0, updtVoSum, updtVo.length, updtVo2.length);
	    System.arraycopy(updtVo3, 0, updtVoSum, updtVo2.length, updtVo3.length);
	   
	    
		/*
		 * for( int i=0; i<updtVoSum.length; i++) {
		 * logger.info("updtVo[i]======="+ updtVoSum[i].toString()); }
		 */
	    
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("list", updtVoSum);
	    
	    if( updtVoSum.length != 0 ) {
	    	updtPollicyMapper.updtPolicyActionResultInsert(map);
	    }
	    
	    
	   
		
	    return null;
	}

	
	


	/**
	 * 사지방 UUID로 부대 seq 가져오기
	 * 
	 * @param sgbUuid
	 * @return 부대seq
	 */
//	public int sgbUUID(String sgbUuid) {
//		GetAgentJobVo agentVo = new GetAgentJobVo();
//		agentVo.setSgb_pc_uuid(sgbUuid);
//		agentVo = agentJobMapper.getAgentJobPcUUID(agentVo);
//		int segSeq = agentVo.getSeq();
//		return segSeq;
//	}

}
