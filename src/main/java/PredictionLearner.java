import java.util.ArrayList;
import java.util.Arrays;

public class PredictionLearner {
	/**
	 * A learner class that aims to learn from past predictions
	 * Adjust weight of prediction criteria based on user feedbacks
	 */
	
	ArrayList<Prediction> predictions;
	// Currently we assume there are 5 criteria we make the predictions on
	ArrayList<Double> weight = new ArrayList<Double>(Arrays.asList(0.2, 0.2, 0.2, 0.2, 0.2));
	
	public void makePrediction(User user) {
		// calls on similarity calculator class
	}
	
	public void makeWeightAdjustment(User user) {
		
	}
}
