package tfg.example.org.materialdesign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iván on 19/04/2018.
 */

public class MyFavMovies extends AppCompatActivity {

    private static final String TAG = MyFavMovies.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    private static List<Pelicula> movies = null;
    private MoviesAdapter adapter = null;
    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "81c39ae2556fdc0f032d9a965252d8be";
    private int id_account;
    private String session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_movies);
        getIncomingIntent();

        connectAndGetFavMovies();
    }
    private void getIncomingIntent() {
        if (getIntent().hasExtra("id_account")) {
            id_account = getIntent().getIntExtra("id_account", 0);
        }
        if (getIntent().hasExtra("session_id")) {
            session_id = getIntent().getStringExtra("session_id");

        }
    }

    public void connectAndGetFavMovies(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);

        Call<MovieResponse> call = movieApiService.getFavMovies(id_account, API_KEY, session_id);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(MyFavMovies.this, 3));
                movies = response.body().getResults();

                updateDB(movies);

                adapter = new MoviesAdapter(movies, R.layout.list_item_movie, MyFavMovies.this, id_account, session_id);

                recyclerView.setAdapter(adapter);
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "ERROR EN API", Toast.LENGTH_SHORT).show();
                Log.e(TAG, throwable.toString());
            }
        });
    }

    public void updateDB(List<Pelicula> movies){
        DBHandler db = new DBHandler(this);

        db.onUpgrade(db.getWritableDatabase(), 1, 2);

        Log.e("Insert: ", "Inserting ..");
        for(int i=0; i<movies.size(); i++){
            db.addMovie(new FavMoviesDB(i, movies.get(i).getId(), movies.get(i).getTitle()));
        }

        // Reading all shops
        Log.e("Reading: ", "Reading all shops..");
        List<FavMoviesDB> films = db.getAllFavMovies();

        for(FavMoviesDB f: films){
            String log = "Id: " + f.getId() + " ,Movie id: " + f.getMovie_id() + " ,Name: " + f.getMovie_name();
// Writing shops to log
            Log.e("Movie: : ", log);
        }
    }
}
