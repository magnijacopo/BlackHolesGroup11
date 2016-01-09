package it.polimi.group11;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import it.polimi.group11.helper.Guest;
import it.polimi.group11.helper.GuestData;
import it.polimi.group11.helper.RecyclerListAdapter;
import it.polimi.group11.helper.SimpleItemTouchHelperCallback;

public class SelectPlayersActivity extends AppCompatActivity {

    ItemTouchHelper mItemTouchHelper;
    private static final String MAX_PLAYER = "You have already add the max number of players";
    private static final String ADD_PLAYER = "Player added";
    private static Context context;
    private FloatingActionButton fab;
    private RecyclerListAdapter adapter;
    private SimpleItemTouchHelperCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new RecyclerListAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        fab  = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer();
                if(RecyclerListAdapter.mItems.size() > 3){
                    CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                    p.setAnchorId(View.NO_ID);
                    fab.setLayoutParams(p);
                    fab.setVisibility(View.GONE);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_players, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_play){
            Intent intent = new Intent(this, PlayGameActivity.class);
            intent.putExtra("PLAYER_NUMBER", adapter.getPlayerNumber());
            startActivity(intent);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void goToChoosePlayerType() {
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }

    private void addPlayer(){
        if(RecyclerListAdapter.mItems.size() < 4) {
            RecyclerListAdapter.mItems.add(new Guest(
                    GuestData.getNameArray(RecyclerListAdapter.mItems.size()),
                    GuestData.getStockImage(),
                    GuestData.getId(RecyclerListAdapter.mItems.size())
            ));
            callback.setPlayersNumberCallback(RecyclerListAdapter.mItems.size());
            adapter.notifyDataSetChanged();
        }
    }
}
