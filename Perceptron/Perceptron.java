import java.util.*;
import java.io.*;
public class Perceptron {
        public static void main(String[] args) {
        	
        		// read in from user
                Scanner input = new Scanner(System.in);
                System.out.println("Welcome to our first neural network - A perceptron net!");
                System.out.println("Enter 1 to train the perceptron using a training data file or 2 to use a trained weight settings file.");
                int fileNum = input.nextInt();
                String dataFilename;
                String weightsFilename;
                
                // if training net
                if (fileNum == 1) {
                        System.out.println("Enter the training data file name:");
                        dataFilename = input.next() + ".txt";
                        //dataFilename = "/Users/mayamcauliffe/Documents/workspace/Comp380Project1/src/initialTraining.txt";
                        System.out.println("Enter 0 to initialize weights to 0, enter 1 to initialize weights to random values between -0.5 and 0.5:");
                        double initWeight = input.nextDouble();
                        if (initWeight == 1) {
                                Random rand = new Random();
                                initWeight = rand.nextDouble() - 0.5;
                        }
                        System.out.println("Enter the maximum number of training epochs:");
                        int maxEpochs = input.nextInt();
                        System.out.println("Enter a file name to save the trained weight settings:");
                        //resultsFilename = ".txt";
                        weightsFilename = input.next() + ".txt";
                        System.out.println("Enter the learning rate alpha from (0, 1]:");
                        double alpha = input.nextDouble();
                        System.out.println("Enter the threshold theta:");
                        double theta = input.nextDouble();
                        input.close();
                        
                        // initialize scanner to read in from file
                        Scanner scanner = null;
                        System.out.println(dataFilename);
                        File file = new File(dataFilename);
                        try {
                                scanner = new Scanner(file);
                                
                        }
                        catch (Exception e) {
                                System.out.println(e.getMessage());
                        }
                        
                        int numInputs = scanner.nextInt() + 1;
                        scanner.nextLine();
                        int numOutputs = scanner.nextInt();
                        scanner.nextLine();
                        int numSets = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("num in: " + numInputs + ", num out: " + numOutputs + ", numSets: " + numSets);

                        // create array to store data and initialize net
                        Sample [] letters = new Sample [numSets];
                        Net net = new Net(alpha, theta, numInputs, numOutputs, initWeight);
                        
                        // read in data from file into array
                        for (int i = 0; i < numSets; i++) {
                        	letters[i] = new Sample(numInputs, numOutputs);
                        	scanner.nextLine();
                                for (int j = 0; j < numInputs - 1; j++) {
                                        letters[i].inputs[j] = scanner.nextInt();
                                }
                                letters[i].inputs[numInputs - 1] = 1;
                               
                                scanner.nextLine();
                                for (int j = 0; j < numOutputs; j++) {
                                        letters[i].outputs[j] = scanner.nextInt();
                                }
                                scanner.next();
                        }
                        
                        // run learning loop on every data sample
                        boolean converged = false;
                        int epoch = 0;
                        while(!converged && epoch < maxEpochs) {
                        	converged = true;
                        	for (int i = 0; i < numSets; i++) {
                        		if (!net.learn(letters[i].inputs, letters[i].outputs)) {
                        			converged = false;
                        		}
                        	}
                        	epoch++;
                        }
                        
                        if (converged) {
                        	System.out.println("training converged after " + epoch + " epochs.");
                        }
                        else {
                        	System.out.println("Training was stopped after " + epoch + " epochs.");
                        }
                        
                        // initialize printwriter to save weights
                        PrintWriter writer = null;
                        File results = new File(weightsFilename);
                        try {
                                writer = new PrintWriter(results);
                        }
                        catch (Exception e) {
                                System.out.println("File not found.");
                        }
                        
                        //print alpha, theta, and weights in file
                        writer.println(alpha);
                        writer.println(theta);
                        for (int i = 0; i < numOutputs; i++) {
                        	for (int j = 0; j < numInputs - 1; j++) {
                        		 writer.print(net.weights[i][j] + " ");
                        	}
                        	writer.println(net.weights[i][numInputs - 1]);
                        }
                        writer.close();
                       
                }
                
                // if net has been trained and we want to test it
                else {
                        System.out.println("Enter the trained weight settings input data file name:");
                        weightsFilename = "/Users/mayamcauliffe/Documents/workspace/Comp380Project1/src/sampleWeights.txt"; //input.next() + ".txt";
                        //weightsFilename = input.next() + ".txt";
                        //System.out.println("Enter 1 to test/deploy using a testing/deploying data file, enter 2 to quit:");
                        int cont = 1; //input.nextInt();
                        if (cont == 2) {
                                System.exit(0);
                        }
                        System.out.println("Enter the testing/deploying data file name:");
                        dataFilename =  "/Users/mayamcauliffe/Documents/workspace/Comp380Project1/src/sampleTestingMedNoise.txt";
                        //dataFilename = input.next() + ".txt";
                        System.out.println("Enter a file name to save the testing/deploying results:");
                        String resultsFilename = "/Users/mayamcauliffe/Documents/workspace/Comp380Project1/src/finalResults.txt";
                        //String resultsFilename = input.next() + ".txt";
                        input.close();
                        
                        // initialize scanner to read in data from file
                        Scanner scanner = null;
                        System.out.println(dataFilename);
                        File file = new File(dataFilename);
                        try {
                                scanner = new Scanner(file);
                                
                        }
                        catch (Exception e) {
                                System.out.println(e.getMessage());
                        }
                        
                        int numInputs = scanner.nextInt() + 1;
                        scanner.nextLine();
                        int numOutputs = scanner.nextInt();
                        scanner.nextLine();
                        int numSets = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("num in: " + numInputs + ", num out: " + numOutputs + ", numSets: " + numSets);

                        Sample [] letters = new Sample [numSets];
                       
                        for (int i = 0; i < numSets; i++) {
                        	letters[i] = new Sample(numInputs, numOutputs);
                        	scanner.nextLine();
                                for (int j = 0; j < numInputs - 1; j++) {
                                        letters[i].inputs[j] = scanner.nextInt();
                                }
                                letters[i].inputs[numInputs - 1] = 1;
                                scanner.nextLine();
                                scanner.nextLine();
                                if (scanner.hasNextLine()) {
                                	scanner.nextLine();
                                }
                        }
                        scanner.close();
                        
                        // initialize scanner to read in weights
                        File weights = new File(weightsFilename);
                        Scanner weightFile = null;
                        try {
                            weightFile = new Scanner(weights);
                            
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                       
                        // get values of alpha and theta and store weights
                        double alpha = weightFile.nextDouble();
                        double theta = weightFile.nextDouble();
                        Net net = new Net(alpha, theta, numInputs, numOutputs);
                        for (int i = 0; i < numOutputs; i++) {
                        	for (int j = 0; j < numInputs; j++) {

                        		net.weights[i][j] = weightFile.nextDouble();

                        	}
                        }
                        weightFile.close();
                        
                        // intialize printwriter to write results of classfication to file
                        PrintWriter writer = null;
                        File results = new File(resultsFilename);
                        try {
                                writer = new PrintWriter(results);
                        }
                        catch (Exception e) {
                                System.out.println("File not found.");
                        }
                        
                        // use net to classify samples
                        for (int i = 0; i < numSets; i++) {
                        	letters[i].outputs = net.classify(letters[i].inputs);
                        	writer.print(translate(letters[i].outputs) + " ");
                        	if (i == 6 || i == 13) {
                        		writer.println();
                        	}
                        }
                        writer.close();
                }
        }
        
        // translate output to letters
        public static char translate(int [] output) {
        	int count = 0;
        	int letter = 0;
        	for (int i = 0; i < output.length; i++) {
        		if (output[i] == 1) {
        			letter = i;
        			count++;
        		}
        	}
        	if (count > 1) {
        		return 'Z';
        	}
        	
        	if (letter == 0) {
        		return 'A';
        	}
        	else if (letter == 1) {
        		return 'B';
        	}
        	else if (letter == 2) {
        		return 'C';
        	}
        	else if (letter == 3) {
        		return 'D';
        	}
        	else if (letter == 4) {
        		return 'E';
        	}
        	else if (letter == 5) {
        		return 'J';
        	}
        	else if (letter == 6) {
        		return 'K';
        	}
        	
        	else return 'Z';
        }   
}

// class of each letter sample to store input and output array
class Sample {
	public int[] inputs;
	public int[] outputs;
	
	public Sample(int numInputs, int numOutputs) {
		this.inputs = new int [numInputs];
		this.outputs = new int [numOutputs];
	}
}

// class of neural net
class Net {
	public double[][] weights;
	private int numInputs;
	private int numOutputs;
	private double alpha;
	private double theta;
	private int[] output;
	
	// creates new untrained net
	public Net(double a, double t, int numInputs, int numOutputs, double initWeight) {
		this.alpha = a;
		this.theta = t;
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		this.weights = new double[numOutputs][numInputs];
		for (int i = 0; i < numOutputs; i++) {
			for (int j = 0; j < numInputs; j++) {
				this.weights[i][j] = initWeight;
			}
		}
	}
	
	// creates already trained net
	public Net(double a, double t, int numInputs, int numOutputs) {
		this.alpha = a;
		this.theta = t;
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		this.output = new int[numOutputs];
		this.weights = new double[numOutputs][numInputs];
	}
	
	// trains net
	public boolean learn(int[] inputs, int[] outputs) {
		boolean converged = true;
		double yin;
		double y;
		for (int i = 0; i < this.numOutputs; i++) {
			yin = 0;
			for (int j = 0; j < this.numInputs; j++) {
				yin += inputs[j] * weights[i][j];
			}
			
			y = learningFunction(yin);
			//System.out.println("y = " + y);
			if (y != outputs[i]) {
				converged = false;
				for (int j = 0; j < numInputs; j++) {
					weights[i][j] += alpha * outputs[i] * inputs[j];
				}
			}
		}
		return converged;	
	}
	
	// normalizes yin values against theta
	public int learningFunction(double yin) {
		if (yin > this.theta) {
			return 1;
		}
		else if (yin == this.theta) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	// classifies letters
	public int[] classify(int[] inputs) {
		double yin;
		double y;
		for (int i = 0; i < this.numOutputs; i++) {
			yin = 0;
			for (int j = 0; j < this.numInputs; j++) {
				yin += inputs[j] * weights[i][j];
			}
			this.output[i] = learningFunction(yin);
			
		}
		return output;
	}
}


    