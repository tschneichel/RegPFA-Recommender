package pfa_elements;

import java.io.Serializable;
import java.util.ArrayList;

public class state implements Serializable{
	private boolean isStart;
	private String label;
	private ArrayList<transitionOld> transitions;
	
	public state(){
		isStart = false;
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
	public ArrayList<transitionOld> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<transitionOld> transitions) {
		this.transitions = transitions;
	}
	
	
	
	
}
