import java.util.ArrayList;
import java.util.HashMap;

public class AuthorSimCalculator extends BookSimCalculator {

    Author targetAuthor;
    Author comparedAuthor;
    HashMap<String, Double> weights;

    public AuthorSimCalculator(Author targetAuthor, Author comparedAuthor) {
        super();
        this.targetAuthor = targetAuthor;
        this.comparedAuthor = comparedAuthor;
        // initialize with equal weight
        this.weights = new HashMap<String, Double>() {{
            put("followers", 0.1);
            put("worksCount", 0.2);
            put("books", 0.7);
        }};
    }

    /**
     * calculates average similarity index of 2 authors' top 5 books
     * if an author has < 5 books, then take the lesser of 5 or author's books count.
     * @return average book similarities between 2 authors
     */
    public double booksSimilarity() {
        ArrayList<Book> bookSet1 = targetAuthor.books;
        ArrayList<Book> bookSet2 = comparedAuthor.books;
        int inspectedBookSize = 0;
        if (bookSet1.size() <= bookSet2.size()) {
            inspectedBookSize = bookSet1.size();
        } else {
            inspectedBookSize = bookSet2.size();
        }
        double totalSimilarities = 0.0;
        ArrayList<Integer> randIndexes = Utility.randomIntArray(inspectedBookSize, 5);
        for (int index : randIndexes) {
            BookSimCalculator sim = new BookSimCalculator(bookSet1.get(index), bookSet2.get(index));
            totalSimilarities += sim.weightedSimilarity();
        }
        return totalSimilarities / 5;
    }

    public double weightedSimilarity() {
        double worksCountSim = weights.get("worksCount") * singleSimCalc(comparedAuthor.worksCount, targetAuthor.worksCount);
        double followersCountSim = weights.get("followers") * singleSimCalc(comparedAuthor.followersCount, targetAuthor.followersCount);
        return worksCountSim + followersCountSim + weights.get("books") * booksSimilarity();
    }
}
