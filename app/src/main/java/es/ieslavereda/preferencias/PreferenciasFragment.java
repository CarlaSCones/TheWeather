package es.ieslavereda.preferencias;


import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Arrays;
import java.util.List;

import es.ieslavereda.myweather.R;

public class PreferenciasFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.preferencias, rootKey);

        final ListPreference unidades = findPreference("unidades");
        final List<String> unidades_entries = Arrays.asList(getResources().getStringArray(R.array.unidades_entries));
        final List<String> unidades_values = Arrays.asList(getResources().getStringArray(R.array.unidades_values));

        int pos  = unidades_values.indexOf(GestionPreferencias.getInstance().getUnidades(getContext()));

        unidades.setSummary("Unidades en " + unidades_entries.get(pos));
        unidades.setOnPreferenceChangeListener((preference, newValue) -> {

            int pos1 = unidades_values.indexOf(newValue);
            unidades.setSummary("Unidades en " + unidades_entries.get(pos1));

            return true;
        });

        ListPreference themePreference = getPreferenceManager().findPreference(getString(R.string.settings_theme_key));
        if (themePreference.getValue() == null) {
            themePreference.setValue(ThemeSetup.Mode.DEFAULT.name());
        }
        themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            ThemeSetup.applyTheme(ThemeSetup.Mode.valueOf((String) newValue));
            return true;
        });
    }
}
