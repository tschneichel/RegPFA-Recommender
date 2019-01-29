package tools;

import java.io.*;
import java.util.ArrayList;

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
        test.add("a(complete)");
        ArrayList<Recommendation> result = allSystemsList.recommendNextTransition(test);
        for (Recommendation recommendation : result){
        	recommendation.print();
        }
        
	}
}
