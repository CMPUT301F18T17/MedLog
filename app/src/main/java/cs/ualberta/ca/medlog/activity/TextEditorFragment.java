package cs.ualberta.ca.medlog.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         Handles the creation of a DialogFragment that provides a gui for a user to edit a piece
 *         of text.
 *         Arguments are used to set any initial text, hints for text entry, the maximum length
 *         of text allowed and what the input type should be. As well an editor id is allowed as an
 *         argument to differentiate different variants returns.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see PatientProblemViewActivity
 * @see PatientProfileActivity
 */
public class TextEditorFragment extends DialogFragment {
    TextEditorFragment.OnTextSetListener mCallback;

    public interface OnTextSetListener {
        void onTextSet(String text,int editorId);
    }

    int editorId = 0;

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View textEditorView = inflater.inflate(R.layout.fragment_text_editor, null);

        final EditText textEdit = textEditorView.findViewById(R.id.fragmentTextEditor_EditText);
        Bundle arguments = getArguments();
        if (arguments != null) {
            editorId = arguments.getInt("argTextId",0);

            textEdit.setText(arguments.getString("argInitialText", ""));
            textEdit.setHint(arguments.getString("argHint",getString(R.string.fragmentTextEditor_EditHint)));

            int maxLength = arguments.getInt("argMaxLength",0);
            if (maxLength > 0) {
                InputFilter[] filter = { new InputFilter.LengthFilter(maxLength) };
                textEdit.setFilters(filter);
            }

            int inputType = arguments.getInt("argInputType",-1);
            if (inputType != -1) {
                if(inputType == InputType.TYPE_TEXT_FLAG_MULTI_LINE) {
                    textEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                }
                textEdit.setInputType(inputType);
            }
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(textEditorView)
                .setPositiveButton(R.string.fragmentTextEditor_ConfirmButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onTextSet(textEdit.getText().toString(), editorId);

                    }
                })
                .setNegativeButton(R.string.fragmentTextEditor_CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextEditorFragment.this.getDialog().cancel();
                    }
                });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (TextEditorFragment.OnTextSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnTextListener");
        }
    }
}