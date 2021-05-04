package com.center.hamonize.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.center.hamonize.answer.AnswersService;
import com.center.hamonize.cmmn.CmmnMap;
import com.center.hamonize.cmmn.service.CmmnService;
import com.center.hamonize.login.service.SecurityMember;
import com.center.hamonize.question.QuestionsService;
import com.center.hamonize.vote.VoteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QuestionsService qs;

	@Autowired
	VoteService vs;

	@Autowired
	AnswersService as;

	@Autowired
	CmmnService cs;
	
	@Autowired
	BoardService bs;
	
	@RequestMapping(value = "/{boardid}/list")
	public String getList(@PathVariable("boardid") String boardid,@RequestParam Map<String, String> params, Model model, Board board,
			@AuthenticationPrincipal SecurityMember user, @PageableDefault Pageable pageable) throws Exception {
		CmmnMap param = new CmmnMap();
		param.putAll(params);
		param.put("boardid", boardid);
		board.setSection(boardid);

		logger.info("----------excel param-----------------------");
		logger.debug("");
		logger.debug(param.toString());
		logger.debug("");
		logger.debug("----------excel param-----------------------");
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(param.getInt("pageNo") > 0 ? param.getInt("pageNo") : 1); // 현재 페이지 번호
		paginationInfo.setRecordCountPerPage(5); // 한 페이지에 게시되는 게시물 건수
		paginationInfo.setPageSize(5); // 페이징 리스트의 사이즈

		int firstRecordIndex = paginationInfo.getFirstRecordIndex();
		int recordCountPerPage = paginationInfo.getRecordCountPerPage();
		board.setFirstRecordIndex(firstRecordIndex);
		board.setRecordCountPerPage(recordCountPerPage);
		board.setSection(boardid);
		List<Board> list = bs.getList(board);

		int listCount = bs.getListCount(board);
		paginationInfo.setTotalRecordCount(listCount); // 전체 게시물 건 수
		model.addAttribute("list", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("vo", param);
		model.addAttribute("boardinfo", cs.selectObject("boardinfo", param));
		return "/board/list";
	}
	
	@RequestMapping("/{boardid}/view/{boardno}")
	public String view(@PathVariable("boardid") String boardid,@PathVariable("boardno") int boardno, Model model,
			@AuthenticationPrincipal SecurityMember user, HttpSession httpSession) throws Exception {
		// List<Tags> tagList = qs.tagList();
		CmmnMap param = new CmmnMap();


		Board vo = new Board();
		vo.setSection(boardid);
		vo = bs.getView(boardno);

		model.addAttribute("result", vo);

		if (httpSession.getAttribute("userSession") != null)
			model.addAttribute("user", httpSession.getAttribute("userSession"));
		return "/board/view";
	}

}
