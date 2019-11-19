import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

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
            Document resp = handler.authorRespDoc("Rowling");
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
            assertEquals(author.name, "JK Rowling");
            assertEquals(author.goodReadsID, "1077326");
            assertEquals(author.worksCount, 250);
            assertNotEquals(author.description, "");
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
        Document resp = null;
        try {
            resp = handler.authorRespDoc("Rowling");
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
}
