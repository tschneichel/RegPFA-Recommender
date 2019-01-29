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
	
	
	public ArrayList<Recommendation> recommendNextTransition(ArrayList<String> currentTransitions){
		ArrayList<Recommendation> allRecommendations = new ArrayList<Recommendation>();
		int startingPoint = 0;
		// start with the first transition in the given list
		int howMany = 5;
		// TODO: Make this flexible
		// placeholder variable for amount of recommendatons
		while (startingPoint < currentTransitions.size() && allRecommendations.size()<howMany){
			// iterate over the starting point in the given list of transitions and stop when either the end of the list was reached
			// OR enough possible following transitions were found
			for (TransitionSystem currentSystem : this.getAllSystems()){
				allRecommendations.addAll(currentSystem.getRecommendations(currentTransitions));
			}
			startingPoint++;
		}
		// TODO: Sum up recommendations for same transition, weight recommendations so total percentage = 100 etc
		return allRecommendations;
	}
}
