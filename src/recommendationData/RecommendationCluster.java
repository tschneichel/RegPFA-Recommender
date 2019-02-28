package recommendationData;

import java.util.ArrayList;

public class RecommendationCluster {
	public Recommendation finalRecommendation;
	public String label;
	public ArrayList<Double> probabilities = new ArrayList<Double>();

	public Recommendation getFinalRecommendation() {
		return finalRecommendation;
	}

	public void setFinalRecommendation(Recommendation finalRecommendation) {
		this.finalRecommendation = finalRecommendation;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Double> getProbabilities() {
		return probabilities;
	}

	public void setProbabilities(ArrayList<Double> probabilities) {
		this.probabilities = probabilities;
	}

	public void addProbability(Double probability) {
		this.getProbabilities().add(probability);
	}
	
	public void clusterToSingle(){
		// Calculates the overall probability for this cluster and stores it on FinalRecommendation
		int biggestPosition = this.findBiggestProbability();
		Double totalProbability = this.mergeProbabilities(biggestPosition);
		Recommendation finalResult = new Recommendation(totalProbability, this.getLabel());
		this.setFinalRecommendation(finalResult);
	}
	
	public int findBiggestProbability(){
		// Finds the index of the biggest probability in the cluster
		int result = 0;
		Double current = 0.0; 
		for (int i = 0; i < this.getProbabilities().size(); i++){
			if (current < this.getProbabilities().get(i)){
				current = this.getProbabilities().get(i);
				result = i;
			}
		}
		return result;
	}
	
	public Double mergeProbabilities(int biggest){
		// calculates the overall probability given the index of the biggest probability in the cluster
		Double result = this.getProbabilities().get(biggest);
		this.getProbabilities().remove(biggest);
		// Pre-sets variable result with the biggest probability, then deletes the corresponding index from the list of probabilities
		int nextHighest = result.intValue()+1;
		// Since probabilities are inflated by adding the number of "hops" performed before the next transition (i.e. how many previously modeled
		// transitions were used in finding the next transition) to the probability, we do not want to exceed the hard limit of the next highest integer.
		// Example: First probability for next transition to be A is 0.3 with 3 previous hops. Therefore, inflated probability = 3.3
		// Second probability for next transition to be A is 0.5 with 2 previous hops. Therefore, inflated probability = 2.5
		// A combination of those probabilities may not exceed 3.9999999, since a probability of 4.0 or higher indicates the existence of a chain of
		// 4 hops performed when the maximum is just 3 in this example.
		for (Double probability : this.getProbabilities()){
			Double maxLeft = nextHighest - result;
			result += maxLeft * probability / nextHighest / (Math.pow(((nextHighest-1)-probability.intValue()),2)+1);
		}
		// Calculates total probability by performing the following steps for all probabilities p in the cluster:
		// Store the difference between the next highest integer "nextHighest", indicating one more hop than the maximum amount of hops in this cluster, on maxLeft
		// Multiply this value by the proportion of p divided by "next Highest" - the result of this is a percentage of maxLeft proportional to the
		// difference of the current probability and the total probability (result) so far.
		// If we stop here, one big probability (e.g. 10.23) and multiple small probabilities (e.g. 50 times 1.2) would result in a total probability
		// of close to 11 (10.9976). Therefore, we normalize by also dividing by the squared difference of the number of hops between the highest
		// and all other probabilities plus one. In the example above, this would lead to a total probability of 10.2796 which better represents the reality.
		return result;
	}
}
