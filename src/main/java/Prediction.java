import java.util.ArrayList;

public class Prediction {
	Book predictedBook;
	Genre genre;
	int score; // score will be provided by user and ranges 1 - 5 (5 is the best result)
	User user;
	ArrayList<Double> weightOfCalc;
}
