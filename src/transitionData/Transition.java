package transitionData;


public class Transition {
	public State source;
	public State target;
	public String label;
	public int count;
	public float probability;
	
	
	
	public State getSource() {
		return source;
	}
	public void setSource(State source) {
		this.source = source;
	}
	public State getTarget() {
		return target;
	}
	public void setTarget(State target) {
		this.target = target;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	
	
	
}
