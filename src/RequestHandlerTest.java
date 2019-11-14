import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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
    void getAuthor() {
        //TODO stub below instead of making actual http requests
        try {
            String authorResp = handler.getAuthorID("Rowling");
            assertEquals("1077326 J.K. Rowling", authorResp);
        } catch (MalformedInputException exp) {
            System.out.println("Incorrect search author URL");
        } catch (IOException exp) {
            System.out.println(exp.getLocalizedMessage());
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
