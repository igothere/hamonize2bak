package com.center.hamonize.curl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.IGetAgentFirewallMapper;
import com.mapper.IGetAgentJobMapper;
import com.mapper.IGetAgentUpdtMapper;
import com.model.GetAgentFirewallVo;
import com.model.GetAgentJobVo;
import com.model.GetAgentUpdtVo;

@RestController
@RequestMapping("/getAgent")
public class CurlAgentUpdtController {

	@Autowired
	private IGetAgentJobMapper agentJobMapper;

	@Autowired
	private IGetAgentUpdtMapper getAgentUpdtMapper;

	

	@RequestMapping("/updt")
	public String getAgentJob(@RequestParam(value = "name", required = false) String sgbUuid,
			@RequestParam(value = "wget", required = false) String sgbWget) throws Exception {

		String output = "";

		System.out.println("===" + sgbUuid + "==" + sgbWget);
		sgbUuid = sgbUuid.trim();

		// uuid로 부대정보 가져오기
		int segSeq = sgbUUID(sgbUuid);
		if( segSeq == 0 ) {
			return  "nodata";
		}
		
		GetAgentUpdtVo agentFirewallVo = new GetAgentUpdtVo();
		agentFirewallVo.setOrg_seq(segSeq);
		agentFirewallVo.setPcm_uuid(sgbUuid);

		int chkProgrmPolicy = getAgentUpdtMapper.getAgentWorkYn(agentFirewallVo);
		System.out.println("//===================================");
		System.out.println("//work yn === " + chkProgrmPolicy);
		System.out.println("//===================================");

		
		if ( chkProgrmPolicy == 0 ) {
			JSONObject jsonProgrmData = progrmPolicyData(agentFirewallVo);
			if( jsonProgrmData.size() == 0 ) {
				output = "nodata";
			}else {
				
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

	
	
	public JSONObject progrmPolicyData(GetAgentUpdtVo getProgrmVo) {
		
		GetAgentUpdtVo rDataVo = new GetAgentUpdtVo();
		System.out.println("//====get Vo param is : "+ getProgrmVo.toString());
		
		JSONObject jsonObject = new JSONObject();
		
		GetAgentUpdtVo agentoldseqVo = getAgentUpdtMapper.getAgentOldSeq(getProgrmVo);
	
		if( agentoldseqVo != null ) {
			System.out.println("//====getAgentOldSeq is : "+ getProgrmVo.toString());
			rDataVo.setOrg_seq(agentoldseqVo.getOrg_seq());
			rDataVo.setPcm_uuid(agentoldseqVo.getPcm_uuid());
			rDataVo.setUpdt_ap_seq(agentoldseqVo.getUpdt_ap_seq());
			
		}else {
			System.out.println("getAgentOldSeq is no data");
			rDataVo.setOrg_seq(getProgrmVo.getOrg_seq());
			rDataVo.setPcm_uuid(getProgrmVo.getPcm_uuid());
		}
		
		System.out.println("//====rDataVorDataVorDataVoget Vo param is : "+ rDataVo.toString());
		
		
		int retInsertSelectVal = getAgentUpdtMapper.setInsertSelect(rDataVo);
		
		System.out.println("//===============================");
		System.out.println("//====retInsertSelectVal is : "+ retInsertSelectVal);
		System.out.println("//===============================");

		// 정책 가져오기 
		List<GetAgentUpdtVo> progrmPolicyData = getAgentUpdtMapper.getListUpdtsPolicy(rDataVo);
		System.out.println("//+progrmPolicyData.size() ==="+ progrmPolicyData.size() );
		if( progrmPolicyData.size() == 0  ) {
			jsonObject.put("nodata", "nodata");
			return jsonObject;
		}
		
		rDataVo.setPpm_seq(progrmPolicyData.get(0).getPpm_seq());
		rDataVo.setNew_pa_seq(Integer.parseInt(progrmPolicyData.get(0).getPa_seq()));
		
		List<GetAgentUpdtVo> outputDatga = null;
		
		if( progrmPolicyData.size() == 1) {
			rDataVo.setOld_pa_seq(0);
			// 최초 실행시 
			outputDatga = getAgentUpdtMapper.getAgentInitWorkData(rDataVo);
			
		}else {
			rDataVo.setOld_pa_seq(Integer.parseInt(progrmPolicyData.get(1).getPa_seq()));
			outputDatga = getAgentUpdtMapper.getAgentWorkData(rDataVo);
		}

		String arrAgentProgrmY = "", arrAgentProgrmN = "", arrUpdtStatusUpdate="";
		String arrUpdtUpgrade = "", arrUpdtInsert = "";

		if (outputDatga.size() > 0) {
			
			int arrUpdtInsertCnt=0;
			int arrUpdtUpgradeCnt=0;
			for (int i = 0; i < outputDatga.size(); i++) {
				System.out.println("1========="+ outputDatga.get(i).getGubun());

				if ("INSERT".contentEquals(outputDatga.get(i).getGubun())) {
					arrUpdtInsert += outputDatga.get(i).getPcm_name() + ",";
					arrUpdtInsertCnt++;
				}
				
				if ("UPGRADE".contentEquals(outputDatga.get(i).getGubun())) {
					arrUpdtUpgrade += outputDatga.get(i).getPcm_name() + ",";
					arrUpdtUpgradeCnt++;
				}
				
				if ("INS".contentEquals(outputDatga.get(i).getGubun())) {
					arrAgentProgrmY += outputDatga.get(i).getPcm_name() + ",";
				}

				if ("DEL".contentEquals(outputDatga.get(i).getGubun())) {
					arrAgentProgrmN += outputDatga.get(i).getPcm_name() + ",";
				}
				
				if( i == outputDatga.size()-1 ) {
					arrUpdtStatusUpdate += outputDatga.get(i).getPcm_seq();
				}else {
					arrUpdtStatusUpdate += outputDatga.get(i).getPcm_seq()+",";
				}
				
			}

			if (arrUpdtInsert != "") {
				jsonObject.put("INSERT", arrUpdtInsert);
				
				// 업데이트 항목을 실행 후 프로그램 테이블에 등록.
				// 적용된 항목들은 업데이트 테이블에서 'Y'로 변경 
//				getProgrmVo.setArrStatus(arrUpdtStatusUpdate);
//				getAgentUpdtMapper.updtStatus(getProgrmVo);
//				getAgentUpdtMapper.setProgrmListFromUpdt(getProgrmVo);
				
			}
			if (arrUpdtUpgrade != "") {
				jsonObject.put("UPGRADE", arrUpdtUpgrade);
			}
			if (arrAgentProgrmY != "") {
				jsonObject.put("INS", arrAgentProgrmY);
			}
			if (arrAgentProgrmN != "") {
				jsonObject.put("DEL", arrAgentProgrmN);

//				getProgrmVo.setArrStatus(arrUpdtStatusUpdate);
//				getAgentUpdtMapper.updtStatusDelete(getProgrmVo);
//				getAgentUpdtMapper.setProgrmListFromUpdtDelete(getProgrmVo);
				
			}
			
		}
		 

		System.out.println("//===============================");
		System.out.println("//==INSERT output data is : " + arrUpdtInsert);
		System.out.println("//==UPGRADE output data is : " + arrUpdtUpgrade);
		System.out.println("//==INS output data is : " + arrAgentProgrmY);
		System.out.println("//==DEL output data is : " + arrAgentProgrmN);
		System.out.println("//==jsonObject  data is : " + jsonObject);
		System.out.println("//===============================");
		 
		
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

}
