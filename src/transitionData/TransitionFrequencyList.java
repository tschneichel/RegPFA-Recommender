package transitionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import recommendationData.Recommendation;

public class TransitionFrequencyList {
	public ArrayList<TransitionFrequency> allFrequencies = new ArrayList<TransitionFrequency>();
	public HashMap<String, Integer> labelToPosition = new HashMap<String, Integer>();
	
	public ArrayList<TransitionFrequency> getAllFrequencies() {
		return allFrequencies;
	}

	public void setAllFrequencies(ArrayList<TransitionFrequency> allFrequencies) {
		this.allFrequencies = allFrequencies;
	}
	
	public HashMap<String, Integer> getLabelToPosition() {
		return labelToPosition;
	}

	public void setLabelToPosition(HashMap<String, Integer> labelToPosition) {
		this.labelToPosition = labelToPosition;
	}

	public void sort(){
		// Sorts the list of all transitions in a way that is efficient for repeated program runs
		ArrayList<TransitionFrequency> frequencies = this.getAllFrequencies();
		boolean alreadySorted = true;
		int divider = 0;
		while (alreadySorted && divider < frequencies.size()-1){
			if (frequencies.get(divider).getFrequency() >= frequencies.get(divider+1).getFrequency()){
				divider = divider+1;
			}
			else {
				alreadySorted = false;
			}
		}
		// This while loop checks up until which element the list appears to be sorted already. 
		// Example: If the list was (10, 9, 8, 7, 6, 6, 7, 3, 5), the last sorted element would be the second 6 at index 5, therefore divider = 5.
		for (int i = divider+1; i < frequencies.size(); i++){
			// for all elements to the right of the divider
			int j = i-1;
			int insertionPoint = -1;
			// reset variables
			while (j >= 0 && insertionPoint < 0){
				if (frequencies.get(i).getFrequency() <= frequencies.get(j).getFrequency()){
					insertionPoint = j+1;
				}
				j--;
			}
			// This while loop finds the position of the first element to the left of the element currently being looked at that is 
			if (insertionPoint > -1){
				TransitionFrequency temp = frequencies.get(i);
				for (int k = i-1; k >= insertionPoint; k--){
					frequencies.get(k+1).setLabel(frequencies.get(k).getLabel()); 
					frequencies.get(k+1).setFrequency(frequencies.get(k).getFrequency()); 
				}
				frequencies.get(insertionPoint).setLabel(temp.getLabel()); 
				frequencies.get(insertionPoint).setFrequency(temp.getFrequency()); 
			}
		}
		this.updateMap();
	}
	
	public void updateMap(){
		// Updates the map with new positions respective to list of transitions after sorting
        for (Entry<String, Integer> entry : this.getLabelToPosition().entrySet()) {
        	// Iterate over all entries of the map
            if (!(entry.getKey()==this.getAllFrequencies().get(entry.getValue()).getLabel())) {
            	// if the label of the frequency at the position of the value of the current entry does not match the key of the current entry
            	int i = 0;
            	boolean found = false;
            	while (i < this.getAllFrequencies().size() && !found){
            		if (entry.getKey()==this.getAllFrequencies().get(i).getLabel()) {
            			this.getLabelToPosition().put(entry.getKey(), i);
            			found = true;
            		}
            		i++;
            	}
            	// Iterate over the whole list until you find the position i of the current entries key and change the keys value to i. 
            }
        }
	}
	public void print(){
		System.out.println(this.getLabelToPosition());
		for (int i = 0; i < this.getAllFrequencies().size(); i++){
			System.out.print(this.getAllFrequencies().get(i).getLabel());
			System.out.print(" with frequency ");
			System.out.println(this.getAllFrequencies().get(i).getFrequency());
		}
	}
}
