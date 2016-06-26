package stephen.moviego;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Stephen on 6/22/2016.
 */
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
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }*/

