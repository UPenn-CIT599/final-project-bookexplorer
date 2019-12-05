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
		this.recMaker = new RecMaker("src/main/resources/authors_and_genres.csv");
		this.reqHandler = new RequestHandler();
	}

	public static void main(String[] args) {
		UserInteraction interacter = new UserInteraction();
		Scanner in = new Scanner(System.in);
		interacter.interactWithUser(in);
		System.out.println("Would you like to see another book?");
		String answer = in.nextLine();
		while (!(answer.equals("No") || answer.equals("N"))) {
			interacter.interactWithUser(in);
		}
	}

	public String getInputFromUser(String phrase, Scanner scanner) {
		System.out.println(phrase);
		String input = scanner.nextLine();
		return input;
	}

	public void interactWithUser(Scanner in) {
		String authorName = getInputFromUser("Please provide the name of an author whom you most recently read", in);
		String bookName = getInputFromUser("Please provide the name of a book that you most recently read:", in);
		String genre = getInputFromUser("Please provide a genre: ", in).toLowerCase();
		in.close();
		System.out.println("Cooking up your next book...");
		HashMap<String, Object> results = recommendAuthorAndBook(authorName, genre, bookName);
		System.out.println("Here is our recommendations: ");
		String bookTitle = ((Book) results.get("Book")).title;
		String recAuthorName = ((Author) results.get("Author")).name;
		System.out.println(bookTitle);
		System.out.println(recAuthorName);
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
				Scanner in = new Scanner(System.in);
				authorName = getInputFromUser("Sorry, we couldn't find your author. Please input a new author name: ", in);
				authorSearchResp = reqHandler.authorSearchDoc(authorName);
				in.close();
			}
			Document bookSearchResp = reqHandler.getBookSearchResp(bookTitle);
			while (!reqHandler.isBookFound(bookSearchResp)) {
				Scanner in = new Scanner(System.in);
				bookTitle = getInputFromUser("Sorry, we couldn't find your book. Please input a new book name: ", in);
				bookSearchResp = reqHandler.getBookSearchResp(bookTitle);
				in.close();
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
