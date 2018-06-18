package tfg.example.org.materialdesign;

/**
 * Created by Iván on 28/05/2018.
 */

public class RecommenderInput {

    public int userId;
    public int movieId;
    public int ratingId;

    public RecommenderInput(int userId, int movieId, int ratingId) {
        this.userId = userId;
        this.movieId = movieId;
        this.ratingId = ratingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }
}
