package project;

import java.util.List;

public class KNN {

	private int knn = 5;
	public List<UDPNormalizedFeature> trainingSamples, testSamples;

	Distance[] distances;

	public int magic() {
		int error = 0;
		distances = new Distance[knn];

		int false_positive = 0;
		int false_negative = 0;
		
		
		for (int j = 0; j < testSamples.size(); j++) { // find the knn
			for (int i = 0; i < trainingSamples.size(); i++) {
				double dist = trainingSamples.get(i).distance(
						testSamples.get(j));
				if (i < knn) {
					distances[i] = new Distance();
					distances[i].d = dist;
					distances[i].label = trainingSamples.get(i).getPacketType();
				} else { // go through the knn list and replace the biggest one
							// if possible
					double biggestd = distances[0].d;
					int biggestindex = 0;
					for (int a = 1; a < knn; a++)
						if (distances[a].d > biggestd) {
							biggestd = distances[a].d;
							biggestindex = a;
						}
					if (dist < biggestd) {
						distances[biggestindex].d = dist;
						distances[biggestindex].label = trainingSamples.get(i)
								.getPacketType();
					}
				}
			}

			// count which label in knn occurs most, this is the classification
			// of (x,y)
			int nT = 0, nF = 0;
			String classification = "";
			for (int i = 0; i < knn; i++)
				if (distances[i].label.equals("normal"))
					nT++;
				else
					nF++;
			if (nT < nF)
				classification = "attack";
			else if (nT > nF)
				classification = "normal";
			// else
			// classification = Math.random() < 0.5; // if tie, randomly break
			// it

			// System.out.println(classification+" "+
			// testSamples.get(j).getPacketType());
			// count classification errors

			if(classification.equals("attack") && testSamples.get(j).getPacketType().equals("normal")){
				false_positive++;
			}
			
			if(classification.equals("normal") && testSamples.get(j).getPacketType().equals("attack")){
				false_negative++;
			}
			
			if (!classification.equals(testSamples.get(j).getPacketType())) {
				System.out.println(error + " out of " + j + " "
						+ testSamples.size() + " classified incorrectly.");
				error++;
			}
		}
		System.out.println(error + " out of " + testSamples.size()
				+ " classified incorrectly.");
		
		System.out.println("False positive : " + false_positive + " out of " + testSamples.size()
				+ " classified incorrectly.");
		
		System.out.println("False negative : " + false_negative + " out of " + testSamples.size()
				+ " classified incorrectly.");
		
		return error;
	}

}
