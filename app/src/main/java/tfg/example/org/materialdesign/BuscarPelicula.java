package tfg.example.org.materialdesign;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Iván on 24/01/2018.
 */

public class BuscarPelicula extends AppCompatActivity {

    EditText text;
    private String session_id;
    private int id_account;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_pelicula);
        getIncomingIntent();
        text = (EditText)findViewById(R.id.query);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabbuscar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dato1 = text.getText().toString();
                Intent init = new Intent(BuscarPelicula.this , ListaBusqueda.class);
                init.putExtra("id_account", id_account);
                init.putExtra("session_id", session_id);
                init.putExtra("query", dato1);
                //init.putExtra("contraseña", dato2);
                setResult(RESULT_OK, init);
                startActivity(init);
            }
        });
    }


    public void getIncomingIntent(){
        if (getIntent().hasExtra("id_account")) {
            id_account = getIntent().getIntExtra("id_account", 0);
        }
        if (getIntent().hasExtra("session_id")) {
            session_id = getIntent().getStringExtra("session_id");

        }
    }


}
