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
	
	
	public ArrayList<Recommendation> recommendNextTransition(ArrayList<String> currentTransitions, int howMany){
		// TODO: Make howMany flexible
		ArrayList<Recommendation> allRecommendations = new ArrayList<Recommendation>();
		int startingPoint = 0;
		// start with the first transition in the given list
		// placeholder variable for amount of recommendatons
		while (startingPoint < currentTransitions.size() && allRecommendations.size()<howMany){
			// iterate over the starting point in the given list of transitions and stop when either the end of the list was reached
			// OR enough possible following transitions were found
			ArrayList<Recommendation> currentRecommendations = new ArrayList<Recommendation>();
			for (TransitionSystem currentSystem : this.getAllSystems()){
				currentRecommendations.addAll(currentSystem.getRecommendations(currentTransitions));
			}
			for (Recommendation recommendation : currentRecommendations){
				recommendation.setProbability(recommendation.getProbability()+(currentTransitions.size()-startingPoint));
			}
			// Since longer sequences of transitions are weighed more heavily, inflate the probability by adding the length of the sequence
			allRecommendations.addAll(currentRecommendations);
			// before adding them to the lists of results
			startingPoint++;
		}
		// TODO: Sum up recommendations for same transition, weight recommendations so total percentage = 100 etc
		ArrayList<Recommendation> finalResult = new ArrayList<Recommendation>();
		if (allRecommendations.size() > howMany){
			for (int i = 0; i < howMany; i++){
				finalResult.add(allRecommendations.get(i));
			}
		}
		return finalResult;
	}
}
