package cs.ualberta.ca.medlog.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.user.Patient;

/**
 * <p>
 *     Description: <br>
 *         Custom Array Adapter for presenting one Text Views based on provided Patients.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see Patient
 */
public class PatientAdapter extends ArrayAdapter<Patient> {
    public PatientAdapter(Context context, ArrayList<Patient> patientList) {
        super(context,0,patientList);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_item,parent,false);
        }

        Patient patient = getItem(position);

        if (patient != null) {
            TextView textView = convertView.findViewById(R.id.fragmentListItem_TextView);
            textView.setText(patient.getUsername());
        }

        return convertView;
    }
}
