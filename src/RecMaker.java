import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RecMaker {
	/**
	 * Responsibility: makes author and book recommendations for the user
	 * Collaborator: UserInteraction, WebScraper, Book, Author
	 */

	HashMap<String, ArrayList<String>> genreAuthors;
	RequestHandler handler;

	public RecMaker() {
		//TODO - add csv file with author - genre pairs and read from csv file
		ArrayList<String> yaAuthors = new ArrayList(Arrays.asList("JK Rowling"));
		this.genreAuthors = new HashMap<String, ArrayList<String>>() {{
			put("Young Adult", yaAuthors);
		}};
	}

	/**
	 * predicts an author that the user might like
	 * @param authorID author ID from goodreads
	 * @param genre genre string from user input
	 * @return an author object
	 */
	public Author getAuthorPrediction(String authorID, String genre) throws ParserConfigurationException, SAXException, IOException {
		Author currentAuthor = handler.saveAuthorDetails(authorID);
		ArrayList<String> authorNames = genreAuthors.get(genre);
		double maxSimilarity = 0;
		String mostSimilarAuthor = "";
		for (String name : authorNames) {
//			Author newAuthor = handler.getAuthor(name);
			//TODO: add process to run similarity metrics between 2 authors
			mostSimilarAuthor = name;
		}
		return new Author(mostSimilarAuthor);
	}
	
	/**
	 * predicts book based on author prediction
	 * @param author predicted by getAuthorPrediction
	 * @return book prediction
	 */
	public Book getBookPrediction(Author author) {
		return new Book();
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
	
	public boolean doesAuthorExist(String authorName) {
		// TODO: update below logic to incorporate requestMaker
		return true;
	}
}
