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
        String arg = ("22101201022010220201010100000002000000000000000000000200100000000h7o");
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
