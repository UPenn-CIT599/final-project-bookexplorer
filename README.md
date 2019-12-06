# Book Explorer

Book Explorer is a java application that aims to recommend authors and books to users based on their most recent reading history. It leverages GoodReads API to retrieve details of authors and their books, and runs its own recommendation algorithm. 

* There are 2 ways to run the program:
    * Command Line:
        * Run the #main method in UserInteraction class, and start interacting with the app!
    * GUI:
        * If you would like a more visual interaction, run the #main method in SearchInterface, and start interacting with the app!
        
* Limitations:
    * Due to time constraint, this app has several limitations and potential opportunities for improvement:
        * The weight of each similarity evaluation is arbitrary, and machine learning (establishing a user profile, adjusting recommendations and weights based on recommendation feedback) was planned but not able to be implemented
        * The app is designed to call on several external APIs. A database should be subsequently configured to save books and authors and their information. Because this was not implemented, the recommendation process is currently very slow. 

1. **Architecture Components**:
    1. Classes that represent objects:
        * Author
        * Book
    2. Classes responsible for key process logic:
        * RecMaker
            * Runs the process of making author and book recommendations
        * RequestHandler
            * Making requests and parsing responses from external APIs (GoodReads, Merriam Webster)
        * UserInteraction
            * Runs the user interaction process
        * AuthorSimCalculator
            * Calculates similarities between 2 authors
        * BookSimCalculator
            * Calculates similarities between 2 books
        * SearchInterface
            * Runs the Swing GUI Interface
        
2. **External Libraries Dependencies** 
    * please add jar file or use a dependency management tool for below libraries:
        * okhttpclient
            * kotlin-standardlib
            * okio