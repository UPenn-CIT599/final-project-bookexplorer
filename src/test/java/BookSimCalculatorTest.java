import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookSimCalculatorTest {

    @BeforeEach
    void setUp() {
        Book book1 = new Book("Pride and Prejudice");
        book1.goodReadsID = "3060926";
        book1.ratingsCount = 2676768;
        book1.publicationYear = 1813;
        book1.averageRating = 4.26;
        Book book2 = new Book("Sense and Sensibility");
        Book book3 = new Book("Tipping Point");
    }

    @Test
    void descriptionSim() {

    }

    @Test
    void weightedSimilarity() {
    }
}