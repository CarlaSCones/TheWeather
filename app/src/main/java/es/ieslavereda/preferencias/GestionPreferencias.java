package es.ieslavereda.preferencias;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import es.ieslavereda.myweather.R;


public class GestionPreferencias {

    private SharedPreferences pref;
    private static GestionPreferencias gestionPreferencias;

    public GestionPreferencias(){

    }

    public static GestionPreferencias getInstance(){
        if(gestionPreferencias==null)
            gestionPreferencias = new GestionPreferencias();
        return gestionPreferencias;
    }

    private void inicializa(Context context) {
        if (pref == null)
            pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUnidades(Context context){
        inicializa(context);
        return pref.getString("unidades","standard");
    }

    public boolean getCheckBoxPreference(Context context){
        inicializa(context);
        return pref.getBoolean("checkBoxPreferenceKey",false);
    }

    public boolean getCheckBoxPreference2(Context context){
        inicializa(context);
        return pref.getBoolean("checkBoxPreferenceKey2",false);
    }

    public String getTheme(Context context){
        inicializa(context);
        return pref.getString(context.getString(R.string.settings_theme_key),ThemeSetup.Mode.DEFAULT.name());
    }


}