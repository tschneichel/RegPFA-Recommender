package recommendationData;

import java.util.ArrayList;

public class RecommendationList {
	ArrayList<Recommendation> recommendations = new ArrayList<Recommendation>();

	public ArrayList<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(ArrayList<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	
	public void mergeRecommendations(){
		// Merges probabilities for transitions with the same name
		ArrayList<RecommendationCluster> clusters = this.createClusters();
		// creates clusters
		ArrayList<Recommendation> finalResult = new ArrayList<Recommendation>();
		// pre-create variable for result
		for (RecommendationCluster cluster : clusters){
			cluster.clusterToSingle();
			finalResult.add(cluster.getFinalRecommendation());
		}
		// calculate the final recommendations for each cluster and store them on finalResult
		this.recommendations = finalResult;
	}
	
	public ArrayList<RecommendationCluster> createClusters(){
		// creates clusters containing only transitions of the same name from a list of recommendations
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
	}
}
