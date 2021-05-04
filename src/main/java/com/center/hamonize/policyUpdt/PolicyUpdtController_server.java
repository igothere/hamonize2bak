package com.center.hamonize.policyUpdt;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;


@Controller
@RequestMapping("/gplcs_server")
public class PolicyUpdtController_server {
	
	@Autowired
	private OrgService oService;
	
	@Autowired
	private PolicyUpdtService_server uService;
	
	@Autowired
	private IPolicyUpdtMapper_server uMapper;
	
	@Autowired
	private AgentAptListService_server aService;
	
	
	@RequestMapping("/umanage")
	public String manage(HttpSession session, Model model) throws Exception{
		
		JSONArray jsonArray = new JSONArray();
		List<PolicyUpdtVo> pList = null;
		//APT저장소 목록
		List<Map<String, Object>> listMap= new ArrayList<Map<String, Object>>();
		//센터 업데이트 프로그램 목록
		List<Map<String, Object>> pSearchList = new ArrayList<Map<String, Object>>();
		//신규등록 프로그램 목록
		List<Map<String, Object>> newAdd = new ArrayList<Map<String, Object>>();
		//버전 업데이트 프로그램 목록
		List<Map<String, Object>> newUpdate = new ArrayList<Map<String, Object>>();
		Map<String, Object> params;

		try {
			OrgVo orgvo = new OrgVo();
			PolicyUpdtVo vo = new PolicyUpdtVo();
			jsonArray = oService.orgList(orgvo);
			
			pSearchList = uMapper.updtComapreList();
			listMap = aService.getApt();
	
			//APT저장소와 업데이트목록 비교후 등록 및 업데이트
			for (int i = 0; i < listMap.size(); i++) {
				boolean chk = false;
				for (int j = 0; j < pSearchList.size(); j++) {
					if (listMap.get(i).get("package").equals(pSearchList.get(j).get("pu_name"))) {
						chk = true;
						if (!listMap.get(i).get("version").equals(pSearchList.get(j).get("deb_new_version"))) {
							System.out.println("update");
							// newUpdate.add(listMap.get(i));
							int result = uService.updtCompareUpdate(listMap.get(i));
							break;
						} else {
							System.out.println("있음");
							break;
						}
					} else {
						chk = false;
					}
				}
				if (!chk) {
					newAdd.add(listMap.get(i));
				}
			}
			if(!newAdd.isEmpty()) {
			params = new HashMap<String, Object>();
			params.put("data", newAdd);
			int result = uService.updtCompareSave(params);
			System.out.println("result===="+result);
			}
			pList = uService.updtList(vo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		
		model.addAttribute("oList", jsonArray);
		model.addAttribute("pList",pList);
		
		return "/policy_server/updtManage";
		
	}
	@ResponseBody
	@RequestMapping("/usave")
	public String usave(HttpSession session, Model model,@RequestParam Map<String, Object> params ) {
		
		JsonParser jp = new JsonParser();
		String data = params.get("data").toString();
		JsonArray jsonArray = (JsonArray) jp.parse(data);
		List<Map<String, Object>> resultSet = new ArrayList<Map<String, Object>>();
		 Map<String, Object> resultMap;
		for(int i = 0; i < jsonArray.size(); i++) {
			resultMap = new HashMap<String, Object>();
			String ch = jsonArray.get(i).toString().replaceAll("[^0-9]", "");
			resultMap.put("org_seq", Integer.parseInt(ch));
			resultSet.add(resultMap);
		}
		params.put("data",resultSet);
		System.out.println("params..."+params);
		int result = 0;
		uService.updtDelete(params);
		result = uService.updtSave(params);
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
	@RequestMapping("ushow")
	public JSONObject ushow(HttpSession session, Model model,PolicyUpdtVo vo) {
		JSONObject data = new JSONObject();
		try {
			vo = uService.updtApplcView(vo);
			data.put("dataInfo", vo);
		}catch(Exception e) {
			
		}
				
		return data;
			
	}
	
}
