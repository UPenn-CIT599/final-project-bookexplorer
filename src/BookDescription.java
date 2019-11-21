import java.util.ArrayList;

/**
 * MCIT - 591 Final Project
 * 
 * BookDescription Class
 * 
 * This class sets the 
 * 
 * @author Thomas Flannery
 *
 */
public class BookDescription {
	
	private ArrayList<String> bookDescription;
	private ArrayList<String> descriptionSynonyms;
	
	/**
	 * For each word found in an inputted book's description, an ArrayList
	 * is created that holds all synonyms of the words written in the book description.
	 * @param bookDescription - List of all words in book description
	 * @return - ArrayList of synonyms
	 */
	public ArrayList<String> getSynonyms(ArrayList<String> bookDescription) {
		
		for (String word : bookDescription) {
			
		}
		
		return descriptionSynonyms;
		
	}
	
	/**
	 * This class calculates the number of words greater than four letters
	 * in a book's description match another book description (including synonyms).
	 * @param inputBookDescription - description of inputted book
	 * @param actualBookDescription - description of comparing book
	 * @return
	 */
	public int bookDescriptionSimilarityMetric(ArrayList<String> inputBookDescription, ArrayList<String> actualBookDescription,
			ArrayList<String> inputSynonyms, ArrayList<String> actualSynonyms) {
		
		int similarityCounter = 0;
		
		for (String inputWord : inputBookDescription) {
//			for (String actualWord : actualBookDescription) {
//				if (inputWord.length() > 4) {
//					if (inputWord.equals(actualWord) || inputWord.equals(actualSynonym) ||
//							inputSynonym.equals(actualSynonym) || inputSynonym.equals(actualWord)) {
//						similarityCounter++;
//					}
//				}
//			}
		}
		
		return similarityCounter;
		
	}

}
