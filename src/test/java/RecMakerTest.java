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
            assertTrue(recAuthor.name != "");
            assertTrue(recAuthor.name != null);
            assertTrue(recAuthor.goodReadsID != "");

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
    }

    @Test
    void readGenreAuthorsFile() {
        assertEquals(new ArrayList<>(Arrays.asList("J.K. Rowling")), maker.genreAuthors.get("young adults"));
    }
}