package tfg.example.org.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static tfg.example.org.materialdesign.R.layout.list_item_movie;

/**
 * Created by Iván on 24/01/2018.
 */

public class MoviesAdapter
        extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>
{
    public static int ip;
    private List<Pelicula> movies;
    private int rowLayout;
    private int id_account;
    private String session_id;
    private Context context;

    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";

    public MoviesAdapter(List<Pelicula> movies, int rowLayout, Context context, int id_account, String session_id) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.id_account=id_account;
        this.session_id = session_id;
    }

    //Una vista holder dentro de la clase donde conseguimos
    // la referencia a las vistas en el layout usando su id

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout moviesLayout;
        TextView movieTitle;
        ImageView movieImage;
        CardView cardview;

        public MovieViewHolder(View v) {
            super(v);
            //moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieImage = (ImageView) v.findViewById(R.id.movie_poster);
            movieTitle = (TextView) v.findViewById(R.id.movie_title);
            cardview = (CardView) v.findViewById(R.id.cardview1);
        }
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(list_item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        if (movies != null) {
            String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
            Picasso.with(context)
                    .load(image_url)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.movieImage);
            holder.movieTitle.setText(movies.get(position).getTitle());
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent q = new Intent(context, PeliculaIndividual.class);
                    q.putExtra("id", movies.get(position).getId());
                    q.putExtra("id_account", id_account);
                    q.putExtra("session_id", session_id);
                    context.startActivity(q);
                }
            });
        } else {
            holder.movieTitle.setText("Interstellar");
            Picasso.with(context)
                    .load(R.drawable.interstellar)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.movieImage);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static int getId(){
        return ip;
    }



}
