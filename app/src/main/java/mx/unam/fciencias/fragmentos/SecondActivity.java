package mx.unam.fciencias.fragmentos;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondActivity extends MainMenuActivity implements InfiniteListAdapter.MasterListItemClickHandler {

    InfiniteListAdapter listAdapter;
    boolean isDetailsPaneAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_second);
        Button button = findViewById(R.id.add_to_list_button);
        button.setOnClickListener(this::addListElement);
        RecyclerView infiniteList = findViewById(R.id.infinite_list);
        infiniteList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        infiniteList.setLayoutManager(layoutManager);
        listAdapter = new InfiniteListAdapter(getResources(),this);
        addListElement(null);
        infiniteList.setAdapter(listAdapter);
        isDetailsPaneAvailable = findViewById(R.id.color_detail_holder)!= null;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isDetailsPaneAvailable){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        }
    }
    public void addListElement(View button) {
        listAdapter.addItem();
    }
    @Override
    public void onItemClicked(int clickItemIndex,String entryText,int masterListSize){
        if (isDetailsPaneAvailable) {
            Bundle detailFragmentArgs = new Bundle();
            detailFragmentArgs.putInt(DetailsFragment.INDEX_KEY, clickItemIndex);
            detailFragmentArgs.putInt(DetailsFragment.MASTER_LIST_SIZE_KEY, masterListSize);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(detailFragmentArgs);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.color_detail_holder, detailsFragment
            ).commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailsFragment.INDEX_KEY, clickItemIndex);
            intent.putExtra(DetailActivity.ENTRY_MESSAGE_KEY, entryText);
            intent.putExtra(DetailsFragment.MASTER_LIST_SIZE_KEY, masterListSize);
            startActivity(intent);
        }
    }
}
