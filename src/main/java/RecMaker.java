import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RecMaker {
	/**
	 * Responsibility: makes author and book recommendations for the user
	 * Collaborator: UserInteraction, WebScraper, Book, Author
	 */

	HashMap<String, ArrayList<String>> genreAuthors;
	RequestHandler handler;
	static HashMap<String, Author> seenAuthorsByID;
	static HashMap<String, Book> seenBooksByTitle;

	public RecMaker(String fileName) {
		//TODO - add csv file with author - genre pairs and read from csv file
		this.genreAuthors = readGenreAuthorsFile(fileName);
		this.seenAuthorsByID = new HashMap<String, Author>();
		this.seenBooksByTitle = new HashMap<String, Book>();
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
			Document authorDetailResp = handler.getAuthorDetail(authorID);
			ArrayList<HashMap<String, String>> booksAttributes = handler.getAuthorBooks(authorDetailResp);
			saveBooksToAuthor(booksAttributes, currentAuthor);
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
			AuthorSimCalculator simCalculator = new AuthorSimCalculator(currentAuthor, compareAuthor);
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
		Book mostSimilarBook = null;
		Book originalBook;
		double maxSimilarity = 0;
		if(seenBooksByTitle.containsKey(bookTitle)) {
			originalBook = seenBooksByTitle.get(bookTitle);
		} else {
			originalBook = handler.searchBookByTitle(bookTitle);
		}
		for (Book book : author.books) {
			BookSimCalculator calc = new BookSimCalculator(originalBook, book);
			if (calc.weightedSimilarity() >= maxSimilarity) {
				maxSimilarity = calc.weightedSimilarity();
				mostSimilarBook = book;
			}
		}
		return mostSimilarBook;
	}

	/**
	 * find or create book objects and associates the books with the author
	 * @param booksAttributes hashmap returned from #getAuthorBooks method from RequestHandler
	 * @param author
	 */
	private void saveBooksToAuthor(ArrayList<HashMap<String, String>> booksAttributes, Author author) {
		for (HashMap<String, String> attributes : booksAttributes) {
			if (seenBooksByTitle.containsKey(attributes.get("title"))) {
				Book seenBook = seenBooksByTitle.get(attributes.get("title"));
				seenBook.authors.add(author);
				author.books.add(seenBook);
			} else {
				Book newBook = new Book(attributes.get("title"));
				newBook.goodReadsID = attributes.get("goodReadsID");
				newBook.authors.add(author);
				newBook.description = attributes.get("description");
				newBook.imageUrl = attributes.get("imageURL");
				newBook.averageRating = Double.valueOf(attributes.get("rating"));
				seenBooksByTitle.put(newBook.goodReadsID, newBook);
			}
		}
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
