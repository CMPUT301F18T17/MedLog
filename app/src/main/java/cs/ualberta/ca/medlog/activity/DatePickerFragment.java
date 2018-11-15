package cs.ualberta.ca.medlog.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;

/**
 * <p>
 *     Description: <br>
 *         Handles the creation of a DialogFragment that provides the user with a gui to set a new
 *         date on a visual calendar. It returns a new calendar object reflecting this date.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see PatientAddProblemActivity
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    OnNewDateSetListener mCallback;

    public interface OnNewDateSetListener {
        void onNewDateSet(Calendar cal);
    }

    Calendar cal = Calendar.getInstance();

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle dateData = getArguments();
        cal = Calendar.getInstance();
        if (dateData != null) {
            cal = (Calendar)dateData.getSerializable("argCal");
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnNewDateSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnNewDateSetListener");
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,day);
        mCallback.onNewDateSet(cal);
    }
}