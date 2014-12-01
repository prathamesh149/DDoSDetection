package rbf_neural_network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.RBFNetwork;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.nnet.learning.RBFLearning;


public class RBFClassification implements LearningEventListener {
    
    /**
     *  Runs this sample
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {    
          (new RBFClassification()).run();
    }
    
    
    public void run() throws IOException {
        // get the path to file with data
        String inputFileName = "src//rbf_neural_network//udp_rbf_training_file_ucla.txt";
        
        // create MultiLayerPerceptron neural network
        RBFNetwork neuralNet = new RBFNetwork(7, 6, 1);
        
        // create training set from file
        DataSet trainingDataSet = DataSet.createFromFile(inputFileName, 7, 1, ",", false);

        RBFLearning learningRule = ((RBFLearning)neuralNet.getLearningRule());
        learningRule.setLearningRate(0.02);
        learningRule.setMaxError(0.1);
        learningRule.addListener(this);
                
        // train the network with training set
        neuralNet.learn(trainingDataSet);      
                        
        System.out.println("Done training.");
        System.out.println("Testing network...");
        
        //create training set from file
        String testFileName = "src//rbf_neural_network//udp_rbf_test_file_ucla.txt";
        DataSet testDataSet = DataSet.createFromFile(testFileName, 7, 1, ",", false);
        
        testNeuralNetwork(neuralNet, testDataSet);                
        //testNeuralNetwork(neuralNet, trainingDataSet);
    }
    
    /**
     * Prints network output for the each element from the specified training set.
     * @param neuralNet neural network
     * @param testSet test data set
     * @throws IOException 
     */
    public void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) throws IOException {
        FileWriter fw = new FileWriter("src//rbf_neural_network//udp_test_output_file_ucla.txt");
        FileReader fr = new FileReader("src//rbf_neural_network//udp_rbf_test_file_ucla.txt");
        
        BufferedWriter bw = new BufferedWriter(fw);
        BufferedReader br = new BufferedReader(fr);
        int false_negative = 0;
        int false_positive = 0;
        
    	for(DataSetRow testSetRow : testSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            bw.write("Input: " + Arrays.toString( testSetRow.getInput() ) );
            bw.write("Output: " + networkOutput[0]);
            bw.newLine();
            
            Double predicted_output = networkOutput[0];
            Double actual_output = Double.parseDouble(br.readLine().split(",")[7]);
            
            if (actual_output == 0 && predicted_output < -0.5){
            	false_negative++;
            	System.out.println("False negative: " + false_negative);
            } 
            
            if (actual_output == -1 && predicted_output > -0.5){
            	false_positive++;
            	System.out.println("False negative: " + false_positive);
            } 
            
            System.out.print("Input: " + Arrays.toString( testSetRow.getInput() ) );
            System.out.println(" Output: " + Arrays.toString( networkOutput) );
        }
    	
    	System.out.println("Final false positive " + false_positive + " of " + testSet.size());
    	System.out.println("Final false negative " + false_negative + " of " + testSet.size());
    	
    	
    	bw.close();
    	br.close();
    }  
    
    @Override
    public void handleLearningEvent(LearningEvent event) {
        LMS lr = (LMS) event.getSource();
        System.out.println(lr.getCurrentIteration() + ". iteration | Total network error: " + lr.getTotalNetworkError());
    }    
    
}
