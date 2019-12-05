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
        // below weights are determined based on manual trial and error
        // as an additional feature idea, use machine learning to adjust weights
        this.weights = new HashMap<String, Double>() {{
            put("ratings", 0.15);
            put("ratingsCount", 0.05);
            put("bookLength", 0.1);
            put("description", 0.3);
            put("publicationYear", 0.4);
        }};
        this.handler = new RequestHandler();
    }

    public BookSimCalculator() {

    }

    /**
     * Calculates similarities of 2 books' descriptions based on synonyms
     * @return a similarity index of between 0 and 1
     */
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
                    if (synonyms.size() != 0) {
                        for (String syn : synonyms) {
                            if (descriptionWords2.contains(syn)) {
                                synCount += 1;
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println(String.format("Warning: An error occurred while calling the synonyms API - %s", e.getMessage()));
                } catch (ParserConfigurationException e) {
                    System.out.println(String.format("Warning: An error occurred while calling the synonyms API - %s", e.getMessage()));
                } catch (SAXException e) {
                    System.out.println(String.format("Warning: An error occurred while calling the synonyms API - %s", e.getMessage()));
                }
            }
        }
        return 1- ((double) synCount) / (descriptionWords1.size() + descriptionWords2.size());
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
            return 1 - Math.abs(num1 - num2) / (num1 + num2);
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
        return ratingsSim + ratingsCountSim + publicationYearSim + bookLengthSim + descriptionSim;
    }

    /**
     * removes punctuations from description and returns an array list of words
     * @param description - string of book description
     * @return - array list of words in the description without punctuations or special symbols
     */
    private ArrayList<String> descriptionToWords(String description) {
        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(description.split(" ")));
        ArrayList<String> cleanWords = new ArrayList<>();
        for (String word : wordsList) {
            cleanWords.add(word.replaceAll("[^a-zA-Z]", "").toLowerCase());
        }
        return cleanWords;
    }
}
