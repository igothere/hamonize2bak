package com.center.hamonize.curl.server;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.IGetAgentBackupMapper;
import com.mapper.IGetAgentJobMapper;
import com.mapper.server.IGetAgentBackupMapper_Server;
import com.mapper.server.IGetAgentJobMapper_Server;
import com.model.CurlPcBackupVo;
import com.model.GetAgentBackupVo;
import com.model.GetAgentJobVo;

@RestController
@RequestMapping("/getAgentServer")
public class Server_CurlAgentBackupController {

	@Autowired
	private IGetAgentJobMapper_Server agentJobMapper;

	@Autowired
	private IGetAgentBackupMapper_Server getAgentBackupMapper;

	

	@RequestMapping("/backup")
	public String getAgentJob(@RequestParam(value = "name", required = false) String sgbUuid,
			@RequestParam(value = "wget", required = false) String sgbWget) throws Exception {

		// 출력 변수
		String output = "";

//		JSONParser jsonParser = new JSONParser();
//		JSONObject jsonObj = (JSONObject) jsonParser.parse( payload);
//		System.out.println("====> "+ jsonObj.get("name") +"==="+ jsonObj.get("call"));
//        
//		String sgbUuid = jsonObj.get("name").toString().trim();
//		String wgetGB = jsonObj.get("call").toString().trim();

		System.out.println("===" + sgbUuid + "==" + sgbWget);
		sgbUuid = sgbUuid.trim();

		// uuid로 부대정보 가져오기
		int segSeq = sgbUUID(sgbUuid);
		if( segSeq == 0 ) {
			return  "nodata";
		}

		GetAgentBackupVo agentBackupVo = new GetAgentBackupVo();
		agentBackupVo.setOrg_seq(segSeq);
		agentBackupVo.setPcm_uuid(sgbUuid);

		int chkProgrmPolicy = getAgentBackupMapper.getAgentWorkYn(agentBackupVo);
		System.out.println("//===================================");
		System.out.println("//work yn === " + chkProgrmPolicy);
		System.out.println("//===================================");

		
		if ( chkProgrmPolicy == 0 ) {
			JSONObject jsonProgrmData = progrmPolicyData(agentBackupVo);
			//output = jsonProgrmData.toJSONString();
			
			if( jsonProgrmData.size() == 0 ) {
				output = "nodata";
			}else {
//				output = jsonProgrmData.toJSONString();
				System.out.println("jsonProgrmData.get(\"NODATA\")======"+ jsonProgrmData.get("nodata"));
				
				if( jsonProgrmData.get("nodata") != null ) {
					output =  jsonProgrmData.get("nodata").toString();	
				}else {
					output = jsonProgrmData.toJSONString();
				}
				
			}
			
		} else {
			output = "nodata";
		}
		

		System.out.println("//===================================");
		System.out.println("//result data is : " + output);
		System.out.println("//===================================");
		
		return output;
	}

	
	
	public JSONObject progrmPolicyData(GetAgentBackupVo agentBackupVo) {

		JSONObject jsonObject = new JSONObject();

		
		int retInsertSelectVal = getAgentBackupMapper.setInsertSelect(agentBackupVo);
		
		System.out.println("//===============================");
		System.out.println("//====retInsertSelectVal is : "+ retInsertSelectVal);
		System.out.println("//===============================");

		// 정책 가져오기 
		List<GetAgentBackupVo> progrmBackupData = getAgentBackupMapper.getListBackupPolicy(agentBackupVo);
		System.out.println("//+progrmPolicyData.size() ==="+ progrmBackupData.size() );
		if( progrmBackupData.size() == 0  ) {
			jsonObject.put("nodata", "nodata");
			return jsonObject;
		}
		
		
		JSONObject backupInfo = new JSONObject();
		String arrAgentProgrmY = "", arrAgentProgrmN = "";
		for (GetAgentBackupVo set : progrmBackupData) {
			System.out.println("----> " + set.getBac_seq() + "==" + set.getOrg_seq() + "==" + set.getBac_cycle_option() + "=="
					+ set.getBac_cycle_time() + "==" + set.getBac_gubun());
			
			backupInfo = new JSONObject();
			backupInfo.put("cycle_option", set.getBac_cycle_option());
			backupInfo.put("cycle_time", set.getBac_cycle_time());
			backupInfo.put("Bac_gubun", set.getBac_gubun());
		}
		

		JSONArray backupArray = new JSONArray();
		backupArray.add(backupInfo);
		
        jsonObject.put("backup", backupArray);
		return jsonObject;
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

	
//	======================== init program data insert ==============
	

	@RequestMapping("/setBackupJob")
	public String  setBackupJob(@RequestBody String valLoad ) throws Exception {

		String output = "";
		System.out.println("setBackupJobsetBackupJobsetBackupJobsetBackupJobsetBackupJobsetBackupJobsetBackupJobvalLoad==="+ valLoad);
		
		
		CurlPcBackupVo cbVo = new CurlPcBackupVo();

        
		JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(valLoad);
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");
		
		for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
            cbVo.setBr_backup_path(tempObj.get("dir").toString());
            cbVo.setBk_name(tempObj.get("name").toString());
            cbVo.setBk_uuid(tempObj.get("uuid").toString());
            cbVo.setBr_backup_iso_dt(tempObj.get("datetime").toString());
            cbVo.setBk_hostname(tempObj.get("hostname").toString());
            cbVo.setBr_backup_gubun(tempObj.get("gubun").toString());
		}
		
		System.out.println("===> "+ cbVo.toString());

//		valLoad==={       
//			"events" : [ {       "datetime":"2019-06-27 23:09:16",       
//			"uuid":"HamoniKR-5DDAC624-3298-46AC-80A6-38EF370EDDB2",      
//			"name": "",       
//			"hostname": "t05-vb",       
//			"dir": "/timeshift/snapshots"      
//			"gubun": "B"       } ]}
		
		
		GetAgentJobVo agentVo = new GetAgentJobVo(); 
		agentVo.setSgb_pc_uuid(cbVo.getBk_uuid());
		agentVo.setSeq(cbVo.getBr_org_seq());
		System.out.println("agentVo ===222"+ agentVo.toString() );
		agentVo = agentJobMapper.getAgentJobPcUUIDInBACKUP(agentVo);
		
		System.out.println("agentVo ==111="+ agentVo.toString() );
		
		
		cbVo.setBr_org_seq( agentVo.getOrg_seq());
		cbVo.setSgb_seq(agentVo.getSgb_seq());
//		cbVo.setBr_backup_gubun("A");
		agentJobMapper.insertBackupInfo( cbVo );
		
		
		return output;
	}
}
