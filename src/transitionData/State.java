package transitionData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pfa_elements.transitionOld;

public class State implements Serializable {
	private String label;
	public Map<String, ArrayList<Transition>> transitionsTo = new HashMap<String, ArrayList<Transition>>();
	public Map<String, ArrayList<Transition>> transitionsFrom = new HashMap<String, ArrayList<Transition>>();
	
	public State(){
		this.setTransitionsFrom(new HashMap<String, ArrayList<Transition>>());
		this.setTransitionsTo(new HashMap<String, ArrayList<Transition>>());
	}
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	


	public Map<String, ArrayList<Transition>> getTransitionsTo() {
		return transitionsTo;
	}


	public void setTransitionsTo(Map<String, ArrayList<Transition>> transitionsTo) {
		this.transitionsTo = transitionsTo;
	}


	public Map<String, ArrayList<Transition>> getTransitionsFrom() {
		return transitionsFrom;
	}


	public void setTransitionsFrom(Map<String, ArrayList<Transition>> transitionsFrom) {
		this.transitionsFrom = transitionsFrom;
	}
	
	public void addTransitionTo (Transition transition){
		ArrayList<Transition> newList = this.getTransitionsTo().get(transition.getLabel());
		newList.add(transition);
		this.transitionsTo.put(transition.getLabel(), newList);
	}
	
	public void addTransitionFrom (Transition transition){
		ArrayList<Transition> newList = this.getTransitionsFrom().get(transition.getLabel());
		newList.add(transition);
		this.transitionsFrom.put(transition.getLabel(), newList);
	}
	
	public boolean containsSequence (ArrayList<String> sequence){
		if (sequence.isEmpty()){
			return true;
		}
		if (this.getTransitionsTo().containsKey(sequence.get(0))){
			
		}
		return containsSequence (new ArrayList<String> (sequence.subList(1, sequence.size()-1)));
	}
	
}
