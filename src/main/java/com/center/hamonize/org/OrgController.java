package com.center.hamonize.org;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mapper.IOrgMapper;
import com.model.OrgVo;
import com.service.IpManagementService;
import com.service.OrgService;
import com.util.AdLdapUtils;


@Controller
@RequestMapping("/org/orgManage")
public class OrgController {
	
	@Autowired
	private OrgService oService;
	
	@Autowired
	private IOrgMapper oMapper;
	
	@Autowired
	private IpManagementService iService;

	/**
	 * 부대관리 페이지
	 * 
	 * @model gList : jsonGroupList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="!type")
	public String orgList(HttpSession session, Model model,HttpServletRequest request) {
		//List<OrgVo> orglist = null;
		JSONArray jsonArray = new JSONArray();

		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
			/*
			 * for (int i = 0; i < orglist.size(); i++) { JSONObject data = new
			 * JSONObject(); data.put("seq", orglist.get(i).getSeq()); data.put("p_seq",
			 * orglist.get(i).getP_seq()); data.put("org_nm", orglist.get(i).getOrg_nm());
			 * data.put("org_ordr", orglist.get(i).getOrg_ordr()); data.put("section",
			 * orglist.get(i).getSection()); jsonArray.add(i, data); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		//System.out.println(jsonArray.toString().replaceAll("\"", ""));
		model.addAttribute("oList", jsonArray);

		return "/org/orgList";
	}
	
	@ResponseBody
	@RequestMapping(params="type=show",method=RequestMethod.POST)
	public JSONObject orgView(HttpSession session, Model model,OrgVo orgvo) {
		orgvo = oService.orgView(orgvo);
		JSONObject data = new JSONObject();
		data.put("seq", orgvo.getSeq());
        data.put("p_seq", orgvo.getP_seq());
        data.put("org_nm", orgvo.getOrg_nm());
        data.put("p_org_nm", orgvo.getP_org_nm());
        data.put("all_org_nm", orgvo.getAll_org_nm());
        data.put("sido", orgvo.getSido());
        data.put("gugun", orgvo.getGugun());
        data.put("section", orgvo.getSection());
        data.put("org_num", orgvo.getOrg_num());
        data.put("org_ordr", orgvo.getOrg_ordr());
		
		return data;
			
	}
	
	@ResponseBody
	@RequestMapping(params="type=save",method=RequestMethod.POST)
	public int orgSave(HttpSession session, Model model,OrgVo orgvo) throws Exception{
		int result = oService.orgSave(orgvo);
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping(params="type=delt",method=RequestMethod.POST)
	public int orgDelete(HttpSession session, Model model,OrgVo orgvo) throws Exception {
		
		int result = oService.orgDelete(orgvo);
		return result;
		
	}
	
	@RequestMapping("addAdServer")
	public void addAdServer() throws Exception{
		 List<OrgVo> oList = new ArrayList<OrgVo>();
		  oList = oMapper.orgList();
		  for(int i = 0; i < oList.size();i++) {
			  AdLdapUtils adUtils = new AdLdapUtils();
			  OrgVo upGroupInfo = oMapper.groupUpperCode(oList.get(i));
				System.out.println(i+"-----------NEWupGroupInfo=================="+upGroupInfo.getOrg_nm());
				System.out.println("section====="+oList.get(i).getSection());
				System.out.println("부대다!!!");
				adUtils.adOuCreate(upGroupInfo.getOrg_nm());
				if("S".equals(oList.get(i).getSection()) ){
				adUtils.sgbOuModify(upGroupInfo.getOrg_nm());
					System.out.println("사지방이다!!!!!!!!!!!!!!!");
				System.out.println("upGroupInfo.getOrgname()====="+ upGroupInfo.getOrg_nm().replaceAll("/","\\/"));
				}
			  System.out.println(oList.get(i).getOrg_nm());
		  }
	}

//	@RequestMapping("change")
//    public @ResponseBody Object change(HttpServletRequest request,
//    		HttpServletResponse response, 
//    		@RequestParam Map<String, Object> params ) throws Exception {
//    	
//		
//   		ObjectMapper mapper = new ObjectMapper();
//   		List<Map<String,Object>>  dataList = mapper.readValue(params.get("data").toString(), new TypeReference<List<Map<String,Object>>>(){});
//   		//cmmnService.updateBatchObject("orgcht.change", param, dataList);
//   		//cmmnService.updateBatchObject("orgchtjob.change", param, dataList);
//		
//   		return "SUCCESS";
//   		
//   	}

}
