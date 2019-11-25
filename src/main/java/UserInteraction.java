import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class UserInteraction {
	
	/**
	 * Responsibility: Controls main logic for user interactions
	 * Collaborator: PredictionMaker
	 */

	RecMaker recMaker;
	RequestHandler reqHandler;

	public UserInteraction() {
		this.recMaker = new RecMaker("genreAuthors.csv");
		this.reqHandler = new RequestHandler();
	}

	public static void main(String[] args) {
		// To user: select a genre from dropdown menu that they recently read
		// To user: Please provide an author that you most recently read and enjoyed
		// To user: please provide a book that you most recently read and enjoyed
		// Run similarity metrics of authors the user input, with our list of authors
		// pick an author recommended by our algo
		// pick a book by the author similar to the book that user provided
		// Return recommended author, with image and short description
		// Return recommended author's book, with image and short description, amazon link, etc.
		// Ask for user feedback - if not good, adjust weight of similarity metrics, then give another one
	}

	public String authorInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please provide an author that you most recently read and enjoyed");
		String authorName = in.nextLine();
		return authorName;
	}

	public void bookInput() {

	}

	public void recommendAuthorAndBook(String authorName, String genreName, String bookName) {
		Author nextAuthor = null;
		try {
			Document authorApiResp = reqHandler.authorSearchDoc(authorName);
			while(!reqHandler.isAuthorFound(authorApiResp)) {
				authorName = authorInput();
				authorApiResp = reqHandler.authorSearchDoc(authorName);
			}
			nextAuthor = recMaker.getAuthorPrediction(authorName, genreName);
		} catch (ParserConfigurationException e) {
			System.out.println("Error finding your author, please try again");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Book nextBook = recMaker.getBookPrediction(nextAuthor, bookName);
	}
}
