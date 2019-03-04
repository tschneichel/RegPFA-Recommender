package transitionData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import recommendationData.Recommendation;
import recommendationData.RecommendationList;

public class TransitionSystemList implements Serializable{
	private static final long serialVersionUID = 1L;
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
		RecommendationList allRecommendations = new RecommendationList();
		int startingPoint = 0;
		int endPoint = currentTransitions.size();
		ArrayList<String> uniqueFound = new ArrayList<String>();
		// pre-creating variables
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
			//currentTransitions.remove(0);
			// TODO: Fix this section...
			ArrayList<String> tempList = new ArrayList<String>();
			for (int i = 1; i < currentTransitions.size(); i++){
				tempList.add(currentTransitions.get(i));
			}
			currentTransitions = tempList;
			// removing the first element of transition sequence to only search for shorter sequences now
			startingPoint++;
		}
		allRecommendations.mergeRecommendations();
		// Merge multiple recommendations for the same transition to just one
		RecommendationList finalResult = new RecommendationList();
		int foundNormally = allRecommendations.getRecommendations().size();
		// store amount of recommendations found via checking for previous transitions on foundNormally
		if (foundNormally > howMany){
			// if more transitions were found than need to be returned
			for (int i = 0; i < howMany; i++){
				finalResult.getRecommendations().add(allRecommendations.getRecommendations().get(i));
			}
			// Only retain those that have the highest probability
		}
		else {
			if (foundNormally < howMany){
				// if fewer transitions were found than need to be returned
				int counter = 0;
				if (foundNormally != 0){
					// if at least one recommendation was found
					while (counter < allFrequencies.getAllFrequencies().size() && (allRecommendations.getRecommendations().size() < howMany)){
						if (!allRecommendations.getTransitionLabels().contains(allFrequencies.getAllFrequencies().get(counter).getLabel())){
							allRecommendations.getRecommendations().add(new Recommendation(Double.valueOf(allFrequencies.getAllFrequencies().get(counter).getFrequency()), allFrequencies.getAllFrequencies().get(counter).getLabel()));
						}
						counter++;
					}
					// only add as many transitions as needed from the sorted list of all transitions and their respective frequencies, and only
					// those that are not already found. Probability is equal to their frequency for now, but will be adjusted
					Double totalNewFrequencies = 0.0;
					for (int i = foundNormally; i < allRecommendations.getRecommendations().size(); i++){
						totalNewFrequencies += allRecommendations.getRecommendations().get(i).getProbability();
					}
					for (int i = foundNormally; i < allRecommendations.getRecommendations().size(); i++){
						allRecommendations.getRecommendations().get(i).setProbability(allRecommendations.getRecommendations().get(i).getProbability() / totalNewFrequencies);
					}
					finalResult = allRecommendations;
				}
				else {
					// if no recommendation was found at all (i.e. the last modeled element was not ever recorded previously)
					while (counter < allFrequencies.getAllFrequencies().size() && (allRecommendations.getRecommendations().size() < howMany)){
						if (!allRecommendations.getTransitionLabels().contains(allFrequencies.getAllFrequencies().get(counter).getLabel())){
							allRecommendations.getRecommendations().add(new Recommendation(Double.valueOf(allFrequencies.getAllFrequencies().get(counter).getFrequency()), allFrequencies.getAllFrequencies().get(counter).getLabel()));
						}
						counter++;
					}
					finalResult = allRecommendations;
					// return the first howMany transitions from the sorted list of all transitions and their respective frequencies
					// with probability according to their frequency
				}
			}
		}
		finalResult.weighRecommendations();
		return finalResult.getRecommendations();
	}
}
