import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BookSimCalculatorTest {

    Book pride;
    Book sensibility;
    Book tippingPoint;
    BookSimCalculator cal1;
    BookSimCalculator cal2;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        pride = new Book("Pride and Prejudice");
        pride.goodReadsID = "1885";
        pride.ratingsCount = 2676768;
        pride.publicationYear = 1813;
        pride.averageRating = 4.26;
        pride.pagesNum = 279;
        pride.description = "Jane Austen's radiant wit sparkles as her characters dance a delicate quadrille of flirtation and intrigue, making this book the most superb comedy of manners of Regency England.";
        sensibility = new Book("Sense and Sensibility");
        sensibility.goodReadsID = "14935";
        sensibility.ratingsCount = 888581;
        sensibility.publicationYear = 1811;
        sensibility.averageRating = 4.07;
        sensibility.pagesNum = 409;
        sensibility.description = "Through their parallel experience of love—and its threatened loss—the sisters learn that sense must mix with sensibility if they are to find personal happiness in a society where status and money govern the rules of love.";
        tippingPoint = new Book("Tipping Point");
        tippingPoint.goodReadsID = "2612";
        tippingPoint.ratingsCount = 627591;
        tippingPoint.publicationYear = 2000;
        tippingPoint.averageRating = 3.96;
        tippingPoint.pagesNum = 301;
        tippingPoint.description = "Gladwell introduces us to the particular personality types who are natural pollinators of new ideas and trends, the people who create the phenomenon of word of mouth.";
        cal1 = new BookSimCalculator(sensibility, tippingPoint);
        cal2 = new BookSimCalculator(pride, sensibility);
    }

    @Test
    void descriptionSim() {
        double sim1 = cal1.descriptionSim();
        double sim2 = cal2.descriptionSim();
        System.out.println(sim1);
        System.out.println(sim2);
        assertTrue(sim1 < sim2);
    }

    @Test
    void weightedSimilarity() {
        double weighted1 = cal1.weightedSimilarity();
        double weighted2 = cal2.weightedSimilarity();
        System.out.println(weighted1);
        System.out.println(weighted2);
        assertTrue(weighted1 < weighted2);
    }
}