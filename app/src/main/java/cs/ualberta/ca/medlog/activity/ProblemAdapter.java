package cs.ualberta.ca.medlog.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Problem;

/**
 * <p>
 *     Description: <br>
 *         Custom Array Adapter for presenting one with Text Views based on provided Problems.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see Problem
 */
public class ProblemAdapter extends ArrayAdapter<Problem> {
    public ProblemAdapter(Context context, ArrayList<Problem> recordList) {
        super(context,0,recordList);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_item,parent,false);
        }

        Problem problem = getItem(position);
        String problemText = problem.getTitle() + " | ";
        Calendar cal = Calendar.getInstance();
        cal.setTime(problem.getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        problemText += String.format(Locale.getDefault(),"Since: %04d/%02d/%02d",year,month,day) + " | ";
        problemText += Integer.toString(problem.getRecords().size());

        TextView textView = convertView.findViewById(R.id.fragmentListItem_TextView);
        textView.setText(problemText);

        return convertView;
    }
}
