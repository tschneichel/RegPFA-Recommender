package recommendationData;

public class Recommendation {
	public String nextTransition;
	public Double probability;
	
	
	public String getNextTransition() {
		return nextTransition;
	}
	public void setNextTransition(String nextTransition) {
		this.nextTransition = nextTransition;
	}
	public Double getProbability() {
		return probability;
	}
	public void setProbability(double d) {
		this.probability = d;
	}
	
	public void print(){
		// Simply prints the recommendation with associated values
		System.out.println("Recommend "+this.getNextTransition()+" with probability "+this.getProbability());
	}
	
	public Recommendation(Double probability, String nextTransition){
		// constructor
		this.probability = probability;
		this.nextTransition = nextTransition;
	}
}
