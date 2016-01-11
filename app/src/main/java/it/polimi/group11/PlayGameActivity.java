package it.polimi.group11;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.Objects;

import it.polimi.group11.model.*;


public class PlayGameActivity extends AppCompatActivity {

    /**
     * Attribute declaration
     */
    private Game2 game2 ;

    ImageView cells[] = new ImageView[49];
    ImageView hbars[] = new ImageView[7];
    ImageView vbars[] = new ImageView[7];
    private float a;
    private float b;
    private float da;
    private float db;
    float centre[] = new float[2];
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
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            int j = bundle.getInt("PLAYER_NUMBER");
            game2 = new Game2(j);
        }

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.parent);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mainLayout.getLayoutParams());
        ImageView beads[][] = new ImageView[game2.getPlayerNum()][5];

        game2.randomFirstPlayer();


        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < game2.getPlayerNum(); i++) {
                beads[i][j] = new ImageView(this);
                mainLayout.addView(beads[i][j], params);
                String id = "bead" + Integer.toString(i) + Integer.toString(j);
                switch(Integer.parseInt(game2.getCurrentPlayer())) {
                    case 1:
                        beads[i][j].setImageResource(R.mipmap.bead1);
                        break;
                    case 2:
                        beads[i][j].setImageResource(R.mipmap.bead2);
                        break;
                    case 3:
                        beads[i][j].setImageResource(R.mipmap.bead3);
                        break;
                    case 4:
                        beads[i][j].setImageResource(R.mipmap.bead4);
                        break;
                    default:
                        break;
                }
                game2.iteratorNext();
                //ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(beads[j][i].getLayoutParams());
                //marginParams.setMargins(0, 0, (int) (10 * beads[j][i].getResources().getDisplayMetrics().density), (int) (10 * beads[j][i].getResources().getDisplayMetrics().density));
                //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                //beads[j][i].setLayoutParams(layoutParams);
                beads[i][j].setLayoutParams(new RelativeLayout.LayoutParams((int) (20 * beads[i][j].getResources().getDisplayMetrics().density), (int) (20 * beads[i][j].getResources().getDisplayMetrics().density)));
                beads[i][j].setContentDescription(id);
                beads[i][j].setOnTouchListener(new MyTouchListener());
            }
        }

        /**
         * The following "for" contains initial position of the bars
         */
//Initial position of the horizontal bars
        for (int i=0; i<7; i++) {
            int id = getResources().getIdentifier("horizontalbar" + i, "id", getPackageName());
            hbars[i] = (ImageView) findViewById(id);
            posH = game2.board.horizontalBar[i].getPosition();
            float marginLeftH = hbars[i].getX();
            switch (posH) {
                case 0:
                    int plusMarginLeft = (int) (marginLeftH + (30 * hbars[i].getResources().getDisplayMetrics().density));
                    hbars[i].setX(plusMarginLeft);
                    break;
                case 1:
                    break;
                case 2:
                    int subMarginLeft = (int)(marginLeftH - (30 * hbars[i].getResources().getDisplayMetrics().density));
                    hbars[i].setX(subMarginLeft);
                    break;
                default:
                    break;

            }
        }



//Initial position of the vertical bars
        for (int i=0; i<7; i++) {
            int id = getResources().getIdentifier("verticalbar" + i, "id", getPackageName());
            vbars[i] = (ImageView) findViewById(id);
            posV = game2.board.verticalBar[i].getPosition();
            float marginTopV = vbars[i].getY();
            switch (posV) {
                case 0:
                    int plusMarginTop = (int) (marginTopV + (30 * vbars[i].getResources().getDisplayMetrics().density));
                    vbars[i].setY(plusMarginTop);
                    break;
                case 1:
                    break;
                case 2:
                    int subMarginTop = (int) (marginTopV - (30 * vbars[i].getResources().getDisplayMetrics().density));
                    vbars[i].setY(subMarginTop);
                    break;
                default:
                    break;

            }
        }


        for (int i=0; i<49; i++) {
            int id = getResources().getIdentifier("cell" + i, "id", getPackageName());
            cells[i] = (ImageView) findViewById(id);
            cells[i].setOnDragListener(new MyDragListener());
        }




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
                    //This "for" set the Hystorical Point, that is the start point, of every bars
                    if(v.getContentDescription().equals("horizontal") || v.getContentDescription().equals("vertical")) {
                        for (int i = 0; i < 7; i++) {
                            String barh = "it.polimi.group11:id/horizontalbar" + i;
                            String barv = "it.polimi.group11:id/verticalbar" + i;
                            if (barh.equals(v.getResources().getResourceName(v.getId()))) {
                                if (!getFirstH(i)) {
                                    setHystoricalPointX(getA() - getDa());
                                    setFirstH(true, i);
                                    //Sets limit right and left of movement
                                    switch (game2.board.horizontalBar[i].getPosition()) {
                                        case 0:
                                            setLimitRight(getHystoricalPointX());
                                            setLimitLeft(getHystoricalPointX() - (60 * v.getResources().getDisplayMetrics().density));
                                            break;
                                        case 1:
                                            setLimitRight((getHystoricalPointX() + (30 * v.getResources().getDisplayMetrics().density)));
                                            setLimitLeft((getHystoricalPointX() - (30 * v.getResources().getDisplayMetrics().density)));
                                            break;
                                        case 2:
                                            setLimitRight(getHystoricalPointX() + (60 * v.getResources().getDisplayMetrics().density));
                                            setLimitLeft(getHystoricalPointX());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                            if (barv.equals(v.getResources().getResourceName(v.getId()))) {
                                if (!getFirstV(i)) {
                                    setHystoricalPointY(getB() - getDb());
                                    setFirstV(true, i);
                                    //Sets limit top and bottom of movement
                                    switch (game2.board.verticalBar[i].getPosition()) {
                                        case 0:
                                            setLimitBottom(getHystoricalPointY());
                                            setLimitTop(getHystoricalPointY() - (60 * v.getResources().getDisplayMetrics().density));
                                            break;
                                        case 1:
                                            setLimitBottom((getHystoricalPointY() + (30 * v.getResources().getDisplayMetrics().density)));
                                            setLimitTop((getHystoricalPointY() - (30 * v.getResources().getDisplayMetrics().density)));
                                            break;
                                        case 2:
                                            setLimitBottom(getHystoricalPointY() + (60 * v.getResources().getDisplayMetrics().density));
                                            setLimitTop(getHystoricalPointY());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }

                    setMoving(true);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (getMoving()) {
                        //this condition verify that the instructions on the inside are made ​​only by ImageView with description = " horizontal ", that is Horizontal Bars
                        if (v.getContentDescription().equals("horizontal")) {

                           setDeltaX((event.getRawX() - getDa()) - getStartPointA()) ;
                            //if the bar moves to the right
                            if ( getDeltaX() > 0 )
                            {
                                setFinalPoint((getStartPointA()+ (30 * v.getResources().getDisplayMetrics().density)));
                                //It prevents the bar to move towards the right, for more than one cell and one cell at a time
                                if(((event.getRawX() - getDa()) <= getFinalPoint()) && (event.getRawX() - getDa()) < getLimitRight()) {
                                    setA(event.getRawX() - getDa());
                                    v.setX(getA());
                                }
                            }
                            //if the bar moves to the left
                            if( getDeltaX() < 0 )
                            {
                                setFinalPoint(((getStartPointA()- (30 * v.getResources().getDisplayMetrics().density))));
                                //It prevents the bar to move towards the left, for more than one cell and one cell at a time
                                if(((event.getRawX() - getDa()) >= getFinalPoint())  && (event.getRawX() - getDa()) > getLimitLeft()) {
                                    setA(event.getRawX() - getDa());
                                    v.setX(getA());
                                }
                            }


                        }
                        else {
                            //this condition verify that the instructions on the inside are made ​​only by ImageView with description = " vertical ", that is Vertical Bars
                            if (v.getContentDescription().equals("vertical")) {

                                setDeltaY((event.getRawY() - getDb()) - getStartPointB()) ;
                                //if the bar moves to the top
                                if ( getDeltaY() < 0 )
                                {
                                    setFinalPoint((getStartPointB() - (30 * v.getResources().getDisplayMetrics().density)));
                                    //It prevents the bar to move towards the top, for more than one cell and one cell at a time
                                    if(((event.getRawY() - getDb()) >= getFinalPoint()) && (event.getRawY() - getDb()) > getLimitTop()) {
                                        setB(event.getRawY() - getDb());
                                        v.setY(getB());
                                    }
                                }
                                //if the bar moves to the bottom
                                if( getDeltaY() > 0 )
                                {
                                    setFinalPoint(((getStartPointB() + (30 * v.getResources().getDisplayMetrics().density))));
                                    //It prevents the bar to move towards the bottom, for more than one cell and one cell at a time
                                    if(((event.getRawY() - getDb()) <= getFinalPoint()) && (event.getRawY() - getDb()) < getLimitBottom()) {
                                        setB(event.getRawY() - getDb());
                                        v.setY(getB());
                                    }
                                }

                            }
                            else {
                                ClipData data = ClipData.newPlainText("", "");
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                                v.startDrag(data, shadowBuilder, v, 0);
                                //v.setVisibility(View.INVISIBLE);
                            }

                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:

                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the right)
                    if(getDeltaX()>0 && ((event.getRawX() - getDa()) > (getStartPointA()+((getFinalPoint()-getStartPointA())/2))) )
                    {
                        StringBuilder mov = new StringBuilder();
                        int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(34,35))+1;
                        mov.append("h");
                        mov.append(Integer.toString(num));
                        mov.append("i");
                        if(game2.board.checkBoundsValidity(mov.toString())) {
                            game2.board.moveBar(mov.toString());
                            setA(getFinalPoint());
                            v.setX(getA());
                        }

                       /* int marginLeft = (int) (40 * v.getResources().getDisplayMetrics().density);
                        int newMArginLeft = (int) (marginLeft + (30 * v.getResources().getDisplayMetrics().density));
                        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(v.getLayoutParams());
                        marginParams.setMargins(newMArginLeft,550,0,0);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                        v.setLayoutParams(layoutParams);*/
                    }
                    //These "if" occur that the bar back to the starting position if it moves  less than half of a cell (move to the right)
                    if(getDeltaX()>0 && ((event.getRawX() - getDa()) < (getStartPointA()+((getFinalPoint()-getStartPointA())/2))) && (event.getRawX() - getDa()) < getLimitRight())
                    {
                        setA(getStartPointA());
                        v.setX(getA());
                    }
                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the left)
                    if(getDeltaX()<0 && ((event.getRawX() - getDa()) < (getFinalPoint()+((getStartPointA()-getFinalPoint())/2))))
                    {
                        StringBuilder mov = new StringBuilder();
                        int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(34,35))+1;
                        mov.append("h");
                        mov.append(Integer.toString(num));
                        mov.append("o");
                        if(game2.board.checkBoundsValidity(mov.toString())) {
                            game2.board.moveBar(mov.toString());
                            setA(getFinalPoint());
                            v.setX(getA());
                        }
                    }
                    //These "if" occur that the bar back to the starting position if it moves  less than half of a cell (move to the left)
                    if(getDeltaX()<0 && ((event.getRawX() - getDa()) > (getFinalPoint()+((getStartPointA()-getFinalPoint())/2))) && (event.getRawX() - getDa()) > getLimitLeft())
                    {
                        setA(getStartPointA());
                        v.setX(getA());
                    }

                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the bottom)
                    if (getDeltaY()>0 && ((event.getRawY() - getDb()) > (getStartPointB()+((getFinalPoint()-getStartPointB())/2))))
                    {
                        StringBuilder mov = new StringBuilder();
                        int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(32,33))+1;
                        mov.append("v");
                        mov.append(Integer.toString(num));
                        mov.append("i");
                        if(game2.board.checkBoundsValidity(mov.toString())) {
                            game2.board.moveBar(mov.toString());
                            setB(getFinalPoint());
                            v.setY(getB());
                        }

                    }
                    //These "if" occur that the bar back to the starting position if it moves less than half of a cell (move to the bottom)
                    if (getDeltaY()>0 && ((event.getRawY() - getDb()) < (getStartPointB()+((getFinalPoint()-getStartPointB())/2))) && (event.getRawY() - getDb()) < getLimitBottom())
                    {
                        setB(getStartPointB());
                        v.setY(getB());
                    }
                    //These "if" occur that the bar is shifted by a cell if the bar moves more than half of a cell (move to the top)
                    if (getDeltaY()<0 && ((event.getRawY() - getDb()) < (getFinalPoint()+((getStartPointB()-getFinalPoint())/2))))
                    {
                        StringBuilder mov = new StringBuilder();
                        int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(32,33))+1;
                        mov.append("v");
                        mov.append(Integer.toString(num));
                        mov.append("o");
                        if(game2.board.checkBoundsValidity(mov.toString())) {
                            game2.board.moveBar(mov.toString());
                            setB(getFinalPoint());
                            v.setY(getB());
                        }
                    }
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
                                    int now = Integer.parseInt(game2.getCurrentPlayer());

                                    if (game2.getCurrentMovingPlayer().placeBead(game2.getCurrentPlayer(),i,j) == true){
                                        view.setX(centre[0]);
                                        view.setY(centre[1]);
                                        view.setVisibility(View.VISIBLE);
                                        view.setOnTouchListener(null);
                                        game2.setTotalBeadsInBoard(game2.getTotalBeadsInBoard()+1);
                                        Log.i("ciao","sta posizionando i bead il giocatore "+game2.getCurrentPlayer());
                                        game2.iteratorNext();
                                    }
                                    else{
                                        view.setX(35);
                                        view.setY(35);
                                        view.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }


                    if(game2.getTotalBeadsInBoard() == (5*game2.getPlayerNum())) {
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
                        viewF.setX(35);
                        viewF.setY(35);
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



}
