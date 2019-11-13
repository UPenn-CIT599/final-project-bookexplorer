import java.util.ArrayList;

/**
 * MCIT 591 - Final Project
 * 
 * Book Class
 * 
 * This class sets the criteria of determining the most common book title
 * names to the book title the user types in. Similar to the Author class
 * and the WordRecommender class found in Assignment Four, this class first
 * splits the inputted title of the book into separate strings based on
 * the spaces found in the title. These strings are then calculated for left
 * and right similarities with other words, obtaining word suggestions
 * for the words in the name, and tracking all words that start with the
 * same letter as the inputted name.
 * 
 * @author Thomas Flannery
 *
 */
public class Book {

	/**
	 * Calculates how similar the title of a book the user inputs is
	 * to the title of another book being compared to it, when both titles
	 * are lined up with one another (how many letters are located in
	 * the same position if both book titles were placed in an array).
	 *
	 * @param inputTitle  - user entered book title
	 * @param actualTitle - book title to be compared with inputTitle
	 * @return - average similarity when comparing names from left to right
	 * and vice versa
	 */
	public double leftRightSimilarityMetric(String inputTitle, String actualTitle) {

		// Puts title to lowercase letters
		String inputLowercase = inputTitle.toLowerCase();
		String actualLowercase = actualTitle.toLowerCase();

		String inputTitleNoSpaces = inputLowercase.replaceAll(" ", "");
		String actualTitleNoSpaces = actualLowercase.replace(" ", "");

		// Initializing leftSimilarity, rightSimilarity
		double leftSimilarity = 0.0;
		double rightSimilarity = 0.0;

		// Determine which String is longer, inputTitle or actualTitle
		String smallerTitle = "";
		String largerTitle = "";

		if (inputTitleNoSpaces.length() < actualTitleNoSpaces.length()) {
			smallerTitle = inputTitleNoSpaces;
			largerTitle = actualTitleNoSpaces;
		} else {
			smallerTitle = actualTitleNoSpaces;
			largerTitle = inputTitleNoSpaces;
		}

		// Left Similarity
		for (int i = 0; i < smallerTitle.length(); i++) {
			if (smallerTitle.charAt(i) == largerTitle.charAt(i)) {
				leftSimilarity++;
			}
		}

		// Right Similarity
		int smallerTitleLength = smallerTitle.length() - 1;
		int largerTitleLength = largerTitle.length() - 1;

		for (int j = smallerTitleLength; j >= 0; j--) {
			if (smallerTitle.charAt(j) == largerTitle.charAt(largerTitleLength)) {
				rightSimilarity++;
			}
			largerTitleLength--;
		}

		// Calculating average similarity to get final Similarity Metric
		double averageSimilarity = (leftSimilarity + rightSimilarity) / 2;

		return averageSimilarity;

	}

	/**
	 * Calculates the percentage of how many letters both the inputted book
	 * title and the comparing book title have in common with one another
	 * (Number of letters similar / All letters found in both titles).
	 *
	 * @param inputTitle  - user entered book title
	 * @param actualTitle - book title to be compared with inputTitle
	 * @return - Percentage of letters the user inputted book title has similar
	 * to the compared book title
	 */
	public double getCommonPercentage(String inputTitle, String actualTitle) {

		// Puts title to lowercase letters
		String inputLowercase = inputTitle.toLowerCase();
		String actualLowercase = actualTitle.toLowerCase();

		String inputTitleNoSpaces = inputLowercase.replaceAll(" ", "");
		String actualTitleNoSpaces = actualLowercase.replace(" ", "");

		// Initializing Character arrays to keep track of letters in titles
		ArrayList<Character> lettersInputTitle = new ArrayList<>();
		ArrayList<Character> lettersActualTitle = new ArrayList<>();

		ArrayList<Character> similarCharacters = new ArrayList<>();
		ArrayList<Character> allCharacters = new ArrayList<>();

		for (int i = 0; i < inputTitleNoSpaces.length(); i++) {
			if (!lettersInputTitle.contains(inputTitleNoSpaces.charAt(i))) {
				lettersInputTitle.add(inputTitleNoSpaces.charAt(i));
				allCharacters.add(inputTitleNoSpaces.charAt(i));
			}
		}

		for (int j = 0; j < actualTitleNoSpaces.length(); j++) {
			if (!lettersActualTitle.contains(actualTitleNoSpaces.charAt(j))) {
				lettersActualTitle.add(actualTitleNoSpaces.charAt(j));
				if (!allCharacters.contains(actualTitleNoSpaces.charAt(j))) {
					allCharacters.add(actualTitleNoSpaces.charAt(j));
				}
			}
		}

		for (int k = 0; k < allCharacters.size(); k++) {
			if (lettersInputTitle.contains(allCharacters.get(k)) && lettersActualTitle.contains(allCharacters.get(k))) {
				similarCharacters.add(allCharacters.get(k));
			}
		}

		// Initialize commonPercentage
		double commonPercentage = (double) similarCharacters.size() / allCharacters.size();

		return commonPercentage;

	}

	/**
	 * Adds inputted book title's to ArrayList that holds similar titles so
	 * long as the inputted titles have a high enough average similarity
	 * and common percentage
	 *
	 * @param bookTitle                - title of the inputted book
	 * @param similarityMetric         - average similarity (left and right) of author
	 * @param commonPercentage         - percentage of similar letters of book title
	 * @param similarityMetricStandard - average similarity standard that
	 *                                 variable similarityMetric must be higher than to be moved to ArrayList
	 *                                 of similar book titles
	 * @param commonPercentageStandard - percentage of similar letters standard
	 *                                 that variable commonPercentage must be higher than to be moved to ArrayList
	 *                                 of similar book titles
	 * @return - ArrayList of similar book titles
	 */
	public ArrayList<String> getSimilarWords(String bookTitle, double similarityMetric,
											 double commonPercentage, double similarityMetricStandard, double commonPercentageStandard) {

		ArrayList<String> similarBookTitles = new ArrayList<>();

		if (similarityMetric > similarityMetricStandard && commonPercentage > commonPercentageStandard) {
			similarBookTitles.add(bookTitle);
		}

		return similarBookTitles;

	}
}