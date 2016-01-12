package it.polimi.group11;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by aless on 12/01/2016.
 */
public class ConfirmDeletionDialog extends DialogFragment {
    DialogListener callback;
    public ConfirmDeletionDialog(){}

    public interface DialogListener{
        void onOkClicked();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            callback = (DialogListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException((activity.toString()) + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        Bundle args = getArguments();
        String title = args.getString("title", "");
        String message = args.getString("message", "");
        return new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onOkClicked();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            }
        }).create();
    }
}
