package com.center.hamonize.curl;

import java.io.BufferedReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.IBackupCycleMapper;
import com.mapper.IGetAgentJobMapper;
import com.mapper.IHmProgrmUpdtMapper;
import com.mapper.IHmSecurityMapper;
import com.mapper.IHmprogramMapper;
import com.mapper.IOrgMapper;
import com.mapper.IPcMangrMapper;
import com.model.BackupCycleVo;
import com.model.BlockingNxssInfoVo;
import com.model.GetAgentJobVo;
import com.model.HmProgrmUpdtVo;
import com.model.HmSecurityVo;
import com.model.HmprogramVo;
import com.model.OrgVo;
import com.model.PcMangrVo;
import com.util.AdLdapUtils;

@RestController
@RequestMapping("/hmsvc")
public class CurlController {

	
	@Autowired
	private IPcMangrMapper pcMangrMapper;

	@Autowired
	private IOrgMapper orgMapper;
	
	@Autowired
	private IGetAgentJobMapper agentJobMapper;

	@Autowired
	private IHmprogramMapper hmprogramMapper;
	
	@Autowired
	private IHmProgrmUpdtMapper hmProgrmUpdtMapper;

	@Autowired
	private IHmSecurityMapper hmSecurityMapper;
	
	@Autowired
	private IBackupCycleMapper backupCycleMapper;
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/test")
	public String  greeqwewqting(@RequestParam(value = "name", required = false) String name) {
//		return new Greeting(counter.incrementAndGet(),  name);
		System.out.println(name);
		String str = "PROGRM:hamonia, office";
		return str;
	}
	
	@RequestMapping("/getNxssList")
	public String  getNxssList(@RequestParam(value = "name", required = false) String sgbUuid ) throws Exception {

		// 출력 변수
		String output = "";
		List<BlockingNxssInfoVo> nxssVo = agentJobMapper.nxssListInfo();
		for( BlockingNxssInfoVo bnif : nxssVo ){
			output += bnif.getSma_domain() +"\n";
		}
		System.out.println("data==="+ output);
		return output;
	}
	
	@RequestMapping("/getAgentBackupJob")
	public String  getAgentProgrmJob(@RequestParam(value = "name", required = false) String sgbUuid ) throws Exception {
		String output = "";
		List<GetAgentJobVo> agentJobVo = agentJobMapper.getAgentJobList();
		System.out.println("==="+ sgbUuid);

		// uuid로 부대정보 가져오기
		GetAgentJobVo agentVo = new GetAgentJobVo(); 
		agentVo.setSgb_uuid(sgbUuid);
		agentVo = agentJobMapper.getAgentJobPcUUID(agentVo);
		System.out.println("agentVo ==="+ agentVo );
		
		// 정책정보 가져오기
		List<GetAgentJobVo> agentJobDataVo = agentJobMapper.getAgentJobPcBackup(agentVo);
		System.out.println("agentJobDataVo==="+ agentJobDataVo);
		
		
		for( int a=0; a<agentJobDataVo.size(); a++ ){
			System.out.println("agentJobVo.get(a)getAj_seq=="+ agentJobDataVo.get(a).getAj_seq());
			System.out.println("agentJobVo.get(a)getAj_table_gubun=="+ agentJobDataVo.get(a).getAj_table_gubun());
			System.out.println("agentJobVo.get(a)getAj_table_seq=="+ agentJobDataVo.get(a).getAj_table_seq());
			System.out.println("agentJobVo.get(a)getAj_return_val=="+ agentJobDataVo.get(a).getAj_return_val());
			
			
			BackupCycleVo backupVo = new BackupCycleVo();
			backupVo.setSelectOrgName(agentVo.getUpper_org_code());
			backupVo = backupCycleMapper.backupCycleList(backupVo);
			
			System.out.println("backupVo===="+ backupVo);
			
			output += agentJobDataVo.get(a).getAj_table_gubun()+ "-" + backupVo.getBac_cycle_option() +"-"+ backupVo.getBac_cycle_time();
			
		}
		
		System.out.println("backup == > " + output);
		
		return output;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/getAgentJob")
//	public String  getAgentJob(@RequestBody String payload ) throws Exception {
		public String  getAgentJob(@RequestParam(value = "name", required = false) String sgbUuid, @RequestParam(value = "wget", required = false) String sgbWget ) throws Exception {

		// 출력 변수
		String output = "";
		List<GetAgentJobVo> agentJobVo = agentJobMapper.getAgentJobList();
		
//		JSONParser jsonParser = new JSONParser();
//		JSONObject jsonObj = (JSONObject) jsonParser.parse( payload);
//		System.out.println("====> "+ jsonObj.get("name") +"==="+ jsonObj.get("call"));
//        
//		String sgbUuid = jsonObj.get("name").toString().trim();
//		String wgetGB = jsonObj.get("call").toString().trim();
		System.out.println("==="+ sgbUuid +"=="+ sgbWget);
		sgbUuid = sgbUuid.trim();
		sgbWget = sgbWget.trim();
		
		// uuid로 부대정보 가져오기
		GetAgentJobVo agentVo = new GetAgentJobVo(); 
		agentVo.setSgb_uuid(sgbUuid);
		agentVo = agentJobMapper.getAgentJobPcUUID(agentVo);
		System.out.println("agentVo ==="+ agentVo );
		
		// 정책정보 가져오기
		List<GetAgentJobVo> agentJobDataVo = agentJobMapper.getAgentJobPcPolocy(agentVo);
		System.out.println("agentJobDataVo==="+ agentJobDataVo);
		if( "Y".equals(sgbWget) ){
			agentJobMapper.setAgentJobPcPolocy(agentVo);
		}
		
		JSONObject jsonObject = new JSONObject();
		Boolean isProgramjob = false;
		Boolean isUpdateJob = false;
		Boolean isSecurityJob = false;
		
		
		if( agentJobDataVo.size() > 0){
			
		
			if( "PROGRM".equals(agentJobDataVo.get(0).getAj_table_gubun()) ){
				isProgramjob = true;
			}else if( "UPDT".equals(agentJobDataVo.get(0).getAj_table_gubun()) ){
				isUpdateJob = true;
			}else if( "SCRTY".equals(agentJobDataVo.get(0).getAj_table_gubun()) ){
				isSecurityJob = true;
			}
			
			System.out.println("isProgramjob===="+ isProgramjob);
			System.out.println("isUpdateJob===="+ isUpdateJob);
			System.out.println("isSecurityJob===="+ isSecurityJob);
			
			for( int a=0; a<agentJobDataVo.size(); a++ ){
				System.out.println("agentJobVo.get(a)=="+ agentJobDataVo.get(a));
				
				String[] splitTmpPcmSeqVal = agentJobDataVo.get(a).getPpa_pcm_seq().split("\\{");
				splitTmpPcmSeqVal = splitTmpPcmSeqVal[1].split("\\}");
				splitTmpPcmSeqVal = splitTmpPcmSeqVal[0].split(",");
				String convertTmp[] =new String[splitTmpPcmSeqVal.length]; ;
				
	
				if( splitTmpPcmSeqVal.length > 0){
					for( int i=0; i<splitTmpPcmSeqVal.length; i++ ){
						System.out.println(i +"=="+ splitTmpPcmSeqVal[i]);
						convertTmp[i] = splitTmpPcmSeqVal[i];
					}
				}
				
				if( isProgramjob ){
					HmprogramVo hvo = new HmprogramVo();
					hvo.setOrgpcmseq(convertTmp);
					hvo = hmprogramMapper.selectHmProgrmAgentJob(hvo);
				
					output += agentJobDataVo.get(a).getAj_table_gubun()+ "=" + hvo.getPcm_name();
					if( a > 0 ){
						output +="<br>";
					}
				}else if( isUpdateJob ){
					HmProgrmUpdtVo hvo = new HmProgrmUpdtVo();
					hvo.setOrgpcmseq(convertTmp);
					hvo = hmProgrmUpdtMapper.selectHmUpdateAgentJob(hvo);
				
					output += agentJobDataVo.get(a).getAj_table_gubun()+ "=" + hvo.getPcm_name();
					if( a > 0 ){
						output +="<br>";
					}	
				}else if( isSecurityJob ){
				/*	HmSecurityVo hvo = new HmSecurityVo();
					hvo.setOrgpcmseq(convertTmp);
					hvo = hmSecurityMapper.selectHmSecurityAgentJob(hvo);
				
					output += agentJobDataVo.get(a).getAj_table_gubun()+ "=" + hvo.getPcm_name();
					if( a > 0 ){
						output +="<br>";
					}	*/
					
					HmSecurityVo hsvo = new HmSecurityVo();
					hsvo.setOrgpcmseq(convertTmp);
					hsvo = hmSecurityMapper.selectHmSecurityAgentJob(hsvo);
				
					hsvo.setOrgpcmseq(convertTmp);
					hsvo.setOrg_seq(agentVo.getUpper_org_code());
					
					
					List<HmSecurityVo> listPVO = agentJobMapper.selectSecurityInAgentJob(hsvo);
					System.out.println("listPVO======"+ listPVO.size());
					
					
					JSONArray newArray = new JSONArray();
					JSONArray updArray = new JSONArray();
					JSONObject news = new JSONObject();
					JSONObject upds = new JSONObject();
					

					JSONObject response = new JSONObject(); 
					
					
					if( listPVO.size() == 0  ){
						agentJobMapper.selectInsertSecurityInAgentJob(hsvo);
						List<HmSecurityVo> agentSEY = agentJobMapper.securityAgentY(hsvo);
						
						for(HmSecurityVo i : agentSEY){
							System.out.println(i.getSm_name().trim() +"=="+ i.getSm_seq() +"=="+ i.getSm_status());
							news.put(i.getSm_name(), i.getSm_gubun());
						}
						newArray.add(news);
				        jsonObject.put("INS", newArray);
				        
					}else{
						
						
						List<HmSecurityVo> agentSyY = agentJobMapper.securityAgentY(hsvo);
						List<HmSecurityVo> agentSyN = agentJobMapper.securityAgentN(hsvo);

						
						JSONObject data = new JSONObject();
						JSONObject data2 = new JSONObject();

						String arrAgentSyY = "", arrAgentSyN="";
						if (agentSyY.size() > 0) {
							
							for( int i=0; i<agentSyY.size(); i++ ){
								
								if (!"Y".equals(agentSyY.get(i).getPcm_status())) {
									if( agentSyY.size() -1 == i ){
										arrAgentSyY += agentSyY.get(i).getSm_name() +"-" + agentSyY.get(i).getSm_gubun() +"-" + agentSyY.get(i).getSm_port();	
									}else {
										arrAgentSyY += agentSyY.get(i).getSm_name() +"-" + agentSyY.get(i).getSm_gubun()+"-" + agentSyY.get(i).getSm_port() +",";
									}
								}
							
							}
							response.put("INS", arrAgentSyY); 
							
							
							/*for (HmSecurityVo i : agentSyY) {
								System.out.println(i.getSm_name()+"=="+ i.getSm_seq() +"=="+ i.getPcm_status()+"=="+ i.getSm_gubun());
								
								if (!"Y".equals(i.getPcm_status())) {
									news.put(i.getSm_name(), i.getSm_gubun());
									
									HmSecurityVo insVo = new HmSecurityVo();
									insVo.setPcm_seq(i.getSm_seq());
									insVo.setPcm_name(i.getSm_name());
									insVo.setOrg_seq(agentVo.getUpper_org_code());
									if( "Y".equals(sgbWget) ){
										agentJobMapper.securityAgentInsert(insVo);
									}
									
								}
							}
							newArray.add(news);
							jsonObject.put("INS", newArray);*/
							
						}
						
						
						if (agentSyN.size() > 0) {

							for (int i = 0; i < agentSyN.size(); i++) {

//								if (!"Y".equals(agentSyN.get(i).getPcm_status())) { 
									if (agentSyN.size() - 1 == i) {
										arrAgentSyN += agentSyN.get(i).getPcm_name()+"-"+agentSyN.get(i).getSm_gubun()+"-" + agentSyN.get(i).getSm_port();
									} else {
										arrAgentSyN += agentSyN.get(i).getPcm_name()+"-"+agentSyN.get(i).getSm_gubun()+"-" + agentSyN.get(i).getSm_port() + ",";
									}
//								}

							}
							response.put("UPD", arrAgentSyN);

						/*	for (HmSecurityVo i : agentSyN) {
								
								System.out.println(i.getPcm_name() + "==" + i.getPcm_seq() + "==" + i.getPcm_status()+"=="+ i.getSm_gubun());
								upds.put(i.getPcm_name(), i.getSm_gubun());
								data2.put(i.getSm_name(), i.getSm_gubun());
								
								HmSecurityVo desVo = new HmSecurityVo();
								desVo.setPcm_seq(i.getPcm_seq());
								desVo.setPcm_name(i.getPcm_name());
								desVo.setOrg_seq(agentVo.getUpper_org_code());
								if( "Y".equals(sgbWget) ){
									agentJobMapper.securityAgentDel(desVo);
								}
							}

							updArray.add(upds);
							jsonObject.put("UPD", updArray);*/

							
						//	response.put("UPD", data); 
							
							
						}			   
						
						
						
					}
					System.out.println("response====="+ response.toString());
					output = "SCRTY=" + response.toString();
					
					
				}
				
				
				
				System.out.println(a + "-- " + output);
			}
		
		}else {
			output = "NODATA";
		}
		
		return output;
	}
	

	@RequestMapping("/getou")
	public String  getou(@RequestParam(value = "name", required = false) String sgbnm ) throws Exception {
		System.out.println("sgbnm==="+ sgbnm);
		
		OrgVo gvo = new OrgVo();
		gvo.setOrg_nm(sgbnm);
		gvo = orgMapper.selectGroupInfo( gvo );
		
		System.out.println("=========="+ gvo);
		
		String pbisJoinDN = null;
		if( gvo == null ){
			pbisJoinDN = "NOSGB";
		}else{
			
			System.out.println("gvo.getOrguppercode()====="+ gvo.getP_seq());
			
			gvo.setSeq(gvo.getP_seq() );
			System.out.println("==========> "+ gvo.getSeq());
			OrgVo retOU = orgMapper.groupUpperCode(gvo);
			pbisJoinDN = retOU.getOrg_nm().replaceAll(" ", "");
			
//			String yourNewString =yourString.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");           

			
			pbisJoinDN = "OU=" + gvo.getOrg_nm()+","+ pbisJoinDN;
		}

		pbisJoinDN = pbisJoinDN.replaceAll(" ", "");
		System.out.println("=-==="+ pbisJoinDN);
		
		return pbisJoinDN;
	}
	
	@RequestMapping("/process")
	public String  process(HttpServletRequest request) throws Exception {
		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	        	System.out.println("line===> "+ line);
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
	    
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        System.out.println("=====client pc device info =====");
        
        PcMangrVo hdVo = new PcMangrVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
//        	private int org_seq;
        	
            hdVo.setFirst_date(tempObj.get("datetime").toString());
            hdVo.setSgb_pc_uuid(tempObj.get("uuid").toString());
            hdVo.setSgb_pc_cpu_id(tempObj.get("cpuid").toString());
            hdVo.setSgb_pc_cpu(tempObj.get("cpuinfo").toString());
            hdVo.setSgb_pc_disk_id(tempObj.get("hddid").toString());
            hdVo.setSgb_pc_disk(tempObj.get("hddinfo").toString());
            hdVo.setSgb_pc_macaddress(tempObj.get("macaddr").toString());
            hdVo.setSgb_pc_ip(tempObj.get("ipaddr").toString());
            hdVo.setSgb_pc_vpnip(tempObj.get("vpnipaddr").toString());
            hdVo.setSgb_pc_hostname(tempObj.get("hostname").toString());
            hdVo.setSgb_pc_os(tempObj.get("sgbpcos").toString());
            hdVo.setSgb_pc_memory(tempObj.get("memory").toString() +"G");
            hdVo.setSgbname(tempObj.get("sgbname").toString());
            hdVo.setSgbpcname(tempObj.get("sgbpcname").toString());
            
        }
        
        
        int retVal = pcMangrMapper.inserPcInfo(hdVo);
        AdLdapUtils aldp = new AdLdapUtils();
    	String retOU = aldp.adComputerSearchUseCn(hdVo.getSgbpcname());
    	System.out.println("retOU=============="+ retOU);
    	Boolean isBool = aldp.computerModify( retOU, hdVo.getSgbpcname() , hdVo.getSgb_pc_macaddress());
    	System.out.println("isBool === " + isBool);
        
        return isBool+"";
	}
	
	
	@RequestMapping("/pcMacModify")
	public String  pcMacModify(HttpServletRequest request) throws Exception {
		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	        	System.out.println("line===> "+ line);
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
	    
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        System.out.println("=====client pc device info =====");
        
        PcMangrVo hdVo = new PcMangrVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
//        	private int org_seq;
        	
            hdVo.setFirst_date(tempObj.get("datetime").toString());
            hdVo.setSgb_pc_uuid(tempObj.get("uuid").toString());
            hdVo.setSgb_pc_macaddress(tempObj.get("macaddr").toString());
            hdVo.setSgb_pc_ip(tempObj.get("ipaddr").toString());
            hdVo.setSgb_pc_vpnip(tempObj.get("vpnipaddr").toString());
            hdVo.setSgb_pc_hostname(tempObj.get("hostname").toString());
            hdVo.setSgbname(tempObj.get("sgbname").toString());
            
        }
        
        System.out.println("pcMacModify hdVo======="+ hdVo.toString());
        
        String output = "";
    	AdLdapUtils aldp = new AdLdapUtils();
    	String retOU = aldp.adComputerSearchUseCn(hdVo.getSgbname());
    	System.out.println("retOU=============="+ retOU);
    	Boolean isBool = aldp.computerModify( retOU, hdVo.getSgbname() , hdVo.getSgb_pc_macaddress());
    	System.out.println("isBool === " + isBool);
        	
        return output;
	}
	
	
	
	@RequestMapping("/getSgbNm")
	public String  getSgbNm(@RequestParam(value = "name", required = false) String name) throws Exception {
		String output="";

        System.out.println("=====getSgbNm info =====" +name);
     
        OrgVo gvo = new OrgVo();
        String retVal = "";
        if(name == "" || name == null) {
        	retVal = "NODATA";
        }else {
			gvo.setSgb_pc_uuid(name.trim());
			gvo = orgMapper.getSgbName( gvo );
			
			if(gvo == null ) {
				retVal = "NODATA";
			}else {
				retVal = gvo.getOrg_nm();		
			}
			
        }
       
        return retVal;
	}
	
	
	
	@RequestMapping("/sgbPcLoginout")
	public String  sgbPcLoginout(HttpServletRequest request) throws Exception {
		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	        	System.out.println("line===> "+ line);
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
	    
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
        JSONArray hmdArray = (JSONArray) jsonObj.get("events");

        System.out.println("=====client pc device info =====");
        
        PcMangrVo hdVo = new PcMangrVo();
        for(int i=0 ; i<hmdArray.size() ; i++){
            JSONObject tempObj = (JSONObject) hmdArray.get(i);
        	
            hdVo.setFirst_date(tempObj.get("datetime").toString());
            hdVo.setSgb_pc_macaddress(tempObj.get("macaddr").toString());
            hdVo.setSgb_pc_ip(tempObj.get("ipaddr").toString());
            hdVo.setSgb_pc_vpnip(tempObj.get("vpnipaddr").toString());
            hdVo.setSgb_pc_hostname(tempObj.get("hostname").toString());
            hdVo.setSgb_pc_cpu_id(tempObj.get("user").toString());
            hdVo.setSgb_pc_uuid(tempObj.get("pcuuid").toString());
            hdVo.setStatus(tempObj.get("action").toString());
        }

        
        PcMangrVo chkPcMangrVo = pcMangrMapper.chkPcinfo(hdVo);
        
        int retVal = 0;
        if( !chkPcMangrVo.getSgb_pc_vpnip().equals(hdVo.getSgb_pc_vpnip())) {
        	
        	hdVo.setOld_pc_ip(chkPcMangrVo.getSgb_pc_ip());
        	hdVo.setOld_pc_vpnip(chkPcMangrVo.getSgb_pc_vpnip());
        	hdVo.setOld_pc_macaddr(chkPcMangrVo.getSgb_pc_macaddress());
        
        	retVal = pcMangrMapper.updatePcinfo(hdVo);
        	pcMangrMapper.pcIpchnLog(hdVo);
        	
        	
        	AdLdapUtils aldp = new AdLdapUtils();
        	String retOU = aldp.adComputerSearchUseCn(hdVo.getSgb_pc_hostname());
        	Boolean isBool = aldp.computerModify( retOU, hdVo.getSgb_pc_hostname() , hdVo.getSgb_pc_macaddress());
        	
        }
        System.out.println("sgbpcloginout info === "+ hdVo.toString());
        
        return "retval:"+retVal;
	}

	/*
	 * @RequestMapping("/getTime") public String getTime() throws Exception {
	 * 
	 * Date date = Calendar.getInstance().getTime(); //SimpleDateFormat time = new
	 * SimpleDateFormat("HH:mm:ss",Locale.KOREA); //String gtime =
	 * time.format(date); String output2=
	 * (date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
	 * SimpleDateFormat time = new SimpleDateFormat("kk:mm:dd"); String output =
	 * time.format(date); System.out.println(output2);
	 * 
	 * return output; }
	 */
	
	@RequestMapping("/getTime")
	public String  getTime() throws Exception {

			TimeZone time;
			Date date = new Date();
			DateFormat df = new SimpleDateFormat(

					"yyyyMMdd HH:mm:ss");

			time = TimeZone.getTimeZone("Asia/Seoul");

			df.setTimeZone(time);

			System.out.format("%s%n%s%n%n", time.getDisplayName(),
					df.format(date));
			return df.format(date);

	}
	
	@RequestMapping("/prcssKill")
	public void prcssKill(HttpServletRequest request) throws Exception {
		
		StringBuffer json = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = request.getReader();
	        while((line = reader.readLine()) != null) {
	            json.append(line);
	        }
	 
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
	    
	    
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParser.parse( json.toString());
		JSONArray inetvalArray = (JSONArray) jsonObj.get("events");
		System.out.println("====> "+ jsonObj.get("events"));

		Map<String,Object> prcssList = new HashMap<String,Object>();
        for(int i=0 ; i<inetvalArray.size() ; i++){
            JSONObject tempObj = (JSONObject) inetvalArray.get(i);
            prcssList.put("insert_dt",tempObj.get("datetime").toString());
            prcssList.put("hostname",tempObj.get("hostname").toString());
            prcssList.put("prcssname",tempObj.get("prcssname").toString());
            prcssList.put("ipaddr",tempObj.get("ipaddr").toString());
            prcssList.put("macaddr",tempObj.get("macaddr").toString());
            prcssList.put("uuid",tempObj.get("uuid").toString());
            prcssList.put("org_seq",sgbUUID(tempObj.get("uuid").toString()));
            prcssList.put("user_id",tempObj.get("userid").toString());
            
        }
        
        
        hmprogramMapper.prcssKillLog(prcssList);
	}
	
	@RequestMapping("/getSgbBlockStatus")
	public String  getSgbBlockStatus(@RequestParam(value = "name", required = false) String name) throws Exception {
		String output="";

        System.out.println("=====getSgbBlockStatus info =====" +name);
        
        //HashMap<String,Object> map = new HashMap<String,Object>();
        PcMangrVo vo = new PcMangrVo();
        String retVal = "";
        if(name == "" || name == null) {
        	retVal = "NODATA";
        }else {
        	//map.put("uuid", name);
        	vo.setSgb_pc_uuid(name.trim());
        	System.out.println("getsgbuuid=="+vo.getSgb_pc_uuid());
			vo = pcMangrMapper.getSgbPcBlockStatus(vo);
			//System.out.println("status==========="+vo.toString());
			retVal = vo.getStatus();
			
        }
        if(vo == null ) {
			retVal = "NODATA";
		}else {
			retVal = vo.getStatus();		
		}
        System.out.println("retVal===="+retVal);
       
        return retVal;
	}
	
	/**
	 * 사지방 UUID로 부대 seq 가져오기
	 * 
	 * @param sgbUuid
	 * @return 부대seq
	 */
	public int sgbUUID(String sgbUuid) {
		GetAgentJobVo agentVo = new GetAgentJobVo();
		agentVo.setSgb_pc_uuid(sgbUuid);
		agentVo = agentJobMapper.getAgentJobPcUUID(agentVo);
		int segSeq = agentVo.getSeq();
		return segSeq;
	}
	
}


