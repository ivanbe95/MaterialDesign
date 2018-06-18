package tfg.example.org.materialdesign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iván on 19/04/2018.
 */

public class FavMoviePost {

    @SerializedName("media_type")
    @Expose
    public String media_type;
    @SerializedName("media_id")
    @Expose
    public int media_id;
    @SerializedName("favorite")
    @Expose
    public boolean favorite;

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "FavMoviePost{" +
                "media_type='" + media_type + '\'' +
                ", media_id=" + media_id +
                ", favorite=" + favorite +
                '}';
    }
}
