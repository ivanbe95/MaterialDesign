package tfg.example.org.materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iván on 24/01/2018.
 */

public class ListaBusqueda extends AppCompatActivity {

    private String query;
    private String session_id;
    private int id_account;

    private static final String TAG = ListaBusqueda.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    List<Pelicula> movies;
    private MoviesAdapter adapter = null;
    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "81c39ae2556fdc0f032d9a965252d8be";

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (requestCode==1 && resultCode==RESULT_OK) {
            query= data.getExtras().getString("query");
        }

    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.most_rated);
        //Intent intentprev = getIntent();
        //query = intentprev.getStringExtra("query");
        getIncomingIntent();
        try {
            connectAndGetApiData();
        } catch (Exception e){
            Log.e(TAG, "Error del catch encoding");
            //e.printStackTrace();
        }
    }

    public void connectAndGetApiData() throws UnsupportedEncodingException{
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);
        //URLEncoder.encode(query, "UTF-8");
        Call<MovieResponse> call = movieApiService.getMovies(API_KEY, query);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                //recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(ListaBusqueda.this, 3));
                movies = response.body().getResults();
                adapter = new MoviesAdapter(movies, R.layout.list_item_movie, ListaBusqueda.this, id_account, session_id);
                recyclerView.setAdapter(adapter);
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("query")){
            query = getIntent().getStringExtra("query");
        }
        if (getIntent().hasExtra("id_account")) {
            id_account = getIntent().getIntExtra("id_account", 0);
        }
        if (getIntent().hasExtra("session_id")) {
            session_id = getIntent().getStringExtra("session_id");

        }
    }
}
