import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    RequestHandler handler;

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
        this.handler = new RequestHandler();
    }

    public BookSimCalculator() {

    }

    public double descriptionSim() {
        int synCount = 0;
        ArrayList<String> descriptionWords1 = descriptionToWords(comparedBook.description);
        ArrayList<String> descriptionWords2 = descriptionToWords(targetBook.description);
        for(String word : descriptionWords1) {
            if (descriptionWords2.contains(word)) {
                synCount++;
            } else {
                ArrayList<String> synonyms = null;
                try {
                    synonyms = handler.getSynonyms(word);
                    for (String syn : synonyms) {
                        if (descriptionWords2.contains(syn)) {
                            synCount += 1;
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println(String.format("An error occurred while calling the synonyms API: %s", e.getMessage()));
                } catch (ParserConfigurationException e) {
                    System.out.println(String.format("An error occurred while calling the synonyms API: %s", e.getMessage()));
                } catch (SAXException e) {
                    System.out.println(String.format("An error occurred while calling the synonyms API: %s", e.getMessage()));
                }
            }
        }
        return ((double) synCount) / (descriptionWords1.size() + descriptionWords2.size());
    }

    /**
     * returns 0 if 2 attributes are most similar, and 1 if least similar
     * @param num1 - attribute from first book
     * @param num2 - attribute from second book
     * @return a double between 0 to 1
     */
    public double singleSimCalc(double num1, double num2) {
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

    /**
     * removes non-word characters from description and returns an array list of words
     * @param description
     * @return
     */
    private ArrayList<String> descriptionToWords(String description) {
        ArrayList<String> cleanWords = new ArrayList<>(Arrays.asList(description.replaceAll("[^a-zA-Z]", "").toLowerCase().split(" ")));
        return cleanWords;
    }
}
