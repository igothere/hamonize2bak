package com.center.hamonize.policyProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.OrgVo;
import com.model.PolicyProgramVo;
import com.service.OrgService;
import com.service.PolicyProgramService;
import com.service.PolicyProgramService_server;

@Controller
@RequestMapping("/gplcs_server")
public class PolicyProgramController_server {
	
	@Autowired
	private OrgService oService;
	
	@Autowired
	private PolicyProgramService_server pService;
	
	
	@RequestMapping("/pmanage")
	public String manage(HttpSession session, Model model) {
		
		JSONArray jsonArray = new JSONArray();
		List<PolicyProgramVo> pList = null;

		try {
			OrgVo orgvo = new OrgVo();
			PolicyProgramVo vo = new PolicyProgramVo();
			jsonArray = oService.orgList(orgvo);
			pList = pService.programList(vo);
		
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		model.addAttribute("oList", jsonArray);
		model.addAttribute("pList",pList);
		
		return "/policy_server/programManage";
		
	}
	@ResponseBody
	@RequestMapping("/psave")
	public String pinsert(HttpSession session, Model model,@RequestParam Map<String, Object> params ) {
		
		JsonParser jp = new JsonParser();
		String data = params.get("data").toString();
		JsonArray jsonArray = (JsonArray) jp.parse(data);
		List<Map<String, Object>> resultSet = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap;		
		for (int i = 0; i < jsonArray.size(); i++) {
			resultMap = new HashMap<String, Object>();
			String ch = jsonArray.get(i).toString().replaceAll("[^0-9]", "");
			resultMap.put("org_seq", Integer.parseInt(ch));
			resultSet.add(resultMap);
		}
		
		params.put("data",resultSet);
		System.out.println("params..."+params);
		int result = 0;
		pService.programDelete(params);
		result = pService.programSave(params);
		/*
		 * try { OrgVo orgvo = new OrgVo(); vo = new PolicyProgramVo(); jsonArray =
		 * oService.orgList(orgvo); pList = pService.programList(vo);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); // FAIL_GET_LIST }
		 */
		if(result >=1 )
			return "SUCCESS";
		else
			return"FAIL";
		
	}
	
	@ResponseBody
	@RequestMapping("pshow")
	public JSONObject pshow(HttpSession session, Model model,PolicyProgramVo vo) {
			JSONObject data = new JSONObject();
		try {
			vo = pService.programApplcView(vo);
			data.put("dataInfo", vo);
		}catch(Exception e) {
			
		}
		/*
		 * System.out.println("asd"+vo.getPcm_seq()); vo =
		 * pService.programApplcView(vo);
		 * System.out.println("orgvo===="+vo.getPpm_seq()); JSONObject data = new
		 * JSONObject(); data.put("dataInfo", vo);
		 * System.out.println("zzzzz"+data.get("ppm_seq"));
		 */

		
		return data;
			
	}
	
}
