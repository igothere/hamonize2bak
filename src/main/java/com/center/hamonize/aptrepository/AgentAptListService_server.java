package com.center.hamonize.aptrepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AgentAptListService_server {
	
	  @Value("${apt.ip}") 
	  private String aptIp;
	 

	public List<Map<String,Object>> getApt() {

//		String apiURL = "http://"+aptIp+"/dists/sgb-title/main/binary-amd64/Packages";
		String apiURL = "http://"+aptIp+"/dists/sgb-server/main/binary-amd64/Packages";

		//JSONArray ja = new JSONArray();
		List<String> list = new ArrayList<String>();
		List<String> listTmp = new ArrayList<String>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		try {

			URL url = new URL(apiURL);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String tempReadLine = "";
			int co = 0;
			
			String line = "";
			
			
			while((line = bufferedReader.readLine()) != null){
			    if( (line != null) && (!line.isBlank()) ) {
			    	tempReadLine += line +"\n";
			    }else { 
					

			    	if ( !tempReadLine.contains("Source") ) {
				    	
			    		Map<String,Object> map = new HashMap<String,Object>();
			    		String[] lines = tempReadLine.split("\n");
			    		for( int i=0; i<lines.length; i++ ) {
			    			
				    		if (lines[i].contains("Package") ) {
					    		map.put("package", lines[i].split(":")[1].trim());
							} 
				    		if (lines[i].contains("Version") ) {
					    		map.put("version", lines[i].split(":")[1].trim());
							} 
				    		
			    		}
			    		result.add(co,map);
			    		co++;
				    		
			    	}
			    	
			    	tempReadLine = "";
			    }
			    
			    
			}   
			


//			while ((inputLine = bufferedReader.readLine()) != null) {
//				stringBuilder.append(inputLine);
//				stringBuilder.append(System.lineSeparator());
//				if (inputLine.indexOf("Package") == 0) {
//					list.add(inputLine);
//					System.out.println("1============================++++"+inputLine);
//				} 
//				else if (inputLine.indexOf("Version") == 0) {
//					list.add(inputLine);
//					System.out.println("22222=================x===========++++"+inputLine);
//				}else if (inputLine.indexOf("Source") == 0) {
//					list.add(inputLine);
//					System.out.println("3333============================++++"+inputLine);
//				}
//			}
//			int co = 1;
//			for(int i = 0; i < list.size();i++) {
//				if((i % 2) != 0) {
//					Map<String,Object> map = new HashMap<String,Object>();
//					map.put("package", list.get((i-1)).split(":")[1].trim());
//					map.put("version", list.get(i).split(":")[1].trim());
//					map.put("Source", list.get(i).split(":")[1].trim());
//					
//					result.add((i-co),map);
//					co++;
//				}
//			}

			bufferedReader.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
System.out.println("result========++"+result);
		return result;

	}

}
