import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorSimCalculatorTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @Test
    void booksSimilarity() {
    }

    @Test
    void weightedSimilarity() {
        Author rowling = new Author("J.K. Rowling");
        Book stone = new Book("Harry Potter and the Sorcerer's Stone");
        Author tolkien = new Author("J.R.R. Tolkien");
        Author dickens = new Author("Charles Dickens");
        Author austen = new Author("Jane Austen");
        Book prideAndPrejudice = new Book("Pride and Prejudice");
        rowling.worksCount = 246;
        rowling.followersCount = 204544;
        stone.goodReadsID = "3";
        stone.description = "Harry Potter's life is miserable. His parents are dead and he's stuck with his heartless relatives, who force him to live in a tiny closet under the stairs. But his fortune changes when he receives a letter that tells him the truth about himself: he's a wizard. A mysterious visitor rescues him from his relatives and takes him to his new home, Hogwarts School of Witchcraft and Wizardry.After a lifetime of bottling up his magical powers, Harry finally feels like a normal kid. But even within the Wizarding community, he is special. He is the boy who lived: the only person to have ever survived a killing curse inflicted by the evil Lord Voldemort, who launched a brutal takeover of the Wizarding world, only to vanish after failing to kill Harry.Though Harry's first year at Hogwarts is the best of his life, not everything is perfect. There is a dangerous secret object hidden within the castle walls, and Harry believes it's his responsibility to prevent it from falling into evil hands. But doing so will bring him into contact with forces more terrifying than he ever could have imagined. Full of sympathetic characters, wildly imaginative situations, and countless exciting details, the first installment in the series assembles an unforgettable magical world and sets the stage for many high-stakes adventures to come.";
        rowling.books.add(stone);
        prideAndPrejudice.goodReadsID = "1885";
        prideAndPrejudice.ratingsCount = 2676768;
        prideAndPrejudice.publicationYear = 1813;
        prideAndPrejudice.averageRating = 4.26;
        prideAndPrejudice.pagesNum = 279;
        austen.books.add(prideAndPrejudice);
        AuthorSimCalculator calc1 = new AuthorSimCalculator(rowling, tolkien);
        AuthorSimCalculator calc2 = new AuthorSimCalculator(tolkien, dickens);
        AuthorSimCalculator calc3 = new AuthorSimCalculator(dickens, austen);
        assertTrue(calc1.weightedSimilarity() > calc2.weightedSimilarity());
        assertTrue(calc3.weightedSimilarity() > calc2.weightedSimilarity());
    }
}