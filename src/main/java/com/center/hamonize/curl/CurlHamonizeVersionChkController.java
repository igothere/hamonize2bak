package com.center.hamonize.curl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.IHamonizeVersionChkMapper;
import com.model.hamonizeVersionChkVo;

@RestController
@RequestMapping("/hmsvc")
public class CurlHamonizeVersionChkController {


	@Autowired
	IHamonizeVersionChkMapper hamonizeVersionChkMapper;
	

	@RequestMapping("/version")
	public void version(@RequestBody String valLoad ) throws Exception {
		JSONParser jsonParser = new JSONParser();
       JSONObject jsonObj = (JSONObject) jsonParser.parse(valLoad);
       JSONArray hmdArray = (JSONArray) jsonObj.get("versionchk");
       hamonizeVersionChkVo  hamonizeVersionChkVo = new hamonizeVersionChkVo();
        
        
		for(int i=0 ; i<hmdArray.size() ; i++){
			JSONObject tempObj = (JSONObject) hmdArray.get(i);
			
			System.out.println("tempObj.get(\"state\").toString()===="+tempObj.get("state").toString());
			
			hamonizeVersionChkVo.setDebname(tempObj.get("debname").toString());
			hamonizeVersionChkVo.setDebversion(tempObj.get("debver").toString());
			hamonizeVersionChkVo.setDebstatus(tempObj.get("state").toString());
			hamonizeVersionChkVo.setPcuuid((tempObj.get("uuid").toString()));
		}
		
		hamonizeVersionChkVo  chkVo = new hamonizeVersionChkVo();
		chkVo = hamonizeVersionChkMapper.getHamonizeAgentInfoOnPc(hamonizeVersionChkVo);
		
		if(chkVo == null ) {
			// insert 
			hamonizeVersionChkMapper.setHamonizeAgentIfnoOnPc(hamonizeVersionChkVo);
		}else {
			// update 
			if( !hamonizeVersionChkVo.getDebversion().equals( chkVo.getDebversion() )  || 
					!hamonizeVersionChkVo.getDebstatus().contentEquals( chkVo.getDebstatus())	) {
				System.out.println("hamonizeVersionChkVo is not equals");
				hamonizeVersionChkMapper.updateHamonizeAgentIfnoOnPc(hamonizeVersionChkVo);
			}else {
				System.out.println(" hamonizeVersionChkVo is quals");
			}
			
		}
		
	}

	

}