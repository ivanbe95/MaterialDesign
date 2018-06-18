package tfg.example.org.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static tfg.example.org.materialdesign.R.layout.pelicula_ind;
import static tfg.example.org.materialdesign.R.layout.vista_pelicula;

/**
 * Created by Iván on 26/01/2018.
 */

public class PeliculaIndividual extends AppCompatActivity {

    private static final String TAG = PeliculaIndividual.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "81c39ae2556fdc0f032d9a965252d8be";
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";
    private MoviesAdapterInfo layout = null;
    private static MovieDetails movie = null;
    private int id_account;
    private String session_id;
    int id;
    private CreditsResponse credits;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIncomingIntent();

        setContentView(R.layout.pelicula_ind);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        connectandGetMovieDetails(id);

    }

    public void connectandGetMovieDetails(int movie_id) {
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);

        Call<CreditsResponse> call = movieApiService.getCredits(movie_id, API_KEY);

        call.enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                credits = response.body();
                connectAndGetApiData();
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR EN API", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

    }

    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);
        Call<MovieDetails> call = movieApiService.getMovie(id, API_KEY);

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                movie = response.body();
                checkDB();
                layout = new MoviesAdapterInfo(movie, vista_pelicula, getApplicationContext(), id_account, session_id, credits);
                recyclerView.setAdapter(layout);
                Log.d(TAG, "Bien?");
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "ERROR EN API", Toast.LENGTH_SHORT);
                Log.e(TAG, throwable.toString());
            }
        });
    }

    private void getIncomingIntent(){
       if(getIntent().hasExtra("id")){
           id = getIntent().getIntExtra("id", 75);
       }
       if(getIntent().hasExtra("id_account")){
           id_account = getIntent().getIntExtra("id_account", 0);
       }
       if(getIntent().hasExtra("session_id")){
           session_id = getIntent().getStringExtra("session_id");
       }
    }

    public void checkDB(){
        DBHandler db = new DBHandler(this);

        Log.e("Reading: ", "Reading all shops..");
        List<FavMoviesDB> films = db.getAllFavMovies();

        for(FavMoviesDB f: films){
            String log = "Id: " + f.getId() + " ,Movie id: " + f.getMovie_id() + " ,Name: " + f.getMovie_name();
// Writing shops to log
            Log.e("Movie: : ", log);
        }
    }
}
