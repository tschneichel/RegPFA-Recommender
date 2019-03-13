package tools;

import java.io.*;

import transitionData.*;

public class MainAnalyzer {
	public static void main (String args[]) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream in = null;
        TransitionSystemList allSystemsList = new TransitionSystemList();
        TransitionFrequencyList allFrequenciesList = new TransitionFrequencyList();
        try {
            fis = new FileInputStream("data\\allSystems.data");
            in = new ObjectInputStream(fis);
            allSystemsList = (TransitionSystemList) in.readObject();
            fis = new FileInputStream("data\\allTransitions.data");
            in = new ObjectInputStream(fis);
            allFrequenciesList = (TransitionFrequencyList) in.readObject();
            in.close();
        } catch (Exception ex) {
            System.out.println("A Datafile was not found. Please check your data folder for \"allSystems.data\" and \"allTransitions.data.\"");
        }
        // reads allSystems.data as an object file and stores its content on allSystemsList as well as allTransitions.data and stores it on allFrequenciesList
		String path = "data\\";
		File dir = new File(path);
		// Select data folder
		File[] directoryListing = dir.listFiles();
		// Store all file names in the data folder on directoryListing
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().endsWith(".tsml")){
		    		String pfaFile = path.concat(child.getName());
					TransitionSystem newSystem = new TransitionSystem(allFrequenciesList, pfaFile);
					// creates new transition system from input .tsml file
					allFrequenciesList.mergeSort();
					allFrequenciesList.updateMap();
					// sorts the list of all transitions by the frequency of their appearance after the new transition system was read and updates the corresponding map
					allSystemsList.getAllSystems().add(newSystem);
					child.renameTo(new File("archive\\"+child.getName()));
					//newSystem.printWholeSystem();
				}
			}
		  } else {
			  System.out.println("Data directory does not exist. Please create a folder named \"data\" in the folder where the JAR is located or run EmptyDataFile.jar.");
		  }
		  try {
			FileOutputStream fileOutput = new FileOutputStream("data\\allSystems.data");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			output.writeObject(allSystemsList);
			fileOutput = new FileOutputStream("data\\allTransitions.data");
			output = new ObjectOutputStream(fileOutput);
			output.writeObject(allFrequenciesList);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// stores the lists for further program runs
		System.out.println("RegPFA_Analzyer finished. Press enter to continue.");
		System.in.read();
	}

}
