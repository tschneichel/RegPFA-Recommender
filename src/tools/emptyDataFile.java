package tools;

import java.io.*;

import transitionData.*;

public class emptyDataFile {
	public static void main (String args[]){
		TransitionSystemList testSystemList = new TransitionSystemList();
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
		}
		// Creates an empty transition system list for the initial stage of this program
	}
}
