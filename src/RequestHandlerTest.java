import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    RequestHandler handler;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        handler = new RequestHandler();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void getAuthor() {
        try {
            String authorResp = handler.getAuthor("Rowling");
            assertEquals("JK Rowling", authorResp);
        } catch (MalformedInputException exp) {
            System.out.println("Incorrect search author URL");
        } catch (IOException exp) {
            System.out.println(exp.getLocalizedMessage());
        }
    }
}
