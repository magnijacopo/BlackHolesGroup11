package it.polimi.group11;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import it.polimi.group11.model.Move;

public class SetYourBarsActivity extends AppCompatActivity {

    private ImageView hbars[] = new ImageView[7];
    private ImageView vbars[] = new ImageView[7];
    private int hbarsPoisiton[] = new int[7];
    private int vbarsPosition[] = new int[7];
    private float a;
    private float b;
    private float da;
    private float db;
    private float startPointA;
    private float startPointB;
    private float finalPoint;
    private float hystoricalPointX;
    private float hystoricalPointY;
    private float deltaX;
    private float deltaY;
    private boolean moving;
    private float limitRight;
    private float limitLeft;
    private float limitTop;
    private float limitBottom;
    private boolean firstH[] = new boolean[7];
    private boolean firstV[] = new boolean[7];
    private Button start;


    public SetYourBarsActivity() {
        setB(0.0f);
        setA(0.0f);
        setMoving(false);
        for(int i=0;i<7;i++) {
            setFirstH(false, i);
            setFirstV(false, i);
            hbarsPoisiton[i] = 1;
            vbarsPosition[i] = 1;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_your_bars);
        start = (Button) findViewById(R.id.buttonsaveconfig);

        for (int i=0; i<7; i++){
            int id = getResources().getIdentifier("horizontalbar" + i, "id", getPackageName());
            hbars[i] = (ImageView) findViewById(id);
            hbars[i].setOnTouchListener(new MyTouchListener());
        }

        for (int i=0; i<7; i++){
            int id = getResources().getIdentifier("vertical" + i, "id", getPackageName());
            vbars[i] = (ImageView) findViewById(id);
            vbars[i].setOnTouchListener(new MyTouchListener());
        }
    }

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
            setFinalPoint(((getStartPointA() - (32 * v.getResources().getDisplayMetrics().density))));
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

    public void moveToRight(View v,MotionEvent event){
        if(getDeltaX()>0 && ((event.getRawX() - getDa()) > (getStartPointA()+((getFinalPoint()-getStartPointA())/2))) ) {
            setA(getFinalPoint());
            v.setX(getA());
            int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(34,35));
            hbarsPoisiton[num] = hbarsPoisiton[num] - 1;
        }
    }

    public void moveToLeft(View v, MotionEvent event){
        if(getDeltaX()<0 && ((event.getRawX() - getDa()) < (getFinalPoint()+((getStartPointA()-getFinalPoint())/2)))) {
            setA(getFinalPoint());
            v.setX(getA());
            int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(34,35));
            hbarsPoisiton[num] = hbarsPoisiton[num] + 1;
        }
    }

    public void moveToBottom(View v, MotionEvent event){
        if (getDeltaY()>0 && ((event.getRawY() - getDb()) > (getStartPointB()+((getFinalPoint()-getStartPointB())/2)))) {
            setB(getFinalPoint());
            v.setY(getB());
            int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(32,33));
            vbarsPosition[num] = vbarsPosition[num] - 1;
        }
    }

    public void moveToTop(View v,MotionEvent event){
        if (getDeltaY()<0 && ((event.getRawY() - getDb()) < (getFinalPoint()+((getStartPointB()-getFinalPoint())/2)))) {
                setB(getFinalPoint());
                v.setY(getB());
                int num = Integer.parseInt((v.getResources().getResourceName(v.getId())).substring(32,33));
                vbarsPosition[num] = vbarsPosition[num] + 1;
        }
    }

    public void setRightLeftBound(int i,View v){
        setLimitRight((getHystoricalPointX() + (32 * v.getResources().getDisplayMetrics().density)));
        setLimitLeft((getHystoricalPointX() - (32 * v.getResources().getDisplayMetrics().density)));
    }

    public void setBottomTopBound(int i,View v){
        setLimitBottom((getHystoricalPointY() + (32 * v.getResources().getDisplayMetrics().density)));
        setLimitTop((getHystoricalPointY() - (32 * v.getResources().getDisplayMetrics().density)));
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

    public void goToPlayGame(){

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

    public boolean getMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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



}
