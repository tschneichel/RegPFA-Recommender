package recommender;

import java.io.*;

import transitionData.*;

public class mainRecommender {
	public static void main (String args[]){
		/*pfa testPFA = new pfa();
		testPFA.print();
		try {
			FileOutputStream fileOutput = new FileOutputStream("object.data");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			output.writeObject(testPFA);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("object.data");
            in = new ObjectInputStream(fis);
            pfa newPFA = (pfa) in.readObject();
            in.close();
            newPFA.print();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
		
		TransitionSystem testSystem = new TransitionSystem();
		testSystem.print();
	}

}
