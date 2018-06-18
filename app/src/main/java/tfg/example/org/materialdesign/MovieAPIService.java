package tfg.example.org.materialdesign;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Iván on 24/01/2018.
 */

public interface MovieAPIService {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
    @GET("search/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("query") String query);
    @GET("movie/{id}")
    Call<MovieDetails> getMovie(@Path("id") int id ,@Query("api_key") String apiKey);
    @GET("authentication/token/new")
    Call<RequestToken> getNewToken(@Query("api_key") String api_key);
    @GET("authentication/session/new")
    Call<RequestSessionID> getSessionID(@Query("api_key") String api_key, @Query("request_token") String request_token);
    @GET("account")
    Call<Account> getDetails(@Query("api_key") String api_key, @Query("session_id") String session_id);
    @POST("account/{account_id}/favorite")
    Call<SuccessResponse> postFavMovie(@Path("account_id") int account_id, @Query("api_key") String api_key,
                                    @Query("session_id") String session_id, @Header("content-type") String content_type,
                                    @Body FavMoviePost movie);
    @GET("account/{account_id}/favorite/movies")
    Call<MovieResponse> getFavMovies(@Path("account_id") int account_id, @Query("api_key") String api_key,
                                    @Query("session_id") String session_id);
    @GET("movie/{movie_id}/credits")
    Call<CreditsResponse> getCredits(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("authentication/guest_session/new")
    Call<GuestSession> getGuestSessionID(@Query("api_key") String api_key);
}
