package transitionData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TransitionFrequencyList implements Serializable{
	private static final long serialVersionUID = 1L;
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

	public ArrayList<TransitionFrequency> merge (TransitionFrequencyList rightList){
		// merge method of mergeSort for TransitionFrequencyList
		ArrayList<TransitionFrequency> left = this.getAllFrequencies();
		ArrayList<TransitionFrequency> right = rightList.getAllFrequencies();
		ArrayList<TransitionFrequency> merged = new ArrayList<TransitionFrequency>();
		while (!left.isEmpty() && !right.isEmpty()){
			if (!(left.get(0).getFrequency() < right.get(0).getFrequency())){
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
		// main method of mergeSort for TransitionFrequencyList
		ArrayList<TransitionFrequency> input = this.getAllFrequencies();
		if (input.size() != 1) {
			ArrayList<TransitionFrequency> left = new ArrayList<TransitionFrequency>();
			ArrayList<TransitionFrequency> right = new ArrayList<TransitionFrequency>();
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
			TransitionFrequencyList leftList = new TransitionFrequencyList();
			TransitionFrequencyList rightList = new TransitionFrequencyList();
			leftList.setAllFrequencies(left);
			rightList.setAllFrequencies(right);
			leftList.mergeSort();
			rightList.mergeSort();
			input.addAll(leftList.merge(rightList));
		}
	}
	
	/**
	 * This method provides an alternative sorting algorithm that can be used to sort the list of all transitions, grouped by name, by their frequency
	 * It runs considerably faster than mergeSort when a large part of the list to the left is already sorted (nearly O(n) time)
	 * However, since it is not guaranteed that this condition holds true most of the time, standard mergeSort is used as the default sorting algorithm here
	 * 
	public void divideSort(){
		// Sorts the list of all transitions in a way that is efficient for repeated program runs
		// This is under the assumption that new transition systems likely won't change the frequencies of already encountered transitions
		// in a meaningful way after a certain point. If that holds true, a huge amount of elements "to the left" of the list are already sorted
		ArrayList<TransitionFrequency> frequencies = this.getAllFrequencies();
		boolean alreadySorted = true;
		int divider = 0;
		// pre-creating variables
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
		ArrayList<TransitionFrequency> leftFrequencies = new ArrayList<TransitionFrequency>();
		for (int i = 0; i <= divider; i++){
			leftFrequencies.add(frequencies.get(i));
		}
		// Stores the already sorted part of the list on leftFrequencies 
		TransitionFrequencyList rightList = new TransitionFrequencyList();
		ArrayList<TransitionFrequency> rightFrequencies = new ArrayList<TransitionFrequency>();
		for (int i = divider +1; i < frequencies.size(); i++){
			rightFrequencies.add(frequencies.get(i));
		}
		// Stores the unsorted part of the list on rightFrequencies
		rightList.setAllFrequencies(rightFrequencies);
		rightList.mergeSort();
		// Sorts the unsorted part of the list using mergeSort
		this.setAllFrequencies(leftFrequencies);
		this.merge(rightList);
		// Merges the already sorted part of the list and the newly sorted part of the list that was previously unsorted
	}**/
	
	public void updateMap(){
		// Simply iterates over the list of transitions and their frequencies and puts the correct positions in the HashMap for future runs
		for (int i = 0; i < this.getAllFrequencies().size(); i++){
			this.getLabelToPosition().put(this.getAllFrequencies().get(i).getLabel(), i);
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
