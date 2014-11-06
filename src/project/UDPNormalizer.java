package project;

import java.util.ArrayList;
import java.util.List;

public class UDPNormalizer {

	public static List<UDPNormalizedFeature> normalize(List<UDPFeature> features) {
		
		List<UDPNormalizedFeature> normalizedFeatures = new ArrayList<UDPNormalizedFeature>();
		
		Integer maxPackets = features.get(0).getPackets();
		Integer maxBytes = features.get(0).getBytes();
		
		Double maxAveragePacketSize = features.get(0).getAveragePacketSize();
		Double maxPacketRate = features.get(0).getPacketRate();
		Double maxByteRate = features.get(0).getByteRate();
		Double maxTimeIntervalVariance = features.get(0).getTimeIntervalVariance();
		Double maxPacketSizeVariance = features.get(0).getPacketSizeVariance();
		
		
		for(int i=0; i< features.size(); i++) {
			if (maxPackets < features.get(i).getPackets()){
				maxPackets = features.get(i).getPackets();
			} 
			if (maxBytes < features.get(i).getBytes()){
				maxBytes = features.get(i).getBytes();
			}
			if (maxAveragePacketSize < features.get(i).getAveragePacketSize()){
				maxAveragePacketSize = features.get(i).getAveragePacketSize();
			}
			if (maxPacketRate < features.get(i).getPacketRate()){
				maxPacketRate = features.get(i).getPacketRate();
			}
			if (maxByteRate < features.get(i).getByteRate()){
				maxByteRate = features.get(i).getByteRate();
			}
			if (maxTimeIntervalVariance < features.get(i).getTimeIntervalVariance()){
				maxTimeIntervalVariance = features.get(i).getTimeIntervalVariance();
			}
			if (maxPacketSizeVariance < features.get(i).getPacketSizeVariance()){
				maxPacketSizeVariance = features.get(i).getPacketSizeVariance();
			}
		}
		
		for(int j=0; j<features.size(); j++) {
			
			String sourceIP = features.get(j).getSourceIP();
			String sourcePort = features.get(j).getSourcePort();
			String destIP = features.get(j).getDestIP();
			String destPort = features.get(j).getDestPort();
			
			String packetType = features.get(j).getPacketType();
			
			Double packets = (double) features.get(j).getPackets();
			Double bytes = (double) features.get(j).getBytes();
			
			Double averagePacketSize = features.get(j).getAveragePacketSize();
			Double packetRate = features.get(j).getPacketRate();
			Double byteRate = features.get(j).getByteRate();
			Double timeIntervalVariance = features.get(j).getTimeIntervalVariance();
			Double packetSizeVariance = features.get(j).getPacketSizeVariance();
			
			if(maxPackets != 0) {
				packets = packets/maxPackets;
			} 
			if(maxBytes != 0) {
				bytes = bytes/maxBytes;
			} 
			if(maxAveragePacketSize != 0) {
				averagePacketSize = averagePacketSize/maxAveragePacketSize;
			} 
			if(maxPacketRate != 0) {
				packetRate = packetRate/maxPacketRate;
			} 
			if(maxByteRate != 0) {
				byteRate = packets/maxByteRate;
			} 
			if(maxTimeIntervalVariance != 0) {
				timeIntervalVariance = packets/maxTimeIntervalVariance;
			} 
			if(maxPacketSizeVariance != 0) {
				packetSizeVariance = packets/maxPacketSizeVariance;
			} 
			
			UDPNormalizedFeature normalizedFeature = new UDPNormalizedFeature();
			normalizedFeature.setSourceIP(sourceIP);
			normalizedFeature.setSourcePort(sourcePort);
			normalizedFeature.setDestIP(destIP);
			normalizedFeature.setDestPort(destPort);
			
			normalizedFeature.setPackets(packetSizeVariance);
			normalizedFeature.setBytes(bytes);
			normalizedFeature.setAveragePacketSize(averagePacketSize);
			normalizedFeature.setPacketRate(packetRate);
			normalizedFeature.setByteRate(byteRate);
			
			normalizedFeature.setTimeIntervalVariance(timeIntervalVariance);
			normalizedFeature.setPacketSizeVariance(packetSizeVariance);
			
			normalizedFeature.setPacketType(packetType);
			
			normalizedFeatures.add(normalizedFeature);
		}
				
		return normalizedFeatures;
	}
	
}
