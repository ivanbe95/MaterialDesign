package tfg.example.org.materialdesign;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public String session_id;
    public String request_token;
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "81c39ae2556fdc0f032d9a965252d8be";
    public Account myAccount;
    public boolean guest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Barra de acciones
        getIncomingIntent();
        if(!guest) {
            connectAndGetAccoundDetails();
        }else {
            Toast.makeText(MainActivity.this, "Bienvenido, usuario invitado", Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void message(View view){

            if (myAccount == null) {
                if(guest) {
                    Snackbar.make(view, "Usted es un invitado. Para disfrutar de todas las funcionalidades, regístrese en TMDB.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Usted aún no se ha identificado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } else {
                Snackbar.make(view, "Bienvenido, " + myAccount.getUsername(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

    }

    public void lanzarBusqueda(View view){
        Intent p = new Intent(this, BuscarPelicula.class);
        p.putExtra("id_account", myAccount.getId());
        p.putExtra("session_id", session_id);
        startActivity(p);
        //Log.e(TAG, throwable.toString());
    }

    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);

    }

    public void lanzarMapa(View view){
        Intent i = new Intent(this, TheatersActivity.class);
        startActivity(i);
    }

    public void lanzarVistaPelicula(View view){
        Intent i = new Intent(this, VistaPeliculaActivity.class);
        //i.putExtra("id", (long)0);
        i.putExtra("id_account", myAccount.getId());
        i.putExtra("session_id", session_id);
        startActivity(i);
    }

    public void lanzarMyFavMovies(View view){
        Intent w = new Intent(this, MyFavMovies.class);
        w.putExtra("session_id", session_id);
        w.putExtra("id_account", myAccount.getId());
        startActivity(w);
    }

    public void salir(View view){
        finish();
    }

    public void entrar(View view){
        Intent e = new Intent(this, Logon.class);
        startActivityForResult(e, 1);
    }
    private void getIncomingIntent() {

        if(getIntent().hasExtra("session_id")){
            session_id = getIntent().getStringExtra("session_id");
        }
        if(getIntent().hasExtra("guest")){
            guest = getIntent().getBooleanExtra("guest", true);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }
        if (id == R.id.buscar)  {
            lanzarBusqueda(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connectAndGetAccoundDetails(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);

        Call<Account> call = movieApiService.getDetails(API_KEY, session_id);

        call.enqueue(new Callback<Account>(){
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                myAccount = response.body();
                Toast.makeText(MainActivity.this, "Bienvenido, " + myAccount.getUsername(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Session_id caught caught");

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Usted no se ha autentificado", Toast.LENGTH_LONG);
                Log.e(TAG, t.toString());
            }

        });
    }

}
