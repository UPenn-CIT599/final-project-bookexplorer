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
	 * Process:
	 */

	HashMap<String, ArrayList<String>> genreAuthors;
	RequestHandler handler;
	HashMap<String, Author> seenAuthorsByID;
	HashMap<String, Book> seenBooksByID;

	public RecMaker(String fileName) {
		//TODO - add csv file with author - genre pairs and read from csv file
		final ArrayList<String> yaAuthors = new ArrayList(Arrays.asList("JK Rowling"));
		this.genreAuthors = readGenreAuthorsFile(fileName);
		this.seenAuthorsByID = new HashMap<String, Author>();
		this.seenBooksByID = new HashMap<String, Book>();
	}

	/**
	 * predicts an author that the user might like
	 * @param authorID author ID from goodreads
	 * @param genre genre string from user input
	 * @return an author object
	 */
	public Author getAuthorPrediction(String authorID, String genre) throws ParserConfigurationException, SAXException, IOException {
		Author currentAuthor;
		// check if author is seen, if yes, get author, if not, get author information by pulling data from GoodReads API, and save it to seenAuthors hash map
		if (!seenAuthorsByID.containsKey(authorID)) {
			currentAuthor = handler.saveAuthorDetails(authorID);
			seenAuthorsByID.put(currentAuthor.goodReadsID, currentAuthor);
		} else {
			currentAuthor = seenAuthorsByID.get(authorID);
		}
		Author mostSimilarAuthor = currentAuthor;
		// get list of authors from the same genre
		ArrayList<String> authorIDs = genreAuthors.get(genre);
		double maxSimilarity = 0;
		// find author of the max weighted similarity
		for (String id : authorIDs) {
			Author compareAuthor = seenAuthorsByID.get(id);
			AuthorSimilarity simCalculator = new AuthorSimilarity(currentAuthor, compareAuthor);
			double similarity = simCalculator.weightedSimilarity();
			if (similarity > maxSimilarity) {
				maxSimilarity = similarity;
				mostSimilarAuthor = compareAuthor;
			}
		}
		return mostSimilarAuthor;
	}
	
	/**
	 * predicts book based on author prediction
	 * @param author predicted by getAuthorPrediction
	 * @return book prediction
	 */
	public Book getBookPrediction(Author author, String bookTitle) {
		for (Book book : author.books) {

		}
		return new Book("harry potter");
	}

	/**
	 * read data file that contains genre -> author names
	 * @param fileName
	 * @return
	 */
	public HashMap<String, ArrayList<String>> readGenreAuthorsFile(String fileName) {
		return genreAuthors;
	}
}
