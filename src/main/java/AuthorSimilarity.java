public class AuthorSimilarity {

    Author targetAuthor;

    public double worksCountSim(Author comparedAuthor) {
        return ((double) targetAuthor.worksCount) / (comparedAuthor.worksCount);
    }

    public double followerCountSim(Author comparedAuthor) {
        return ((double) targetAuthor.worksCount) / (comparedAuthor.worksCount);
    }

    public double aboutSim(Author comparedAuthor) {
        // TODO: update below logic
        return 0.0;
    }

}
