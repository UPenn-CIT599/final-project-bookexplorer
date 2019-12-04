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
        pride.description = "Since its immediate success in 1813, Pride and Prejudice has remained one of the most popular novels in the English language. Jane Austen called this brilliant work \"her own darling child\" and its vivacious heroine, Elizabeth Bennet, \"as delightful a creature as ever appeared in print.\" The romantic clash between the opinionated Elizabeth and her proud beau, Mr. Darcy, is a splendid performance of civilized sparring. And Jane Austen's radiant wit sparkles as her characters dance a delicate quadrille of flirtation and intrigue, making this book the most superb comedy of manners of Regency England.";
        sensibility = new Book("Sense and Sensibility");
        sensibility.goodReadsID = "14935";
        sensibility.ratingsCount = 888581;
        sensibility.publicationYear = 1811;
        sensibility.averageRating = 4.07;
        sensibility.pagesNum = 409;
        sensibility.description = "Marianne Dashwood wears her heart on her sleeve, and when she falls in love with the dashing but unsuitable John Willoughby she ignores her sister Elinor's warning that her impulsive behaviour leaves her open to gossip and innuendo. Meanwhile Elinor, always sensitive to social convention, is struggling to conceal her own romantic disappointment, even from those closest to her. Through their parallel experience of love—and its threatened loss—the sisters learn that sense must mix with sensibility if they are to find personal happiness in a society where status and money govern the rules of love.";
        tippingPoint = new Book("Tipping Point");
        tippingPoint.goodReadsID = "2612";
        tippingPoint.ratingsCount = 627591;
        tippingPoint.publicationYear = 2000;
        tippingPoint.averageRating = 3.96;
        tippingPoint.pagesNum = 301;
        tippingPoint.description = "The tipping point is that magic moment when an idea, trend, or social behavior crosses a threshold, tips, and spreads like wildfire. Just as a single sick person can start an epidemic of the flu, so too can a small but precisely targeted push cause a fashion trend, the popularity of a new product, or a drop in the crime rate. This widely acclaimed bestseller, in which Malcolm Gladwell explores and brilliantly illuminates the tipping point phenomenon, is already changing the way people throughout the world think about selling products and disseminating ideas.";
        cal1 = new BookSimCalculator(sensibility, tippingPoint);
        cal2 = new BookSimCalculator(pride, sensibility);
    }

    @Test
    void descriptionSim() {
        assertTrue(cal1.descriptionSim() < cal2.descriptionSim());
    }

    @Test
    void weightedSimilarity() {
        assertTrue(cal1.weightedSimilarity() < cal2.weightedSimilarity());
    }
}