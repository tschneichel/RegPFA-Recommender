package transitionData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import recommendationData.Recommendation;

public class TransitionSystem implements Serializable{
	private static final long serialVersionUID = 1L;
	public Map<String, ArrayList<Transition>> nameToTransition = new HashMap<String, ArrayList<Transition>>();
	public Map<String, State> nameToState = new HashMap<String, State>();
	public ArrayList<String> stateNames;
	public ArrayList<String> transitionNames;
	public State startState;

	public Map<String, ArrayList<Transition>> getNameToTransition() {
		return nameToTransition;
	}

	public void setNameToTransition(Map<String, ArrayList<Transition>> nameToTransition) {
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
	
	public State getStartState() {
		return startState;
	}

	public void setStartState(State startState) {
		this.startState = startState;
	}

	public TransitionSystem(TransitionFrequencyList allTransitions, String pfaFile){
    	BufferedReader br = null;
    	// iterate new BufferedReader in order to read tsml file
        String splitForStates = " ";
        // Char by which lines concerning state names in tsml-files can be separated
        String splitForTransitions = "\"";
        // Char by which lines concerning transitions in tsml-files can be separated
        this.setTransitionNames(new ArrayList<String>());
        this.setStateNames(new ArrayList<String>());
        this.setNameToState(new HashMap<String, State>());
        this.setNameToTransition(new HashMap<String, ArrayList<Transition>>());
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
        			tempState.setLabel(stateName.toLowerCase());
        			tempState.setStartState(false);
        			// Create a new state with label according to the state's name in the tsml-file and mark it as non start state for now
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
        			tempTransition.setSource(nameToState.get(splitLine[3]));
        			tempTransition.setTarget(nameToState.get(splitLine[5]));
        			// Therefore, create new transition linking to states to each other
        			for (int i = 0; i <= 1; i++){
        				br.readLine();
        			}
        			// skip two lines
        			splitLine = br.readLine().split("<");
        			// read next line contains the label and probability of the transition
        			String tempLabel = splitLine[0];
        			int splitIndicator = tempLabel.lastIndexOf('[')+1;
        			// probability will be of the form [value], so finding the position of the last "[" will indicate where probability begins
        			Double probability = Double.valueOf(tempLabel.substring(splitIndicator, tempLabel.length()-1));
        			// transform probability into a Double and store it
        			tempLabel = tempLabel.substring(0, splitIndicator-2).toLowerCase();
        			// cut the probability off from the transition name
        			tempTransition.setLabel(tempLabel);
        			// add the label to the transition
        			if (!transitionNames.contains(tempLabel)){
        				transitionNames.add(tempLabel);
        			}
        			// and to the list of all transition names
        			tempTransition.setProbability(probability);
        			// Sets the probability of current transition
        			if (this.nameToTransition.containsKey(tempLabel)){
        				ArrayList<Transition> tempTransitionList = this.nameToTransition.get(tempLabel);
        				tempTransitionList.add(tempTransition);
        				this.nameToTransition.put(tempLabel, tempTransitionList);
        				// If there already exists a transition with the same name in the transition system,
        				// add the current transition to the list of transitions derived from that name
        				
        			}
        			else{
        				ArrayList<Transition> tempTransitionList = new ArrayList<Transition>();
        				tempTransitionList.add(tempTransition);
        				this.nameToTransition.put(tempLabel, tempTransitionList);
        				// else create a new entry for this transition name
        			}
        			State sourceState = tempTransition.getSource();
        			State targetState = tempTransition.getTarget();
        			if (sourceState.getTransitionsTo().containsKey(tempLabel)){
        				sourceState.addTransitionTo(tempTransition);
        				// If there already exists a transition with the same name from the source state,
        				// add the current transition to the list of transitions derived from that name
        			}
        			else{
        				ArrayList<Transition> tempTransitionList = new ArrayList<Transition>();
        				tempTransitionList.add(tempTransition);
        				sourceState.transitionsTo.put(tempLabel, tempTransitionList);
        				// else create a new entry for this transition name
        			}
        			
        			if (targetState.getTransitionsFrom().containsKey(tempLabel)){
        				targetState.addTransitionFrom(tempTransition);
        				// If there already exists a transition with the same name towards the target state,
        				// add the current transition to the list of transitions derived from that name
        			}
        			else{
        				ArrayList<Transition> tempTransitionList = new ArrayList<Transition>();
        				tempTransitionList.add(tempTransition);
        				targetState.transitionsFrom.put(tempLabel, tempTransitionList);
        				// else create a new entry for this transition name
        			}
        			if (allTransitions.getLabelToPosition().containsKey(tempLabel)){
        				allTransitions.getAllFrequencies().get(allTransitions.getLabelToPosition().get(tempLabel)).incrementFrequency();
        				// if the current transition was already encountered in any system, increase its frequency by 1
        			}
        			else {
        				allTransitions.getAllFrequencies().add(new TransitionFrequency(tempLabel, 1));
        				// else create a new entry for it in the list of all transitions and their respective frequencies
        				allTransitions.getLabelToPosition().put(tempLabel, allTransitions.getAllFrequencies().size()-1);
        				// as well as in the map of transition names to their positions in the list above
        			}
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
	
	public void printWholeSystem(){
		System.out.println("The transition system consists of the following states:");
		for (int i = 0; i < this.stateNames.size(); i++){
			System.out.println(this.nameToState.get(stateNames.get(i)).getLabel()+" with probability "+this.nameToState.get(stateNames.get(i)).getProbability());
		}
		System.out.println("And the following transitions:");
		for (int i = 0; i < this.transitionNames.size(); i++){
			for (int j = 0; j < this.nameToTransition.get(this.transitionNames.get(i)).size(); j++){
				System.out.print(this.nameToTransition.get(transitionNames.get(i)).get(j).getSource().getLabel());
				System.out.print(" is linked to ");
				System.out.print(this.nameToTransition.get(transitionNames.get(i)).get(j).getTarget().getLabel());
				System.out.print(" via ");
				System.out.print(this.nameToTransition.get(transitionNames.get(i)).get(j).getLabel());
				System.out.print(" with probability ");
				System.out.println(this.nameToTransition.get(transitionNames.get(i)).get(j).getProbability());
			}

		}
		
	}
	public ArrayList<Recommendation> getRecommendations (ArrayList<String> sequence){
		// This method creates a list of all possible recommendations for the next transition after a certain sequence of transitions already occured 
		// based on a single transition system
		ArrayList<Recommendation> result = new ArrayList<Recommendation>();
		// pre-creating the return element
		for (String event : sequence){
			// for all transitions in the sequence
			if (!this.getTransitionNames().contains(event)){
				return result;
			}
			// if the transition does not exist in the transition system, a match can't be found and hence an empty list is returned
		}
		for (Transition transition : this.getNameToTransition().get(sequence.get(0))){
			// iterate over all transitions of that name
			Double probability;
			if (transition.getSource().getProbability() == 0.0){
				// if the probability of the source state is 0, i.e. the transition system contains a circle / is incomplete
				probability = transition.getProbability();
				// set starting probability for the recommendation to that of the the transition
			}
			else {
				// if the probability of the source state is not 0
				probability = transition.getProbability() * transition.getSource().getProbability();
				// set starting probability of the recommendations to that of the transition multiplied by the source state's probability
			}
			if (sequence.size() == 1){
				ArrayList<Recommendation> recommendations = transition.getTarget().getFinalRecommendations(probability);
				result.addAll(recommendations);
			}
			else {
				ArrayList<Recommendation> recommendations = transition.getTarget().getRecommendations(new ArrayList<String> (sequence.subList(1, sequence.size())), probability);
				// and check whether it's possible to move through the transition system by following the sequence of transitions.
				result.addAll(recommendations);
			}				
			// all possible next transitions and their probabilities are stored in result
		}
		return result;
	}
	
	public void setStateProbabilities(){
		// this method adds the probability to land on each state when traversing the transition system randomly to each state
		boolean startStateFound = false;
		int stateIndex = 0;
		ArrayList<State> allStates = new ArrayList<State>();
		// pre-creating variables
		for (int i = 0; i < this.getStateNames().size(); i++){
			// creates ArrayList of all states in the system for ease of use
			allStates.add(this.getNameToState().get(this.getStateNames().get(i)));
		}
		while (!startStateFound && stateIndex < allStates.size()){
			// iterate over all states and find the starting state, i.e. the state that has no transitions pointing towards it
			if (allStates.get(stateIndex).getTransitionsFrom().isEmpty()){
				allStates.get(stateIndex).setStartState(true);
				// mark it as the start state
				allStates.get(stateIndex).setProbability(1.0);
				// set its probability to 1
				startStateFound = true;
				// end the while loop by falsifying the condition
				this.setStartState(allStates.get(stateIndex));
				// set start state of the transition system
			}
			stateIndex++;
		}
		boolean newPositions = true;
		// boolean used to stop while loop prematurely if a circle exists in the transition system
		while (!(allStates.size() == 1) && newPositions){
			// while not all state probabilities have been set
			ArrayList<Integer> positions = new ArrayList<Integer>();
			// ArrayList that stores the positions of all states that were used in the next step to set probabilities for other states
			for (int i = 0; i < allStates.size(); i++){
				// iterate over all states that have not been used to set state probabilities of other states yet
				if (!(allStates.get(i).probability == 0.0)){
					// if the probability of the current state is not 0, i.e. the state's probabilities has been set already
					boolean transitionsCovered = true;
					for (String s : allStates.get(i).getTransitionsFrom().keySet()){
						for (Transition t : allStates.get(i).getTransitionsFrom().get(s)){
							// iterate over all transitions to the state
							if (t.getUsed() == false){
								transitionsCovered = false;
								// if at least one transition to the state was not used to generate probabilities yet, the state can not be used
								// to set probabilities for other states yet
							}
						}
					}
					if (transitionsCovered){
						// if all transitions pointing to the current state were used to generate probabilities
						positions.add(i);
						// add the state to probabilities
					}
				}
			}
			if (positions.isEmpty()){
				// if no states were found that fulfill the conditions outlined above, a circle must exist in the transition system.
				// Therefore, stop setting state probabilities by ending the while loop
				newPositions = false;
			}
			else{
				for (int i = positions.size()-1; i >= 0; i--){
					for (String s : allStates.get(positions.get(i)).getTransitionsTo().keySet()){
						for (Transition t : allStates.get(positions.get(i)).getTransitionsTo().get(s)){
							// iterate over all transitions from the state towards other states
							t.setUsed(true);
							// mark the transition as used to generate new state probabilities
							t.getTarget().setProbability(t.getTarget().getProbability() + t.getSource().getProbability() * t.getProbability());
							// add the probability of the source state of t multiplied by the probability of t to the probability of the target state of t
						}
					}
					State tempState = allStates.get(positions.get(i));
					allStates.remove(tempState);
				}
				// otherwise, remove all states that were used to set other state positions from the list of states and continue looping
			}
		}
	}
	
}
