package com.center.hamonize.policymngr;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mapper.IBlockingNxssMngrMapper;
import com.mapper.IGroupMapper;
import com.mapper.IHmProgrmApplcMapper;
import com.mapper.IHmProgrmUpdtMapper;
import com.mapper.IHmSecurityMapper;
import com.mapper.IHmprogramMapper;
import com.mapper.IIpManagementMapper;
import com.model.AllowIpInfoVo;
import com.model.BlockingNxssInfoVo;
import com.model.GroupVo;
import com.model.HmProgrmUpdtVo;
import com.model.HmSecurityVo;
import com.model.HmprogramVo;
import com.paging.PagingUtil;
import com.paging.PagingVo;
import com.service.BlockingNxssMngrService;
import com.service.GroupService;
import com.service.HmProgrmUpdtService;
import com.service.HmSecurityService;
import com.service.HmprogramService;
import com.service.IpManagementService;
import com.util.Constant;


@Controller
@RequestMapping("/gplcs")
public class PolicyMngrController {

	@Autowired
	private GroupService gService;
	
	@Autowired
	private IGroupMapper groupMapper;
	
	@Autowired
	private HmprogramService hmprogramService;

	@Autowired
	private IHmprogramMapper hmprogramMapper;

	@Autowired
	private HmProgrmUpdtService hmProgrmUpdtService;
	
	@Autowired
	private IHmProgrmUpdtMapper hmProgrmUpdtMapper;

	@Autowired
	private HmSecurityService hmSecurityService;
	
	@Autowired
	private IHmSecurityMapper hmSecurityMapper;
	
	@Autowired
	private IHmProgrmApplcMapper hmProgrmApplcMapper;

	
	@Autowired
	private IpManagementService ipManagementService;
	
	@Autowired
	private IIpManagementMapper ipManagementMapper;

	@Autowired
	private BlockingNxssMngrService blockingNxssMngrService;
	
	@Autowired
	private IBlockingNxssMngrMapper blockingNxssMngrMapper;
	
	

	/**
	 * ????????? IP ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ipManagement")
	public String ipManagementPage(Model model) {
		//log.info(" -- ctr:ipManagementPage");
//		JSONArray groupList = null;
//
//		try {
//			GroupVo gvo = new GroupVo();
//			groupList = gService.groupList(gvo);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		return "/secrty/ipManagement";
	}
	
	
	/**
	 * ?????????ip?????? ?????? ?????????
	 * @param vo
	 * @param pagingVo
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("ipManagementList.proc")
	public Map<String, Object> ipManagementList(AllowIpInfoVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// ?????????
		pagingVo.setCurrentPage(vo.getMngeListInfoCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(ipManagementMapper.countMngrListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			List<AllowIpInfoVo> gbList = ipManagementService.ipManagementList(vo, pagingVo);
			jsonObject.put("list", gbList);
			jsonObject.put("mngeVo", vo);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	/**
	 * ????????? IP ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipManagement.proc", method=RequestMethod.POST)
	public Map<String, Object> ipManagementProc(HttpSession session, AllowIpInfoVo vo) throws Exception {
		////log.info(" -- ctr:ipManagementProc");
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			ipManagementService.insertIpInfo(vo);
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
	
	/**
	 * ????????? IP ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteIpManagement.proc", method=RequestMethod.POST)
	public Map<String, Object> deleteIpManagementProc(HttpSession session, AllowIpInfoVo vo) throws Exception {
		//log.info(" -- ctr:deleteIpManagementProc - vo : " + vo);
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			ipManagementService.deleteIpInfo(vo);
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
	
	
	//====================================================
	
	
	/**
	 * ?????? ????????? ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/blockingNxssMngr")
	public String blockingNxssPage(Model model) {

		return "/secrty/blockingNxssMngr";
	}
	
	
	/**
	 * ????????????????????? ?????? ?????????
	 * @param vo
	 * @param pagingVo
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("blockingNxssMngrList.proc")
	public Map<String, Object> blockingNxssMngrListProc(BlockingNxssInfoVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// ?????????
		pagingVo.setCurrentPage(vo.getMngeListInfoCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(blockingNxssMngrMapper.countMngrListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			List<BlockingNxssInfoVo> gbList = blockingNxssMngrService.blockingList(vo, pagingVo);
			jsonObject.put("list", gbList);
			jsonObject.put("mngeVo", vo);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	/**
	 * ??????????????? ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBlockingNxss.proc", method=RequestMethod.POST)
	public Map<String, Object> addBlockingNxssProc(HttpSession session, BlockingNxssInfoVo vo) throws Exception {
		//log.info(" -- ctr:addBlockingNxssProc");
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			blockingNxssMngrService.insertDomainInfo(vo);
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
	
	
	/**
	 * ??????????????? ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteBlockingNxss.proc", method=RequestMethod.POST)
	public Map<String, Object> deleteBlockingNxssProc(HttpSession session, BlockingNxssInfoVo vo) throws Exception {
		//log.info(" -- ctr:deleteBlockingNxssProc - vo : " + vo);
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			blockingNxssMngrService.deleteDomainInfo(vo);
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
	
	
	/**
	 * ????????? ????????? ????????? ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateBlockingNxssDomain.proc", method=RequestMethod.POST)
	public Map<String, Object> updateBlockingNxssDomainProc(HttpSession session, BlockingNxssInfoVo vo) throws Exception {
		//log.info(" -- ctr:updateBlockingNxssDomainProc");
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			blockingNxssMngrService.updateForwardDomainInfo(vo);
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
	
	
	/**
	 * ????????????????????? ????????? ????????? ?????????
	 * @param vo
	 * @param pagingVo
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getForwardDomain.proc")
	public Map<String, Object> getForwardDomain(HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		BlockingNxssInfoVo vo = new BlockingNxssInfoVo();
		
		try {
			vo = blockingNxssMngrMapper.getForwardDomain();
			System.out.println("1=========="+vo.getSma_domain());
			System.out.println("2=========="+vo.getSma_info());
			jsonObject.put("forwardDomain", vo.getSma_domain());
			jsonObject.put("forwardNotice", vo.getSma_info());

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	
	//====================================================
	
	
	/**
	 * ???????????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/programManagement")
	public String programManagementPage(Model model) {
		//log.info(" -- ctr:programManagementPage");
		JSONArray groupList = null;

		try {
			GroupVo gvo = new GroupVo();
			gvo.setGroup_gubun("group");
			groupList = gService.groupList(gvo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("gList", groupList);

		return "/secrty/programManagement";
	}
	
	
	/**
	 * ???????????? ????????? ?????????
	 * @param vo
	 * @param pagingVo
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("programManagement.proc")
	public Map<String, Object> programManagementProc(HmprogramVo vo, PagingVo pagingVo, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// ?????????
		pagingVo.setCurrentPage(vo.getHmProgramListInfoCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(hmprogramMapper.countHmPcProgramListInfo(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			List<HmprogramVo> gbList = hmprogramService.soliderList(vo, pagingVo);
			
			String programManagementList = hmprogramService.programManagementList(vo);
			
			GroupVo gvo = new GroupVo();
			System.out.println("vo.getSelectOrgUpperCode()==="+ vo.getSelectOrgUpperCode());
			gvo.setOrgupcodename( vo.getSelectOrgUpperCode() );
			System.out.println( "gvo=== "+  gvo);
			
			List<GroupVo> sgbGroup = groupMapper.groupSgbList(gvo);
			
			jsonObject.put("list", gbList);
			jsonObject.put("programManagementList", programManagementList);
			jsonObject.put("sgblist", sgbGroup);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}

	
	/**
	 * ???????????? ?????? ??????
	 * @param session
	 * @param hVo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="programManagementInsert.proc", method=RequestMethod.POST)
	public Map<String, Object> programManagementInsertProc(HttpSession session, HmprogramVo hVo) throws Exception {

		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			hmprogramService.programManagementInsert(hVo);
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		
		
		return jsonObject;
	}

	
	// ======================================================================

	/**
	 * ???????????? ?????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateManagement")
	public String updateManagementPage(Model model) {
		//log.info(" -- ctr:updateManagementPage");
		JSONArray groupList = null;

		try {
			GroupVo gvo = new GroupVo();
			gvo.setGroup_gubun("group");
			groupList = gService.groupList(gvo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("gList", groupList);

		return "/secrty/updateManagement";
	}
	

	/**
	 * ???????????? ???????????? ????????? ?????????
	 * @param vo
	 * @param pagingVo
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateManagement.proc")
	public Map<String, Object> updateManagementProc(HmProgrmUpdtVo vo, PagingVo pagingVo, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// ?????????
		pagingVo.setCurrentPage(vo.getHmProgrmUpdtListInfoCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(hmProgrmUpdtMapper.countHmProgrmUpdt(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			
			List<HmProgrmUpdtVo> gbList = hmProgrmUpdtService.ListHmProgrmUpdt(vo, pagingVo);
			
			String updateManagementList = hmProgrmUpdtService.selectHmProgrmUpdt(vo);

			GroupVo gvo = new GroupVo();
			System.out.println("vo.getSelectOrgUpperCode()==="+ vo.getSelectOrgUpperCode());
			gvo.setOrgupcodename( vo.getSelectOrgUpperCode() );
			System.out.println( "gvo=== "+  gvo);
			
			List<GroupVo> sgbGroup = groupMapper.groupSgbList(gvo);
			
			jsonObject.put("list", gbList);
			jsonObject.put("updateManagementList", updateManagementList);
			jsonObject.put("sgblist", sgbGroup);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}

	

	/**
	 * ???????????? ???????????? ?????? ??????
	 * @param session
	 * @param hVo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="updateManagementInsert.proc", method=RequestMethod.POST)
	public Map<String, Object> updateManagementInsertProc(HttpSession session, HmProgrmUpdtVo hVo) throws Exception {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		System.out.println("hVo======"+ hVo);
		try {
			hmProgrmUpdtService.InsertHmProgrmUpdt(hVo);
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		
		
		return jsonObject;
	}
	
// ======================================================================
	
	
	/**
	 * ???????????? ?????????
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/securityManagement")
	public String securityManagementPage(Model model) {
		////log.info(" -- ctr:securityManagementPage");
		JSONArray groupList = null;

		try {
			GroupVo gvo = new GroupVo();
			gvo.setGroup_gubun("group");
			groupList = gService.groupList(gvo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("gList", groupList);

		return "/secrty/securityManagement";
	}
	
	
	
	/**
	 * ???????????? ????????? ?????????
	 * @param vo
	 * @param pagingVo
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("securityManagement.proc")
	public Map<String, Object> securityManagementProc(HmSecurityVo vo, PagingVo pagingVo, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> jsonObject = new HashMap<String, Object>();

		// ?????????
		pagingVo.setCurrentPage(vo.getHmSecurityCurrentPage());
		pagingVo = PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, pagingVo);

		int cnt = Integer.parseInt(hmSecurityMapper.countHmSecurity(vo) + "");
		pagingVo.setTotalRecordSize(cnt);
		pagingVo = PagingUtil.setPaging(pagingVo);

		try {
			
			List<HmSecurityVo> gbList = hmSecurityService.ListHmSecurity(vo, pagingVo);
			
			String securityList = hmSecurityService.selectHmSecurity(vo);

			GroupVo gvo = new GroupVo();
			System.out.println("vo.getSelectOrgUpperCode()==="+ vo.getSelectOrgUpperCode());
			gvo.setOrgupcodename( vo.getSelectOrgUpperCode() );
			System.out.println( "gvo=== "+  gvo);
			
			List<GroupVo> sgbGroup = groupMapper.groupSgbList(gvo);
			
			jsonObject.put("list", gbList);
			jsonObject.put("securityList", securityList);
			jsonObject.put("sgblist", sgbGroup);
			jsonObject.put("pagingVo", pagingVo);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	
	
	/**
	 * ???????????? ???????????? ?????? ??????
	 * @param session
	 * @param hVo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="securityInsert.proc", method=RequestMethod.POST)
	public Map<String, Object> securityInsertProc(HttpSession session, HmSecurityVo hVo) throws Exception {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		System.out.println("hVo======"+ hVo);
		try {
			hmSecurityService.InsertHmSecurity(hVo);
			
			
			// ???????????? ??? ???????????? ?????? curl
			
			
			
			jsonObject.put("msg", Constant.Board.SUCCESS_GROUP_BOARD);
			jsonObject.put("success", true);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (DataIntegrityViolationException dive ){
			dive.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("msg", Constant.Board.SUCCESS_FAIL);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
}

