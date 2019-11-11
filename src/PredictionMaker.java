
public class PredictionMaker {
	/**
	 * Responsibility: runs the logics to make predictions
	 */
	
	
	/**
	 * predicts an author that the user might like
	 * @param author name from user input
	 * @return an author object
	 */
	public Author getAuthorPrediction(String authorFromUser) {
		return "Stephen King";
	}
	
	/**
	 * predicts book based on author prediction
	 * @param author predicted by getAuthorPrediction
	 * @return book prediction
	 */
	public Book getBookPrediction(Author author) {
		
	}
	
	/**
	 * searches for author from user input 
	 * @param author
	 */
	private void findAuthor(String author) {
		// use goodreads API to search for author URL in goodreads
		// if author exists in goodreads, scrape author website, and return description and book titles
		// if author does not exist, ask user for another input
	}
}
