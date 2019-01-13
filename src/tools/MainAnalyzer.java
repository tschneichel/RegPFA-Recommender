package tools;

import java.io.*;

import transitionData.*;

public class MainAnalyzer {
	public static void main (String args[]){
		/*TransitionSystemList testSystemList = new TransitionSystemList();
		try {
			FileOutputStream fileOutput = new FileOutputStream("allSystems.data");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			output.writeObject(testSystemList);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// KEEP THIS BLOCK OF CODE
		// Creates an empty transition system list for the initial stage of this program
		
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
        allSystemsList.print();
		TransitionSystem newSystem = new TransitionSystem();
		newSystem.printWholeSystem();
		System.out.println("");
		newSystem.printStatesWithTransitions();
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
