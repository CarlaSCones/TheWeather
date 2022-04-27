package es.ieslavereda.myweather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.ieslavereda.myweather.Parameters;
import es.ieslavereda.myweather.R;
import es.ieslavereda.myweather.base.ImageDownloader;
import es.ieslavereda.preferencias.GestionPreferencias;
import es.ieslavereda.preferencias.PreferenciasActivity;
import es.ieslavereda.preferencias.ThemeSetup;

public class DetallesDia extends AppCompatActivity {

    Root root;
    int pos;
    private TextView dia;
    private TextView tHora;
    private TextView fecha;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_dia);

        Intent intent = getIntent();
        root = (Root) intent.getExtras().getSerializable("root");
        pos = intent.getExtras().getInt("position");

        imageView = findViewById(R.id.imagen);
        dia = findViewById(R.id.textDia);
        tHora = findViewById(R.id.texthora);
        fecha = findViewById(R.id.textFecha);

        ImageDownloader.downloadImage(Parameters.ICON_URL_PRE + root.list.get(pos).weather.get(0).icon + Parameters.ICON_URL_POST,imageView);
        Date date = new Date((long)root.list.get(pos).dt*1000);
        SimpleDateFormat dayFor = new SimpleDateFormat("EEEE",new Locale( "es" , "ES" ));
        SimpleDateFormat timeFor = new SimpleDateFormat("hh:mm");
        SimpleDateFormat dateFor = new SimpleDateFormat("dd/MM/yyyy");
        String stringDay= dayFor.format(date);
        String stringTime = timeFor.format(date);

        dia.setText(stringDay);
        tHora.setText(stringTime);
        fecha.setText(dateFor.format(date));

        ThemeSetup.applyPreferenceTheme(getApplicationContext());

        GestionPreferencias.getInstance().getCheckBoxPreference(getApplicationContext());
        GestionPreferencias.getInstance().getCheckBoxPreference2(getApplicationContext());
        GestionPreferencias.getInstance().getUnidades(getApplicationContext());
        GestionPreferencias.getInstance().getTheme(getApplicationContext());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.configuracion):
                Intent intentPreferenciasActivity = new Intent(this, PreferenciasActivity.class);
                startActivity(intentPreferenciasActivity);
                return true;
            case (R.id.exit):
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void close(View v){
        finish();
    }
}