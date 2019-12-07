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
		String nextRoundAnswer = interacter.getInputFromUser("Would you like to see another recommendation?", in).toLowerCase();
		while (!(nextRoundAnswer.equals("no") || nextRoundAnswer.equals("n"))) {
			interacter.interactWithUser(in);
			nextRoundAnswer = interacter.getInputFromUser("Would you like to see another recommendation?", in).toLowerCase();
		}
		in.close();
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
		System.out.println("Cooking up your next book...");
		HashMap<String, Object> results = recommendAuthorAndBook(authorName, genre, bookName);
		System.out.println("Here is our recommendations: ");
		Book recBook = (Book) results.get("Book");
		Author recAuthor = (Author) results.get("Author");
		System.out.println("Our recommended book to you: ");
		System.out.println(recBook.title);
		System.out.println(recBook.description);
		System.out.println("Our recommended author to you: ");
		System.out.println(recAuthor.name);
	}

	/**
	 *
	 * @param authorName - author name input from user
	 * @param genreName - genre name input from user
	 * @param bookTitle - book title input from user
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
