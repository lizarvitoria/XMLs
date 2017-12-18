package com.example.dm2.xmls;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnCargar;
    private TextView txtResultado;
    private List<Noticia> noticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCargar = (Button)findViewById(R.id.btnCargar);
        txtResultado = (TextView)findViewById(R.id.txtResultado);
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Carga del XML mediante la Tarea Asincrona
                CargarXmlTask tarea = new CargarXmlTask();
                tarea.execute("http://www.elcorreo.com/rss/2.0/?section=deportes/mas-deportes");
            }
        });
    }

    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {
        protected Boolean doInBackground(String... params) {
            RssParserDom saxparser = new RssParserDom(params[0]);
            noticias = saxparser.parse();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            txtResultado.setText("");
            for(int i=0; i<noticias.size(); i++)
            {
                txtResultado.setText(
                        txtResultado.getText().toString() +
                                System.getProperty("line.separator") +
                                noticias.get(i).getTitulo());
            }
        }
    }

}
