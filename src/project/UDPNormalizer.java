package project;

import java.util.ArrayList;
import java.util.List;

public class UDPNormalizer {

	public static List<UDPNormalizedFeature> normalize(List<UDPFeature> features) {
		
		List<UDPNormalizedFeature> normalizedFeatures = new ArrayList<UDPNormalizedFeature>();
		
		/*
		Integer maxPackets = features.get(0).getPackets();
		Integer maxBytes = features.get(0).getBytes();
		
		Double maxAveragePacketSize = features.get(0).getAveragePacketSize();
		Double maxPacketRate = features.get(0).getPacketRate();
		Double maxByteRate = features.get(0).getByteRate();
		Double maxTimeIntervalVariance = features.get(0).getTimeIntervalVariance();
		Double maxPacketSizeVariance = features.get(0).getPacketSizeVariance();
		*/
		
		//Sum
		Double sumPackets = (double)features.get(0).getPackets();
		Double sumBytes = (double)features.get(0).getBytes();
		
		Double sumAveragePacketSize = features.get(0).getAveragePacketSize();
		Double sumPacketRate = features.get(0).getPacketRate();
		Double sumByteRate = features.get(0).getByteRate();
		Double sumTimeIntervalVariance = features.get(0).getTimeIntervalVariance();
		Double sumPacketSizeVariance = features.get(0).getPacketSizeVariance();
		
		/*
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
		}*/
		
		for(int i=1; i< features.size(); i++) {
			
				sumPackets += features.get(i).getPackets();
				sumBytes += features.get(i).getBytes();
				sumAveragePacketSize += features.get(i).getAveragePacketSize();
				sumPacketRate += features.get(i).getPacketRate();
				sumByteRate += features.get(i).getByteRate();
				sumTimeIntervalVariance += features.get(i).getTimeIntervalVariance();
				sumPacketSizeVariance += features.get(i).getPacketSizeVariance();
		}
		
		sumPackets /= features.size();
		sumBytes /= features.size();
		sumAveragePacketSize /= features.size();
		sumPacketRate /= features.size();
		sumByteRate /= features.size();
		sumTimeIntervalVariance /= features.size();
		sumPacketSizeVariance /= features.size();
		
		
		Double varPackets = 0.0;
		Double varBytes = 0.0;
		
		Double varAveragePacketSize = 0.0;
		Double varPacketRate = 0.0;
		Double varByteRate = 0.0;
		Double varTimeIntervalVariance = 0.0;
		Double varPacketSizeVariance = 0.0;
		
		for(int k=0; k<features.size();k++) {
			
			varPackets += Math.pow((features.get(k).getPackets()-sumPackets), 2);
			varBytes += Math.pow((features.get(k).getBytes()-sumBytes), 2);
			varAveragePacketSize += Math.pow((features.get(k).getAveragePacketSize()-sumAveragePacketSize), 2);
			varPacketRate += Math.pow((features.get(k).getPacketRate()-sumPacketRate), 2);
			varByteRate += Math.pow((features.get(k).getByteRate()-sumByteRate), 2);
			varTimeIntervalVariance += Math.pow((features.get(k).getTimeIntervalVariance()-sumTimeIntervalVariance), 2);
			varPacketSizeVariance += Math.pow((features.get(k).getPacketSizeVariance()-sumPacketSizeVariance), 2);
			
		}
		
		varPackets = Math.sqrt(varPackets/features.size());
		varBytes = Math.sqrt(varBytes/features.size());
		varAveragePacketSize = Math.sqrt(varAveragePacketSize/features.size());
		varPacketRate = Math.sqrt(varPacketRate/features.size());
		varByteRate = Math.sqrt(varByteRate/features.size());
		varTimeIntervalVariance = Math.sqrt(varTimeIntervalVariance/features.size());
		varPacketSizeVariance = Math.sqrt(varPacketSizeVariance/features.size());
		
		/*
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
				byteRate = byteRate/maxByteRate;
			} 
			if(maxTimeIntervalVariance != 0) {
				timeIntervalVariance = timeIntervalVariance/maxTimeIntervalVariance;
			} 
			if(maxPacketSizeVariance != 0) {
				packetSizeVariance = packetSizeVariance/maxPacketSizeVariance;
			} 
			
			UDPNormalizedFeature normalizedFeature = new UDPNormalizedFeature();
			normalizedFeature.setSourceIP(sourceIP);
			normalizedFeature.setSourcePort(sourcePort);
			normalizedFeature.setDestIP(destIP);
			normalizedFeature.setDestPort(destPort);
			
			normalizedFeature.setPackets(packets);
			normalizedFeature.setBytes(bytes);
			normalizedFeature.setAveragePacketSize(averagePacketSize);
			normalizedFeature.setPacketRate(packetRate);
			normalizedFeature.setByteRate(byteRate);
			
			normalizedFeature.setTimeIntervalVariance(timeIntervalVariance);
			normalizedFeature.setPacketSizeVariance(packetSizeVariance);
			
			normalizedFeature.setPacketType(packetType);
			
			normalizedFeatures.add(normalizedFeature);
		}*/
		
		
		
		for(int j=0;j<features.size();j++) {
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
			
			if(varPackets != 0) {
				packets = (packets-sumPackets)/varPackets;
			} 
			if(varBytes != 0) {
				bytes = (bytes-sumBytes)/varBytes;
			} 
			if(varAveragePacketSize != 0) {
				averagePacketSize = (averagePacketSize-sumAveragePacketSize)/varAveragePacketSize;
			} 
			if(varPacketRate != 0) {
				packetRate = (packetRate-sumPacketRate)/varPacketRate;
			} 
			if(varByteRate != 0) {
				byteRate = (byteRate-sumByteRate)/varByteRate;
			} 
			if(varTimeIntervalVariance != 0) {
				timeIntervalVariance = (timeIntervalVariance-sumTimeIntervalVariance)/varTimeIntervalVariance;
			} 
			if(varPacketSizeVariance != 0) {
				packetSizeVariance = (packetSizeVariance-sumPacketSizeVariance)/varPacketSizeVariance;
			} 
			
			UDPNormalizedFeature normalizedFeature = new UDPNormalizedFeature();
			normalizedFeature.setSourceIP(sourceIP);
			normalizedFeature.setSourcePort(sourcePort);
			normalizedFeature.setDestIP(destIP);
			normalizedFeature.setDestPort(destPort);
			
			normalizedFeature.setPackets(packets);
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
