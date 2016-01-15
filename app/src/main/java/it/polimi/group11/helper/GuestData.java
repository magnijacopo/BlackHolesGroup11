package it.polimi.group11.helper;

import it.polimi.group11.R;

/**
 * Created by Alessandro on 06/01/2016.
 */
public class GuestData {
    public static String[] nameArray = {"GUEST 1", "GUEST 2", "GUEST 3", "GUEST 4"};
    static Integer[] imageArray = {R.drawable.blue_planet_icon, R.drawable.green_astronaut_icon, R.drawable.orange_rocket_icon, R.drawable.pink_comet_icon};
    static String[] colorArray = {"#1d8282","#86c467", "#fab31b", "#fc8e86"};
    static Integer[] id_ = {1, 2, 3, 4};
    public static int cardPosition = 0;

    public static String getNameArray(int position){
        return nameArray[position];
    }
    public static Integer getStockImage(int position) { return imageArray[position]; }
    public static String getColor(int position) { return colorArray[position]; }
    public static Integer getId(int position){return id_[position];}



}