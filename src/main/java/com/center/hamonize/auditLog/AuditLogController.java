package com.center.hamonize.auditLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.center.hamonize.auditLog.mapper.IAuditLogMapper;
import com.center.hamonize.org.OrgService;
import com.center.hamonize.org.OrgVo;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/auditLog")
public class AuditLogController {

	@Autowired
	private OrgService oService;
	
	@Autowired
	private AuditLogService logService;
	
	@Autowired
	private IAuditLogMapper logMapper;
	
	
	/**
	 * PC 사용 로그
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pcUserLog")
	public String pcUserLog(Model model ,@RequestParam Map<String, Object> params ) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		//System.out.println(jsonArray.toString().replaceAll("\"", ""));
		model.addAttribute("oList", jsonArray);

		return "/auditLog/pcUserLog";
	}
	
	@ResponseBody
	@RequestMapping("userLogList.proc")
	public Map<String, Object> listProc(AuditLogVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {

		System.out.println("===========" + vo.getOrg_seq());
		System.out.println("txtsearch=="+vo.getTxtSearch());
		System.out.println("keyword=="+vo.getKeyWord());
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countUserLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			List<AuditLogVo> list = logService.userLogList(vo, pagingVo);
			jsonObject.put("list", list);
			jsonObject.put("auditLogVo", vo);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	@RequestMapping("/pcUserLogExcel")
	public CmmnExcelService pcUserLogExcel(AuditLogVo vo, PagingVo pagingVo,HttpServletRequest request, HttpServletResponse response, ModelMap model,
    		@RequestParam Map<String, String> params) throws Exception {
		System.out.println("params"+params.toString());
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		vo.setDate_fr(vo.getDate_fr().replaceAll("/",""));
		vo.setDate_to(vo.getDate_to().replaceAll("/",""));

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countUserLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		List<Map<String, Object>> list = logMapper.userLogListExcel(vo);
		
		String[] head ={"번호","군번","계급","이름","아이디","로그인시간","로그아웃시간","사용시간","접속사지방명","가입사지방명"};
		String[] column ={"rownum","user_gunbun","rank","user_name","user_id","login_dt","logout_dt","spent_time","org_nm","join_org_nm"};
		jsonObject.put("header", head);		  // Excel 상단
		jsonObject.put("column", column);		  // Excel 상단
		jsonObject.put("excelName","사용자접속로그");    // Excel 파일명
		jsonObject.put("list", list);          // Excel Data
		
		//model.addAttribute("header", head);
		//model.addAttribute("excelName","고고고");
		//model.addAttribute("list", list);
		model.addAttribute("data", jsonObject);
		return new CmmnExcelService();
    }
	
	/**
	 * 인터넷 사용 로그
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/iNetLog")
	public String iNetLog(Model model ,@RequestParam Map<String, Object> params,AuditLogVo vo ) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}

		model.addAttribute("oList", jsonArray);
		model.addAttribute("auditLogVo",vo);

		return "/auditLog/pcInetLog";
	}
	
	@ResponseBody
	@RequestMapping("iNetLogList.proc")
	public Map<String, Object> iNetLogList(AuditLogVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {
		System.out.println("start-"+System.currentTimeMillis()/1000);
		vo.setDate_fr(vo.getDate_fr().replaceAll("/",""));
		vo.setDate_to(vo.getDate_to().replaceAll("/",""));
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);
		//System.out.println("start-"+System.currentTimeMillis()/1000);
		int cnt = Integer.parseInt(logMapper.countInetLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);
		//System.out.println("end-"+System.currentTimeMillis()/1000);
		try {
			//System.out.println("start-"+System.currentTimeMillis()/1000);
			List<AuditLogVo> list = logService.iNetLogList(vo, pagingVo);
			jsonObject.put("list", list);
			jsonObject.put("auditLogVo", vo);
			jsonObject.put("pagingVo", pagingVo);
			//System.out.println("end-"+System.currentTimeMillis()/1000);
			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}
		System.out.println("end-"+System.currentTimeMillis()/1000);
		return jsonObject;
	}
	
	@RequestMapping("/iNetLogExcel")
	public CmmnExcelService iNetLogExcel(AuditLogVo vo, PagingVo pagingVo,HttpServletRequest request, HttpServletResponse response, ModelMap model,
    		@RequestParam Map<String, String> params) throws Exception {
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		vo.setDate_fr(vo.getDate_fr().replaceAll("/",""));
		vo.setDate_to(vo.getDate_to().replaceAll("/",""));

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countInetLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		List<Map<String, Object>> list = logMapper.iNetLogListExcel(vo);
		
		String[] head ={"번호","아이피","URL","PCUUID","HOSTNAME","상태","시간","아이디","이름","계급","사지방명"};
		String[] column ={"rownum","pc_ip","cnnc_url","pc_uuid","hostname","state","reg_dt","user_id","user_name","rank","org_nm"};
		jsonObject.put("header", head);		  // Excel 상단
		jsonObject.put("column", column);		  // Excel 상단
		jsonObject.put("excelName","인터넷사용로그");    // Excel 파일명
		jsonObject.put("list", list);          // Excel Data
		
		//model.addAttribute("header", head);
		//model.addAttribute("excelName","고고고");
		//model.addAttribute("list", list);
		model.addAttribute("data", jsonObject);
		return new CmmnExcelService();
    }
	
	/**
	 * PC 하드웨어 변경
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pcChangeLog")
	public String pcChangeLog(Model model ,@RequestParam Map<String, Object> params ) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		//System.out.println(jsonArray.toString().replaceAll("\"", ""));
		model.addAttribute("oList", jsonArray);

		return "/auditLog/pcChangeLog";
	}
	
	@ResponseBody
	@RequestMapping("pcChangeLogList.proc")
	public Map<String, Object> pcChangeLogList(AuditLogVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {

		System.out.println("===========" + vo.getOrg_seq());
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countPcChangeLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			List<AuditLogVo> list = logService.pcChangeLogList(vo, pagingVo);
			jsonObject.put("list", list);
			jsonObject.put("auditLogVo", vo);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	@RequestMapping("/pcChangeLogExcel")
	public CmmnExcelService pcChangeLogExcel(AuditLogVo vo, PagingVo pagingVo,HttpServletRequest request, HttpServletResponse response, ModelMap model,
    		@RequestParam Map<String, String> params) throws Exception {
		System.out.println("params"+params.toString());
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		vo.setDate_fr(vo.getDate_fr().replaceAll("/",""));
		vo.setDate_to(vo.getDate_to().replaceAll("/",""));

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countUserLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		List<Map<String, Object>> list = logMapper.userLogListExcel(vo);
		
		String[] head ={"번호","UUID","제품회사","제품번호","INFO","사용자아이디","사용일"};
		String[] column ={"rownum","user_gunbun","rank","user_name","user_id","login_dt","logout_dt","spent_time","org_nm"};
		jsonObject.put("header", head);		  // Excel 상단
		jsonObject.put("column", column);		  // Excel 상단
		jsonObject.put("excelName","사용자접속로그");    // Excel 파일명
		jsonObject.put("list", list);          // Excel Data
		
		//model.addAttribute("header", head);
		//model.addAttribute("excelName","고고고");
		//model.addAttribute("list", list);
		model.addAttribute("data", jsonObject);
		return new CmmnExcelService();
    }
	
	
	/**
	 * PC 사용 로그
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/unAuthLog")
	public String unAuthLog(Model model ,@RequestParam Map<String, Object> params ) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		//System.out.println(jsonArray.toString().replaceAll("\"", ""));
		model.addAttribute("oList", jsonArray);

		return "/auditLog/pcUnAuthLog";
	}
	
	@ResponseBody
	@RequestMapping("unAuthLogList.proc")
	public Map<String, Object> unAuthLogListproc(AuditLogVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {

		System.out.println("===========" + vo.getOrg_seq());
		System.out.println("txtsearch=="+vo.getTxtSearch());
		System.out.println("keyword=="+vo.getKeyWord());
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countUnAuthLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			List<AuditLogVo> list = logService.unAuthLogList(vo, pagingVo);
			jsonObject.put("list", list);
			jsonObject.put("auditLogVo", vo);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	@RequestMapping("/unAuthLogExcel")
	public CmmnExcelService unAuthLogExcel(AuditLogVo vo, PagingVo pagingVo,HttpServletRequest request, HttpServletResponse response, ModelMap model,
    		@RequestParam Map<String, String> params) throws Exception {
		System.out.println("params"+params.toString());
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		vo.setDate_fr(vo.getDate_fr().replaceAll("/",""));
		vo.setDate_to(vo.getDate_to().replaceAll("/",""));

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countUnAuthLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		List<Map<String, Object>> list = logMapper.unAuthLogListExcel(vo);
		
		String[] head ={"번호","UUID","제품회사번호","제품번호","INFO","사용아이디","사용일","사지방명"};
		String[] column ={"rownum","pc_uuid","vendor","product","info","pc_user","insert_dt","org_nm"};
		jsonObject.put("header", head);		  // Excel 상단
		jsonObject.put("column", column);		  // Excel 상단
		jsonObject.put("excelName","비인가 디바이스 사용로그");    // Excel 파일명
		jsonObject.put("list", list);          // Excel Data
		
		//model.addAttribute("header", head);
		//model.addAttribute("excelName","고고고");
		//model.addAttribute("list", list);
		model.addAttribute("data", jsonObject);
		return new CmmnExcelService();
    }
	
	/**
	 * 프로세스 차단 로그
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/prcssBlockLog")
	public String prcssBlockLog(Model model ,@RequestParam Map<String, Object> params ) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		//System.out.println(jsonArray.toString().replaceAll("\"", ""));
		model.addAttribute("oList", jsonArray);

		return "/auditLog/prcssBlockLog";
	}
	
	@ResponseBody
	@RequestMapping("prcssBlockLogList.proc")
	public Map<String, Object> prcssBlockLogList(AuditLogVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {

		System.out.println("===========" + vo.getOrg_seq());
		System.out.println("txtsearch=="+vo.getTxtSearch());
		System.out.println("keyword=="+vo.getKeyWord());
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countPrcssBlockLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			List<AuditLogVo> list = logService.prcssBlockLogList(vo, pagingVo);
			jsonObject.put("list", list);
			jsonObject.put("auditLogVo", vo);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	@RequestMapping("/prcssBlockLogExcel")
	public CmmnExcelService prcssBlockLogExcel(AuditLogVo vo, PagingVo pagingVo,HttpServletRequest request, HttpServletResponse response, ModelMap model,
    		@RequestParam Map<String, String> params) throws Exception {
		System.out.println("params"+params.toString());
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// 페이징
		pagingVo.setCurrentPage(vo.getCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(logMapper.countUserLogListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		List<Map<String, Object>> list = logMapper.prcssBlockLogListExcel(vo);
		
		String[] head ={"번호","사지방번호","프로세스명","PC관리번호","IP","아이디","차단시간"};
		String[] column ={"rownum","org_nm","prcssname","hostname","ipaddr","user_id","insert_dt"};
		jsonObject.put("header", head);		  // Excel 상단
		jsonObject.put("column", column);		  // Excel 상단
		jsonObject.put("excelName","프로세스차단로그");    // Excel 파일명
		jsonObject.put("list", list);          // Excel Data
		
		//model.addAttribute("header", head);
		//model.addAttribute("excelName","고고고");
		//model.addAttribute("list", list);
		model.addAttribute("data", jsonObject);
		return new CmmnExcelService();
    }
	
	/**
	 * 프로세스 차단 로그
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updateCheckLog")
	public String updateCheckLog(Model model ,@RequestParam Map<String, Object> params ) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		//System.out.println(jsonArray.toString().replaceAll("\"", ""));
		model.addAttribute("oList", jsonArray);

		return "/auditLog/updateCheckLog";
	}
	
	
	
	/**
	 * 사용자 접속 현황
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userLog")
	public String userLogPage(Model model) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			e.printStackTrace();
			// FAIL_GET_LIST
		}
		//System.out.println(jsonArray.toString().replaceAll("\"", ""));
		model.addAttribute("oList", jsonArray);
		
		return "/auditLog/userConectLog";
	}


	@ResponseBody
	@RequestMapping("pcMngrList.proc")
	public Map<String, Object> listProc(PcMangrVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {

		System.out.println("===========" + vo.getOrg_seq());
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// 페이징
		pagingVo.setCurrentPage(vo.getPcListInfoCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		///int cnt = Integer.parseInt(pcmapper.countPcListInfo(vo) + "");
		//pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			//List<PcMangrVo> gbList = pcService.pcMangrList(vo, pagingVo);
			//jsonObject.put("list", gbList);
			jsonObject.put("pcVo", vo);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	@ResponseBody
	@RequestMapping("detailPolicy.proc")
	public HashMap<String, Object> detailPolicy(@RequestParam HashMap<String, Object> params, HttpSession session) {
		
		HashMap<String, Object> jsonObject = new HashMap<String, Object>();
		//List<Map<String,Object>> updt = new ArrayList<Map<String,Object>>();
		//List<Map<String,Object>> program = new ArrayList<Map<String,Object>>();
		
		try {
			jsonObject.put("udpt", logService.udptList(params));
			jsonObject.put("program", logService.programList(params));
			jsonObject.put("device", logService.deviceList(params));
			jsonObject.put("nxss", logService.nxssList(params));
			jsonObject.put("firewall", logService.firewallList(params));
			//jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
}