package recommendationData;

import java.util.ArrayList;

public class RecommendationList {
	ArrayList<Recommendation> recommendations = new ArrayList<Recommendation>();
	ArrayList<String> transitionLabels = new ArrayList<String>();

	public ArrayList<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(ArrayList<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	
	public ArrayList<String> getTransitionLabels() {
		return transitionLabels;
	}

	public void setTransitionLabels(ArrayList<String> transitionLabels) {
		this.transitionLabels = transitionLabels;
	}

	public void mergeRecommendations(){
		// Merges probabilities for transitions with the same name
		ArrayList<RecommendationCluster> clusters = this.createClusters();
		// creates clusters
		ArrayList<Recommendation> finalResult = new ArrayList<Recommendation>();
		ArrayList<String> transitionNames = new ArrayList<String>();
		// pre-create variable for result
		for (RecommendationCluster cluster : clusters){
			cluster.clusterToSingle();
			finalResult.add(cluster.getFinalRecommendation());
			transitionNames.add(cluster.getLabel());
		}
		// calculate the final recommendations for each cluster and store them on finalResult, also store the name of each transition in a list
		this.recommendations = finalResult;
		this.transitionLabels = transitionNames;
	}
	
	public ArrayList<RecommendationCluster> createClusters(){
		// creates clusters containing only transitions of the same name from a list of recommendations
		this.mergeSort();
		// Sorting the recommendations first
		ArrayList<RecommendationCluster> clusters = new ArrayList<RecommendationCluster>();
		ArrayList<String> names = new ArrayList<String>();
		// pre-creates variables
		for (Recommendation recommendation : this.getRecommendations()){
			if (!names.contains(recommendation.getNextTransition())){
				// if the current transition was not already found earlier
				RecommendationCluster tempCluster = new RecommendationCluster();
				String tempLabel = recommendation.getNextTransition();
				tempCluster.setLabel(tempLabel);
				ArrayList<Double> probabilities = new ArrayList<Double>();
				probabilities.add(recommendation.getProbability());
				tempCluster.setProbabilities(probabilities);
				names.add(tempLabel);
				clusters.add(tempCluster);
				// create new cluster for this transition and store its label in "names"
			}
			else {
				// if it was found earlier
				int clusterPosition = names.indexOf(recommendation.getNextTransition());
				clusters.get(clusterPosition).addProbability(recommendation.probability);
				// add the probability to the corresponding cluster
			}
		}
		return clusters;
	}
	
	public void weighRecommendations(){
		// This method weighs the recommendations by applying the softmax-function
		Double total = 0.0;
		for (Recommendation recommendation : this.getRecommendations()){
			total += Math.exp(recommendation.getProbability());
		}
		// calculates the sum of e^(probability) for each probability of distinct transitions and stores it on total
		for (Recommendation recommendation : this.getRecommendations()){
			recommendation.setProbability(Math.exp(recommendation.getProbability()) / total);
		}
		// divides e^(probability) / total for each probability of distinct transitions and stores the result as final probability
		this.mergeSort();
		// sorts the recommendations in descending order
	}
	
	public void weighRecommendations2(){
		// This method weighs the recommendations as percentages
				Double total = 0.0;
				for (Recommendation recommendation : this.getRecommendations()){
					total += recommendation.getProbability();
				}
				// calculates the sum probabilities for each probability of distinct transitions and stores it on total
				for (Recommendation recommendation : this.getRecommendations()){
					recommendation.setProbability(recommendation.getProbability() / total);
				}
				// divides probability / total for each probability of distinct transitions and stores the result as final probability
				this.mergeSort();
				// sorts the recommendations in descending order
	}
	
	public ArrayList<Recommendation> merge (RecommendationList rightList){
		// merge method of mergeSort for RecommendationList
		ArrayList<Recommendation> left = this.getRecommendations();
		ArrayList<Recommendation> right = rightList.getRecommendations();
		ArrayList<Recommendation> merged = new ArrayList<Recommendation>();
		while (!left.isEmpty() && !right.isEmpty()){
			if (!(left.get(0).getProbability() < right.get(0).getProbability())){
				merged.add(left.remove(0));
			}	
			else {
				merged.add(right.remove(0));
			}
		}
		merged.addAll(left);
		merged.addAll(right);
		return merged;
	}
	
	public void mergeSort(){
		// main method of mergeSort for RecommendationList
		ArrayList<Recommendation> input = this.getRecommendations();
		if (input.size() != 1) {
			ArrayList<Recommendation> left = new ArrayList<Recommendation>();
			ArrayList<Recommendation> right = new ArrayList<Recommendation>();
			boolean switcher = true;
			while (!input.isEmpty()){
				if (switcher){
					left.add(input.remove(0));
				}
				else {
					right.add(input.remove(0));
				}
				switcher = !switcher;
			}
			RecommendationList leftList = new RecommendationList();
			RecommendationList rightList = new RecommendationList();
			leftList.setRecommendations(left);
			rightList.setRecommendations(right);
			leftList.mergeSort();
			rightList.mergeSort();
			input.addAll(leftList.merge(rightList));
		}
	}
}
