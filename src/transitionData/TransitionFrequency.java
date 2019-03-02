package transitionData;

import java.io.Serializable;

public class TransitionFrequency implements Serializable{
	private static final long serialVersionUID = 1L;
	public String label;
	public int frequency;
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public void incrementFrequency(){
		this.frequency +=1;
	}
	
	public TransitionFrequency(String label, int frequency){
		this.setLabel(label);
		this.setFrequency(frequency);
	}
	
}
