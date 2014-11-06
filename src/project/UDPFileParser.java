package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class UDPFileParser {
	
	public static Map<UDPKey,ArrayList<UDPData>> getAssortedUDPMap(String url, Double base_time, Map<UDPKey,ArrayList<UDPData>> map,String type) throws IOException {
				
		URL traceURL;
		BufferedReader br = null;
		
		traceURL = new URL(url);
		br = new BufferedReader(new InputStreamReader(traceURL.openStream()));
				
		int i = 0;		
		String line = null;
		
		while( (line = br.readLine()) != null) {
			
			String[] parts = line.split(" ");
			
			String timeString = parts[0];
			Double time = Double.parseDouble(timeString);
			
			String sourceIP = parts[1];
			String destIP = parts[2];
			String sourcePort = parts[3];
			String destPort = parts[4];
			String protocol = parts[5];
			String size = parts[6];
			int pktSize = Integer.parseInt(size);
						
			if( !(base_time.compareTo(time)==0)) {
				time = time + base_time;					
			}
			
			UDPKey key = new UDPKey(sourceIP, destIP, sourcePort, destPort,type);
			UDPData data = new UDPData(pktSize, time); 
			
			//Put in map
			if (!map.containsKey(key)) {
				ArrayList<UDPData> list = new ArrayList<UDPData>();
				list.add(data);
				map.put(key, list);				
			} else {				
				ArrayList<UDPData> existingList = map.get(key);
				existingList.add(data);
				map.put(key, existingList);	
				System.out.println("i: " + i + " " + key);
			}						
			i++;			
		}
				
		br.close();
		return map;
	}
	
	public static void main(String[] args) throws IOException {
		String url = "http://www.lasr.cs.ucla.edu/ddos/traces/public/trace5/udp/file1";		
	}
}
