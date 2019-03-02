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
            fis = new FileInputStream("allSystems.data");
            in = new ObjectInputStream(fis);
            allSystemsList = (TransitionSystemList) in.readObject();
            fis = new FileInputStream("allTransitions.data");
            in = new ObjectInputStream(fis);
            allFrequenciesList = (TransitionFrequencyList) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (allSystemsList.getAllSystems().isEmpty() || allFrequenciesList.getAllFrequencies().isEmpty()){
        	System.out.println("Your datafiles appear to be empty, please run the analyzer first.");
        	System.out.println("Press the enter key to exit this program.");
        	System.in.read();
        	System.exit(0);
        }
        // reads allSystems.data as an object file and stores its content on allSystemsList
        ArrayList<String> test = new ArrayList<String>();
        test.add("b(complete)");
        test.add("c(complete)");
        test.add("a(complete)");
        //test.add("e(complete)");
        //test.add("f(complete)");
        // TODO: Create input for this as well as the number of recommendations.
        ArrayList<Recommendation> result = allSystemsList.recommendNextTransition(test, 3, allFrequenciesList);
        for (Recommendation recommendation : result){
        	recommendation.print();
        }
        
	}
}
