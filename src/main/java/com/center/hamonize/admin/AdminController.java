package com.center.hamonize.admin;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.center.hamonize.admin.mapper.ISvrlstMapper;
import com.center.hamonize.cmmn.CmmnMap;
import com.center.hamonize.org.OrgService;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminservice;
	
	@Autowired
	private OrgService oService;

	@Autowired
	private SvrlstService svrlstService;
	
	@Autowired
	private ISvrlstMapper svrlstMapper;
	
	//서버 관리자
	@RequestMapping("/serverlist")
	public String serverlist(HttpSession session, Model model,Admin vo) throws Exception{
		return "/svrlst/list";
	}
	
	@ResponseBody
	@RequestMapping("serverlist.proc")
	public Map<String, Object> serverlistProc(@RequestParam Map<String, String> params, SvrlstVo vo, @PageableDefault Pageable pageable, HttpSession session, HttpServletRequest request) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		CmmnMap param = new CmmnMap();
		param.putAll(params);
		// 페이징
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(param.getInt("pageNo") > 0 ? param.getInt("pageNo") : 1); // 현재 페이지 번호
		paginationInfo.setRecordCountPerPage(5); // 한 페이지에 게시되는 게시물 건수
		paginationInfo.setPageSize(10); // 페이징 리스트의 사이즈

		int firstRecordIndex = paginationInfo.getFirstRecordIndex();
		int recordCountPerPage = paginationInfo.getRecordCountPerPage();
		vo.setFirstRecordIndex(firstRecordIndex);
		vo.setRecordCountPerPage(recordCountPerPage);

		try {
			List<SvrlstVo> gbList = svrlstService.getSvrlstList(vo);
			jsonObject.put("list", gbList);

			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject.put("success", false);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	@RequestMapping("serverlistDelete.proc")
	public String serverlistDelete(SvrlstVo vo, PagingVo pagingVo, HttpSession session, HttpServletRequest request) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		svrlstMapper.svrlstDelete(vo);
		
		return "/svrlst/list";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="serverlistInsert.proc")
	public Map<String, Object> serverlistInsert(HttpSession session, SvrlstVo nVo) throws Exception {
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			svrlstService.svrlstInsert(nVo);
			
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
	
	
	//센터 관리자
	@RequestMapping("/list")
	public String list(HttpSession session, Model model,Admin vo) throws Exception{
		List<Admin> list = new ArrayList<Admin>();

			// 페이징
			vo.setCurrentPage(vo.getAdminListInfoCurrentPage());
			vo = (Admin) PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, vo);
			int cnt = Integer.parseInt(adminservice.countListInfo(vo) + "");
			vo.setTotalRecordSize(cnt);
			vo = (Admin) PagingUtil.setPaging(vo);
			list = adminservice.adminList(vo);
			
		model.addAttribute("keyWord",vo.getKeyWord());
		model.addAttribute("txtSearch",vo.getTxtSearch());
		model.addAttribute("aList", list);
		model.addAttribute("paging", vo);
		
		return "/admin/list";
	}
	@RequestMapping("/view")
	public String view(Model model,Admin vo) throws Exception{
		
		if(vo.getUser_id() != null) {
			Admin avo = adminservice.adminView(vo);
			//avo.setPass_wd(St);
			model.addAttribute("result",avo);
		}
		
		return "/admin/view";
		
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public int save(Model model,Admin vo) throws NoSuchAlgorithmException {
		int result=0;
		vo.setPass_wd(StringUtil.EncodingSHA256(vo.getPass_wd()));
		result = adminservice.adminSave(vo);
		return result;
		
	}
	
	@RequestMapping("/modify")
	@ResponseBody
	public int modify(Model model,Admin vo) throws Exception {
		int result=0;
		if(vo.getPass_wd() != null || vo.getPass_wd() != "") {
		vo.setPass_wd(StringUtil.EncodingSHA256(vo.getPass_wd()));
		}
		
		result = adminservice.adminModify(vo);
		return result;
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public int delete(Model model,Admin vo) throws Exception{
		int result=0;
		result = adminservice.adminDelete(vo);
		return result;
		
	}
	
	@RequestMapping("/idDuplCheck")
	@ResponseBody
	public int idDuplCheck(Model model,Admin vo) throws Exception{
		int result = 0;
		result = adminservice.adminIdCheck(vo);
		return result;
		
	}

}
