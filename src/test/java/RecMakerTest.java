import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RecMakerTest {

    RecMaker maker;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        maker = new RecMaker("src/test/resources/testFile.csv");
    }

    @Test
    void getAuthorPrediction() {
        try {
            Author recAuthor = maker.getAuthorPrediction("656983", "young adults");
            assertFalse(recAuthor.name.equals(""));
            assertFalse(recAuthor.name.equals("J.R.R. Tolkien"));
            assertFalse(recAuthor.goodReadsID.equals(""));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBookPrediction() {
        Author tolkien = new Author("J.R.R. Tolkien");
        tolkien.followersCount = 54795;
        tolkien.worksCount = 482;
        tolkien.goodReadsID = "656983";
        Book hobbit = new Book("The Hobbit, or There and Back Again");
        hobbit.description = "Now recognized as a timeless classic, this introduction to the hobbit Bilbo Baggins, the wizard Gandalf, Gollum, and the spectacular world of Middle-earth recounts of the adventures of a reluctant hero, a powerful and dangerous ring, and the cruel dragon Smaug the Magnificent.";
        hobbit.goodReadsID = "5907";
        hobbit.ratingsCount = 2606534;
        hobbit.averageRating = 4.27;
        hobbit.pagesNum = 398;
        hobbit.publicationYear = 2003;
        tolkien.books.add(hobbit);
        Book recBook = maker.getBookPrediction(tolkien, "Harry Potter and the Sorcerer's Stone");
        assertFalse(recBook.title.equals(""));
        assertFalse(recBook.goodReadsID.equals(""));
    }

    @Test
    void readGenreAuthorsFile() {
        assertEquals(new ArrayList<>(Arrays.asList("J.K. Rowling")), maker.genreAuthors.get("young adults"));
    }
}