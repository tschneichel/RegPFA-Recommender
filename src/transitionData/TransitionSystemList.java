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
	
	
	public ArrayList<Recommendation> recommendNextTransition(ArrayList<String> currentTransitions, int howMany, TransitionFrequencyList allFrequencies){
		// TODO: Make howMany flexible
		RecommendationList allRecommendations = new RecommendationList();
		int startingPoint = 0;
		int endPoint = currentTransitions.size();
		ArrayList<String> uniqueFound = new ArrayList<String>();
		// start with the first transition in the given list
		while (startingPoint < endPoint && uniqueFound.size()<howMany){
			// iterate over the starting point in the given list of transitions and stop when either the end of the list was reached
			// OR enough possible following transitions were found
			ArrayList<Recommendation> currentRecommendations = new ArrayList<Recommendation>();
			for (TransitionSystem currentSystem : this.getAllSystems()){
				currentRecommendations.addAll(currentSystem.getRecommendations(currentTransitions));
			}
			// Iterate over all systems and get recommendations adhering to the current list of previous transitions
			for (Recommendation recommendation : currentRecommendations){
				recommendation.setProbability(recommendation.getProbability()+currentTransitions.size());
				// Since longer sequences of transitions are weighed more heavily, inflate the probability by adding the length of the sequence
				if (!uniqueFound.contains(recommendation.getNextTransition())){
					uniqueFound.add(recommendation.getNextTransition());
				}
				// Check if the transition to be performed next was already found in an earlier iteration in order to guarantee that howMany unique transitions will be returned
			}
			allRecommendations.getRecommendations().addAll(currentRecommendations);
			// before adding them to the lists of results
			currentTransitions.remove(0);
			// removing the first element of transition sequence to only search for shorter sequences now
			startingPoint++;
		}
		allRecommendations.mergeRecommendations();
		// Merge multiple recommendations for the same transition to just one
		RecommendationList finalResult = new RecommendationList();
		if (allRecommendations.getRecommendations().size() > howMany){
			// if more transitions were found than need to be returned
			for (int i = 0; i < howMany; i++){
				finalResult.getRecommendations().add(allRecommendations.getRecommendations().get(i));
			}
			// Only retain those that have the highest probability
		}
		else {
			if (allRecommendations.getRecommendations().size() < howMany){
				// if fewer transitions were found than need to be returned
				int counter = 0;
				if (allRecommendations.getRecommendations().size() != 0){
					// if at least one recommendation was found
					while (counter < allFrequencies.getAllFrequencies().size() && (allRecommendations.getRecommendations().size() < howMany)){
						if (!allRecommendations.getTransitionLabels().contains(allFrequencies.getAllFrequencies().get(counter).getLabel())){
							allRecommendations.getRecommendations().add(new Recommendation(1.0, allFrequencies.getAllFrequencies().get(counter).getLabel()));
						}
						counter++;
					}
					// only add as many transitions as needed from the sorted list of all transitions and their respective frequencies, and only
					// those that are not already found. Probability is equal to 1 in thise case for all transitions added this way 

				}
				else {
					// if no recommendation was found at all (i.e. the last modeled element was not ever recorded previously)
					while (counter < allFrequencies.getAllFrequencies().size() && (allRecommendations.getRecommendations().size() < howMany)){
						if (!allRecommendations.getTransitionLabels().contains(allFrequencies.getAllFrequencies().get(counter).getLabel())){
							allRecommendations.getRecommendations().add(new Recommendation(Double.valueOf(allFrequencies.getAllFrequencies().get(counter).getFrequency()), allFrequencies.getAllFrequencies().get(counter).getLabel()));
						}
						counter++;
					}
					// return the first howMany transitions from the sorted list of all transitions and their respective frequencies
					// with probability according to their frequency
				}
			}
		}
		finalResult = allRecommendations;
		finalResult.weighRecommendations2();
		return finalResult.getRecommendations();
	}
}
