package tools;

import java.io.*;

import transitionData.*;

public class MainAnalyzer {
	public static void main (String args[]){
		int max = 1;
		for (int i = 0; i < max; i++){
			//System.out.println(i);
			//long startTime = System.currentTimeMillis();
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
	            System.out.println("A Datafile was not found. Please check your data folder for \"allSystems.data\" and \"allTransitions.data.\"");
	        }
	        // reads allSystems.data as an object file and stores its content on allSystemsList as well as allTransitions.data and stores it on allFrequenciesList
			//System.out.println(System.currentTimeMillis()-startTime);
			//startTime = System.currentTimeMillis();
			String pfaFile = "data/test_set2.txt";
			TransitionSystem newSystem = new TransitionSystem(allFrequenciesList, pfaFile);
			allFrequenciesList.mergeSort();
			allFrequenciesList.updateMap();
			allFrequenciesList.print();
			// sorts the list of all transitions by the frequency of their appearance after the new transition system was read
			//newSystem.printWholeSystem();
			//System.out.println("");
			allSystemsList.getAllSystems().add(newSystem);			
			try {
				FileOutputStream fileOutput = new FileOutputStream("allSystems.data");
				ObjectOutputStream output = new ObjectOutputStream(fileOutput);
				output.writeObject(allSystemsList);
				fileOutput = new FileOutputStream("allTransitions.data");
				output = new ObjectOutputStream(fileOutput);
				output.writeObject(allFrequenciesList);
				output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// stores the list as allSystems.data for further program runs
			//System.out.println(System.currentTimeMillis()-startTime);
			//System.out.println();
		}
	}

}
