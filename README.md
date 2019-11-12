# Book Explorer

Book Explorer is an application that aims to recommend new books and authors based on the genre and most recent author and book that an user read. We leverage GoodReads API to retrieve an author's detail and books, and maintains a database of author names of our own research to find similar authors of the same genre. 

1. Class Components:
    1. Classes that represent objects:
        * Author
        * Book
        * Genre
        * Prediction
        * User
        * Publisher
    2. Classes responsible for key process logic:
        * PredictionMaker
        * PredictionLearner
        * RequestHandler
        * UserInteraction
        * WebScraper (TBD)
        
2. External Libraries Dependencies:
    * okhttpclient
        * kotlin-standardlib
        * okio
