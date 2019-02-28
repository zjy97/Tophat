package application.TrainModel;

import java.util.Collection;
import java.util.HashMap;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackBlock;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;

public class TrainModelSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainModelSingleton instance = null;

	private TrainModelSingleton() {
		 trainModelHashMap = new HashMap<>();
		 this.createTrain("Train_1");
	}

	public static TrainModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrainModelSingleton();
		}

		return instance;
	}

	// =====================================

	private HashMap<String, TrainModel> trainModelHashMap;

    public TrainModel getTrain(String trainID){
        return trainModelHashMap.get(trainID);
    }

    public TrainModel createTrain(String trainID) {
        TrainModel train = new TrainModel(trainID);
        trainModelHashMap.put(trainID, train);
        return train;
    }

    public boolean dispatchTrain(String trainID) {
        return trainModelHashMap.put(trainID, new TrainModel(trainID)) != null;
    }

    public void makeTrain(int ID, double x, double y, TrackBlock currentBlock, TrackBlock nextBlock) {
        String trainID = String.valueOf(ID);
        TrainModel train = new TrainModel(trainID, x, y, currentBlock);
        trainModelHashMap.put(trainID, train);
    }


    Collection<TrainModel> getTrains() {
        return trainModelHashMap.values();
    }
  

	public int getCount() {
		return -1;
	}
	
	
	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		
		for(TrainModel trainModel : trainModelHashMap.values()) {
			trainModel.update(10000);
		}

		//TODO Call getTrainLocation() method from TrackModel Singleton.
			// use 5 or 0 as parameter. See my interface for description.
		
	}

	public void removeTrain(int i) {
		// TODO Auto-generated method stub
		
	}

}
