package mx.unam.fciencias.fragmentos;

import static mx.unam.fciencias.fragmentos.MainMenuActivity.RESULT_EXIT;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    public  static final String ENTRY_MESSAGE_KEY= "mx.unam.fciencias.fragmentos.INDEX";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent startIntent = getIntent();
        ActionBar actionBar = getActionBar();
        String detailFragmentTitle = null;
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(startIntent.getStringExtra(ENTRY_MESSAGE_KEY));
        } else{
            detailFragmentTitle = startIntent.getStringExtra(ENTRY_MESSAGE_KEY);
        }
        Bundle detailFragmentArgs = new Bundle();
        detailFragmentArgs.putInt(DetailsFragment.INDEX_KEY,
                startIntent.getIntExtra(DetailsFragment.INDEX_KEY,-1));
        detailFragmentArgs.putInt(DetailsFragment.MASTER_LIST_SIZE_KEY,
                startIntent.getIntExtra(DetailsFragment.MASTER_LIST_SIZE_KEY,-1));
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(detailFragmentArgs);
        getSupportFragmentManager().beginTransaction().add(
                R.id.color_detail_holder,detailsFragment
        ).commit()
        ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int intId = item.getItemId();
        if (intId == R.id.menu_about){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Acerca de")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("Ejemplo de Material Design y Vistas en Android")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface,int i){
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
            return true;
        }
        if (intId == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}