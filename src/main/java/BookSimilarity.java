import java.util.HashMap;

public class BookSimilarity {

    Book targetBook;
    Book comparedBook;
    HashMap<String, Double> weights;

    public BookSimilarity(Book targetBook, Book comparedBook) {
        this.targetBook = targetBook;
        this.comparedBook = comparedBook;
        this.weights = new HashMap<String, Double>() {{
            put("ratings", 0.25);
            put("ratingsCount", 0.25);
            put("bookLength", 0.25);
            put("description", 0.25);
        }};
    }

    public double ratingSim() {
        return ((double) targetBook.averageRating) / comparedBook.averageRating;
    }

    public double ratingsCountSim() {
        return (double) targetBook.ratingsCount / comparedBook.ratingsCount;
    }

    public double descriptionSim() {
        //TODO update below
        return 1.0;
    }

    public double bookLengthSim() {
        return (double) targetBook.pagesNum / comparedBook.pagesNum;
    }

    public double weightedSimilarity() {
        return weights.get("ratings") * ratingSim() + weights.get("description") * descriptionSim() + weights.get("ratingsCount") * ratingsCountSim() + weights.get("bookLength") * bookLengthSim();
    }
}
