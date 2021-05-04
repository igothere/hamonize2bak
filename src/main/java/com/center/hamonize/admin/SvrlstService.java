package com.center.hamonize.admin;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import com.center.hamonize.admin.mapper.ISvrlstMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SvrlstService {

	@Autowired
	private ISvrlstMapper svrlstMapper;

	public List<SvrlstVo> getSvrlstList(SvrlstVo vo) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("mngeVo", vo);

		List<SvrlstVo> retData = svrlstMapper.getSvrlstList(paramMap);

		return retData;
	}
	
	

	@Transactional
	public void svrlstInsert(SvrlstVo nVo) throws Exception {
		svrlstMapper.svrlstInsert(nVo);
	}
	

}