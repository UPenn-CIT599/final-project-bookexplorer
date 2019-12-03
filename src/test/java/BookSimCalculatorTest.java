import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BookSimCalculatorTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @Test
    void descriptionSim() {

    }

    @Test
    void weightedSimilarity() {
        Book book1 = new Book("Pride and Prejudice");
        book1.goodReadsID = "1885";
        book1.ratingsCount = 2676768;
        book1.publicationYear = 1813;
        book1.averageRating = 4.26;
        book1.pagesNum = 279;
        Book book2 = new Book("Sense and Sensibility");
        book2.goodReadsID = "14935";
        book2.ratingsCount = 888581;
        book2.publicationYear = 1811;
        book2.averageRating = 4.07;
        book2.pagesNum = 409;
        Book book3 = new Book("Tipping Point");
        book3.goodReadsID = "2612";
        book3.ratingsCount = 627591;
        book3.publicationYear = 2000;
        book3.averageRating = 3.96;
        book3.pagesNum = 301;
        BookSimCalculator cal2 = new BookSimCalculator(book1, book2);
        BookSimCalculator cal1 = new BookSimCalculator(book2, book3);
    }
}