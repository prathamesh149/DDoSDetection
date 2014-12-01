package svm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import project.UDPFeature;
import project.UDPFeatureExtractor;
import project.UDPNormalizedFeature;
import project.UDPNormalizer;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class SVMTest {

	public double[][] test;
	public double[][] train;

	public svm_model svmTrain() {

		svm_problem prob = new svm_problem();
		int dataCount = train.length;
		prob.y = new double[dataCount];
		prob.l = dataCount;
		prob.x = new svm_node[dataCount][];

		for (int i = 0; i < dataCount; i++) {
			double[] features = train[i];
			prob.x[i] = new svm_node[features.length - 1];
			for (int j = 1; j < features.length; j++) {
				svm_node node = new svm_node();
				node.index = j;
				node.value = features[j];
				prob.x[i][j - 1] = node;
			}
			prob.y[i] = features[0];
		}

		svm_parameter param = new svm_parameter();
		param.probability = 1;
		param.gamma = 0.5;
		param.nu = 0.5;
		param.C = 1;
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.LINEAR;
		param.cache_size = 20000;
		param.eps = 0.001;

		svm_model model = svm.svm_train(prob, param);
		System.out.println("Training done");
		return model;
	}

	public void evaluateTestData(svm_model model) {
		
		int false_positive = 0;
		int false_negative = 0;
		
		for(int i=0;i<test.length;i++){
			double v = evaluate(test[i], model);
			if(v == 0 && test[i][0] == -1){
				false_positive++;
				System.out.println("False positive: " + false_positive + " out of " + test.length + "classified incorrectly") ;			
			}
			
			if(v == -1 && test[i][0] == 0){
				false_negative++;
				System.out.println("False negative: " + false_negative + " out of " + test.length + "classified incorrectly") ;			
			}
		}
		
		System.out.println("Final False positive: " + false_positive + " out of " + test.length + "classified incorrectly") ;			
		System.out.println("Final False negative: " + false_negative + " out of " + test.length + "classified incorrectly") ;			

	}

	public double evaluate(double[] features, svm_model model) {
		svm_node[] nodes = new svm_node[features.length - 1];
		for (int i = 1; i < features.length; i++) {
			svm_node node = new svm_node();
			node.index = i;
			node.value = features[i];

			nodes[i - 1] = node;
		}

		int totalClasses = 2;
		int[] labels = new int[totalClasses];
		svm.svm_get_labels(model, labels);

		double[] prob_estimates = new double[totalClasses];
		double v = svm.svm_predict_probability(model, nodes, prob_estimates);

		for (int i = 0; i < totalClasses; i++) {
			System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
		}
		System.out.println("(Actual:" + features[0] + " Prediction:" + v + ")");

		return v;
	}

	public void initialize() throws IOException {

		File normalFile = new File("src//project//udp_normal_file_urls_ucla.txt");
		List<UDPFeature> normalFeatures = UDPFeatureExtractor
				.getAllUDPFeatures(normalFile, "normal");
		File attackFile = new File("src//project//udp_attack_file_urls_ucla.txt");
		List<UDPFeature> attackFeatures = UDPFeatureExtractor
				.getAllUDPFeatures(attackFile, "attack");
		List<UDPFeature> combined = new ArrayList<UDPFeature>();

		combined.addAll(normalFeatures);
		combined.addAll(attackFeatures);

		List<UDPNormalizedFeature> normalized = UDPNormalizer
				.normalize(combined);
		List<UDPNormalizedFeature> normalizedNormalFeatures = new ArrayList<UDPNormalizedFeature>();
		for (int i = 0; i < normalFeatures.size(); i++) {
			normalizedNormalFeatures.add(normalized.get(i));
		}
		List<UDPNormalizedFeature> normalizedAttackFeatures = new ArrayList<UDPNormalizedFeature>();
		for (int j = normalFeatures.size(); j < normalized.size(); j++) {
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
		int normalTrainingLength = (int) (normalTraffic.size() * trainingFraction);
		int attackTrainingLength = (int) (attackTraffic.size() * trainingFraction);

		List<UDPNormalizedFeature> trainingList = new ArrayList<>(
				normalTraffic.subList(0, normalTrainingLength));
		trainingList.addAll(attackTraffic.subList(0, attackTrainingLength));

		List<UDPNormalizedFeature> testList = new ArrayList<>(
				normalTraffic.subList(normalTrainingLength,
						normalTraffic.size()));
		testList.addAll(attackTraffic.subList(attackTrainingLength,
				attackTraffic.size()));

		train = new double[trainingList.size()][];
		test = new double[testList.size()][];

		for (int i = 0; i < trainingList.size(); i++) {
			train[i] = trainingList.get(i).toDoubleEntry();
		}

		for (int j = 0; j < testList.size(); j++) {
			test[j] = testList.get(j).toDoubleEntry();
		}

	}

	public static void main(String[] args) throws IOException {
		SVMTest t = new SVMTest();
		// Initialize train and test data
		t.initialize();

		// Train the model with trained data
		svm_model model = t.svmTrain();

		t.evaluateTestData(model);

	}

}
