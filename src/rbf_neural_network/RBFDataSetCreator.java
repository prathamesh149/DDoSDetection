package rbf_neural_network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import project.KNN;
import project.UDPFeature;
import project.UDPFeatureExtractor;
import project.UDPNormalizedFeature;
import project.UDPNormalizer;

public class RBFDataSetCreator {

	public void createDataSets() throws IOException {

		
		
		File normalFile = new File("src//project//udp_normal_file_urls_ucla.txt");
		List<UDPFeature> normalFeatures = UDPFeatureExtractor.getAllUDPFeatures(normalFile, "normal");
		File attackFile = new File("src//project//udp_attack_file_urls_ucla.txt");
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

		/*
		 * Seperation of training and test data
		 */
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
		
		/*
		 * Code to generate data set files
		 */
		createDataSetFile(trainingList,"src//rbf_neural_network//udp_rbf_training_file_ucla.txt");
		createDataSetFile(testList,"src//rbf_neural_network//udp_rbf_test_file_ucla.txt");
		
	}
	
	public void createDataSetFile(List<UDPNormalizedFeature> list,String name) throws IOException {
		
		File f = new File(name);
			
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		
		String line = null;
		
		for(UDPNormalizedFeature feature: list) {
			
			line = feature.toStringEntry();
			
			bw.write(line);
			bw.newLine();
		}
		
		bw.close();
	}
	
	public static void main(String[] args) throws IOException {
		RBFDataSetCreator r = new RBFDataSetCreator();
		r.createDataSets();
	}
		
}
