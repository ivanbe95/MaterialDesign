package tfg.example.org.materialdesign;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iván on 03/12/2017.
 */

public class VistaPeliculaActivity extends AppCompatActivity {
    private static final String TAG = VistaPeliculaActivity.class.getSimpleName();
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
        setContentView(R.layout.most_rated);
        getIncomingIntent();

        connectAndGetApiData();
    }

    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(VistaPeliculaActivity.this, 3));
                movies = response.body().getResults();

                adapter = new MoviesAdapter(movies, R.layout.list_item_movie, VistaPeliculaActivity.this, id_account, session_id);
                /*adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Selección: "+movies.get
                                        (recyclerView.getChildAdapterPosition(v)).getTitle()
                        , Toast.LENGTH_SHORT).show();
                        lanzarPeliculaIndividual(v);
                    }
                });*/
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

    public void lanzarPeliculaIndividual(View v){
        Intent q = new Intent(this, PeliculaIndividual.class);
        q.putExtra("id_account", id_account);
        q.putExtra("session_id", session_id);
        startActivity(q);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("id_account")) {
            id_account = getIntent().getIntExtra("id_account", 0);
        }
        if (getIntent().hasExtra("session_id")) {
            session_id = getIntent().getStringExtra("session_id");

        }
    }



}

