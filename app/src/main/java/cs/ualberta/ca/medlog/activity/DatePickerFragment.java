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
 *         Handles the creation of a DialogFragment that provides a user a gui to set a new date
 *         on a calendar.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see PatientAddProblemActivity
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    OnNewDateSetListener mCallback;

    public interface OnNewDateSetListener {
        void onNewDateSet(int year, int month, int day);
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle dateData = getArguments();
        final Calendar cal = Calendar.getInstance();
        int year, month, day;
        if (dateData != null) {
            year = dateData.getInt("argYear", cal.get(Calendar.YEAR));
            month = dateData.getInt("argMonth", cal.get(Calendar.MONTH));
            day = dateData.getInt("argDay", cal.get(Calendar.DAY_OF_MONTH));
        }
        else {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }

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
        mCallback.onNewDateSet(year,month,day);
    }
}