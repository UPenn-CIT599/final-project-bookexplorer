import java.util.ArrayList;
import java.util.Arrays;

public class PredictionLearner {
	/**
	 * A learner class that aims to learn from past predictions
	 * Adjust weight of similarities based on prediction feedbacks
	 */
	
	ArrayList<Prediction> predictions;
	ArrayList<Double> weight = new ArrayList<Double>(Arrays.asList(0.2, 0.2, 0.2, 0.2, 0.2));
	
	public void makePrediction(User user) {
		// calls on similarity calculator class
	}
	
	public void makeWeightAdjustment(User user) {
		
	}
}
