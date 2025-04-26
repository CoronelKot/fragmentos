package mx.unam.fciencias.fragmentos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NavUtils;
import androidx.preference.PreferenceManager;

public class MainMenuActivity extends AppCompatActivity {
    protected  String lightThemeId;
    protected  String themePreferenceKey;
    public static final byte RESULT_CHECK_STYLE = 2;
    protected  SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightThemeId= getString(R.string.light_theme_preference_id);
        themePreferenceKey = getString(R.string.theme_preference_key);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyTheme(sharedPreferences.getString(themePreferenceKey,lightThemeId),false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    protected final ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            int resultCode = result.getResultCode();
                            Intent data = result.getData();
                            if (resultCode == RESULT_CHECK_STYLE && data != null) {
                                try {
                                    int selectedTheme = getThemeResourceIdFromPreferenceId(
                                            data.getStringExtra(themePreferenceKey)
                                    );
                                    if (getPackageManager().getActivityInfo(
                                            getComponentName(),0
                                    ).getThemeResourse() == selectedTheme){
                                        return;
                                    }
                                    setTheme(selectedTheme);
                                    recreate();
                                }catch (PackageManager.NameNotFoundException e){
                                    Log.w(this.getClass().getSimpleName(),
                                            "Couldn't get style",e);
                                }
                            }
                        }
                    }
            );


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        int intId = item.getItemId();
        if (itemId == R.id.menu_about) {
            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.menu_about)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage(R.string.about)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                    .create().show();
            return true;
        }
        if (itemId == R.id.menu_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        if(intId == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        resultLauncher.launch(new Intent(this, SecondActivity.class));
        return super.onOptionsItemSelected(item);
    }
    @Override
    public  void startActivity(Intent intent){
        resultLauncher.launch(intent);
    }
    public  void startActivity(Intent intent, ActivityOptionsCompat options){
        resultLauncher.launch(intent,options);
    }

    @Override
    public void finish() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(themePreferenceKey,lightThemeId);
        setResult(RESULT_CHECK_STYLE,resultIntent);
        super.finish();
    }

    protected  void applyTheme(String themeKey, boolean recreate){
        setTheme(getThemeResourceIdFromPreferenceId(themeKey));
        if (recreate) recreate();
    }
    private  int getThemeResourceIdFromPreferenceId(String stylePreferenceId){
        if(lightThemeId.equals(stylePreferenceId)){
            return R.style.Theme_Fragmentos;
        }else{
            return R.style.DarkTheme;
        }
    }
}
