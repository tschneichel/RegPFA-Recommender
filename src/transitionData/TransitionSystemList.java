package transitionData;

import java.io.Serializable;
import java.util.ArrayList;

import recommendationData.Recommendation;
import recommendationData.RecommendationList;

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
		RecommendationList allRecommendations = new RecommendationList();
		int startingPoint = 0;
		int endPoint = currentTransitions.size();
		// start with the first transition in the given list
		while (startingPoint < endPoint && allRecommendations.getRecommendations().size()<howMany){
			// iterate over the starting point in the given list of transitions and stop when either the end of the list was reached
			// OR enough possible following transitions were found
			ArrayList<Recommendation> currentRecommendations = new ArrayList<Recommendation>();
			for (TransitionSystem currentSystem : this.getAllSystems()){
				currentRecommendations.addAll(currentSystem.getRecommendations(currentTransitions));
			}
			for (Recommendation recommendation : currentRecommendations){
				recommendation.setProbability(recommendation.getProbability()+currentTransitions.size());
			}
			// Since longer sequences of transitions are weighed more heavily, inflate the probability by adding the length of the sequence
			allRecommendations.getRecommendations().addAll(currentRecommendations);
			// before adding them to the lists of results
			currentTransitions.remove(0);
			// removing the first element of transition sequence to only search for shorter sequences now
			startingPoint++;
		}
		allRecommendations.mergeRecommendations();
		// TODO: Sum up recommendations for same transition, weight recommendations so total percentage = 100 etc
		RecommendationList finalResult = new RecommendationList();
		if (allRecommendations.getRecommendations().size() > howMany){
			for (int i = 0; i < howMany; i++){
				finalResult.getRecommendations().add(allRecommendations.getRecommendations().get(i));
			}
		}
		else finalResult = allRecommendations;
		finalResult.weighRecommendations();
		return finalResult.getRecommendations();
	}
}
