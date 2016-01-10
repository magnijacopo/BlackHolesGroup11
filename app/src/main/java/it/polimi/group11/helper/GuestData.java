package it.polimi.group11.helper;

import it.polimi.group11.R;

/**
 * Created by Alessandro on 06/01/2016.
 */
public class GuestData {
    static String[] nameArray = {"GUEST 1", "GUEST 2", "GUEST 3", "GUEST 4"};
    static Integer stockImage = R.drawable.ic_account_circle_black_24dp;
    static Integer[] id_ = {0, 1, 2, 3};

    public static String getNameArray(int position){
        return nameArray[position];
    }
    public static Integer getStockImage(){
        return stockImage;
    }
    public static Integer getId(int position){
        return id_[position];
    }


}