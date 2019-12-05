import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorSimCalculatorTest {

    Author rowling;
    Author tolkien;
    Author dickens;
    Author austen;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        rowling = new Author("J.K. Rowling");
        Book stone = new Book("Harry Potter and the Sorcerer's Stone");
        tolkien = new Author("J.R.R. Tolkien");
        dickens = new Author("Charles Dickens");
        austen = new Author("Jane Austen");
        Book prideAndPrejudice = new Book("Pride and Prejudice");
        rowling.worksCount = 246;
        rowling.followersCount = 204544;
        stone.goodReadsID = "3";
        stone.description = "Full of sympathetic characters, wildly imaginative situations, and countless exciting details, the first installment in the series assembles an unforgettable magical world and sets the stage for many high-stakes adventures to come.";
        rowling.books.add(stone);
        prideAndPrejudice.goodReadsID = "1885";
        prideAndPrejudice.ratingsCount = 2676768;
        prideAndPrejudice.publicationYear = 1813;
        prideAndPrejudice.averageRating = 4.26;
        prideAndPrejudice.pagesNum = 279;
        prideAndPrejudice.description = "Jane Austen's radiant wit sparkles as her characters dance a delicate quadrille of flirtation and intrigue, making this book the most superb comedy of manners of Regency England.";
        austen.books.add(prideAndPrejudice);
        dickens.worksCount = 4460;
        dickens.followersCount = 21584;
        Book twoCities = new Book("A tale of two cities");
        twoCities.description = "After eighteen years as a political prisoner in the Bastille, the ageing Doctor Manette is finally released and reunited with his daughter in England. There the lives of two very different men, Charles Darnay, an exiled French aristocrat, and Sydney Carton, a disreputable but brilliant English lawyer, become enmeshed through their love for Lucie Manette";
        twoCities.averageRating = 3.83;
        twoCities.publicationYear = 2003;
        twoCities.pagesNum = 489;
        twoCities.ratingsCount = 761694;
        twoCities.goodReadsID = "1953";
        dickens.books.add(twoCities);
        Book hobbit = new Book("The Hobbit, or There and Back Again");
        hobbit.description = "Now recognized as a timeless classic, this introduction to the hobbit Bilbo Baggins, the wizard Gandalf, Gollum, and the spectacular world of Middle-earth recounts of the adventures of a reluctant hero, a powerful and dangerous ring, and the cruel dragon Smaug the Magnificent.";
        hobbit.goodReadsID = "5907";
        hobbit.ratingsCount = 2606534;
        hobbit.averageRating = 4.27;
        hobbit.pagesNum = 398;
        hobbit.publicationYear = 2003;
        tolkien.books.add(hobbit);
    }

    @Test
    void booksSimilarity() {
        AuthorSimCalculator calc1 = new AuthorSimCalculator(rowling, austen);
        AuthorSimCalculator calc2 = new AuthorSimCalculator(rowling, tolkien);
        double similarity1 = calc1.booksSimilarity();
        double similarity2 = calc2.booksSimilarity();
        System.out.println(similarity1);
        System.out.println(similarity2);
        assertTrue(similarity1 < similarity2);
    }

    @Test
    void weightedSimilarity() {
        AuthorSimCalculator calc1 = new AuthorSimCalculator(rowling, tolkien);
        AuthorSimCalculator calc2 = new AuthorSimCalculator(tolkien, dickens);
        AuthorSimCalculator calc3 = new AuthorSimCalculator(dickens, austen);
        double sim1 = calc1.weightedSimilarity();
        double sim2 = calc2.weightedSimilarity();
        double sim3 = calc3.weightedSimilarity();
        System.out.println(sim1);
        System.out.println(sim2);
        System.out.println(sim3);
        assertTrue(sim1 > sim2);
        assertTrue(sim3 > sim2);
    }
}