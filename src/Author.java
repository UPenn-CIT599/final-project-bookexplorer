import java.util.ArrayList;

/**
 * MCIT 591 - Final Project
 * 
 * Author Class
 * 
 * This class sets the criteria of determining the most common author names
 * to the user's searched author name (if applicable). The program utilizes
 * similar methods as the WordRecommender class from Assignment Four, utilizing
 * the left and right similarity metrics for both first and last names, obtaining
 * name suggestions, and tracking all authors with the same letter of first and last
 * name.
 * 
 * @author Thomas Flannery
 *
 */
public class Author {
	
	public double leftRightSimilarityMetric(String inputAuthor, String actualAuthor) {
		
		// Puts author's name to lowercase letters
		inputAuthor.toLowerCase();
		actualAuthor.toLowerCase();
		
		// Initializing leftSimilarity, rightSimilarity
		double leftSimilarity = 0.0;
		double rightSimilarity = 0.0;
		
		// Determine which String is longer, inputAuthor or actualAuthor
		String smallerAuthor = "";
		String largerAuthor = "";
		
		if (inputAuthor.length() < actualAuthor.length()) {
			smallerAuthor = inputAuthor;
			largerAuthor = actualAuthor;
		} else {
			smallerAuthor = actualAuthor;
			largerAuthor = inputAuthor;
		}
		
		// Left Similarity
		for (int i = 0; i < smallerAuthor.length(); i++) {
			if (smallerAuthor.charAt(i) == largerAuthor.charAt(i)) {
				leftSimilarity++;
			}
		}
		
		// Right Similarity
		int smallerAuthorLength = smallerAuthor.length() - 1;
		int largerAuthorLength = largerAuthor.length() - 1;
		
		for (int j = smallerAuthorLength; j >= 0; j++) {
			if (smallerAuthor.charAt(j) == largerAuthor.charAt(largerAuthorLength)) {
				rightSimilarity++;
			}
			largerAuthorLength--;
		}
		
		// Calculating average similarity to get final Similarity Metric
		double averageSimilarity = (leftSimilarity + rightSimilarity) / 2;
		
		return averageSimilarity;
		
	}
	
	public double getCommonPercentage(String inputAuthor, String actualAuthor) {
		
		// Puts title to lowercase letters
		inputAuthor.toLowerCase();
		actualAuthor.toLowerCase();
		
		// Initializing Character arrays to keep track of letters in titles
		ArrayList<Character> lettersInputAuthor = new ArrayList<>();
		ArrayList<Character> lettersActualAuthor = new ArrayList<>();
		
		ArrayList<Character> similarCharacters = new ArrayList<>();
		ArrayList<Character> allCharacters = new ArrayList<>();
		
		for (int i = 0; i < inputAuthor.length(); i++) {
			if (!lettersInputAuthor.contains(inputAuthor.charAt(i))) {
				lettersInputAuthor.add(inputAuthor.charAt(i));
				allCharacters.add(inputAuthor.charAt(i));
			}
		}
		
		for (int j = 0; j < actualAuthor.length(); j++) {
			if (!lettersActualAuthor.contains(actualAuthor.charAt(j))) {
				lettersActualAuthor.add(actualAuthor.charAt(j));
				if (!allCharacters.contains(actualAuthor.charAt(j))) {
					allCharacters.add(actualAuthor.charAt(j));
				}
			}
			
		}
		
		for (int k = 0; k < allCharacters.size(); k++) {
			if (lettersInputAuthor.contains(allCharacters.get(k)) && lettersActualAuthor.contains(allCharacters.get(k))) {
				similarCharacters.add(allCharacters.get(k));
			}
		}
		
		// Initialize commonPercentage
		double commonPercentage = similarCharacters.size() / allCharacters.size();
		
		return commonPercentage;
		
	}
	
	public ArrayList<String> getSimilarAuthors(String author, double similarityMetric,
			double commonPercentage, double similarityMetricStandard, double commonPercentageStandard) {
		
		ArrayList<String> similarAuthorNames = new ArrayList<>();
		
		if (similarityMetric > similarityMetricStandard && commonPercentage > commonPercentageStandard) {
			similarAuthorNames.add(author);
		}
		
		return similarAuthorNames;
		
	}

}
