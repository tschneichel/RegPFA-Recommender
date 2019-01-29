package transitionData;

import java.util.ArrayList;

public class TransitionSequence {
	public ArrayList<Transition> allTransitions;
	public float totalProbability;
	public ArrayList<Transition> getAllTransitions() {
		return allTransitions;
	}
	public void setAllTransitions(ArrayList<Transition> allTransitions) {
		this.allTransitions = allTransitions;
	}
	public float getTotalProbability() {
		return totalProbability;
	}
	public void setTotalProbability(float totalProbability) {
		this.totalProbability = totalProbability;
	}
	
	public TransitionSequence(){
		this.setAllTransitions(new ArrayList<Transition>());
		this.setTotalProbability(1);
	}
	
	
}
