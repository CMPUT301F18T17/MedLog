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
 *         title.
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
public class TitleEditorFragment extends DialogFragment {
    TitleEditorFragment.OnTitleSetListener mCallback;

    public interface OnTitleSetListener {
        void onNewTitleSet(String desc);
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View titleEditorView = inflater.inflate(R.layout.fragment_title_editor, null);

        final EditText titleEditText = titleEditorView.findViewById(R.id.fragmentTitleEditor_EditText);
        Bundle titleData = getArguments();
        if (titleData != null) {
            titleEditText.setText(titleData.getString("argTitle", ""));
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(titleEditorView)
                .setPositiveButton(R.string.fragmentTitleEditor_ConfirmButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onNewTitleSet(titleEditText.getText().toString());

                    }
                })
                .setNegativeButton(R.string.fragmentTitleEditor_CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TitleEditorFragment.this.getDialog().cancel();
                    }
                });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (TitleEditorFragment.OnTitleSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnTitleSetListener");
        }
    }
}