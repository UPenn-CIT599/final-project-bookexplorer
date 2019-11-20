import java.util.ArrayList;

public class Book {

	String title;
	String description;
	String imageUrl;
	double averageRating;
	int ratingsCount;
	int pagesNum;
	ArrayList<Author> authors;

	public Book(String title) {
		this.title = title;
		this.description = "";
		this.imageUrl = "";
		this.averageRating = 0.0;
		this.ratingsCount = 0;
		this.pagesNum = 0;
		this.authors = new ArrayList<Author>();
	}
}