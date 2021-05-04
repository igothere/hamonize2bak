package com.center.hamonize.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mapper.IMainMapper;
import com.mapper.ITchnlgyMapper;
import com.model.TchnlgyVo;
import com.service.MainService;

@Controller
public class MainController {
	@Autowired
	private IMainMapper mMapper;
	
	@Autowired
	private MainService mService;
	
	@Autowired
	private ITchnlgyMapper tchnlgyMapper;

	@RequestMapping("/")
	public String mainPage() throws Exception {
		return "redirect:/login/login";
//		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String homePage() throws Exception {
		return "/mntrng/pcControl";
//		return "/home";
	}
	
	@RequestMapping("/main")
	public String mainMap() throws Exception {
		return "/main/mainMap";
//		return "/home";
	}

	
	@RequestMapping("/main_server")
	public String mainMapServer() throws Exception {
		return "/main/mainMap_server";
	}

	/**
	@ResponseBody
	@RequestMapping(value = "/sidoCount")
	public Map<String, Object> sidoCount(Model model,@RequestParam Map<String, Object> params ) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String,Object>> sList = mMapper.sidoCount();
		List<Map<String,Object>> tList = mMapper.tcngSidoCount();
		//JSONArray ja = new JSONArray();
		
		for(int i=0;i<sList.size();i++) {
			JSONObject data = new JSONObject();
			data.put("sido", sList.get(i).get("sido"));
			data.put("count", sList.get(i).get("count"));
			 ja.add(data);
			 System.out.println("sidoCount================="+data.toString());
		}
		JSONArray ja2 = new JSONArray();
		for(int i=0;i<tList.size();i++) {
			JSONObject data = new JSONObject();
			data.put("sido", sList.get(i).get("sido"));
			data.put("count", sList.get(i).get("count"));
			 ja2.add(data);
			 System.out.println("tcngCount================="+data.toString());
		} 
		dataMap.put("sidoCount", sList);
		dataMap.put("tcngSidoCount", tList);
		return dataMap;
	}
*/
	
	
	@ResponseBody
	@RequestMapping(value = "/sidoCount")
	public Map<String, Object> sidoCount(Model model,@RequestParam Map<String, Object> params ) {
		//개방상용 구분
		int wCount = mMapper.wCount();
		int hCount = mMapper.hCount();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String,Object>> sList = mMapper.sidoCount();
		List<Map<String,Object>> tList = mMapper.pcTotalSidoCount();
		TchnlgyVo vo  = new TchnlgyVo();
		vo = tchnlgyMapper.countMngrListInfo(vo);
		System.out.println("vo ====="+vo.getTbl_cnt());
		//사용중인pc시도별
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		//Map<String, Object> dataParams = new HashMap<String, Object>();
		//params.put("org_seq", Integer.parseInt(params.get("org_seq").toString()));
		int on = 0;
		int off = 0;
		try {
			//list =  mService.pcListInfo(params);
			list = mMapper.pcUseSidoCount();
			for(int i = 0; i < list.size();i++) {
				//System.out.println("list===="+list.get(i));
				resultList.add((Map<String, Object>) list.get(i));
				if(list.get(i).get("sgb_pc_status")=="true")
					on++;
				else
					off++;		
			}
			
		} catch (Exception e) {
			//jsonObject.put("influxData", "ssssssss");
			e.printStackTrace();
		}
		//JSONArray ja = new JSONArray();
		/**
		for(int i=0;i<sList.size();i++) {
			JSONObject data = new JSONObject();
			data.put("sido", sList.get(i).get("sido"));
			data.put("pc_cnt", sList.get(i).get("pc_cnt"));
			data.put("t_sum", sList.get(i).get("t_sum"));
			 ja.add(data);
			 System.out.println("sidoCount================="+data.toString());
		}
		*/
		dataMap.put("sidoCount", sList);
		//dataMap.put("sido", resultList);
		dataMap.put("pcOn", on);
		dataMap.put("pcOff", off);
		dataMap.put("useList", list);
		dataMap.put("totalList", tList);
		dataMap.put("tchnlgyCount", vo);
		//개방상용 구분
		dataMap.put("wCount", wCount);
		dataMap.put("hCount", hCount);
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/serversidoCount")
	public Map<String, Object> serversidoCount(Model model,@RequestParam Map<String, Object> params ) {
		//개방상용 구분
		int wCount = mMapper.wCount();
		int hCount = mMapper.hCount();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String,Object>> sList = mMapper.serversidoCount();
		List<Map<String,Object>> tList = mMapper.serverTotalSidoCount();
		TchnlgyVo vo  = new TchnlgyVo();
		vo = tchnlgyMapper.countMngrListInfo(vo);
		System.out.println("vo ====="+vo.getTbl_cnt());
		//사용중인pc시도별
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		//Map<String, Object> dataParams = new HashMap<String, Object>();
		//params.put("org_seq", Integer.parseInt(params.get("org_seq").toString()));
		int on = 0;
		int off = 0;
		try {
			//list =  mService.pcListInfo(params);
			list = mMapper.serverUseSidoCount();
			for(int i = 0; i < list.size();i++) {
				//System.out.println("list===="+list.get(i));
				resultList.add((Map<String, Object>) list.get(i));
				if(list.get(i).get("sgb_pc_status")=="true")
					on++;
				else
					off++;		
			}
			
		} catch (Exception e) {
			//jsonObject.put("influxData", "ssssssss");
			e.printStackTrace();
		}
		//JSONArray ja = new JSONArray();
		/**
		for(int i=0;i<sList.size();i++) {
			JSONObject data = new JSONObject();
			data.put("sido", sList.get(i).get("sido"));
			data.put("pc_cnt", sList.get(i).get("pc_cnt"));
			data.put("t_sum", sList.get(i).get("t_sum"));
			 ja.add(data);
			 System.out.println("sidoCount================="+data.toString());
		}
		*/
		dataMap.put("sidoCount", sList);
		//dataMap.put("sido", resultList);
		dataMap.put("pcOn", on);
		dataMap.put("pcOff", off);
		dataMap.put("useList", list);
		dataMap.put("totalList", tList);
		dataMap.put("tchnlgyCount", vo);
		//개방상용 구분
		dataMap.put("wCount", wCount);
		dataMap.put("hCount", hCount);
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/gugunCount")
	public JSONArray gugunCount(Model model,@RequestParam Map<String, Object> params ) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String,Object>> sList = mMapper.gugunCount(params);
		JSONArray ja = new JSONArray();
		
		for(int i=0;i<sList.size();i++) {
			JSONObject data = new JSONObject();
			data.put("gugun", sList.get(i).get("gugun"));
			data.put("pc_cnt", sList.get(i).get("pc_cnt"));
			data.put("t_sum", sList.get(i).get("t_sum"));
			 ja.add(data);
		}
		return ja;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/pcList")
	public Map<String,Object> pcList(Model model,@RequestParam Map<String, Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataParams = new HashMap<String, Object>();
		//params.put("org_seq", Integer.parseInt(params.get("org_seq").toString()));
		int on = 0;
		int off = 0;
		try {
			list =  mService.pcListInfo(params);
			for(int i = 0; i < list.size();i++) {
				resultList.add((Map<String, Object>) list.get(i));
				
				if(list.get(i).get("sgb_pc_status")=="true")
					on++;
				else
					off++;		
			}
			//influxListData = mntrgService.influxInfo();
			
		} catch (Exception e) {
			//jsonObject.put("influxData", "ssssssss");
			e.printStackTrace();
		}
		dataParams.put("sido", resultList);
		dataParams.put("pcOn", on);
		dataParams.put("pcOff", off);
		//model.addAttribute("pcList",list);
		//model.addAttribute("on",on);
		//model.addAttribute("off",off);
		return dataParams;
	}

	@ResponseBody
	@RequestMapping(value = "/serverList")
	public Map<String,Object> serverList(Model model,@RequestParam Map<String, Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataParams = new HashMap<String, Object>();
		//params.put("org_seq", Integer.parseInt(params.get("org_seq").toString()));
		int on = 0;
		int off = 0;
		try {
			list =  mService.serverListInfo(params);
			for(int i = 0; i < list.size();i++) {
				resultList.add((Map<String, Object>) list.get(i));
				
				if(list.get(i).get("sgb_pc_status")=="true")
					on++;
				else
					off++;		
			}
			//influxListData = mntrgService.influxInfo();
			
		} catch (Exception e) {
			//jsonObject.put("influxData", "ssssssss");
			e.printStackTrace();
		}
		dataParams.put("sido", resultList);
		dataParams.put("pcOn", on);
		dataParams.put("pcOff", off);
		//model.addAttribute("pcList",list);
		//model.addAttribute("on",on);
		//model.addAttribute("off",off);
		return dataParams;
	}
	
	@ResponseBody
	@RequestMapping(value = "/inetLogCount")
	public Map<String, Object> inetLogCount(Model model,@RequestParam Map<String, Object> params ) {
		System.out.println("offset===="+params.get("offset"));
		params.put("offset", Integer.parseInt(params.get("offset").toString()));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String,Object>> cList = mMapper.inetLogConnect(params);
		List<Map<String,Object>> iList = mMapper.inetLogIlligal(params);
		
		dataMap.put("conList", cList);
		dataMap.put("illList", iList);
		return dataMap;
	}
	
	/**
	@throws UnsupportedEncodingException 
	 * @ResponseBody
	@RequestMapping(value = "/gugunCount")
	public Map<String, Object> gugunCount(Model model,@RequestParam Map<String, Object> params ) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String,Object>> sList = mMapper.gugunCount(params);
		List<Map<String,Object>> tList = mMapper.tcngGugunCount(params);
		
		dataMap.put("gugunCount", sList);
		dataMap.put("tcngGugunCount", tList);
		return dataMap;
	}
	
	*/
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/tcngCount") public JSONArray tcngCount(Model
	 * model,@RequestParam Map<String, Object> params ) { List<Map<String,Object>>
	 * sList = mMapper.tcngGugunCount(); JSONObject data; JSONArray ja = new
	 * JSONArray(); for(int i=0;i<sList.size();i++) { data = new JSONObject();
	 * data.put("sido", sList.get(i).get("sido")); data.put("count",
	 * sList.get(i).get("count")); //data.put("tcng_status",
	 * sList.get(i).get("tcng_status")); ja.add(data);
	 * System.out.println("tcngCount================="+data.toString()); } return
	 * ja; }
	 */
	
	/**
	 * 
	 * @throws UnsupportedEncodingException
	vworld API에서 좌표 받아서 DB에 입력
	@ResponseBody
	@RequestMapping(value = "/sigun")
	public void aaa() throws UnsupportedEncodingException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String,Object>> cList = mMapper.sigun();
		for(int i = 0; i < cList.size();i++) {
			System.out.println(cList.get(i).get("sido")+" "+cList.get(i).get("gugun"));
			String sigun = cList.get(i).get("sido").toString().trim()+" "+cList.get(i).get("gugun").toString().trim();
			sigun = URLEncoder.encode(sigun, "UTF-8");
			get("http://api.vworld.kr/req/address?service=address&request=getcoord&version=2.0&crs=epsg:4326&address="+sigun+"&refine=true&simple=false&format=xml&type=road&key=C8EFA90F-AD07-3F41-A85B-6F20D7980D4B",cList.get(i).get("sido").toString(),cList.get(i).get("gugun").toString());
		}
	}
	
	public void get(String requestURL,String sido,String gugun) {
		try {
			//requestURL = URLEncoder.encode(requestURL, "UTF-8");

			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
			//getRequest.addHeader("key", "C8EFA90F-AD07-3F41-A85B-6F20D7980D4B"); //KEY 입력

			HttpResponse response = client.execute(getRequest);

			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("success");
				ResponseHandler<String> handler = new BasicResponseHandler();
				HttpEntity entity = response.getEntity();
				InputStream is = null;
				is = entity.getContent();
				Document doc = parseXML(is);
				NodeList descNodes = doc.getElementsByTagName("point");
				Map<String,Object> cList = new HashMap<String,Object>();
				String xpoint = "";
				String ypoint = "";
				for(int i=0; i<descNodes.getLength();i++){
					
					for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행

						if(node.getNodeName().equals("x")){
							System.out.println(node.getTextContent());
							xpoint = node.getTextContent();
							//cList.put("xpoint", node.getTextContent());
						}else if(node.getNodeName().equals("y")){
							System.out.println(node.getTextContent());
							 ypoint = node.getTextContent();
							//cList.put("ypoint", node.getTextContent());
						}

					}

				}
				cList.put("xpoint", xpoint);
				cList.put("ypoint", ypoint);
				cList.put("sido", sido.trim());
				cList.put("gugun", gugun.trim());
				int bb = mMapper.updtxy(cList);
				System.out.println("bb=========="+bb);
				//String body = handler.handleResponse(response);
				//System.out.println(body);
			} else {
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
			}

		} catch (Exception e){
			System.err.println(e.toString());
		}
	}
	private Document parseXML(InputStream stream) throws Exception{

		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;

		try{

			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

			doc = objDocumentBuilder.parse(stream);

		}catch(Exception ex){
			throw ex;
		}       

		return doc;
	}
	 */
}
