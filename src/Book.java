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
//		commonPercentage = (commonPercentage, %.4f);
		
		return commonPercentage;
		
	}
	
	public ArrayList<String> getSimilarWords(String bookTitle, double similarityMetric,
			double commonPercentage, double similarityMetricStandard, double commonPercentageStandard) {
		
		ArrayList<String> similarBookTitles = new ArrayList<>();
		
		if (similarityMetric > similarityMetricStandard && commonPercentage > commonPercentageStandard) {
			similarBookTitles.add(bookTitle);
		}
		
		return similarBookTitles;
		
	}

}
