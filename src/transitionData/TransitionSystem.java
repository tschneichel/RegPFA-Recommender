package transitionData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransitionSystem {
	public Map<String,Transition> nameToTransition = new HashMap<String, Transition>();
	public Map<String, State> nameToState = new HashMap<String, State>();
	public ArrayList<String> stateNames;
	public ArrayList<String> transitionNames;

	public Map<String, Transition> getNameToTransition() {
		return nameToTransition;
	}

	public void setNameToTransition(Map<String, Transition> nameToTransition) {
		this.nameToTransition = nameToTransition;
	}
	
	
	public Map<String, State> getNameToState() {
		return nameToState;
	}

	public void setNameToState(Map<String, State> nameToState) {
		this.nameToState = nameToState;
	}
	
	public ArrayList<String> getStateNames() {
		return stateNames;
	}

	public void setStateNames(ArrayList<String> stateNames) {
		this.stateNames = stateNames;
	}

	public ArrayList<String> getTransitionNames() {
		return transitionNames;
	}

	public void setTransitionNames(ArrayList<String> transitionNames) {
		this.transitionNames = transitionNames;
	}

	public TransitionSystem(){
		String pfaFile = "data/test_set.txt";
    	// ToDo: Ultimately, the file needs to be read from the JAR's location. Since we don't have a JAR currently,
    	// the file source used above has to be altered to suit the actual location
    	// Once we have the JAR, refer to the link below for possible fixes
    	// https://stackoverflow.com/questions/3627426/loading-a-file-relative-to-the-executing-jar-file
        //...
    	BufferedReader br = null;
    	// iterate new BufferedReader in order to read tsvFile
        String splitForStates = " ";
        // Char by which lines concerning state names in tsml-files can be separated
        String splitForTransitions = "\"";
        // Char by which lines concerning transitions in tsml-files can be separated
        this.setTransitionNames(new ArrayList<String>());
        this.setStateNames(new ArrayList<String>());
        this.setNameToState(new HashMap<String, State>());
        this.setNameToTransition(new HashMap<String, Transition>());
        try {
        	br = new BufferedReader(new InputStreamReader(new FileInputStream(pfaFile), "ISO-8859-1"));
        	String[] splitLine = br.readLine().toString().split(splitForStates);
        	while (!(splitLine[0].equals("<transition"))){
        		// While the transitions-part of the tsml-file has not yet been reached
        		if (splitLine[0].equals("<state")){
        			// If the first part of the current line is of form <state, it will define the state's name
        			String stateName = splitLine[1].substring(4, splitLine[1].length()-2);
        			// Remove unnecessary chars to retrieve the state's name
        			State tempState = new State();
        			tempState.setLabel(stateName);
        			// Create a new state with label according to the state's name in the tsml-file
        			nameToState.put(stateName, tempState);
        			stateNames.add(stateName);
        			// And put it in the map of all states, as well as its name in the List of all state names
        		}
        		splitLine = br.readLine().toString().split(splitForStates);
        		// read next line so while loop continues
        	}
        	// While loop ends when the first line of a transition-statement in the tsml-file was reached
        	// Now we have to separate the line differently, therefore we need to restructure the current String Array splitLine
        	StringBuilder builder = new StringBuilder();
        	for (String string : splitLine){
        		if (builder.length() > 0){
        			builder.append(" ");
        		}
        		builder.append(string);
        	}
        	splitLine = builder.toString().split(splitForTransitions);
        	// Changes splitLine to be separated by quotation marks instead of a blank space
        	while (!(splitLine[0].equals("</tsml>"))){
        		// While the end of the tsml-file has not yet been reached
        		if (splitLine[0].equals("<transition id=")){
        			// If the current line is of form <transition id=, it will define the transition's name
        			Transition tempTransition = new Transition();
        			String tempLabel = splitLine[1];
        			tempTransition.setLabel(tempLabel);
        			tempTransition.setSource(nameToState.get(splitLine[3]));
        			tempTransition.setTarget(nameToState.get(splitLine[5]));
        			// Therefore, create new transition linking to states to each other
        			transitionNames.add(tempLabel);
        			// Also add the label of the current transition to the list of all transition names
        			for (int i = 0; i <= 1; i++){
        				br.readLine();
        			}
        			// skip two lines
        			splitLine = br.readLine().split("<");
        			// read next line contains the label of the transition
        			tempTransition.setLabel(splitLine[0]);
        			// add the label to the transition
        			br.readLine();
        			// skip another line
        			splitLine = br.readLine().split("<");
        			if (splitLine[1].equals("graphics>")){
        				splitLine = br.readLine().split("<");
        				while (!splitLine[1].equals("/graphics>")){
        					splitLine = br.readLine().split("<");
        				}
        				splitLine = br.readLine().split("<");
        			}
        			tempTransition.setProbability(Float.valueOf(splitLine[1].substring(12, splitLine[1].length())));
        			// Sets the probability of current transition 
        			this.nameToTransition.put(tempLabel, tempTransition);
        		}
        		splitLine = br.readLine().toString().split(splitForTransitions);
        		// read next line so while loop continues
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
	}
	
	public void print(){
		System.out.println("The transition system consists of the following states:");
		for (int i = 0; i < this.stateNames.size(); i++){
			System.out.println(this.nameToState.get(stateNames.get(i)).getLabel());
		}
		System.out.println("And the following transitions:");
		for (int i = 0; i < this.transitionNames.size(); i++){
			System.out.print(this.nameToTransition.get(transitionNames.get(i)).getSource().getLabel());
			System.out.print(" is linked to ");
			System.out.print(this.nameToTransition.get(transitionNames.get(i)).getTarget().getLabel());
			System.out.print(" via ");
			System.out.println(this.nameToTransition.get(transitionNames.get(i)).getLabel());
		}
	}
}
