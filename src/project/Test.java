package project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		File normalFile = new File("src//project//udp_normal_file_urls.txt");
		List<UDPFeature> normalFeatures = UDPFeatureExtractor.getAllUDPFeatures(normalFile, "normal");
		File attackFile = new File("src//project//udp_attack_file_urls.txt");
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

		//////////////////////////////////
		double trainingFraction = 0.90;

		List<UDPNormalizedFeature> normalTraffic = normalizedNormalFeatures;
		List<UDPNormalizedFeature> attackTraffic = normalizedAttackFeatures;
		System.out.println(normalTraffic.size());
		System.out.println(attackTraffic.size());
		int normalTrainingLength = (int)(normalTraffic.size()*trainingFraction);
		int attackTrainingLength = (int)(attackTraffic.size()*trainingFraction);
		
		List<UDPNormalizedFeature> trainingList = new ArrayList<>(normalTraffic.subList(0, normalTrainingLength));
		trainingList.addAll(attackTraffic.subList(0, attackTrainingLength));
		
		List<UDPNormalizedFeature> testList = new ArrayList<>(normalTraffic.subList(normalTrainingLength, normalTraffic.size()));
		testList.addAll(attackTraffic.subList(attackTrainingLength, attackTraffic.size()));
		
		KNN knn = new KNN();
		
		knn.trainingSamples = trainingList;
		knn.testSamples = testList;
		//knn.magic();
	}

}
