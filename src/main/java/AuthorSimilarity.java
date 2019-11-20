import java.util.ArrayList;
import java.util.HashMap;

public class AuthorSimilarity {

    Author targetAuthor;
    Author comparedAuthor;
    HashMap<String, Double> weights;

    public double worksCountSim() {
        return ((double) targetAuthor.worksCount) / (comparedAuthor.worksCount);
    }

    public double followerCountSim() {
        return ((double) targetAuthor.worksCount) / (comparedAuthor.worksCount);
    }

    /**
     * Only calculated if both authors have descriptions
     * @return count of synonyms words / total words
     */
    public double aboutSim() {
        // TODO: update below logic

        return 0.0;
    }

    public AuthorSimilarity(Author targetAuthor, Author comparedAuthor) {
        this.targetAuthor = targetAuthor;
        this.comparedAuthor = comparedAuthor;
        this.weights = new HashMap<String, Double>() {{
            put("followers", 0.25);
            put("description", 0.25);
            put("worksCount", 0.25);
            put("books", 0.25);
        }};
    }

    /**
     * calculates average similarity index of 2 authors' top 5 books
     * if an author has < 5 books, then take the lesser of 5 or author's books count.
     * @return average book similarities between 2 authors
     */
    public double bookSimilarity() {
        int booksCount = 5;
        ArrayList<Book> targetBooks = new ArrayList<Book>();
        ArrayList<Book> comparedBooks = new ArrayList<Book>();
        double totalSimilarities = 0.0;
        if (targetAuthor.books.size() < comparedAuthor.books.size()) {
            if (targetAuthor.books.size() <= 5) {
                booksCount = targetAuthor.books.size();
            }

        } else {
            if (comparedAuthor.books.size() <= 5) {
                booksCount = comparedAuthor.books.size();
            }
        }
        for (int i = 0; i < booksCount; i++) {
            targetBooks.add(targetAuthor.books.get(i));
            comparedBooks.add(comparedAuthor.books.get(i));
        }
        for (int i = 0; i < booksCount; i++) {
            BookSimilarity sim = new BookSimilarity(targetBooks.get(i), comparedBooks.get(i));
            totalSimilarities += sim.weightedSimilarity();
        }
        return totalSimilarities / booksCount;
    }

    public double weightedSimilarity() {
        return weights.get("worksCount") * worksCountSim() + weights.get("description") * aboutSim() + weights.get("followers") * followerCountSim() + weights.get("books") * bookSimilarity();
    }
}
