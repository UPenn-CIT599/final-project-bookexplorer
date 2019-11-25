import java.util.ArrayList;

public class Book {

	String title;
	String description;
	String imageUrl;
	double averageRating;
	int ratingsCount;
	int pagesNum;
	String goodReadsID;
	ArrayList<Author> authors;

	public Book(String title) {
		this.title = title;
		this.description = "";
		this.imageUrl = "";
		this.averageRating = 0.0;
		this.ratingsCount = 0;
		this.pagesNum = 0;
		this.goodReadsID = "";
		this.authors = new ArrayList<Author>();
	}

	@Override
	public boolean equals(Object comparedBook) {
		Book compared = (Book) comparedBook;
		return compared.title.equals(title) && compared.goodReadsID.equals(goodReadsID);
	}
}