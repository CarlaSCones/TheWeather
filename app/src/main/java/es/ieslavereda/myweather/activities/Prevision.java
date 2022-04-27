package es.ieslavereda.myweather.activities;

import static es.ieslavereda.myweather.Parameters.API;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ieslavereda.myweather.R;
import es.ieslavereda.myweather.api.Connector;
import es.ieslavereda.myweather.base.BaseActivity;
import es.ieslavereda.myweather.base.CallInterface;
import es.ieslavereda.preferencias.GestionPreferencias;
import es.ieslavereda.preferencias.PreferenciasActivity;
import es.ieslavereda.preferencias.ThemeSetup;


public class Prevision extends BaseActivity implements CallInterface, View.OnClickListener {

    private static final String TAG = Prevision.class.getName();

    private Root root;
    private RecyclerView recyclerView;
    private List<es.ieslavereda.myweather.activities.List> datos;
    private Ciudades ciudades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);

        if( getIntent().getExtras() != null) {

            ciudades = (Ciudades) getIntent().getExtras().getSerializable("place");

            datos = new ArrayList<>();
            recyclerView = findViewById(R.id.recycler);
            TextView ciudad = findViewById(R.id.ciudad);

            ciudad.setText(ciudades.getNombre());

            recyclerView.setOnClickListener(this);
            MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, datos);
            myRecyclerViewAdapter.setOnClickListener(this);
            recyclerView.setAdapter(myRecyclerViewAdapter);

            LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(myLinearLayoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);



        } else {
            Toast.makeText(this,"No se ha seleccionado ninguna ciudad",Toast.LENGTH_SHORT).show();
        }

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

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        executeCall(this);
    }

    @Override
    public void doInBackground() {
        String url = "forecast?lang=es&units=metric&lat="+ ciudades.getLat() + "&lon="+ ciudades.getLon() +"&appid="+ API;
        Log.d(TAG, url);
        root = Connector.getConector().get(Root.class,url);
    }

    @Override
    public void doInUI() {
        hideProgress();
        datos.addAll(root.list);
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(this, DetallesDia.class);
        intent.putExtra("root",root);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}