package cs.ualberta.ca.medlog.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         Handles the creation of a DialogFragment that provides a user a gui edit a problem
 *         description.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see PatientProblemViewActivity
 */
public class DescriptionEditorFragment extends DialogFragment {
    DescriptionEditorFragment.OnDescriptionSetListener mCallback;

    public interface OnDescriptionSetListener {
        void onNewDescriptionSet(String desc);
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View descEditorView = inflater.inflate(R.layout.fragment_description_editor, null);

        final EditText descEditText = descEditorView.findViewById(R.id.fragmentDescriptionEditor_EditText);
        Bundle descData = getArguments();
        if (descData != null) {
            descEditText.setText(descData.getString("argDesc", ""));
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(descEditorView)
                .setPositiveButton(R.string.fragmentDescriptionEditor_ConfirmButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onNewDescriptionSet(descEditText.getText().toString());

                    }
                })
                .setNegativeButton(R.string.fragmentDescriptionEditor_CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DescriptionEditorFragment.this.getDialog().cancel();
                    }
                });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (DescriptionEditorFragment.OnDescriptionSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnDescriptionSetListener");
        }
    }
}