import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import static org.junit.jupiter.api.Assertions.*;

class RequestMakerTest {

    RequestMaker maker;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        maker = new RequestMaker();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void getAuthor() {
        try {
            System.out.println(maker.getAuthor("Rowling"));
        } catch (MalformedInputException exp) {
            System.out.println("Incorrect search author URL");
        } catch (IOException exp) {
            System.out.println(exp.getLocalizedMessage());
        }
    }
}
