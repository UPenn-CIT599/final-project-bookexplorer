import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.MalformedInputException;

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
        assertEquals("1077326 J.K. Rowling", authorResp);
    }

    @Test
    void authorRespDoc() {
        try {
            Document resp = handler.authorRespDoc("Rowling");
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
        boolean found = handler.isAuthorFound("Arthur Conan Doyle");
        assertEquals(true, found);
    }
}
