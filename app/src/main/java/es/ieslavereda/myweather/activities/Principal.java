package es.ieslavereda.myweather.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

import es.ieslavereda.myweather.Parameters;
import es.ieslavereda.myweather.R;
import es.ieslavereda.myweather.base.ImageDownloader;
import es.ieslavereda.preferencias.GestionPreferencias;
import es.ieslavereda.preferencias.PreferenciasActivity;
import es.ieslavereda.preferencias.ThemeSetup;

public class Principal extends AppCompatActivity implements LocationListener{

    private Spinner spinner;
    private ImageView imagenCiudad;
    private Button prevision;
    private Ciudades ciudades;
    private String salida;
    private LocationManager managerloc;
    private String proveedor;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elegir_ciudad);

        spinner = findViewById(R.id.spinner);
        imagenCiudad = findViewById(R.id.imagePlace);
        prevision = findViewById(R.id.prevision);

        managerloc = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setCostAllowed(false);
        criteria.setAltitudeRequired(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        proveedor = managerloc.getBestProvider(criteria, true);
        @SuppressLint("MissingPermission")
        Location location = managerloc.getLastKnownLocation(proveedor);

        Parameters parameters = new Parameters();

        parameters.ciudadesList.add(new Ciudades("Ubicacion" , "https://cedominombre.com/wp-content/uploads/Google-Photos-Como-eliminar-o-editar-su-ubicacion.png",getLat(location), getLon(location) ));

        ThemeSetup.applyPreferenceTheme(getApplicationContext());

        GestionPreferencias.getInstance().getCheckBoxPreference(getApplicationContext());
        GestionPreferencias.getInstance().getCheckBoxPreference2(getApplicationContext());
        GestionPreferencias.getInstance().getUnidades(getApplicationContext());
        GestionPreferencias.getInstance().getTheme(getApplicationContext());

        ArrayAdapter<Ciudades> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Parameters.ciudadesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ciudades = adapter.getItem(i);
                ImageDownloader.downloadImage(ciudades.getImagen(), imagenCiudad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(adapter);

        prevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Prevision.class);
                intent.putExtra("place" , ciudades);
                startActivity(intent);
            }
        });
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

    public float getLat(Location location){
        return (float) location.getLatitude();
    }
    public float getLon(Location location){
        return (float) location.getLongitude();
    }

    private void mostrarLocalizacion(Location location) {
        salida ="";
        if(location!=null) {
            salida+="Lat: " + location.getLatitude() +
                    "Lon: " + location.getLongitude();
        }else
            salida+=("Lat y Lon: " + "desconocido");
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        managerloc.requestLocationUpdates(proveedor, 10*1000, 5, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mostrarLocalizacion(location);
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
        for(Location location : locations)
            mostrarLocalizacion(location);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}