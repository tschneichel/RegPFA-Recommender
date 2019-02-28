package tools;

import java.io.*;

import transitionData.*;

public class MainAnalyzer {
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
        TransitionFrequencyList allTransitions = new TransitionFrequencyList();
        //TODO: Create Input / Output Stream for this as well
		TransitionSystem newSystem = new TransitionSystem(allTransitions);
		allTransitions.sort();
		allTransitions.print();
		// sorts the list of all transitions by the frequency of their appearance after the new transition system was read
		newSystem.printWholeSystem();
		System.out.println("");
		allSystemsList.getAllSystems().add(newSystem);
		// TODO: Change file location: reads a new transition system from <<file location to be specified>> and stores it on the list
		
		try {
			FileOutputStream fileOutput = new FileOutputStream("allSystems.data");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			output.writeObject(allSystemsList);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// stores the list as allSystems.data for further program runs


	}

}
