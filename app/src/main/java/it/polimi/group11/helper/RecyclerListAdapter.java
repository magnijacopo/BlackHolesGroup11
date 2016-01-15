package it.polimi.group11.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group11.ChoosePlayerTypeActivity;
import it.polimi.group11.R;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    public List<Guest> mItems = new ArrayList<>();
    Context contexto;
    public int guestNumber = 0;

    public RecyclerListAdapter(Context context) {
        this.contexto = context;

        for (int i = 0; i < 4; i++){
            mItems.add(new Guest(
                    GuestData.nameArray[i],
                    GuestData.imageArray[i],
                    GuestData.colorArray[i],
                    GuestData.id_[i]
            ));
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, View.OnClickListener{
        TextView name;
        ImageView avatar;
        public IMyViewHolderClicks mListener;

        public ItemViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name.setOnClickListener(this);
            avatar.setOnClickListener(this);
        }

        @Override
        public void onItemSelected() {}

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

        @Override
        public void onClick(View v){
            Log.i("position: ", Integer.toString(this.getLayoutPosition()));
            if (v instanceof ImageView){
                mListener.onTomato((ImageView)v);
            } else {
                mListener.onPotato(v, this.getLayoutPosition());
            }
        }

        public interface IMyViewHolderClicks {
            void onPotato(View caller, int position);
            void onTomato(ImageView callerImage);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        RecyclerListAdapter.ItemViewHolder vh = new ItemViewHolder(view, new RecyclerListAdapter.ItemViewHolder.IMyViewHolderClicks() {
            public void onPotato(View caller, int position) {
                GuestData.cardPosition = position;
                Log.i("valore guest: ", Integer.toString(guestNumber));
                Intent intent = new Intent(parent.getContext(), ChoosePlayerTypeActivity.class);
                parent.getContext().startActivity(intent);
            }
            public void onTomato(ImageView callerImage) { Log.i("eerer", "yoyo"); }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        TextView textViewName = holder.name;
        ImageView imageViewAvatar = holder.avatar;
        textViewName.setTypeface(Typeface.createFromAsset(contexto.getAssets(), "fonts/SignPainter-HouseScript.ttf"));
        textViewName.setText(mItems.get(position).getName());
        imageViewAvatar.setImageResource(mItems.get(position).getIcon());
    }

    @Override
    public void onItemDismiss(int position) {
        if(mItems.size() > 2) {
            mItems.remove(position);
            notifyItemRemoved(position);
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
        if(mItems.size() < 4) {
            mItems.add(new Guest(
                    GuestData.nameArray[mItems.size() + 1],
                    GuestData.imageArray[mItems.size() + 1],
                    GuestData.colorArray[mItems.size() + 1],
                    GuestData.id_[mItems.size() + 1]
            ));
            notifyItemInserted(position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}