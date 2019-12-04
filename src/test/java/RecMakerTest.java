import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecMakerTest {

    RecMaker maker;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        maker = new RecMaker("src/test/resources/testFile.csv");
    }

    @Test
    void getAuthorPrediction() {
    }

    @Test
    void getBookPrediction() {
    }

    @Test
    void readGenreAuthorsFile() {
        assertEquals("J.K. Rowling", maker.genreAuthors.get("Young Adults"));
    }
}