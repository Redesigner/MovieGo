package stephen.moviego;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity
    implements Preference.OnPreferenceChangeListener{
        @Override
    public void onCreate(Bundle saveBundle){
        super.onCreate(saveBundle);
        setContentView(R.layout.activity_settings);
        addPreferencesFromResource(R.xml.settings_menu);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        setSummary(preference);
        return false;
    }

    private void setSummary(Preference p){
        if(p instanceof ListPreference) {
            ListPreference listPref = (ListPreference)p;
            p.setSummary(listPref.getEntry());
        }
        if(p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference)p;
            p.setSummary(editTextPref.getText());
        }
    }
}

