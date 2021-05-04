package com.center.hamonize.admin.mapper;

import java.util.HashMap;
import java.util.List;

import com.center.hamonize.admin.SvrlstVo;


public interface ISvrlstMapper {

	public List<SvrlstVo> getSvrlstList(HashMap<String, Object> map);
	
	public int countSvrlstListInfo(SvrlstVo vo);
	
	public int svrlstInsert(SvrlstVo vo);
	
	public List<SvrlstVo> getSvrlstDataList();
	
	public int svrlstDelete(SvrlstVo vo);
	
	
}
