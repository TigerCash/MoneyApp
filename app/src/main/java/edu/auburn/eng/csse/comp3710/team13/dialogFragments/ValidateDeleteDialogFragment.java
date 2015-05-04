package edu.auburn.eng.csse.comp3710.team13.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import edu.auburn.eng.csse.comp3710.team13.home.RecentTransactionsFragment;


public class ValidateDeleteDialogFragment extends DialogFragment {



	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Build the dialog and set up the button click handlers
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("message")
				.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Send the positive button event back to the host activity
						((RecentTransactionsFragment)getTargetFragment()).onValidateDeleteDialogPositiveClick();
					}
				})
				.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Send the negative button event back to the host activity
						((RecentTransactionsFragment)getTargetFragment()).onValidateDeleteDialogNegativeClick();
					}
				});
		return builder.create();
	}

}
