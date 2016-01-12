package it.polimi.group11;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import it.polimi.group11.helper.Guest;
import it.polimi.group11.helper.GuestData;
import it.polimi.group11.helper.PlayersNumberManager;
import it.polimi.group11.helper.RecyclerListAdapter;
import it.polimi.group11.helper.SimpleItemTouchHelperCallback;

public class SelectPlayersActivity extends AppCompatActivity implements PlayersNumberManager.Listener {

    ItemTouchHelper mItemTouchHelper;
    private static Context context;
    private FloatingActionButton fab;
    private RecyclerListAdapter adapter;
    private SimpleItemTouchHelperCallback callback;
    private PlayersNumberManager playersManager;
    private int playersNumber;
    private CoordinatorLayout.LayoutParams p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);

        adapter = new RecyclerListAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        playersNumber = adapter.mItems.size();


        fab  = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer();
            }
        });

        p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

        playersManager = new PlayersNumberManager();
        playersManager.registerListener(this);
        playersManager.change(adapter.mItems.size());

    }

    public void goToPlayGame(View view){
        Intent intent = new Intent(this, PlayGameActivity.class);
        intent.putExtra("PLAYER_NUMBER", adapter.mItems.size());
        startActivity(intent);
    }


    public void goToChoosePlayerType() {
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }

    private void addPlayer(){
        if(adapter.mItems.size() < 4) {
            adapter.mItems.add(new Guest(
                    GuestData.getNameArray(adapter.mItems.size()),
                    GuestData.getStockImage(),
                    GuestData.getId(adapter.mItems.size())
            ));
            callback.setPlayersNumberCallback(adapter.mItems.size());
            adapter.notifyDataSetChanged();
        }
    }

    public void goToChoosePlayerType(View view) {
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPlayersNumberChange(boolean trigger){
        if(trigger) {
            p.setAnchorId(View.NO_ID);
            fab.setLayoutParams(p);
            fab.setVisibility(View.GONE);
        }else{
            fab.setVisibility(View.VISIBLE);
        }
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
