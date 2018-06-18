package tfg.example.org.materialdesign;

import android.app.Activity;
import android.content.Intent;
import retrofit2.Callback;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iván on 27/11/2017.
 */

public class Logon extends AppCompatActivity{

    EditText user;
    EditText password;
    public String session_id;
    private static final String TAG = Logon.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "81c39ae2556fdc0f032d9a965252d8be";
    RequestToken reqtok = null;
    SuccessResponse error = null;
    public boolean guest = false;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrar);
        requestToken();
        //Boton flotante
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        if (id == R.id. acercaDe) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void requestToken(){

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);
        //URLEncoder.encode(query, "UTF-8");
        Call<RequestToken> call = movieApiService.getNewToken(API_KEY);

        call.enqueue(new Callback<RequestToken>(){
            @Override
            public void onResponse(Call<RequestToken> call, Response<RequestToken> response) {
                if(response.code()!=200){
                    Toast.makeText(Logon.this, response.message(), Toast.LENGTH_LONG).show();
                } else {
                    reqtok = response.body();
                    Toast.makeText(Logon.this, "Request_token conseguida", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Request token caught");
                }
            }

            @Override
            public void onFailure(Call<RequestToken> call, Throwable t) {
                Toast.makeText(Logon.this, "Revisa tu conexión", Toast.LENGTH_LONG).show();
                Log.e(TAG, t.toString());
            }

        });
    }

    public void lanzar(View v){
        Intent viewIntent = new Intent(Logon.this, WebActivity.class);
        viewIntent.putExtra("token", reqtok.getRequest_token());
        startActivity(viewIntent);
    }

    public synchronized void volver(View v) {
        Intent init = new Intent(Logon.this, MainActivity.class);
        //init.putExtra("myAccount", myAccount);
        init.putExtra("session_id", session_id);
        init.putExtra("guest", guest);
        startActivity(init);
    }

    public void autentificar(View v){
        connectAndGetSessionID();
    }

    public void autentificarGuest(View v){
        connectAndGetGuestSessionID();
    }


    public void connectAndGetSessionID(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);
        //URLEncoder.encode(query, "UTF-8");
        Call<RequestSessionID> call = movieApiService.getSessionID(API_KEY, reqtok.request_token);

        call.enqueue(new Callback<RequestSessionID>(){
            @Override
            public void onResponse(Call<RequestSessionID> call, Response<RequestSessionID> response) {
                if(response.code()==200) {
                    session_id = response.body().session_id;
                    Log.e(TAG, "Session_id caught");
                    Toast.makeText(Logon.this, "Session_id conseguida", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Logon.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestSessionID> call, Throwable t) {
                Toast.makeText(Logon.this, "Usted no se ha autentificado", Toast.LENGTH_LONG);
                Log.e(TAG, t.toString());
            }

        });
    }

    public void connectAndGetGuestSessionID(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);
        //URLEncoder.encode(query, "UTF-8");
        Call<GuestSession> call = movieApiService.getGuestSessionID(API_KEY);

        call.enqueue(new Callback<GuestSession>(){
            @Override
            public void onResponse(Call<GuestSession> call, Response<GuestSession> response) {
                session_id = response.body().guest_session_id;
                guest = true;
                Log.e(TAG, "Session_id caught");
                Toast.makeText(Logon.this, "Guest_Session_id conseguida", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GuestSession> call, Throwable t) {
                Toast.makeText(Logon.this, "Usted no se ha autentificado", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }

        });


    }
}
