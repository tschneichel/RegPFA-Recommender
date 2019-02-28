package transitionData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pfa_elements.transitionOld;
import recommendationData.Recommendation;

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
		return containsSequence (new ArrayList<String> (sequence.subList(1, sequence.size())));
	}


	public ArrayList<Recommendation> getRecommendations(ArrayList<String> sequence, Double probability) {
		ArrayList<Recommendation> result = new ArrayList<Recommendation>();
		/*// This method generates all recommendations for a certain sequence of transitions and a given start state
		ArrayList<Recommendation> result = new ArrayList<Recommendation>();
		if (sequence.size() == 0){
			// If the end of the input sequence was reached, all possible transitions from the current state may be recommended
			// This marks the end of the recursion
			ArrayList<String> allTransitions = new ArrayList<String>(this.getTransitionsTo().keySet());
			// Store the names of all possible transitions as an ArrayList of Strings
			for (String transitionName : allTransitions){
				// Iterate over all possible transitions
				Recommendation recommendation = new Recommendation();
				recommendation.setNextTransition(transitionName);
				// Create a new recommendation with the name of the current transition
				Double totalProbability = 0.0;
				for (Transition transition : this.getTransitionsTo().get(transitionName)){
					totalProbability += transition.getProbability();
				}
				recommendation.setProbability(totalProbability * probability);
				// calculate the probability of said transition to occur by summing up the probabilities to leave the current state
				// via this transition and then multiply by the probability to reach this state with the initial sequence
				result.add(recommendation);
				// add the recommendation to result
			}
			return (result);
			
			// TODO: Exception for end state. Probably add "endstate = true" in constructor of states and set it to false when a transitions to this state was created
		}*/
		
		// Part above probably not needed due to new function below
		
		if (sequence.size() == 0){
			return (this.getFinalRecommendations(probability));
		}
		if (this.getTransitionsTo().containsKey(sequence.get(0))){
			// If the next transition in the sequence is possible from this state
			for (Transition transition : this.getTransitionsTo().get(sequence.get(0))){
				// Iterate over all transitions with that name
				Double new_probability = transition.getProbability() * probability;
				// Probability to leave this state via current transition equals probability to reach this state with current sub-sequence of transitions
				// multiplied by probability of current transition to occur starting from this state 
				ArrayList<Recommendation> recommendations = transition.getTarget().getRecommendations(new ArrayList<String> (sequence.subList(1, sequence.size())), new_probability);
				// recursive call of this function. The first element of sequence was removed and the probability was altered
				result.addAll(recommendations);
				
				// TODO: Check whether addAll does what's expected (add all entries of ArrayList to another ArrayList)
				
				// all possible next transitions and their probabilities are stored in result
			}
		}
		return (result);	
	}
	
	public ArrayList<Recommendation> getFinalRecommendations(Double probability) {
		// This method generates all recommendations for a certain sequence of transitions and a given start state
		ArrayList<Recommendation> result = new ArrayList<Recommendation>();
			// If the end of the input sequence was reached, all possible transitions from the current state may be recommended
			// This marks the end of the recursion
			ArrayList<String> allTransitions = new ArrayList<String>(this.getTransitionsTo().keySet());
			// Store the names of all possible transitions as an ArrayList of Strings
			for (String transitionName : allTransitions){
				// Iterate over all possible transitions
				Double totalProbability = 0.0;
				for (Transition transition : this.getTransitionsTo().get(transitionName)){
					totalProbability += transition.getProbability();
				}
				Double recommendationProbability = totalProbability * probability;
				// calculate the probability of said transition to occur by summing up the probabilities to leave the current state
				// via this transition and then multiply by the probability to reach this state with the initial sequence
				Recommendation recommendation = new Recommendation(recommendationProbability, transitionName);
				// Save next transition name as well as the aforementioned probability as a recommendation
				result.add(recommendation);
				// add the recommendation to result
			}
			return (result);
			
			// TODO: Exception for end state. Probably add "endstate = true" in constructor of states and set it to false when a transitions to this state was created
		}
	
}
