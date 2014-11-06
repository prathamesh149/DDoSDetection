package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UDPFeatureExtractor {
	public static List<UDPFeature> getAllUDPFeatures(File f,String type) throws IOException {
		Map<UDPKey,ArrayList<UDPData>> map = UDPFeatureExtractor.generateMapFromTraces(f,type);
		List<UDPFeature> features = UDPFeatureExtractor.getUDPFeaturesFromMap(map);
		return features;
	}
	//A method that loops over urls in the file and generates a single map
	public static Map<UDPKey,ArrayList<UDPData>> generateMapFromTraces(File f,String type) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String firstURL = br.readLine();
		Double base_time = calculateBaseTime(firstURL);
		Map<UDPKey,ArrayList<UDPData>> map = new HashMap<UDPKey,ArrayList<UDPData>>();
		map = UDPFileParser.getAssortedUDPMap(firstURL,base_time,map,type);
		String line = null;
		while ((line = br.readLine()) != null) {
			map = UDPFileParser.getAssortedUDPMap(line,base_time,map,type);
		}
		br.close();
		return map;
	}
	//A method that returns the arraylist of 11 parameter values (7+4) from map
	public static List<UDPFeature> getUDPFeaturesFromMap(Map<UDPKey,ArrayList<UDPData>> map) {
		List<UDPFeature> features = new ArrayList<UDPFeature>();
		for (Map.Entry<UDPKey, ArrayList<UDPData>> entry: map.entrySet()) {
			UDPFeature feature = extractUDPFeature(entry.getKey(),entry.getValue());
			features.add(feature);
		}
		return features;
	}
	private static UDPFeature extractUDPFeature(UDPKey key, ArrayList<UDPData> value) {
		UDPFeature feature = new UDPFeature();
		String sourceIP = key.getSourceIP();
		String sourcePort = key.getSourcePort();
		String destIP = key.getDestIP();
		String destPort = key.getDestPort();
		String packetType = key.getPacketType();
		int packets = value.size();
		int bytes = 0;
		Double totalTime = 0.0;
		for (int i=0; i < value.size(); i++) {
			bytes += value.get(i).getPacketSize();
			totalTime += value.get(i).getTime();
		}
		Double averagePacketSize = (double) (bytes/packets);
		Double te = value.get(packets-1).getTime();
		Double ts = value.get(0).getTime();
		//ByteRate, packetRate, timeIntervalVariance, packetSizeVariance are defined zero if value has 1 packet
		Double packetRate = 0.0;
		Double byteRate = 0.0;
		Double timeIntervalVariance = 0.0;
		Double packetSizeVariance = 0.0;
		if (!(te.compareTo(ts)==0)) {
			packetRate = packets/(te-ts);
			byteRate = bytes/(te-ts);
			Double meanTime = totalTime/packets;
			Double meanPacketSize = (double) (bytes/packets);
			//Time and packet size deviation
			Double timeDeviationSquares = 0.0;
			Double sizeDeviationSquares = 0.0;
			for (int j=0; j<value.size();j++) {
				timeDeviationSquares += (meanTime-value.get(j).getTime())*(meanTime-value.get(j).getTime());
				sizeDeviationSquares += (meanPacketSize-value.get(j).getPacketSize())*(meanPacketSize-value.get(j).getPacketSize());
			}
			timeIntervalVariance = Math.sqrt(timeDeviationSquares/packets);
			packetSizeVariance = Math.sqrt(sizeDeviationSquares/packets);
		}
		feature.setSourceIP(sourceIP);
		feature.setSourcePort(sourcePort);
		feature.setDestIP(destIP);
		feature.setDestPort(destPort);
		feature.setPacketType(packetType);
		feature.setPackets(packets);
		feature.setBytes(bytes);
		feature.setAveragePacketSize(averagePacketSize);
		feature.setPacketRate(packetRate);
		feature.setByteRate(byteRate);
		feature.setTimeIntervalVariance(timeIntervalVariance);
		feature.setPacketSizeVariance(packetSizeVariance);
		return feature;
	}
	//Send the first file URL to get the base time
	private static Double calculateBaseTime(String url) throws IOException {
		URL first_url = new URL(url);
		BufferedReader reader = new BufferedReader(new InputStreamReader(first_url.openStream()));
		String line = reader.readLine();
		String[] parts = line.split(" ");
		Double base_time = Double.parseDouble(parts[0]);
		return base_time;
	}
	public static void main(String[] args) throws IOException {
		File f = new File("src//extract//udp_attack_file_urls.txt");
		List<UDPFeature> features = UDPFeatureExtractor.getAllUDPFeatures(f, "attack");
		System.out.println();
	}
}


