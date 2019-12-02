import java.util.HashMap;

public class BookSimCalculator {

    /**
     * Responsibilities:
     * Calculates Similarities between 2 books objects, based on the books' attributes
     * Equal weights are distributed to each attribute, unless otherwise specified
     * Collaborator:
     * Book
     */

    Book targetBook;
    Book comparedBook;
    HashMap<String, Double> weights;

    public BookSimCalculator(Book targetBook, Book comparedBook) {
        this.targetBook = targetBook;
        this.comparedBook = comparedBook;
        this.weights = new HashMap<String, Double>() {{
            put("ratings", 0.2);
            put("ratingsCount", 0.2);
            put("bookLength", 0.2);
            put("description", 0.2);
            put("publicationYear", 0.2);
        }};
    }

    public double descriptionSim() {
        //TODO update below
        return 1.0;
    }

    /**
     * returns 0 if 2 attributes are most similar, and 1 if least similar
     * @param num1 - attribute from first book
     * @param num2 - attribute from second book
     * @return
     */
    private double singleSimCalc(double num1, double num2) {
        if (num1 == 0 && num2 == 0) {
            return 1;
        } else {
            return Math.abs(num1 - num2) / (num1 + num2);
        }
    }

    /**
     * This method combines the similarities of 2 books' attributes by weights and returns 1 if most similar, and 0 if least similar
     * @return weighted similarity between 2 books
     */
    public double weightedSimilarity() {
        double ratingsSim = weights.get("ratings") * singleSimCalc(comparedBook.averageRating, targetBook.averageRating);
        double ratingsCountSim = weights.get("ratingsCount") * singleSimCalc(comparedBook.ratingsCount, targetBook.ratingsCount);
        double publicationYearSim = weights.get("publicationYear") * singleSimCalc(comparedBook.publicationYear, targetBook.publicationYear);
        double bookLengthSim = weights.get("bookLength") * singleSimCalc(comparedBook.pagesNum, targetBook.pagesNum);
        double descriptionSim = weights.get("description") * descriptionSim();
        return 1 - (ratingsSim + ratingsCountSim + publicationYearSim + bookLengthSim + descriptionSim);
    }
}
