import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookTest {

	@Test
	// JUnit testing of obtaining the commonPercentage and leftRightSimilarity metrics between
	// "The Things They Carried" by Tim O'Brien and "The Way We Live Now" by Anthony Trollope.
	void testLeftRightSimilarityMetric1() {
		Book bookTest = new Book();
		
		String book1 = "The Things They Carried";
		String book2 = "The Way We Live Now";
		double averageSimilarity = bookTest.leftRightSimilarityMetric(book1, book2);
		double commonPercentage = bookTest.getCommonPercentage(book1, book2);
		assertEquals(averageSimilarity, 2);
		assertEquals(commonPercentage, 0.4375);
	}
	
	@Test
	// JUnit testing of obtaining the commonPercentage and leftRightSimilarity metrics between
	// "Mad River" by John Sandford and "The River" by Gary Paulsen.
	void testLeftRightSimilarityMetric2() {
		Book bookTest = new Book();
		
		String book1 = "Mad River";
		String book2 = "The River";
		double averageSimilarity = bookTest.leftRightSimilarityMetric(book1, book2);
		double commonPercentage = bookTest.getCommonPercentage(book1, book2);
		assertEquals(averageSimilarity, 5);
		assertEquals(commonPercentage, 0.4444444444444444);
	}
	
	@Test
	// JUnit testing of obtaining the commonPercentage and leftRightSimilarity metrics between
	// the words "Python" and "Java".
	void testLeftRightSimilarityMetric3() {
		Book bookTest = new Book();
		
		String book1 = "Java";
		String book2 = "Python";
		double averageSimilarity = bookTest.leftRightSimilarityMetric(book1, book2);
		double commonPercentage = bookTest.getCommonPercentage(book1, book2);
		assertEquals(averageSimilarity, 0);
		assertEquals(commonPercentage, 0.0);
	}

}
