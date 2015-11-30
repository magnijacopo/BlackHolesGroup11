package it.polimi.group11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;


import it.polimi.group11.firstReleaseTest.TestFirstRelease;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.Start();
    }

    public void Start(){
        TestFirstRelease testFirstRelease = new TestFirstRelease();
        String arg = ( "2" // number of players
                + "1" // moving player
                + "0120120" // positions of the horizontal bars
                + "2101102" // positions of the vertical bars
                + "0000000" // beads in the grid
                + "0000000"
                + "0000200"
                + "0000010"
                + "0010000"
                + "2000000"
                + "0001020"
                + "h3i"
                + "h6i"
                + "h3o"
                + "v5o"
                + "h3i"
        );
        String result = testFirstRelease.moveTest(arg);
        System.out.println(result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
