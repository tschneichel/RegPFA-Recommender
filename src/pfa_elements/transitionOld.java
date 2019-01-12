package pfa_elements;

import java.io.Serializable;

public class transitionOld implements Serializable{
	private state source;
	private state target;
	private String label;
	
	
	public transitionOld(state source, state target, String label){
		this.setSource(source);
		this.setTarget(target);
		this.setLabel(label);
	}
	
	public state getSource() {
		return source;
	}
	public void setSource(state source) {
		this.source = source;
	}
	public state getTarget() {
		return target;
	}
	public void setTarget(state target) {
		this.target = target;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
