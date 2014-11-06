package extract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) throws IOException {
		File normalFile = new File("src//extract//udp_normal_file_urls.txt");
		List<UDPFeature> normalFeatures = UDPFeatureExtractor.getAllUDPFeatures(normalFile, "normal");
		
		File attackFile = new File("src//extract//udp_attack_file_urls.txt");
		List<UDPFeature> attackFeatures = UDPFeatureExtractor.getAllUDPFeatures(attackFile, "attack");
		
		List<UDPFeature> combined = new ArrayList<UDPFeature>();
		combined.addAll(normalFeatures);
		combined.addAll(attackFeatures);
		
		List<UDPNormalizedFeature> normalized = UDPNormalizer.normalize(combined);
		
		List<UDPNormalizedFeature> normalizedNormalFeatures = new ArrayList<UDPNormalizedFeature>();
		for (int i=0; i <normalFeatures.size(); i++) {
			normalizedNormalFeatures.add(normalized.get(i));			
		}
		
		List<UDPNormalizedFeature> normalizedAttackFeatures = new ArrayList<UDPNormalizedFeature>();
		for (int j=normalFeatures.size() ; j <normalized.size(); j++) {
			normalizedAttackFeatures.add(normalized.get(j));			
		}
				
	}
}
