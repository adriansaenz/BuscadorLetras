package com.example.adrian.buscadorletras;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textoLetra;
    EditText buscar;
    ProgressDialog mProgressDialog;
    String busqueda = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscar = (EditText) findViewById(R.id.textoBuscar);
        textoLetra = (TextView) findViewById(R.id.textoLetra);
        mProgressDialog = new ProgressDialog(MainActivity.this);

    }

    public void ejecutar(View v){
        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        busqueda = buscar.getText().toString();
        busqueda = busqueda.replace(" "," + ");
        new Title().execute();
    }

    @Override
    protected void onDestroy() {
        mProgressDialog.dismiss();
        super.onDestroy();
    }

    public String modificaLetra(String l) {
        int a = l.indexOf("\">");
        int b = l.indexOf("</");
        String aux = l.substring((a + 2), b);
        aux = aux.replace("<br>", "\n");
        return aux;
    }


    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String url = "";
        String url1 = "https://www.google.es/search?q=";
        String letra = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = url1 + busqueda;
                letra = new Buscador(url, "www.musica.com", "links").buscarLinks();
            } catch (Exception e) {
                Log.i("ERROR ERROR",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            letra = modificaLetra(letra);
            textoLetra.setText(letra);
            mProgressDialog.dismiss();
        }
    }
}
