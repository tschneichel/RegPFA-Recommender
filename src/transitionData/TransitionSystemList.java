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
	
	public void print(){
		for (int i = 0; i < this.getAllSystems().size(); i++){
			this.getAllSystems().get(i).printStatesWithTransitions();
		}
	}
	
	public ArrayList<Transition> recommendNextTransition(){
		
		return null;
	}
}
