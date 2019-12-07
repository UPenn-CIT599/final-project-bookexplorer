import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class RecMaker {
	/**
	 * Responsibility: makes author and book recommendations for the user
	 * Collaborator: UserInteraction, Book, Author, RequestHandler
	 */

	HashMap<String, ArrayList<String>> genreAuthors;
	RequestHandler handler;
	static HashMap<String, Author> seenAuthorsByID;
	static HashMap<String, Book> seenBooksByTitle;

	public RecMaker(String fileName) {
		this.genreAuthors = new HashMap<>();
		this.genreAuthors = readGenreAuthorsFile(fileName);
		handler = new RequestHandler();
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
			currentAuthor = createAuthorFromId(authorID);
			seenAuthorsByID.put(currentAuthor.goodReadsID, currentAuthor);
		} else {
			currentAuthor = seenAuthorsByID.get(authorID);
		}
		Author mostSimilarAuthor = currentAuthor;
		// get list of authors from the same genre
		if (!genreAuthors.containsKey(genre)) {
			System.out.println("Genre not found, please try again");
		}
		ArrayList<String> authorNames = genreAuthors.get(genre);
		ArrayList<String> authorIDs = new ArrayList<String>();
		// get 5 random authors from the same genre from genre authors data list
		ArrayList<Integer> authorIndexes = Utility.randomIntArray(authorNames.size(), 4);
		for (int index : authorIndexes) {
			String name = authorNames.get(index);
		    if (!name.equals(currentAuthor.name)) {
                authorIDs.add(handler.getAuthorID(handler.authorSearchDoc(name)));
            }
		}
		double maxSimilarity = 0;
		// find author of the max weighted similarity
		for (String id : authorIDs) {
			Author compareAuthor = null;
			if (seenAuthorsByID.containsKey(id)) {
				compareAuthor = seenAuthorsByID.get(id);
			} else {
				compareAuthor = createAuthorFromId(id);
				seenAuthorsByID.put(id, compareAuthor);
			}
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
		Book originalBook = new Book(bookTitle);
		double maxSimilarity = 0;
		if(seenBooksByTitle.containsKey(bookTitle)) {
			originalBook = seenBooksByTitle.get(bookTitle);
		} else {
			try {
				Document bookSearchResp = handler.getBookSearchResp(bookTitle);
				originalBook = handler.getBookFromBookSearch(bookSearchResp);
				handler.saveBookDescriptions(originalBook);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
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
	 * @param author - author object who needs book associations
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
				author.books.add(newBook);
				seenBooksByTitle.put(newBook.goodReadsID, newBook);
			}
		}
	}

	/**
	 * read data file that contains genre -> author name
	 * @param fileName - name of genre to author data file
	 * @return hashmap of genre to author names
	 */
	public HashMap<String, ArrayList<String>> readGenreAuthorsFile(String fileName) {
		File dataFile = new File(fileName);
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(dataFile);
		} catch (FileNotFoundException e) {
			System.out.println(String.format("File %s not found, exiting the program", fileName));
			System.exit(0);
		}
		fileScanner.nextLine();
		while (fileScanner.hasNextLine()) {
			String[] genreAuthorsArray = fileScanner.nextLine().split(",");
			String author = genreAuthorsArray[0];
			String genre = genreAuthorsArray[1].toLowerCase();
			if (genreAuthors.containsKey(genre)) {
				genreAuthors.get(genre).add(author);
			} else {
				genreAuthors.put(genre, new ArrayList<String>(Arrays.asList(author)));
			}
		}
		return genreAuthors;
	}

	private Author createAuthorFromId(String authorID) throws ParserConfigurationException, SAXException, IOException {
		Author compareAuthor = handler.saveAuthorDetails(authorID);
		Document authorDetailResp = handler.getAuthorDetail(authorID);
		ArrayList<HashMap<String, String>> booksAttributes = handler.getAuthorBooks(authorDetailResp);
		saveBooksToAuthor(booksAttributes, compareAuthor);
		return compareAuthor;
	}
}
