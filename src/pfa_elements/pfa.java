package pfa_elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pfa implements Serializable{
	private ArrayList<state> states;
	private ArrayList<transitionOld> transitions;
	
	public ArrayList<state> getStates() {
		return states;
	}

	public void setStates(ArrayList<state> states) {
		this.states = states;
	}

	public ArrayList<transitionOld> getTransitions() {
		return transitions;
	}

	public void setTransitions(ArrayList<transitionOld> transitions) {
		this.transitions = transitions;
	}

	public pfa(){
		String pfaFile = "data/test_set.txt";
    	//String tsvFile = url + "\\PhraseStructureList.txt";									-> this line will be needed in productive mode
    	// ToDo: Ultimately, the file needs to be read from the JAR's location. Since we don't have a JAR currently,
    	// the file source used above has to be altered to suit the actual location
    	// Once we have the JAR, refer to the link below for possible fixes
    	// https://stackoverflow.com/questions/3627426/loading-a-file-relative-to-the-executing-jar-file
        //...
    	// iterate new BufferedReader in order to read tsvFile
    	BufferedReader br = null;
        String line = "";
        // split phraseStructureList tsvFile by comma
        String lineSplitBy = " ";
        try {
        	br = new BufferedReader(new InputStreamReader(new FileInputStream(pfaFile), "ISO-8859-1"));
        	while (!(line = br.readLine().toString()).equals(".state graph")){
        		}
			Map<String,state> nameToState = new HashMap<String, state>();
        	ArrayList<state> states = new ArrayList<state>();
        	ArrayList<transitionOld> transitions = new ArrayList<transitionOld>();
        	line = br.readLine();
        	String[] fullLine = line.split(lineSplitBy);
        	while (!fullLine[0].equals(".marking")) {
	        	if (!nameToState.containsKey(fullLine[0])){
	        		state tempState = new state();
	        		tempState.setLabel(fullLine[0]);
	        		nameToState.put(fullLine[0], tempState);
	        		states.add(tempState);
	        	}
	        	if (!nameToState.containsKey(fullLine[2])){
	        		state tempState = new state();
	        		tempState.setLabel(fullLine[2]);
	        		nameToState.put(fullLine[2], tempState);
	        		states.add(tempState);
	        	}
	        	transitions.add(new transitionOld(nameToState.get(fullLine[0]), nameToState.get(fullLine[2]), fullLine[1]));
	        	line = br.readLine();
	        	fullLine = line.split(lineSplitBy);
        	}
        	nameToState.get(fullLine[2]).setIsStart(true);
            this.setStates(states);
            this.setTransitions(transitions);
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
		System.out.println("The states in this PFA are:");
		for (int i = 0; i < this.getStates().size(); i++){
			if (this.getStates().get(i).getIsStart()){
				System.out.println(this.getStates().get(i).getLabel()+" (Starting state)");
			}
			else{
				System.out.println(this.getStates().get(i).getLabel());
			}

		}
		System.out.println("The transitions in this PFA are:");
		for (int i = 0; i < this.getTransitions().size(); i++){
			System.out.println(this.getTransitions().get(i).getSource().getLabel()+" connects to "+this.getTransitions().get(i).getTarget().getLabel()+" with event "+this.getTransitions().get(i).getLabel());
		}
	}
	
	
}
