package tools;

import java.io.*;
import java.util.ArrayList;

import recommendationData.Recommendation;
import transitionData.*;

public class MainRecommender {
	public static void main (String args[]) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream in = null;
        TransitionSystemList allSystemsList = new TransitionSystemList();
        TransitionFrequencyList allFrequenciesList = new TransitionFrequencyList();
        try {
            // reads allSystems.data as an object file and stores its content on allSystemsList as well as allTransitions.data, stored on allFrequenciesList
            fis = new FileInputStream("data\\allSystems.data");
            in = new ObjectInputStream(fis);
            allSystemsList = (TransitionSystemList) in.readObject();
            fis = new FileInputStream("data\\allTransitions.data");
            in = new ObjectInputStream(fis);
            allFrequenciesList = (TransitionFrequencyList) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (allSystemsList.getAllSystems().isEmpty() || allFrequenciesList.getAllFrequencies().isEmpty()){
        	// If no transition systems where read yet
        	System.out.println("Your datafiles appear to be empty, please run the Reader first.");
        	System.out.println("Press the enter key to exit this program.");
        	System.in.read();
        	System.exit(0);
        }
        ArrayList<String> transitionSequence = new ArrayList<String>();
        // variable for transition Sequence
        int howMany = 1;
        // variable for amount of recommendations
        int weightFactor = 1;
        // variable for weightFactor, i.e. the natural number that denotes how little subsequences with smaller length shall be weighed.
        // The higher weightFactor, the lower they are weighed.
        BufferedReader br = null;
    	// iterate new BufferedReader in order to read config file
        String splitForSequence = ", ";
        // String by which the transition sequence will be separated
        try {
        	br = new BufferedReader(new InputStreamReader(new FileInputStream("data/config.txt"), "ISO-8859-1"));
        	br.readLine();
        	// skip first line as its just declarative
        	howMany = Integer.valueOf(br.readLine().toString());
        	// read next line as number of recommendations
        	if (howMany < 1){
        		howMany = 1;
        	}
        	// In case of false user input, fix howMany to 1
        	br.readLine();
        	// skip next line as its just declarative
        	weightFactor = Integer.valueOf(br.readLine().toString());
        	// read next line as weightFactor
        	if (weightFactor < 0){
        		weightFactor = 0;
        	}
        	// In case of false user input, fix weightFactor to 0
        	br.readLine();
        	// skip next line as its just declarative
        	String[] splitLine = br.readLine().toString().split(splitForSequence);
        	if (splitLine.length == 1){
        		// if only a single event transpired (also contains the case that no event transpired at all)
        		if (splitLine[0].equals("[]")){
        			// If no event transpired, use an empty list
        		}
        		else {
        			// if one event transpired
        			transitionSequence.add(splitLine[0].substring(1, splitLine[0].length()-1));
        			// add that event to the transition sequence, disregarding the "[" at the start and the "]" at the end and save it on transitionSequence
        		}
        	}
        	else {
        		// if more than one event transpired
	        	transitionSequence.add(splitLine[0].substring(1, splitLine[0].length()));
	        	// read first element of transition sequence, disregarding the "[" at the start, and save it on transitionSequence
	        	for (int i = 1; i < splitLine.length - 1 ; i++){
	        		transitionSequence.add(splitLine[i]);
	        	}
	        	// read 2nd to second-to-last element of transition sequence and save it on transitionSequence
	        	transitionSequence.add(splitLine[splitLine.length-1].substring(0, splitLine[splitLine.length-1].length()-1));
	        	// read last element of transition sequence, disregarding the "]" at the end, and save it on transitionSequence
        	}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // TODO: Create input for this as well as the number of recommendations.
        ArrayList<Recommendation> result = allSystemsList.recommendNextTransition(transitionSequence, howMany, weightFactor, allFrequenciesList);
        for (Recommendation recommendation : result){
        	recommendation.print();
        }
        System.out.println("RegPFA_Recommender finished. Press enter to continue.");
        System.in.read();
	}
}
