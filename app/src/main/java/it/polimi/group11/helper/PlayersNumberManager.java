package it.polimi.group11.helper;

/**
 * Created by Alessandro on 10/01/2016.
 */
public class PlayersNumberManager {
    public interface Listener {
        void onPlayersNumberChange(boolean trigger);
    }

    private Listener listener = null;
    public void registerListener (Listener listener){
        this.listener = listener;
    }

    boolean trigger = false;
    public void change(int playersNumber){
        trigger = playersNumber > 3;
        if(listener != null)
            listener.onPlayersNumberChange(trigger);
    }
}

