package it.polimi.group11.helper;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group11.R;

/**
 * @author Paul Burke (ipaulpro)
 */

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    public static List<Guest> mItems = new ArrayList<>();
    private int playerNumber = 2;

    public RecyclerListAdapter() {
        for (int i = 0; i < playerNumber; i++){
            mItems.add(new Guest(
                    GuestData.nameArray[i],
                    GuestData.stockImage,
                    GuestData.id_[i]
            ));
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        TextView name;
        ImageView avatar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        TextView textViewName = holder.name;
        ImageView imageViewAvatar = holder.avatar;

        textViewName.setText(mItems.get(position).getName());
        imageViewAvatar.setImageResource(mItems.get(position).getImage());

    }

    @Override
    public void onItemDismiss(int position) {
        if(playerNumber > 2) {
            mItems.remove(position);
            notifyItemRemoved(position);
            playerNumber--;
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Guest prev = mItems.remove(fromPosition);
        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemInsert(int position){
        if(playerNumber < 4) {
            mItems.add(new Guest(
                    GuestData.nameArray[mItems.size() + 1],
                    GuestData.stockImage,
                    GuestData.id_[mItems.size() + 1]
            ));
            playerNumber++;
            notifyItemInserted(position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
}