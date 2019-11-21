import java.util.ArrayList;

/**
 * MCIT 591 - Final Project
 * 
 * SimilarityCalculator Class
 * 
 * This class gathers the similarity metrics and common percentages
 * found in four other classes, the Book class, Author class,
 * User class, and Publisher class, in order to determine which
 * books should be recommended to the user.
 * 
 * @author Thomas Flannery
 *
 */
public class SimilarityCalculator {
	
	public String suggestedBook;
	
	public ArrayList<String> getSimilarBooks(double descriptionSimilarity, double averageRating, int fanCount,
			int worksCount) {

		ArrayList<String> similarBooks = new ArrayList<>();
		
		// Check that bookDescription similarityCounter is above certain amount
		if (descriptionSimilarity > 0.6) {

			// Compare average ratings of books
			if (averageRating > 3.0) {
				
				// Compare number of fans of author
				if (fanCount > 500) {

					// Compare works counts of authors
					if (worksCount > 30) {
						similarBooks.add(suggestedBook);
					}
				}
			}
		}
		
		return similarBooks;
		
	}
	
}
