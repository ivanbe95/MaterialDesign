package tfg.example.org.materialdesign;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iván on 26/01/2018.
 */

public class MoviesAdapterInfo
        extends  RecyclerView.Adapter<MoviesAdapterInfo.MovieViewHolder> {

    private MovieDetails movie;
    private CreditsResponse credits;
    private int rowLayout;
    private Context context;
    private String session_id;
    private int id_account;
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "81c39ae2556fdc0f032d9a965252d8be";
    private static final String TAG = MoviesAdapterInfo.class.getSimpleName();

    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";

    public MoviesAdapterInfo(MovieDetails movie, int rowLayout, Context context, int id_account, String session_id, CreditsResponse credits) {
        this.movie = movie;
        this.rowLayout = rowLayout;
        this.context = context;
        this.id_account=id_account;
        this.session_id=session_id;
        this.credits = credits;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        //LinearLayout moviesLayout;
        TextView movieTitle;
        //LinearLayout moviesLayout2;
        TextView gender;
        TextView director;
        TextView date;
        RatingBar val;
        ImageView movieImage;
        TextView description;
        FloatingActionButton fav;

        public MovieViewHolder(View v) {
            super(v);
            //moviesLayout = (LinearLayout) v.findViewById(R.id.linearlayout1);
            gender = (TextView) v.findViewById(R.id.genero);
            movieImage = (ImageView) v.findViewById(R.id.foto);
            date = (TextView) v.findViewById(R.id.fecha);
            movieTitle = (TextView) v.findViewById(R.id.nombre);
            //moviesLayout2 = (LinearLayout) v.findViewById(R.id.linearlayout2);
            director = (TextView) v.findViewById(R.id.director);
            description = (TextView) v.findViewById(R.id.comentario);
            val = (RatingBar) v.findViewById(R.id.valoracion);
            fav = (FloatingActionButton)v.findViewById(R.id.favorite);

        }
    }

    @Override
    public MoviesAdapterInfo.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_pelicula, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapterInfo.MovieViewHolder holder, final int position) {
        if (movie != null) {
            String image_url = IMAGE_URL_BASE_PATH + movie.getPosterPath();
            Picasso.with(context)
                    .load(image_url)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.movieImage);

            holder.director.setText(credits.getDirector(credits.getCrew()));
            holder.movieTitle.setText(movie.getTitle());
            holder.date.setText(movie.getReleaseDate());
            holder.description.setText(movie.getOverview());
            holder.val.setRating(movie.getVoteAverage()/2);
            if(isMovieFav(movie.getId())){
                holder.fav.setImageResource(R.drawable.ic_favorite);
            } else {
                holder.fav.setImageResource(R.drawable.ic_no_favorite);
            }

            //holder.fav.setImageDrawable(R.drawable.);

            holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setMovieFavorite(movie.getId());
                    if(isMovieFav(movie.getId())){
                        holder.fav.setImageResource(R.drawable.ic_favorite);
                    } else {
                        holder.fav.setImageResource(R.drawable.ic_no_favorite);
                    }
                }
            });
            holder.gender.setText(movie.getGenres(movie.getGenreIds()));
        } else {
            holder.director.setText("Ivan Benitez");
            holder.movieTitle.setText("ERROR DE API");
            holder.date.setText("");
            holder.description.setText("ERROR DE API");
            holder.val.setRating(5);
            holder.gender.setText("ERROR DE API");
            holder.movieImage.setImageDrawable(null);

        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setMovieFavorite(int media_id){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        FavMoviePost favmovie = new FavMoviePost();
        favmovie.setMedia_id(media_id);
        favmovie.setMedia_type("movie");
        if(isMovieFav(media_id)){
            favmovie.setFavorite(false);
        } else {
            favmovie.setFavorite(true);
        }


        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);

        Call<SuccessResponse> call = movieApiService.postFavMovie(id_account, API_KEY, session_id, "application/json;charset=utf-8",
                favmovie);

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                SuccessResponse message = response.body();
                Toast.makeText(context, message.getStatus_message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Toast.makeText(context, "ERROR EN API", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

    }

    public boolean isMovieFav(int id){
        DBHandler db = new DBHandler(this.context);

        Log.e("Reading: ", "Reading if movie is in the fav list..");

        FavMoviesDB f = db.getMovie(id);
        if(f!=null){
            Log.e("FavMovieDB: ", "Movie is in the list");
            return true;
        } else {
            return false;
        }
    }
}
