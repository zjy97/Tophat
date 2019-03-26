package application.TrainModel;
/**
 * This is the TrainModelSingleton this class is used to communicate with all of the train model objects.
 * 
 * @author jar254
 * @version 1.0
 *
 */

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import application.TrackModel.TrackBlock;
import application.TrainController.TrainControllerSingleton;

public class TrainModelSingleton implements TrainModelInterface {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainModelSingleton instance = null;

	private TrainModelSingleton() {
		 trainModelHashTable = new Hashtable<>();
	}

	public static TrainModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrainModelSingleton();
		}

		return instance;
	}

	private boolean disabled = false;
	
	private TrainControllerSingleton trainControllerSingleton = TrainControllerSingleton.getInstance();
	
	public boolean toggleDisable() {
		return disabled = !disabled;
	}

	// =====================================

	private Hashtable<Integer, TrainModel> trainModelHashTable;
	
    public TrainInterface createTrain(int trainID) {  	
    	if(trainExists(trainID)) return null;
    	TrainModel train = new TrainModel(trainID);
//    	trainControllerSingleton.createTrain(trainID, train); //Create this method.
    	
        return trainModelHashTable.putIfAbsent(trainID, train);
    }

    public void makeTrain(int trainID, double x, double y, TrackBlock currentBlock, TrackBlock nextBlock) {
    	if(trainExists(trainID));
    	System.out.println("Created a new train " + trainID);
    	TrainModel train = new TrainModel(trainID, x, y, currentBlock);
//    	trainControllerSingleton.createTrain(trainID, train); //Create this method.
    	
    	trainModelHashTable.put(trainID, train);
    }
    
    public boolean trainExists(int trainID) {
        return trainModelHashTable.containsKey(trainID);
    }
    
    public TrainInterface getTrain(int trainID){
        return trainModelHashTable.get(trainID);
    }
    
	public boolean removeTrain(int tranID) {	
		TrainModel train = trainModelHashTable.remove(tranID);
		if(train != null) train.remove();
		return train != null;
	}
	
	public boolean dispatchTrain(int trainID) {
		TrainModel train = trainModelHashTable.get(trainID);
		if(train != null) return train.dispatch();
		return false;
	}
    
	/**
	 * Returns the count of how many trains there are.
	 * return: int count of trains.
	 */
	@Override
	public int trainCount() {
		return trainModelHashTable.size();
	}

	@Override
	public final Set<Integer> getAllTrainIDs() {
		return trainModelHashTable.keySet();
	}


    Collection<TrainModel> getTrains() {
        return trainModelHashTable.values();
    }
    
    /**
     * Gets the speed of the first train.
     * @return
     */
    @Deprecated
    public String getSpeed() {
    	
    	for(TrainModel train : trainModelHashTable.values()) {
    		return train.getSpeed() + "mph";
    	}
    	
    	return "0mph";
    }

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		if(disabled) return;
		
		for(TrainModel trainModel : trainModelHashTable.values()) {
			//Any code to call for each train model update.
			trainModel.update(0);
		}
	}
}