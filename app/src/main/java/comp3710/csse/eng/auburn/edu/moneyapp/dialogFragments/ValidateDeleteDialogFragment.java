package comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.home.RecentTransactionsFragment;


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
