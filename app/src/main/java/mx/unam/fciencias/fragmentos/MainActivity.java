package mx.unam.fciencias.fragmentos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

public class MainActivity extends MainMenuActivity {
    private  Button launchSecondActivityButton;
    private String sharedViewTransitionName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        launchSecondActivityButton = findViewById(R.id.launch_second_activity);
        launchSecondActivityButton.setOnClickListener(this::launchSecondActivity);
    }
    public void launchSecondActivity(View button){
        Intent intent = new Intent(this,SecondActivity.class);
        if (!sharedPreferences.getBoolean(getString(
                R.string.enable_transitions_preference_key),false)){
            startActivity(intent);
            return;
        }
        if(sharedViewTransitionName == null){
            sharedViewTransitionName = getString(R.string.second_activity_launcher_button);
        }
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,launchSecondActivityButton,sharedViewTransitionName
        );

        resultLauncher.launch(intent, options);
    }

}