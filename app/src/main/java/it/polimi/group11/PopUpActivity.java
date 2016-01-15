package it.polimi.group11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by rezzo on 13/01/2016.
 */
public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow);
        Intent intent = getIntent();

        String message = intent.getStringExtra(PlayGameActivity.EXTRA_MESSAGE);
        TextView popup = (TextView) findViewById(R.id.popup);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.popuprel) ;
        //rl.getBackground().setAlpha(3);
        ImageView player = (ImageView) findViewById(R.id.popup_bg);
        popup.setText("The winner is "+message);

        switch(message){
            case "1":
                player.setImageResource(R.drawable.astronaut_win);
            case "2":
                player.setImageResource(R.drawable.comet_win);
            case "3":
                player.setImageResource(R.drawable.planet_win);
            case "4":
                player.setImageResource(R.drawable.rocket_win);
        }


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

//questo setta le dimensioni del popup all' 70% della larghezza dello schermo e al 50% dell' altezza dello schermo
        getWindow().setLayout((int)(width*.70),(int)(height*.50));
    }
    public void goToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
