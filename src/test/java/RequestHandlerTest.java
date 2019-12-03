import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    RequestHandler handler;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        handler = new RequestHandler();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAuthorID() {
        try {
            Document resp = handler.authorSearchDoc("Rowling");
            String authorID = handler.getAuthorID(resp);
            assertEquals("1077326", authorID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveAuthorDetails() {
        try {
            String authorID = "1077326";
            Author author = handler.saveAuthorDetails(authorID);
            assertEquals("J.K. Rowling", author.name);
            assertEquals("1077326", author.goodReadsID);
            assertNotEquals("", author.description);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isAuthorFound() {
        try {
            Document resp = handler.authorSearchDoc("Rowling");
            boolean found = handler.isAuthorFound(resp);
            assertEquals(true, found);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isBookFound() {
        try {
            Document resp = handler.getBookSearchResp("Tipping Point");
            assertEquals(true, handler.isBookFound(resp));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAuthorBooks() {
        try {
            Document authorDetail = handler.getAuthorDetail("18210028");
            ArrayList<HashMap<String, String>> booksAttributes = handler.getAuthorBooks(authorDetail);
            assertEquals("The Donald J. Trump Presidential Twitter Library", booksAttributes.get(0).get("title"));
            assertEquals("4.0", booksAttributes.get(0).get("rating"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchBookByTitle() {
        try {
            Document resp = handler.getBookSearchResp("Pride and Prejudice");
            Book searched = handler.getBookFromBookSearch(resp);
            assertEquals("1885", searched.goodReadsID);
            assertEquals("Pride and Prejudice", searched.title);
            assertEquals(1813, searched.publicationYear);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
