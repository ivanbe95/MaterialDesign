package tfg.example.org.materialdesign;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Iván on 18/04/2018.
 */

public class Account implements Serializable {

    @SerializedName("avatar")
    public Avatar avatar;
    @SerializedName("id")
    public int id;
    @SerializedName("iso_639_1")
    public String iso_639_1;
    @SerializedName("iso_3166_1")
    public String iso_3166_1;
    @SerializedName("name")
    public String name;
    @SerializedName("include_adult")
    public boolean include_adult;
    @SerializedName("username")
    public String username;

    public Account(Avatar avatar, int id, String iso_639_1, String iso_3166_1, String name, boolean include_adult, String username) {
        this.avatar = avatar;
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
        this.include_adult = include_adult;
        this.username = username;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInclude_adult() {
        return include_adult;
    }

    public void setInclude_adult(boolean include_adult) {
        this.include_adult = include_adult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    class Avatar{

        @SerializedName("gravatar")
        public Gravatar gravatar;

        public Avatar(Gravatar gravatar) {
            this.gravatar = gravatar;
        }

        public Gravatar getGravatar() {
            return gravatar;
        }

        public void setGravatar(Gravatar gravatar) {
            this.gravatar = gravatar;
        }

        class Gravatar{

            @SerializedName("hash")
            public String hash;
            public Gravatar(String hash) {
                this.hash = hash;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }
        }
    }
}
