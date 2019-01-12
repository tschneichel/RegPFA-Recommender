package transitionSystem;

import java.util.HashMap;
import java.util.Map;


public class Transition {
	public Map<String,Transition> followingTransitions = new HashMap<String, Transition>();
//	public Map<String,Transition> previousTransitions = new HashMap<String, Transition>();
	// Probably unneccessary?
	public int count;
}
