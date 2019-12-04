import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UserInteraction {
	
	/**
	 * Responsibility: Controls main logic for user interactions
	 * Collaborator: PredictionMaker
	 */

	RecMaker recMaker;
	RequestHandler reqHandler;

	public UserInteraction() {
		this.recMaker = new RecMaker("src/main/resources/genreAuthors.csv");
		this.reqHandler = new RequestHandler();
	}

	public static void main(String[] args) {
		UserInteraction interacter = new UserInteraction();
		String authorName = interacter.getInputFromUser("Please provide the name of an author whom you most recently read");
		String bookName = interacter.getInputFromUser("Please provide the name of a book that you most recently read:");
		String genre = interacter.getInputFromUser("Please provide a genre: ").toLowerCase();
		interacter.recommendAuthorAndBook(authorName, genre, bookName);
	}

	public String getInputFromUser(String phrase) {
		Scanner in = new Scanner(System.in);
		System.out.println(phrase);
		String input = in.nextLine();
		in.close();
		return input;
	}

	/**
	 *
	 * @param authorName
	 * @param genreName
	 * @param bookTitle
	 * @return a hash map of {"Book": suggestedBook, "Author": suggestedAuthor}
	 */
	public HashMap<String, Object> recommendAuthorAndBook(String authorName, String genreName, String bookTitle) {
		Author nextAuthor = null;
		Book nextBook = null;
		try {
			Document authorSearchResp = reqHandler.authorSearchDoc(authorName);
			while (!reqHandler.isAuthorFound(authorSearchResp)) {
				authorName = getInputFromUser("Sorry, we couldn't find your author. Please input a new author name: ");
				authorSearchResp = reqHandler.authorSearchDoc(authorName);
			}
			Document bookSearchResp = reqHandler.getBookSearchResp(bookTitle);
			while (!reqHandler.isBookFound(bookSearchResp)) {
				bookTitle = getInputFromUser("Sorry, we couldn't find your book. Please input a new book name: ");
				bookSearchResp = reqHandler.getBookSearchResp(bookTitle);
			}
			String authorID = reqHandler.getAuthorID(authorSearchResp);
			nextAuthor = recMaker.getAuthorPrediction(authorID, genreName);
			nextBook = recMaker.getBookPrediction(nextAuthor, bookTitle);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Author finalNextAuthor = nextAuthor;
		Book finalNextBook = nextBook;
		return new HashMap<String, Object>() {{
			put("Author", finalNextAuthor);
			put("Book", finalNextBook);
		}};
	}
}
