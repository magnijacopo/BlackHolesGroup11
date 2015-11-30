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
        String arg = ( "4" + "1" +
                "1111111" +
                "1111111" +
                "0304000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000012" +
                "h4i" +
                "h3i" +
                "h2i" +
                "h1i" +
                "h4o" +
                "h3o" +
                "h5o"
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
