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
	HashMap<String, Author> seenAuthorsByID;

	public RecMaker() {
		//TODO - add csv file with author - genre pairs and read from csv file
		final ArrayList<String> yaAuthors = new ArrayList(Arrays.asList("JK Rowling"));
		this.genreAuthors = new HashMap<String, ArrayList<String>>() {{
			put("Young Adult", yaAuthors);
		}};
		this.seenAuthorsByID = new HashMap<String, Author>();
	}

	/**
	 * predicts an author that the user might like
	 * @param authorID author ID from goodreads
	 * @param genre genre string from user input
	 * @return an author object
	 */
	public Author getAuthorPrediction(String authorID, String genre) throws ParserConfigurationException, SAXException, IOException {
		Author currentAuthor;
		if (!seenAuthorsByID.containsKey(authorID)) {
			currentAuthor = handler.saveAuthorDetails(authorID);
		} else {
			currentAuthor = seenAuthorsByID.get(authorID);
		}
		Author mostSimilarAuthor = currentAuthor;
		ArrayList<String> authorIDs = genreAuthors.get(genre);
		double maxSimilarity = 0;
		for (String id : authorIDs) {
			Author compareAuthor = seenAuthorsByID.get(id);
			double similarity = authorSimilarity(currentAuthor, compareAuthor);
			if (similarity > maxSimilarity) {
				maxSimilarity = similarity;
				mostSimilarAuthor = compareAuthor;
			}
		}
		return mostSimilarAuthor;
	}

	public double authorSimilarity(Author author1, Author author2) {
		return 0.0;
	}
	
	/**
	 * predicts book based on author prediction
	 * @param author predicted by getAuthorPrediction
	 * @return book prediction
	 */
	public Book getBookPrediction(Author author) {
		for (Book book : author.books) {

		}
		return new Book();
	}
}
