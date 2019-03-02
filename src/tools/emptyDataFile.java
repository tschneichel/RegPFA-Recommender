package tools;

import java.io.*;
import java.util.Scanner;

import transitionData.*;

public class emptyDataFile {
	public static void main (String args[]) throws IOException{
		InputStream stream = System.in;
		Scanner scanner = new Scanner(stream);
		String input = "blubb";
		while (!input.equals("y") && !input.equals("n")){
			System.out.println("Do you really want to delete all contents of your datafiles? Type \"y\" for yes and \"n\" for no.");
			input = scanner.next();
		}
		if (input.equals("y")){
			TransitionSystemList testSystemList = new TransitionSystemList();
			TransitionFrequencyList testFrequencyList = new TransitionFrequencyList();
			try {
				FileOutputStream fileOutput = new FileOutputStream("allSystems.data");
				ObjectOutputStream output = new ObjectOutputStream(fileOutput);
				output.writeObject(testSystemList);
				fileOutput = new FileOutputStream("allTransitions.data");
				output = new ObjectOutputStream(fileOutput);
				output.writeObject(testFrequencyList);
				output.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Creates an empty transition system list for the initial stage of this program
			System.out.println("The contents of your datafiles were deleted. Press the enter key to continue.");
			System.in.read();
		}
		else {
		System.out.println("Program stopped. Nothing was deleted. Press the enter key to continue.");
		System.in.read();
		}
		scanner.close();
	}
	

}
