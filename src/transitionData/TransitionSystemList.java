package transitionData;

import java.io.Serializable;
import java.util.ArrayList;

public class TransitionSystemList implements Serializable{
	public ArrayList<TransitionSystem> allSystems;

	public ArrayList<TransitionSystem> getAllSystems() {
		return allSystems;
	}

	public void setAllSystems(ArrayList<TransitionSystem> allSystems) {
		this.allSystems = allSystems;
	}
	
	public TransitionSystemList(){
		ArrayList<TransitionSystem> allSystems = new ArrayList<TransitionSystem>();
		this.setAllSystems(allSystems);
	}
	
	
	public ArrayList<Transition> recommendNextTransition(ArrayList<String> currentTransitions){
		ArrayList<TransitionSequence> allSequences = new ArrayList<TransitionSequence>();
		int startingPoint = 0;
		// start with the first transition in the given list
		int howMany = 5;
		// placeholder variable for amount of recommendatons
		while (startingPoint < currentTransitions.size() && allSequences.size()<howMany){
			// iterate over the starting point in the given list of transitions and stop when either the end of the list was reached
			// OR enough possible following transitions were found
			for (TransitionSystem currentSystem : this.getAllSystems()){
				// iterate over all saved transition systems
				
				
				
				String firstTransition = currentTransitions.get(startingPoint);
				if (currentSystem.getNameToTransition().containsKey(firstTransition)){
					// check whether the transition system even contains the first transition we are looking at
					
					// Clusterfuck. WAHRSCHEINLICH INT ARRAY IN GRÖßE DES EINGEGEBENEN STRINGARRAYS ANGEBEN, DAS JEWEILS DIE POSITION IN DER
					// ENTSPRECHENDEN TIEFE SPEICHERT. SOLLTE GEHEN...
					
					TransitionSequence tempSequence = new TransitionSequence();
					// If it does, create an empty sequence of transitions
					int testedTransition = 0;
					// as well as a counter for which Transition is being tested
					ArrayList<Transition> possibleFirstJumps = currentSystem.getNameToTransition().get(firstTransition);
					// and also save the list of all possible following transitions (jumps) on possibleFirstJumps
					boolean stillJumping = true;
					for (int jumpCounter = 0; jumpCounter < possibleFirstJumps.size(); jumpCounter++){
						
					}
				}
			}
			startingPoint++;
		}

		return null;
	}
}
