package tfg.example.org.materialdesign;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iván on 24/04/2018.
 */

public class CreditsResponse {

    public int id;
    public Cast[] cast;
    public Crew[] crew;

    public CreditsResponse(int id, Cast[] cast, Crew[] crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cast[] getCast() {
        return cast;
    }

    public void setCast(Cast[] cast) {
        this.cast = cast;
    }

    public Crew[] getCrew() {
        return crew;
    }

    public void setCrew(Crew[] crew) {
        this.crew = crew;
    }

    public String getActors(Cast[] cast){
        StringBuilder a = new StringBuilder();
        for(int i = 0; i<cast.length; i++){
            a.append(cast[i].name);
            if(i==cast.length-1){
                return a.append(".").toString();
            }
            a.append(", ");
        }
        return "No hay actores conocidos";
    }

    public String getDirector(Crew[] crew){
        String director = null;

        for(int i = 0; i<crew.length; i++){
            if(crew[i].job.equals("Director")){
                director = crew[i].getName();
            }
        }
        return director;
    }

    class Cast{
        @SerializedName("cast_id")
        public int cast_id;
        @SerializedName("character")
        public String character;
        @SerializedName("credit_id")
        public String credit_id;
        @SerializedName("gender")
        public int gender;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("order")
        public int order;
        @SerializedName("profile_path")
        public String profile_path;

        public Cast(int cast_id, String character, String credit_id, int gender, int id, String name, int order, String profile_path) {
            this.cast_id = cast_id;
            this.character = character;
            this.credit_id = credit_id;
            this.gender = gender;
            this.id = id;
            this.name = name;
            this.order = order;
            this.profile_path = profile_path;
        }

        public int getCast_id() {
            return cast_id;
        }

        public void setCast_id(int cast_id) {
            this.cast_id = cast_id;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }

    class Crew{

        @SerializedName("credit_id")
        public String credit_id;
        @SerializedName("department")
        public String department;
        @SerializedName("gender")
        public int gender;
        @SerializedName("id")
        public int id;
        @SerializedName("job")
        public String job;
        @SerializedName("name")
        public String name;
        @SerializedName("profile_path")
        public String profile_path;

        public Crew(String credit_id, String department, int gender, int id, String job, String name, String profile_path) {
            this.credit_id = credit_id;
            this.department = department;
            this.gender = gender;
            this.id = id;
            this.job = job;
            this.name = name;
            this.profile_path = profile_path;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }
}
