package mx.unam.fciencias.fragmentos;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
public class SettingsFragment extends MainMenuActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey){
        addPreferencesFromResource(R.xml.preferences);
    }

}
