package it.polimi.group11;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.AudioManager;
import android.media.SoundPool;
import it.polimi.group11.model.*;
import android.widget.Chronometer;

public class PlayGameActivity extends AppCompatActivity {

    /**
     * Attribute declaration
     */
    private Game2 game2 ;
    public Configuration config;
    private TextView turn;
    private ImageView nowPlay;
    private TextView instruction;
    private TextView contBeadNum1;
    private TextView contBeadNum2;
    private TextView contBeadNum3;
    private TextView contBeadNum4;
    private ImageView playerIcon1;
    private ImageView playerIcon2;
    private ImageView playerIcon3;
    private ImageView playerIcon4;
    private Chronometer matchTime;
    private ImageView cells[] = new ImageView[49];
    private ImageView hbars[] = new ImageView[7];
    private ImageView vbars[] = new ImageView[7];
    private float a;
    private float b;
    private float da;
    private float db;
    private float centre[] = new float[2];
    private float startPointA;
    private float startPointB;
    private float finalPoint;
    private float hystoricalPointX;
    private float hystoricalPointY;
    private float deltaX;
    private float deltaY;
    private boolean moving;
    private boolean firstH[] = new boolean[7];
    private boolean firstV[] = new boolean[7];
    private int posH;
    private int posV;
    private float limitRight;
    private float limitLeft;
    private float limitTop;
    private float limitBottom;
    private ImageView beads[][];
    private SoundPool sounds;
    private int sound1;
    private boolean fxOn;
    public final static String EXTRA_MESSAGE = "it.polimi.group11.MESSAGE";
    private long lastPause;

    /**
     * Constructor
     */
    public PlayGameActivity() {
        setB(0.0f);
        setA(0.0f);
        setMoving(false);
        for(int i=0;i<7;i++) {
            setFirstH(false, i);
            setFirstV(false,i);
        }
    }

    /**
     * OnCreate event means inizialization of every graphic element of the layout
     * and assignment of listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        int k=0;

        //Get intents from previous activity
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            k = bundle.getInt("PLAYER_NUMBER");
            game2 = new Game2(k);
            config = new Configuration();
            for(int i=0;i<game2.getPlayerNum();i++) {
                game2.getCurrentMovingPlayer().setConfiguration(config);
                game2.iteratorNext();
            }
        }

        //Declaration variable for sound effects
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sound1 = sounds.load(getApplicationContext(), R.raw.bead_destroyed_fx, 1);
        fxOn = OptionsActivity.fxSoundsCheck;

        //Declaration of initial situation of the Play Game Activity
        setStartViewSituation(k);

        //Beads initialization
        createBeadView();
        displayTurn();
        changeIconPlayerTurn();

        //Initial position of the horizontal bars
        placeHorizontalBarStartPosition();

        //Initial position of the vertical bars
        placeVerticalBarStartPosition();

        //Initialization of the cells
        placeBeadsStartPosition();
    }


    /**
     *OnTouchListener: implements the listener on onTouch events
     */
    private final class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setA(event.getRawX());
                    setB(event.getRawY());
                    setDa(getA() - v.getX());
                    setDb(getB() - v.getY());
                    setStartPointA(getA() - getDa());
                    setStartPointB(getB() - getDb());

                    //This function set the Hystorical Point, that is the start point, of every bars
                    createHystoricalPoint(v);
                    setMoving(true);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (getMoving()) {
                        //this condition verify that the instructions on the inside are made ​​only by ImageView with description = " horizontal ", that is Horizontal Bars

                        if (v.getContentDescription().equals("horizontal")) {
                            moveHorizontalBar(v,event);
                        }
                        else {
                            //this condition verify that the instructions on the inside are made ​​only by ImageView with description = " vertical ", that is Vertical Bars

                            if (v.getContentDescription().equals("vertical")) {
                                moveVerticalBar(v,event);
                            }
                            else {
                                //this condition verify that the instructions on the inside are made ​​only by ImageView with description != "vertical" or "horizontal", that is Beads
                                ClipData data = ClipData.newPlainText("", "");
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                                v.startDrag(data, shadowBuilder, v, 0);
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the right)
                    moveToRight(v,event);
                    //These "if" occur that the bar back to the starting position if it moves  less than half of a cell (move to the right)
                    if(getDeltaX()>0 && ((event.getRawX() - getDa()) < (getStartPointA()+((getFinalPoint()-getStartPointA())/2))) && (event.getRawX() - getDa()) < getLimitRight())
                    {
                        setA(getStartPointA());
                        v.setX(getA());
                    }
                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the left)
                    moveToLeft(v,event);
                    //These "if" occur that the bar back to the starting position if it moves  less than half of a cell (move to the left)
                    if(getDeltaX()<0 && ((event.getRawX() - getDa()) > (getFinalPoint()+((getStartPointA()-getFinalPoint())/2))) && (event.getRawX() - getDa()) > getLimitLeft())
                    {
                        setA(getStartPointA());
                        v.setX(getA());
                    }

                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the bottom)
                    moveToBottom(v,event);
                    //These "if" occur that the bar back to the starting position if it moves less than half of a cell (move to the bottom)
                    if (getDeltaY()>0 && ((event.getRawY() - getDb()) < (getStartPointB()+((getFinalPoint()-getStartPointB())/2))) && (event.getRawY() - getDb()) < getLimitBottom())
                    {
                        setB(getStartPointB());
                        v.setY(getB());
                    }
                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the top)
                    moveToTop(v,event);
                    //These "if" occur that the bar back to the starting position if it moves less than half of a cell (move to the top)
                    if (getDeltaY()<0 && ((event.getRawY() - getDb()) > (getFinalPoint()+((getStartPointB()-getFinalPoint())/2)))&& (event.getRawY() - getDb()) > getLimitTop())
                    {
                        setB(getStartPointB());
                        v.setY(getB());
                    }
                    setDeltaX(0);
                    setDeltaY(0);
                    setMoving(false);
                    break;
            }

            return true;
        }
    }

    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    View view = (View) event.getLocalState();
                    for(int i=0; i<7;i++) {
                        for(int j=0; j<7;j++) {
                            if(v.getContentDescription().equals("cella")) {
                                if (game2.board.getCell(i, j).getId().equals(v.getResources().getResourceName(v.getId()).substring(21,(v.getResources().getResourceName(v.getId()).length())))) {
                                    game2.board.getCell(i, j).setCentre(v, view.getWidth(), view.getHeight());
                                    centre = game2.board.getCell(i, j).getCentre();
                                    if (game2.getCurrentMovingPlayer().placeBead(game2.getCurrentPlayer(),i, j) == true){
                                        view.setX(centre[0]);
                                        view.setY(centre[1]);
                                        view.setVisibility(View.VISIBLE);
                                        view.setOnTouchListener(null);
                                        game2.setTotalBeadsInBoard(game2.getTotalBeadsInBoard() + 1);
                                        game2.iteratorNext();
                                        displayTurn();
                                        changeIconPlayerTurn();
                                    }
                                    else{
                                        view.setX(100);
                                        view.setY(900);
                                        view.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }


                    if(game2.getTotalBeadsInBoard() == (5*game2.getPlayerNum())) {
                        //Log.i("ciao","checkGrid "+game2.board.getCheckGrid());
                        //Log.i("ciao","beadsPosition "+game2.board.getBeadsPosition());
                        config.setGame2(game2);
                        config.setBoard(game2.board);
                        instruction.setText("Move Bars");
                        Log.i("PlayGame", "config " + config.getConfiguration());
                        Log.i("PlayGame","CheckGrid "+game2.board.getCheckGrid());
                        for(int i=0;i <7;i++) {
                            hbars[i].setOnTouchListener(new MyTouchListener());
                            vbars[i].setOnTouchListener(new MyTouchListener());
                        }
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    View viewF = (View) event.getLocalState();
                    if(!event.getResult())
                    {
                        viewF.setX(100);
                        viewF.setY(900);
                        viewF.setVisibility(View.VISIBLE);

                    }

                default:
                    break;
            }
            return true;
        }

    }



    public float getLimitBottom() {
        return limitBottom;
    }

    public void setLimitBottom(float limitBottom) {
        this.limitBottom = limitBottom;
    }

    public float getLimitTop() {
        return limitTop;
    }

    public void setLimitTop(float limitTop) {
        this.limitTop = limitTop;
    }

    public float getLimitLeft() {
        return limitLeft;
    }

    public void setLimitLeft(float limitLeft) {
        this.limitLeft = limitLeft;
    }

    public float getLimitRight() {
        return limitRight;
    }

    public void setLimitRight(float limitRight) {
        this.limitRight = limitRight;
    }

    public boolean getFirstV(int position) {
        return firstV[position];
    }

    public void setFirstV(boolean firstV,int position) {
        this.firstV[position] = firstV;
    }

    public boolean getFirstH(int position) {
        return firstH[position];
    }

    public void setFirstH(boolean firstH,int position) {
        this.firstH[position] = firstH;
    }

    public float getStartPointB() {
        return startPointB;
    }

    public void setStartPointB(float startPointB) {
        this.startPointB = startPointB;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    public float getHystoricalPointY() {
        return hystoricalPointY;
    }

    public void setHystoricalPointY(float hystoricalPointY) {
        this.hystoricalPointY = hystoricalPointY;
    }

    public float getHystoricalPointX() {
        return hystoricalPointX;
    }

    public void setHystoricalPointX(float hystoricalPointX) {
        this.hystoricalPointX = hystoricalPointX;
    }

    public float getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(float finalPoint) {
        this.finalPoint = finalPoint;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public float getStartPointA() {
        return startPointA;
    }

    public void setStartPointA(float startPoint) {
        this.startPointA = startPoint;
    }


    public boolean getMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getDb() {
        return db;
    }

    public void setDb(float db) {
        this.db = db;
    }

    public float getDa() {
        return da;
    }

    public void setDa(float da) {
        this.da = da;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void playSoundBead(){
        if(fxOn) {
            sounds.play(sound1, 1.0f, 1.0f, 0, 0, 1.5f);
        }
    }

    void updateAlpha(View v)
    {

        v.setAlpha(0.1f);
    }

    public void setStartViewSituation(int numPlayer){
        turn = (TextView) findViewById(R.id.textViewPlayerName);
        matchTime = (Chronometer) findViewById(R.id.chronometer);
        nowPlay = (ImageView) findViewById(R.id.imageViewBackgroundPlayer);
        instruction = (TextView) findViewById(R.id.textViewActionToDo);
        contBeadNum1 = (TextView) findViewById(R.id.textViewPlayer1);
        contBeadNum2 = (TextView) findViewById(R.id.textViewPlayer2);
        contBeadNum3 = (TextView) findViewById(R.id.textViewPlayer3);
        contBeadNum4 = (TextView) findViewById(R.id.textViewPlayer4);
        playerIcon1 = (ImageView) findViewById(R.id.imageViewPlayer1);
        playerIcon2 = (ImageView) findViewById(R.id.imageViewPlayer2);
        playerIcon3 = (ImageView) findViewById(R.id.imageViewPlayer3);
        playerIcon4 = (ImageView) findViewById(R.id.imageViewPlayer4);
        instruction.setText("Place Bead");
        matchTime.setBase(SystemClock.elapsedRealtime());
        matchTime.start();
        switch(numPlayer){
            case 2:
                contBeadNum1.setText("5");
                contBeadNum2.setText("5");
                contBeadNum3.setText("X");
                contBeadNum4.setText("X");
                updateAlpha(playerIcon3);
                updateAlpha(playerIcon4);
                break;
            case 3:
                contBeadNum1.setText("5");
                contBeadNum2.setText("5");
                contBeadNum3.setText("5");
                contBeadNum4.setText("X");
                updateAlpha(playerIcon4);
                break;
            case 4:
                contBeadNum1.setText("5");
                contBeadNum2.setText("5");
                contBeadNum3.setText("5");
                contBeadNum4.setText("5");
                break;
            default:
                break;
        }
    }

    public void displayTurn(){
        turn.setText("Turn of "+ game2.getCurrentPlayer());
    }

    public void createBeadView(){
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.relative);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mainLayout.getLayoutParams());
        params.setMargins(100,900,0,0);
        beads = new ImageView[game2.getPlayerNum()][5];

        for (int j = 4; j >=0; j--) {
            for (int i = (game2.getPlayerNum()-1); i >=0 ; i--) {
                beads[i][j] = new ImageView(this);
                mainLayout.addView(beads[i][j], params);
                String id = "bead" + Integer.toString(i) + Integer.toString(j);
                game2.iteratorPrevious(game2.getPlayerNum());
                switch(Integer.parseInt(game2.getCurrentPlayer())) {
                    case 1:
                        beads[i][j].setImageResource(R.drawable.bead3);
                        break;
                    case 2:
                        beads[i][j].setImageResource(R.drawable.bead1);
                        break;
                    case 3:
                        beads[i][j].setImageResource(R.drawable.bead4);
                        break;
                    case 4:
                        beads[i][j].setImageResource(R.drawable.bead2);
                        break;
                    default:
                        break;
                }
                beads[i][j].setLayoutParams(params);
                beads[i][j].getLayoutParams().height =(int) (30 * beads[i][j].getResources().getDisplayMetrics().density);
                beads[i][j].getLayoutParams().width =(int) (30 * beads[i][j].getResources().getDisplayMetrics().density);
                beads[i][j].setContentDescription(id);
                beads[i][j].setOnTouchListener(new MyTouchListener());
            }
        }
    }

    public void placeHorizontalBarStartPosition(){
        for (int i=0; i<7; i++) {
            int id = getResources().getIdentifier("horizontalbar" + i, "id", getPackageName());
            hbars[i] = (ImageView) findViewById(id);
            posH = game2.board.horizontalBar[i].getPosition();
            float marginLeftH = hbars[i].getX();
            switch (posH) {
                case 0:
                    int plusMarginLeft = (int) (marginLeftH + (32 * hbars[i].getResources().getDisplayMetrics().density));
                    hbars[i].setX(plusMarginLeft);
                    break;
                case 1:
                    break;
                case 2:
                    int subMarginLeft = (int)(marginLeftH - (32 * hbars[i].getResources().getDisplayMetrics().density));
                    hbars[i].setX(subMarginLeft);
                    break;
                default:
                    break;
            }
        }
    }

    public void placeVerticalBarStartPosition(){
        for (int i=0; i<7; i++) {
            int id = getResources().getIdentifier("verticalbar" + i, "id", getPackageName());
            vbars[i] = (ImageView) findViewById(id);
            posV = game2.board.verticalBar[i].getPosition();
            float marginTopV = vbars[i].getY();
            switch (posV) {
                case 0:
                    int plusMarginTop = (int) (marginTopV + (32 * vbars[i].getResources().getDisplayMetrics().density));
                    vbars[i].setY(plusMarginTop);
                    break;
                case 1:
                    break;
                case 2:
                    int subMarginTop = (int) (marginTopV - (32 * vbars[i].getResources().getDisplayMetrics().density));
                    vbars[i].setY(subMarginTop);
                    break;
                default:
                    break;
            }
        }
    }

    public void placeBeadsStartPosition(){
        for (int i=0; i<49; i++) {
            int id = getResources().getIdentifier("cell" + i, "id", getPackageName());
            cells[i] = (ImageView) findViewById(id);
            cells[i].setOnDragListener(new MyDragListener());
        }
    }

    public void setRightLeftBound(int i,View v){
        switch (game2.board.horizontalBar[i].getPosition()) {
            case 0:
                setLimitRight(getHystoricalPointX());
                setLimitLeft(getHystoricalPointX() - (64 * v.getResources().getDisplayMetrics().density));
                break;
            case 1:
                setLimitRight((getHystoricalPointX() + (32 * v.getResources().getDisplayMetrics().density)));
                setLimitLeft((getHystoricalPointX() - (32 * v.getResources().getDisplayMetrics().density)));
                break;
            case 2:
                setLimitRight(getHystoricalPointX() + (64 * v.getResources().getDisplayMetrics().density));
                setLimitLeft(getHystoricalPointX());
                break;
            default:
                break;
        }
    }

    public void setBottomTopBound(int i,View v){
        switch (game2.board.verticalBar[i].getPosition()) {
            case 0:
                setLimitBottom(getHystoricalPointY());
                setLimitTop(getHystoricalPointY() - (64 * v.getResources().getDisplayMetrics().density));
                break;
            case 1:
                setLimitBottom((getHystoricalPointY() + (32 * v.getResources().getDisplayMetrics().density)));
                setLimitTop((getHystoricalPointY() - (32 * v.getResources().getDisplayMetrics().density)));
                break;
            case 2:
                setLimitBottom(getHystoricalPointY() + (64 * v.getResources().getDisplayMetrics().density));
                setLimitTop(getHystoricalPointY());
                break;
            default:
                break;
        }
    }

    public void createHystoricalPoint(View v){
        if(v.getContentDescription().equals("horizontal") || v.getContentDescription().equals("vertical")) {
            for (int i = 0; i < 7; i++) {
                String barh = "it.polimi.group11:id/horizontalbar" + i;
                String barv = "it.polimi.group11:id/verticalbar" + i;
                if (barh.equals(v.getResources().getResourceName(v.getId()))) {
                    if (!getFirstH(i)) {
                        setHystoricalPointX(getA() - getDa());
                        setFirstH(true, i);

                        //Sets limit right and left of movement
                        setRightLeftBound(i,v);
                    }
                }
                if (barv.equals(v.getResources().getResourceName(v.getId()))) {
                    if (!getFirstV(i)) {
                        setHystoricalPointY(getB() - getDb());
                        setFirstV(true, i);

                        //Sets limit top and bottom of movement
                        setBottomTopBound(i,v);
                    }
                }
            }
        }
    }

    public void moveHorizontalBar(View v,MotionEvent event){
        setDeltaX((event.getRawX() - getDa()) - getStartPointA()) ;
        //if the bar moves to the right
        if ( getDeltaX() > 0 )
        {
            setFinalPoint((getStartPointA() + (32 * v.getResources().getDisplayMetrics().density)));
            //It prevents the bar to move towards the right, for more than one cell and one cell at a time
            if(((event.getRawX() - getDa()) <= getFinalPoint()) && (event.getRawX() - getDa()) < getLimitRight()) {
                setA(event.getRawX() - getDa());
                v.setX(getA());
            }
        }
        //if the bar moves to the left
        if(getDeltaX() < 0 )
        {
            setFinalPoint(((getStartPointA()- (32 * v.getResources().getDisplayMetrics().density))));
            //It prevents the bar to move towards the left, for more than one cell and one cell at a time
            if(((event.getRawX() - getDa()) >= getFinalPoint())  && (event.getRawX() - getDa()) > getLimitLeft()) {
                setA(event.getRawX() - getDa());
                v.setX(getA());
            }
        }
    }

    public void moveVerticalBar(View v, MotionEvent event){
        setDeltaY((event.getRawY() - getDb()) - getStartPointB()) ;
        //if the bar moves to the top
        if (getDeltaY() < 0 )
        {
            setFinalPoint((getStartPointB() - (32 * v.getResources().getDisplayMetrics().density)));
            //It prevents the bar to move towards the top, for more than one cell and one cell at a time
            if(((event.getRawY() - getDb()) >= getFinalPoint()) && (event.getRawY() - getDb()) > getLimitTop()) {
                setB(event.getRawY() - getDb());
                v.setY(getB());
            }
        }
        //if the bar moves to the bottom
        if(getDeltaY() > 0 )
        {
            setFinalPoint(((getStartPointB() + (32 * v.getResources().getDisplayMetrics().density))));
            //It prevents the bar to move towards the bottom, for more than one cell and one cell at a time
            if(((event.getRawY() - getDb()) <= getFinalPoint()) && (event.getRawY() - getDb()) < getLimitBottom()) {
                setB(event.getRawY() - getDb());
                v.setY(getB());
            }
        }
    }


    public void finishGame(){
        Log.i("ciao ", "vince " + game2.getNextPlayer());
        Intent intent = new Intent(PlayGameActivity.this, PopUpActivity.class);
        String message = game2.getNextPlayer();
        intent.putExtra(EXTRA_MESSAGE, message);
        for(int i=0;i <7;i++) {
            hbars[i].setOnTouchListener(null);
            vbars[i].setOnTouchListener(null);
        }
        matchTime.stop();
        startActivity(intent);
    }


    public void killBead(Move moveToCheck) {
        game2.getCurrentMovingPlayer().setMovesNumber(game2.getCurrentMovingPlayer().getMovesNumber() + 1);
        game2.getMovesList().add(moveToCheck);
        int aliveBeads=0;
        for (int i = 0; i < game2.getPlayerNum(); i++) {
            for (int j = 0; j < 5; j++) {
                if (!game2.getCurrentMovingPlayer().getBead(j).getLife()) {
                    beads[(Integer.parseInt(game2.getCurrentMovingPlayer().getId())) - 1][j].setVisibility(View.INVISIBLE);
                    // playSoundBead();
                    aliveBeads = game2.getCurrentMovingPlayer().getBeadsInBoard();
                    decrementContBeadsView(game2.getCurrentMovingPlayer().getId(),aliveBeads);
                }
            }
            game2.iteratorNext();
        }
        do {
            game2.iteratorNext();
            if (!game2.getCurrentMovingPlayer().getStatus()) {
                moveToCheck = new Move("RIP", game2.getCurrentMovingPlayer().getId());
                game2.getMovesList().add(moveToCheck);
            }
        }while(!game2.getCurrentMovingPlayer().getStatus());
        displayTurn();
        changeIconPlayerTurn();
    }

    public void moveToRight(View v,MotionEvent event){
        if(getDeltaX()>0 && ((event.getRawX() - getDa()) > (getStartPointA()+((getFinalPoint()-getStartPointA())/2))) ) {
            StringBuilder mov = new StringBuilder();
            int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(34,35))+1;
            mov.append("h");
            mov.append(Integer.toString(num));
            mov.append("i");
            Move moveToCheck = new Move(mov.toString(), game2.getCurrentMovingPlayer().getId());
            Log.i("PlayGame", "la mossa è " + game2.generalMoveCheck(moveToCheck));
            if((game2.generalMoveCheck(moveToCheck))){
                game2.getCurrentMovingPlayer().makeMove(mov.toString());
                game2.checkRowBeadsLife(num);
                setA(getFinalPoint());
                v.setX(getA());
                if(game2.getGameOver()){
                    finishGame();
                }
                else {
                    killBead(moveToCheck);
                }
            }
            else{
                setA(getStartPointA());
                v.setX(getA());

            }
            game2.setValidity(true);
            Log.i("PlayGame", "config " + config.getConfiguration());
        }
    }

    public void moveToLeft(View v, MotionEvent event){
        if(getDeltaX()<0 && ((event.getRawX() - getDa()) < (getFinalPoint()+((getStartPointA()-getFinalPoint())/2))))
        {
            StringBuilder mov = new StringBuilder();
            int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(34,35))+1;
            mov.append("h");
            mov.append(Integer.toString(num));
            mov.append("o");
            Move moveToCheck = new Move(mov.toString(), game2.getCurrentMovingPlayer().getId());
            Log.i("PlayGame", "la mossa è " + game2.generalMoveCheck(moveToCheck));
            if(game2.generalMoveCheck(moveToCheck)){
                game2.getCurrentMovingPlayer().makeMove(mov.toString());
                game2.checkRowBeadsLife(num);
                setA(getFinalPoint());
                v.setX(getA());
                if(game2.getGameOver()){
                    finishGame();
                }
                else {
                    killBead(moveToCheck);
                }
            }
            else{
                setA(getStartPointA());
                v.setX(getA());
            }
            game2.setValidity(true);
            Log.i("PlayGame", "config " + config.getConfiguration());
        }
    }

    public void moveToBottom(View v, MotionEvent event){
        if (getDeltaY()>0 && ((event.getRawY() - getDb()) > (getStartPointB()+((getFinalPoint()-getStartPointB())/2))))
        {
            StringBuilder mov = new StringBuilder();
            int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(32,33))+1;
            mov.append("v");
            mov.append(Integer.toString(num));
            mov.append("i");
            Move moveToCheck = new Move(mov.toString(), game2.getCurrentMovingPlayer().getId());
            Log.i("PlayGame", "la mossa è " + game2.generalMoveCheck(moveToCheck));
            if((game2.generalMoveCheck(moveToCheck))){
                game2.getCurrentMovingPlayer().makeMove(mov.toString());
                game2.checkColumnBeadsLife(num);
                setB(getFinalPoint());
                v.setY(getB());
                if(game2.getGameOver()){
                    finishGame();
                }
                else {
                    killBead(moveToCheck);
                }
            }
            else{
                setB(getStartPointB());
                v.setY(getB());
            }
            game2.setValidity(true);
            Log.i("PlayGame", "config " + config.getConfiguration());
        }
    }

    public void moveToTop(View v,MotionEvent event){
        if (getDeltaY()<0 && ((event.getRawY() - getDb()) < (getFinalPoint()+((getStartPointB()-getFinalPoint())/2))))
        {
            StringBuilder mov = new StringBuilder();
            int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(32,33))+1;
            mov.append("v");
            mov.append(Integer.toString(num));
            mov.append("o");
            Move moveToCheck = new Move(mov.toString(), game2.getCurrentMovingPlayer().getId());
            Log.i("PlayGame", "la mossa è " + game2.generalMoveCheck(moveToCheck));
            if((game2.generalMoveCheck(moveToCheck))){
                game2.getCurrentMovingPlayer().makeMove(mov.toString());
                game2.checkColumnBeadsLife(num);
                setB(getFinalPoint());
                v.setY(getB());
                if(game2.getGameOver()){
                    finishGame();
                }
                else{
                    killBead(moveToCheck);
                }
            }
            else{
                setB(getStartPointB());
                v.setY(getB());
            }
            game2.setValidity(true);
            Log.i("PlayGame", "config " + config.getConfiguration());
        }
    }

    public void showAlert(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pause");
        builder.setPositiveButton("Resume Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                matchTime.setBase(matchTime.getBase()+SystemClock.elapsedRealtime()-lastPause);
                matchTime.start();
            }
        });
        builder.setNegativeButton("Exit Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToMainActivity();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        lastPause = SystemClock.elapsedRealtime();
        matchTime.stop();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void decrementContBeadsView(String player,int aliveBeads){
        switch(player){
            case "1":
                contBeadNum1.setText(Integer.toString(aliveBeads));
                if(aliveBeads == 0) {
                    updateAlpha(playerIcon1);
                }
                break;
            case "2":
                contBeadNum2.setText(Integer.toString(aliveBeads));
                if(aliveBeads == 0) {
                    updateAlpha(playerIcon2);
                }
                break;
            case "3":
                contBeadNum3.setText(Integer.toString(aliveBeads));
                if(aliveBeads == 0) {
                    updateAlpha(playerIcon3);
                }
                break;
            case "4":
                contBeadNum4.setText(Integer.toString(aliveBeads));
                if(aliveBeads == 0) {
                    updateAlpha(playerIcon4);
                }
                break;
            default:
                break;
        }
    }

    public void changeIconPlayerTurn(){
        switch(game2.getCurrentPlayer()) {
            case "1":
                nowPlay.setImageResource(R.drawable.planet_statusbar);
                break;
            case "2":
                nowPlay.setImageResource(R.drawable.astronaut_statusbar);
                break;
            case "3":
                nowPlay.setImageResource(R.drawable.rocket_statusbar);
                break;
            case "4":
                nowPlay.setImageResource(R.drawable.comet_statusbar);
                break;
            default:
                break;
        }
    }

}
