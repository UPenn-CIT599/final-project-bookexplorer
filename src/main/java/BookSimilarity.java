import java.util.ArrayList;
import java.util.Arrays;

public class BookSimilarity {

    Book targetBook;
    Book comparedBook;
    ArrayList<Double> weights;

    public BookSimilarity(Book targetBook, Book comparedBook) {
        this.targetBook = targetBook;
        this.comparedBook = comparedBook;
        this.weights = new ArrayList<Double>(Arrays.asList(0.2, 0.2, 0.2, 0.2, 0.2));
    }

    public double ratingSim() {

    }

    public double ratingsCountSim() {

    }

    public double descriptionSim() {

    }

    public double bookLengthSim() {

    }

    public double averageSimilarity() {

    }
}
