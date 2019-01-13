package transitionData;

import java.io.Serializable;
import java.util.ArrayList;

import pfa_elements.transitionOld;

public class State implements Serializable {
	private boolean isStart;
	private String label;
	private ArrayList<Transition> transitions;
	
	public State(){
		this.setIsStart(false);
		this.setTransitions(new ArrayList<Transition>());
	}
	
	public boolean getIsStart() {
		return isStart;
	}
	public void setIsStart(boolean isStart) {
		this.isStart = isStart;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public ArrayList<Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}
}
