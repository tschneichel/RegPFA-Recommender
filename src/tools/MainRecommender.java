package tools;

import java.io.*;
import java.util.ArrayList;

import recommendationData.Recommendation;
import transitionData.*;

public class MainRecommender {
	public static void main (String args[]){
        FileInputStream fis = null;
        ObjectInputStream in = null;
        TransitionSystemList allSystemsList = new TransitionSystemList();
        try {
            fis = new FileInputStream("allSystems.data");
            in = new ObjectInputStream(fis);
            allSystemsList = (TransitionSystemList) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // reads allSystems.data as an object file and stores its content on allSystemsList
        ArrayList<String> test = new ArrayList<String>();
        test.add("d(complete)");
        test.add("c(complete)");
        test.add("a(complete)");
        //test.add("e(complete)");
        //test.add("f(complete)");
        ArrayList<Recommendation> result = allSystemsList.recommendNextTransition(test, 50);
        for (Recommendation recommendation : result){
        	recommendation.print();
        }
        
	}
}
