import java.util.ArrayList;

public class Author {

	public String name;
	public String description;
	public String goodReadsID;
	public int worksCount;
	public String genre;
	public ArrayList<Book> books;


	public Author(String name) {
		this.name = name;
		this.worksCount = 0;
		this.books = new ArrayList<Book>();
		this.description = "";
	}

	@Override
	public boolean equals(Object author) {
		Author authorCompared = (Author) author;
		return authorCompared.name.equals(name) && authorCompared.goodReadsID.equals(goodReadsID);
	}

}
