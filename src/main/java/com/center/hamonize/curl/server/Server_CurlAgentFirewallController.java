package com.center.hamonize.curl.server;

import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.server.IGetAgentFirewallMapper_Server;
import com.mapper.server.IGetAgentJobMapper_Server;
import com.model.GetAgentFirewallVo;
import com.model.GetAgentJobVo;

@RestController
@RequestMapping("/getAgentServer")
public class Server_CurlAgentFirewallController {
	private final static Logger logger = Logger.getGlobal();

	@Autowired
	private IGetAgentJobMapper_Server agentJobMapper;

	@Autowired
	private IGetAgentFirewallMapper_Server getAgentFirewallMapper;

	@RequestMapping("/firewall")
	public String getAgentJob(@RequestParam(value = "name", required = false) String sgbUuid,
			@RequestParam(value = "wget", required = false) String sgbWget) throws Exception {

		
		logger.info("sgbUuid============++"+ sgbUuid);
		// 출력 변수
		String output = "";
		logger.info("sgbUuid===" + sgbUuid + "sgbWget==" + sgbWget);
		sgbUuid = sgbUuid.trim();

		// uuid로 부대정보 가져오기
		int segSeq = sgbUUID(sgbUuid);
		if( segSeq == 0 ) {
			return  "nodata";
		}
		
		GetAgentFirewallVo agentFirewallVo = new GetAgentFirewallVo();
		agentFirewallVo.setOrg_seq(segSeq);
		agentFirewallVo.setPcm_uuid(sgbUuid);

		int chkProgrmPolicy = getAgentFirewallMapper.getAgentWorkYn(agentFirewallVo);
		logger.info("//===================================");
		logger.info("//work yn === " + chkProgrmPolicy);
		logger.info("//===================================");

		if ( chkProgrmPolicy == 0 ) {
			JSONObject jsonProgrmData = progrmPolicyData(agentFirewallVo);
			if( jsonProgrmData.size() == 0 ) {
				output = "nodata";
			}else {
				logger.info("jsonProgrmData.get(\"NODATA\")======"+ jsonProgrmData.get("nodata"));
				if( jsonProgrmData.get("nodata") != null ) {
					output =  jsonProgrmData.get("nodata").toString();	
				}else {
					output = jsonProgrmData.toJSONString();
				}
			}
		} else {
			output = "nodata";
		}
		
		logger.info("//===================================");
		logger.info("//result data is : " + output);
		logger.info("//===================================");
		
		return output;
	}

	
	
	public JSONObject progrmPolicyData(GetAgentFirewallVo getProgrmVo) {

		JSONObject jsonObject = new JSONObject();
		
		GetAgentFirewallVo agentoldseqVo = getAgentFirewallMapper.getAgentOldSeq(getProgrmVo);
		if( agentoldseqVo != null ) {
			logger.info("//====getAgentOldSeq is : "+ agentoldseqVo.toString());
			getProgrmVo.setOrg_seq(agentoldseqVo.getOrg_seq());
			getProgrmVo.setPcm_uuid(agentoldseqVo.getPcm_uuid());
			getProgrmVo.setFa_seq(agentoldseqVo.getFa_seq());
			
		}else {
			logger.info("getAgentOldSeq is no data");
			getProgrmVo.setOrg_seq(getProgrmVo.getOrg_seq());
			getProgrmVo.setPcm_uuid(getProgrmVo.getPcm_uuid());
		}		
		
		logger.info("getProgrmVo==="+getProgrmVo.toString());
		int retInsertSelectVal = getAgentFirewallMapper.setInsertSelect(getProgrmVo);
		
		logger.info("//===============================");
		logger.info("//====retInsertSelectVal is : "+ retInsertSelectVal);
		logger.info("//===============================");

		// 방화벽 정책 가져오기 
		List<GetAgentFirewallVo> progrmPolicyData = getAgentFirewallMapper.getListFirewallPolicy(getProgrmVo);
		logger.info("//+progrmPolicyData.size() ==="+ progrmPolicyData.size() );
		if( progrmPolicyData.size() == 0  ) {
			jsonObject.put("nodata", "nodata");
			return jsonObject;
		}

		getProgrmVo.setPpm_seq(progrmPolicyData.get(0).getPpm_seq());
		getProgrmVo.setNew_pa_seq(Integer.parseInt(progrmPolicyData.get(0).getPa_seq()));
		List<GetAgentFirewallVo> outputDatga = null;
		
		if( progrmPolicyData.size() == 1) {
			getProgrmVo.setOld_pa_seq(0);
			
			outputDatga = getAgentFirewallMapper.getAgentInitWorkData(getProgrmVo);
			
		}else {
			getProgrmVo.setOld_pa_seq(Integer.parseInt(progrmPolicyData.get(1).getPa_seq()));
			outputDatga = getAgentFirewallMapper.getAgentWorkData(getProgrmVo);
		}

		String arrAgentProgrmY = "", arrAgentProgrmN = "";

		if (outputDatga.size() > 0) {
			for (int i = 0; i < outputDatga.size(); i++) {

				if ("INS".contentEquals(outputDatga.get(i).getGubun())) {
					if (outputDatga.size() - 1 == i) {
						arrAgentProgrmY += outputDatga.get(i).getSm_port();
					} else {
						arrAgentProgrmY += outputDatga.get(i).getSm_port() + ",";
					}
				}

				if ("DEL".contentEquals(outputDatga.get(i).getGubun())) {
					if (outputDatga.size() - 1 == i) {
						arrAgentProgrmN += outputDatga.get(i).getSm_port();
					} else {
						arrAgentProgrmN += outputDatga.get(i).getSm_port()+ ",";
					}
				}
			}

			if (arrAgentProgrmY != "") {
				jsonObject.put("INS", arrAgentProgrmY);
			}
			if (arrAgentProgrmN != "") {
				jsonObject.put("DEL", arrAgentProgrmN);
			}

		}

		logger.info("//===============================");
		logger.info("//==INS output data is : " + arrAgentProgrmY);
		logger.info("//==DEL output data is : " + arrAgentProgrmN);
		logger.info("//==jsonObject  data is : " + jsonObject);
		logger.info("//===============================");
		 
		
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
