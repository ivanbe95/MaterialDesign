package tfg.example.org.materialdesign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iván on 03/12/2017.
 */

public class PeliculasVector {

    int page;
    int total_pages;
    int total_results;
    ArrayList<Pelicula> results;

    public PeliculasVector(int page, int total_pages, int total_results, ArrayList<Pelicula> results) {
        super();
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = results;
    }


    public ArrayList<String> MovietoString(ArrayList<Pelicula> results) {

        int resultados = results.size();
        int i = 0;
        ArrayList<String> titles = new ArrayList<String>();
        while(i < resultados-1){
            titles.add(results.get(i).getTitle());
            i++;
        }
        return titles;
    }

    @Override
    public String toString() {
        return "MovieArray \n page \t  " + page + "\n total_pages \t" + total_pages + "\n total_results \t" + total_results
                + "\n results \n" + results.toString();
    }



}
