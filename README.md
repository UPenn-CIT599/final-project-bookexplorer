# Book Explorer

Book Explorer is a java application that aims to recommend authors and books to users based on their most recent reading history. It leverages GoodReads API to retrieve details of authors and their books, and runs its own recommendation algorithm. 

1. **Architecture Components**:
    1. Classes that represent objects:
        * Author
        * Book
        * Genre
        * Prediction
        * User
        * Publisher
    2. Classes responsible for key process logic:
        * PredictionMaker
            * Runs the process of making author and book recommendations
        * PredictionLearner
            * A learner model to improve from existing predictions
        * RequestHandler
            * Making requests and parsing responses from GoodReads API
        * UserInteraction
            * Runs the user interaction process
        * WebScraper (TBD)
            * Web scraper to obtain more book and author details
        
2. **External Libraries Dependencies** 
    * please add jar file or use a dependency management tool for below libraries:
        * okhttpclient
            * kotlin-standardlib
            * okio
